/*
 * #%L
 * ELK OWL Model Implementation
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
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
 * 
 */
package org.semanticweb.elk.owl.implementation;

import java.util.Set;

import org.semanticweb.elk.owl.interfaces.ElkClassExpression;
import org.semanticweb.elk.owl.interfaces.ElkDataPropertyExpression;
import org.semanticweb.elk.owl.interfaces.ElkHasKeyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyExpression;
import org.semanticweb.elk.owl.visitors.ElkAxiomVisitor;
import org.semanticweb.elk.owl.visitors.ElkHasKeyAxiomVisitor;
import org.semanticweb.elk.owl.visitors.ElkObjectVisitor;

/**
 * Implementation of {@link ElkHasKeyAxiom}
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * 
 */
public class ElkHasKeyAxiomImpl implements ElkHasKeyAxiom {

	private final ElkClassExpression classExp_;
	private final Set<ElkObjectPropertyExpression> objectPropExprs_;
	private final Set<ElkDataPropertyExpression> dataPropExprs_;

	ElkHasKeyAxiomImpl(ElkClassExpression classExp,
			Set<ElkObjectPropertyExpression> objectPEs,
			Set<ElkDataPropertyExpression> dataPEs) {
		classExp_ = classExp;
		objectPropExprs_ = objectPEs;
		dataPropExprs_ = dataPEs;
	}

	@Override
	public ElkClassExpression getClassExpression() {
		return classExp_;
	}

	@Override
	public Set<ElkObjectPropertyExpression> getObjectPropertyExpressions() {
		return objectPropExprs_;
	}

	@Override
	public Set<ElkDataPropertyExpression> getDataPropertyExpressions() {
		return dataPropExprs_;
	}

	@Override
	public <O> O accept(ElkAxiomVisitor<O> visitor) {
		return accept((ElkHasKeyAxiomVisitor<O>) visitor);
	}

	@Override
	public <O> O accept(ElkObjectVisitor<O> visitor) {
		return accept((ElkHasKeyAxiomVisitor<O>) visitor);
	}

	@Override
	public <O> O accept(ElkHasKeyAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}
}