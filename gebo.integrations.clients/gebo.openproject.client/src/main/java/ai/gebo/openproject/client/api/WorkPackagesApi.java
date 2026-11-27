package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.AITextActionCollectionModel;
import ai.gebo.openproject.client.model.ActivityCommentWriteModel;
import ai.gebo.openproject.client.model.AddWatcherRequest;
import ai.gebo.openproject.client.model.AttachmentUploadModel;
import ai.gebo.openproject.client.model.AvailableAssigneesModel;
import ai.gebo.openproject.client.model.CreateWorkPackageReminderRequest;
import ai.gebo.openproject.client.model.EmojiReactionsModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import java.io.File;
import ai.gebo.openproject.client.model.FileLinkCollectionReadModel;
import ai.gebo.openproject.client.model.FileLinkCollectionWriteModel;
import ai.gebo.openproject.client.model.ListReminders200Response;
import ai.gebo.openproject.client.model.ReminderModel;
import ai.gebo.openproject.client.model.RepositoryBranchCollectionModel;
import ai.gebo.openproject.client.model.RepositoryCommitCollectionModel;
import ai.gebo.openproject.client.model.RepositoryIssueCollectionModel;
import ai.gebo.openproject.client.model.RepositoryMergeRequestCollectionModel;
import ai.gebo.openproject.client.model.WatchersModel;
import ai.gebo.openproject.client.model.WorkPackageFormModel;
import ai.gebo.openproject.client.model.WorkPackageModel;
import ai.gebo.openproject.client.model.WorkPackagePatchModel;
import ai.gebo.openproject.client.model.WorkPackageWriteModel;
import ai.gebo.openproject.client.model.WorkPackagesModel;

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
public class WorkPackagesApi {
    private ApiClient apiClient;

    public WorkPackagesApi() {
        this(new ApiClient());
    }

    public WorkPackagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add watcher
     * Adds a watcher to the specified work package.  The request is expected to contain a single JSON object, that contains a link object under the &#x60;user&#x60; key.  The response will be user added as watcher. In case the user was already watching the work package an &#x60;HTTP 200&#x60; is returned, an &#x60;HTTP 201&#x60; if the user was added as a new watcher.
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.  For example:  * The request did not contain a single JSON object  * The JSON object did not contain the key &#x60;user&#x60;  * The value of &#x60;users&#x60; was not a link object
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permissions:**  * view work package (for self)  * add work package watchers (for other users)  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to specify a link to a resource that is not a user (&#x60;ResourceTypeMismatch&#x60;)  * the user specified is not allowed to watch that work package (&#x60;PropertyConstraintViolation&#x60;)  * the user specified does not exist (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Work package id
     * @param addWatcherRequest The addWatcherRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addWatcherRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable AddWatcherRequest addWatcherRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling addWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/watchers", HttpMethod.POST, pathParams, localVarQueryParams, addWatcherRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add watcher
     * Adds a watcher to the specified work package.  The request is expected to contain a single JSON object, that contains a link object under the &#x60;user&#x60; key.  The response will be user added as watcher. In case the user was already watching the work package an &#x60;HTTP 200&#x60; is returned, an &#x60;HTTP 201&#x60; if the user was added as a new watcher.
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.  For example:  * The request did not contain a single JSON object  * The JSON object did not contain the key &#x60;user&#x60;  * The value of &#x60;users&#x60; was not a link object
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permissions:**  * view work package (for self)  * add work package watchers (for other users)  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to specify a link to a resource that is not a user (&#x60;ResourceTypeMismatch&#x60;)  * the user specified is not allowed to watch that work package (&#x60;PropertyConstraintViolation&#x60;)  * the user specified does not exist (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Work package id
     * @param addWatcherRequest The addWatcherRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void addWatcher(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable AddWatcherRequest addWatcherRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        addWatcherRequestCreation(id, addWatcherRequest).body(localVarReturnType);
    }

    /**
     * Add watcher
     * Adds a watcher to the specified work package.  The request is expected to contain a single JSON object, that contains a link object under the &#x60;user&#x60; key.  The response will be user added as watcher. In case the user was already watching the work package an &#x60;HTTP 200&#x60; is returned, an &#x60;HTTP 201&#x60; if the user was added as a new watcher.
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.  For example:  * The request did not contain a single JSON object  * The JSON object did not contain the key &#x60;user&#x60;  * The value of &#x60;users&#x60; was not a link object
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permissions:**  * view work package (for self)  * add work package watchers (for other users)  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to specify a link to a resource that is not a user (&#x60;ResourceTypeMismatch&#x60;)  * the user specified is not allowed to watch that work package (&#x60;PropertyConstraintViolation&#x60;)  * the user specified does not exist (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Work package id
     * @param addWatcherRequest The addWatcherRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> addWatcherWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable AddWatcherRequest addWatcherRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addWatcherRequestCreation(id, addWatcherRequest).toEntity(localVarReturnType);
    }

    /**
     * Add watcher
     * Adds a watcher to the specified work package.  The request is expected to contain a single JSON object, that contains a link object under the &#x60;user&#x60; key.  The response will be user added as watcher. In case the user was already watching the work package an &#x60;HTTP 200&#x60; is returned, an &#x60;HTTP 201&#x60; if the user was added as a new watcher.
     * <p><b>200</b> - OK
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.  For example:  * The request did not contain a single JSON object  * The JSON object did not contain the key &#x60;user&#x60;  * The value of &#x60;users&#x60; was not a link object
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permissions:**  * view work package (for self)  * add work package watchers (for other users)  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to specify a link to a resource that is not a user (&#x60;ResourceTypeMismatch&#x60;)  * the user specified is not allowed to watch that work package (&#x60;PropertyConstraintViolation&#x60;)  * the user specified does not exist (&#x60;PropertyConstraintViolation&#x60;)
     * @param id Work package id
     * @param addWatcherRequest The addWatcherRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addWatcherWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable AddWatcherRequest addWatcherRequest) throws RestClientResponseException {
        return addWatcherRequestCreation(id, addWatcherRequest);
    }

    /**
     * Create work package in project
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#create-work-package-in-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createProjectWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createProjectWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notify", notify));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/work_packages", HttpMethod.POST, pathParams, localVarQueryParams, workPackageModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create work package in project
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#create-work-package-in-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public WorkPackageModel createProjectWorkPackage(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectWorkPackageRequestCreation(id, notify, workPackageModel).body(localVarReturnType);
    }

    /**
     * Create work package in project
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#create-work-package-in-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return ResponseEntity&lt;WorkPackageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<WorkPackageModel> createProjectWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createProjectWorkPackageRequestCreation(id, notify, workPackageModel).toEntity(localVarReturnType);
    }

    /**
     * Create work package in project
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#create-work-package-in-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec createProjectWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        return createProjectWorkPackageRequestCreation(id, notify, workPackageModel);
    }

    /**
     * Create Work Package
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  A project link must be set when creating work packages through this route.  When setting start date, finish date, and duration together, their correctness will be checked and a 422 error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageRequestCreation(@jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notify", notify));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages", HttpMethod.POST, pathParams, localVarQueryParams, workPackageModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create Work Package
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  A project link must be set when creating work packages through this route.  When setting start date, finish date, and duration together, their correctness will be checked and a 422 error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageModel createWorkPackage(@jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageRequestCreation(notify, workPackageModel).body(localVarReturnType);
    }

    /**
     * Create Work Package
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  A project link must be set when creating work packages through this route.  When setting start date, finish date, and duration together, their correctness will be checked and a 422 error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return ResponseEntity&lt;WorkPackageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageModel> createWorkPackageWithHttpInfo(@jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageRequestCreation(notify, workPackageModel).toEntity(localVarReturnType);
    }

    /**
     * Create Work Package
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.  A project link must be set when creating work packages through this route.  When setting start date, finish date, and duration together, their correctness will be checked and a 422 error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageWithResponseSpec(@jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        return createWorkPackageRequestCreation(notify, workPackageModel);
    }

    /**
     * Comment work package
     * Creates an activity for the selected work package and, on success, returns the updated activity.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create journals  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageActivityRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkPackageActivity", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notify", notify));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/activities", HttpMethod.POST, pathParams, localVarQueryParams, activityCommentWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Comment work package
     * Creates an activity for the selected work package and, on success, returns the updated activity.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create journals  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void createWorkPackageActivity(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        createWorkPackageActivityRequestCreation(id, notify, activityCommentWriteModel).body(localVarReturnType);
    }

    /**
     * Comment work package
     * Creates an activity for the selected work package and, on success, returns the updated activity.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create journals  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> createWorkPackageActivityWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageActivityRequestCreation(id, notify, activityCommentWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Comment work package
     * Creates an activity for the selected work package and, on success, returns the updated activity.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** create journals  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param activityCommentWriteModel The activityCommentWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageActivityWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable ActivityCommentWriteModel activityCommentWriteModel) throws RestClientResponseException {
        return createWorkPackageActivityRequestCreation(id, notify, activityCommentWriteModel);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageFileLinkRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkPackageFileLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/file_links", HttpMethod.POST, pathParams, localVarQueryParams, fileLinkCollectionWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public FileLinkCollectionReadModel createWorkPackageFileLink(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageFileLinkRequestCreation(id, fileLinkCollectionWriteModel).body(localVarReturnType);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return ResponseEntity&lt;FileLinkCollectionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FileLinkCollectionReadModel> createWorkPackageFileLinkWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageFileLinkRequestCreation(id, fileLinkCollectionWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageFileLinkWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        return createWorkPackageFileLinkRequestCreation(id, fileLinkCollectionWriteModel);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ReminderModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageReminderRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkPackageReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'createWorkPackageReminderRequest' is set
        if (createWorkPackageReminderRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'createWorkPackageReminderRequest' when calling createWorkPackageReminder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/reminders", HttpMethod.POST, pathParams, localVarQueryParams, createWorkPackageReminderRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ReminderModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReminderModel createWorkPackageReminder(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageReminderRequestCreation(id, createWorkPackageReminderRequest).body(localVarReturnType);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ResponseEntity&lt;ReminderModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReminderModel> createWorkPackageReminderWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        ParameterizedTypeReference<ReminderModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageReminderRequestCreation(id, createWorkPackageReminderRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a work package reminder
     * Creates a new reminder for the specified work package.  **Note:** A user can only have one **active** reminder at a time for a given work package.
     * <p><b>201</b> - Reminder created successfully
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>409</b> - Returned if the user already has an active reminder for this work package.  **Error message**: You can only set one reminder at a time for a work package. Please delete or update the existing reminder.
     * @param id Work package id
     * @param createWorkPackageReminderRequest The createWorkPackageReminderRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageReminderWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull CreateWorkPackageReminderRequest createWorkPackageReminderRequest) throws RestClientResponseException {
        return createWorkPackageReminderRequestCreation(id, createWorkPackageReminderRequest);
    }

    /**
     * Create work package in workspace
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkspaceWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkspaceWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notify", notify));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/work_packages", HttpMethod.POST, pathParams, localVarQueryParams, workPackageModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create work package in workspace
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageModel createWorkspaceWorkPackage(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkspaceWorkPackageRequestCreation(id, notify, workPackageModel).body(localVarReturnType);
    }

    /**
     * Create work package in workspace
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return ResponseEntity&lt;WorkPackageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageModel> createWorkspaceWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkspaceWorkPackageRequestCreation(id, notify, workPackageModel).toEntity(localVarReturnType);
    }

    /**
     * Create work package in workspace
     * When calling this endpoint the client provides a single object, containing at least the properties and links that are required, in the body. The required fields of a WorkPackage can be found in its schema, which is embedded in the respective form. Note that it is only allowed to provide properties or links supporting the write operation.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permissions:** view workspace  *Note: A client without sufficient permissions shall not be able to test for the existence of a workspace. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * the client tries to write a read-only property  * a constraint for a property was violated  * a property was provided in an unreadable format
     * @param id Project id
     * @param notify Indicates whether change notifications (e.g. via E-Mail) should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackageModel The workPackageModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkspaceWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackageModel workPackageModel) throws RestClientResponseException {
        return createWorkspaceWorkPackageRequestCreation(id, notify, workPackageModel);
    }

    /**
     * Delete Work Package
     * Deletes the work package, as well as:  - all associated time entries - its hierarchy of child work packages
     * <p><b>204</b> - Returned if the work package was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/work_packages/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Work Package
     * Deletes the work package, as well as:  - all associated time entries - its hierarchy of child work packages
     * <p><b>204</b> - Returned if the work package was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteWorkPackage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteWorkPackageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete Work Package
     * Deletes the work package, as well as:  - all associated time entries - its hierarchy of child work packages
     * <p><b>204</b> - Returned if the work package was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteWorkPackageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete Work Package
     * Deletes the work package, as well as:  - all associated time entries - its hierarchy of child work packages
     * <p><b>204</b> - Returned if the work package was deleted successfully.  Note that the response body is empty as of now. In future versions of the API a body *might* be returned along with an appropriate HTTP status.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteWorkPackageRequestCreation(id);
    }

    /**
     * Get work packages of project
     * Returns the collection of work packages that are related to the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#get-work-packages-of-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return WorkPackagesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectWorkPackageCollectionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProjectWorkPackageCollection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/work_packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get work packages of project
     * Returns the collection of work packages that are related to the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#get-work-packages-of-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return WorkPackagesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public WorkPackagesModel getProjectWorkPackageCollection(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectWorkPackageCollectionRequestCreation(id, offset, pageSize, filters, sortBy, groupBy, showSums, select).body(localVarReturnType);
    }

    /**
     * Get work packages of project
     * Returns the collection of work packages that are related to the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#get-work-packages-of-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;WorkPackagesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<WorkPackagesModel> getProjectWorkPackageCollectionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectWorkPackageCollectionRequestCreation(id, offset, pageSize, filters, sortBy, groupBy, showSums, select).toEntity(localVarReturnType);
    }

    /**
     * Get work packages of project
     * Returns the collection of work packages that are related to the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#get-work-packages-of-workspace)
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec getProjectWorkPackageCollectionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return getProjectWorkPackageCollectionRequestCreation(id, offset, pageSize, filters, sortBy, groupBy, showSums, select);
    }

    /**
     * View Work Package
     * Returns the specified work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons) of the work-package attributes, you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timestamps", timestamps));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Work Package
     * Returns the specified work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons) of the work-package attributes, you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageModel getWorkPackage(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkPackageRequestCreation(id, timestamps).body(localVarReturnType);
    }

    /**
     * View Work Package
     * Returns the specified work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons) of the work-package attributes, you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return ResponseEntity&lt;WorkPackageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageModel> getWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkPackageRequestCreation(id, timestamps).toEntity(localVarReturnType);
    }

    /**
     * View Work Package
     * Returns the specified work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons) of the work-package attributes, you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        return getWorkPackageRequestCreation(id, timestamps);
    }

    /**
     * Export a work package as PDF
     * Renders a single work package as a PDF document and returns it as a file download.  The document is rendered from one of the PDF export templates that an administrator enabled for the type of the work package:  | Template     | Content                                                                                      | |:------------:|----------------------------------------------------------------------------------------------| | &#x60;attributes&#x60; | The attributes and the description of the work package.                                       | | &#x60;contract&#x60;   | The description of the work package, rendered as a document with a cover, headers and footers. | | &#x60;artefact&#x60;   | The attributes and the description of the work package, together with the attributes, the phases and the budgets of its project. |  Every option left out of the request falls back to the value an administrator stored for that template on the type, which is the same value the *Generate PDF* dialog offers in the user interface.  **Required permission:** export work packages
     * <p><b>200</b> - Returned if the request was successful. The body is the PDF document, the &#x60;Content-Disposition&#x60; header carries the file name it is to be saved under.
     * <p><b>400</b> - Returned if the requested template does not exist, or if it is not enabled for the type of the work package.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** export work packages
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @param template The template to render. Defaults to the first template enabled for the type of the work package.
     * @param footerText The text printed in the footer of every page. Only used by the &#x60;attributes&#x60; template.
     * @param pageOrientation The orientation of the pages. Only used by the &#x60;attributes&#x60; template.
     * @param headerTextRight The text printed on the right hand side of the header. Only used by the &#x60;contract&#x60; template.
     * @param footerTextCenter The text printed in the center of the footer. Only used by the &#x60;contract&#x60; template.
     * @param toc Whether a table of contents is rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeLifecycle Whether the phases of the project are rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeBudget Whether the budgets of the project are rendered. Only used by the &#x60;artefact&#x60; template and only applied if the budgets module is active and visible to the user.
     * @param hyphenation Whether long text is hyphenated.
     * @param hyphenationLanguage The language the hyphenation rules are taken from, as an ISO 639-1 code. Defaults to the language of the current user. Only applied if &#x60;hyphenation&#x60; is enabled.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWorkPackagePdfRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String template, @jakarta.annotation.Nullable String footerText, @jakarta.annotation.Nullable String pageOrientation, @jakarta.annotation.Nullable String headerTextRight, @jakarta.annotation.Nullable String footerTextCenter, @jakarta.annotation.Nullable Boolean toc, @jakarta.annotation.Nullable Boolean includeLifecycle, @jakarta.annotation.Nullable Boolean includeBudget, @jakarta.annotation.Nullable Boolean hyphenation, @jakarta.annotation.Nullable String hyphenationLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getWorkPackagePdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "template", template));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "footerText", footerText));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageOrientation", pageOrientation));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "headerTextRight", headerTextRight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "footerTextCenter", footerTextCenter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "toc", toc));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "includeLifecycle", includeLifecycle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "includeBudget", includeBudget));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "hyphenation", hyphenation));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "hyphenationLanguage", hyphenationLanguage));

        final String[] localVarAccepts = { 
            "application/pdf", "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/pdf", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Export a work package as PDF
     * Renders a single work package as a PDF document and returns it as a file download.  The document is rendered from one of the PDF export templates that an administrator enabled for the type of the work package:  | Template     | Content                                                                                      | |:------------:|----------------------------------------------------------------------------------------------| | &#x60;attributes&#x60; | The attributes and the description of the work package.                                       | | &#x60;contract&#x60;   | The description of the work package, rendered as a document with a cover, headers and footers. | | &#x60;artefact&#x60;   | The attributes and the description of the work package, together with the attributes, the phases and the budgets of its project. |  Every option left out of the request falls back to the value an administrator stored for that template on the type, which is the same value the *Generate PDF* dialog offers in the user interface.  **Required permission:** export work packages
     * <p><b>200</b> - Returned if the request was successful. The body is the PDF document, the &#x60;Content-Disposition&#x60; header carries the file name it is to be saved under.
     * <p><b>400</b> - Returned if the requested template does not exist, or if it is not enabled for the type of the work package.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** export work packages
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @param template The template to render. Defaults to the first template enabled for the type of the work package.
     * @param footerText The text printed in the footer of every page. Only used by the &#x60;attributes&#x60; template.
     * @param pageOrientation The orientation of the pages. Only used by the &#x60;attributes&#x60; template.
     * @param headerTextRight The text printed on the right hand side of the header. Only used by the &#x60;contract&#x60; template.
     * @param footerTextCenter The text printed in the center of the footer. Only used by the &#x60;contract&#x60; template.
     * @param toc Whether a table of contents is rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeLifecycle Whether the phases of the project are rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeBudget Whether the budgets of the project are rendered. Only used by the &#x60;artefact&#x60; template and only applied if the budgets module is active and visible to the user.
     * @param hyphenation Whether long text is hyphenated.
     * @param hyphenationLanguage The language the hyphenation rules are taken from, as an ISO 639-1 code. Defaults to the language of the current user. Only applied if &#x60;hyphenation&#x60; is enabled.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getWorkPackagePdf(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String template, @jakarta.annotation.Nullable String footerText, @jakarta.annotation.Nullable String pageOrientation, @jakarta.annotation.Nullable String headerTextRight, @jakarta.annotation.Nullable String footerTextCenter, @jakarta.annotation.Nullable Boolean toc, @jakarta.annotation.Nullable Boolean includeLifecycle, @jakarta.annotation.Nullable Boolean includeBudget, @jakarta.annotation.Nullable Boolean hyphenation, @jakarta.annotation.Nullable String hyphenationLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkPackagePdfRequestCreation(id, template, footerText, pageOrientation, headerTextRight, footerTextCenter, toc, includeLifecycle, includeBudget, hyphenation, hyphenationLanguage).body(localVarReturnType);
    }

    /**
     * Export a work package as PDF
     * Renders a single work package as a PDF document and returns it as a file download.  The document is rendered from one of the PDF export templates that an administrator enabled for the type of the work package:  | Template     | Content                                                                                      | |:------------:|----------------------------------------------------------------------------------------------| | &#x60;attributes&#x60; | The attributes and the description of the work package.                                       | | &#x60;contract&#x60;   | The description of the work package, rendered as a document with a cover, headers and footers. | | &#x60;artefact&#x60;   | The attributes and the description of the work package, together with the attributes, the phases and the budgets of its project. |  Every option left out of the request falls back to the value an administrator stored for that template on the type, which is the same value the *Generate PDF* dialog offers in the user interface.  **Required permission:** export work packages
     * <p><b>200</b> - Returned if the request was successful. The body is the PDF document, the &#x60;Content-Disposition&#x60; header carries the file name it is to be saved under.
     * <p><b>400</b> - Returned if the requested template does not exist, or if it is not enabled for the type of the work package.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** export work packages
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @param template The template to render. Defaults to the first template enabled for the type of the work package.
     * @param footerText The text printed in the footer of every page. Only used by the &#x60;attributes&#x60; template.
     * @param pageOrientation The orientation of the pages. Only used by the &#x60;attributes&#x60; template.
     * @param headerTextRight The text printed on the right hand side of the header. Only used by the &#x60;contract&#x60; template.
     * @param footerTextCenter The text printed in the center of the footer. Only used by the &#x60;contract&#x60; template.
     * @param toc Whether a table of contents is rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeLifecycle Whether the phases of the project are rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeBudget Whether the budgets of the project are rendered. Only used by the &#x60;artefact&#x60; template and only applied if the budgets module is active and visible to the user.
     * @param hyphenation Whether long text is hyphenated.
     * @param hyphenationLanguage The language the hyphenation rules are taken from, as an ISO 639-1 code. Defaults to the language of the current user. Only applied if &#x60;hyphenation&#x60; is enabled.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getWorkPackagePdfWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String template, @jakarta.annotation.Nullable String footerText, @jakarta.annotation.Nullable String pageOrientation, @jakarta.annotation.Nullable String headerTextRight, @jakarta.annotation.Nullable String footerTextCenter, @jakarta.annotation.Nullable Boolean toc, @jakarta.annotation.Nullable Boolean includeLifecycle, @jakarta.annotation.Nullable Boolean includeBudget, @jakarta.annotation.Nullable Boolean hyphenation, @jakarta.annotation.Nullable String hyphenationLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkPackagePdfRequestCreation(id, template, footerText, pageOrientation, headerTextRight, footerTextCenter, toc, includeLifecycle, includeBudget, hyphenation, hyphenationLanguage).toEntity(localVarReturnType);
    }

    /**
     * Export a work package as PDF
     * Renders a single work package as a PDF document and returns it as a file download.  The document is rendered from one of the PDF export templates that an administrator enabled for the type of the work package:  | Template     | Content                                                                                      | |:------------:|----------------------------------------------------------------------------------------------| | &#x60;attributes&#x60; | The attributes and the description of the work package.                                       | | &#x60;contract&#x60;   | The description of the work package, rendered as a document with a cover, headers and footers. | | &#x60;artefact&#x60;   | The attributes and the description of the work package, together with the attributes, the phases and the budgets of its project. |  Every option left out of the request falls back to the value an administrator stored for that template on the type, which is the same value the *Generate PDF* dialog offers in the user interface.  **Required permission:** export work packages
     * <p><b>200</b> - Returned if the request was successful. The body is the PDF document, the &#x60;Content-Disposition&#x60; header carries the file name it is to be saved under.
     * <p><b>400</b> - Returned if the requested template does not exist, or if it is not enabled for the type of the work package.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** export work packages
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @param template The template to render. Defaults to the first template enabled for the type of the work package.
     * @param footerText The text printed in the footer of every page. Only used by the &#x60;attributes&#x60; template.
     * @param pageOrientation The orientation of the pages. Only used by the &#x60;attributes&#x60; template.
     * @param headerTextRight The text printed on the right hand side of the header. Only used by the &#x60;contract&#x60; template.
     * @param footerTextCenter The text printed in the center of the footer. Only used by the &#x60;contract&#x60; template.
     * @param toc Whether a table of contents is rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeLifecycle Whether the phases of the project are rendered. Only used by the &#x60;artefact&#x60; template.
     * @param includeBudget Whether the budgets of the project are rendered. Only used by the &#x60;artefact&#x60; template and only applied if the budgets module is active and visible to the user.
     * @param hyphenation Whether long text is hyphenated.
     * @param hyphenationLanguage The language the hyphenation rules are taken from, as an ISO 639-1 code. Defaults to the language of the current user. Only applied if &#x60;hyphenation&#x60; is enabled.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWorkPackagePdfWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String template, @jakarta.annotation.Nullable String footerText, @jakarta.annotation.Nullable String pageOrientation, @jakarta.annotation.Nullable String headerTextRight, @jakarta.annotation.Nullable String footerTextCenter, @jakarta.annotation.Nullable Boolean toc, @jakarta.annotation.Nullable Boolean includeLifecycle, @jakarta.annotation.Nullable Boolean includeBudget, @jakarta.annotation.Nullable Boolean hyphenation, @jakarta.annotation.Nullable String hyphenationLanguage) throws RestClientResponseException {
        return getWorkPackagePdfRequestCreation(id, template, footerText, pageOrientation, headerTextRight, footerTextCenter, toc, includeLifecycle, includeBudget, hyphenation, hyphenationLanguage);
    }

    /**
     * View Work Package Schema
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on the project where this schema is used)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param identifier Identifier of the schema
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWorkPackageSchemaRequestCreation(@jakarta.annotation.Nonnull String identifier) throws RestClientResponseException {
        // verify the required parameter 'identifier' is set
        if (identifier == null) {
            throw new RestClientResponseException("Missing the required parameter 'identifier' when calling getWorkPackageSchema", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("identifier", identifier);

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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/schemas/{identifier}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Work Package Schema
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on the project where this schema is used)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param identifier Identifier of the schema
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getWorkPackageSchema(@jakarta.annotation.Nonnull String identifier) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getWorkPackageSchemaRequestCreation(identifier).body(localVarReturnType);
    }

    /**
     * View Work Package Schema
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on the project where this schema is used)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param identifier Identifier of the schema
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getWorkPackageSchemaWithHttpInfo(@jakarta.annotation.Nonnull String identifier) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkPackageSchemaRequestCreation(identifier).toEntity(localVarReturnType);
    }

    /**
     * View Work Package Schema
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages (on the project where this schema is used)  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param identifier Identifier of the schema
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWorkPackageSchemaWithResponseSpec(@jakarta.annotation.Nonnull String identifier) throws RestClientResponseException {
        return getWorkPackageSchemaRequestCreation(identifier);
    }

    /**
     * Get work packages of workspace
     * Returns the collection of work packages that are related to the given workspace.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return WorkPackagesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWorkspaceWorkPackageCollectionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getWorkspaceWorkPackageCollection", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/work_packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get work packages of workspace
     * Returns the collection of work packages that are related to the given workspace.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return WorkPackagesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackagesModel getWorkspaceWorkPackageCollection(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkspaceWorkPackageCollectionRequestCreation(id, offset, pageSize, filters, sortBy, groupBy, showSums, select).body(localVarReturnType);
    }

    /**
     * Get work packages of workspace
     * Returns the collection of work packages that are related to the given workspace.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return ResponseEntity&lt;WorkPackagesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackagesModel> getWorkspaceWorkPackageCollectionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWorkspaceWorkPackageCollectionRequestCreation(id, offset, pageSize, filters, sortBy, groupBy, showSums, select).toEntity(localVarReturnType);
    }

    /**
     * Get work packages of workspace
     * Returns the collection of work packages that are related to the given workspace.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;).
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWorkspaceWorkPackageCollectionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select) throws RestClientResponseException {
        return getWorkspaceWorkPackageCollectionRequestCreation(id, offset, pageSize, filters, sortBy, groupBy, showSums, select);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAiTextActionsByWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAiTextActionsByWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/ai_text_actions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextActionCollectionModel listAiTextActionsByWorkPackage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByWorkPackageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return ResponseEntity&lt;AITextActionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextActionCollectionModel> listAiTextActionsByWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByWorkPackageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAiTextActionsByWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAiTextActionsByWorkPackageRequestCreation(id);
    }

    /**
     * Project Available assignees
     * Gets a list of users that can be assigned to work packages in the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/available_assignees&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#workspace-available-assignees)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @return AvailableAssigneesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableAssigneesForProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAvailableAssigneesForProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/available_assignees", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Project Available assignees
     * Gets a list of users that can be assigned to work packages in the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/available_assignees&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#workspace-available-assignees)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @return AvailableAssigneesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public AvailableAssigneesModel listAvailableAssigneesForProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableAssigneesForProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Project Available assignees
     * Gets a list of users that can be assigned to work packages in the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/available_assignees&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#workspace-available-assignees)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @return ResponseEntity&lt;AvailableAssigneesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<AvailableAssigneesModel> listAvailableAssigneesForProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableAssigneesForProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Project Available assignees
     * Gets a list of users that can be assigned to work packages in the given project. This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/available_assignees&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#workspace-available-assignees)
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec listAvailableAssigneesForProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAvailableAssigneesForProjectRequestCreation(id);
    }

    /**
     * Work Package Available assignees
     * Gets a list of users that can be assigned to the given work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return AvailableAssigneesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableAssigneesForWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAvailableAssigneesForWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/available_assignees", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Work Package Available assignees
     * Gets a list of users that can be assigned to the given work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return AvailableAssigneesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AvailableAssigneesModel listAvailableAssigneesForWorkPackage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableAssigneesForWorkPackageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Work Package Available assignees
     * Gets a list of users that can be assigned to the given work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return ResponseEntity&lt;AvailableAssigneesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AvailableAssigneesModel> listAvailableAssigneesForWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableAssigneesForWorkPackageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Work Package Available assignees
     * Gets a list of users that can be assigned to the given work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableAssigneesForWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAvailableAssigneesForWorkPackageRequestCreation(id);
    }

    /**
     * Workspace Available assignees
     * Gets a list of users that can be assigned to work packages in the given workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @return AvailableAssigneesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableAssigneesForWorkspaceRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAvailableAssigneesForWorkspace", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/available_assignees", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workspace Available assignees
     * Gets a list of users that can be assigned to work packages in the given workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @return AvailableAssigneesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AvailableAssigneesModel listAvailableAssigneesForWorkspace(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableAssigneesForWorkspaceRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Workspace Available assignees
     * Gets a list of users that can be assigned to work packages in the given workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @return ResponseEntity&lt;AvailableAssigneesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AvailableAssigneesModel> listAvailableAssigneesForWorkspaceWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AvailableAssigneesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableAssigneesForWorkspaceRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Workspace Available assignees
     * Gets a list of users that can be assigned to work packages in the given workspace.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding workspace.*
     * <p><b>404</b> - Returned if the workspace does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view workspace
     * @param id Workspace id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableAssigneesForWorkspaceWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAvailableAssigneesForWorkspaceRequestCreation(id);
    }

    /**
     * Available projects for work package
     * Gets a list of projects that are available as projects to which the work package can be moved.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableProjectsForWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAvailableProjectsForWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/available_projects", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available projects for work package
     * Gets a list of projects that are available as projects to which the work package can be moved.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAvailableProjectsForWorkPackage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForWorkPackageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Available projects for work package
     * Gets a list of projects that are available as projects to which the work package can be moved.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAvailableProjectsForWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableProjectsForWorkPackageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Available projects for work package
     * Gets a list of projects that are available as projects to which the work package can be moved.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableProjectsForWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAvailableProjectsForWorkPackageRequestCreation(id);
    }

    /**
     * Available relation candidates
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Project id
     * @param pageSize Maximum number of candidates to list (default 10)
     * @param filters JSON specifying filter conditions. Accepts the same filters as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint.
     * @param query Shortcut for filtering by ID or subject
     * @param type Type of relation to find candidates for (default \&quot;relates\&quot;)
     * @param sortBy JSON specifying sort criteria. Accepts the same sort criteria as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint, including the special &#x60;exactMatch&#x60; key for ranking an exact id/identifier/sequence number match for &#x60;query&#x60; first, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableRelationCandidatesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAvailableRelationCandidates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "type", type));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/available_relation_candidates", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available relation candidates
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Project id
     * @param pageSize Maximum number of candidates to list (default 10)
     * @param filters JSON specifying filter conditions. Accepts the same filters as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint.
     * @param query Shortcut for filtering by ID or subject
     * @param type Type of relation to find candidates for (default \&quot;relates\&quot;)
     * @param sortBy JSON specifying sort criteria. Accepts the same sort criteria as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint, including the special &#x60;exactMatch&#x60; key for ranking an exact id/identifier/sequence number match for &#x60;query&#x60; first, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAvailableRelationCandidates(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableRelationCandidatesRequestCreation(id, pageSize, filters, query, type, sortBy).body(localVarReturnType);
    }

    /**
     * Available relation candidates
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Project id
     * @param pageSize Maximum number of candidates to list (default 10)
     * @param filters JSON specifying filter conditions. Accepts the same filters as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint.
     * @param query Shortcut for filtering by ID or subject
     * @param type Type of relation to find candidates for (default \&quot;relates\&quot;)
     * @param sortBy JSON specifying sort criteria. Accepts the same sort criteria as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint, including the special &#x60;exactMatch&#x60; key for ranking an exact id/identifier/sequence number match for &#x60;query&#x60; first, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAvailableRelationCandidatesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableRelationCandidatesRequestCreation(id, pageSize, filters, query, type, sortBy).toEntity(localVarReturnType);
    }

    /**
     * Available relation candidates
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Project id
     * @param pageSize Maximum number of candidates to list (default 10)
     * @param filters JSON specifying filter conditions. Accepts the same filters as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint.
     * @param query Shortcut for filtering by ID or subject
     * @param type Type of relation to find candidates for (default \&quot;relates\&quot;)
     * @param sortBy JSON specifying sort criteria. Accepts the same sort criteria as the [work packages](https://www.openproject.org/docs/api/endpoints/work-packages/) endpoint, including the special &#x60;exactMatch&#x60; key for ranking an exact id/identifier/sequence number match for &#x60;query&#x60; first, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableRelationCandidatesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String query, @jakarta.annotation.Nullable String type, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listAvailableRelationCandidatesRequestCreation(id, pageSize, filters, query, type, sortBy);
    }

    /**
     * Available watchers
     * Gets a list of users that are able to be watchers of the specified work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAvailableWatchersForWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAvailableWatchersForWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/available_watchers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Available watchers
     * Gets a list of users that are able to be watchers of the specified work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listAvailableWatchersForWorkPackage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableWatchersForWorkPackageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Available watchers
     * Gets a list of users that are able to be watchers of the specified work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listAvailableWatchersForWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAvailableWatchersForWorkPackageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Available watchers
     * Gets a list of users that are able to be watchers of the specified work package.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAvailableWatchersForWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAvailableWatchersForWorkPackageRequestCreation(id);
    }

    /**
     * List watchers
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to see the watchers of a work package without being able to see the work package.*
     * @param id Work package id
     * @return WatchersModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWatchersRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWatchers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<WatchersModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/watchers", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List watchers
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to see the watchers of a work package without being able to see the work package.*
     * @param id Work package id
     * @return WatchersModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WatchersModel listWatchers(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<WatchersModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWatchersRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List watchers
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to see the watchers of a work package without being able to see the work package.*
     * @param id Work package id
     * @return ResponseEntity&lt;WatchersModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WatchersModel> listWatchersWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<WatchersModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWatchersRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List watchers
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note that you will effectively not be able to see the watchers of a work package without being able to see the work package.*
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWatchersWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWatchersRequestCreation(id);
    }

    /**
     * List work package activities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageActivitiesRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageActivities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/activities", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List work package activities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listWorkPackageActivities(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List work package activities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listWorkPackageActivitiesWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List work package activities
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageActivitiesWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageActivitiesRequestCreation(id);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageActivitiesEmojiReactionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageActivitiesEmojiReactions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/activities_emoji_reactions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionsModel listWorkPackageActivitiesEmojiReactions(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return ResponseEntity&lt;EmojiReactionsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionsModel> listWorkPackageActivitiesEmojiReactionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageActivitiesEmojiReactionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageFileLinksRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageFileLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/file_links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public FileLinkCollectionReadModel listWorkPackageFileLinks(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageFileLinksRequestCreation(id, filters).body(localVarReturnType);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return ResponseEntity&lt;FileLinkCollectionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FileLinkCollectionReadModel> listWorkPackageFileLinksWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageFileLinksRequestCreation(id, filters).toEntity(localVarReturnType);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageFileLinksWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listWorkPackageFileLinksRequestCreation(id, filters);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ListReminders200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRemindersRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageReminders", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/reminders", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ListReminders200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ListReminders200Response listWorkPackageReminders(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRemindersRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseEntity&lt;ListReminders200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ListReminders200Response> listWorkPackageRemindersWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ListReminders200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRemindersRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List work package reminders
     * Gets a list of your upcoming reminders for this work package.  Only active reminders that belong to the current user are returned.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRemindersWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageRemindersRequestCreation(id);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryBranchCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryBranchesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryBranches", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryBranchCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_branches", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryBranchCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryBranchCollectionModel listWorkPackageRepositoryBranches(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryBranchCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryBranchesRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryBranchCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryBranchCollectionModel> listWorkPackageRepositoryBranchesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryBranchCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryBranchesRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository branches of a work package
     * Returns a paginated collection of the repository branches linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of branches.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryBranchesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryBranchesRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryCommitCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryCommitsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryCommits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryCommitCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_commits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryCommitCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryCommitCollectionModel listWorkPackageRepositoryCommits(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryCommitCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryCommitsRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryCommitCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryCommitCollectionModel> listWorkPackageRepositoryCommitsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryCommitCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryCommitsRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository commits of a work package
     * Returns a paginated collection of the repository commits linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of commits.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryCommitsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryCommitsRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryIssueCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryIssuesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryIssues", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryIssueCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryIssueCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryIssueCollectionModel listWorkPackageRepositoryIssues(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryIssueCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryIssuesRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryIssueCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryIssueCollectionModel> listWorkPackageRepositoryIssuesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryIssueCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryIssuesRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository issues of a work package
     * Returns a paginated collection of the repository issues linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of issues.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryIssuesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryIssuesRequestCreation(id, offset, pageSize);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryMergeRequestCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRepositoryMergeRequestsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRepositoryMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<RepositoryMergeRequestCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/repository_merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return RepositoryMergeRequestCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RepositoryMergeRequestCollectionModel listWorkPackageRepositoryMergeRequests(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryMergeRequestCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryMergeRequestsRequestCreation(id, offset, pageSize).body(localVarReturnType);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseEntity&lt;RepositoryMergeRequestCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RepositoryMergeRequestCollectionModel> listWorkPackageRepositoryMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        ParameterizedTypeReference<RepositoryMergeRequestCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRepositoryMergeRequestsRequestCreation(id, offset, pageSize).toEntity(localVarReturnType);
    }

    /**
     * List repository merge requests of a work package
     * Returns a paginated collection of the repository merge requests linked to the given work package.  Request it with &#x60;pageSize&#x3D;0&#x60; to only retrieve the total number of merge requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** show repository content  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRepositoryMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize) throws RestClientResponseException {
        return listWorkPackageRepositoryMergeRequestsRequestCreation(id, offset, pageSize);
    }

    /**
     * Revisions
     * Gets a list of revisions that are linked to this work package, e.g., because it is referenced in the commit message of the revision. Only linked revisions from repositories are shown if the user has the view changesets permission in the defining project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageRevisionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageRevisions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/revisions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revisions
     * Gets a list of revisions that are linked to this work package, e.g., because it is referenced in the commit message of the revision. Only linked revisions from repositories are shown if the user has the view changesets permission in the defining project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listWorkPackageRevisions(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRevisionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Revisions
     * Gets a list of revisions that are linked to this work package, e.g., because it is referenced in the commit message of the revision. Only linked revisions from repositories are shown if the user has the view changesets permission in the defining project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listWorkPackageRevisionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageRevisionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Revisions
     * Gets a list of revisions that are linked to this work package, e.g., because it is referenced in the commit message of the revision. Only linked revisions from repositories are shown if the user has the view changesets permission in the defining project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages for the project the work package is contained in.  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageRevisionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageRevisionsRequestCreation(id);
    }

    /**
     * List Work Package Schemas
     * List all work package schemas that match the given filters. This endpoint does not return a successful response, if no filter is given.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View work packages in any project.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: The schema&#39;s id  Schema id has the form &#x60;project_id-work_package_type_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageSchemasRequestCreation(@jakarta.annotation.Nonnull String filters) throws RestClientResponseException {
        // verify the required parameter 'filters' is set
        if (filters == null) {
            throw new RestClientResponseException("Missing the required parameter 'filters' when calling listWorkPackageSchemas", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/schemas", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Work Package Schemas
     * List all work package schemas that match the given filters. This endpoint does not return a successful response, if no filter is given.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View work packages in any project.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: The schema&#39;s id  Schema id has the form &#x60;project_id-work_package_type_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void listWorkPackageSchemas(@jakarta.annotation.Nonnull String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        listWorkPackageSchemasRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * List Work Package Schemas
     * List all work package schemas that match the given filters. This endpoint does not return a successful response, if no filter is given.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View work packages in any project.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: The schema&#39;s id  Schema id has the form &#x60;project_id-work_package_type_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> listWorkPackageSchemasWithHttpInfo(@jakarta.annotation.Nonnull String filters) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageSchemasRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * List Work Package Schemas
     * List all work package schemas that match the given filters. This endpoint does not return a successful response, if no filter is given.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** View work packages in any project.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + id: The schema&#39;s id  Schema id has the form &#x60;project_id-work_package_type_id&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageSchemasWithResponseSpec(@jakarta.annotation.Nonnull String filters) throws RestClientResponseException {
        return listWorkPackageSchemasRequestCreation(filters);
    }

    /**
     * List work packages
     * Returns a collection of work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages (globally or in any project)
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;), otherwise a default filter is applied. A Currently supported filters are (there are additional filters added by modules):  - assigned_to - assignee_or_group - attachment_base - attachment_content - attachment_file_name - author - blocked - blocks - category - comment - created_at - custom_field - dates_interval - description - done_ratio - due_date - duplicated - duplicates - duration - estimated_hours - file_link_origin_id - follows - group - id - includes - linkable_to_storage_id - linkable_to_storage_url - manual_sort - milestone - only_subproject - parent - partof - precedes - principal_base - priority - project - relatable - relates - required - requires - responsible - role - search - start_date - status - storage_id - storage_url - subject - subject_or_id - subproject - type - typeahead - updated_at - version - watcher - work_package  Filters that take work packages as values (e.g. &#x60;id&#x60;, &#x60;parent&#x60;, &#x60;ancestor&#x60; and the relation filters) accept numeric ids and project-based identifiers (e.g. &#x60;PROJ-42&#x60;), also mixed. Former identifiers of a work package match as well. Identifiers match exactly.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  A special &#x60;exactMatch&#x60; key is available when combined with a &#x60;typeahead&#x60; filter: sorting by &#x60;exactMatch&#x60; &#x60;desc&#x60; ranks work packages whose id, sequence number or identifier is an exact match for the typeahead search term first, regardless of any other sort criteria. Combine it with a secondary criterion to break ties, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons), you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return WorkPackagesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackagesRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "groupBy", groupBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "showSums", showSums));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "select", select));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "timestamps", timestamps));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List work packages
     * Returns a collection of work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages (globally or in any project)
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;), otherwise a default filter is applied. A Currently supported filters are (there are additional filters added by modules):  - assigned_to - assignee_or_group - attachment_base - attachment_content - attachment_file_name - author - blocked - blocks - category - comment - created_at - custom_field - dates_interval - description - done_ratio - due_date - duplicated - duplicates - duration - estimated_hours - file_link_origin_id - follows - group - id - includes - linkable_to_storage_id - linkable_to_storage_url - manual_sort - milestone - only_subproject - parent - partof - precedes - principal_base - priority - project - relatable - relates - required - requires - responsible - role - search - start_date - status - storage_id - storage_url - subject - subject_or_id - subproject - type - typeahead - updated_at - version - watcher - work_package  Filters that take work packages as values (e.g. &#x60;id&#x60;, &#x60;parent&#x60;, &#x60;ancestor&#x60; and the relation filters) accept numeric ids and project-based identifiers (e.g. &#x60;PROJ-42&#x60;), also mixed. Former identifiers of a work package match as well. Identifiers match exactly.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  A special &#x60;exactMatch&#x60; key is available when combined with a &#x60;typeahead&#x60; filter: sorting by &#x60;exactMatch&#x60; &#x60;desc&#x60; ranks work packages whose id, sequence number or identifier is an exact match for the typeahead search term first, regardless of any other sort criteria. Combine it with a secondary criterion to break ties, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons), you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return WorkPackagesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackagesModel listWorkPackages(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackagesRequestCreation(offset, pageSize, filters, sortBy, groupBy, showSums, select, timestamps).body(localVarReturnType);
    }

    /**
     * List work packages
     * Returns a collection of work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages (globally or in any project)
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;), otherwise a default filter is applied. A Currently supported filters are (there are additional filters added by modules):  - assigned_to - assignee_or_group - attachment_base - attachment_content - attachment_file_name - author - blocked - blocks - category - comment - created_at - custom_field - dates_interval - description - done_ratio - due_date - duplicated - duplicates - duration - estimated_hours - file_link_origin_id - follows - group - id - includes - linkable_to_storage_id - linkable_to_storage_url - manual_sort - milestone - only_subproject - parent - partof - precedes - principal_base - priority - project - relatable - relates - required - requires - responsible - role - search - start_date - status - storage_id - storage_url - subject - subject_or_id - subproject - type - typeahead - updated_at - version - watcher - work_package  Filters that take work packages as values (e.g. &#x60;id&#x60;, &#x60;parent&#x60;, &#x60;ancestor&#x60; and the relation filters) accept numeric ids and project-based identifiers (e.g. &#x60;PROJ-42&#x60;), also mixed. Former identifiers of a work package match as well. Identifiers match exactly.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  A special &#x60;exactMatch&#x60; key is available when combined with a &#x60;typeahead&#x60; filter: sorting by &#x60;exactMatch&#x60; &#x60;desc&#x60; ranks work packages whose id, sequence number or identifier is an exact match for the typeahead search term first, regardless of any other sort criteria. Combine it with a secondary criterion to break ties, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons), you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return ResponseEntity&lt;WorkPackagesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackagesModel> listWorkPackagesWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackagesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackagesRequestCreation(offset, pageSize, filters, sortBy, groupBy, showSums, select, timestamps).toEntity(localVarReturnType);
    }

    /**
     * List work packages
     * Returns a collection of work packages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages (globally or in any project)
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. If no filter is to be applied, the client should send an empty array (&#x60;[]&#x60;), otherwise a default filter is applied. A Currently supported filters are (there are additional filters added by modules):  - assigned_to - assignee_or_group - attachment_base - attachment_content - attachment_file_name - author - blocked - blocks - category - comment - created_at - custom_field - dates_interval - description - done_ratio - due_date - duplicated - duplicates - duration - estimated_hours - file_link_origin_id - follows - group - id - includes - linkable_to_storage_id - linkable_to_storage_url - manual_sort - milestone - only_subproject - parent - partof - precedes - principal_base - priority - project - relatable - relates - required - requires - responsible - role - search - start_date - status - storage_id - storage_url - subject - subject_or_id - subproject - type - typeahead - updated_at - version - watcher - work_package  Filters that take work packages as values (e.g. &#x60;id&#x60;, &#x60;parent&#x60;, &#x60;ancestor&#x60; and the relation filters) accept numeric ids and project-based identifiers (e.g. &#x60;PROJ-42&#x60;), also mixed. Former identifiers of a work package match as well. Identifiers match exactly.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint.  A special &#x60;exactMatch&#x60; key is available when combined with a &#x60;typeahead&#x60; filter: sorting by &#x60;exactMatch&#x60; &#x60;desc&#x60; ranks work packages whose id, sequence number or identifier is an exact match for the typeahead search term first, regardless of any other sort criteria. Combine it with a secondary criterion to break ties, e.g. &#x60;[[\&quot;exactMatch\&quot;, \&quot;desc\&quot;], [\&quot;updatedAt\&quot;, \&quot;desc\&quot;]]&#x60;.
     * @param groupBy The column to group by.
     * @param showSums Indicates whether properties should be summed up if they support it.
     * @param select Comma separated list of properties to include.
     * @param timestamps In order to perform a [baseline comparison](/docs/api/baseline-comparisons), you may provide one or several timestamps in ISO-8601 format as comma-separated list. The timestamps may be absolute or relative, such as ISO8601 dates, ISO8601 durations and the following relative date keywords: \&quot;oneDayAgo@HH:MM+HH:MM\&quot;, \&quot;lastWorkingDay@HH:MM+HH:MM\&quot;, \&quot;oneWeekAgo@HH:MM+HH:MM\&quot;, \&quot;oneMonthAgo@HH:MM+HH:MM\&quot;. The first \&quot;HH:MM\&quot; part represents the zero paded hours and minutes. The last \&quot;+HH:MM\&quot; part represents the timezone offset from UTC associated with the time, the offset can be positive or negative e.g.\&quot;oneDayAgo@01:00+01:00\&quot;, \&quot;oneDayAgo@01:00-01:00\&quot;.  Usually, the first timestamp is the baseline date, the last timestamp is the current date. Values older than 1 day are accepted only with valid Enterprise Token available.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackagesWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String filters, @jakarta.annotation.Nullable String sortBy, @jakarta.annotation.Nullable String groupBy, @jakarta.annotation.Nullable Boolean showSums, @jakarta.annotation.Nullable String select, @jakarta.annotation.Nullable String timestamps) throws RestClientResponseException {
        return listWorkPackagesRequestCreation(offset, pageSize, filters, sortBy, groupBy, showSums, select, timestamps);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareWorkPackageAttachmentUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling prepareWorkPackageAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metadata' is set
        if (metadata == null) {
            throw new RestClientResponseException("Missing the required parameter 'metadata' when calling prepareWorkPackageAttachmentUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("metadata", metadata);

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/attachments/prepare", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return AttachmentUploadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AttachmentUploadModel prepareWorkPackageAttachmentUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareWorkPackageAttachmentUploadRequestCreation(id, metadata).body(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseEntity&lt;AttachmentUploadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AttachmentUploadModel> prepareWorkPackageAttachmentUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        ParameterizedTypeReference<AttachmentUploadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareWorkPackageAttachmentUploadRequestCreation(id, metadata).toEntity(localVarReturnType);
    }

    /**
     * Prepare a direct attachment upload for a work package
     * Prepares a direct upload of an attachment for the given work package, to be performed by the client without the file content passing through the OpenProject server. See &#x60;POST /api/v3/attachments/prepare&#x60; for a detailed description of the three-step flow this endpoint is the first step of.  Unlike &#x60;POST /api/v3/attachments/prepare&#x60;, the resulting pending attachment is immediately associated with this work package as its container.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the client sends a not understandable request. Reasons include:  * Omitting the &#x60;metadata&#x60; part  * sending unparsable JSON in the metadata part
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package or add work package  *Note that you will only receive this error, if you are at least allowed to see the work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the client tries to prepare an invalid attachment upload. Reasons are:  * Omitting the file name (&#x60;fileName&#x60; property of metadata part)  * Omitting the file size (&#x60;fileSize&#x60; property of metadata part)  * The instance is not configured for direct uploads
     * @param id ID of the work package to receive the attachment
     * @param metadata A **single** part, named &#x60;metadata&#x60;, whose value is a *JSON-encoded string* conforming to the schema referenced above (see the *Model* tab for its properties) - it must **not** be split into separate &#x60;fileName&#x60;, &#x60;fileSize&#x60;, ... form fields. No file content is sent with this request.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareWorkPackageAttachmentUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull String metadata) throws RestClientResponseException {
        return prepareWorkPackageAttachmentUploadRequestCreation(id, metadata);
    }

    /**
     * Remove watcher
     * Removes the specified user from the list of watchers for the given work package.  If the request succeeds, the specified user is not watching the work package anymore.  *Note: This might also be the case, if the specified user did not watch the work package prior to the request.*
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned in one of the following cases:  Either the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  Or the specified user does not exist at all.  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @param userId User id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec removeWatcherRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling removeWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling removeWatcher", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("user_id", userId);

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
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/watchers/{user_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove watcher
     * Removes the specified user from the list of watchers for the given work package.  If the request succeeds, the specified user is not watching the work package anymore.  *Note: This might also be the case, if the specified user did not watch the work package prior to the request.*
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned in one of the following cases:  Either the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  Or the specified user does not exist at all.  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @param userId User id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void removeWatcher(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        removeWatcherRequestCreation(id, userId).body(localVarReturnType);
    }

    /**
     * Remove watcher
     * Removes the specified user from the list of watchers for the given work package.  If the request succeeds, the specified user is not watching the work package anymore.  *Note: This might also be the case, if the specified user did not watch the work package prior to the request.*
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned in one of the following cases:  Either the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  Or the specified user does not exist at all.  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @param userId User id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> removeWatcherWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeWatcherRequestCreation(id, userId).toEntity(localVarReturnType);
    }

    /**
     * Remove watcher
     * Removes the specified user from the list of watchers for the given work package.  If the request succeeds, the specified user is not watching the work package anymore.  *Note: This might also be the case, if the specified user did not watch the work package prior to the request.*
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** delete work package watchers  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned in one of the following cases:  Either the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package  Or the specified user does not exist at all.  *Note that you will effectively not be able to change the watchers of a work package without being able to see the work package.*
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id Work package id
     * @param userId User id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec removeWatcherWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer userId) throws RestClientResponseException {
        return removeWatcherRequestCreation(id, userId);
    }

    /**
     * Update a Work Package
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.  Additionally to the fields the client wants to change, it is mandatory to provide the value of &#x60;lockVersion&#x60; which was received by the &#x60;GET&#x60; request this change originates from.  The value of &#x60;lockVersion&#x60; is used to implement [optimistic locking](https://en.wikipedia.org/wiki/Optimistic_concurrency_control).  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the update request, it will not be validated. This enables clients to update specific attributes independently without having to provide values for all required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package, assign version, change work package status, manage subtasks or move work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the resource was changed since the client requested it. This is determined using the &#x60;lockVersion&#x60; property.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param notify Indicates whether change notifications should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackagePatchModel The workPackagePatchModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackagePatchModel workPackagePatchModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "notify", notify));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, workPackagePatchModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Work Package
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.  Additionally to the fields the client wants to change, it is mandatory to provide the value of &#x60;lockVersion&#x60; which was received by the &#x60;GET&#x60; request this change originates from.  The value of &#x60;lockVersion&#x60; is used to implement [optimistic locking](https://en.wikipedia.org/wiki/Optimistic_concurrency_control).  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the update request, it will not be validated. This enables clients to update specific attributes independently without having to provide values for all required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package, assign version, change work package status, manage subtasks or move work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the resource was changed since the client requested it. This is determined using the &#x60;lockVersion&#x60; property.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param notify Indicates whether change notifications should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackagePatchModel The workPackagePatchModel parameter
     * @return WorkPackageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageModel updateWorkPackage(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackagePatchModel workPackagePatchModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWorkPackageRequestCreation(id, notify, workPackagePatchModel).body(localVarReturnType);
    }

    /**
     * Update a Work Package
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.  Additionally to the fields the client wants to change, it is mandatory to provide the value of &#x60;lockVersion&#x60; which was received by the &#x60;GET&#x60; request this change originates from.  The value of &#x60;lockVersion&#x60; is used to implement [optimistic locking](https://en.wikipedia.org/wiki/Optimistic_concurrency_control).  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the update request, it will not be validated. This enables clients to update specific attributes independently without having to provide values for all required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package, assign version, change work package status, manage subtasks or move work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the resource was changed since the client requested it. This is determined using the &#x60;lockVersion&#x60; property.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param notify Indicates whether change notifications should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackagePatchModel The workPackagePatchModel parameter
     * @return ResponseEntity&lt;WorkPackageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageModel> updateWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackagePatchModel workPackagePatchModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWorkPackageRequestCreation(id, notify, workPackagePatchModel).toEntity(localVarReturnType);
    }

    /**
     * Update a Work Package
     * When calling this endpoint the client provides a single object, containing the properties and links that it wants to change, in the body. Note that it is only allowed to provide properties or links supporting the **write** operation.  Additionally to the fields the client wants to change, it is mandatory to provide the value of &#x60;lockVersion&#x60; which was received by the &#x60;GET&#x60; request this change originates from.  The value of &#x60;lockVersion&#x60; is used to implement [optimistic locking](https://en.wikipedia.org/wiki/Optimistic_concurrency_control).  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the update request, it will not be validated. This enables clients to update specific attributes independently without having to provide values for all required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work package, assign version, change work package status, manage subtasks or move work package
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>409</b> - Returned if the resource was changed since the client requested it. This is determined using the &#x60;lockVersion&#x60; property.
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  - the client tries to modify a read-only property (&#x60;PropertyIsReadOnly&#x60;) - a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;) - the client provides a link to an invalid resource (&#x60;ResourceTypeMismatch&#x60;)
     * @param id Work package id
     * @param notify Indicates whether change notifications should be sent. Note that this controls notifications for all users interested in changes to the work package (e.g. watchers, author and assignee), not just the current user.
     * @param workPackagePatchModel The workPackagePatchModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean notify, @jakarta.annotation.Nullable WorkPackagePatchModel workPackagePatchModel) throws RestClientResponseException {
        return updateWorkPackageRequestCreation(id, notify, workPackagePatchModel);
    }

    /**
     * Form for creating Work Packages in a Project
     * This endpoint allows you to validation a new work package creation body in a specific project. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s project in the path, so that it does not have to be defined in the request body.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages/form&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#form-for-creating-work-packages-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the project in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateProjectWorkPackageCreateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateProjectWorkPackageCreateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json", "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/work_packages/form", HttpMethod.POST, pathParams, localVarQueryParams, workPackageWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Form for creating Work Packages in a Project
     * This endpoint allows you to validation a new work package creation body in a specific project. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s project in the path, so that it does not have to be defined in the request body.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages/form&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#form-for-creating-work-packages-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the project in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public WorkPackageFormModel validateProjectWorkPackageCreateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProjectWorkPackageCreateFormRequestCreation(id, workPackageWriteModel).body(localVarReturnType);
    }

    /**
     * Form for creating Work Packages in a Project
     * This endpoint allows you to validation a new work package creation body in a specific project. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s project in the path, so that it does not have to be defined in the request body.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages/form&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#form-for-creating-work-packages-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the project in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseEntity&lt;WorkPackageFormModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<WorkPackageFormModel> validateProjectWorkPackageCreateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateProjectWorkPackageCreateFormRequestCreation(id, workPackageWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Form for creating Work Packages in a Project
     * This endpoint allows you to validation a new work package creation body in a specific project. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s project in the path, so that it does not have to be defined in the request body.  This endpoint is deprecated and replaced by [&#x60;/api/v3/workspaces/{id}/work_packages/form&#x60;](https://www.openproject.org/docs/api/endpoints/work-packages/#form-for-creating-work-packages-in-a-workspace)
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the project in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseSpec validateProjectWorkPackageCreateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        return validateProjectWorkPackageCreateFormRequestCreation(id, workPackageWriteModel);
    }

    /**
     * Form for creating a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be created, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to create a work package.  Only the properties of the work package write model are allowed to set on a work package on creation.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateWorkPackageCreateFormRequestCreation(@jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json", "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/form", HttpMethod.POST, pathParams, localVarQueryParams, workPackageWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Form for creating a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be created, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to create a work package.  Only the properties of the work package write model are allowed to set on a work package on creation.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageFormModel validateWorkPackageCreateForm(@jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateWorkPackageCreateFormRequestCreation(workPackageWriteModel).body(localVarReturnType);
    }

    /**
     * Form for creating a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be created, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to create a work package.  Only the properties of the work package write model are allowed to set on a work package on creation.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseEntity&lt;WorkPackageFormModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageFormModel> validateWorkPackageCreateFormWithHttpInfo(@jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateWorkPackageCreateFormRequestCreation(workPackageWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Form for creating a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be created, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to create a work package.  Only the properties of the work package write model are allowed to set on a work package on creation.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateWorkPackageCreateFormWithResponseSpec(@jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        return validateWorkPackageCreateFormRequestCreation(workPackageWriteModel);
    }

    /**
     * Form for editing a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be edited, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to edit the work package.  Only the properties of the work package write model are allowed to set on a work package on editing.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the form request, it will not be validated, allowing clients to validate partial updates without triggering validation errors for unrelated required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the work package being modified
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateWorkPackageUpdateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateWorkPackageUpdateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json", "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/form", HttpMethod.POST, pathParams, localVarQueryParams, workPackageWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Form for editing a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be edited, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to edit the work package.  Only the properties of the work package write model are allowed to set on a work package on editing.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the form request, it will not be validated, allowing clients to validate partial updates without triggering validation errors for unrelated required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the work package being modified
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageFormModel validateWorkPackageUpdateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateWorkPackageUpdateFormRequestCreation(id, workPackageWriteModel).body(localVarReturnType);
    }

    /**
     * Form for editing a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be edited, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to edit the work package.  Only the properties of the work package write model are allowed to set on a work package on editing.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the form request, it will not be validated, allowing clients to validate partial updates without triggering validation errors for unrelated required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the work package being modified
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseEntity&lt;WorkPackageFormModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageFormModel> validateWorkPackageUpdateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateWorkPackageUpdateFormRequestCreation(id, workPackageWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Form for editing a Work Package
     * When calling this endpoint, the client provides a single object containing the properties and links to be edited, in the body. The input is validated and a schema response is returned. If the validation errors of the response is empty, the same payload can be used to edit the work package.  Only the properties of the work package write model are allowed to set on a work package on editing.  When setting start date, finish date, and duration together, their correctness will be checked and a validation error will be returned if one value does not match with the two others. You can make the server compute a value: set only two values in the request and the third one will be computed and returned in the response. For instance, when sending &#x60;{ \&quot;startDate\&quot;: \&quot;2022-08-23\&quot;, duration: \&quot;P2D\&quot; }&#x60;, the response will include &#x60;{ \&quot;dueDate\&quot;: \&quot;2022-08-24\&quot; }&#x60;.  **Custom Field Validation**  Required custom fields are only validated when they are explicitly provided in the request body. If a custom field is not included in the form request, it will not be validated, allowing clients to validate partial updates without triggering validation errors for unrelated required custom fields.  To override this behavior and validate all required custom fields regardless of whether they are included in the request, set &#x60;validateCustomFields&#x60; to &#x60;true&#x60; in the &#x60;_meta&#x60; object of the request body.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the work package being modified
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateWorkPackageUpdateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        return validateWorkPackageUpdateFormRequestCreation(id, workPackageWriteModel);
    }

    /**
     * Form for creating Work Packages in a Workspace
     * This endpoint allows you to validation a new work package creation body in a specific workspace. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s workspace in the path, so that it does not have to be defined in the request body.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the workspace in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec validateWorkspaceWorkPackageCreateFormRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling validateWorkspaceWorkPackageCreateForm", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json", "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/workspaces/{id}/work_packages/form", HttpMethod.POST, pathParams, localVarQueryParams, workPackageWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Form for creating Work Packages in a Workspace
     * This endpoint allows you to validation a new work package creation body in a specific workspace. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s workspace in the path, so that it does not have to be defined in the request body.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the workspace in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return WorkPackageFormModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WorkPackageFormModel validateWorkspaceWorkPackageCreateForm(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateWorkspaceWorkPackageCreateFormRequestCreation(id, workPackageWriteModel).body(localVarReturnType);
    }

    /**
     * Form for creating Work Packages in a Workspace
     * This endpoint allows you to validation a new work package creation body in a specific workspace. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s workspace in the path, so that it does not have to be defined in the request body.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the workspace in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseEntity&lt;WorkPackageFormModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkPackageFormModel> validateWorkspaceWorkPackageCreateFormWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<WorkPackageFormModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return validateWorkspaceWorkPackageCreateFormRequestCreation(id, workPackageWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Form for creating Work Packages in a Workspace
     * This endpoint allows you to validation a new work package creation body in a specific workspace. It works similarly to the &#x60;/api/v3/work_packages/form&#x60; endpoint, but already specifies the work package&#39;s workspace in the path, so that it does not have to be defined in the request body.
     * <p><b>200</b> - OK
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param id ID of the workspace in which the work package will be created
     * @param workPackageWriteModel The workPackageWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec validateWorkspaceWorkPackageCreateFormWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable WorkPackageWriteModel workPackageWriteModel) throws RestClientResponseException {
        return validateWorkspaceWorkPackageCreateFormRequestCreation(id, workPackageWriteModel);
    }
}
