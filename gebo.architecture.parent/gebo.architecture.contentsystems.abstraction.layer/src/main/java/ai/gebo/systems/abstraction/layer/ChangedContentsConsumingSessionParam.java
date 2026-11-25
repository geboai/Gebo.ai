/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.systems.abstraction.layer;

/**
 * The parameter of a session of the ingestion fed by its caller with what it found
 * changed in a data source - the new contents, the changed ones and the ones gone -
 * rather than with the whole data source. Such a session tells its own deletions, so
 * it does not end with the check, on the data source, of every content it did not
 * meet: that check is what a publication, which reads everything, ends with.
 */
public class ChangedContentsConsumingSessionParam extends NoContentConsumingSessionParam {

}
