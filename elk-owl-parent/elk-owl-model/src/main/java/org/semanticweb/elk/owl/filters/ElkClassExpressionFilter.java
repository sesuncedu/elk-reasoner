/*
 * #%L
 * elk-reasoner
 * 
 * $Id: ElkClassExpressionVisitor.java 300 2011-08-10 16:52:49Z mak@aifb.uni-karlsruhe.de $
 * $HeadURL: https://elk-reasoner.googlecode.com/svn/trunk/elk-reasoner/src/main/java/org/semanticweb/elk/syntax/ElkClassExpressionVisitor.java $
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
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
/**
 * @author Yevgeny Kazakov, Apr 8, 2011
 */
package org.semanticweb.elk.owl.filters;

import org.semanticweb.elk.owl.interfaces.ElkClassExpression;

/**
 * A filter producing objects in {@link ElkClassExpression} from objects of this
 * type.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public interface ElkClassExpressionFilter extends ElkClassFilter,
		ElkDataPropertyListRestrictionQualifiedFilter,
		ElkObjectComplementOfFilter, ElkObjectIntersectionOfFilter,
		ElkObjectOneOfFilter, ElkObjectUnionOfFilter,
		ElkPropertyRestrictionFilter {

	// combined visitor
}
