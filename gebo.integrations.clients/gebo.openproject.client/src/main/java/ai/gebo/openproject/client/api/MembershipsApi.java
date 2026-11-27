package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.MembershipCollectionModel;
import ai.gebo.openproject.client.model.MembershipFormModel;
import ai.gebo.openproject.client.model.MembershipReadModel;
import ai.gebo.openproject.client.model.MembershipSchemaModel;
import ai.gebo.openproject.client.model.MembershipWriteModel;
import ai.gebo.openproject.client.model.ProjectCollectionModel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.client.RestClientResponseException;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class MembershipsApi {
    private ApiClient apiClient;

    public MembershipsApi() {
        this(new ApiClient());
    }

    public MembershipsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create a membership
     * Creates a new membership applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the newly created membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMembershipRequestCreation(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships", HttpMethod.POST, pathParams, localVarQueryParams, membershipWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a membership
     * Creates a new membership applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the newly created membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipReadModel createMembership(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMembershipRequestCreation(membershipWriteModel).body(localVarReturnType);
    }

    /**
     * Create a membership
     * Creates a new membership applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the newly created membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseEntity&lt;MembershipReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipReadModel> createMembershipWithHttpInfo(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMembershipRequestCreation(membershipWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create a membership
     * Creates a new membership applying the attributes provided in the body.  You can use the form and schema to retrieve the valid attribute values and by that be guided towards successful creation.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the newly created membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMembershipWithResponseSpec(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        return createMembershipRequestCreation(membershipWriteModel);
    }

    /**
     * Delete membership
     * Deletes the membership.
     * <p><b>204</b> - Returned if the membership was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMembershipRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete membership
     * Deletes the membership.
     * <p><b>204</b> - Returned if the membership was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteMembership(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteMembershipRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete membership
     * Deletes the membership.
     * <p><b>204</b> - Returned if the membership was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteMembershipWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMembershipRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete membership
     * Deletes the membership.
     * <p><b>204</b> - Returned if the membership was successfully deleted
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMembershipWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteMembershipRequestCreation(id);
    }

    /**
     * Get a membership
     * Retrieves a membership resource identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members **or** manage members  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Membership id
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMembershipRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a membership
     * Retrieves a membership resource identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members **or** manage members  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Membership id
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipReadModel getMembership(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a membership
     * Retrieves a membership resource identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members **or** manage members  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Membership id
     * @return ResponseEntity&lt;MembershipReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipReadModel> getMembershipWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a membership
     * Retrieves a membership resource identified by the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view members **or** manage members  *Note: A client without sufficient permissions shall not be able to test for the existence of a membership. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Membership id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMembershipWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getMembershipRequestCreation(id);
    }

    /**
     * Schema membership
     * Retrieves the schema for the membership resource object.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** manage members or view memberships on any project
     * @return MembershipSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMembershipSchemaRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/schema", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Schema membership
     * Retrieves the schema for the membership resource object.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** manage members or view memberships on any project
     * @return MembershipSchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipSchemaModel getMembershipSchema() throws RestClientResponseException {
        ParameterizedTypeReference<MembershipSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipSchemaRequestCreation().body(localVarReturnType);
    }

    /**
     * Schema membership
     * Retrieves the schema for the membership resource object.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** manage members or view memberships on any project
     * @return ResponseEntity&lt;MembershipSchemaModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipSchemaModel> getMembershipSchemaWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<MembershipSchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getMembershipSchemaRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Schema membership
     * Retrieves the schema for the membership resource object.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the schema.  **Required permission:** manage members or view memberships on any project
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMembershipSchemaWithResponseSpec() throws RestClientResponseException {
        return getMembershipSchemaRequestCreation();
    }

    /**
     * Available projects for memberships
     * Gets a list of projects in which a membership can be created in. The list contains all projects in which the user issuing the request has the manage members permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * @return ProjectCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableProjectsForMembershipsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ProjectCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/available_projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available projects for memberships
     * Gets a list of projects in which a membership can be created in. The list contains all projects in which the user issuing the request has the manage members permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * @return ProjectCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectCollectionModel listAvailableProjectsForMemberships() throws RestClientResponseException {
        ParameterizedTypeReference<ProjectCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForMembershipsRequestCreation().body(localVarReturnType);
    }

    /**
     * Available projects for memberships
     * Gets a list of projects in which a membership can be created in. The list contains all projects in which the user issuing the request has the manage members permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * @return ResponseEntity&lt;ProjectCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectCollectionModel> listAvailableProjectsForMembershipsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<ProjectCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForMembershipsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Available projects for memberships
     * Gets a list of projects in which a membership can be created in. The list contains all projects in which the user issuing the request has the manage members permissions.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage members
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableProjectsForMembershipsWithResponseSpec() throws RestClientResponseException {
        return listAvailableProjectsForMembershipsRequestCreation();
    }

    /**
     * List memberships
     * Returns a collection of memberships. The client can choose to filter the memberships similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain memberships, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + any_name_attribute: filters memberships based on the name of the principal. All possible name variants   (and also email and login) are searched. + blocked: reduces the result set to all memberships that are temporarily blocked or that are not blocked   temporarily. + group: filters memberships based on the name of a group. The group however is not the principal used for   filtering. Rather, the memberships of the group are used as the filter values. + name: filters memberships based on the name of the principal. Note that only the name is used which depends   on a setting in the OpenProject instance. + principal: filters memberships based on the id of the principal. + project: filters memberships based on the id of the project. + role: filters memberships based on the id of any role assigned to the membership. + status: filters memberships based on the status of the principal. + created_at: filters memberships based on the time the membership was created. + updated_at: filters memberships based on the time the membership was updated last.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key + name: Sort by the name of the principal. Note that this depends on the setting for how the name is to be   displayed at least for users. + email: Sort by the email address of the principal. Groups and principal users, which do not have an email,   are sorted last. + status: Sort by the status of the principal. Groups and principal users, which do not have a status, are   sorted together with the active users. + created_at: Sort by membership creation datetime + updated_at: Sort by the time the membership was updated last
     * @return MembershipCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMembershipsRequestCreation(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List memberships
     * Returns a collection of memberships. The client can choose to filter the memberships similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain memberships, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + any_name_attribute: filters memberships based on the name of the principal. All possible name variants   (and also email and login) are searched. + blocked: reduces the result set to all memberships that are temporarily blocked or that are not blocked   temporarily. + group: filters memberships based on the name of a group. The group however is not the principal used for   filtering. Rather, the memberships of the group are used as the filter values. + name: filters memberships based on the name of the principal. Note that only the name is used which depends   on a setting in the OpenProject instance. + principal: filters memberships based on the id of the principal. + project: filters memberships based on the id of the project. + role: filters memberships based on the id of any role assigned to the membership. + status: filters memberships based on the status of the principal. + created_at: filters memberships based on the time the membership was created. + updated_at: filters memberships based on the time the membership was updated last.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key + name: Sort by the name of the principal. Note that this depends on the setting for how the name is to be   displayed at least for users. + email: Sort by the email address of the principal. Groups and principal users, which do not have an email,   are sorted last. + status: Sort by the status of the principal. Groups and principal users, which do not have a status, are   sorted together with the active users. + created_at: Sort by membership creation datetime + updated_at: Sort by the time the membership was updated last
     * @return MembershipCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipCollectionModel listMemberships(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMembershipsRequestCreation(filters, sortBy).body(localVarReturnType);
    }

    /**
     * List memberships
     * Returns a collection of memberships. The client can choose to filter the memberships similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain memberships, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + any_name_attribute: filters memberships based on the name of the principal. All possible name variants   (and also email and login) are searched. + blocked: reduces the result set to all memberships that are temporarily blocked or that are not blocked   temporarily. + group: filters memberships based on the name of a group. The group however is not the principal used for   filtering. Rather, the memberships of the group are used as the filter values. + name: filters memberships based on the name of the principal. Note that only the name is used which depends   on a setting in the OpenProject instance. + principal: filters memberships based on the id of the principal. + project: filters memberships based on the id of the project. + role: filters memberships based on the id of any role assigned to the membership. + status: filters memberships based on the status of the principal. + created_at: filters memberships based on the time the membership was created. + updated_at: filters memberships based on the time the membership was updated last.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key + name: Sort by the name of the principal. Note that this depends on the setting for how the name is to be   displayed at least for users. + email: Sort by the email address of the principal. Groups and principal users, which do not have an email,   are sorted last. + status: Sort by the status of the principal. Groups and principal users, which do not have a status, are   sorted together with the active users. + created_at: Sort by membership creation datetime + updated_at: Sort by the time the membership was updated last
     * @return ResponseEntity&lt;MembershipCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipCollectionModel> listMembershipsWithHttpInfo(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMembershipsRequestCreation(filters, sortBy).toEntity(localVarReturnType);
    }

    /**
     * List memberships
     * Returns a collection of memberships. The client can choose to filter the memberships similar to how work packages are filtered. In addition to the provided filters, the server will reduce the result set to only contain memberships, for which the requesting client has sufficient permissions (*view_members*, *manage_members*).
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + any_name_attribute: filters memberships based on the name of the principal. All possible name variants   (and also email and login) are searched. + blocked: reduces the result set to all memberships that are temporarily blocked or that are not blocked   temporarily. + group: filters memberships based on the name of a group. The group however is not the principal used for   filtering. Rather, the memberships of the group are used as the filter values. + name: filters memberships based on the name of the principal. Note that only the name is used which depends   on a setting in the OpenProject instance. + principal: filters memberships based on the id of the principal. + project: filters memberships based on the id of the project. + role: filters memberships based on the id of any role assigned to the membership. + status: filters memberships based on the status of the principal. + created_at: filters memberships based on the time the membership was created. + updated_at: filters memberships based on the time the membership was updated last.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key + name: Sort by the name of the principal. Note that this depends on the setting for how the name is to be   displayed at least for users. + email: Sort by the email address of the principal. Groups and principal users, which do not have an email,   are sorted last. + status: Sort by the status of the principal. Groups and principal users, which do not have a status, are   sorted together with the active users. + created_at: Sort by membership creation datetime + updated_at: Sort by the time the membership was updated last
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMembershipsWithResponseSpec(@jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listMembershipsRequestCreation(filters, sortBy);
    }

    /**
     * Update membership
     * Updates the given membership by applying the attributes provided in the body.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the updated membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members in the membership&#39;s project.
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view member  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateMembershipRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateMembership", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, membershipWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update membership
     * Updates the given membership by applying the attributes provided in the body.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the updated membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members in the membership&#39;s project.
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view member  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipReadModel updateMembership(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMembershipRequestCreation(id, membershipWriteModel).body(localVarReturnType);
    }

    /**
     * Update membership
     * Updates the given membership by applying the attributes provided in the body.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the updated membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members in the membership&#39;s project.
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view member  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseEntity&lt;MembershipReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipReadModel> updateMembershipWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateMembershipRequestCreation(id, membershipWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update membership
     * Updates the given membership by applying the attributes provided in the body.  By providing a &#x60;notificationMessage&#x60; within the &#x60;_meta&#x60; block of the payload, the client can include a customized message to the user of the updated membership. In case of a group, the message will be sent to every user belonging to the group.  By including &#x60;{ \&quot;sendNotifications\&quot;: false }&#x60; within the &#x60;_meta&#x60; block of the payload, no notifications is send out at all.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Manage members in the membership&#39;s project.
     * <p><b>404</b> - Returned if the membership does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view member  *Note: A client without sufficient permissions shall not be able to test for the existence of a version. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateMembershipWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        return updateMembershipRequestCreation(id, membershipWriteModel);
    }

    /**
     * Form create membership
     * Requests and validates the creation form for memberships. The request payload, if sent, is validated. The form endpoint itself does not create a membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage memberships in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateMembershipCreateFormRequestCreation(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/form", HttpMethod.POST, pathParams, localVarQueryParams, membershipWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Form create membership
     * Requests and validates the creation form for memberships. The request payload, if sent, is validated. The form endpoint itself does not create a membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage memberships in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipFormModel validateMembershipCreateForm(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateMembershipCreateFormRequestCreation(membershipWriteModel).body(localVarReturnType);
    }

    /**
     * Form create membership
     * Requests and validates the creation form for memberships. The request payload, if sent, is validated. The form endpoint itself does not create a membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage memberships in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseEntity&lt;MembershipFormModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipFormModel> validateMembershipCreateFormWithHttpInfo(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateMembershipCreateFormRequestCreation(membershipWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Form create membership
     * Requests and validates the creation form for memberships. The request payload, if sent, is validated. The form endpoint itself does not create a membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage memberships in any project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateMembershipCreateFormWithResponseSpec(@jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        return validateMembershipCreateFormRequestCreation(membershipWriteModel);
    }

    /**
     * Form update membership
     * Requests and validates the update form for a membership identified by the given id. The request payload, if sent, is validated. The form endpoint itself does not change the membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateMembershipUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateMembershipUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/memberships/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, membershipWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Form update membership
     * Requests and validates the update form for a membership identified by the given id. The request payload, if sent, is validated. The form endpoint itself does not change the membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return MembershipReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public MembershipReadModel validateMembershipUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateMembershipUpdateFormRequestCreation(id, membershipWriteModel).body(localVarReturnType);
    }

    /**
     * Form update membership
     * Requests and validates the update form for a membership identified by the given id. The request payload, if sent, is validated. The form endpoint itself does not change the membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseEntity&lt;MembershipReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MembershipReadModel> validateMembershipUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<MembershipReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateMembershipUpdateFormRequestCreation(id, membershipWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Form update membership
     * Requests and validates the update form for a membership identified by the given id. The request payload, if sent, is validated. The form endpoint itself does not change the membership.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage versions in the version&#39;s project
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Membership id
     * @param membershipWriteModel The membershipWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateMembershipUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable MembershipWriteModel membershipWriteModel) throws RestClientResponseException {
        return validateMembershipUpdateFormRequestCreation(id, membershipWriteModel);
    }
}
