/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.integration.tests;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.FileCopyUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.tests.TestChatModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.tests.TestEmbeddingModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.config.GeboAISecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * AI generated comments Abstract base class to configure integration tests for
 * the Gebo project using MongoDB. This class sets up necessary configurations,
 * provides utility methods for test data management, and integrates external
 * services needed for the tests.
 */
@AutoConfigureDataMongo
public class AbstractGeboMonolithicIntegrationTests {

	/** Manager for handling persistent objects. */
	@Autowired
	protected IGPersistentObjectManager persistentObjectManager;
	/** DAO for chat model runtime configurations. */
	@Autowired
	protected IGChatModelRuntimeConfigurationDao chatModelRuntimeDao;
	/** DAO for embedding model runtime configurations. */
	@Autowired
	protected IGEmbeddingModelRuntimeConfigurationDao embeddingModelRuntimeDao;
	/** Service for test chat model support. */
	@Autowired
	protected TestChatModelSupportServiceImpl testChatModelSupportService;
	/** Service for test embedding model support. */
	@Autowired
	protected TestEmbeddingModelSupportServiceImpl testEmbeddingModelSupportService;
	/** Service to handle ingestion job queue. */
	@Autowired
	protected IGGeboIngestionJobQueueService ingestionJobService;
	/** Factory for creating document references. */
	@Autowired
	protected IGDocumentReferenceFactory docreferenceFactory;
	/** Service for discovering local persistent folders. */
	@Autowired
	protected IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService;
	/** Repository for handling job statuses. */
	@Autowired
	protected JobStatusRepository statusRepo;
	/** Service for accessing secrets. */
	@Autowired
	protected IGeboSecretsAccessService secretsAccessService;
	/** List of all MongoDB repositories used. */
	@Autowired
	protected List<IGBaseMongoDBRepository> allRepositories;
	/** Repository for vectorized content. */
	@Autowired
	protected VectorizedContentRepository vectorizedContentRepository;
	/** Logger for logging information. */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/** Path for home directory used in tests. */
	protected static Path HOME;
	/** Path for work directory used in tests. */
	protected static Path WORK;
	// User repository to store fungible test users
	@Autowired
	protected UserRepository userRepository;

	// Static initialization block to set up temporary directories and environment
	// properties
	static {
		try {
			Path temp = Files.createTempDirectory("GEBO-TESTS");
			HOME = Path.of(temp.toString(), "home");
			WORK = Path.of(temp.toString(), "work");
			Files.createDirectories(HOME);
			Files.createDirectories(WORK);

			System.out.println("Tests using folder:" + temp.toString());
			System.setProperty(EnvironmentHolder.GEBO_HOME, HOME.toAbsolutePath().toString());
			System.setProperty(EnvironmentHolder.GEBO_WORK_DIRECTORY, WORK.toAbsolutePath().toString());
		} catch (IOException e) {
			Path temp = Paths.get("target");
			HOME = Path.of(temp.toString(), "home");
			WORK = Path.of(temp.toString(), "work");
			try {
				Files.createDirectories(HOME);

			} catch (IOException e1) {

			}
			try {

				Files.createDirectories(WORK);
			} catch (IOException e1) {

			}
			System.setProperty(EnvironmentHolder.GEBO_HOME, HOME.toAbsolutePath().toString());
			System.setProperty(EnvironmentHolder.GEBO_WORK_DIRECTORY, WORK.toAbsolutePath().toString());
		}
	}

	/** Container to manage a MongoDB instance for integration tests. */
	@Container
	protected static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

	/** ObjectMapper instance for JSON operations. */
	protected static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Configures the dynamic properties for the test containers.
	 * 
	 * @param registry the registry to update with container properties.
	 */
	@DynamicPropertySource
	public static void containersProperties(DynamicPropertyRegistry registry) {
		mongoDBContainer.start();
		registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
		registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
	}

	/**
	 * Cleans up all databases by deleting all records from repositories.
	 */
	protected void cleanAllDb() {
		LOGGER.info("Resetting content of:" + GVectorizedContent.class.getName() + " (nr:"
				+ vectorizedContentRepository.count() + ")");
		vectorizedContentRepository.deleteAll();
		for (IGBaseMongoDBRepository repo : allRepositories) {
			LOGGER.info("Resetting content of:" + repo.getManagedType().getName() + " (nr:" + repo.count() + ")");
			repo.deleteAll();
		}
	}

	/**
	 * Creates and persists a new project endpoint.
	 * 
	 * @param <T>         the type of the project endpoint.
	 * @param description the description of the project.
	 * @param type        the class type of the project endpoint.
	 * @return the persisted project endpoint.
	 * @throws GeboPersistenceException on persistence errors.
	 * @throws InstantiationException   if the endpoint cannot be instantiated.
	 * @throws IllegalAccessException   if the endpoint cannot be accessed.
	 */
	protected <T extends GProjectEndpoint> T createAndPersist(String description, Class<T> type)
			throws GeboPersistenceException, InstantiationException, IllegalAccessException {
		GKnowledgeBase testKB = new GKnowledgeBase();
		testKB.setDescription(description + " KB");
		testKB = persistentObjectManager.insert(testKB);
		GProject testPj = new GProject();
		testPj.setRootKnowledgeBaseCode(testKB.getCode());
		testPj.setDescription(description + " pj");
		testPj = persistentObjectManager.insert(testPj);
		T endpoint = type.newInstance();
		endpoint.setDescription(description);
		endpoint.setParentProjectCode(testPj.getCode());
		return persistentObjectManager.insert(endpoint);
	}

	protected static final List<String> ALL_ROLES = List.of(GeboAISecurityConfig.ADMIN_ROLE,
			GeboAISecurityConfig.USER_ROLE);

	protected static final String DEFAULT_ALL_ROLES_USER = "testuser@gebo.ai";

	protected void createDefaultUser() {
		this.createUser(DEFAULT_ALL_ROLES_USER, ALL_ROLES);
	}

	protected User getDefaultUser() {
		return getUser(DEFAULT_ALL_ROLES_USER);
	}

	protected UserInfos getDefaultUserInfos() {

		return this.getUserInfos(DEFAULT_ALL_ROLES_USER);
	}

	protected UserInfos getUserInfos(String email) {
		return UserInfos.of(getUser(email));
	}

	protected User createUser(String email, List<String> roles) {
		User user = new User();
		user.setUsername(email);
		// Encrypting the user's password
		user.setPassword("NOPASSWORD");
		user.setEmailVerified(true);
		user.setProvider(AuthProvider.local);
		user.setProviderId("local-jwt");
		user.setRoles(roles != null && !roles.isEmpty() ? roles : ALL_ROLES);
		userRepository.insert(user);
		return user;
	}

	protected User getUser(String email) {
		Optional<User> userData = userRepository.findById(email);
		return userData.isPresent() ? userData.get() : null;
	}

	/**
	 * Loads data from a JSON file and persists it as a project endpoint.
	 * 
	 * @param <T>             the type of the project endpoint.
	 * @param bundledJsonPath path to the bundled JSON file.
	 * @param description     description to be given.
	 * @param type            class type of the project endpoint.
	 * @param initializer     a consumer to initialize the newly persisted endpoint.
	 * @return the persisted endpoint.
	 * @throws IOException              on IO errors.
	 * @throws GeboPersistenceException on persistence errors.
	 * @throws InstantiationException   if the endpoint cannot be instantiated.
	 * @throws IllegalAccessException   if access is not allowed.
	 * @throws StreamReadException      on JSON parsing errors.
	 * @throws DatabindException        on errors in data binding.
	 */
	protected <T extends GProjectEndpoint> T loadFromJsonAndPersist(String bundledJsonPath, String description,
			Class<T> type, Consumer<T> initializer) throws GeboPersistenceException, InstantiationException,
			IllegalAccessException, StreamReadException, DatabindException, IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(bundledJsonPath);
		if (is == null)
			throw new RuntimeException("Cannot load bundled json:" + bundledJsonPath);
		GKnowledgeBase testKB = new GKnowledgeBase();
		testKB.setDescription(description + " KB");
		testKB = persistentObjectManager.insert(testKB);
		GProject testPj = new GProject();
		testPj.setRootKnowledgeBaseCode(testKB.getCode());
		testPj.setDescription(description + " pj");
		testPj = persistentObjectManager.insert(testPj);
		ObjectMapper mapper = new ObjectMapper();
		T data = mapper.readValue(is, type);
		data = persistentObjectManager.insert(data);
		if (initializer != null)
			initializer.accept(data);
		return data;
	}

	/**
	 * Retrieves the project associated with a given endpoint.
	 * 
	 * @param <T>      the type of the project endpoint.
	 * @param endpoint the endpoint whose project is needed.
	 * @return the associated project.
	 * @throws GeboPersistenceException on retrieval errors.
	 */
	protected <T extends GProjectEndpoint> GProject getProject(T endpoint) throws GeboPersistenceException {
		return persistentObjectManager.findById(GProject.class, endpoint.getParentProjectCode());
	}

	/**
	 * Cleans up the persistent data for a given endpoint.
	 * 
	 * @param <T>      the type of the project endpoint.
	 * @param endpoint the endpoint to clean.
	 * @throws GeboPersistenceException on persistence errors.
	 */
	protected <T extends GProjectEndpoint> void cleanPersistent(T endpoint) throws GeboPersistenceException {
		GProject pj = getProject(endpoint);
		GKnowledgeBase kb = persistentObjectManager.findById(GKnowledgeBase.class, pj.getRootKnowledgeBaseCode());
		if (kb != null) {
			persistentObjectManager.delete(kb);
		}
		persistentObjectManager.delete(pj);
		persistentObjectManager.delete(endpoint);
	}

	/**
	 * Copies a resource from the classpath to a specified base folder.
	 * 
	 * @param classPathElement path of the resource in the classpath.
	 * @param baseFolder       base folder to copy the resource to.
	 * @return path to the copied file.
	 * @throws IOException on IO errors during copying.
	 */
	protected Path copyResource(String classPathElement, String baseFolder) throws IOException {

		InputStream is = getClass().getClassLoader().getResourceAsStream(classPathElement);
		if (is == null)
			throw new RuntimeException("The resource:" + classPathElement + " does not exist");
		Path file = Path.of(baseFolder, classPathElement);
		try {
			Files.createDirectories(file.getParent());
		} catch (IOException e) {
		}
		LOGGER.info("Copying => " + classPathElement + " to " + file.toAbsolutePath().toString());
		FileOutputStream fos = new FileOutputStream(file.toFile());
		FileCopyUtils.copy(is, fos);
		fos.close();
		return file;
	}

	/**
	 * Extracts a resource from the classpath to a temporary file.
	 * 
	 * @param classPathElement path of the resource in the classpath.
	 * @param postfix          postfix to append to the temp file name.
	 * @return path to the temporary file created.
	 * @throws IOException on IO errors during file creation.
	 */
	protected Path extractResource(String classPathElement, String postfix) throws IOException {
		Path file = Files.createTempFile("gebo", postfix);
		InputStream is = getClass().getClassLoader().getResourceAsStream(classPathElement);
		if (is == null)
			throw new RuntimeException("The resource:" + classPathElement + " does not exist");
		FileOutputStream fos = new FileOutputStream(file.toFile());
		FileCopyUtils.copy(is, fos);
		fos.close();
		return file;
	}

	/**
	 * Registers a security token in the secret service.
	 * 
	 * @param token       the token to register.
	 * @param user        the user associated with the token.
	 * @param description description of the token.
	 * @param contextCode context under which the token is used.
	 * @return the secret ID of the stored token.
	 * @throws GeboCryptSecretException on errors in storing the token.
	 */
	protected String registerSecurityToken(String token, String user, String description, String contextCode)
			throws GeboCryptSecretException {
		GeboTokenContent securityAccount = new GeboTokenContent();
		securityAccount.setToken(token);
		securityAccount.setUser(user);
		String secretId = secretsAccessService.storeSecret(securityAccount, description, contextCode);
		return secretId;
	}

	/**
	 * Displays a user message in the log based on the message severity.
	 * 
	 * @param message the message to show.
	 */
	protected void showMessage(GUserMessage message) {
		switch (message.getSeverity()) {
		case warn: {
			LOGGER.warn(message.getSummary() + "-" + message.getDetail());
		}
			break;
		case error: {
			LOGGER.error(message.getSummary() + "-" + message.getDetail());
		}
			break;
		case info: {
			LOGGER.info(message.getSummary() + "-" + message.getDetail());
		}
			break;
		case success: {
			LOGGER.info(message.getSummary() + "-" + message.getDetail());
		}
			break;
		}
	}

	/**
	 * Displays a list of user messages.
	 * 
	 * @param msg list of messages to show.
	 */
	protected void showMessages(List<GUserMessage> msg) {
		for (GUserMessage gUserMessage : msg) {
			showMessage(gUserMessage);
		}
	}

	/**
	 * Displays messages from an operation status instance.
	 * 
	 * @param <T>    the type of the operation result.
	 * @param status status containing messages to display.
	 */
	protected <T> void showMessages(OperationStatus<T> status) {
		showMessages(status.getMessages());
	}
}