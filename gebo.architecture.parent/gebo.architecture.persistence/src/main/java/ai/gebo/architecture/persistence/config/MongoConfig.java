/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

/**
 * Gebo.ai comment agent
 * MongoConfig class configuration for MongoDB.
 * This class extends AbstractMongoClientConfiguration to provide MongoDB connection settings.
 * The configuration is enabled conditionally based on the "ai.gebo.mongodb.enabled" property.
 */
@ConditionalOnProperty(prefix = "ai.gebo.mongodb", name = "enabled", havingValue = "true")
@Configuration
@ConfigurationProperties("ai.gebo.mongodb")
public class MongoConfig extends AbstractMongoClientConfiguration {
    // The name of the MongoDB database to connect to
    String databaseName = "Gebo-ai";
    
    // The MongoDB connection string
    String connectionString = null;
    
    // Boolean to indicate if the MongoDB configuration is enabled
    Boolean enabled = true;

    /**
     * Gets the name of the database to connect to.
     * 
     * @return the database name
     */
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    /**
     * Sets the name of the database to connect to.
     * 
     * @param databaseName the new database name
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Configures MongoClient settings using the provided builder.
     * Throws a MissingEnvironmentVariableException if the connection string is not set.
     * 
     * @param builder the MongoClientSettings builder to configure
     */
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        // Check if the connection string is null and throw an exception if so
        if (connectionString == null) {
            throw new MissingEnvironmentVariableException("ai.gebo.mongodb.connectionString configuration is missing");
        }
        // Apply the connection string to the builder
        builder.applyConnectionString(new ConnectionString(connectionString));
    }

    /**
     * Gets the MongoDB connection string.
     * 
     * @return the connection string
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * Sets the MongoDB connection string.
     * 
     * @param connectionString the new connection string
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Gets whether the MongoDB configuration is enabled.
     * 
     * @return true if enabled, false otherwise
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether the MongoDB configuration is enabled.
     * 
     * @param enabled true to enable, false to disable
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}