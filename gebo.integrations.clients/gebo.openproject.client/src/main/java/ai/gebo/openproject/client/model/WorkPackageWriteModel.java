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
import ai.gebo.openproject.client.model.WorkPackageWriteModelLinks;
import ai.gebo.openproject.client.model.WorkPackageWriteModelMeta;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This model is used for creating and updating work packages. It can also be used for validation against the work package form endpoints.
 */
@JsonPropertyOrder({
  WorkPackageWriteModel.JSON_PROPERTY_SUBJECT,
  WorkPackageWriteModel.JSON_PROPERTY_DESCRIPTION,
  WorkPackageWriteModel.JSON_PROPERTY_SCHEDULE_MANUALLY,
  WorkPackageWriteModel.JSON_PROPERTY_START_DATE,
  WorkPackageWriteModel.JSON_PROPERTY_DUE_DATE,
  WorkPackageWriteModel.JSON_PROPERTY_ESTIMATED_TIME,
  WorkPackageWriteModel.JSON_PROPERTY_DURATION,
  WorkPackageWriteModel.JSON_PROPERTY_IGNORE_NON_WORKING_DAYS,
  WorkPackageWriteModel.JSON_PROPERTY_LINKS,
  WorkPackageWriteModel.JSON_PROPERTY_META
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class WorkPackageWriteModel {
  public static final String JSON_PROPERTY_SUBJECT = "subject";
  @jakarta.annotation.Nullable
  private String subject;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  @jakarta.annotation.Nullable
  private Formattable description;

  public static final String JSON_PROPERTY_SCHEDULE_MANUALLY = "scheduleManually";
  @jakarta.annotation.Nullable
  private Boolean scheduleManually;

  public static final String JSON_PROPERTY_START_DATE = "startDate";
  @jakarta.annotation.Nullable
  private LocalDate startDate;

  public static final String JSON_PROPERTY_DUE_DATE = "dueDate";
  @jakarta.annotation.Nullable
  private LocalDate dueDate;

  public static final String JSON_PROPERTY_ESTIMATED_TIME = "estimatedTime";
  @jakarta.annotation.Nullable
  private String estimatedTime;

  public static final String JSON_PROPERTY_DURATION = "duration";
  @jakarta.annotation.Nullable
  private String duration;

  public static final String JSON_PROPERTY_IGNORE_NON_WORKING_DAYS = "ignoreNonWorkingDays";
  @jakarta.annotation.Nullable
  private Boolean ignoreNonWorkingDays;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private WorkPackageWriteModelLinks links;

  public static final String JSON_PROPERTY_META = "_meta";
  @jakarta.annotation.Nullable
  private WorkPackageWriteModelMeta meta;

  public WorkPackageWriteModel() {
  }

  public WorkPackageWriteModel subject(@jakarta.annotation.Nullable String subject) {
    
    this.subject = subject;
    return this;
  }

  /**
   * Work package subject
   * @return subject
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SUBJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSubject() {
    return subject;
  }


  @JsonProperty(value = JSON_PROPERTY_SUBJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSubject(@jakarta.annotation.Nullable String subject) {
    this.subject = subject;
  }

  public WorkPackageWriteModel description(@jakarta.annotation.Nullable Formattable description) {
    
    this.description = description;
    return this;
  }

  /**
   * The work package description
   * @return description
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Formattable getDescription() {
    return description;
  }


  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDescription(@jakarta.annotation.Nullable Formattable description) {
    this.description = description;
  }

  public WorkPackageWriteModel scheduleManually(@jakarta.annotation.Nullable Boolean scheduleManually) {
    
    this.scheduleManually = scheduleManually;
    return this;
  }

  /**
   * Uses manual scheduling mode when true (default). Uses automatic scheduling mode when false. Can be automatic only when predecessors or children are present.
   * @return scheduleManually
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SCHEDULE_MANUALLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getScheduleManually() {
    return scheduleManually;
  }


  @JsonProperty(value = JSON_PROPERTY_SCHEDULE_MANUALLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setScheduleManually(@jakarta.annotation.Nullable Boolean scheduleManually) {
    this.scheduleManually = scheduleManually;
  }

  public WorkPackageWriteModel startDate(@jakarta.annotation.Nullable LocalDate startDate) {
    
    this.startDate = startDate;
    return this;
  }

  /**
   * Scheduled beginning of a work package
   * @return startDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getStartDate() {
    return startDate;
  }


  @JsonProperty(value = JSON_PROPERTY_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStartDate(@jakarta.annotation.Nullable LocalDate startDate) {
    this.startDate = startDate;
  }

  public WorkPackageWriteModel dueDate(@jakarta.annotation.Nullable LocalDate dueDate) {
    
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Scheduled end of a work package
   * @return dueDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getDueDate() {
    return dueDate;
  }


  @JsonProperty(value = JSON_PROPERTY_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDueDate(@jakarta.annotation.Nullable LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public WorkPackageWriteModel estimatedTime(@jakarta.annotation.Nullable String estimatedTime) {
    
    this.estimatedTime = estimatedTime;
    return this;
  }

  /**
   * Time a work package likely needs to be completed excluding its descendants
   * @return estimatedTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getEstimatedTime() {
    return estimatedTime;
  }


  @JsonProperty(value = JSON_PROPERTY_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEstimatedTime(@jakarta.annotation.Nullable String estimatedTime) {
    this.estimatedTime = estimatedTime;
  }

  public WorkPackageWriteModel duration(@jakarta.annotation.Nullable String duration) {
    
    this.duration = duration;
    return this;
  }

  /**
   * The amount of time in hours the work package needs to be completed. This value must be bigger or equal to &#x60;P1D&#x60;, and any the value will get floored to the nearest day.  The duration has no effect, unless either a start date or a due date is set.  Not available for milestone type of work packages.
   * @return duration
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DURATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDuration() {
    return duration;
  }


  @JsonProperty(value = JSON_PROPERTY_DURATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDuration(@jakarta.annotation.Nullable String duration) {
    this.duration = duration;
  }

  public WorkPackageWriteModel ignoreNonWorkingDays(@jakarta.annotation.Nullable Boolean ignoreNonWorkingDays) {
    
    this.ignoreNonWorkingDays = ignoreNonWorkingDays;
    return this;
  }

  /**
   * When scheduling, whether or not to ignore the non working days being defined. A work package with the flag set to true will be allowed to be scheduled to a non working day.
   * @return ignoreNonWorkingDays
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_IGNORE_NON_WORKING_DAYS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getIgnoreNonWorkingDays() {
    return ignoreNonWorkingDays;
  }


  @JsonProperty(value = JSON_PROPERTY_IGNORE_NON_WORKING_DAYS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIgnoreNonWorkingDays(@jakarta.annotation.Nullable Boolean ignoreNonWorkingDays) {
    this.ignoreNonWorkingDays = ignoreNonWorkingDays;
  }

  public WorkPackageWriteModel links(@jakarta.annotation.Nullable WorkPackageWriteModelLinks links) {
    
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

  public WorkPackageWriteModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable WorkPackageWriteModelLinks links) {
    this.links = links;
  }

  public WorkPackageWriteModel meta(@jakarta.annotation.Nullable WorkPackageWriteModelMeta meta) {
    
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   * @return meta
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_META, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public WorkPackageWriteModelMeta getMeta() {
    return meta;
  }


  @JsonProperty(value = JSON_PROPERTY_META, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMeta(@jakarta.annotation.Nullable WorkPackageWriteModelMeta meta) {
    this.meta = meta;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkPackageWriteModel workPackageWriteModel = (WorkPackageWriteModel) o;
    return Objects.equals(this.subject, workPackageWriteModel.subject) &&
        Objects.equals(this.description, workPackageWriteModel.description) &&
        Objects.equals(this.scheduleManually, workPackageWriteModel.scheduleManually) &&
        Objects.equals(this.startDate, workPackageWriteModel.startDate) &&
        Objects.equals(this.dueDate, workPackageWriteModel.dueDate) &&
        Objects.equals(this.estimatedTime, workPackageWriteModel.estimatedTime) &&
        Objects.equals(this.duration, workPackageWriteModel.duration) &&
        Objects.equals(this.ignoreNonWorkingDays, workPackageWriteModel.ignoreNonWorkingDays) &&
        Objects.equals(this.links, workPackageWriteModel.links) &&
        Objects.equals(this.meta, workPackageWriteModel.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject, description, scheduleManually, startDate, dueDate, estimatedTime, duration, ignoreNonWorkingDays, links, meta);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkPackageWriteModel {\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    scheduleManually: ").append(toIndentedString(scheduleManually)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    estimatedTime: ").append(toIndentedString(estimatedTime)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    ignoreNonWorkingDays: ").append(toIndentedString(ignoreNonWorkingDays)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
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

