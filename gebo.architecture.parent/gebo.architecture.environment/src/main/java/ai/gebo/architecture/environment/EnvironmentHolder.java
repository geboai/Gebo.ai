/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.environment;

/**
 * EnvironmentHolder is a utility class that manages environment-related configurations.
 * It provides constants and methods to retrieve environment variable values.
 * 
 * Gebo.ai comment agent
 */
public class EnvironmentHolder {
    // Constant for the work directory environment variable name
    public static final String GEBO_WORK_DIRECTORY = "GEBO_WORK_DIRECTORY";
    // Constant for the home directory environment variable name
    public static final String GEBO_HOME = "GEBO_HOME";
    // Retrieves the value of the home directory from system properties or environment variables
    public static final String GEBO_HOME_ENVIRONMENT_VALUE = getPropertyOrEnvironment(GEBO_HOME);
    // Retrieves the value of the work directory from system properties or environment variables
    public static final String GEBO_WORK_DIRECTORY_ENVIRONMENT_VALUE = getPropertyOrEnvironment(GEBO_WORK_DIRECTORY);

    /**
     * Retrieves the value of a specified variable from system properties,
     * falling back to environment variables if the property is not set or is empty.
     *
     * @param variable the name of the property or environment variable
     * @return the value of the property or environment variable, or null if not found
     */
    public static String getPropertyOrEnvironment(String variable) {
        // Attempt to retrieve the variable from system properties
        String value = System.getProperty(variable);
        // If the property is not found or empty, try environment variables
        if (value == null || value.trim().length() == 0) {
            value = System.getenv(variable);
        }
        return value; // Return the found value, or null if neither are set
    }
}