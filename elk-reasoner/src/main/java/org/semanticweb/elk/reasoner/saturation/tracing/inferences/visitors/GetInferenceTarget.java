/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors;

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
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedBackwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedExistentialBackwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.Inference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.PropagatedSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ReversedForwardLink;

/**
 * Given an {@link Inference}, returns the root of the context to which this
 * inference should be produced.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class GetInferenceTarget extends
		AbstractInferenceVisitor<Context, IndexedClassExpression> {

	@Override
	protected IndexedClassExpression defaultTracedVisit(Inference conclusion,
			Context premiseContext) {
		// by default produce to the context where the inference has been
		// made (where its premises are stored)
		return premiseContext.getRoot();
	}

	@Override
	public IndexedClassExpression visit(PropagatedSubsumer conclusion,
			Context premiseContext) {
		return conclusion.getBackwardLink().getSource();
	}

	@Override
	public IndexedClassExpression visit(ComposedBackwardLink conclusion,
			Context premiseContext) {
		return conclusion.getForwardLink().getTarget();
	}

	@Override
	public IndexedClassExpression visit(ReversedForwardLink conclusion,
			Context premiseContext) {
		return conclusion.getSourceLink().getTarget();
	}

	@Override
	public IndexedClassExpression visit(
			DecomposedExistentialBackwardLink conclusion, Context premiseContext) {
		return conclusion.getExistential().getExpression().getFiller();
	}
}
