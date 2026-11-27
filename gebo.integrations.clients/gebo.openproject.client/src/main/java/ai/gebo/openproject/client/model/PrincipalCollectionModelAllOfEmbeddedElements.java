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
import ai.gebo.openproject.client.model.GroupModel;
import ai.gebo.openproject.client.model.GroupModelAllOfEmbedded;
import ai.gebo.openproject.client.model.GroupModelAllOfLinks;
import ai.gebo.openproject.client.model.PlaceholderUserModel;
import ai.gebo.openproject.client.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.net.URI;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PrincipalCollectionModelAllOfEmbeddedElements
 */
@JsonPropertyOrder({
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_HAL_TYPE,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_ID,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_NAME,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_CREATED_AT,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_UPDATED_AT,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_LINKS,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_AVATAR,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_LOGIN,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_FIRST_NAME,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_LAST_NAME,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_EMAIL,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_ADMIN,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_STATUS,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_LANGUAGE,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_IDENTITY_URL,
  PrincipalCollectionModelAllOfEmbeddedElements.JSON_PROPERTY_EMBEDDED
})
@JsonTypeName("PrincipalCollectionModel_allOf__embedded_elements")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class PrincipalCollectionModelAllOfEmbeddedElements {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    GROUP(String.valueOf("Group"));

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

  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nonnull
  private Integer id;

  public static final String JSON_PROPERTY_NAME = "name";
  @jakarta.annotation.Nonnull
  private String name;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nonnull
  private GroupModelAllOfLinks links;

  public static final String JSON_PROPERTY_AVATAR = "avatar";
  @jakarta.annotation.Nullable
  private URI avatar;

  public static final String JSON_PROPERTY_LOGIN = "login";
  @jakarta.annotation.Nullable
  private String login;

  public static final String JSON_PROPERTY_FIRST_NAME = "firstName";
  @jakarta.annotation.Nullable
  private String firstName;

  public static final String JSON_PROPERTY_LAST_NAME = "lastName";
  @jakarta.annotation.Nullable
  private String lastName;

  public static final String JSON_PROPERTY_EMAIL = "email";
  @jakarta.annotation.Nullable
  private String email;

  public static final String JSON_PROPERTY_ADMIN = "admin";
  @jakarta.annotation.Nullable
  private Boolean admin;

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nullable
  private String status;

  public static final String JSON_PROPERTY_LANGUAGE = "language";
  @jakarta.annotation.Nullable
  private String language;

  public static final String JSON_PROPERTY_IDENTITY_URL = "identityUrl";
  @jakarta.annotation.Nullable
  private String identityUrl;

  public static final String JSON_PROPERTY_EMBEDDED = "_embedded";
  @jakarta.annotation.Nonnull
  private GroupModelAllOfEmbedded embedded;

  public PrincipalCollectionModelAllOfEmbeddedElements() {
  }

  public PrincipalCollectionModelAllOfEmbeddedElements halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public PrincipalCollectionModelAllOfEmbeddedElements id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * The principal&#39;s unique identifier.
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

  public PrincipalCollectionModelAllOfEmbeddedElements name(@jakarta.annotation.Nonnull String name) {
    
    this.name = name;
    return this;
  }

  /**
   * The principal&#39;s display name, layout depends on instance settings.
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

  public PrincipalCollectionModelAllOfEmbeddedElements createdAt(@jakarta.annotation.Nullable OffsetDateTime createdAt) {
    
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

  public PrincipalCollectionModelAllOfEmbeddedElements updatedAt(@jakarta.annotation.Nullable OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change to the principal
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

  public PrincipalCollectionModelAllOfEmbeddedElements links(@jakarta.annotation.Nonnull GroupModelAllOfLinks links) {
    
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

  public GroupModelAllOfLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLinks(@jakarta.annotation.Nonnull GroupModelAllOfLinks links) {
    this.links = links;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements avatar(@jakarta.annotation.Nullable URI avatar) {
    
    this.avatar = avatar;
    return this;
  }

  /**
   * URL to user&#39;s avatar
   * @return avatar
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_AVATAR, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public URI getAvatar() {
    return avatar;
  }


  @JsonProperty(value = JSON_PROPERTY_AVATAR, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAvatar(@jakarta.annotation.Nullable URI avatar) {
    this.avatar = avatar;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements login(@jakarta.annotation.Nullable String login) {
    
    this.login = login;
    return this;
  }

  /**
   * The user&#39;s login name  # Conditions  - User is self, or &#x60;create_user&#x60; or &#x60;manage_user&#x60; permission globally
   * @return login
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LOGIN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLogin() {
    return login;
  }


  @JsonProperty(value = JSON_PROPERTY_LOGIN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLogin(@jakarta.annotation.Nullable String login) {
    this.login = login;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements firstName(@jakarta.annotation.Nullable String firstName) {
    
    this.firstName = firstName;
    return this;
  }

  /**
   * The user&#39;s first name  # Conditions  - User is self, or &#x60;create_user&#x60; or &#x60;manage_user&#x60; permission globally
   * @return firstName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_FIRST_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFirstName() {
    return firstName;
  }


  @JsonProperty(value = JSON_PROPERTY_FIRST_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFirstName(@jakarta.annotation.Nullable String firstName) {
    this.firstName = firstName;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements lastName(@jakarta.annotation.Nullable String lastName) {
    
    this.lastName = lastName;
    return this;
  }

  /**
   * The user&#39;s last name  # Conditions  - User is self, or &#x60;create_user&#x60; or &#x60;manage_user&#x60; permission globally
   * @return lastName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LAST_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLastName() {
    return lastName;
  }


  @JsonProperty(value = JSON_PROPERTY_LAST_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLastName(@jakarta.annotation.Nullable String lastName) {
    this.lastName = lastName;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements email(@jakarta.annotation.Nullable String email) {
    
    this.email = email;
    return this;
  }

  /**
   * The user&#39;s email address  # Conditions  - E-Mail address not hidden - User is not a new record - User is self, or &#x60;create_user&#x60; or &#x60;manage_user&#x60; permission globally
   * @return email
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_EMAIL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getEmail() {
    return email;
  }


  @JsonProperty(value = JSON_PROPERTY_EMAIL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEmail(@jakarta.annotation.Nullable String email) {
    this.email = email;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements admin(@jakarta.annotation.Nullable Boolean admin) {
    
    this.admin = admin;
    return this;
  }

  /**
   * Flag indicating whether or not the user is an admin  # Conditions  - &#x60;admin&#x60;
   * @return admin
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ADMIN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getAdmin() {
    return admin;
  }


  @JsonProperty(value = JSON_PROPERTY_ADMIN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAdmin(@jakarta.annotation.Nullable Boolean admin) {
    this.admin = admin;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements status(@jakarta.annotation.Nullable String status) {
    
    this.status = status;
    return this;
  }

  /**
   * The current activation status of the placeholder user.  # Conditions  - User has &#x60;manage_placeholder_user&#x60; permission globally
   * @return status
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATUS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getStatus() {
    return status;
  }


  @JsonProperty(value = JSON_PROPERTY_STATUS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStatus(@jakarta.annotation.Nullable String status) {
    this.status = status;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements language(@jakarta.annotation.Nullable String language) {
    
    this.language = language;
    return this;
  }

  /**
   * User&#39;s language | ISO 639-1 format  # Conditions  - User is self, or &#x60;create_user&#x60; or &#x60;manage_user&#x60; permission globally
   * @return language
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LANGUAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLanguage() {
    return language;
  }


  @JsonProperty(value = JSON_PROPERTY_LANGUAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLanguage(@jakarta.annotation.Nullable String language) {
    this.language = language;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements identityUrl(@jakarta.annotation.Nullable String identityUrl) {
    
    this.identityUrl = identityUrl;
    return this;
  }

  /**
   * User&#39;s identity_url for OmniAuth authentication. **Deprecated:** It will be removed in the near future.  # Conditions  - User is self, or &#x60;create_user&#x60; or &#x60;manage_user&#x60; permission globally
   * @return identityUrl
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_IDENTITY_URL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getIdentityUrl() {
    return identityUrl;
  }


  @JsonProperty(value = JSON_PROPERTY_IDENTITY_URL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentityUrl(@jakarta.annotation.Nullable String identityUrl) {
    this.identityUrl = identityUrl;
  }

  public PrincipalCollectionModelAllOfEmbeddedElements embedded(@jakarta.annotation.Nonnull GroupModelAllOfEmbedded embedded) {
    
    this.embedded = embedded;
    return this;
  }

  /**
   * Get embedded
   * @return embedded
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_EMBEDDED, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public GroupModelAllOfEmbedded getEmbedded() {
    return embedded;
  }


  @JsonProperty(value = JSON_PROPERTY_EMBEDDED, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEmbedded(@jakarta.annotation.Nonnull GroupModelAllOfEmbedded embedded) {
    this.embedded = embedded;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrincipalCollectionModelAllOfEmbeddedElements principalCollectionModelAllOfEmbeddedElements = (PrincipalCollectionModelAllOfEmbeddedElements) o;
    return Objects.equals(this.halType, principalCollectionModelAllOfEmbeddedElements.halType) &&
        Objects.equals(this.id, principalCollectionModelAllOfEmbeddedElements.id) &&
        Objects.equals(this.name, principalCollectionModelAllOfEmbeddedElements.name) &&
        Objects.equals(this.createdAt, principalCollectionModelAllOfEmbeddedElements.createdAt) &&
        Objects.equals(this.updatedAt, principalCollectionModelAllOfEmbeddedElements.updatedAt) &&
        Objects.equals(this.links, principalCollectionModelAllOfEmbeddedElements.links) &&
        Objects.equals(this.avatar, principalCollectionModelAllOfEmbeddedElements.avatar) &&
        Objects.equals(this.login, principalCollectionModelAllOfEmbeddedElements.login) &&
        Objects.equals(this.firstName, principalCollectionModelAllOfEmbeddedElements.firstName) &&
        Objects.equals(this.lastName, principalCollectionModelAllOfEmbeddedElements.lastName) &&
        Objects.equals(this.email, principalCollectionModelAllOfEmbeddedElements.email) &&
        Objects.equals(this.admin, principalCollectionModelAllOfEmbeddedElements.admin) &&
        Objects.equals(this.status, principalCollectionModelAllOfEmbeddedElements.status) &&
        Objects.equals(this.language, principalCollectionModelAllOfEmbeddedElements.language) &&
        Objects.equals(this.identityUrl, principalCollectionModelAllOfEmbeddedElements.identityUrl) &&
        Objects.equals(this.embedded, principalCollectionModelAllOfEmbeddedElements.embedded);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, id, name, createdAt, updatedAt, links, avatar, login, firstName, lastName, email, admin, status, language, identityUrl, embedded);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PrincipalCollectionModelAllOfEmbeddedElements {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    avatar: ").append(toIndentedString(avatar)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    admin: ").append(toIndentedString(admin)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    identityUrl: ").append(toIndentedString(identityUrl)).append("\n");
    sb.append("    embedded: ").append(toIndentedString(embedded)).append("\n");
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

