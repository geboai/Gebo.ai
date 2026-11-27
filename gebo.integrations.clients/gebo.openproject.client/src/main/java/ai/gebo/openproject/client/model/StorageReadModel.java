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


package ai.gebo.openproject.client.model;

import java.util.Objects;
import java.util.Arrays;
import ai.gebo.openproject.client.model.StorageReadModelEmbedded;
import ai.gebo.openproject.client.model.StorageReadModelLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * StorageReadModel
 */
@JsonPropertyOrder({
  StorageReadModel.JSON_PROPERTY_ID,
  StorageReadModel.JSON_PROPERTY_HAL_TYPE,
  StorageReadModel.JSON_PROPERTY_NAME,
  StorageReadModel.JSON_PROPERTY_STORAGE_AUDIENCE,
  StorageReadModel.JSON_PROPERTY_TOKEN_EXCHANGE_SCOPE,
  StorageReadModel.JSON_PROPERTY_TENANT_ID,
  StorageReadModel.JSON_PROPERTY_DRIVE_ID,
  StorageReadModel.JSON_PROPERTY_HAS_APPLICATION_PASSWORD,
  StorageReadModel.JSON_PROPERTY_FORBIDDEN_FILE_NAME_CHARACTERS,
  StorageReadModel.JSON_PROPERTY_CREATED_AT,
  StorageReadModel.JSON_PROPERTY_UPDATED_AT,
  StorageReadModel.JSON_PROPERTY_CONFIGURED,
  StorageReadModel.JSON_PROPERTY_EMBEDDED,
  StorageReadModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class StorageReadModel {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nonnull
  private Integer id;

  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    STORAGE(String.valueOf("Storage"));

    private String value;

    HalTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static HalTypeEnum fromValue(String value) {
      for (HalTypeEnum b : HalTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_HAL_TYPE = "_type";
  @jakarta.annotation.Nonnull
  private HalTypeEnum halType;

  public static final String JSON_PROPERTY_NAME = "name";
  @jakarta.annotation.Nonnull
  private String name;

  public static final String JSON_PROPERTY_STORAGE_AUDIENCE = "storageAudience";
  @jakarta.annotation.Nullable
  private String storageAudience;

  public static final String JSON_PROPERTY_TOKEN_EXCHANGE_SCOPE = "tokenExchangeScope";
  @jakarta.annotation.Nullable
  private String tokenExchangeScope;

  public static final String JSON_PROPERTY_TENANT_ID = "tenantId";
  @jakarta.annotation.Nullable
  private String tenantId;

  public static final String JSON_PROPERTY_DRIVE_ID = "driveId";
  @jakarta.annotation.Nullable
  private String driveId;

  public static final String JSON_PROPERTY_HAS_APPLICATION_PASSWORD = "hasApplicationPassword";
  @jakarta.annotation.Nullable
  private Boolean hasApplicationPassword;

  public static final String JSON_PROPERTY_FORBIDDEN_FILE_NAME_CHARACTERS = "forbiddenFileNameCharacters";
  @jakarta.annotation.Nullable
  private String forbiddenFileNameCharacters;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_CONFIGURED = "configured";
  @jakarta.annotation.Nullable
  private Boolean configured;

  public static final String JSON_PROPERTY_EMBEDDED = "_embedded";
  @jakarta.annotation.Nullable
  private StorageReadModelEmbedded embedded;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nonnull
  private StorageReadModelLinks links;

  public StorageReadModel() {
  }

  public StorageReadModel id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * Storage id
   * @return id
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_ID, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getId() {
    return id;
  }


  @JsonProperty(value = JSON_PROPERTY_ID, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(@jakarta.annotation.Nonnull Integer id) {
    this.id = id;
  }

  public StorageReadModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
    this.halType = halType;
    return this;
  }

  /**
   * Get halType
   * @return halType
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_HAL_TYPE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public HalTypeEnum getHalType() {
    return halType;
  }


  @JsonProperty(value = JSON_PROPERTY_HAL_TYPE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setHalType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    this.halType = halType;
  }

  public StorageReadModel name(@jakarta.annotation.Nonnull String name) {
    
    this.name = name;
    return this;
  }

  /**
   * Storage name
   * @return name
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_NAME, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getName() {
    return name;
  }


  @JsonProperty(value = JSON_PROPERTY_NAME, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(@jakarta.annotation.Nonnull String name) {
    this.name = name;
  }

  public StorageReadModel storageAudience(@jakarta.annotation.Nullable String storageAudience) {
    
    this.storageAudience = storageAudience;
    return this;
  }

  /**
   * The audience that the storage expects in tokens for requests to it, usually the storage&#39;s client ID at the identity provider.  This is only required for authentication through single-sign-on and so far only supported for provider type Nextcloud.
   * @return storageAudience
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STORAGE_AUDIENCE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getStorageAudience() {
    return storageAudience;
  }


  @JsonProperty(value = JSON_PROPERTY_STORAGE_AUDIENCE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStorageAudience(@jakarta.annotation.Nullable String storageAudience) {
    this.storageAudience = storageAudience;
  }

  public StorageReadModel tokenExchangeScope(@jakarta.annotation.Nullable String tokenExchangeScope) {
    
    this.tokenExchangeScope = tokenExchangeScope;
    return this;
  }

  /**
   * The scope that will be requested when requesting a token for the storage through token exchange. Has no effect if no token exchange is performed.  This is only required for authentication through single-sign-on and so far only supported for provider type Nextcloud.
   * @return tokenExchangeScope
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TOKEN_EXCHANGE_SCOPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTokenExchangeScope() {
    return tokenExchangeScope;
  }


  @JsonProperty(value = JSON_PROPERTY_TOKEN_EXCHANGE_SCOPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTokenExchangeScope(@jakarta.annotation.Nullable String tokenExchangeScope) {
    this.tokenExchangeScope = tokenExchangeScope;
  }

  public StorageReadModel tenantId(@jakarta.annotation.Nullable String tenantId) {
    
    this.tenantId = tenantId;
    return this;
  }

  /**
   * The tenant id of a file storage of type OneDrive.  Ignored if the provider type is not OneDrive. May be null if the storage is not configured completely.
   * @return tenantId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TENANT_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTenantId() {
    return tenantId;
  }


  @JsonProperty(value = JSON_PROPERTY_TENANT_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTenantId(@jakarta.annotation.Nullable String tenantId) {
    this.tenantId = tenantId;
  }

  public StorageReadModel driveId(@jakarta.annotation.Nullable String driveId) {
    
    this.driveId = driveId;
    return this;
  }

  /**
   * The drive id of a file storage of type OneDrive.  Ignored if the provider type is not OneDrive. May be null if the storage is not configured completely.
   * @return driveId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DRIVE_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDriveId() {
    return driveId;
  }


  @JsonProperty(value = JSON_PROPERTY_DRIVE_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDriveId(@jakarta.annotation.Nullable String driveId) {
    this.driveId = driveId;
  }

  public StorageReadModel hasApplicationPassword(@jakarta.annotation.Nullable Boolean hasApplicationPassword) {
    
    this.hasApplicationPassword = hasApplicationPassword;
    return this;
  }

  /**
   * Whether the storage has the application password to use for the Nextcloud storage.  Ignored if the provider type is not Nextcloud.
   * @return hasApplicationPassword
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HAS_APPLICATION_PASSWORD, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getHasApplicationPassword() {
    return hasApplicationPassword;
  }


  @JsonProperty(value = JSON_PROPERTY_HAS_APPLICATION_PASSWORD, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHasApplicationPassword(@jakarta.annotation.Nullable Boolean hasApplicationPassword) {
    this.hasApplicationPassword = hasApplicationPassword;
  }

  public StorageReadModel forbiddenFileNameCharacters(@jakarta.annotation.Nullable String forbiddenFileNameCharacters) {
    
    this.forbiddenFileNameCharacters = forbiddenFileNameCharacters;
    return this;
  }

  /**
   * A string with all the characters forbidden to be used for file and folder names in the storage. Used by OpenProject to avoid creating files with unsupported names (e.g. when creating project folders).  Only supported for provider type Nextcloud so far.
   * @return forbiddenFileNameCharacters
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_FORBIDDEN_FILE_NAME_CHARACTERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getForbiddenFileNameCharacters() {
    return forbiddenFileNameCharacters;
  }


  @JsonProperty(value = JSON_PROPERTY_FORBIDDEN_FILE_NAME_CHARACTERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setForbiddenFileNameCharacters(@jakarta.annotation.Nullable String forbiddenFileNameCharacters) {
    this.forbiddenFileNameCharacters = forbiddenFileNameCharacters;
  }

  public StorageReadModel createdAt(@jakarta.annotation.Nullable OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Time of creation
   * @return createdAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreatedAt(@jakarta.annotation.Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public StorageReadModel updatedAt(@jakarta.annotation.Nullable OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change to the storage
   * @return updatedAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdatedAt(@jakarta.annotation.Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public StorageReadModel configured(@jakarta.annotation.Nullable Boolean configured) {
    
    this.configured = configured;
    return this;
  }

  /**
   * Indication, if the storage is fully configured
   * @return configured
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CONFIGURED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getConfigured() {
    return configured;
  }


  @JsonProperty(value = JSON_PROPERTY_CONFIGURED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConfigured(@jakarta.annotation.Nullable Boolean configured) {
    this.configured = configured;
  }

  public StorageReadModel embedded(@jakarta.annotation.Nullable StorageReadModelEmbedded embedded) {
    
    this.embedded = embedded;
    return this;
  }

  /**
   * Get embedded
   * @return embedded
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_EMBEDDED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public StorageReadModelEmbedded getEmbedded() {
    return embedded;
  }


  @JsonProperty(value = JSON_PROPERTY_EMBEDDED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEmbedded(@jakarta.annotation.Nullable StorageReadModelEmbedded embedded) {
    this.embedded = embedded;
  }

  public StorageReadModel links(@jakarta.annotation.Nonnull StorageReadModelLinks links) {
    
    this.links = links;
    return this;
  }

  /**
   * Get links
   * @return links
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_LINKS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public StorageReadModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLinks(@jakarta.annotation.Nonnull StorageReadModelLinks links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StorageReadModel storageReadModel = (StorageReadModel) o;
    return Objects.equals(this.id, storageReadModel.id) &&
        Objects.equals(this.halType, storageReadModel.halType) &&
        Objects.equals(this.name, storageReadModel.name) &&
        Objects.equals(this.storageAudience, storageReadModel.storageAudience) &&
        Objects.equals(this.tokenExchangeScope, storageReadModel.tokenExchangeScope) &&
        Objects.equals(this.tenantId, storageReadModel.tenantId) &&
        Objects.equals(this.driveId, storageReadModel.driveId) &&
        Objects.equals(this.hasApplicationPassword, storageReadModel.hasApplicationPassword) &&
        Objects.equals(this.forbiddenFileNameCharacters, storageReadModel.forbiddenFileNameCharacters) &&
        Objects.equals(this.createdAt, storageReadModel.createdAt) &&
        Objects.equals(this.updatedAt, storageReadModel.updatedAt) &&
        Objects.equals(this.configured, storageReadModel.configured) &&
        Objects.equals(this.embedded, storageReadModel.embedded) &&
        Objects.equals(this.links, storageReadModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, halType, name, storageAudience, tokenExchangeScope, tenantId, driveId, hasApplicationPassword, forbiddenFileNameCharacters, createdAt, updatedAt, configured, embedded, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StorageReadModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    storageAudience: ").append(toIndentedString(storageAudience)).append("\n");
    sb.append("    tokenExchangeScope: ").append(toIndentedString(tokenExchangeScope)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    driveId: ").append(toIndentedString(driveId)).append("\n");
    sb.append("    hasApplicationPassword: ").append(toIndentedString(hasApplicationPassword)).append("\n");
    sb.append("    forbiddenFileNameCharacters: ").append(toIndentedString(forbiddenFileNameCharacters)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    configured: ").append(toIndentedString(configured)).append("\n");
    sb.append("    embedded: ").append(toIndentedString(embedded)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }

}

