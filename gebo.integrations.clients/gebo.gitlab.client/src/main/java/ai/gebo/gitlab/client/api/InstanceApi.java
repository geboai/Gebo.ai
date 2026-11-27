package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAppearance;
import ai.gebo.gitlab.client.model.APIEntitiesApplicationSetting;
import ai.gebo.gitlab.client.model.APIEntitiesApplicationStatistics;
import java.io.File;
import ai.gebo.gitlab.client.model.PutApiV4ApplicationSettingsRequest;

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
public class InstanceApi {
    private ApiClient apiClient;

    public InstanceApi() {
        this(new ApiClient());
    }

    public InstanceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the current appearance
     * Retrieves the instance&#39;s branding and appearance configuration including logos, colors, and custom headers.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesAppearance
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ApplicationAppearanceRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAppearance> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/appearance", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get the current appearance
     * Retrieves the instance&#39;s branding and appearance configuration including logos, colors, and custom headers.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesAppearance
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAppearance getApiV4ApplicationAppearance() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAppearance> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationAppearanceRequestCreation().body(localVarReturnType);
    }

    /**
     * Get the current appearance
     * Retrieves the instance&#39;s branding and appearance configuration including logos, colors, and custom headers.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesAppearance&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAppearance> getApiV4ApplicationAppearanceWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAppearance> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationAppearanceRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get the current appearance
     * Retrieves the instance&#39;s branding and appearance configuration including logos, colors, and custom headers.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ApplicationAppearanceWithResponseSpec() throws RestClientResponseException {
        return getApiV4ApplicationAppearanceRequestCreation();
    }

    /**
     * Retrieve application settings
     * Retrieves the current application settings for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesApplicationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ApplicationSettingsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesApplicationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/settings", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve application settings
     * Retrieves the current application settings for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesApplicationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationSetting getApiV4ApplicationSettings() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationSettingsRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve application settings
     * Retrieves the current application settings for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesApplicationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationSetting> getApiV4ApplicationSettingsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationSettingsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve application settings
     * Retrieves the current application settings for this GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ApplicationSettingsWithResponseSpec() throws RestClientResponseException {
        return getApiV4ApplicationSettingsRequestCreation();
    }

    /**
     * Retrieve application statistics
     * Retrieves the current application statistics for this GitLab instance. For values less than 10,000, an exact count is returned. For values of 10,000 and greater, the count might be approximate.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesApplicationStatistics
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ApplicationStatisticsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesApplicationStatistics> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/statistics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve application statistics
     * Retrieves the current application statistics for this GitLab instance. For values less than 10,000, an exact count is returned. For values of 10,000 and greater, the count might be approximate.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return APIEntitiesApplicationStatistics
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationStatistics getApiV4ApplicationStatistics() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationStatistics> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationStatisticsRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve application statistics
     * Retrieves the current application statistics for this GitLab instance. For values less than 10,000, an exact count is returned. For values of 10,000 and greater, the count might be approximate.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;APIEntitiesApplicationStatistics&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationStatistics> getApiV4ApplicationStatisticsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationStatistics> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationStatisticsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve application statistics
     * Retrieves the current application statistics for this GitLab instance. For values less than 10,000, an exact count is returned. For values of 10,000 and greater, the count might be approximate.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ApplicationStatisticsWithResponseSpec() throws RestClientResponseException {
        return getApiV4ApplicationStatisticsRequestCreation();
    }

    /**
     * Modify appearance
     * Updates instance appearance settings like branding, logos, headers, and footer messages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param title Instance title on the sign in / sign up page
     * @param description Markdown text shown on the sign in / sign up page
     * @param pwaName Name of the Progressive Web App. Used for the &#x60;name&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param pwaShortName Optional, short name for Progressive Web App
     * @param pwaDescription An explanation of what the Progressive Web App does. Used for the &#x60;description&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param logo Instance image used on the sign in / sign up page
     * @param pwaIcon Icon used for Progressive Web App
     * @param headerLogo Instance image used for the main navigation bar
     * @param favicon Instance favicon in .ico/.png format
     * @param memberGuidelines Markdown text shown on the members page of a group or project. Shown only to users with permission to change members.
     * @param newProjectGuidelines Markdown text shown on the new project page
     * @param profileImageGuidelines Markdown text shown on the profile page below Public Avatar
     * @param headerMessage Message within the system header bar
     * @param footerMessage Message within the system footer bar
     * @param messageBackgroundColor Background color for the system header / footer bar
     * @param messageFontColor Font color for the system header / footer bar
     * @param emailHeaderAndFooterEnabled Add header and footer to all outgoing emails if enabled
     * @param siteName Last part of the webpage title. Defaults to empty.
     * @return APIEntitiesAppearance
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ApplicationAppearanceRequestCreation(@jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String pwaName, @jakarta.annotation.Nullable String pwaShortName, @jakarta.annotation.Nullable String pwaDescription, @jakarta.annotation.Nullable File logo, @jakarta.annotation.Nullable File pwaIcon, @jakarta.annotation.Nullable File headerLogo, @jakarta.annotation.Nullable File favicon, @jakarta.annotation.Nullable String memberGuidelines, @jakarta.annotation.Nullable String newProjectGuidelines, @jakarta.annotation.Nullable String profileImageGuidelines, @jakarta.annotation.Nullable String headerMessage, @jakarta.annotation.Nullable String footerMessage, @jakarta.annotation.Nullable String messageBackgroundColor, @jakarta.annotation.Nullable String messageFontColor, @jakarta.annotation.Nullable Boolean emailHeaderAndFooterEnabled, @jakarta.annotation.Nullable String siteName) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (title != null) {
            formParams.add("title", title);
        }
        if (description != null) {
            formParams.add("description", description);
        }
        if (pwaName != null) {
            formParams.add("pwa_name", pwaName);
        }
        if (pwaShortName != null) {
            formParams.add("pwa_short_name", pwaShortName);
        }
        if (pwaDescription != null) {
            formParams.add("pwa_description", pwaDescription);
        }
        if (logo != null) {
            formParams.add("logo", new FileSystemResource(logo));
        }
        if (pwaIcon != null) {
            formParams.add("pwa_icon", new FileSystemResource(pwaIcon));
        }
        if (headerLogo != null) {
            formParams.add("header_logo", new FileSystemResource(headerLogo));
        }
        if (favicon != null) {
            formParams.add("favicon", new FileSystemResource(favicon));
        }
        if (memberGuidelines != null) {
            formParams.add("member_guidelines", memberGuidelines);
        }
        if (newProjectGuidelines != null) {
            formParams.add("new_project_guidelines", newProjectGuidelines);
        }
        if (profileImageGuidelines != null) {
            formParams.add("profile_image_guidelines", profileImageGuidelines);
        }
        if (headerMessage != null) {
            formParams.add("header_message", headerMessage);
        }
        if (footerMessage != null) {
            formParams.add("footer_message", footerMessage);
        }
        if (messageBackgroundColor != null) {
            formParams.add("message_background_color", messageBackgroundColor);
        }
        if (messageFontColor != null) {
            formParams.add("message_font_color", messageFontColor);
        }
        if (emailHeaderAndFooterEnabled != null) {
            formParams.add("email_header_and_footer_enabled", emailHeaderAndFooterEnabled);
        }
        if (siteName != null) {
            formParams.add("site_name", siteName);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesAppearance> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/appearance", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Modify appearance
     * Updates instance appearance settings like branding, logos, headers, and footer messages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param title Instance title on the sign in / sign up page
     * @param description Markdown text shown on the sign in / sign up page
     * @param pwaName Name of the Progressive Web App. Used for the &#x60;name&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param pwaShortName Optional, short name for Progressive Web App
     * @param pwaDescription An explanation of what the Progressive Web App does. Used for the &#x60;description&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param logo Instance image used on the sign in / sign up page
     * @param pwaIcon Icon used for Progressive Web App
     * @param headerLogo Instance image used for the main navigation bar
     * @param favicon Instance favicon in .ico/.png format
     * @param memberGuidelines Markdown text shown on the members page of a group or project. Shown only to users with permission to change members.
     * @param newProjectGuidelines Markdown text shown on the new project page
     * @param profileImageGuidelines Markdown text shown on the profile page below Public Avatar
     * @param headerMessage Message within the system header bar
     * @param footerMessage Message within the system footer bar
     * @param messageBackgroundColor Background color for the system header / footer bar
     * @param messageFontColor Font color for the system header / footer bar
     * @param emailHeaderAndFooterEnabled Add header and footer to all outgoing emails if enabled
     * @param siteName Last part of the webpage title. Defaults to empty.
     * @return APIEntitiesAppearance
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAppearance putApiV4ApplicationAppearance(@jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String pwaName, @jakarta.annotation.Nullable String pwaShortName, @jakarta.annotation.Nullable String pwaDescription, @jakarta.annotation.Nullable File logo, @jakarta.annotation.Nullable File pwaIcon, @jakarta.annotation.Nullable File headerLogo, @jakarta.annotation.Nullable File favicon, @jakarta.annotation.Nullable String memberGuidelines, @jakarta.annotation.Nullable String newProjectGuidelines, @jakarta.annotation.Nullable String profileImageGuidelines, @jakarta.annotation.Nullable String headerMessage, @jakarta.annotation.Nullable String footerMessage, @jakarta.annotation.Nullable String messageBackgroundColor, @jakarta.annotation.Nullable String messageFontColor, @jakarta.annotation.Nullable Boolean emailHeaderAndFooterEnabled, @jakarta.annotation.Nullable String siteName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAppearance> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ApplicationAppearanceRequestCreation(title, description, pwaName, pwaShortName, pwaDescription, logo, pwaIcon, headerLogo, favicon, memberGuidelines, newProjectGuidelines, profileImageGuidelines, headerMessage, footerMessage, messageBackgroundColor, messageFontColor, emailHeaderAndFooterEnabled, siteName).body(localVarReturnType);
    }

    /**
     * Modify appearance
     * Updates instance appearance settings like branding, logos, headers, and footer messages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param title Instance title on the sign in / sign up page
     * @param description Markdown text shown on the sign in / sign up page
     * @param pwaName Name of the Progressive Web App. Used for the &#x60;name&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param pwaShortName Optional, short name for Progressive Web App
     * @param pwaDescription An explanation of what the Progressive Web App does. Used for the &#x60;description&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param logo Instance image used on the sign in / sign up page
     * @param pwaIcon Icon used for Progressive Web App
     * @param headerLogo Instance image used for the main navigation bar
     * @param favicon Instance favicon in .ico/.png format
     * @param memberGuidelines Markdown text shown on the members page of a group or project. Shown only to users with permission to change members.
     * @param newProjectGuidelines Markdown text shown on the new project page
     * @param profileImageGuidelines Markdown text shown on the profile page below Public Avatar
     * @param headerMessage Message within the system header bar
     * @param footerMessage Message within the system footer bar
     * @param messageBackgroundColor Background color for the system header / footer bar
     * @param messageFontColor Font color for the system header / footer bar
     * @param emailHeaderAndFooterEnabled Add header and footer to all outgoing emails if enabled
     * @param siteName Last part of the webpage title. Defaults to empty.
     * @return ResponseEntity&lt;APIEntitiesAppearance&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAppearance> putApiV4ApplicationAppearanceWithHttpInfo(@jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String pwaName, @jakarta.annotation.Nullable String pwaShortName, @jakarta.annotation.Nullable String pwaDescription, @jakarta.annotation.Nullable File logo, @jakarta.annotation.Nullable File pwaIcon, @jakarta.annotation.Nullable File headerLogo, @jakarta.annotation.Nullable File favicon, @jakarta.annotation.Nullable String memberGuidelines, @jakarta.annotation.Nullable String newProjectGuidelines, @jakarta.annotation.Nullable String profileImageGuidelines, @jakarta.annotation.Nullable String headerMessage, @jakarta.annotation.Nullable String footerMessage, @jakarta.annotation.Nullable String messageBackgroundColor, @jakarta.annotation.Nullable String messageFontColor, @jakarta.annotation.Nullable Boolean emailHeaderAndFooterEnabled, @jakarta.annotation.Nullable String siteName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAppearance> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ApplicationAppearanceRequestCreation(title, description, pwaName, pwaShortName, pwaDescription, logo, pwaIcon, headerLogo, favicon, memberGuidelines, newProjectGuidelines, profileImageGuidelines, headerMessage, footerMessage, messageBackgroundColor, messageFontColor, emailHeaderAndFooterEnabled, siteName).toEntity(localVarReturnType);
    }

    /**
     * Modify appearance
     * Updates instance appearance settings like branding, logos, headers, and footer messages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param title Instance title on the sign in / sign up page
     * @param description Markdown text shown on the sign in / sign up page
     * @param pwaName Name of the Progressive Web App. Used for the &#x60;name&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param pwaShortName Optional, short name for Progressive Web App
     * @param pwaDescription An explanation of what the Progressive Web App does. Used for the &#x60;description&#x60; attribute in &#x60;manifest.json&#x60;.
     * @param logo Instance image used on the sign in / sign up page
     * @param pwaIcon Icon used for Progressive Web App
     * @param headerLogo Instance image used for the main navigation bar
     * @param favicon Instance favicon in .ico/.png format
     * @param memberGuidelines Markdown text shown on the members page of a group or project. Shown only to users with permission to change members.
     * @param newProjectGuidelines Markdown text shown on the new project page
     * @param profileImageGuidelines Markdown text shown on the profile page below Public Avatar
     * @param headerMessage Message within the system header bar
     * @param footerMessage Message within the system footer bar
     * @param messageBackgroundColor Background color for the system header / footer bar
     * @param messageFontColor Font color for the system header / footer bar
     * @param emailHeaderAndFooterEnabled Add header and footer to all outgoing emails if enabled
     * @param siteName Last part of the webpage title. Defaults to empty.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ApplicationAppearanceWithResponseSpec(@jakarta.annotation.Nullable String title, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String pwaName, @jakarta.annotation.Nullable String pwaShortName, @jakarta.annotation.Nullable String pwaDescription, @jakarta.annotation.Nullable File logo, @jakarta.annotation.Nullable File pwaIcon, @jakarta.annotation.Nullable File headerLogo, @jakarta.annotation.Nullable File favicon, @jakarta.annotation.Nullable String memberGuidelines, @jakarta.annotation.Nullable String newProjectGuidelines, @jakarta.annotation.Nullable String profileImageGuidelines, @jakarta.annotation.Nullable String headerMessage, @jakarta.annotation.Nullable String footerMessage, @jakarta.annotation.Nullable String messageBackgroundColor, @jakarta.annotation.Nullable String messageFontColor, @jakarta.annotation.Nullable Boolean emailHeaderAndFooterEnabled, @jakarta.annotation.Nullable String siteName) throws RestClientResponseException {
        return putApiV4ApplicationAppearanceRequestCreation(title, description, pwaName, pwaShortName, pwaDescription, logo, pwaIcon, headerLogo, favicon, memberGuidelines, newProjectGuidelines, profileImageGuidelines, headerMessage, footerMessage, messageBackgroundColor, messageFontColor, emailHeaderAndFooterEnabled, siteName);
    }

    /**
     * Update application settings
     * Updates the current application settings for this GitLab instance. At least one setting must be updated.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param putApiV4ApplicationSettingsRequest The putApiV4ApplicationSettingsRequest parameter
     * @return APIEntitiesApplicationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ApplicationSettingsRequestCreation(@jakarta.annotation.Nullable PutApiV4ApplicationSettingsRequest putApiV4ApplicationSettingsRequest) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesApplicationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/settings", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ApplicationSettingsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update application settings
     * Updates the current application settings for this GitLab instance. At least one setting must be updated.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param putApiV4ApplicationSettingsRequest The putApiV4ApplicationSettingsRequest parameter
     * @return APIEntitiesApplicationSetting
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationSetting putApiV4ApplicationSettings(@jakarta.annotation.Nullable PutApiV4ApplicationSettingsRequest putApiV4ApplicationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ApplicationSettingsRequestCreation(putApiV4ApplicationSettingsRequest).body(localVarReturnType);
    }

    /**
     * Update application settings
     * Updates the current application settings for this GitLab instance. At least one setting must be updated.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param putApiV4ApplicationSettingsRequest The putApiV4ApplicationSettingsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApplicationSetting&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationSetting> putApiV4ApplicationSettingsWithHttpInfo(@jakarta.annotation.Nullable PutApiV4ApplicationSettingsRequest putApiV4ApplicationSettingsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationSetting> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ApplicationSettingsRequestCreation(putApiV4ApplicationSettingsRequest).toEntity(localVarReturnType);
    }

    /**
     * Update application settings
     * Updates the current application settings for this GitLab instance. At least one setting must be updated.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param putApiV4ApplicationSettingsRequest The putApiV4ApplicationSettingsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ApplicationSettingsWithResponseSpec(@jakarta.annotation.Nullable PutApiV4ApplicationSettingsRequest putApiV4ApplicationSettingsRequest) throws RestClientResponseException {
        return putApiV4ApplicationSettingsRequestCreation(putApiV4ApplicationSettingsRequest);
    }
}
