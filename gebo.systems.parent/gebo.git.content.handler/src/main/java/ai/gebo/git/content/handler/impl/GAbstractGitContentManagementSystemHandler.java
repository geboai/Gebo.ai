/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.IGBaseGitContentManagementSystemHandler;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.model.GUserMessage;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * AI generated comments
 * 
 * Abstract class for Git content management system handlers that provides implementation
 * for interacting with Git repositories through JGit.
 * 
 * @param <SystemIntegrationConfigType> Type of Git content management system configuration
 * @param <EndpointConfigType> Type of Git project endpoint configuration
 */
public abstract class GAbstractGitContentManagementSystemHandler<SystemIntegrationConfigType extends GGitContentManagementSystem, EndpointConfigType extends GGitProjectEndpoint>
		extends GAbstractContentManagementSystemHandler<SystemIntegrationConfigType, EndpointConfigType>
		implements IGBaseGitContentManagementSystemHandler<SystemIntegrationConfigType, EndpointConfigType> {
	/** Service for accessing secrets, primarily for repository credentials */
	IGeboSecretsAccessService secretsAccessService = null;

	/**
	 * Constructor for the Git content management system handler.
	 * 
	 * @param buildSystemHandlerRepository Repository pattern for build system handlers
	 * @param contentHandler Factory for document references
	 * @param configurationsDao DAO for content management system configurations
	 * @param endpointsDao DAO for project endpoint runtime configurations
	 * @param secretsAccessService Service for accessing secrets
	 * @param localPersistentFolderDiscoveryService Service for discovering local persistent folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Broker for messages
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public GAbstractGitContentManagementSystemHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,
			IGContentManagementSystemConfigurationDao<SystemIntegrationConfigType> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<EndpointConfigType> endpointsDao,
			IGeboSecretsAccessService secretsAccessService,
			IGLocalPersistentFolderDiscoveryService localPersistentFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localPersistentFolderDiscoveryService, persistentObjectManager, messageBroker, ingestionHandler);
		this.secretsAccessService = secretsAccessService;

	}

	/**
	 * Implements the content consumption process for Git repositories.
	 * Clones or pulls the repository based on whether it already exists locally,
	 * and then processes its contents.
	 * 
	 * @param contentManagementConfig Configuration for the content management system
	 * @param buildSystems List of build systems
	 * @param endpoint Endpoint configuration for the Git repository
	 * @param consumer Consumer for the repository content
	 * @param messagesConsumer Consumer for user messages
	 * @param errorConsumer Consumer for content access errors
	 * @throws GeboContentHandlerSystemException If there's an error in handling the content
	 */
	@Override
	protected void consumeImplementation(SystemIntegrationConfigType contentManagementConfig,
			List<GBuildSystem> buildSystems, EndpointConfigType endpoint, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extract(rootItem,contentManagementConfig,buildSystems,endpoint)");
		}

		File folder2analyze = null;
		Git git = null;

		try {
			GVirtualFolder rootItem = createRootItem(contentManagementConfig, endpoint);
			consumer.accept(rootItem);
			String identityCode = null;
			boolean publicAccess = contentManagementConfig.getPublicAccess() != null
					&& contentManagementConfig.getPublicAccess();
			if (!publicAccess) {
				identityCode = contentManagementConfig.getDefaultIdentityCode();
			}
			if (endpoint.getPublicAccess() != null && endpoint.getPublicAccess()) {
				identityCode = null;
				publicAccess = true;
			} else {
				if (endpoint.getIdentityCode() != null) {
					identityCode = endpoint.getIdentityCode();
				}
				publicAccess = false;
			}
			AbstractGeboSecretContent identity = null;
			if (!publicAccess && identityCode != null) {
				identity = this.secretsAccessService.getSecretContentById(identityCode);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remote repository handling: " + endpoint.getRepositoryUri());
			}
			String localFolder = localFolderDiscoveryService.getLocalPersistentFolder(contentManagementConfig,
					endpoint);

			folder2analyze = new File(localFolder);
			if (!folder2analyze.exists()) {
				folder2analyze.mkdirs();
			}
			// check if it has .git file
			File[] allFiles = folder2analyze.listFiles();
			boolean hasDotGit = false;
			if (allFiles != null) {
				for (File file : allFiles) {
					hasDotGit = hasDotGit || file.getName().equalsIgnoreCase(".git");
				}
			}
			if (hasDotGit) {
				messagesConsumer.accept(GUserMessage.infoMessage("Try to pull existing local git repo ",
						"Try to pull already existing local git repo  with remote address:"
								+ endpoint.getRepositoryUri()));
				git = this.pullExistingRepo(endpoint, folder2analyze, publicAccess, identity);
				messagesConsumer.accept(GUserMessage.successMessage("Pulled existing local git repo with success ",
						"Pulled existing local git repo with success with remote address:"
								+ endpoint.getRepositoryUri()));
			} else {
				messagesConsumer.accept(GUserMessage.infoMessage("Try to clone in local git repo ",
						"Try to clone in local git repo with remote address:" + endpoint.getRepositoryUri()));
				git = this.cloneRepository(endpoint, folder2analyze, publicAccess, identity);
				messagesConsumer.accept(GUserMessage.successMessage("Cloned git repo with success ",
						"Cloned git repo with success with remote address:" + endpoint.getRepositoryUri()));
			}

			if (folder2analyze.exists() && folder2analyze.isDirectory()) {
				consumeGitMetaInformations(git, consumer);
				super.consume(rootItem, contentManagementConfig, buildSystems, endpoint,
						Path.of(folder2analyze.getAbsolutePath()), (Path) -> {
							return !(Files.exists(Path, LinkOption.NOFOLLOW_LINKS)
									&& Path.getFileName().toString().equalsIgnoreCase(".git"));
						}, consumer, messagesConsumer, errorConsumer);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End extract(rootItem,contentManagementConfig,buildSystems,endpoint)");
				}

			} else {
				throw new GeboContentHandlerSystemException(
						"The repository " + endpoint.getRepositoryUri() + " does not exist or is not a directory");
			}

		} catch (Throwable th) {
			LOGGER.error("Exception in extract(..)", th);
			errorConsumer.accept(ContentsAccessError.of(th, ContentsAccessedObjectType.FOLDER, getId()));
			throw new GeboContentHandlerSystemException("exception in extract(.....)", th);
		} finally {

		}

	}

	/**
	 * Pulls updates from the remote repository into the existing local repository.
	 * 
	 * @param endpoint Endpoint configuration for the Git repository
	 * @param folder2analyze Local folder containing the Git repository
	 * @param publicAccess Whether the repository is publicly accessible
	 * @param secret Secret content for authentication if not public
	 * @return Git object representing the repository
	 * @throws IOException If there's an I/O error
	 * @throws WrongRepositoryStateException If the repository state is invalid for the operation
	 * @throws InvalidConfigurationException If there's an invalid configuration
	 * @throws InvalidRemoteException If the remote is invalid
	 * @throws CanceledException If the operation is canceled
	 * @throws RefNotFoundException If a reference is not found
	 * @throws RefNotAdvertisedException If a reference is not advertised
	 * @throws NoHeadException If there's no HEAD reference
	 * @throws TransportException If there's a transport error
	 * @throws GitAPIException If there's an error with the Git API
	 */
	private Git pullExistingRepo(EndpointConfigType endpoint, File folder2analyze, boolean publicAccess,
			AbstractGeboSecretContent secret) throws IOException, WrongRepositoryStateException,
			InvalidConfigurationException, InvalidRemoteException, CanceledException, RefNotFoundException,
			RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException {
		Git git = Git.open(folder2analyze);
		PullCommand pullCmd = git.pull();
		if (!publicAccess) {
			if (endpoint.getRepositoryUri().startsWith("ssh:")) {
				SShTransportConfigCallbackImpl transportConfigCallback = new SShTransportConfigCallbackImpl(endpoint,
						publicAccess, secret);
				pullCmd = pullCmd.setTransportConfigCallback(transportConfigCallback);
			}
			if (secret != null && secret.type() == GeboSecretType.USERNAME_PASSWORD) {
				GeboUsernamePasswordContent content = (GeboUsernamePasswordContent) secret;
				UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(
						content.getUsername(), content.getPassword());
				pullCmd = pullCmd.setCredentialsProvider(user);
			}
		}
		pullCmd.setRemote("origin").setRemoteBranchName(endpoint.getBranch()).call();
		return git;
	}

	/**
	 * Clones a remote Git repository into a local folder.
	 * 
	 * @param endpoint Endpoint configuration for the Git repository
	 * @param folder2analyze Local folder to clone the repository into
	 * @param publicAccess Whether the repository is publicly accessible
	 * @param secret Secret content for authentication if not public
	 * @return Git object representing the cloned repository
	 * @throws InvalidRemoteException If the remote is invalid
	 * @throws TransportException If there's a transport error
	 * @throws GitAPIException If there's an error with the Git API
	 * @throws GeboContentHandlerSystemException If there's a system error in handling the content
	 */
	private Git cloneRepository(EndpointConfigType endpoint, File folder2analyze, boolean publicAccess,
			AbstractGeboSecretContent secret)
			throws InvalidRemoteException, TransportException, GitAPIException, GeboContentHandlerSystemException {
		CloneCommand clone = Git.cloneRepository().setURI(endpoint.getRepositoryUri()).setBranch(endpoint.getBranch())
				.setDirectory(folder2analyze);
		if (!publicAccess) {
			if (endpoint.getRepositoryUri().startsWith("ssh:")) {
				SShTransportConfigCallbackImpl transportConfigCallback = new SShTransportConfigCallbackImpl(endpoint,
						publicAccess, secret);
				clone = clone.setTransportConfigCallback(transportConfigCallback);
			}
			if (secret != null && secret.type() == GeboSecretType.USERNAME_PASSWORD) {
				CredentialsProvider provider = null;
				GeboUsernamePasswordContent content = (GeboUsernamePasswordContent) secret;
				provider = new UsernamePasswordCredentialsProvider(content.getUsername(), content.getPassword());
				clone = clone.setCredentialsProvider(provider);
			}
		}
		return clone.call();
	}

	/**
	 * Recursively removes a file or directory.
	 * 
	 * @param file File or directory to remove
	 * @throws GeboContentHandlerSystemException If the file or directory cannot be deleted
	 */
	protected void removeRecursively(File file) throws GeboContentHandlerSystemException {
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File _file = files[i];
				removeRecursively(_file);
			}
			if (!file.delete()) {
				throw new GeboContentHandlerSystemException("Cannot delete " + file.getAbsolutePath());
			}
		} else {
			if (!file.delete()) {
				throw new GeboContentHandlerSystemException("Cannot delete " + file.getAbsolutePath());
			}
		}
	}

	/**
	 * Consumes Git meta information from the repository.
	 * Currently a placeholder for future implementation.
	 * 
	 * @param git Git object representing the repository
	 * @param consumer Consumer for the content
	 */
	protected void consumeGitMetaInformations(Git git, IGContentConsumer consumer) {
		Repository repository = git.getRepository();
		// Empty implementation, likely to be extended in subclasses
	}

}