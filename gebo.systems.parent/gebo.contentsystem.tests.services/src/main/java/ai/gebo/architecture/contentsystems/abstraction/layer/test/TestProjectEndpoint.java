/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contentsystems.abstraction.layer.test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.GUserMessage;

/**
 * AI generated comments
 * 
 * A test implementation of GProjectEndpoint for testing purposes.
 * This class allows for simulating project endpoints with test data
 * like virtual folders, document references, user messages, and filesystem paths.
 */
public class TestProjectEndpoint extends GProjectEndpoint {
	/**
	 * Enum defining the types of test endpoints.
	 * PATH_CONTENTS: Represents endpoint containing file system paths.
	 * CONTAINED_CONTENTS: Represents endpoint containing other content types.
	 */
	public static enum TestEndpointType {
		PATH_CONTENTS,CONTAINED_CONTENTS
	}
	
	/**
	 * Collection of virtual folders for testing.
	 */
	private List<GVirtualFolder> testVirtualFolders = new ArrayList<GVirtualFolder>();
	
	/**
	 * Collection of document references for testing.
	 */
	private List<GDocumentReference> testDocumentReferences = new ArrayList<GDocumentReference>();
	
	/**
	 * Collection of user messages for testing.
	 */
	private List<GUserMessage> testUserMessages=new ArrayList<GUserMessage>();
	
	/**
	 * Collection of filesystem paths for testing.
	 */
	private List<String> testFilesystemPaths=new ArrayList<String>();
	
	/**
	 * The type of this test endpoint.
	 */
	private TestEndpointType testType=null;
	
	/**
	 * Default constructor for creating a new TestProjectEndpoint.
	 */
	public TestProjectEndpoint() {

	}

	/**
	 * Gets the list of test virtual folders.
	 * 
	 * @return The list of virtual folders used for testing
	 */
	public List<GVirtualFolder> getTestVirtualFolders() {
		return testVirtualFolders;
	}

	/**
	 * Sets the list of test virtual folders.
	 * 
	 * @param testVirtualFolders The list of virtual folders to use for testing
	 */
	public void setTestVirtualFolders(List<GVirtualFolder> testVirtualFolders) {
		this.testVirtualFolders = testVirtualFolders;
	}

	/**
	 * Gets the list of test document references.
	 * 
	 * @return The list of document references used for testing
	 */
	public List<GDocumentReference> getTestDocumentReferences() {
		return testDocumentReferences;
	}

	/**
	 * Sets the list of test document references.
	 * 
	 * @param testDocumentReferences The list of document references to use for testing
	 */
	public void setTestDocumentReferences(List<GDocumentReference> testDocumentReferences) {
		this.testDocumentReferences = testDocumentReferences;
	}

	/**
	 * Gets the list of test user messages.
	 * 
	 * @return The list of user messages used for testing
	 */
	public List<GUserMessage> getTestUserMessages() {
		return testUserMessages;
	}

	/**
	 * Sets the list of test user messages.
	 * 
	 * @param testUserMessages The list of user messages to use for testing
	 */
	public void setTestUserMessages(List<GUserMessage> testUserMessages) {
		this.testUserMessages = testUserMessages;
	}

	/**
	 * Gets the type of this test endpoint.
	 * 
	 * @return The type of this test endpoint
	 */
	public TestEndpointType getTestType() {
		return testType;
	}

	/**
	 * Sets the type of this test endpoint.
	 * 
	 * @param testType The type to set for this test endpoint
	 */
	public void setTestType(TestEndpointType testType) {
		this.testType = testType;
	}

	/**
	 * Gets the list of test filesystem paths.
	 * 
	 * @return The list of filesystem paths used for testing
	 */
	public List<String> getTestFilesystemPaths() {
		return testFilesystemPaths;
	}

	/**
	 * Sets the list of test filesystem paths.
	 * 
	 * @param testFilesystemPaths The list of filesystem paths to use for testing
	 */
	public void setTestFilesystemPaths(List<String> testFilesystemPaths) {
		this.testFilesystemPaths = testFilesystemPaths;
	}

}