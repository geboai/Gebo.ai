/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.Avatar;
import ai.gebo.jira.cloud.client.model.Avatars;
import ai.gebo.jira.cloud.client.model.SystemAvatars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * AvatarsApi class provides methods to interact with Avatars related operations in Jira.
 * AI generated comments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.AvatarsApi")
public class AvatarsApi {

    // ApiClient instance to handle API calls
    private ApiClient apiClient;

    /**
     * Default constructor that initializes AvatarsApi with a default ApiClient.
     */
    public AvatarsApi() {
        this(new ApiClient());
    }

    /**
     * Constructor to initialize AvatarsApi with a specific ApiClient.
     * 
     * @param apiClient The ApiClient instance to be used by this API.
     */
    //@Autowired
    public AvatarsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns the ApiClient instance used by this API.
     * 
     * @return ApiClient instance
     */
    public ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Sets the ApiClient instance for this API.
     * 
     * @param apiClient The new ApiClient to be set.
     */
    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete avatar
     * Deletes an avatar from a project, issue type or priority.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>403</b> - Returned if the user does not have permission to delete the avatar, the avatar is not deletable.
     * <p><b>404</b> - Returned if the avatar type, associated item ID, or avatar ID is invalid.
     * @param type The avatar type. (required)
     * @param owningObjectId The ID of the item the avatar is associated with. (required)
     * @param id The ID of the avatar. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteAvatar(String type, String owningObjectId, Long id) throws RestClientException {
        deleteAvatarWithHttpInfo(type, owningObjectId, id);
    }

    /**
     * Delete avatar
     * Deletes an avatar from a project, issue type or priority.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is invalid.
     * <p><b>403</b> - Returned if the user does not have permission to delete the avatar, the avatar is not deletable.
     * <p><b>404</b> - Returned if the avatar type, associated item ID, or avatar ID is invalid.
     * @param type The avatar type. (required)
     * @param owningObjectId The ID of the item the avatar is associated with. (required)
     * @param id The ID of the avatar. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteAvatarWithHttpInfo(String type, String owningObjectId, Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling deleteAvatar");
        }
        // verify the required parameter 'owningObjectId' is set
        if (owningObjectId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'owningObjectId' when calling deleteAvatar");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteAvatar");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        uriVariables.put("owningObjectId", owningObjectId);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/universal_avatar/type/{type}/owner/{owningObjectId}/avatar/{id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        // Setting accept and content types
        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Get system avatars by type
     * Returns a list of system avatar details by owner type, where the owner types are issue type, project, user or priority.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>500</b> - Returned if an error occurs while retrieving the list of avatars.
     * @param type The avatar type. (required)
     * @return SystemAvatars
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SystemAvatars getAllSystemAvatars(String type) throws RestClientException {
        return getAllSystemAvatarsWithHttpInfo(type).getBody();
    }

    /**
     * Get system avatars by type
     * Returns a list of system avatar details by owner type, where the owner types are issue type, project, user or priority.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>500</b> - Returned if an error occurs while retrieving the list of avatars.
     * @param type The avatar type. (required)
     * @return ResponseEntity&lt;SystemAvatars&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SystemAvatars> getAllSystemAvatarsWithHttpInfo(String type) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling getAllSystemAvatars");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/avatar/{type}/system").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        // Specify acceptable content types
        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<SystemAvatars> returnType = new ParameterizedTypeReference<SystemAvatars>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Get avatar image by ID
     * Returns a project, issue type or priority avatar image by ID.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  For system avatars, none.  *  For custom project avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the avatar belongs to.  *  For custom issue type avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the issue type is used in.  *  For priority avatars, none.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if an avatar is not found or an avatar matching the requested size is not found.
     * @param type The icon type of the avatar. (required)
     * @param id The ID of the avatar. (required)
     * @param size The size of the avatar image. If not provided the default size is returned. (optional)
     * @param format The format to return the avatar image in. If not provided the original content format is returned. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void getAvatarImageByID(String type, Long id, String size, String format) throws RestClientException {
        getAvatarImageByIDWithHttpInfo(type, id, size, format);
    }

    /**
     * Get avatar image by ID
     * Returns a project, issue type or priority avatar image by ID.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  For system avatars, none.  *  For custom project avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the avatar belongs to.  *  For custom issue type avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the issue type is used in.  *  For priority avatars, none.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if an avatar is not found or an avatar matching the requested size is not found.
     * @param type The icon type of the avatar. (required)
     * @param id The ID of the avatar. (required)
     * @param size The size of the avatar image. If not provided the default size is returned. (optional)
     * @param format The format to return the avatar image in. If not provided the original content format is returned. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getAvatarImageByIDWithHttpInfo(String type, Long id, String size, String format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling getAvatarImageByID");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getAvatarImageByID");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/universal_avatar/view/type/{type}/avatar/{id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        // Specify the accepted and content type
        final String[] accepts = { 
            "*/*", "application/json", "image/png", "image/svg+xml"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Get avatar image by owner
     * Returns the avatar image for a project, issue type or priority.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  For system avatars, none.  *  For custom project avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the avatar belongs to.  *  For custom issue type avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the issue type is used in.  *  For priority avatars, none.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if an avatar is not found or an avatar matching the requested size is not found.
     * @param type The icon type of the avatar. (required)
     * @param entityId The ID of the project or issue type the avatar belongs to. (required)
     * @param size The size of the avatar image. If not provided the default size is returned. (optional)
     * @param format The format to return the avatar image in. If not provided the original content format is returned. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void getAvatarImageByOwner(String type, String entityId, String size, String format) throws RestClientException {
        getAvatarImageByOwnerWithHttpInfo(type, entityId, size, format);
    }

    /**
     * Get avatar image by owner
     * Returns the avatar image for a project, issue type or priority.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  For system avatars, none.  *  For custom project avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the avatar belongs to.  *  For custom issue type avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the issue type is used in.  *  For priority avatars, none.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if an avatar is not found or an avatar matching the requested size is not found.
     * @param type The icon type of the avatar. (required)
     * @param entityId The ID of the project or issue type the avatar belongs to. (required)
     * @param size The size of the avatar image. If not provided the default size is returned. (optional)
     * @param format The format to return the avatar image in. If not provided the original content format is returned. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getAvatarImageByOwnerWithHttpInfo(String type, String entityId, String size, String format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling getAvatarImageByOwner");
        }
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'entityId' when calling getAvatarImageByOwner");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        uriVariables.put("entityId", entityId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/universal_avatar/view/type/{type}/owner/{entityId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        // Specify the accepted and content type
        final String[] accepts = { 
            "*/*", "application/json", "image/png", "image/svg+xml"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Get avatar image by type
     * Returns the default project, issue type or priority avatar image.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if an avatar is not found or an avatar matching the requested size is not found.
     * @param type The icon type of the avatar. (required)
     * @param size The size of the avatar image. If not provided the default size is returned. (optional)
     * @param format The format to return the avatar image in. If not provided the original content format is returned. (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void getAvatarImageByType(String type, String size, String format) throws RestClientException {
        getAvatarImageByTypeWithHttpInfo(type, size, format);
    }

    /**
     * Get avatar image by type
     * Returns the default project, issue type or priority avatar image.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * <p><b>404</b> - Returned if an avatar is not found or an avatar matching the requested size is not found.
     * @param type The icon type of the avatar. (required)
     * @param size The size of the avatar image. If not provided the default size is returned. (optional)
     * @param format The format to return the avatar image in. If not provided the original content format is returned. (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getAvatarImageByTypeWithHttpInfo(String type, String size, String format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling getAvatarImageByType");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/universal_avatar/view/type/{type}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        // Specify the accepted and content type
        final String[] accepts = { 
            "*/*", "application/json", "image/png", "image/svg+xml"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Get avatars
     * Returns the system and custom avatars for a project, issue type or priority.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  for custom project avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the avatar belongs to.  *  for custom issue type avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the issue type is used in.  *  for system avatars, none.  *  for priority avatars, none.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the avatar type is invalid, the associated item ID is missing, or the item is not found.
     * @param type The avatar type. (required)
     * @param entityId The ID of the item the avatar is associated with. (required)
     * @return Avatars
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Avatars getAvatars(String type, String entityId) throws RestClientException {
        return getAvatarsWithHttpInfo(type, entityId).getBody();
    }

    /**
     * Get avatars
     * Returns the system and custom avatars for a project, issue type or priority.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  for custom project avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the avatar belongs to.  *  for custom issue type avatars, *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for at least one project the issue type is used in.  *  for system avatars, none.  *  for priority avatars, none.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>404</b> - Returned if the avatar type is invalid, the associated item ID is missing, or the item is not found.
     * @param type The avatar type. (required)
     * @param entityId The ID of the item the avatar is associated with. (required)
     * @return ResponseEntity&lt;Avatars&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Avatars> getAvatarsWithHttpInfo(String type, String entityId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling getAvatars");
        }
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'entityId' when calling getAvatars");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        uriVariables.put("entityId", entityId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/universal_avatar/type/{type}/owner/{entityId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        // Specify the accepted content type
        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Avatars> returnType = new ParameterizedTypeReference<Avatars>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }

    /**
     * Load avatar
     * Loads a custom avatar for a project, issue type or priority.  Specify the avatar's local file location in the body of the request. Also, include the following headers:   *  `X-Atlassian-Token: no-check` To prevent XSRF protection blocking the request, for more information see [Special Headers](#special-request-headers).  *  `Content-Type: image/image type` Valid image types are JPEG, GIF, or PNG.  For example:   `curl --request POST `  `--user email@example.com:<api_token> `  `--header 'X-Atlassian-Token: no-check' `  `--header 'Content-Type: image/< image_type>' `  `--data-binary "<@/path/to/file/with/your/avatar>" `  `--url 'https://your-domain.atlassian.net/rest/api/3/universal_avatar/type/{type}/owner/{entityId}'`  The avatar is cropped to a square. If no crop parameters are specified, the square originates at the top left of the image. The length of the square's sides is set to the smaller of the height or width of the image.  The cropped image is then used to create avatars of 16x16, 24x24, 32x32, and 48x48 in size.  After creating the avatar use:   *  [Update issue type](#api-rest-api-3-issuetype-id-put) to set it as the issue type's displayed avatar.  *  [Set project avatar](#api-rest-api-3-project-projectIdOrKey-avatar-put) to set it as the project's displayed avatar.  *  [Update priority](#api-rest-api-3-priority-id-put) to set it as the priority's displayed avatar.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  an image isn't included in the request.  *  the image type is unsupported.  *  the crop parameters extend the crop area beyond the edge of the image.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permissions.
     * <p><b>404</b> - Returned if the avatar type is invalid, the associated item ID is missing, or the item is not found.
     * @param body  (required)
     * @param size The length of each side of the crop region. (required)
     * @param type The avatar type. (required)
     * @param entityId The ID of the item the avatar is associated with. (required)
     * @param x The X coordinate of the top-left corner of the crop region. (optional, default to 0)
     * @param y The Y coordinate of the top-left corner of the crop region. (optional, default to 0)
     * @return Avatar
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Avatar storeAvatar(Object body, Integer size, String type, String entityId, Integer x, Integer y) throws RestClientException {
        return storeAvatarWithHttpInfo(body, size, type, entityId, x, y).getBody();
    }

    /**
     * Load avatar
     * Loads a custom avatar for a project, issue type or priority.  Specify the avatar's local file location in the body of the request. Also, include the following headers:   *  `X-Atlassian-Token: no-check` To prevent XSRF protection blocking the request, for more information see [Special Headers](#special-request-headers).  *  `Content-Type: image/image type` Valid image types are JPEG, GIF, or PNG.  For example:   `curl --request POST `  `--user email@example.com:<api_token> `  `--header 'X-Atlassian-Token: no-check' `  `--header 'Content-Type: image/< image_type>' `  `--data-binary "<@/path/to/file/with/your/avatar>" `  `--url 'https://your-domain.atlassian.net/rest/api/3/universal_avatar/type/{type}/owner/{entityId}'`  The avatar is cropped to a square. If no crop parameters are specified, the square originates at the top left of the image. The length of the square's sides is set to the smaller of the height or width of the image.  The cropped image is then used to create avatars of 16x16, 24x24, 32x32, and 48x48 in size.  After creating the avatar use:   *  [Update issue type](#api-rest-api-3-issuetype-id-put) to set it as the issue type's displayed avatar.  *  [Set project avatar](#api-rest-api-3-project-projectIdOrKey-avatar-put) to set it as the project's displayed avatar.  *  [Update priority](#api-rest-api-3-priority-id-put) to set it as the priority's displayed avatar.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if:   *  an image isn't included in the request.  *  the image type is unsupported.  *  the crop parameters extend the crop area beyond the edge of the image.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permissions.
     * <p><b>404</b> - Returned if the avatar type is invalid, the associated item ID is missing, or the item is not found.
     * @param body  (required)
     * @param size The length of each side of the crop region. (required)
     * @param type The avatar type. (required)
     * @param entityId The ID of the item the avatar is associated with. (required)
     * @param x The X coordinate of the top-left corner of the crop region. (optional, default to 0)
     * @param y The Y coordinate of the top-left corner of the crop region. (optional, default to 0)
     * @return ResponseEntity&lt;Avatar&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Avatar> storeAvatarWithHttpInfo(Object body, Integer size, String type, String entityId, Integer x, Integer y) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling storeAvatar");
        }
        // verify the required parameter 'size' is set
        if (size == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'size' when calling storeAvatar");
        }
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'type' when calling storeAvatar");
        }
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'entityId' when calling storeAvatar");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("type", type);
        uriVariables.put("entityId", entityId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/universal_avatar/type/{type}/owner/{entityId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "x", x));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "y", y));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));

        // Specify accepted and content types
        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "*/*"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Avatar> returnType = new ParameterizedTypeReference<Avatar>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}