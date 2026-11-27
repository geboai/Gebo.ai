/*
 * OpenProject API V3 (Stable)
 * You're looking at the current **stable** documentation of the OpenProject APIv3. If you're interested in the current development version, please go to [github.com/opf](https://github.com/opf/openproject/tree/dev/docs/api/apiv3).  ## Introduction  The documentation for the APIv3 is written according to the [OpenAPI 3.1 Specification](https://swagger.io/specification/). You can either view the static version of this documentation on the [website](https://www.openproject.org/docs/api/introduction/) or the interactive version, rendered with [OpenAPI Explorer](https://github.com/Rhosys/openapi-explorer/blob/main/README.md), in your OpenProject installation under `/api/docs`. In the latter you can try out the various API endpoints directly interacting with our OpenProject data. Moreover you can access the specification source itself under `/api/v3/spec.json` and `/api/v3/spec.yml` (e.g. [here](https://community.openproject.org/api/v3/spec.yml)).  The APIv3 is a hypermedia REST API, a shorthand for \"Hypermedia As The Engine Of Application State\" (HATEOAS). This means that each endpoint of this API will have links to other resources or actions defined in the resulting body.  These related resources and actions for any given resource will be context sensitive. For example, only actions that the authenticated user can take are being rendered. This can be used to dynamically identify actions that the user might take for any given response.  As an example, if you fetch a work package through the [Work Package endpoint](https://www.openproject.org/docs/api/endpoints/work-packages/), the `update` link will only be present when the user you authenticated has been granted a permission to update the work package in the assigned project.  ## HAL+JSON  HAL is a simple format that gives a consistent and easy way to hyperlink between resources in your API. Read more in the following specification: [https://tools.ietf.org/html/draft-kelly-json-hal-08](https://tools.ietf.org/html/draft-kelly-json-hal-08)  **OpenProject API implementation of HAL+JSON format** enriches JSON and introduces a few meta properties:  - `_type` - specifies the type of the resource (e.g.: WorkPackage, Project) - `_links` - contains all related resource and action links available for the resource - `_embedded` - contains all embedded objects  HAL does not guarantee that embedded resources are embedded in their full representation, they might as well be partially represented (e.g. some properties can be left out). However in this API you have the guarantee that whenever a resource is **embedded**, it is embedded in its **full representation**.  ## API response structure  All API responses contain a single HAL+JSON object, even collections of objects are technically represented by a single HAL+JSON object that itself contains its members. More details on collections can be found in the [Collections Section](https://www.openproject.org/docs/api/collections/).  ## Authentication  The API supports the following authentication schemes:  * Session-based authentication * API tokens     * passed as Bearer token     * passed via Basic auth * OAuth 2.0     * using built-in authorization server     * using an external authorization server (RFC 9068)  Depending on the settings of the OpenProject instance many resources can be accessed without being authenticated. In case the instance requires authentication on all requests the client will receive an **HTTP 401** status code in response to any request.  Otherwise unauthenticated clients have all the permissions of the anonymous user.  ### Session-based authentication  This means you have to login to OpenProject via the Web-Interface to be authenticated in the API. This method is well-suited for clients acting within the browser, like the Angular-Client built into OpenProject.  Keep in mind that session-based authentication is only possible when accessing the API from the same origin. This is assured by checking the `Sec-Fetch-Site` HTTP header.  ### API token as bearer token  Users can authenticate towards the API v3 using an API token as a bearer token.  For example:  ```shell API_KEY=opapi-2519132cdf62dcf5a66fd96394672079f9e9cad1 curl -H \"Authorization: Bearer $API_KEY\" https://community.openproject.org/api/v3/users/42 ```  Users can generate API tokens on their account page.  ### API token through Basic Auth  API tokens can also be used with basic auth, using the user name `apikey` (NOT your login) and the API token as the password.  For example:  ```shell API_KEY=opapi-2519132cdf62dcf5a66fd96394672079f9e9cad1 curl -u apikey:$API_KEY https://community.openproject.org/api/v3/users/42 ```  ### OAuth 2.0 authentication  OpenProject allows authentication and authorization with OAuth2 with *Authorization code flow*, as well as *Client credentials* operation modes.  To get started, you first need to register an application in the OpenProject OAuth administration section of your installation. This will save an entry for your application with a client unique identifier (`client_id`) and an accompanying secret key (`client_secret`).  You can then use one the following guides to perform the supported OAuth 2.0 flows:  - [Authorization code flow](https://oauth.net/2/grant-types/authorization-code)  - [Authorization code flow with PKCE](https://doorkeeper.gitbook.io/guides/ruby-on-rails/pkce-flow), recommended for clients unable to keep the client_secret confidential  - [Client credentials](https://oauth.net/2/grant-types/client-credentials/) - Requires an application to be bound to an impersonating user for non-public access  ### OAuth 2.0 using an external authorization server  There is a possibility to use JSON Web Tokens (JWT) generated by an OIDC provider configured in OpenProject as a bearer token to do authenticated requests against the API. The following requirements must be met:  - OIDC provider must be configured in OpenProject with **jwks_uri** - JWT must be signed using RSA algorithm - JWT **iss** claim must be equal to OIDC provider **issuer** - JWT **aud** claim must contain the OpenProject **client ID** used at the OIDC provider - JWT **scope** claim must include a valid scope to access the desired API (e.g. `api_v3` for APIv3) - JWT must be actual (neither expired or too early to be used) - JWT must be passed in Authorization header like: `Authorization: Bearer {jwt}` - User from **sub** claim must be linked to OpenProject before (e.g. by logging in), otherwise it will be not authenticated  In more general terms, OpenProject should be compliant to [RFC 9068](https://www.rfc-editor.org/rfc/rfc9068) when validating access tokens.  ### Why not username and password?  The simplest way to do basic auth would be to use a user's username and password naturally. However, OpenProject already has supported API keys in the past for the API v2, though not through basic auth.  Using **username and password** directly would have some advantages:  * It is intuitive for the user who then just has to provide those just as they would when logging into OpenProject.  * No extra logic for token management necessary.  On the other hand using **API keys** has some advantages too, which is why we went for that:  * If compromised while saved on an insecure client the user only has to regenerate the API key instead of changing their password, too.  * They are naturally long and random which makes them invulnerable to dictionary attacks and harder to crack in general.  Most importantly users may not actually have a password to begin with. Specifically when they have registered through an OpenID Connect provider.  ## Cross-Origin Resource Sharing (CORS)  By default, the OpenProject API is _not_ responding with any CORS headers. If you want to allow cross-domain AJAX calls against your OpenProject instance, you need to enable CORS headers being returned.  Please see [our API settings documentation](https://www.openproject.org/docs/system-admin-guide/api-and-webhooks/) on how to selectively enable CORS.  ## Allowed HTTP methods  - `GET` - Get a single resource or collection of resources  - `POST` - Create a new resource or perform  - `PATCH` - Update a resource  - `DELETE` - Delete a resource  ## Compression  Responses are compressed if requested by the client. Currently [gzip](https://www.gzip.org/) and [deflate](https://tools.ietf.org/html/rfc1951) are supported. The client signals the desired compression by setting the [`Accept-Encoding` header](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3). If no `Accept-Encoding` header is send, `Accept-Encoding: identity` is assumed which will result in the API responding uncompressed.
 *
 * The version of the OpenAPI document: 3
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ai.gebo.openproject.client.invoker;


import tools.jackson.databind.DeserializationFeature;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;

import java.util.function.Consumer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;
import java.util.Optional;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.function.Supplier;

import jakarta.annotation.Nullable;

import java.time.OffsetDateTime;

import ai.gebo.openproject.client.invoker.auth.Authentication;
import ai.gebo.openproject.client.invoker.auth.HttpBasicAuth;
import ai.gebo.openproject.client.invoker.auth.HttpBearerAuth;
import ai.gebo.openproject.client.invoker.auth.ApiKeyAuth;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class ApiClient extends JavaTimeFormatter {
    public enum CollectionFormat {
        CSV(","), TSV("\t"), SSV(" "), PIPES("|"), MULTI(null);

        private final String separator;
        CollectionFormat(String separator) {
            this.separator = separator;
        }

        public String collectionToString(Collection<?> collection) {
            return StringUtils.collectionToDelimitedString(collection, separator);
        }
    }

    protected final HttpHeaders defaultHeaders = new HttpHeaders();
    protected final MultiValueMap<String, String> defaultCookies = new LinkedMultiValueMap<>();

    protected String basePath = "https://community.openproject.org";
    protected List<ServerConfiguration> servers = new ArrayList<ServerConfiguration>(Arrays.asList(
        new ServerConfiguration(
            "https://community.openproject.org",
            "This server",
            new HashMap<String, ServerVariable>()
        )
    ));
    protected Integer serverIndex = 0;
    protected Map<String, String> serverVariables = null;

    protected final RestClient restClient;
    protected final DateFormat dateFormat;
    protected final JsonMapper mapper;
    protected Map<String, Authentication> authentications;

    /**
     * The {@link ExceptionProvider} used to create exceptions thrown by this client.
     * Defaults to {@link ExceptionProvider#DEFAULT}. Can be replaced to customize the exceptions
     * thrown by this client by calling {@link #setExceptionProvider(ExceptionProvider)}.
     */
    protected ExceptionProvider exceptionProvider = ExceptionProvider.DEFAULT;


    public ApiClient() {
        this(null);
    }

    public ApiClient(RestClient restClient) {
        this(restClient, createDefaultDateFormat());
    }

    public ApiClient(JsonMapper mapper, DateFormat format) {
        this(null, mapper, format);
    }

    public ApiClient(RestClient restClient, JsonMapper mapper, DateFormat format) {
        this.mapper = mapper;
        this.restClient = Optional.ofNullable(restClient).orElseGet(() -> buildRestClient(this.mapper));
        this.dateFormat = format;
        this.init();
    }

    protected ApiClient(RestClient restClient, DateFormat format) {
        this(restClient, createDefaultMapper(format), format);
    }

    public static DateFormat createDefaultDateFormat() {
        DateFormat dateFormat = new RFC3339DateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat;
    }

    public static JsonMapper createDefaultMapper(@Nullable DateFormat dateFormat) {
        if (null == dateFormat) {
            dateFormat = createDefaultDateFormat();
        }
        return JsonMapper.builder()
            .defaultDateFormat(dateFormat)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
    }

    protected void init() {
        // Setup authentications (key: authentication name, value: authentication).
        authentications = new HashMap<>();
        authentications.put("BasicAuth", new HttpBasicAuth());
        // Prevent the authentications from being modified.
        authentications = Collections.unmodifiableMap(authentications);
    }

    /**
    * Build the RestClientBuilder used to make RestClient.
    * @param mapper JsonMapper used for serialize/deserialize
    * @return RestClient
    */
    public static RestClient.Builder buildRestClientBuilder(JsonMapper mapper) {

        Consumer<HttpMessageConverters.ClientBuilder> messageConverters = builder -> {
            builder.registerDefaults().withJsonConverter(new JacksonJsonHttpMessageConverter(mapper));
        };

        return RestClient.builder().configureMessageConverters(messageConverters);
    }

    /**
     * Build the RestClientBuilder used to make RestClient.
     * @return RestClient
     */
    public static RestClient.Builder buildRestClientBuilder() {
        return buildRestClientBuilder(createDefaultMapper(null));
    }

    /**
     * Build the RestClient used to make HTTP requests.
     * @param mapper JsonMapper used for serialize/deserialize
     * @return RestClient
     */
    public static RestClient buildRestClient(JsonMapper mapper) {
        return buildRestClientBuilder(mapper).build();
    }

    /**
     * Build the RestClient used to make HTTP requests.
     * @return RestClient
     */
    public static RestClient buildRestClient() {
        return buildRestClientBuilder(createDefaultMapper(null)).build();
    }

    /**
     * Get the current base path
     * @return String the base path
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * Set the base path, which should include the host
     * @param basePath the base path
     * @return ApiClient this client
     */
    public ApiClient setBasePath(String basePath) {
        this.basePath = basePath;
        this.serverIndex = null;
        return this;
    }

    public List<ServerConfiguration> getServers() {
        return servers;
    }

    public ApiClient setServers(List<ServerConfiguration> servers) {
        this.servers = servers;
        return this;
    }

    public Integer getServerIndex() {
        return serverIndex;
    }

    public ApiClient setServerIndex(Integer serverIndex) {
        this.serverIndex = serverIndex;
        return this;
    }

    public Map<String, String> getServerVariables() {
        return serverVariables;
    }

    public ApiClient setServerVariables(Map<String, String> serverVariables) {
        this.serverVariables = serverVariables;
        return this;
    }

    /**
     * Get authentications (key: authentication name, value: authentication).
     * @return Map the currently configured authentication types
     */
    public Map<String, Authentication> getAuthentications() {
        return authentications;
    }

    /**
     * Get the current {@link ExceptionProvider}.
     * @return the exception provider
     */
    public ExceptionProvider getExceptionProvider() {
        return exceptionProvider;
    }

    /**
     * Set a custom {@link ExceptionProvider} to control which exception types are thrown
     * by this client.
     * @param exceptionProvider the exception provider
     * @return this client instance
     */
    public ApiClient setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
        return this;
    }

    /**
     * Get authentication for the given name.
     *
     * @param authName The authentication name
     * @return The authentication, null if not found
     */
    public Authentication getAuthentication(String authName) {
        return authentications.get(authName);
    }

    /**
     * Helper method to set access token for the first Bearer authentication.
     * @param bearerToken Bearer token
     */
    public void setBearerToken(String bearerToken) {
        for (Authentication auth : authentications.values()) {
            if (auth instanceof HttpBearerAuth) {
                ((HttpBearerAuth) auth).setBearerToken(bearerToken);
                return;
            }
        }
        throw exceptionProvider.bearerAuthException();
    }

    /**
    * Helper method to set the supplier of access tokens for Bearer authentication.
    *
    * @param tokenSupplier the token supplier function
    */
    public void setBearerToken(Supplier<String> tokenSupplier) {
        for (Authentication auth : authentications.values()) {
            if (auth instanceof HttpBearerAuth) {
                ((HttpBearerAuth) auth).setBearerToken(tokenSupplier);
            return;
            }
        }
        throw exceptionProvider.bearerAuthException();
    }

    /**
     * Helper method to set username for the first HTTP basic authentication.
     * @param username the username
     */
    public void setUsername(String username) {
        for (Authentication auth : authentications.values()) {
            if (auth instanceof HttpBasicAuth) {
                ((HttpBasicAuth) auth).setUsername(username);
                return;
            }
        }
        throw exceptionProvider.httpBasicAuthException();
    }

    /**
     * Helper method to set password for the first HTTP basic authentication.
     * @param password the password
     */
    public void setPassword(String password) {
        for (Authentication auth : authentications.values()) {
            if (auth instanceof HttpBasicAuth) {
                ((HttpBasicAuth) auth).setPassword(password);
                return;
            }
        }
        throw exceptionProvider.httpBasicAuthException();
    }

    /**
     * Helper method to set API key value for the first API key authentication.
     * @param apiKey the API key
     */
    public void setApiKey(String apiKey) {
        for (Authentication auth : authentications.values()) {
            if (auth instanceof ApiKeyAuth) {
                ((ApiKeyAuth) auth).setApiKey(apiKey);
                return;
            }
        }
        throw exceptionProvider.apiKeyAuthException();
    }

    /**
     * Helper method to set API key prefix for the first API key authentication.
     * @param apiKeyPrefix the API key prefix
     */
    public void setApiKeyPrefix(String apiKeyPrefix) {
        for (Authentication auth : authentications.values()) {
            if (auth instanceof ApiKeyAuth) {
                ((ApiKeyAuth) auth).setApiKeyPrefix(apiKeyPrefix);
                return;
            }
        }
        throw exceptionProvider.apiKeyAuthException();
    }

    /**
     * Set the User-Agent header's value (by adding to the default header map).
     * @param userAgent the user agent string
     * @return ApiClient this client
     */
    public ApiClient setUserAgent(String userAgent) {
        addDefaultHeader("User-Agent", userAgent);
        return this;
    }

    /**
     * Add a default header.
     *
     * @param name The header's name
     * @param value The header's value
     * @return ApiClient this client
     */
    public ApiClient addDefaultHeader(String name, String value) {
        defaultHeaders.set(name, value);
        return this;
    }

    /**
     * Add a default cookie.
     *
     * @param name The cookie's name
     * @param value The cookie's value
     * @return ApiClient this client
     */
    public ApiClient addDefaultCookie(String name, String value) {
        if (defaultCookies.containsKey(name)) {
            defaultCookies.remove(name);
        }
        defaultCookies.add(name, value);
        return this;
    }

    /**
     * Get the date format used to parse/format date parameters.
     * @return DateFormat format
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * Parse the given string into Date object.
     */
    public Date parseDate(String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            throw exceptionProvider.dateTimeException(e);
        }
    }

    /**
     * Format the given Date object into string.
     */
    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Get the JsonMapper used to make HTTP requests.
     * @return JsonMapper mapper
     */
    public JsonMapper getJsonMapper() {
        return mapper;
    }

    /**
     * Get the RestClient used to make HTTP requests.
     * @return RestClient restClient
     */
    public RestClient getRestClient() {
        return restClient;
    }

    /**
     * Format the given parameter object into string.
     * @param param the object to convert
     * @return String the parameter represented as a String
     */
    public String parameterToString(Object param) {
        if (param == null) {
            return "";
        } else if (param instanceof Date) {
            return formatDate( (Date) param);
        } else if (param instanceof OffsetDateTime) {
            return formatOffsetDateTime((OffsetDateTime) param);
        } else if (param instanceof Collection) {
            StringBuilder b = new StringBuilder();
            for(Object o : (Collection<?>) param) {
                if(b.length() > 0) {
                    b.append(",");
                }
                b.append(String.valueOf(o));
            }
            return b.toString();
        } else {
            return String.valueOf(param);
        }
    }

    /**
    * Converts a parameter to a {@link MultiValueMap} containing Json-serialized values for use in REST requests
    * @param collectionFormat The format to convert to
    * @param name The name of the parameter
    * @param value The parameter's value
    * @return a Map containing the Json-serialized String value(s) of the input parameter
    */
    public MultiValueMap<String, String> parameterToMultiValueMapJson(CollectionFormat collectionFormat, String name, Object value) {
        Collection<?> valueCollection;
        if (value instanceof Collection) {
            valueCollection = (Collection<?>) value;
        } else {
            try {
                return parameterToMultiValueMap(collectionFormat, name, mapper.writeValueAsString(value));
            } catch (JacksonException e) {
                throw exceptionProvider.jacksonException(e);
            }
        }

        List<String> values = new ArrayList<>();
        for(Object o : valueCollection) {
            try {
                values.add(mapper.writeValueAsString(o));
            } catch (JacksonException e) {
                throw exceptionProvider.jacksonException(e);
            }
        }
        return parameterToMultiValueMap(collectionFormat, name, "[" + collectionFormat.collectionToString(values) + "]");
    }

    /**
     * Converts a parameter to a {@link MultiValueMap} for use in REST requests
     * @param collectionFormat The format to convert to
     * @param name The name of the parameter
     * @param value The parameter's value
     * @return a Map containing the String value(s) of the input parameter
     */
    public MultiValueMap<String, String> parameterToMultiValueMap(CollectionFormat collectionFormat, String name, Object value) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (name == null || name.isEmpty() || value == null) {
            return params;
        }

        if(collectionFormat == null) {
            collectionFormat = CollectionFormat.CSV;
        }

        if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> valuesMap = (Map<String, Object>) value;
            for (final Entry<String, Object> entry : valuesMap.entrySet()) {
                params.add(entry.getKey(), parameterToString(entry.getValue()));
            }
            return params;
        }

        Collection<?> valueCollection = null;
        if (value instanceof Collection) {
            valueCollection = (Collection<?>) value;
        } else {
            params.add(name, parameterToString(value));
            return params;
        }

        if (valueCollection.isEmpty()){
            return params;
        }

        if (collectionFormat.equals(CollectionFormat.MULTI)) {
            for (Object item : valueCollection) {
                params.add(name, parameterToString(item));
            }
            return params;
        }

        List<String> values = new ArrayList<>();
        for(Object o : valueCollection) {
            values.add(parameterToString(o));
        }
        params.add(name, collectionFormat.collectionToString(values));

        return params;
    }

    /**
    * Check if the given {@code String} is a JSON MIME.
    * @param mediaType the input MediaType
    * @return boolean true if the MediaType represents JSON, false otherwise
    */
    public boolean isJsonMime(String mediaType) {
        // "* / *" is default to JSON
        if ("*/*".equals(mediaType)) {
            return true;
        }

        try {
            return isJsonMime(MediaType.parseMediaType(mediaType));
        } catch (InvalidMediaTypeException e) {
        }
        return false;
    }

    /**
     * Check if the given MIME is a JSON MIME.
     * JSON MIME examples:
     *     application/json
     *     application/json; charset=UTF8
     *     APPLICATION/JSON
     * @param mediaType the input MediaType
     * @return boolean true if the MediaType represents JSON, false otherwise
     */
    public boolean isJsonMime(MediaType mediaType) {
        return mediaType != null && (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || mediaType.getSubtype().matches("^.*(\\+json|ndjson)[;]?\\s*$"));
    }

    /**
    * Check if the given {@code String} is a Problem JSON MIME (RFC-7807).
    * @param mediaType the input MediaType
    * @return boolean true if the MediaType represents Problem JSON, false otherwise
    */
    public boolean isProblemJsonMime(String mediaType) {
        return "application/problem+json".equalsIgnoreCase(mediaType);
    }

    /**
     * Select the Accept header's value from the given accepts array:
     *     if JSON exists in the given array, use it;
     *     otherwise use all of them (joining into a string)
     *
     * @param accepts The accepts array to select from
     * @return List The list of MediaTypes to use for the Accept header
     */
    public List<MediaType> selectHeaderAccept(String[] accepts) {
        if (accepts.length == 0) {
            return null;
        }
        for (String accept : accepts) {
            MediaType mediaType = MediaType.parseMediaType(accept);
            if (isJsonMime(mediaType) && !isProblemJsonMime(accept)) {
                return Collections.singletonList(mediaType);
            }
        }
        return MediaType.parseMediaTypes(StringUtils.arrayToCommaDelimitedString(accepts));
    }

    /**
     * Select the Content-Type header's value from the given array:
     *     if JSON exists in the given array, use it;
     *     otherwise use the first non-wildcard one of the array.
     *
     * @param contentTypes The Content-Type array to select from
     * @return MediaType The Content-Type header to use. If the given array is empty, null will be returned; if it only contains wildcard media types, JSON will be used.
     */
    public MediaType selectHeaderContentType(String[] contentTypes) {
        if (contentTypes.length == 0) {
            return null;
        }
        for (String contentType : contentTypes) {
            MediaType mediaType = MediaType.parseMediaType(contentType);
            if (isJsonMime(mediaType)) {
                // A wildcard media type (e.g. "*/*" or "application/*") is treated as
                // JSON-compatible but cannot be used as a request Content-Type header,
                // so fall back to concrete JSON in that case.
                return mediaType.isWildcardType() || mediaType.isWildcardSubtype() ? MediaType.APPLICATION_JSON : mediaType;
            }
        }
        // No JSON type found; use the first concrete (non-wildcard) media type instead.
        for (String contentType : contentTypes) {
            MediaType mediaType = MediaType.parseMediaType(contentType);
            if (!mediaType.isWildcardType() && !mediaType.isWildcardSubtype()) {
                return mediaType;
            }
        }
        return MediaType.APPLICATION_JSON;
    }

    /**
    * Select the body to use for the request
    *
    * @param obj the body object
    * @param formParams the form parameters
    * @param contentType the content type of the request
    * @return Object the selected body
    */
    protected Object selectBody(Object obj, MultiValueMap<String, Object> formParams, MediaType contentType) {
        boolean isForm = MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType) || MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType);
        return isForm ? formParams : obj;
    }

    /**
     * Invoke API by sending HTTP request with the given options.
     *
     * @param <T> the return type to use
     * @param path The sub-path of the HTTP URL
     * @param method The request method
     * @param pathParams The path parameters
     * @param queryParams The query parameters
     * @param body The request body object
     * @param headerParams The header parameters
     * @param formParams The form parameters
     * @param accept The request's Accept header
     * @param contentType The request's Content-Type header
     * @param authNames The authentications to apply
     * @param returnType The return type into which to deserialize the response
     * @return The response body in chosen type
     */
    public <T> ResponseSpec invokeAPI(String path, HttpMethod method, Map<String, Object> pathParams, MultiValueMap<String, String> queryParams, Object body, HttpHeaders headerParams, MultiValueMap<String, String> cookieParams, MultiValueMap<String, Object> formParams, List<MediaType> accept, MediaType contentType, String[] authNames, ParameterizedTypeReference<T> returnType) throws RestClientException {
        final RestClient.RequestBodySpec requestBuilder = prepareRequest(path, method, pathParams, queryParams, body, headerParams, cookieParams, formParams, accept, contentType, authNames);
        return requestBuilder.retrieve();
    }

    /**
     * Include queryParams in uriParams taking into account the paramName
     * @param queryParams The query parameters
     * @param uriParams The path parameters
     * return templatized query string
     */
    protected String generateQueryUri(MultiValueMap<String, String> queryParams, Map<String, Object> uriParams) {
        StringBuilder queryBuilder = new StringBuilder();
        queryParams.forEach((name, values) -> {
            if (CollectionUtils.isEmpty(values)) {
                if (queryBuilder.length() != 0) {
                    queryBuilder.append('&');
                }
                queryBuilder.append(name);
            } else {
                int valueItemCounter = 0;
                for (Object value : values) {
                    if (queryBuilder.length() != 0) {
                        queryBuilder.append('&');
                    }
                    queryBuilder.append(name);
                    if (value != null) {
                        String templatizedKey = name + valueItemCounter++;
                        uriParams.put(templatizedKey, value.toString());
                        queryBuilder.append('=').append("{").append(templatizedKey).append("}");
                    }
                }
            }
        });
        return queryBuilder.toString();
    }

    protected RestClient.RequestBodySpec prepareRequest(String path, HttpMethod method, Map<String, Object> pathParams,
        MultiValueMap<String, String> queryParams, Object body, HttpHeaders headerParams,
        MultiValueMap<String, String> cookieParams, MultiValueMap<String, Object> formParams, List<MediaType> accept,
        MediaType contentType, String[] authNames) {
        updateParamsForAuth(authNames, queryParams, headerParams, cookieParams);

        String baseUrl = basePath;
        if (serverIndex != null) {
            if (serverIndex < 0 || serverIndex >= servers.size()) {
                throw new ArrayIndexOutOfBoundsException(String.format(
                    java.util.Locale.ROOT,
                    "Invalid index %d when selecting the host settings. Must be less than %d", serverIndex, servers.size()
                ));
            }
            baseUrl = servers.get(serverIndex).URL(serverVariables);
        }

        final UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(baseUrl)
                    .path(path);

        String finalUri = builder.build(false).toUriString();
        Map<String, Object> uriParams = new HashMap<>();
        uriParams.putAll(pathParams);

        if (queryParams != null && !queryParams.isEmpty()) {
            //Include queryParams in uriParams taking into account the paramName
            String queryUri = generateQueryUri(queryParams, uriParams);
            //Append to finalUri the templatized query string like "?param1={param1Value}&.......
            finalUri += "?" + queryUri;
        }

        final RestClient.RequestBodySpec requestBuilder = restClient.method(method).uri(finalUri, uriParams);

        if (accept != null) {
            requestBuilder.accept(accept.toArray(new MediaType[accept.size()]));
        }
        if(contentType != null) {
            requestBuilder.contentType(contentType);
        }

        addHeadersToRequest(headerParams, requestBuilder);
        addHeadersToRequest(defaultHeaders, requestBuilder);
        addCookiesToRequest(cookieParams, requestBuilder);
        addCookiesToRequest(defaultCookies, requestBuilder);

        if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
            formParams.forEach(
                (k, v) -> {
                    if (v instanceof java.util.ArrayList && !v.isEmpty()) {
                        Object first = v.get(0);
                        if (first != null && first.getClass().isEnum()) {
                            v.set(0, first.toString());
                        }
                    }
            });
        }

        var selectedBody = selectBody(body, formParams, contentType);
        if (selectedBody != null) {
          requestBuilder.body(selectedBody);
        }

        return requestBuilder;
    }

    /**
     * Add headers to the request that is being built
     * @param headers The headers to add
     * @param requestBuilder The current request
     */
    protected void addHeadersToRequest(HttpHeaders headers, RestClient.RequestBodySpec requestBuilder) {
        for (Entry<String, List<String>> entry : headers.headerSet()) {
            List<String> values = entry.getValue();
            for(String value : values) {
                if (value != null) {
                    requestBuilder.header(entry.getKey(), value);
                }
            }
        }
    }

     /**
     * Add cookies to the request that is being built
     *
     * @param cookies        The cookies to add
     * @param requestBuilder The current request
     */
    protected void addCookiesToRequest(MultiValueMap<String, String> cookies, RestClient.RequestBodySpec requestBuilder) {
        if (!cookies.isEmpty()) {
            requestBuilder.header("Cookie", buildCookieHeader(cookies));
        }
    }

    /**
     * Build cookie header. Keeps a single value per cookie (as per <a href="https://tools.ietf.org/html/rfc6265#section-5.3">
     * RFC6265 section 5.3</a>).
     *
     * @param cookies map all cookies
     * @return header string for cookies.
     */
    protected String buildCookieHeader(MultiValueMap<String, String> cookies) {
        final StringBuilder cookieValue = new StringBuilder();
        String delimiter = "";
        for (final Map.Entry<String, List<String>> entry : cookies.entrySet()) {
            final String value = entry.getValue().get(entry.getValue().size() - 1);
            cookieValue.append(String.format(java.util.Locale.ROOT, "%s%s=%s", delimiter, entry.getKey(), value));
            delimiter = "; ";
        }
        return cookieValue.toString();
    }

    /**
     * Update query and header parameters based on authentication settings.
     *
     * @param authNames The authentications to apply
     * @param queryParams The query parameters
     * @param headerParams The header parameters
     * @param cookieParams the cookie parameters
     */
    protected void updateParamsForAuth(String[] authNames, MultiValueMap<String, String> queryParams, HttpHeaders headerParams, MultiValueMap<String, String> cookieParams) {
        for (String authName : authNames) {
            Authentication auth = authentications.get(authName);
            if (auth == null) {
                throw exceptionProvider.undefinedAuthenticationException(authName);
            }
            auth.applyToParams(queryParams, headerParams, cookieParams);
        }
    }

    /**
    * Formats the specified collection path parameter to a string value.
    *
    * @param collectionFormat The collection format of the parameter.
    * @param values The values of the parameter.
    * @return String representation of the parameter
    */
    public String collectionPathParameterToString(CollectionFormat collectionFormat, Collection<?> values) {
        // create the value based on the collection format
        if (CollectionFormat.MULTI.equals(collectionFormat)) {
            // not valid for path params
            return parameterToString(values);
        }

         // collectionFormat is assumed to be "csv" by default
        if(collectionFormat == null) {
            collectionFormat = CollectionFormat.CSV;
        }

        return collectionFormat.collectionToString(values);
    }
}
