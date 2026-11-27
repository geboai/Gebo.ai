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
import ai.gebo.openproject.client.model.Formattable;
import ai.gebo.openproject.client.model.VersionReadModelAllOfLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Collection of Versions
 */
@JsonPropertyOrder({
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_ID,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_HAL_TYPE,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_NAME,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_DESCRIPTION,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_START_DATE,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_END_DATE,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_STATUS,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_SHARING,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_CREATED_AT,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_UPDATED_AT,
  VersionsByWorkspaceModelAllOfEmbeddedElements.JSON_PROPERTY_LINKS
})
@JsonTypeName("Versions_by_WorkspaceModel_allOf__embedded_elements")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class VersionsByWorkspaceModelAllOfEmbeddedElements {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nonnull
  private Integer id;

  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    VERSION(String.valueOf("Version"));

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

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  @jakarta.annotation.Nonnull
  private Formattable description;

  public static final String JSON_PROPERTY_START_DATE = "startDate";
  @jakarta.annotation.Nullable
  private LocalDate startDate;

  public static final String JSON_PROPERTY_END_DATE = "endDate";
  @jakarta.annotation.Nullable
  private LocalDate endDate;

  /**
   * The current status of the version. This could be:  - *open*: if the version is available to be assigned to work packages in all shared projects - *locked*: if the version is not finished, but locked for further assignments to work packages - *closed*: if the version is finished
   */
  public enum StatusEnum {
    OPEN(String.valueOf("open")),
    
    LOCKED(String.valueOf("locked")),
    
    CLOSED(String.valueOf("closed"));

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nonnull
  private StatusEnum status;

  /**
   * The indicator of how the version is shared between projects. This could be:  - *none*: if the version is only available in the defining project - *descendants*: if the version is shared with the descendants of the defining project - *hierarchy*: if the version is shared with the descendants and the ancestors of the defining project - *tree*: if the version is shared with the root project of the defining project and all descendants of the root project - *system*: if the version is shared globally
   */
  public enum SharingEnum {
    NONE(String.valueOf("none")),
    
    DESCENDANTS(String.valueOf("descendants")),
    
    HIERARCHY(String.valueOf("hierarchy")),
    
    TREE(String.valueOf("tree")),
    
    SYSTEM(String.valueOf("system"));

    private String value;

    SharingEnum(String value) {
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
    public static SharingEnum fromValue(String value) {
      for (SharingEnum b : SharingEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_SHARING = "sharing";
  @jakarta.annotation.Nonnull
  private SharingEnum sharing;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private VersionReadModelAllOfLinks links;

  public VersionsByWorkspaceModelAllOfEmbeddedElements() {
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * Version id
   * minimum: 1
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

  public VersionsByWorkspaceModelAllOfEmbeddedElements halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public VersionsByWorkspaceModelAllOfEmbeddedElements name(@jakarta.annotation.Nonnull String name) {
    
    this.name = name;
    return this;
  }

  /**
   * Version name
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

  public VersionsByWorkspaceModelAllOfEmbeddedElements description(@jakarta.annotation.Nonnull Formattable description) {
    
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Formattable getDescription() {
    return description;
  }


  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDescription(@jakarta.annotation.Nonnull Formattable description) {
    this.description = description;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements startDate(@jakarta.annotation.Nullable LocalDate startDate) {
    
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LocalDate getStartDate() {
    return startDate;
  }


  @JsonProperty(value = JSON_PROPERTY_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStartDate(@jakarta.annotation.Nullable LocalDate startDate) {
    this.startDate = startDate;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements endDate(@jakarta.annotation.Nullable LocalDate endDate) {
    
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_END_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LocalDate getEndDate() {
    return endDate;
  }


  @JsonProperty(value = JSON_PROPERTY_END_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEndDate(@jakarta.annotation.Nullable LocalDate endDate) {
    this.endDate = endDate;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements status(@jakarta.annotation.Nonnull StatusEnum status) {
    
    this.status = status;
    return this;
  }

  /**
   * The current status of the version. This could be:  - *open*: if the version is available to be assigned to work packages in all shared projects - *locked*: if the version is not finished, but locked for further assignments to work packages - *closed*: if the version is finished
   * @return status
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_STATUS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public StatusEnum getStatus() {
    return status;
  }


  @JsonProperty(value = JSON_PROPERTY_STATUS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(@jakarta.annotation.Nonnull StatusEnum status) {
    this.status = status;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements sharing(@jakarta.annotation.Nonnull SharingEnum sharing) {
    
    this.sharing = sharing;
    return this;
  }

  /**
   * The indicator of how the version is shared between projects. This could be:  - *none*: if the version is only available in the defining project - *descendants*: if the version is shared with the descendants of the defining project - *hierarchy*: if the version is shared with the descendants and the ancestors of the defining project - *tree*: if the version is shared with the root project of the defining project and all descendants of the root project - *system*: if the version is shared globally
   * @return sharing
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_SHARING, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public SharingEnum getSharing() {
    return sharing;
  }


  @JsonProperty(value = JSON_PROPERTY_SHARING, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSharing(@jakarta.annotation.Nonnull SharingEnum sharing) {
    this.sharing = sharing;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements createdAt(@jakarta.annotation.Nonnull OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Time of creation
   * @return createdAt
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCreatedAt(@jakarta.annotation.Nonnull OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements updatedAt(@jakarta.annotation.Nonnull OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change to the version
   * @return updatedAt
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUpdatedAt(@jakarta.annotation.Nonnull OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public VersionsByWorkspaceModelAllOfEmbeddedElements links(@jakarta.annotation.Nullable VersionReadModelAllOfLinks links) {
    
    this.links = links;
    return this;
  }

  /**
   * Get links
   * @return links
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public VersionReadModelAllOfLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable VersionReadModelAllOfLinks links) {
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
    VersionsByWorkspaceModelAllOfEmbeddedElements versionsByWorkspaceModelAllOfEmbeddedElements = (VersionsByWorkspaceModelAllOfEmbeddedElements) o;
    return Objects.equals(this.id, versionsByWorkspaceModelAllOfEmbeddedElements.id) &&
        Objects.equals(this.halType, versionsByWorkspaceModelAllOfEmbeddedElements.halType) &&
        Objects.equals(this.name, versionsByWorkspaceModelAllOfEmbeddedElements.name) &&
        Objects.equals(this.description, versionsByWorkspaceModelAllOfEmbeddedElements.description) &&
        Objects.equals(this.startDate, versionsByWorkspaceModelAllOfEmbeddedElements.startDate) &&
        Objects.equals(this.endDate, versionsByWorkspaceModelAllOfEmbeddedElements.endDate) &&
        Objects.equals(this.status, versionsByWorkspaceModelAllOfEmbeddedElements.status) &&
        Objects.equals(this.sharing, versionsByWorkspaceModelAllOfEmbeddedElements.sharing) &&
        Objects.equals(this.createdAt, versionsByWorkspaceModelAllOfEmbeddedElements.createdAt) &&
        Objects.equals(this.updatedAt, versionsByWorkspaceModelAllOfEmbeddedElements.updatedAt) &&
        Objects.equals(this.links, versionsByWorkspaceModelAllOfEmbeddedElements.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, halType, name, description, startDate, endDate, status, sharing, createdAt, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersionsByWorkspaceModelAllOfEmbeddedElements {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    sharing: ").append(toIndentedString(sharing)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

