/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.security.model.GeboInstallation;
import ai.gebo.security.services.IGInstallationIdProvider;

/**
 * Reads the installation record from this service's database, creating it the
 * first time: a UUID joined to the host name, never "localhost". Read once and
 * kept: the record never changes. Concurrent first starts are safe: the record
 * has a fixed id, the loser of the insert reads the winner's.
 * <p>
 * The owner of the record is the monolith, or heimdall in the microservices
 * deployment; the other microservices have databases of their own and ask
 * heimdall instead, through a provider of the security client module that
 * takes precedence over this one.
 */
@Service
public class GeboInstallationIdProviderImpl implements IGInstallationIdProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboInstallationIdProviderImpl.class);

	private final MongoTemplate template;
	private volatile String installationId = null;

	public GeboInstallationIdProviderImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public String getInstallationId() {
		String id = installationId;
		if (id == null) {
			synchronized (this) {
				id = installationId;
				if (id == null) {
					id = readOrCreate();
					installationId = id;
				}
			}
		}
		return id;
	}

	private String readOrCreate() {
		GeboInstallation existing = template.findById(GeboInstallation.CODE, GeboInstallation.class);
		if (existing != null && existing.getInstallationId() != null) {
			LOGGER.info("Installation id read: {}", existing.getInstallationId());
			return existing.getInstallationId();
		}
		GeboInstallation created = new GeboInstallation();
		String host = hostName();
		created.setHostName(host);
		created.setInstallationId(UUID.randomUUID() + "@" + host);
		created.setDateCreated(new Date());
		try {
			template.insert(created);
			LOGGER.info("Installation id generated: {}", created.getInstallationId());
			return created.getInstallationId();
		} catch (DuplicateKeyException raced) {
			// another service created it first: its id is the one
			GeboInstallation winner = template.findById(GeboInstallation.CODE, GeboInstallation.class);
			if (winner == null || winner.getInstallationId() == null) {
				throw new IllegalStateException("The installation record exists but cannot be read", raced);
			}
			return winner.getInstallationId();
		}
	}

	/**
	 * The host's name, never a loopback one: the machine's own name, else the
	 * HOSTNAME / COMPUTERNAME environment, else the name of a non-loopback
	 * address of a network interface.
	 */
	static String hostName() {
		try {
			String name = InetAddress.getLocalHost().getHostName();
			if (isUsable(name)) {
				return name.toLowerCase(Locale.ROOT);
			}
		} catch (Exception e) {
			LOGGER.debug("The local host name cannot be resolved: {}", e.getMessage());
		}
		for (String variable : new String[] { "HOSTNAME", "COMPUTERNAME" }) {
			String name = System.getenv(variable);
			if (isUsable(name)) {
				return name.toLowerCase(Locale.ROOT);
			}
		}
		try {
			for (NetworkInterface nic : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				if (nic.isLoopback() || !nic.isUp()) {
					continue;
				}
				for (InetAddress address : Collections.list(nic.getInetAddresses())) {
					if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
						String name = address.getCanonicalHostName();
						return (isUsable(name) ? name : address.getHostAddress()).toLowerCase(Locale.ROOT);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.debug("The network interfaces cannot be read: {}", e.getMessage());
		}
		return "unknown-host";
	}

	private static boolean isUsable(String name) {
		if (name == null || name.isBlank()) {
			return false;
		}
		String lower = name.trim().toLowerCase(Locale.ROOT);
		return !lower.equals("localhost") && !lower.startsWith("localhost.") && !lower.equals("127.0.0.1")
				&& !lower.equals("::1") && !lower.equals("0:0:0:0:0:0:0:1");
	}
}
