/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class providing methods to map between Enum constants and 
 * static fields of a class.
 * 
 * Gebo.ai Commentor AI generated comments
 */
public class EnumToStaticMapperUtil {
    // Logger for logging error messages
    private final static Logger LOGGER = LoggerFactory.getLogger(EnumToStaticMapperUtil.class);

    /**
     * Default constructor for EnumToStaticMapperUtil.
     * Initializes an instance of this utility class.
     */
    public EnumToStaticMapperUtil() {

    }

    /**
     * Maps an Enum constant to a corresponding static field in a class.
     *
     * @param enumElem The enum constant to map.
     * @param staticClass The Class object of the static class containing the field.
     * @param <T> The type of the field to return.
     * @return The value of the static field that matches the enum constant.
     */
    public static <T> T enumToStatic(Enum enumElem, Class<T> staticClass) {
        T value = null;
        if (enumElem != null) {
            String name = enumElem.name();
            try {
                // Access field matching the enum constant name
                Field field = staticClass.getField(name);

                // Check if the field is public, static, and of the assignable type
                if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())
                        && staticClass.isAssignableFrom(field.getType())) {
                    value = (T) field.get(null);
                }

            } catch (Throwable th) {
                LOGGER.error("Error in enumToStatic(" + enumElem + ",...)", th);
            }
        }
        return value;
    }

    /**
     * Maps a static field value to a corresponding Enum constant.
     *
     * @param enumType The Class object of the enum type.
     * @param staticClass The Class object of the static class containing the field.
     * @param instanceValue The value of the static field to map.
     * @param <T> The type of the enum to return.
     * @param <I> The type of the instance value.
     * @return The Enum constant that matches the static field value.
     */
    public static <T extends Enum, I> T staticToEnum(Class<T> enumType, Class<I> staticClass, I instanceValue) {
        T outValue = null;
        Field[] fields = staticClass.getFields();
        if (fields != null) {
            for (Field field : fields) {

                // Check if the field is public, static, and of the assignable type
                if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())
                        && staticClass.isAssignableFrom(field.getType())) {
                    try {
                        if (instanceValue == field.get(null)) {
                            T[] enumInstances = enumType.getEnumConstants();
                            if (enumInstances != null) {
                                for (T thisValue : enumInstances) {
                                    // Match enum constant with the field name
                                    if (thisValue.name().equals(field.getName())) {
                                        outValue = thisValue;
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        LOGGER.error("Error in staticToEnum(" + enumType.getName() + ",...)", th);
                    }
                }

            }
        }
        return outValue;
    }

}