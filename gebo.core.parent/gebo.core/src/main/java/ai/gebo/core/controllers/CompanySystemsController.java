/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;


/**
 * Controller for handling company systems-related endpoints in the API.
 * This controller is restricted to users with the ADMIN role.
 * Provides various endpoints to retrieve content system information
 * such as types, systems, specific system information, and project endpoints.
 * 
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/CompanySystemsController")
public class CompanySystemsController {

	@Autowired(required = false)
	List<IGContentManagementSystemHandler> handlers;
	@Autowired IGPersistentObjectManager persistentObjectManager;

	/**
	 * Default constructor for CompanySystemsController.
	 */
	public CompanySystemsController() {

	}

	/**
	 * Endpoint to retrieve all content management system types.
	 * 
	 * @return List of all available GContentManagementSystemType.
	 */
	@GetMapping(value = "getContentSystemTypes()", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GContentManagementSystemType> getContentSystemTypes() {
		List<GContentManagementSystemType> values = new ArrayList<GContentManagementSystemType>();
		// Iterate handlers to collect system types
		if (handlers != null) {
			for (IGContentManagementSystemHandler handler : handlers) {
				values.add(handler.getHandledSystemType());
			}
		}
		return values;
	}

	/**
	 * Endpoint to retrieve all content management systems.
	 * 
	 * @return List of all available GContentManagementSystem.
	 */
	@GetMapping(value = "getContentSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GContentManagementSystem> getContentSystems() {
		List<GContentManagementSystem> out = new ArrayList<GContentManagementSystem>();
		// Iterate handlers to collect system configurations
		if (handlers != null) {
			for (IGContentManagementSystemHandler handler : handlers) {
				out.addAll(handler.getConfigurations());
			}
		}
		return out;
	}

	/**
	 * Endpoint to retrieve a specific content management system type by its code.
	 * 
	 * @param systemTypeCode The code of the system type.
	 * @return The GContentManagementSystemType matching the given code, or null if not found.
	 */
	@GetMapping(value = "getContentSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getContentSystemType(@RequestParam("systemTypeCode") String systemTypeCode) {
		// Iterate handlers to find the matching system type
		if (handlers != null) {
			for (IGContentManagementSystemHandler handler : handlers) {
				if (systemTypeCode != null && handler.getHandledSystemType() != null
						&& systemTypeCode.equals(handler.getHandledSystemType().getCode())) {
					return handler.getHandledSystemType();
				}
			}
		}
		return null;
	}

	/**
	 * Endpoint to retrieve a specific content management system by its system type code and system code.
	 * 
	 * @param systemTypeCode The code of the system type.
	 * @param systemCode The code of the system.
	 * @return The GContentManagementSystem matching the given codes, or null if not found.
	 */
	@GetMapping(value = "getContentSystem", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystem getContentSystem(@RequestParam("systemTypeCode") String systemTypeCode,
			@RequestParam("systemCode") String systemCode) {
		// Iterate handlers and configurations to find the matching system
		if (handlers != null) {
			for (IGContentManagementSystemHandler handler : handlers) {
				if (systemTypeCode != null && handler.getHandledSystemType() != null
						&& systemTypeCode.equals(handler.getHandledSystemType().getCode())) {
					List<GContentManagementSystem> list = new ArrayList(handler.getConfigurations());
					for (GContentManagementSystem cfg : list) {
						if (cfg.getCode() != null && systemCode != null && cfg.getCode().equals(systemCode)) {
							return cfg;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Endpoint to retrieve a project endpoint by system type code, system code, and project endpoint code.
	 * 
	 * @param systemTypeCode The code of the system type.
	 * @param systemCode The code of the system.
	 * @param projectEndpointCode The code of the project endpoint.
	 * @return The GProjectEndpoint matching the given codes, or null if not found.
	 * @throws GeboContentHandlerSystemException if there is a system handling error.
	 */
	@GetMapping(value = "getProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE)
	public GProjectEndpoint getProjectEndpoint(@RequestParam("systemTypeCode") String systemTypeCode,
			@RequestParam("systemCode") String systemCode,
			@RequestParam("projectEndpointCode") String projectEndpointCode) throws GeboContentHandlerSystemException {
		// Iterate handlers to find the matching project endpoint
		if (handlers != null) {
			for (IGContentManagementSystemHandler handler : handlers) {
				if (systemTypeCode != null && handler.getHandledSystemType() != null
						&& systemTypeCode.equals(handler.getHandledSystemType().getCode())) {
					return handler.findProjectEndPoint(systemCode, projectEndpointCode);
				}
			}
		}
		return null;
	}

	/**
	 * Static inner class to encapsulate system information including system type, system, and endpoint.
	 */
	public static class SystemInfos {
		public GContentManagementSystemType systemType = null;
		public GContentManagementSystem system = null;
		public GProjectEndpoint endpoint = null;
	};

	/**
	 * Endpoint to retrieve project endpoint system information based on a reference to a GProjectEndpoint.
	 * 
	 * @param reference Reference to the GProjectEndpoint.
	 * @return SystemInfos containing system type, system, and endpoint data.
	 * @throws GeboPersistenceException if there is a persistence error.
	 * @throws GeboContentHandlerSystemException if there is a system handling error.
	 */
	@PostMapping(value = "getProjectEndpointSystemInfos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SystemInfos getProjectEndpointSystemInfos(@RequestBody GObjectRef<GProjectEndpoint> reference) throws GeboPersistenceException, GeboContentHandlerSystemException {
		SystemInfos info = new SystemInfos();
		
		// Retrieve endpoint from persistent storage
		info.endpoint = persistentObjectManager.findByReference(reference, GProjectEndpoint.class);
		
		// Find the matching system and system type using handlers
		if (handlers != null) {
			for (IGContentManagementSystemHandler handler : handlers) {
				if (handler.isManagedEndpoint(info.endpoint)) {
					info.system = handler.getSystem(info.endpoint);
					info.systemType = handler.getHandledSystemType();
					return info;
				}
			}
		}
		return null;
	}
	
}