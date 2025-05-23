/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemConsumingService;

/**
 * AI generated comments
 * Interface for testing a virtual filesystem remote consumer service.
 * It extends the {@link IGRemoteVirtualFilesystemConsumingService} interface, 
 * which defines methods to consume remote virtual filesystem services.
 *
 * @param <TestVirtualRemoteSystem> The type representing the remote system.
 * @param <TestVirtualRemoteProjectEndpoint> The type representing the remote project endpoint.
 * @param <TestVirtualFilesystemRemoteReference> The type representing the remote reference in the filesystem.
 */
public interface IGTestVirtualFilesystemRemoteConsumerService 
    extends IGRemoteVirtualFilesystemConsumingService<TestVirtualRemoteSystem, TestVirtualRemoteProjectEndpoint, TestVirtualFilesystemRemoteReference> {
  // This interface inherits all methods from IGRemoteVirtualFilesystemConsumingService
  // and does not add any additional methods. It acts as a marker interface, 
  // facilitating the testing of the Virtual Filesystem Remote Consumer Service.
}