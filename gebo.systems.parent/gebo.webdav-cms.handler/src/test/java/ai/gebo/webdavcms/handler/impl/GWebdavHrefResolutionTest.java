/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.webdavcms.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * That an href returned by a WebDAV server is resolved into something this
 * module can list again.
 *
 * <p>
 * The three forms below are not hypothetical. Nextcloud 30 (SabreDAV) answers
 * {@code /remote.php/dav/files/<user>/Documents/} and Apache {@code mod_dav}
 * answers {@code /Documents/} - both absolute-path references, neither usable as
 * a request uri on its own. RFC 4918 also permits the relative-path form and the
 * full-uri form, and only resolution against the LISTED uri gets all of them
 * right.
 * </p>
 *
 * Gebo.ai comment agent
 */
class GWebdavHrefResolutionTest {

	private static final String LISTED = "http://dav.example.com/remote.php/dav/files/webdav";

	@Test
	void anAbsolutePathReferenceTakesSchemeAndHostFromTheListedUri() {
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl
				.absolutize("/remote.php/dav/files/webdav/Documents/", LISTED))
				.isEqualTo("http://dav.example.com/remote.php/dav/files/webdav/Documents/");
	}

	@Test
	void theApacheModDavFormResolvesAgainstTheSameOrigin() {
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.absolutize("/Documents/",
				"http://dav.example.com/")).isEqualTo("http://dav.example.com/Documents/");
	}

	@Test
	void aRelativePathReferenceResolvesAgainstTheListedCollection() {
		// The case that makes the listed uri the right base rather than the system
		// baseUri: resolving this against the server root would lose the collection.
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.absolutize("Documents/", LISTED + "/"))
				.isEqualTo("http://dav.example.com/remote.php/dav/files/webdav/Documents/");
	}

	@Test
	void anHrefThatIsAlreadyAbsoluteIsLeftAlone() {
		String absolute = "http://other.example.com/dav/Shared/";

		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.absolutize(absolute, LISTED))
				.isEqualTo(absolute);
	}

	@Test
	void percentEncodingIsPreserved() {
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl
				.absolutize("/remote.php/dav/files/webdav/Nextcloud%20Manual.pdf", LISTED))
				.isEqualTo("http://dav.example.com/remote.php/dav/files/webdav/Nextcloud%20Manual.pdf");
	}

	@Test
	void nullsAndUnparseableHrefsDoNotThrow() {
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.absolutize(null, LISTED)).isNull();
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.absolutize("/Documents/", null))
				.isEqualTo("/Documents/");
		assertThat(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.absolutize("a b c", LISTED))
				.isEqualTo("a b c");
	}
}
