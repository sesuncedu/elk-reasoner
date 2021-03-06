package org.semanticweb.elk.reasoner.indexing.hierarchy;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
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

import java.util.Set;

import org.semanticweb.elk.owl.interfaces.ElkObjectUnionOf;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedObjectUnionOfVisitor;

/**
 * Represents occurrences of an {@link ElkObjectUnionOf} in an ontology.
 * 
 * @author "Yevgeny Kazakov"
 */
public interface IndexedObjectUnionOf extends IndexedClassExpression {

	/**
	 * @return the {@link IndexedClassExpression}s representing the disjuncts of
	 *         the {@link ElkObjectUnionOf} represented by this
	 *         {@link IndexedObjectUnionOf}.
	 * 
	 * @see IndexedObjectUnionOf#getDisjuncts()
	 */
	public Set<? extends IndexedClassExpression> getDisjuncts();

	public <O> O accept(IndexedObjectUnionOfVisitor<O> visitor);

}
