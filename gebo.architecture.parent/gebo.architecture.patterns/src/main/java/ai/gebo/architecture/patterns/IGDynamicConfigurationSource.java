/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

/**
 * Gebo.ai comment agent
 * Interface representing a dynamic configuration source.
 * This interface provides methods to retrieve configuration data.
 *
 * @param <ConfigTypes> The type of configuration objects that this source handles.
 */
package ai.gebo.architecture.patterns;

import java.util.ArrayList;
import java.util.List;

public interface IGDynamicConfigurationSource<ConfigTypes> {

	/**
	 * Retrieves a list of configuration objects.
	 *
	 * @return a List of configuration objects of type ConfigTypes.
	 */
	public List<ConfigTypes> getConfigurations();

	/**
	 * Finds a configuration object by its unique code.
	 *
	 * @param code the unique code associated with a configuration.
	 * @return the configuration object of type ConfigTypes matching the provided
	 *         code.
	 */
	public ConfigTypes findByCode(String code);

	public static <ConfigTypes> IGDynamicConfigurationSource<ConfigTypes> compose(
			IGDynamicConfigurationSource<ConfigTypes>... configs) {
		return new IGDynamicConfigurationSource<ConfigTypes>() {
			@Override
			public ConfigTypes findByCode(String code) {
				if (configs != null) {
					for (IGDynamicConfigurationSource<ConfigTypes> ds : configs) {
						ConfigTypes cf = ds.findByCode(code);
						if (cf != null)
							return cf;
					}
				}
				return null;
			}

			@Override
			public List<ConfigTypes> getConfigurations() {
				List<ConfigTypes> out = new ArrayList<>();
				if (configs != null) {
					for (IGDynamicConfigurationSource<ConfigTypes> ds : configs) {
						List<ConfigTypes> list = ds.getConfigurations();
						if (list != null) {
							out.addAll(list);
						}
					}
				}
				return out;
			}
		};
	}

}