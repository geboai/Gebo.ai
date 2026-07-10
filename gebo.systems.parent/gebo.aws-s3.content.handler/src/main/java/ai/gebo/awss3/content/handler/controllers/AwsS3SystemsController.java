/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint;
import ai.gebo.awss3.content.handler.GAwsS3System;
import ai.gebo.awss3.content.handler.IGAwsS3SystemContentHandler;
import ai.gebo.awss3.content.handler.impl.AwsS3TestService;
import ai.gebo.awss3.content.handler.repositories.AwsS3ProjectEndpointRepository;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboAwsConnectionCredentials;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/AwsS3SystemsController")
public class AwsS3SystemsController
		extends GAbstractSystemsArchitectureController<GAwsS3System, GAwsS3ProjectEndpoint> {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(AwsS3SystemsController.class);

	final IGAwsS3SystemContentHandler awsS3Handler;
	final AwsS3ProjectEndpointRepository endpointRepository;
	final AwsS3TestService testService;
	final IGeboSecretsAccessService secretAccessService;

	public AwsS3SystemsController(IGPersistentObjectManager persistentObjectManager,
			IGMessageBroker messageBroker, AwsS3SystemsNestedEmitter controllerEmitter,
			IGSecurityService securityService, IGAwsS3SystemContentHandler awsS3Handler,
			AwsS3ProjectEndpointRepository endpointRepository, IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			AwsS3TestService testService, IGeboSecretsAccessService secretAccessService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.awsS3Handler = awsS3Handler;
		this.endpointRepository = endpointRepository;
		this.testService = testService;
		this.secretAccessService = secretAccessService;
	}

	@GetMapping(value = "getAwsS3SystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getAwsS3SystemType() {
		return awsS3Handler.getHandledSystemType();
	}

	@GetMapping("getAwsS3Systems")
	public List<GAwsS3System> getAwsS3Systems() {
		return awsS3Handler.getConfigurations();
	}

	@GetMapping(value = "findAwsS3SystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAwsS3System findAwsS3SystemByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.persistentObjectManager.findById(GAwsS3System.class, code);
	}

	@GetMapping(value = "findAwsS3ProjectEndpointByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAwsS3ProjectEndpoint findAwsS3ProjectEndpointByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.persistentObjectManager.findById(GAwsS3ProjectEndpoint.class, code);
	}

	@PostMapping(value = "findAwsS3EndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GAwsS3ProjectEndpoint> findAwsS3EndpointsByQbe(
			@RequestBody GAwsS3ProjectEndpoint config) throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	@GetMapping("findAwsS3EndpointsByProject")
	public List<GAwsS3ProjectEndpoint> findAwsS3EndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	@PostMapping(value = "updateAwsS3System", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAwsS3System> updateAwsS3System(@RequestBody GAwsS3System object)
			throws GeboPersistenceException {
		OperationStatus<GAwsS3System> status = testService.test(object);
		if (!status.isHasErrorMessages()) {
			status.setResult(updateSystem(object));
		}
		return status;
	}

	@PostMapping(value = "insertAwsS3System", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAwsS3System> insertAwsS3System(@RequestBody GAwsS3System object)
			throws GeboPersistenceException {
		OperationStatus<GAwsS3System> status = testService.test(object);
		if (!status.isHasErrorMessages()) {
			status.setResult(insertSystem(object));
		}
		return status;
	}

	@PostMapping(value = "deleteAwsS3System", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAwsS3System(@RequestBody GAwsS3System object) throws GeboPersistenceException {
		this.deleteSystem(object);
	}

	@PostMapping(value = "updateAwsS3ProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GAwsS3ProjectEndpoint updateAwsS3ProjectEndpoint(
			@RequestBody GAwsS3ProjectEndpoint endpoint) throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "insertAwsS3ProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GAwsS3ProjectEndpoint insertAwsS3ProjectEndpoint(
			@RequestBody GAwsS3ProjectEndpoint endpoint) throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "deleteAwsS3ProjectEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAwsS3ProjectEndpoint(@RequestBody GAwsS3ProjectEndpoint endpoint)
			throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	/**
	 * Data transfer object for quickly setting up an AWS S3 content system with the
	 * minimal required information: a description, an optional custom S3-compatible
	 * endpoint and the AWS connection credentials (access key id, secret access key
	 * and region) that will be stored as an {@code AWS_CONNECTION} secret.
	 */
	public static class FastAwsS3SystemInsertRequest {
		@NotNull
		public String description = null;
		public String awsEndpoint = null;
		@NotNull
		public GeboAwsConnectionCredentials awsConnectionCredentials = null;
	}

	/**
	 * Provides a simplified way to configure an AWS S3 content system in one shot:
	 * it stores the supplied AWS connection credentials as an {@code AWS_CONNECTION}
	 * secret, builds the system referencing that secret, tests the connection and,
	 * when successful, persists it.
	 *
	 * @param data request containing the essential AWS S3 configuration
	 * @return operation status with the created system or error messages
	 */
	@PostMapping(value = "fastAwsS3Config", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAwsS3System> fastAwsS3Config(@Valid @RequestBody FastAwsS3SystemInsertRequest data) {
		try {
			String secretDescription = "AWS S3 " + data.description + " connection secret";
			String secretId = secretAccessService.storeSecret(data.awsConnectionCredentials, secretDescription,
					getAwsS3SystemType().getCode());
			GAwsS3System system = new GAwsS3System();
			system.setDescription(data.description);
			system.setAwsEndpoint(data.awsEndpoint);
			system.setS3SecretCode(secretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(getAwsS3SystemType().getCode());
			OperationStatus<GAwsS3System> status = testService.test(system);
			if (status.isHasErrorMessages()) {
				return status;
			}
			status.setResult(insertSystem(system));
			return status;
		} catch (Throwable th) {
			LOG.error("Error trying inserting AWS S3 system configuration", th);
			OperationStatus<GAwsS3System> os = new OperationStatus<GAwsS3System>();
			os.getMessages().add(GUserMessage.errorMessage("Cannot access AWS S3", ""));
			return os;
		}
	}
}