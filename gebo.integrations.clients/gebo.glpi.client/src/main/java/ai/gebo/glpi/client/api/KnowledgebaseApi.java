package ai.gebo.glpi.client.api;

import ai.gebo.glpi.client.invoker.ApiClient;

import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.KBArticleComment;
import ai.gebo.glpi.client.model.KBArticleRevision;
import ai.gebo.glpi.client.model.KBCategory;
import ai.gebo.glpi.client.model.ReqPost201Response;

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
public class KnowledgebaseApi {
    private ApiClient apiClient;

    public KnowledgebaseApi() {
        this(new ApiClient());
    }

    public KnowledgebaseApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * List or search for KBArticleComments
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticleComment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdCommentGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdCommentGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<KBArticleComment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Comment", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleComments
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticleComment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<KBArticleComment> knowledgebaseArticleArticleIdCommentGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticleComment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleComments
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;KBArticleComment&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<KBArticleComment>> knowledgebaseArticleArticleIdCommentGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticleComment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleComments
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdCommentGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdCommentGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a KBArticleComment
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdCommentIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdCommentIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling knowledgebaseArticleArticleIdCommentIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);
        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "force", force));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Comment/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a KBArticleComment
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void knowledgebaseArticleArticleIdCommentIdDelete(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        knowledgebaseArticleArticleIdCommentIdDeleteRequestCreation(articleId, id, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a KBArticleComment
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> knowledgebaseArticleArticleIdCommentIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentIdDeleteRequestCreation(articleId, id, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a KBArticleComment
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdCommentIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdCommentIdDeleteRequestCreation(articleId, id, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing KBArticleComment
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticleComment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdCommentIdGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdCommentIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling knowledgebaseArticleArticleIdCommentIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);
        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBArticleComment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Comment/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleComment
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticleComment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBArticleComment knowledgebaseArticleArticleIdCommentIdGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleComment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentIdGetRequestCreation(articleId, id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleComment
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;KBArticleComment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBArticleComment> knowledgebaseArticleArticleIdCommentIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleComment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentIdGetRequestCreation(articleId, id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleComment
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdCommentIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdCommentIdGetRequestCreation(articleId, id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing KBArticleComment
     * <p><b>200</b> - The updated KBArticleComment
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return KBArticleComment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdCommentIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdCommentIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling knowledgebaseArticleArticleIdCommentIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);
        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBArticleComment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Comment/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, kbArticleComment, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing KBArticleComment
     * <p><b>200</b> - The updated KBArticleComment
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return KBArticleComment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBArticleComment knowledgebaseArticleArticleIdCommentIdPatch(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleComment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentIdPatchRequestCreation(articleId, id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticleComment).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing KBArticleComment
     * <p><b>200</b> - The updated KBArticleComment
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return ResponseEntity&lt;KBArticleComment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBArticleComment> knowledgebaseArticleArticleIdCommentIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleComment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentIdPatchRequestCreation(articleId, id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticleComment).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing KBArticleComment
     * <p><b>200</b> - The updated KBArticleComment
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdCommentIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdCommentIdPatchRequestCreation(articleId, id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticleComment);
    }

    /**
     * 
     * Create a new KBArticleComment
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdCommentPostRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdCommentPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Comment", HttpMethod.POST, pathParams, localVarQueryParams, kbArticleComment, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new KBArticleComment
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response knowledgebaseArticleArticleIdCommentPost(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentPostRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticleComment).body(localVarReturnType);
    }

    /**
     * 
     * Create a new KBArticleComment
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> knowledgebaseArticleArticleIdCommentPostWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdCommentPostRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticleComment).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new KBArticleComment
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticleComment The kbArticleComment parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdCommentPostWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticleComment kbArticleComment) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdCommentPostRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticleComment);
    }

    /**
     * 
     * Delete a KBArticle
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "force", force));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a KBArticle
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void knowledgebaseArticleArticleIdDelete(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        knowledgebaseArticleArticleIdDeleteRequestCreation(articleId, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a KBArticle
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> knowledgebaseArticleArticleIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdDeleteRequestCreation(articleId, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a KBArticle
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdDeleteRequestCreation(articleId, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing KBArticle
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticle
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBArticle knowledgebaseArticleArticleIdGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticle
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;KBArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBArticle> knowledgebaseArticleArticleIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticle
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticleRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdLanguageRevisionGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdLanguageRevisionGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<KBArticleRevision>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/{language}/Revision", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticleRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<KBArticleRevision> knowledgebaseArticleArticleIdLanguageRevisionGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticleRevision>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdLanguageRevisionGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;KBArticleRevision&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<KBArticleRevision>> knowledgebaseArticleArticleIdLanguageRevisionGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticleRevision>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdLanguageRevisionGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdLanguageRevisionGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdLanguageRevisionGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticleRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdLanguageRevisionRevisionGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdLanguageRevisionRevisionGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBArticleRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/{language}/Revision/{revision}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticleRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBArticleRevision knowledgebaseArticleArticleIdLanguageRevisionRevisionGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdLanguageRevisionRevisionGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;KBArticleRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBArticleRevision> knowledgebaseArticleArticleIdLanguageRevisionRevisionGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdLanguageRevisionRevisionGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdLanguageRevisionRevisionGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdLanguageRevisionRevisionGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing KBArticle
     * <p><b>200</b> - The updated KBArticle
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return KBArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, kbArticle, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing KBArticle
     * <p><b>200</b> - The updated KBArticle
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return KBArticle
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBArticle knowledgebaseArticleArticleIdPatch(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdPatchRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticle).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing KBArticle
     * <p><b>200</b> - The updated KBArticle
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return ResponseEntity&lt;KBArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBArticle> knowledgebaseArticleArticleIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticle> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdPatchRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticle).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing KBArticle
     * <p><b>200</b> - The updated KBArticle
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdPatchRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticle);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticleRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdRevisionGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdRevisionGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<KBArticleRevision>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Revision", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticleRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<KBArticleRevision> knowledgebaseArticleArticleIdRevisionGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticleRevision>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdRevisionGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;KBArticleRevision&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<KBArticleRevision>> knowledgebaseArticleArticleIdRevisionGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticleRevision>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdRevisionGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticleRevisions
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdRevisionGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdRevisionGetRequestCreation(articleId, language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticleRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleArticleIdRevisionRevisionGetRequestCreation(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'articleId' is set
        if (articleId == null) {
            throw new RestClientResponseException("Missing the required parameter 'articleId' when calling knowledgebaseArticleArticleIdRevisionRevisionGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("article_id", articleId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBArticleRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article/{article_id}/Revision/{revision}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBArticleRevision
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBArticleRevision knowledgebaseArticleArticleIdRevisionRevisionGet(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdRevisionRevisionGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;KBArticleRevision&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBArticleRevision> knowledgebaseArticleArticleIdRevisionRevisionGetWithHttpInfo(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBArticleRevision> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleArticleIdRevisionRevisionGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBArticleRevision
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param articleId 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleArticleIdRevisionRevisionGetWithResponseSpec(@jakarta.annotation.Nonnull Integer articleId, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleArticleIdRevisionRevisionGetRequestCreation(articleId, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * List or search for KBArticles
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticleGetRequestCreation(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<KBArticle>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticles
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBArticle&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<KBArticle> knowledgebaseArticleGet(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticle>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleGetRequestCreation(language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticles
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;KBArticle&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<KBArticle>> knowledgebaseArticleGetWithHttpInfo(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBArticle>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticleGetRequestCreation(language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for KBArticles
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticleGetWithResponseSpec(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseArticleGetRequestCreation(language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Create a new KBArticle
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseArticlePostRequestCreation(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Article", HttpMethod.POST, pathParams, localVarQueryParams, kbArticle, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new KBArticle
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response knowledgebaseArticlePost(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticlePostRequestCreation(language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticle).body(localVarReturnType);
    }

    /**
     * 
     * Create a new KBArticle
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> knowledgebaseArticlePostWithHttpInfo(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseArticlePostRequestCreation(language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticle).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new KBArticle
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbArticle The kbArticle parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseArticlePostWithResponseSpec(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBArticle kbArticle) throws RestClientResponseException {
        return knowledgebaseArticlePostRequestCreation(language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbArticle);
    }

    /**
     * 
     * List or search for KBCategories
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseCategoryGetRequestCreation(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<KBCategory>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Category", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for KBCategories
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;KBCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<KBCategory> knowledgebaseCategoryGet(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBCategory>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryGetRequestCreation(language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for KBCategories
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;KBCategory&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<KBCategory>> knowledgebaseCategoryGetWithHttpInfo(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<KBCategory>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryGetRequestCreation(language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for KBCategories
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseCategoryGetWithResponseSpec(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseCategoryGetRequestCreation(language, filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a KBCategory
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseCategoryIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling knowledgebaseCategoryIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "force", force));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Category/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a KBCategory
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void knowledgebaseCategoryIdDelete(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        knowledgebaseCategoryIdDeleteRequestCreation(id, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a KBCategory
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> knowledgebaseCategoryIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryIdDeleteRequestCreation(id, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a KBCategory
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseCategoryIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseCategoryIdDeleteRequestCreation(id, language, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing KBCategory
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseCategoryIdGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling knowledgebaseCategoryIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Category/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing KBCategory
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return KBCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBCategory knowledgebaseCategoryIdGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryIdGetRequestCreation(id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBCategory
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;KBCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBCategory> knowledgebaseCategoryIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<KBCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryIdGetRequestCreation(id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing KBCategory
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseCategoryIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return knowledgebaseCategoryIdGetRequestCreation(id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing KBCategory
     * <p><b>200</b> - The updated KBCategory
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return KBCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseCategoryIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling knowledgebaseCategoryIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<KBCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Category/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, kbCategory, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing KBCategory
     * <p><b>200</b> - The updated KBCategory
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return KBCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KBCategory knowledgebaseCategoryIdPatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        ParameterizedTypeReference<KBCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryIdPatchRequestCreation(id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbCategory).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing KBCategory
     * <p><b>200</b> - The updated KBCategory
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return ResponseEntity&lt;KBCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KBCategory> knowledgebaseCategoryIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        ParameterizedTypeReference<KBCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryIdPatchRequestCreation(id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbCategory).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing KBCategory
     * <p><b>200</b> - The updated KBCategory
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseCategoryIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        return knowledgebaseCategoryIdPatchRequestCreation(id, language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbCategory);
    }

    /**
     * 
     * Create a new KBCategory
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec knowledgebaseCategoryPostRequestCreation(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Knowledgebase/Category", HttpMethod.POST, pathParams, localVarQueryParams, kbCategory, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new KBCategory
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response knowledgebaseCategoryPost(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryPostRequestCreation(language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbCategory).body(localVarReturnType);
    }

    /**
     * 
     * Create a new KBCategory
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> knowledgebaseCategoryPostWithHttpInfo(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return knowledgebaseCategoryPostRequestCreation(language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbCategory).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new KBCategory
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param language 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param kbCategory The kbCategory parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec knowledgebaseCategoryPostWithResponseSpec(@jakarta.annotation.Nullable String language, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable KBCategory kbCategory) throws RestClientResponseException {
        return knowledgebaseCategoryPostRequestCreation(language, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, kbCategory);
    }
}
