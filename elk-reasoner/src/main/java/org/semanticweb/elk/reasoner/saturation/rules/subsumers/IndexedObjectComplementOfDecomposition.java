package org.semanticweb.elk.reasoner.saturation.rules.subsumers;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectComplementOf;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Contradiction;
import org.semanticweb.elk.reasoner.saturation.context.ContextPremises;
import org.semanticweb.elk.reasoner.saturation.rules.ConclusionProducer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromNegation;

/**
 * A {@link SubsumerDecompositionRule} producing {@link Contradiction} when
 * processing the {@link IndexedObjectComplementOf} which negation
 * {@link IndexedClassExpression} is contained in the {@code Context}
 * 
 * @see IndexedObjectComplementOf#getNegated()
 * @see ContradictionFromNegationRule
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class IndexedObjectComplementOfDecomposition extends
		AbstractSubsumerDecompositionRule<IndexedObjectComplementOf> {

	public static final String NAME = "IndexedObjectComplementOf Decomposition";

	private static IndexedObjectComplementOfDecomposition INSTANCE_ = new IndexedObjectComplementOfDecomposition();

	public static IndexedObjectComplementOfDecomposition getInstance() {
		return INSTANCE_;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void accept(SubsumerDecompositionRuleVisitor visitor,
			IndexedObjectComplementOf premise, ContextPremises premises,
			ConclusionProducer producer) {
		visitor.visit(this, premise, premises, producer);
	}

	@Override
	public void apply(IndexedObjectComplementOf premise,
			ContextPremises premises, ConclusionProducer producer) {
		if (premises.getSubsumers().contains(premise.getNegated())) {
			//producer.produce(premises.getRoot(), ContradictionImpl.getInstance());
			producer.produce(premises.getRoot(), new ContradictionFromNegation(premise));
		}
	}
}
