/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.util;

import java.util.Base64;
import java.util.Optional;

import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for handling operations related to HTTP cookies.
 * Provides methods to get, add, delete cookies and to serialize and deserialize cookie values.
 * 
 * AI generated comments
 */
public class CookieUtils {

    /**
     * Retrieves a cookie by name from the HTTP request.
     *
     * @param request the HTTP request containing the cookies
     * @param name the name of the cookie to retrieve
     * @return an Optional containing the Cookie if found, otherwise an empty Optional
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        // Iterate through all cookies to find the one matching the specified name
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        // Return an empty Optional if the cookie is not found
        return Optional.empty();
    }

    /**
     * Adds a new cookie to the HTTP response.
     *
     * @param response the HTTP response to which the cookie will be added
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param maxAge the maximum age of the cookie in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); // Set the path to root
        cookie.setHttpOnly(true); // Make cookie HTTP only
        cookie.setMaxAge(maxAge); // Set max age
        response.addCookie(cookie); // Add cookie to response
    }

    /**
     * Deletes the specified cookie from the HTTP response if it exists in the request.
     *
     * @param request the HTTP request containing the cookies
     * @param response the HTTP response to which the deletion command will be sent
     * @param name the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        
        // Iterate through all cookies to find and delete the one matching the specified name
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(""); // Clear cookie value
                    cookie.setPath("/"); // Reset path
                    cookie.setMaxAge(0); // Invalidate cookie
                    response.addCookie(cookie); // Add updated cookie to response
                }
            }
        }
    }

    /**
     * Serializes an object to a Base64 encoded string.
     * 
     * @param object the object to serialize
     * @return a Base64 encoded string representing the serialized object
     */
    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    /**
     * Deserializes a Base64 encoded cookie value back to an object of specified class type.
     *
     * @param cookie the cookie containing the Base64 encoded value
     * @param cls the class type to cast the deserialized object to
     * @param <T> the type of the class
     * @return the deserialized object cast to the specified class type
     */
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}