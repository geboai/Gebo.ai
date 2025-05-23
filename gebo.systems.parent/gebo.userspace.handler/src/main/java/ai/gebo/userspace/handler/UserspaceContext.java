/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * AI generated comments
 * 
 * This class represents a userspace context that implements the IGVirtualFileSystemContext interface.
 * It manages userspace project endpoints and provides methods to access and modify these endpoints.
 */
public class UserspaceContext implements IGVirtualFileSystemContext {
	/** List of userspace project endpoints associated with this context */
	private List<GUserspaceProjectEndpoint> endpoints=new ArrayList<GUserspaceProjectEndpoint>();
	
	/**
	 * Default constructor that initializes a new empty userspace context.
	 */
	public UserspaceContext() {
		
	}
	
	/**
	 * Returns the list of userspace project endpoints.
	 * 
	 * @return the list of GUserspaceProjectEndpoint objects
	 */
	public List<GUserspaceProjectEndpoint> getEndpoints() {
		return endpoints;
	}
	
	/**
	 * Sets the list of userspace project endpoints.
	 * 
	 * @param endpoints the list of GUserspaceProjectEndpoint objects to set
	 */
	public void setEndpoints(List<GUserspaceProjectEndpoint> endpoints) {
		this.endpoints = endpoints;
	}
	

}