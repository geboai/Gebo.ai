/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * The identity of this installation, generated once: a UUID joined to the host
 * name that generated it. Every token minted here carries it, and a token that
 * does not carry it is refused, so a token minted by another installation
 * signed with the same secret (the shipped default one, for example) is not
 * accepted. One record, fixed id: whichever service starts first creates it.
 */
@Document(collection = "geboInstallation")
@Data
public class GeboInstallation {
	public static final String CODE = "INSTALLATION";

	@Id
	private String code = CODE;
	/** UUID joined to the host name that generated it. */
	private String installationId = null;
	private String hostName = null;
	private Date dateCreated = null;
}
