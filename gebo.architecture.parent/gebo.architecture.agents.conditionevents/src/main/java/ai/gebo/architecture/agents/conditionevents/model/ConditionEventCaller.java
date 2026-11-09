/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Who delivers an event, and through which channel: an authenticated principal
 * with its authorities (a REST call), or a channel trusted as infrastructure (the
 * message broker), whose sender is named but not authenticated here.
 */
public final class ConditionEventCaller {
	/** The channels of the adapters of this module. */
	public static final String REST = "REST";
	public static final String MESSAGE = "MESSAGE";

	private final String name;
	private final Set<String> authorities;
	private final String channel;
	private final boolean trustedChannel;
	private final String channelSource;

	private ConditionEventCaller(String name, Set<String> authorities, String channel, boolean trustedChannel,
			String channelSource) {
		this.name = name;
		this.authorities = authorities != null ? Set.copyOf(authorities) : Set.of();
		this.channel = channel;
		this.trustedChannel = trustedChannel;
		this.channelSource = channelSource;
	}

	/** A principal authenticated by the caller's channel; null authentication makes an anonymous caller. */
	public static ConditionEventCaller authenticated(Authentication authentication, String channel) {
		final Set<String> authorities = new LinkedHashSet<String>();
		if (authentication != null && authentication.getAuthorities() != null) {
			for (GrantedAuthority authority : authentication.getAuthorities()) {
				if (authority != null && authority.getAuthority() != null) {
					authorities.add(authority.getAuthority());
				}
			}
		}
		return new ConditionEventCaller(authentication != null ? authentication.getName() : null, authorities, channel,
				false, null);
	}

	/** A principal named with its authorities, as a channel authenticated it. */
	public static ConditionEventCaller of(String name, Set<String> authorities, String channel) {
		return new ConditionEventCaller(name, authorities, channel, false, null);
	}

	/**
	 * A channel trusted as infrastructure: whoever may write on it may deliver.
	 *
	 * @param sender        who the channel says sent it, when it says
	 * @param channel       the channel
	 * @param channelSource the component of the channel it came from
	 */
	public static ConditionEventCaller trustedChannel(String sender, String channel, String channelSource) {
		return new ConditionEventCaller(sender, Set.of(), channel, true, channelSource);
	}

	public String getName() {
		return name;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public String getChannel() {
		return channel;
	}

	public boolean isTrustedChannel() {
		return trustedChannel;
	}

	public String getChannelSource() {
		return channelSource;
	}

	/** Whether one of the caller's authorities is, or holds, the one named, case aside. */
	public boolean holds(String authority) {
		if (authority == null || authority.isBlank()) {
			return false;
		}
		final String wanted = authority.strip().toUpperCase();
		return authorities.stream().anyMatch(held -> held != null && held.toUpperCase().contains(wanted));
	}

	@Override
	public String toString() {
		return (trustedChannel ? "trusted " : "") + channel + " caller:" + name
				+ (channelSource != null ? " from:" + channelSource : "");
	}
}
