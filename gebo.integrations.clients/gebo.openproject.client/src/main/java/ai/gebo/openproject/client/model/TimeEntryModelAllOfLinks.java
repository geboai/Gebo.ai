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
 * TimeEntryModelAllOfLinks
 */
@JsonPropertyOrder({
  TimeEntryModelAllOfLinks.JSON_PROPERTY_SELF,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_UPDATE_IMMEDIATELY,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_UPDATE,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_DELETE,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_SCHEMA,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_PROJECT,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_WORK_PACKAGE,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_ENTITY,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_USER,
  TimeEntryModelAllOfLinks.JSON_PROPERTY_ACTIVITY
})
@JsonTypeName("TimeEntryModel_allOf__links")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class TimeEntryModelAllOfLinks {
  public static final String JSON_PROPERTY_SELF = "self";
  @jakarta.annotation.Nonnull
  private Link self;

  public static final String JSON_PROPERTY_UPDATE_IMMEDIATELY = "updateImmediately";
  @jakarta.annotation.Nullable
  private Link updateImmediately;

  public static final String JSON_PROPERTY_UPDATE = "update";
  @jakarta.annotation.Nullable
  private Link update;

  public static final String JSON_PROPERTY_DELETE = "delete";
  @jakarta.annotation.Nullable
  private Link delete;

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  @jakarta.annotation.Nullable
  private Link schema;

  public static final String JSON_PROPERTY_PROJECT = "project";
  @jakarta.annotation.Nonnull
  private Link project;

  public static final String JSON_PROPERTY_WORK_PACKAGE = "workPackage";
  @jakarta.annotation.Nullable
  private Link workPackage;

  public static final String JSON_PROPERTY_ENTITY = "entity";
  @jakarta.annotation.Nonnull
  private Link entity;

  public static final String JSON_PROPERTY_USER = "user";
  @jakarta.annotation.Nonnull
  private Link user;

  public static final String JSON_PROPERTY_ACTIVITY = "activity";
  @jakarta.annotation.Nonnull
  private Link activity;

  public TimeEntryModelAllOfLinks() {
  }

  public TimeEntryModelAllOfLinks self(@jakarta.annotation.Nonnull Link self) {
    
    this.self = self;
    return this;
  }

  /**
   * This time entry  **Resource**: TimeEntry
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

  public TimeEntryModelAllOfLinks updateImmediately(@jakarta.annotation.Nullable Link updateImmediately) {
    
    this.updateImmediately = updateImmediately;
    return this;
  }

  /**
   * Directly perform edits on this time entry  # Conditions  **Permission**: &#39;edit time entries&#39; or &#39;edit own time entries&#39; if the time entry belongs to the user
   * @return updateImmediately
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATE_IMMEDIATELY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getUpdateImmediately() {
    return updateImmediately;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATE_IMMEDIATELY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdateImmediately(@jakarta.annotation.Nullable Link updateImmediately) {
    this.updateImmediately = updateImmediately;
  }

  public TimeEntryModelAllOfLinks update(@jakarta.annotation.Nullable Link update) {
    
    this.update = update;
    return this;
  }

  /**
   * Form endpoint that aids in preparing and performing edits on a TimeEntry  # Conditions  **Permission**: &#39;edit time entries&#39; or &#39;edit own time entries&#39; if the time entry belongs to the user
   * @return update
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getUpdate() {
    return update;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdate(@jakarta.annotation.Nullable Link update) {
    this.update = update;
  }

  public TimeEntryModelAllOfLinks delete(@jakarta.annotation.Nullable Link delete) {
    
    this.delete = delete;
    return this;
  }

  /**
   * Delete this time entry  # Conditions  **Permission**: &#39;edit time entries&#39; or &#39;edit own time entries&#39; if the time entry belongs to the user
   * @return delete
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DELETE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getDelete() {
    return delete;
  }


  @JsonProperty(value = JSON_PROPERTY_DELETE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDelete(@jakarta.annotation.Nullable Link delete) {
    this.delete = delete;
  }

  public TimeEntryModelAllOfLinks schema(@jakarta.annotation.Nullable Link schema) {
    
    this.schema = schema;
    return this;
  }

  /**
   * The time entry schema  **Resource**: Schema
   * @return schema
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SCHEMA, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getSchema() {
    return schema;
  }


  @JsonProperty(value = JSON_PROPERTY_SCHEMA, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSchema(@jakarta.annotation.Nullable Link schema) {
    this.schema = schema;
  }

  public TimeEntryModelAllOfLinks project(@jakarta.annotation.Nonnull Link project) {
    
    this.project = project;
    return this;
  }

  /**
   * The project the time entry is bundled in. The project might be different from the work package&#39;s project once the workPackage is moved.  **Resource**: Project
   * @return project
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getProject() {
    return project;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setProject(@jakarta.annotation.Nonnull Link project) {
    this.project = project;
  }

  public TimeEntryModelAllOfLinks workPackage(@jakarta.annotation.Nullable Link workPackage) {
    
    this.workPackage = workPackage;
    return this;
  }

  /**
   * The work package the time entry is created on  **Resource**: WorkPackage  # Conditions  Only rendered if the entity the time entry is created on is a work package.  **Deprecated**: this field is being replaced by &#x60;entity&#x60;, which also supports meetings. Read and write &#x60;entity&#x60; instead.
   * @return workPackage
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_WORK_PACKAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getWorkPackage() {
    return workPackage;
  }


  @JsonProperty(value = JSON_PROPERTY_WORK_PACKAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWorkPackage(@jakarta.annotation.Nullable Link workPackage) {
    this.workPackage = workPackage;
  }

  public TimeEntryModelAllOfLinks entity(@jakarta.annotation.Nonnull Link entity) {
    
    this.entity = entity;
    return this;
  }

  /**
   * The entity the time entry is created on  **Resource**: WorkPackage
   * @return entity
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_ENTITY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getEntity() {
    return entity;
  }


  @JsonProperty(value = JSON_PROPERTY_ENTITY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEntity(@jakarta.annotation.Nonnull Link entity) {
    this.entity = entity;
  }

  public TimeEntryModelAllOfLinks user(@jakarta.annotation.Nonnull Link user) {
    
    this.user = user;
    return this;
  }

  /**
   * The user the time entry tracks expenditures for  **Resource**: User
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

  public TimeEntryModelAllOfLinks activity(@jakarta.annotation.Nonnull Link activity) {
    
    this.activity = activity;
    return this;
  }

  /**
   * The time entry activity the time entry is categorized as  **Resource**: TimeEntriesActivity
   * @return activity
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_ACTIVITY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getActivity() {
    return activity;
  }


  @JsonProperty(value = JSON_PROPERTY_ACTIVITY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setActivity(@jakarta.annotation.Nonnull Link activity) {
    this.activity = activity;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeEntryModelAllOfLinks timeEntryModelAllOfLinks = (TimeEntryModelAllOfLinks) o;
    return Objects.equals(this.self, timeEntryModelAllOfLinks.self) &&
        Objects.equals(this.updateImmediately, timeEntryModelAllOfLinks.updateImmediately) &&
        Objects.equals(this.update, timeEntryModelAllOfLinks.update) &&
        Objects.equals(this.delete, timeEntryModelAllOfLinks.delete) &&
        Objects.equals(this.schema, timeEntryModelAllOfLinks.schema) &&
        Objects.equals(this.project, timeEntryModelAllOfLinks.project) &&
        Objects.equals(this.workPackage, timeEntryModelAllOfLinks.workPackage) &&
        Objects.equals(this.entity, timeEntryModelAllOfLinks.entity) &&
        Objects.equals(this.user, timeEntryModelAllOfLinks.user) &&
        Objects.equals(this.activity, timeEntryModelAllOfLinks.activity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, updateImmediately, update, delete, schema, project, workPackage, entity, user, activity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimeEntryModelAllOfLinks {\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    updateImmediately: ").append(toIndentedString(updateImmediately)).append("\n");
    sb.append("    update: ").append(toIndentedString(update)).append("\n");
    sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    workPackage: ").append(toIndentedString(workPackage)).append("\n");
    sb.append("    entity: ").append(toIndentedString(entity)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    activity: ").append(toIndentedString(activity)).append("\n");
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

