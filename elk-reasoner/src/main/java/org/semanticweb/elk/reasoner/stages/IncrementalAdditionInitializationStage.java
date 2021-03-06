package org.semanticweb.elk.reasoner.stages;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.semanticweb.elk.owl.interfaces.ElkAnnotationProperty;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkDataProperty;
import org.semanticweb.elk.owl.interfaces.ElkDatatype;
import org.semanticweb.elk.owl.interfaces.ElkEntity;
import org.semanticweb.elk.owl.interfaces.ElkNamedIndividual;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.semanticweb.elk.owl.visitors.ElkEntityVisitor;
import org.semanticweb.elk.reasoner.incremental.IncrementalChangesInitialization;
import org.semanticweb.elk.reasoner.incremental.IncrementalStages;
import org.semanticweb.elk.reasoner.indexing.conversion.ElkPolarityExpressionConverter;
import org.semanticweb.elk.reasoner.indexing.conversion.ElkPolarityExpressionConverterImpl;
import org.semanticweb.elk.reasoner.indexing.hierarchy.DifferentialIndex;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.saturation.ContextCreationListener;
import org.semanticweb.elk.reasoner.saturation.ContextModificationListener;
import org.semanticweb.elk.reasoner.saturation.SaturationStateWriter;
import org.semanticweb.elk.reasoner.saturation.SaturationUtils;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.ContextInitializationImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Conclusion;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.contextinit.LinkedContextInitRule;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.ChainableSubsumerRule;
import org.semanticweb.elk.util.collections.Operations;

/**
 * 
 * 
 */
public class IncrementalAdditionInitializationStage extends
		AbstractIncrementalChangesInitializationStage {

	public IncrementalAdditionInitializationStage(
			AbstractReasonerState reasoner, AbstractReasonerStage... preStages) {
		super(reasoner, preStages);
	}

	@Override
	protected IncrementalStages stage() {
		return IncrementalStages.ADDITIONS_INIT;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preExecute() {
		if (!super.preExecute())
			return false;

		DifferentialIndex diffIndex = reasoner.ontologyIndex;
		LinkedContextInitRule changedInitRules = null;
		Map<? extends IndexedClassExpression, ChainableSubsumerRule> changedRulesByCE = null;
		Collection<ArrayList<Context>> inputs = Collections.emptyList();
		ContextCreationListener contextCreationListener = SaturationUtils
				.addStatsToContextCreationListener(
						ContextCreationListener.DUMMY,
						stageStatistics_.getContextStatistics());
		ContextModificationListener contextModificationListener = SaturationUtils
				.addStatsToContextModificationListener(
						ContextModificationListener.DUMMY,
						stageStatistics_.getContextStatistics());

		// first, create and init contexts for new classes
		final ElkPolarityExpressionConverter converter = new ElkPolarityExpressionConverterImpl(
				reasoner.ontologyIndex);
		final SaturationStateWriter<?> writer =

		SaturationUtils.getStatsAwareWriter(reasoner.saturationState
				.getContextCreatingWriter(contextCreationListener,
						contextModificationListener), stageStatistics_);

		// used to initialize new contexts
		Conclusion contextInitConclusion = new ContextInitializationImpl(
				reasoner.saturationState.getOntologyIndex());

		for (ElkEntity newEntity : Operations.concat(
				reasoner.ontologyIndex.getAddedClasses(),
				reasoner.ontologyIndex.getAddedIndividuals())) {
			IndexedClassExpression ice = newEntity
					.accept(new ElkEntityVisitor<IndexedClassExpression>() {

						@Override
						public IndexedClassExpression visit(
								ElkAnnotationProperty elkAnnotationProperty) {
							return null;
						}

						@Override
						public IndexedClassExpression visit(ElkClass elkClass) {
							return converter.visit(elkClass);
						}

						@Override
						public IndexedClassExpression visit(
								ElkDataProperty elkDataProperty) {
							return null;
						}

						@Override
						public IndexedClassExpression visit(
								ElkDatatype elkDatatype) {
							return null;
						}

						@Override
						public IndexedClassExpression visit(
								ElkNamedIndividual elkNamedIndividual) {
							return converter.visit(elkNamedIndividual);
						}

						@Override
						public IndexedClassExpression visit(
								ElkObjectProperty elkObjectProperty) {
							return null;
						}
					});

			if (reasoner.saturationState.getContext(ice) == null)
				writer.produce(ice, contextInitConclusion);
		}

		changedInitRules = diffIndex.getAddedContextInitRules();
		changedRulesByCE = diffIndex.getAddedContextRulesByClassExpressions();

		if (changedInitRules != null || !changedRulesByCE.isEmpty()) {
			inputs = Operations.split(reasoner.saturationState.getContexts(),
					8 * workerNo);
		}

		this.initialization = new IncrementalChangesInitialization(inputs,
				changedInitRules, changedRulesByCE, reasoner.saturationState,
				reasoner.getProcessExecutor(), stageStatistics_, workerNo,
				reasoner.getProgressMonitor());

		return true;
	}

	@Override
	public boolean postExecute() {
		if (!super.postExecute())
			return false;
		reasoner.ontologyIndex.commitAddedRules();
		reasoner.ontologyIndex.initClassChanges();
		reasoner.ontologyIndex.initIndividualChanges();
		return true;
	}

}
