/*
 * #%L
 * ELK Utilities for Testing
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
package org.semanticweb.elk.testing;

import java.io.File;
import java.io.IOException;

import org.semanticweb.elk.io.FileUtils;

/**
 * A collection of test utilities, e.g., for creating and destroying the test environment  
 * 
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class TestUtils {

	public static final String TEST_ROOT = "test.home"; 
	
	public static void createTestEnvironment(File baseDir) {
		File root = new File(baseDir.getAbsolutePath() + "/" + TEST_ROOT);
		
		if (root.exists()) {
			try {
				FileUtils.deleteRecursively(root);
			} catch (IOException e) {
				throw new RuntimeException("Initialization of test environment failed, unable to delete the root test folder");
			}
		}
		
		if (!root.mkdirs()) {
			throw new RuntimeException("Initialization of test environment failed, unable to create the root test folder");
		}
	}
	
	public static void cleanUp(File baseDir) {
		try {
			FileUtils.deleteRecursively(new File(baseDir.getAbsolutePath() + "/" + TEST_ROOT));
		} catch (IOException e) {
			throw new RuntimeException("Clean-up of test environment failed, unable to delete the root test folder");
		}
	}
}
