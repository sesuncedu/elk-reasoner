package org.semanticweb.elk.reasoner.saturation.conclusions.visitors;

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

import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ComposedSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Conclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ContextInitialization;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Contradiction;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.DecomposedSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.DisjointSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Propagation;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.SubContextInitialization;
import org.semanticweb.elk.reasoner.saturation.context.ContextPremises;
import org.semanticweb.elk.reasoner.saturation.rules.ConclusionProducer;
import org.semanticweb.elk.reasoner.saturation.rules.RuleVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.backwardlinks.LinkedBackwardLinkRule;
import org.semanticweb.elk.reasoner.saturation.rules.backwardlinks.SubsumerBackwardLinkRule;
import org.semanticweb.elk.reasoner.saturation.rules.contextinit.LinkedContextInitRule;
import org.semanticweb.elk.reasoner.saturation.rules.contradiction.ContradictionPropagationRule;
import org.semanticweb.elk.reasoner.saturation.rules.disjointsubsumer.ContradicitonCompositionRule;
import org.semanticweb.elk.reasoner.saturation.rules.forwardlink.BackwardLinkFromForwardLinkRule;
import org.semanticweb.elk.reasoner.saturation.rules.forwardlink.ReflexiveBackwardLinkCompositionRule;
import org.semanticweb.elk.reasoner.saturation.rules.propagations.ReflexivePropagationRule;
import org.semanticweb.elk.reasoner.saturation.rules.subcontextinit.PropagationInitializationRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link ConclusionVisitor} that applies non-redundant rules for the visited
 * {@link Conclusion}s using the provided {@link RuleVisitor} to track rule
 * applications and {@link ConclusionProducer} to output the {@link Conclusion}s
 * of the applied rules. A rule is local if it produces only {@link Conclusion}s
 * that logically belong to the same root as the {@link Conclusion} to which it
 * applies. The methods always return {@link true}.
 * 
 * @see RedundantRuleApplicationConclusionVisitor
 * 
 * @author "Yevgeny Kazakov"
 */
public class NonRedundantLocalRuleApplicationConclusionVisitor extends
		AbstractRuleApplicationConclusionVisitor {

	// logger for events
	private static final Logger LOGGER_ = LoggerFactory
			.getLogger(NonRedundantLocalRuleApplicationConclusionVisitor.class);

	/**
	 * cached rules for frequent use
	 */
	private static ContradicitonCompositionRule CONTRADICTION_COMPOSITION_RULE_ = new ContradicitonCompositionRule();

	public NonRedundantLocalRuleApplicationConclusionVisitor(
			RuleVisitor ruleAppVisitor, ConclusionProducer producer) {
		super(ruleAppVisitor, producer);
	}

	@Override
	protected Boolean defaultVisit(Conclusion conclusion, ContextPremises input) {
		// all methods should be explicitly implemented
		throw new RuntimeException("Non-redundant rules for " + conclusion
				+ " not implemented!");
	}

	@Override
	public Boolean visit(BackwardLink subConclusion, ContextPremises premises) {
		ruleAppVisitor.visit(SubsumerBackwardLinkRule.getInstance(),
				subConclusion, premises, producer);

		// apply all backward link rules of the context
		LinkedBackwardLinkRule backLinkRule = premises
				.getBackwardLinkRuleHead();
		while (backLinkRule != null) {
			backLinkRule.accept(ruleAppVisitor, subConclusion, premises,
					producer);
			backLinkRule = backLinkRule.next();
		}
		return true;
	}

	@Override
	public Boolean visit(Propagation subConclusion, ContextPremises premises) {
		// propagate over reflexive backward links
		ruleAppVisitor.visit(ReflexivePropagationRule.getInstance(),
				subConclusion, premises, producer);
		return true;
	}

	@Override
	public Boolean visit(SubContextInitialization subConclusion,
			ContextPremises premises) {
		LOGGER_.trace("{}::{} applying sub-concept init rules:",
				premises.getRoot(), subConclusion.getSubRoot());
		PropagationInitializationRule.getInstance().accept(ruleAppVisitor,
				subConclusion, premises, producer);
		return true;
	}

	@Override
	public Boolean visit(ComposedSubsumer<?> conclusion,
			ContextPremises premises) {
		applyCompositionRules(conclusion, premises);
		return true;
	}

	@Override
	public Boolean visit(ContextInitialization conclusion,
			ContextPremises premises) {
		LinkedContextInitRule rule = conclusion.getContextInitRuleHead();
		LOGGER_.trace("applying init rules:");
		while (rule != null) {
			LOGGER_.trace("init rule: {}", rule.getName());
			rule.accept(ruleAppVisitor, conclusion, premises, producer);
			rule = rule.next();
		}
		return true;
	}

	@Override
	public Boolean visit(Contradiction conclusion, ContextPremises premises) {
		ruleAppVisitor.visit(ContradictionPropagationRule.getInstance(),
				conclusion, premises, producer);
		return true;
	}

	@Override
	public Boolean visit(DecomposedSubsumer<?> conclusion,
			ContextPremises premises) {
		applyCompositionRules(conclusion, premises);
		applyDecompositionRules(conclusion, premises);
		return true;
	}

	@Override
	public Boolean visit(DisjointSubsumer conclusion, ContextPremises premises) {
		//if (premises.isInconsistForDisjointnessAxiom(conclusion.getAxiom())) {
		ruleAppVisitor.visit(CONTRADICTION_COMPOSITION_RULE_, conclusion,
					premises, producer);
		//}
		return true;
	}

	@Override
	public Boolean visit(ForwardLink conclusion, ContextPremises premises) {
		// generate backward links
		ruleAppVisitor.visit(BackwardLinkFromForwardLinkRule.getInstance(),
				conclusion, premises, producer);
		// compose with reflexive backward links
		ruleAppVisitor.visit(
				ReflexiveBackwardLinkCompositionRule.getRuleFor(conclusion),
				conclusion, premises, producer);
		return true;
	}

}
