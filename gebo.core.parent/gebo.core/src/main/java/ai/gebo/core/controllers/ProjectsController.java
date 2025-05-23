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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.impl.GCoreMessagesEmitterImpl;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Controller for managing project-related operations for an admin.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/ProjectsController")
public class ProjectsController {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	IGPersistentObjectManager persistenceManager;

	@Autowired
	DocumentReferenceRepository docRepository;

	@Autowired
	VirtualFolderRepository vfolderRepository;

	@Autowired 
	GCoreMessagesEmitterImpl coreEmitter;

	/**
	 * Default constructor for ProjectsController.
	 */
	public ProjectsController() {

	}

	/**
	 * Inner class representing a filter for project research.
	 */
	public static class ProjectsResearchFilter {
		private String rootKnowledgeBaseCode = null;
		private String parentProjectCode = null;

		/**
		 * Gets the root knowledge base code.
		 * 
		 * @return the root knowledge base code
		 */
		public String getRootKnowledgeBaseCode() {
			return rootKnowledgeBaseCode;
		}

		/**
		 * Sets the root knowledge base code.
		 * 
		 * @param rootKnowledgeBaseCode the root knowledge base code to set
		 */
		public void setRootKnowledgeBaseCode(String rootKnowledgeBaseCode) {
			this.rootKnowledgeBaseCode = rootKnowledgeBaseCode;
		}

		/**
		 * Gets the parent project code.
		 * 
		 * @return the parent project code
		 */
		public String getParentProjectCode() {
			return parentProjectCode;
		}

		/**
		 * Sets the parent project code.
		 * 
		 * @param parentProjectCode the parent project code to set
		 */
		public void setParentProjectCode(String parentProjectCode) {
			this.parentProjectCode = parentProjectCode;
		}
	}

	/**
	 * Retrieves all projects.
	 * 
	 * @return a list of all projects
	 */
	@GetMapping(value = "getProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GProject> getProjects() {
		return projectRepository.findAll();
	}

	/**
	 * Finds a project by its code.
	 * 
	 * @param code the code of the project
	 * @return the project with the given code or null if not found
	 */
	@GetMapping(value = "findProjectByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GProject findProjectByCode(@RequestParam("code") String code) {
		Optional<GProject> returned = projectRepository.findById(code);
		return returned.isPresent() ? returned.get() : null;
	}

	/**
	 * Finds root projects with the specified knowledge base code.
	 * 
	 * @param knowledgeBaseCode the knowledge base code
	 * @return a list of root projects
	 */
	@GetMapping(value = "findRootProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GProject> findRootProjects(@RequestParam("knowledgeBaseCode") String knowledgeBaseCode) {
		List<GProject> out = new ArrayList<GProject>();
		GProject example = new GProject();
		example.setRootKnowledgeBaseCode(knowledgeBaseCode);
		List<GProject> allChilds = this.projectRepository.findAll(Example.of(example));
		if (allChilds != null) {
			for (GProject child : allChilds) {
				if (child.getParentProjectCode() == null) {
					out.add(child);
				}
			}
		}
		return out;
	}

	/**
	 * Finds child projects with the specified knowledge base code and parent project code.
	 * 
	 * @param knowledgeBaseCode the knowledge base code
	 * @param parentProjectCode the parent project code
	 * @return a list of child projects
	 */
	@GetMapping(value = "findChildProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GProject> findChildProjects(@RequestParam("knowledgeBaseCode") String knowledgeBaseCode,
			@RequestParam("parentProjectCode") String parentProjectCode) {
		GProject example = new GProject();
		example.setRootKnowledgeBaseCode(knowledgeBaseCode);
		example.setParentProjectCode(parentProjectCode);
		return this.projectRepository.findAll(Example.of(example));
	}

	/**
	 * Finds projects from other knowledge bases that can be included.
	 * 
	 * @param knowledgeBaseCode the current knowledge base code
	 * @param actualSelectedProjects the list of already selected projects' codes
	 * @return a list of includable projects
	 */
	@GetMapping(value = "findOtherKnowledgeBaseIncludableProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GProject> findOtherKnowledgeBaseIncludableProjects(
			@RequestParam("knowledgeBaseCode") String knowledgeBaseCode,
			@RequestParam("actualSelectedProjects") List<String> actualSelectedProjects) {
		List<GProject> out = new ArrayList<GProject>();
		List<GProject> allProjects = this.projectRepository.findAll();
		for (GProject project : allProjects) {
			if (project.getRootKnowledgeBaseCode() != null
					&& !project.getRootKnowledgeBaseCode().equals(knowledgeBaseCode)
					&& !actualSelectedProjects.contains(project.getCode())) {
				out.add(project);
			}
		}
		return out;
	}

	/**
	 * Searches projects based on the provided filter.
	 * 
	 * @param filter the filter criteria for the projects
	 * @return the list of projects that match the filter
	 */
	@PostMapping(value = "searchProjects", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GProject> searchProjects(@RequestBody ProjectsResearchFilter filter) {
		GProject example = new GProject();
		example.setParentProjectCode(filter.parentProjectCode);
		example.setRootKnowledgeBaseCode(filter.rootKnowledgeBaseCode);
		return searchProjectsByQbe(example);
	}

	/**
	 * Searches projects using Query By Example (QBE) technique.
	 * 
	 * @param example an example project used as the search criteria
	 * @return a list of projects matching the example
	 */
	@PostMapping(value = "searchProjectsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GProject> searchProjectsByQbe(@RequestBody GProject example) {
		return projectRepository.findAll(Example.of(example));
	}

	/**
	 * Updates a project entity.
	 * 
	 * @param entity the project entity to be updated
	 * @return the updated project entity
	 * @throws GeboPersistenceException if an error occurs during update
	 */
	@PostMapping(value = "updateProject", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GProject updateProject(@RequestBody GProject entity) throws GeboPersistenceException {
		if (entity.getObjectSpaceType()==null) {
			entity.setObjectSpaceType(ObjectSpaceType.COMPANY);
		}
		return persistenceManager.update(entity);
	}

	/**
	 * Inserts a new project entity.
	 * 
	 * @param entity the project entity to be inserted
	 * @return the inserted project entity
	 * @throws GeboPersistenceException if an error occurs during insertion
	 */
	@PostMapping(value = "insertProject", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GProject insertProject(@RequestBody GProject entity) throws GeboPersistenceException {
		if (entity.getObjectSpaceType()==null) {
			entity.setObjectSpaceType(ObjectSpaceType.COMPANY);
		}
		return persistenceManager.insert(entity);
	}

	/**
	 * Deletes a project entity.
	 * 
	 * @param entity the project entity to be deleted
	 * @throws GeboPersistenceException if an error occurs during deletion
	 */
	@PostMapping(value = "deleteProject", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteProject(@RequestBody GProject entity) throws GeboPersistenceException {
		persistenceManager.delete(entity);
		GDeletedProjectPayload payload=new GDeletedProjectPayload();
		// Set the project for the deletion payload
		payload.setProject(entity);
		// Emit the deleting payload
		coreEmitter.sendDeletingPayload(payload);
	}

	/**
	 * Class containing information about a virtual path.
	 */
	public static class VPathInfo {
		public String code = null;
		public String name = null;
		public String parentFolderCode = null;
		public GObjectRef<GProjectEndpoint> endpointRef = null;

		/**
		 * Default constructor.
		 */
		public VPathInfo() {
		}

		/**
		 * Constructor initializing fields using a virtual filesystem object.
		 * 
		 * @param o an abstract virtual filesystem object
		 */
		public VPathInfo(GAbstractVirtualFilesystemObject o) {
			this.code = o.getCode();
			this.endpointRef = o.getProjectEndpointReference();
			this.name = o.getName();
			this.parentFolderCode = o.getParentVirtualFolderCode();
		}
	}

	/**
	 * Class representing folder information in the virtual path.
	 */
	public static class VFolderInfo extends VPathInfo {
		/**
		 * Default constructor.
		 */
		public VFolderInfo() {
		}

		/**
		 * Constructor using a virtual folder object.
		 * 
		 * @param f a virtual folder object
		 */
		public VFolderInfo(GVirtualFolder f) {
			super(f);
		}
	}

	/**
	 * Class representing document information in the virtual path.
	 */
	public static class VDocumentInfo extends VPathInfo {
		public Date creationDate;
		public Date modificationDate;
		public String extension;

		/**
		 * Default constructor.
		 */
		public VDocumentInfo() {
		}

		/**
		 * Constructor using a document reference object.
		 * 
		 * @param r a document reference object
		 */
		public VDocumentInfo(GDocumentReference r) {
			super(r);
			this.contentType = r.getContentType();
			this.creationDate = r.getCreationDate();
			this.modificationDate = r.getModificationDate();
			this.extension = r.getExtension();
		}

		public String contentType = null;
	}

	/**
	 * Retrieves root folders for the project endpoint.
	 * 
	 * @param endpointRef a reference to the project endpoint
	 * @return a list of root folder information
	 */
	@PostMapping(value = "getRootFolders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<VFolderInfo> getRootFolders(@NotNull @RequestBody GObjectRef<GProjectEndpoint> endpointRef) {
		return this.vfolderRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCodeIsNull(
						endpointRef.getClassName(), endpointRef.getCode())
				.map(x -> {
					return new VFolderInfo(x);
				}).toList();
	}

	/**
	 * Retrieves root documents for the project endpoint.
	 * 
	 * @param endpointRef a reference to the project endpoint
	 * @return a list of root document information
	 */
	@PostMapping(value = "getRootDocuments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<VDocumentInfo> getRootDocuments(@NotNull @RequestBody GObjectRef<GProjectEndpoint> endpointRef) {
		return this.docRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCodeIsNull(
						endpointRef.getClassName(), endpointRef.getCode())
				.map(x -> {
					return new VDocumentInfo(x);
				}).toList();
	}

	/**
	 * Class representing parameters for fetching child virtual filesystem structures.
	 */
	public static class ChildVirtualFSParam {

		@NotNull
		public GObjectRef<GProjectEndpoint> endpointRef = null;

		@NotNull
		public VFolderInfo folder = null;

	}

	/**
	 * Retrieves child folders within a specified folder and project endpoint.
	 * 
	 * @param dir parameters specifying the directory to search within
	 * @return a list of child folder information
	 */
	@PostMapping(value = "getChildFolders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<VFolderInfo> getChildFolders(@NotNull @RequestBody ChildVirtualFSParam dir) {
		return this.vfolderRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCode(
						dir.endpointRef.getClassName(), dir.endpointRef.getCode(), dir.folder.code)
				.map(x -> {
					return new VFolderInfo(x);
				}).toList();
	}

	/**
	 * Retrieves child documents within a specified folder and project endpoint.
	 * 
	 * @param dir parameters specifying the directory to search within
	 * @return a list of child document information
	 */
	@PostMapping(value = "getChildDocuments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<VDocumentInfo> getChildDocuments(@NotNull @RequestBody ChildVirtualFSParam dir) {
		return this.docRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCode(
						dir.endpointRef.getClassName(), dir.endpointRef.getCode(), dir.folder.code)
				.map(x -> {
					return new VDocumentInfo(x);
				}).toList();
	}
}