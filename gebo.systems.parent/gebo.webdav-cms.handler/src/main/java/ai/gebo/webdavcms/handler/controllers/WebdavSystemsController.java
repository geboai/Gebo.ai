package ai.gebo.webdavcms.handler.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.replicator.service.IEntityReplicationService;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;
import ai.gebo.webdavcms.handler.IGWebdavContentManagementSystemHandler;
import ai.gebo.webdavcms.handler.WebdavVersion;
import ai.gebo.webdavcms.handler.impl.WebdavContentManagementHandlerImpl;
import ai.gebo.webdavcms.handler.impl.WebdavSystemsTestService;
import ai.gebo.webdavcms.handler.repositories.WebdavProjectEndpointRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/WebdavSystemsController")
public class WebdavSystemsController
		extends GAbstractSystemsArchitectureController<GWebdavContentManagementSystem, GWebdavProjectEndpoint> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebdavSystemsController.class);

	@Component
	@Scope("singleton")
	public static class WebdavControllerEmitter extends ControllerNestedEmitter {

		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.WEBDAB_CMS_MODULE;
		}
	}

	private final IGWebdavContentManagementSystemHandler handler;
	private final WebdavProjectEndpointRepository endpointRepository;
	private final IGeboSecretsAccessService secretAccessService;
	private final WebdavSystemsTestService webdavTestService;

	public WebdavSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			WebdavControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGWebdavContentManagementSystemHandler handler, WebdavProjectEndpointRepository endpointRepository,
			IGeboSecretsAccessService secretAccessService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			WebdavSystemsTestService webdavTestService, IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService,
				entityProcessingRunnableFactory, jobQueueService, replicationService);
		this.handler = handler;
		this.endpointRepository = endpointRepository;
		this.secretAccessService = secretAccessService;
		this.webdavTestService = webdavTestService;
	}

	@GetMapping(value = "getWebdavSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getWebdavSystemTypes() {
		return handler.getHandledSystemType();
	}

	@GetMapping("getWebdavSystems")
	public List<GWebdavContentManagementSystem> getWebdavSystems() {
		return handler.getConfigurations();
	}

	@PostMapping(value = "findWebdavEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GWebdavProjectEndpoint> findWebdavEndpointsByQbe(@RequestBody GWebdavProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	@GetMapping("findWebdavEndpointsByProject")
	public List<GWebdavProjectEndpoint> findWebdavEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	@GetMapping("findWebdavEndpointsByCode")
	public GWebdavProjectEndpoint findWebdavEndpointsByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(GWebdavProjectEndpoint.class, code);
	}

	@GetMapping("findWebdavSystemByCode")
	public GWebdavContentManagementSystem findWebdavSystemByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(GWebdavContentManagementSystem.class, code);
	}

	@PostMapping(value = "updateWebdavSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GWebdavContentManagementSystem> updateWebdavSystem(
			@RequestBody GWebdavContentManagementSystem object) throws GeboPersistenceException {
		OperationStatus<GWebdavContentManagementSystem> os = webdavTestService.testWebdavSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}
		return OperationStatus.of(updateSystem(object));
	}

	@PostMapping(value = "insertWebdavSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GWebdavContentManagementSystem> insertWebdavSystem(
			@RequestBody GWebdavContentManagementSystem object) throws GeboPersistenceException {
		OperationStatus<GWebdavContentManagementSystem> os = webdavTestService.testWebdavSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}
		return OperationStatus.of(insertSystem(object));
	}

	@PostMapping(value = "testWebdavSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GWebdavContentManagementSystem> testWebdavSystem(
			@RequestBody GWebdavContentManagementSystem object) throws GeboPersistenceException {
		return webdavTestService.testWebdavSystem(object);
	}

	@PostMapping(value = "deleteWebdavSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteWebdavSystem(@RequestBody GWebdavContentManagementSystem object)
			throws GeboPersistenceException {
		this.deleteSystem(object);
	}

	@PostMapping(value = "updateWebdavEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GWebdavProjectEndpoint updateWebdavEndpoint(@RequestBody GWebdavProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "insertWebdavEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GWebdavProjectEndpoint insertWebdavEndpoint(@RequestBody GWebdavProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "deleteWebdavEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteWebdavEndpoint(@RequestBody GWebdavProjectEndpoint endpoint)
			throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	@PostMapping(value = "publishWebdavEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishWebdavEndpoint(@RequestBody GWebdavProjectEndpoint endpoint) {
		return publish(endpoint);
	}

	public static class FastWebdavSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public WebdavVersion authType = null;
		public String username = null;
		public String password = null;
		public String token = null;
	}

	@PostMapping(value = "fastWebdavConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GWebdavContentManagementSystem> fastWebdavConfig(
			@Valid @RequestBody FastWebdavSystemInsertRequest data) {
		try {
			AbstractGeboSecretContent secret = null;
			GWebdavContentManagementSystem system = new GWebdavContentManagementSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setWebdavAuthType(data.authType);

			switch (data.authType) {
			case BASIC:
			case DIGEST:
			case NTLM: {
				GeboUsernamePasswordContent up = new GeboUsernamePasswordContent();
				up.setUsername(data.username);
				up.setPassword(data.password);
				secret = up;
			}
				break;
			case BEARER_TOKEN: {
				GeboTokenContent t = new GeboTokenContent();
				t.setUser(data.username);
				t.setToken(data.token);
				secret = t;
			}
				break;
			default:
				break;
			}

			if (secret != null) {
				String secretDescription = data.authType.name() + " WebDAV system secret";
				String secretId = secretAccessService.storeSecret(secret, secretDescription,
						WebdavContentManagementHandlerImpl.WEBDAB_CMS);
				system.setSecretCode(secretId);
			}

			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(WebdavContentManagementHandlerImpl.WEBDAB_CMS);

			OperationStatus<GWebdavContentManagementSystem> status = webdavTestService.testWebdavSystem(system);
			if (status.isHasErrorMessages()) {
				return status;
			}
			system = persistentObjectManager.insert(system);
			return OperationStatus.of(system);
		} catch (Throwable th) {
			LOGGER.error("Error trying inserting WebDAV system configuration", th);
			OperationStatus<GWebdavContentManagementSystem> os = new OperationStatus<GWebdavContentManagementSystem>();
			os.getMessages().add(GUserMessage.errorMessage("Cannot access WebDAV", ""));
			return os;
		}
	}
}