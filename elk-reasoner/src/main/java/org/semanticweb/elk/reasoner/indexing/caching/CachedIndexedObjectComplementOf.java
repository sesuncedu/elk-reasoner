package org.semanticweb.elk.reasoner.indexing.caching;

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
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedObjectComplementOf;

/**
 * A {@link ModifiableIndexedObjectComplementOf} that can be used for
 * memoization (caching).
 * 
 * @author "Yevgeny Kazakov"
 *
 * @param <T>
 *            the type of the {@link CachedIndexedObjectComplementOf}
 */
public interface CachedIndexedObjectComplementOf extends
		ModifiableIndexedObjectComplementOf,
		CachedIndexedComplexClassExpression<CachedIndexedObjectComplementOf> {

	static class Helper extends CachedIndexedObject.Helper {

		public static int structuralHashCode(IndexedClassExpression negated) {
			return combinedHashCode(combinedHashCode(
					CachedIndexedObjectComplementOf.class, negated));
		}

		public static CachedIndexedObjectComplementOf structuralEquals(
				CachedIndexedObjectComplementOf first, Object second) {
			if (first == second) {
				return first;
			}
			if (second instanceof CachedIndexedObjectComplementOf) {
				CachedIndexedObjectComplementOf secondEntry = (CachedIndexedObjectComplementOf) second;
				if (first.getNegated().equals(secondEntry.getNegated()))
					return secondEntry;
			}
			// else
			return null;
		}

	}

}
