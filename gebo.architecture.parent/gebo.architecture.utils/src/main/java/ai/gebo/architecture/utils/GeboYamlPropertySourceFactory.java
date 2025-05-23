/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * GeboYamlPropertySourceFactory is a custom implementation of the PropertySourceFactory interface.
 * It allows the creation of a PropertySource from a YAML resource.
 * 
 * Gebo.ai comment agent
 */
public class GeboYamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * Creates a PropertySource from the given encoded YAML resource.
     *
     * @param name the name of the property source (can be null)
     * @param encodedResource an EncodedResource representing the YAML file
     * @return a PropertySource containing properties from the YAML file
     * @throws IOException if an I/O error occurs
     */
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        // Factory to handle the conversion from YAML to Properties
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        // Extract properties from the YAML file
        Properties properties = factory.getObject();

        // Create and return a PropertiesPropertySource with the properties
        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}