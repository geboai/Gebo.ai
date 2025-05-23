/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * Service for interacting with Git repositories through JGit.
 * Provides functionality to retrieve branch information and test Git connectivity.
 */
@Service
public class GitClientService {
	@Autowired
	IGeboSecretsAccessService secretsAccessService;
	
	/**
	 * Constant prefix for remote branch references in Git.
	 */
	public static final String REMOTE_BRANCHES_PREFIX = "refs/heads/";

	/**
	 * Retrieves a list of branches from a Git repository.
	 * 
	 * @param endpoint The Git project endpoint containing repository URI and authentication details
	 * @return OperationStatus containing the list of branch names if successful, or error messages if not
	 */
	public OperationStatus<List<String>> getGitBranches(GGitProjectEndpoint endpoint) {
		List<String> branchesList = new ArrayList<String>();
		OperationStatus<List<String>> os = OperationStatus.of(branchesList);
		os.getMessages().clear();
		try {

			LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository().setRemote(endpoint.getRepositoryUri()) // Set the
																												// remote
																												// URL
					.setHeads(true) // Include branch heads in the output
					.setTags(false); // Exclude tags from the output
			boolean publicAccess = endpoint.getPublicAccess() != null && endpoint.getPublicAccess();
			if (endpoint.getIdentityCode() != null && endpoint.getIdentityCode().trim().length() > 0 && !publicAccess) {
				// Configure authentication if identity code is provided and repository is not public
				AbstractGeboSecretContent geboSecret = secretsAccessService
						.getSecretContentById(endpoint.getIdentityCode());
				SShTransportConfigCallbackImpl sshTransportCallbackImpl = new SShTransportConfigCallbackImpl(endpoint,
						publicAccess, geboSecret);
				lsRemoteCommand.setTransportConfigCallback(sshTransportCallbackImpl);
				switch (geboSecret.type()) {
				case USERNAME_PASSWORD: {
					// Setup username/password authentication
					GeboUsernamePasswordContent up = (GeboUsernamePasswordContent) geboSecret;
					lsRemoteCommand.setCredentialsProvider(
							new UsernamePasswordCredentialsProvider(up.getUsername(), up.getPassword()));
				}
					break;
				case SSH_KEY: {
					// SSH key authentication is handled by the transport callback
				}
					;
					break;
				}
			} else {
				if (!publicAccess) {
					// Add error message if repository requires credentials but none provided
					os.getMessages()
							.add(GUserMessage.errorMessage(
									"Repository not configured as \'public\' but no credentials provided",
									"Please configure credentials"));
				}
			}
			// Execute the command to list remote references
			Collection<Ref> result = lsRemoteCommand.call();
			List<Ref> refs = new ArrayList<Ref>(result);
			for (int i = 0; i < refs.size(); i++) {
				Ref ref = refs.get(i);
				if (ref.getName().startsWith(REMOTE_BRANCHES_PREFIX)) {
					// Strip the prefix for branch names
					branchesList.add(ref.getName().substring(REMOTE_BRANCHES_PREFIX.length()));
				} else
					branchesList.add(ref.getName());
			}

		} catch (GitAPIException e) {
			// Handle Git communication errors
			os.getMessages().add(GUserMessage
					.errorMessage("Problems comunicating with the git server, review the configuration", e));
		} catch (GeboCryptSecretException e) {
			// Handle credential encryption/decryption errors
			os.getMessages().add(GUserMessage.errorMessage("Technical problems in secure credentials subsystems",
					"Some technical issues in the secure credentials subsystem"));
		} finally {
		}
		return os;
	}

	/**
	 * Tests the connectivity and configuration of a Git repository endpoint.
	 * Verifies if the specified branch exists in the repository.
	 * 
	 * @param endpoint The Git project endpoint to test
	 * @return OperationStatus containing the endpoint with success/error messages
	 */
	public OperationStatus<GGitProjectEndpoint> testGitConfiguration(GGitProjectEndpoint endpoint) {
		OperationStatus<List<String>> branchesList = getGitBranches(endpoint);
		OperationStatus<GGitProjectEndpoint> os = OperationStatus.of(endpoint);
		if (branchesList.isHasErrorMessages()) {
			// If there were errors getting branches, propagate those errors
			os.getMessages().clear();
			os.setMessages(branchesList.getMessages());
		} else {
			// Check if the specified branch exists in the repository
			boolean branchFound = branchesList.getResult().contains(endpoint.getBranch());
			if (branchFound) {
				os.getMessages().add(GUserMessage.successMessage("Git repository connectivity OK!",
						"The list of remote branches is:" + branchesList.toString()));
			} else {
				os.getMessages()
						.add(GUserMessage.errorMessage(
								"The git repository does not manage a \'" + endpoint.getBranch() + "\' branch",
								"The list of remote branches is:" + branchesList.getResult().toString()));
			}
		}
		return os;
	}
}