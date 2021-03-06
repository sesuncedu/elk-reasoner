package org.semanticweb.elk.reasoner.saturation.rules.contradiction;

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

import java.util.Map;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Contradiction;
import org.semanticweb.elk.reasoner.saturation.context.ContextPremises;
import org.semanticweb.elk.reasoner.saturation.context.SubContextPremises;
import org.semanticweb.elk.reasoner.saturation.rules.ConclusionProducer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.PropagatedContradiction;

/**
 * A {@link ContradictionRule} applied when processing {@link Contradiction}
 * producing {@link Contradiction} in all contexts linked by
 * {@link BackwardLink}s in a {@code Context}
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class ContradictionPropagationRule extends AbstractContradictionRule {

	private static final ContradictionPropagationRule INSTANCE_ = new ContradictionPropagationRule();

	public static final String NAME = "Contradiction Propagation over Backward Links";

	private ContradictionPropagationRule() {
		// do not allow creation of instances outside of this class
	}

	public static ContradictionPropagationRule getInstance() {
		return INSTANCE_;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void apply(Contradiction premise, ContextPremises premises,
			ConclusionProducer producer) {
		final Map<IndexedObjectProperty, ? extends SubContextPremises> subPremises = premises
				.getSubContextPremisesByObjectProperty();
		// no need to propagate over reflexive links
		for (IndexedObjectProperty propRelation : subPremises.keySet()) {
			for (IndexedClassExpression target : subPremises.get(propRelation)
					.getLinkedRoots()) {
				//producer.produce(target, premise);
				producer.produce(target, new PropagatedContradiction(propRelation, target, premises.getRoot()));
			}
		}
	}

	@Override
	public void accept(ContradictionRuleVisitor visitor, Contradiction premise,
			ContextPremises premises, ConclusionProducer producer) {
		visitor.visit(this, premise, premises, producer);
	}

}