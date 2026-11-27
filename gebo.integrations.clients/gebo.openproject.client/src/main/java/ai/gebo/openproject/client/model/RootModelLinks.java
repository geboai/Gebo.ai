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
import ai.gebo.openproject.client.model.Link;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * RootModelLinks
 */
@JsonPropertyOrder({
  RootModelLinks.JSON_PROPERTY_SELF,
  RootModelLinks.JSON_PROPERTY_CONFIGURATION,
  RootModelLinks.JSON_PROPERTY_MEMBERSHIPS,
  RootModelLinks.JSON_PROPERTY_PRIORITIES,
  RootModelLinks.JSON_PROPERTY_RELATIONS,
  RootModelLinks.JSON_PROPERTY_STATUSES,
  RootModelLinks.JSON_PROPERTY_TIME_ENTRIES,
  RootModelLinks.JSON_PROPERTY_TYPES,
  RootModelLinks.JSON_PROPERTY_USER,
  RootModelLinks.JSON_PROPERTY_USER_PREFERENCES,
  RootModelLinks.JSON_PROPERTY_WORK_PACKAGES
})
@JsonTypeName("RootModel__links")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class RootModelLinks {
  public static final String JSON_PROPERTY_SELF = "self";
  @jakarta.annotation.Nonnull
  private Link self;

  public static final String JSON_PROPERTY_CONFIGURATION = "configuration";
  @jakarta.annotation.Nonnull
  private Link _configuration;

  public static final String JSON_PROPERTY_MEMBERSHIPS = "memberships";
  @jakarta.annotation.Nonnull
  private Link memberships;

  public static final String JSON_PROPERTY_PRIORITIES = "priorities";
  @jakarta.annotation.Nonnull
  private Link priorities;

  public static final String JSON_PROPERTY_RELATIONS = "relations";
  @jakarta.annotation.Nonnull
  private Link relations;

  public static final String JSON_PROPERTY_STATUSES = "statuses";
  @jakarta.annotation.Nonnull
  private Link statuses;

  public static final String JSON_PROPERTY_TIME_ENTRIES = "time_entries";
  @jakarta.annotation.Nonnull
  private Link timeEntries;

  public static final String JSON_PROPERTY_TYPES = "types";
  @jakarta.annotation.Nonnull
  private Link types;

  public static final String JSON_PROPERTY_USER = "user";
  @jakarta.annotation.Nonnull
  private Link user;

  public static final String JSON_PROPERTY_USER_PREFERENCES = "userPreferences";
  @jakarta.annotation.Nonnull
  private Link userPreferences;

  public static final String JSON_PROPERTY_WORK_PACKAGES = "workPackages";
  @jakarta.annotation.Nonnull
  private Link workPackages;

  public RootModelLinks() {
  }

  public RootModelLinks self(@jakarta.annotation.Nonnull Link self) {
    
    this.self = self;
    return this;
  }

  /**
   * This root information object.  **Resource**: Root
   * @return self
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_SELF, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getSelf() {
    return self;
  }


  @JsonProperty(value = JSON_PROPERTY_SELF, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSelf(@jakarta.annotation.Nonnull Link self) {
    this.self = self;
  }

  public RootModelLinks _configuration(@jakarta.annotation.Nonnull Link _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

  /**
   * The configuration resource.  **Resource**: Configuration
   * @return _configuration
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_CONFIGURATION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getConfiguration() {
    return _configuration;
  }


  @JsonProperty(value = JSON_PROPERTY_CONFIGURATION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setConfiguration(@jakarta.annotation.Nonnull Link _configuration) {
    this._configuration = _configuration;
  }

  public RootModelLinks memberships(@jakarta.annotation.Nonnull Link memberships) {
    
    this.memberships = memberships;
    return this;
  }

  /**
   * The collection of memberships.  **Resource**: Collection
   * @return memberships
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_MEMBERSHIPS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getMemberships() {
    return memberships;
  }


  @JsonProperty(value = JSON_PROPERTY_MEMBERSHIPS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMemberships(@jakarta.annotation.Nonnull Link memberships) {
    this.memberships = memberships;
  }

  public RootModelLinks priorities(@jakarta.annotation.Nonnull Link priorities) {
    
    this.priorities = priorities;
    return this;
  }

  /**
   * The collection of priorities.  **Resource**: Collection
   * @return priorities
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_PRIORITIES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getPriorities() {
    return priorities;
  }


  @JsonProperty(value = JSON_PROPERTY_PRIORITIES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPriorities(@jakarta.annotation.Nonnull Link priorities) {
    this.priorities = priorities;
  }

  public RootModelLinks relations(@jakarta.annotation.Nonnull Link relations) {
    
    this.relations = relations;
    return this;
  }

  /**
   * The collection of relations.  **Resource**: Collection
   * @return relations
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_RELATIONS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getRelations() {
    return relations;
  }


  @JsonProperty(value = JSON_PROPERTY_RELATIONS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRelations(@jakarta.annotation.Nonnull Link relations) {
    this.relations = relations;
  }

  public RootModelLinks statuses(@jakarta.annotation.Nonnull Link statuses) {
    
    this.statuses = statuses;
    return this;
  }

  /**
   * The collection of statuses.  **Resource**: Collection
   * @return statuses
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_STATUSES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getStatuses() {
    return statuses;
  }


  @JsonProperty(value = JSON_PROPERTY_STATUSES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatuses(@jakarta.annotation.Nonnull Link statuses) {
    this.statuses = statuses;
  }

  public RootModelLinks timeEntries(@jakarta.annotation.Nonnull Link timeEntries) {
    
    this.timeEntries = timeEntries;
    return this;
  }

  /**
   * The collection of time entries.  **Resource**: Collection
   * @return timeEntries
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_TIME_ENTRIES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getTimeEntries() {
    return timeEntries;
  }


  @JsonProperty(value = JSON_PROPERTY_TIME_ENTRIES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTimeEntries(@jakarta.annotation.Nonnull Link timeEntries) {
    this.timeEntries = timeEntries;
  }

  public RootModelLinks types(@jakarta.annotation.Nonnull Link types) {
    
    this.types = types;
    return this;
  }

  /**
   * The collection of types.  **Resource**: Collection
   * @return types
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_TYPES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getTypes() {
    return types;
  }


  @JsonProperty(value = JSON_PROPERTY_TYPES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTypes(@jakarta.annotation.Nonnull Link types) {
    this.types = types;
  }

  public RootModelLinks user(@jakarta.annotation.Nonnull Link user) {
    
    this.user = user;
    return this;
  }

  /**
   * The current user resource.  **Resource**: User
   * @return user
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_USER, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getUser() {
    return user;
  }


  @JsonProperty(value = JSON_PROPERTY_USER, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUser(@jakarta.annotation.Nonnull Link user) {
    this.user = user;
  }

  public RootModelLinks userPreferences(@jakarta.annotation.Nonnull Link userPreferences) {
    
    this.userPreferences = userPreferences;
    return this;
  }

  /**
   * The current user preferences resource.  **Resource**: UserPreferences
   * @return userPreferences
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_USER_PREFERENCES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getUserPreferences() {
    return userPreferences;
  }


  @JsonProperty(value = JSON_PROPERTY_USER_PREFERENCES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUserPreferences(@jakarta.annotation.Nonnull Link userPreferences) {
    this.userPreferences = userPreferences;
  }

  public RootModelLinks workPackages(@jakarta.annotation.Nonnull Link workPackages) {
    
    this.workPackages = workPackages;
    return this;
  }

  /**
   * The work package collection.  **Resource**: Collection
   * @return workPackages
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_WORK_PACKAGES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getWorkPackages() {
    return workPackages;
  }


  @JsonProperty(value = JSON_PROPERTY_WORK_PACKAGES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setWorkPackages(@jakarta.annotation.Nonnull Link workPackages) {
    this.workPackages = workPackages;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RootModelLinks rootModelLinks = (RootModelLinks) o;
    return Objects.equals(this.self, rootModelLinks.self) &&
        Objects.equals(this._configuration, rootModelLinks._configuration) &&
        Objects.equals(this.memberships, rootModelLinks.memberships) &&
        Objects.equals(this.priorities, rootModelLinks.priorities) &&
        Objects.equals(this.relations, rootModelLinks.relations) &&
        Objects.equals(this.statuses, rootModelLinks.statuses) &&
        Objects.equals(this.timeEntries, rootModelLinks.timeEntries) &&
        Objects.equals(this.types, rootModelLinks.types) &&
        Objects.equals(this.user, rootModelLinks.user) &&
        Objects.equals(this.userPreferences, rootModelLinks.userPreferences) &&
        Objects.equals(this.workPackages, rootModelLinks.workPackages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, _configuration, memberships, priorities, relations, statuses, timeEntries, types, user, userPreferences, workPackages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RootModelLinks {\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    memberships: ").append(toIndentedString(memberships)).append("\n");
    sb.append("    priorities: ").append(toIndentedString(priorities)).append("\n");
    sb.append("    relations: ").append(toIndentedString(relations)).append("\n");
    sb.append("    statuses: ").append(toIndentedString(statuses)).append("\n");
    sb.append("    timeEntries: ").append(toIndentedString(timeEntries)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    userPreferences: ").append(toIndentedString(userPreferences)).append("\n");
    sb.append("    workPackages: ").append(toIndentedString(workPackages)).append("\n");
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

