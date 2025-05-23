/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.internal.transport.ssh.jsch.CredentialsProviderUserInfo;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboSshKeySecretContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;

/**
 * AI generated comments
 * 
 * Implementation of TransportConfigCallback that configures SSH transport for Git operations.
 * This class handles both SSH key and username/password authentication methods for Git repositories
 * accessed via SSH protocol.
 */
public class SShTransportConfigCallbackImpl implements TransportConfigCallback {
	// The Git project endpoint
	GGitProjectEndpoint endpoint = null;
	// Flag indicating if the repository access is public
	boolean publicAccess = false;
	// Secret credentials for authentication
	AbstractGeboSecretContent secret = null;

	/**
	 * Constructor for SSH transport configuration.
	 * 
	 * @param endpoint The Git project endpoint to connect to
	 * @param publicAccess Flag indicating if the repository has public access
	 * @param secret The secret credentials for authentication (SSH key or username/password)
	 */
	public SShTransportConfigCallbackImpl(GGitProjectEndpoint endpoint, boolean publicAccess,
			AbstractGeboSecretContent secret) {
		this.endpoint = endpoint;
		this.secret = secret;
		this.publicAccess = publicAccess;
	}

	/**
	 * Custom SSH session factory that configures SSH connection parameters and handles
	 * authentication with the appropriate credentials.
	 */
	private final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
		/**
		 * Configures the SSH session, disabling strict host key checking and setting up
		 * username/password authentication if provided.
		 * 
		 * @param hc The host configuration
		 * @param session The SSH session to configure
		 */
		@Override
		protected void configure(OpenSshConfig.Host hc, Session session) {
			session.setConfig("StrictHostKeyChecking", "no");
			if (secret != null && secret.type() == GeboSecretType.USERNAME_PASSWORD) {
				GeboUsernamePasswordContent content = (GeboUsernamePasswordContent) secret;
				UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(
						content.getUsername(), content.getPassword());
				CredentialsProviderUserInfo uinfo = new CredentialsProviderUserInfo(session, user);
				session.setUserInfo(uinfo);
			}
		}

		/**
		 * Creates and configures the JSch instance with SSH key authentication if provided.
		 * 
		 * @param fs The filesystem
		 * @return The configured JSch instance
		 * @throws JSchException If there's an error configuring JSch
		 */
		@Override
		protected JSch createDefaultJSch(FS fs) throws JSchException {
			JSch jSch = super.createDefaultJSch(fs);
			if (secret != null && secret.type() == GeboSecretType.SSH_KEY) {
				GeboSshKeySecretContent sshKeyContent = (GeboSshKeySecretContent) secret;
				jSch.addIdentity(sshKeyContent.getEmail(), sshKeyContent.getKey().getBytes(),
						sshKeyContent.getPub().getBytes(), sshKeyContent.getPassphrase().getBytes());
			}
			return jSch;
		}
	};

	/**
	 * Configures the transport with the SSH session factory.
	 * 
	 * @param transport The Git transport to configure
	 */
	@Override
	public void configure(Transport transport) {
		SshTransport sshTransport = (SshTransport) transport;
		sshTransport.setSshSessionFactory(sshSessionFactory);
	}

}