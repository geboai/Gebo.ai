package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.Link;
import ai.gebo.zammad.client.model.LinkCreate;
import ai.gebo.zammad.client.model.LinkRemove;
import ai.gebo.zammad.client.model.LinksResult;
import ai.gebo.zammad.client.model.ListMentionsMentionableTypeParameter;

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
public class LinksApi {
    private ApiClient apiClient;

    public LinksApi() {
        this(new ApiClient());
    }

    public LinksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add ticket link
     * 
     * <p><b>201</b> - Link created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkCreate The linkCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Link
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addTicketLinkRequestCreation(@jakarta.annotation.Nonnull LinkCreate linkCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'linkCreate' is set
        if (linkCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkCreate' when calling addTicketLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Link> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/links/add", HttpMethod.POST, pathParams, localVarQueryParams, linkCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add ticket link
     * 
     * <p><b>201</b> - Link created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkCreate The linkCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Link
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Link addTicketLink(@jakarta.annotation.Nonnull LinkCreate linkCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Link> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addTicketLinkRequestCreation(linkCreate, from).body(localVarReturnType);
    }

    /**
     * Add ticket link
     * 
     * <p><b>201</b> - Link created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkCreate The linkCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Link&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Link> addTicketLinkWithHttpInfo(@jakarta.annotation.Nonnull LinkCreate linkCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Link> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addTicketLinkRequestCreation(linkCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Add ticket link
     * 
     * <p><b>201</b> - Link created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkCreate The linkCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addTicketLinkWithResponseSpec(@jakarta.annotation.Nonnull LinkCreate linkCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return addTicketLinkRequestCreation(linkCreate, from);
    }

    /**
     * List ticket links
     * The upstream documentation shows a GET body, but the controller reads normal request parameters. Query parameters are used here for broad HTTP-client compatibility.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkObject The linkObject parameter
     * @param linkObjectValue Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return LinksResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listTicketLinksRequestCreation(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter linkObject, @jakarta.annotation.Nonnull Long linkObjectValue, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'linkObject' is set
        if (linkObject == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkObject' when calling listTicketLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'linkObjectValue' is set
        if (linkObjectValue == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkObjectValue' when calling listTicketLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "link_object", linkObject));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "link_object_value", linkObjectValue));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<LinksResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List ticket links
     * The upstream documentation shows a GET body, but the controller reads normal request parameters. Query parameters are used here for broad HTTP-client compatibility.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkObject The linkObject parameter
     * @param linkObjectValue Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return LinksResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LinksResult listTicketLinks(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter linkObject, @jakarta.annotation.Nonnull Long linkObjectValue, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<LinksResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketLinksRequestCreation(linkObject, linkObjectValue, from).body(localVarReturnType);
    }

    /**
     * List ticket links
     * The upstream documentation shows a GET body, but the controller reads normal request parameters. Query parameters are used here for broad HTTP-client compatibility.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkObject The linkObject parameter
     * @param linkObjectValue Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;LinksResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LinksResult> listTicketLinksWithHttpInfo(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter linkObject, @jakarta.annotation.Nonnull Long linkObjectValue, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<LinksResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listTicketLinksRequestCreation(linkObject, linkObjectValue, from).toEntity(localVarReturnType);
    }

    /**
     * List ticket links
     * The upstream documentation shows a GET body, but the controller reads normal request parameters. Query parameters are used here for broad HTTP-client compatibility.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkObject The linkObject parameter
     * @param linkObjectValue Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listTicketLinksWithResponseSpec(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter linkObject, @jakarta.annotation.Nonnull Long linkObjectValue, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return listTicketLinksRequestCreation(linkObject, linkObjectValue, from);
    }

    /**
     * Remove ticket link
     * 
     * <p><b>201</b> - Link removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkRemove The linkRemove parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec removeTicketLinkRequestCreation(@jakarta.annotation.Nonnull LinkRemove linkRemove, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'linkRemove' is set
        if (linkRemove == null) {
            throw new RestClientResponseException("Missing the required parameter 'linkRemove' when calling removeTicketLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/links/remove", HttpMethod.DELETE, pathParams, localVarQueryParams, linkRemove, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Remove ticket link
     * 
     * <p><b>201</b> - Link removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkRemove The linkRemove parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object removeTicketLink(@jakarta.annotation.Nonnull LinkRemove linkRemove, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeTicketLinkRequestCreation(linkRemove, from).body(localVarReturnType);
    }

    /**
     * Remove ticket link
     * 
     * <p><b>201</b> - Link removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkRemove The linkRemove parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> removeTicketLinkWithHttpInfo(@jakarta.annotation.Nonnull LinkRemove linkRemove, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return removeTicketLinkRequestCreation(linkRemove, from).toEntity(localVarReturnType);
    }

    /**
     * Remove ticket link
     * 
     * <p><b>201</b> - Link removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param linkRemove The linkRemove parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec removeTicketLinkWithResponseSpec(@jakarta.annotation.Nonnull LinkRemove linkRemove, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return removeTicketLinkRequestCreation(linkRemove, from);
    }
}
