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
import ai.gebo.openproject.client.model.StorageFileModelAllOfLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * StorageFileModel
 */
@JsonPropertyOrder({
  StorageFileModel.JSON_PROPERTY_ID,
  StorageFileModel.JSON_PROPERTY_NAME,
  StorageFileModel.JSON_PROPERTY_MIME_TYPE,
  StorageFileModel.JSON_PROPERTY_SIZE,
  StorageFileModel.JSON_PROPERTY_CREATED_AT,
  StorageFileModel.JSON_PROPERTY_LAST_MODIFIED_AT,
  StorageFileModel.JSON_PROPERTY_CREATED_BY_NAME,
  StorageFileModel.JSON_PROPERTY_LAST_MODIFIED_BY_NAME,
  StorageFileModel.JSON_PROPERTY_HAL_TYPE,
  StorageFileModel.JSON_PROPERTY_LOCATION,
  StorageFileModel.JSON_PROPERTY_PERMISSIONS,
  StorageFileModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class StorageFileModel {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nonnull
  private String id;

  public static final String JSON_PROPERTY_NAME = "name";
  @jakarta.annotation.Nonnull
  private String name;

  public static final String JSON_PROPERTY_MIME_TYPE = "mimeType";
  @jakarta.annotation.Nullable
  private String mimeType;

  public static final String JSON_PROPERTY_SIZE = "size";
  @jakarta.annotation.Nullable
  private Integer size;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_LAST_MODIFIED_AT = "lastModifiedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime lastModifiedAt;

  public static final String JSON_PROPERTY_CREATED_BY_NAME = "createdByName";
  @jakarta.annotation.Nullable
  private String createdByName;

  public static final String JSON_PROPERTY_LAST_MODIFIED_BY_NAME = "lastModifiedByName";
  @jakarta.annotation.Nullable
  private String lastModifiedByName;

  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    STORAGE_FILE(String.valueOf("StorageFile"));

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

  public static final String JSON_PROPERTY_LOCATION = "location";
  @jakarta.annotation.Nonnull
  private String location;

  /**
   * Gets or Sets permissions
   */
  public enum PermissionsEnum {
    READABLE(String.valueOf("readable")),
    
    WRITABLE(String.valueOf("writable"));

    private String value;

    PermissionsEnum(String value) {
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
    public static PermissionsEnum fromValue(String value) {
      for (PermissionsEnum b : PermissionsEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_PERMISSIONS = "permissions";
  @jakarta.annotation.Nonnull
  private List<PermissionsEnum> permissions = new ArrayList<>();

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nonnull
  private StorageFileModelAllOfLinks links;

  public StorageFileModel() {
  }

  public StorageFileModel id(@jakarta.annotation.Nonnull String id) {
    
    this.id = id;
    return this;
  }

  /**
   * Linked file&#39;s id on the origin
   * @return id
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_ID, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getId() {
    return id;
  }


  @JsonProperty(value = JSON_PROPERTY_ID, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(@jakarta.annotation.Nonnull String id) {
    this.id = id;
  }

  public StorageFileModel name(@jakarta.annotation.Nonnull String name) {
    
    this.name = name;
    return this;
  }

  /**
   * Linked file&#39;s name on the origin
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

  public StorageFileModel mimeType(@jakarta.annotation.Nullable String mimeType) {
    
    this.mimeType = mimeType;
    return this;
  }

  /**
   * MIME type of the linked file.  To link a folder entity, the custom MIME type &#x60;application/x-op-directory&#x60; MUST be provided. Otherwise it defaults back to an unknown MIME type.
   * @return mimeType
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MIME_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getMimeType() {
    return mimeType;
  }


  @JsonProperty(value = JSON_PROPERTY_MIME_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMimeType(@jakarta.annotation.Nullable String mimeType) {
    this.mimeType = mimeType;
  }

  public StorageFileModel size(@jakarta.annotation.Nullable Integer size) {
    
    this.size = size;
    return this;
  }

  /**
   * file size on origin in bytes
   * minimum: 0
   * @return size
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SIZE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getSize() {
    return size;
  }


  @JsonProperty(value = JSON_PROPERTY_SIZE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSize(@jakarta.annotation.Nullable Integer size) {
    this.size = size;
  }

  public StorageFileModel createdAt(@jakarta.annotation.Nullable OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of the creation datetime of the file on the origin
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

  public StorageFileModel lastModifiedAt(@jakarta.annotation.Nullable OffsetDateTime lastModifiedAt) {
    
    this.lastModifiedAt = lastModifiedAt;
    return this;
  }

  /**
   * Timestamp of the datetime of the last modification of the file on the origin
   * @return lastModifiedAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LAST_MODIFIED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getLastModifiedAt() {
    return lastModifiedAt;
  }


  @JsonProperty(value = JSON_PROPERTY_LAST_MODIFIED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLastModifiedAt(@jakarta.annotation.Nullable OffsetDateTime lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  public StorageFileModel createdByName(@jakarta.annotation.Nullable String createdByName) {
    
    this.createdByName = createdByName;
    return this;
  }

  /**
   * Display name of the author that created the file on the origin
   * @return createdByName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATED_BY_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCreatedByName() {
    return createdByName;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATED_BY_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreatedByName(@jakarta.annotation.Nullable String createdByName) {
    this.createdByName = createdByName;
  }

  public StorageFileModel lastModifiedByName(@jakarta.annotation.Nullable String lastModifiedByName) {
    
    this.lastModifiedByName = lastModifiedByName;
    return this;
  }

  /**
   * Display name of the author that modified the file on the origin last
   * @return lastModifiedByName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LAST_MODIFIED_BY_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLastModifiedByName() {
    return lastModifiedByName;
  }


  @JsonProperty(value = JSON_PROPERTY_LAST_MODIFIED_BY_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLastModifiedByName(@jakarta.annotation.Nullable String lastModifiedByName) {
    this.lastModifiedByName = lastModifiedByName;
  }

  public StorageFileModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public StorageFileModel location(@jakarta.annotation.Nonnull String location) {
    
    this.location = location;
    return this;
  }

  /**
   * Location identification for file in storage
   * @return location
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_LOCATION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getLocation() {
    return location;
  }


  @JsonProperty(value = JSON_PROPERTY_LOCATION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLocation(@jakarta.annotation.Nonnull String location) {
    this.location = location;
  }

  public StorageFileModel permissions(@jakarta.annotation.Nonnull List<PermissionsEnum> permissions) {
    
    this.permissions = permissions;
    return this;
  }

  public StorageFileModel addPermissionsItem(PermissionsEnum permissionsItem) {
    if (this.permissions == null) {
      this.permissions = new ArrayList<>();
    }
    this.permissions.add(permissionsItem);
    return this;
  }

  /**
   * List of permissions for the authenticated user.
   * @return permissions
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_PERMISSIONS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<PermissionsEnum> getPermissions() {
    return permissions;
  }


  @JsonProperty(value = JSON_PROPERTY_PERMISSIONS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPermissions(@jakarta.annotation.Nonnull List<PermissionsEnum> permissions) {
    this.permissions = permissions;
  }

  public StorageFileModel links(@jakarta.annotation.Nonnull StorageFileModelAllOfLinks links) {
    
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

  public StorageFileModelAllOfLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLinks(@jakarta.annotation.Nonnull StorageFileModelAllOfLinks links) {
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
    StorageFileModel storageFileModel = (StorageFileModel) o;
    return Objects.equals(this.id, storageFileModel.id) &&
        Objects.equals(this.name, storageFileModel.name) &&
        Objects.equals(this.mimeType, storageFileModel.mimeType) &&
        Objects.equals(this.size, storageFileModel.size) &&
        Objects.equals(this.createdAt, storageFileModel.createdAt) &&
        Objects.equals(this.lastModifiedAt, storageFileModel.lastModifiedAt) &&
        Objects.equals(this.createdByName, storageFileModel.createdByName) &&
        Objects.equals(this.lastModifiedByName, storageFileModel.lastModifiedByName) &&
        Objects.equals(this.halType, storageFileModel.halType) &&
        Objects.equals(this.location, storageFileModel.location) &&
        Objects.equals(this.permissions, storageFileModel.permissions) &&
        Objects.equals(this.links, storageFileModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, mimeType, size, createdAt, lastModifiedAt, createdByName, lastModifiedByName, halType, location, permissions, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StorageFileModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    lastModifiedAt: ").append(toIndentedString(lastModifiedAt)).append("\n");
    sb.append("    createdByName: ").append(toIndentedString(createdByName)).append("\n");
    sb.append("    lastModifiedByName: ").append(toIndentedString(lastModifiedByName)).append("\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
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

