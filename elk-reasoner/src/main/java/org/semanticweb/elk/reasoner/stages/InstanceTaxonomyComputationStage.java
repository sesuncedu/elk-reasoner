/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.reasoner.stages;

import org.semanticweb.elk.reasoner.taxonomy.InstanceTaxonomyComputation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link ReasonerStage} during which the instance taxonomy of the current
 * ontology is computed
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
class InstanceTaxonomyComputationStage extends AbstractReasonerStage {

	// logger for this class
	private static final Logger LOGGER_ = LoggerFactory
			.getLogger(InstanceTaxonomyComputationStage.class);

	/**
	 * the computation used for this stage
	 */
	private InstanceTaxonomyComputation computation_ = null;

	public InstanceTaxonomyComputationStage(AbstractReasonerState reasoner,
			AbstractReasonerStage... preStages) {
		super(reasoner, preStages);
	}

	@Override
	public String getName() {
		return "Instance Taxonomy Computation";
	}

	@Override
	public boolean preExecute() {
		if (!super.preExecute())
			return false;

		if (reasoner.doneTaxonomy()) {
			reasoner.initInstanceTaxonomy();

			computation_ = new InstanceTaxonomyComputation(
					reasoner.ontologyIndex.getIndividuals(),
					reasoner.getProcessExecutor(), workerNo, progressMonitor,
					reasoner.saturationState,
					reasoner.instanceTaxonomyState.getTaxonomy());
		}

		if (LOGGER_.isDebugEnabled()) {
			LOGGER_.debug(getName() + " using " + workerNo + " workers");
		}

		return true;
	}

	@Override
	public void executeStage() throws ElkInterruptedException {
		for (;;) {
			computation_.process();
			if (!spuriousInterrupt())
				break;
		}
	}

	@Override
	public boolean postExecute() {
		if (!super.postExecute())
			return false;

		this.computation_ = null;

		return true;
	}

	@Override
	public void printInfo() {
		if (computation_ != null)
			computation_.printStatistics();
	}

}
