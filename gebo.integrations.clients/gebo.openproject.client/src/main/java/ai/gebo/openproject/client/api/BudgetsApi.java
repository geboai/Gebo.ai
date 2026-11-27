package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.BudgetModel;
import ai.gebo.openproject.client.model.ErrorResponse;

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
public class BudgetsApi {
    private ApiClient apiClient;

    public BudgetsApi() {
        this(new ApiClient());
    }

    public BudgetsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * view Budget
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages **or** view budgets (on the budgets project)
     * @param id Budget id
     * @return BudgetModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getBudgetRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getBudget", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BudgetModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/budgets/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view Budget
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages **or** view budgets (on the budgets project)
     * @param id Budget id
     * @return BudgetModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public BudgetModel getBudget(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<BudgetModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getBudgetRequestCreation(id).body(localVarReturnType);
    }

    /**
     * view Budget
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages **or** view budgets (on the budgets project)
     * @param id Budget id
     * @return ResponseEntity&lt;BudgetModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<BudgetModel> getBudgetWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<BudgetModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getBudgetRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * view Budget
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view work packages **or** view budgets (on the budgets project)
     * @param id Budget id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getBudgetWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getBudgetRequestCreation(id);
    }

    /**
     * view Budgets of a Project
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the budgets of the given project.  **Required permission:** view work packages **or** view budgets  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if either:  * the project does not exist  * the client does not have sufficient permissions to see the project  * the costs module is not enabled on the given project  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getBudgetsOfAProjectRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getBudgetsOfAProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/projects/{id}/budgets", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * view Budgets of a Project
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the budgets of the given project.  **Required permission:** view work packages **or** view budgets  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if either:  * the project does not exist  * the client does not have sufficient permissions to see the project  * the costs module is not enabled on the given project  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object getBudgetsOfAProject(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getBudgetsOfAProjectRequestCreation(id).body(localVarReturnType);
    }

    /**
     * view Budgets of a Project
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the budgets of the given project.  **Required permission:** view work packages **or** view budgets  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if either:  * the project does not exist  * the client does not have sufficient permissions to see the project  * the costs module is not enabled on the given project  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getBudgetsOfAProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getBudgetsOfAProjectRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * view Budgets of a Project
     * 
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to see the budgets of the given project.  **Required permission:** view work packages **or** view budgets  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if either:  * the project does not exist  * the client does not have sufficient permissions to see the project  * the costs module is not enabled on the given project  **Required permission:** view project  *Note: A client without sufficient permissions shall not be able to test for the existence of a project. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getBudgetsOfAProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getBudgetsOfAProjectRequestCreation(id);
    }
}
