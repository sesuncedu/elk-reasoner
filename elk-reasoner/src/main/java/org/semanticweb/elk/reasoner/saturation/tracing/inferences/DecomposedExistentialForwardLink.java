/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.DecomposedSubsumerImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.ForwardLinkImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Subsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.InferenceVisitor;

/**
 * A {@link ForwardLink} that is obtained by decomposing an
 * {@link IndexedObjectSomeValuesFrom}.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class DecomposedExistentialForwardLink extends ForwardLinkImpl implements
		Inference {

	private final IndexedObjectSomeValuesFrom existential_;

	/**
	 * 
	 */
	public DecomposedExistentialForwardLink(IndexedObjectSomeValuesFrom subsumer) {
		super(subsumer.getProperty(), subsumer.getFiller());
		existential_ = subsumer;
	}

	@Override
	public <I, O> O acceptTraced(InferenceVisitor<I, O> visitor, I parameter) {
		return visitor.visit(this, parameter);
	}

	public Subsumer<IndexedObjectSomeValuesFrom> getExistential() {
		return new DecomposedSubsumerImpl<IndexedObjectSomeValuesFrom>(
				existential_);
	}

	@Override
	public IndexedClassExpression getInferenceContextRoot(
			IndexedClassExpression rootWhereStored) {
		return rootWhereStored;
	}

	@Override
	public String toString() {
		return super.toString() + " (decomposition)";
	}
}
