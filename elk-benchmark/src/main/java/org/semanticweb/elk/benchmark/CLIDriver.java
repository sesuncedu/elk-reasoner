/*
 * #%L
 * ELK Bencharking Package
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
package org.semanticweb.elk.benchmark;

import org.semanticweb.elk.benchmark.reasoning.ClassificationTask;



/**
 * A simple command line tool which instantiates a task and passes it to the
 * task runner.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */	
public class CLIDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BenchmarkUtils.runTask(ClassificationTask.class.getName(),
				1, 10,
				//new String[]{"/home/pavel/ulm/data/snomed/snomedStated_INT_20130731.owl", "4"});
				new String[]{"/Users/ses/src/Semantic/GO-cache/src/test/resources/gene_ontology_write.ofn", "4"});
		/*BenchmarkUtils.runTaskCollection2(IncrementalClassificationMultiDeltas.class.getName(),
				0, 1,
				new String[]{"/home/pavel/ulm/data/snomed/incremental-1", "4"});*/
		/*BenchmarkUtils.runTask(RandomWalkIncrementalClassificationTask.class.getName(),
				0,
				2,
				new String[]{"/home/pavel/ulm/data/galens/EL-GALEN.owl"});*/
		/*BenchmarkUtils.runTask(RandomWalkIncrementalClassificationWithABoxTask.class.getName(),
				0,
				1,
				new String[]{"/home/pavel/ulm/data/VFB/fbbt_FC_all_clustered_ind.owl"});*/
		/*BenchmarkUtils.runTaskCollection2(AllSubsumptionTracingTaskCollection.class.getName(),
				0, 1,
				new String[]{"/home/pavel/ulm/data/galens/EL-GALEN.owl", "1"});*/
				//new String[]{"/home/pavel/ulm/data/go/go.r14858.fss.owl", "1"});
				//new String[]{"/home/pavel/ulm/data/go/go.r7991.fss.owl", "1"});
				//new String[]{"/home/pavel/ulm/data/go/go_merged_rewritten.owl", "1"});
				//new String[]{"/home/pavel/ulm/data/snomed/snomedStated_INT_20130731.owl", "1"});
		/*BenchmarkUtils.runTaskCollection2(SampleSubsumptionsForJustifications.class.getName(),
				0, 1,
				//new String[]{"/home/pavel/ulm/data/galens/EL-GALEN-rewritten.owl", "1", "10000"});
				new String[]{"/home/pavel/ulm/data/go/go_merged_rewritten.owl", "1", "100000"});
				//new String[]{"/home/pavel/ulm/data/snomed/snomedStated_INT_20130731_rewritten.owl", "4", "10000"});
*/	}
}
