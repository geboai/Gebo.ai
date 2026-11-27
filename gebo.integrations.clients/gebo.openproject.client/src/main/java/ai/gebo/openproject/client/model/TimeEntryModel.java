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
import ai.gebo.openproject.client.model.TimeEntryModelAllOfLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TimeEntryModel
 */
@JsonPropertyOrder({
  TimeEntryModel.JSON_PROPERTY_ID,
  TimeEntryModel.JSON_PROPERTY_COMMENT,
  TimeEntryModel.JSON_PROPERTY_SPENT_ON,
  TimeEntryModel.JSON_PROPERTY_HOURS,
  TimeEntryModel.JSON_PROPERTY_ONGOING,
  TimeEntryModel.JSON_PROPERTY_CREATED_AT,
  TimeEntryModel.JSON_PROPERTY_START_TIME,
  TimeEntryModel.JSON_PROPERTY_END_TIME,
  TimeEntryModel.JSON_PROPERTY_UPDATED_AT,
  TimeEntryModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class TimeEntryModel {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nullable
  private Integer id;

  public static final String JSON_PROPERTY_COMMENT = "comment";
  @jakarta.annotation.Nullable
  private Formattable comment;

  public static final String JSON_PROPERTY_SPENT_ON = "spentOn";
  @jakarta.annotation.Nullable
  private LocalDate spentOn;

  public static final String JSON_PROPERTY_HOURS = "hours";
  @jakarta.annotation.Nullable
  private String hours;

  public static final String JSON_PROPERTY_ONGOING = "ongoing";
  @jakarta.annotation.Nullable
  private Boolean ongoing;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_START_TIME = "startTime";
  @jakarta.annotation.Nullable
  private OffsetDateTime startTime;

  public static final String JSON_PROPERTY_END_TIME = "endTime";
  @jakarta.annotation.Nullable
  private OffsetDateTime endTime;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private TimeEntryModelAllOfLinks links;

  public TimeEntryModel() {
  }

  public TimeEntryModel id(@jakarta.annotation.Nullable Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * The id of the time entry
   * minimum: 1
   * @return id
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getId() {
    return id;
  }


  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@jakarta.annotation.Nullable Integer id) {
    this.id = id;
  }

  public TimeEntryModel comment(@jakarta.annotation.Nullable Formattable comment) {
    
    this.comment = comment;
    return this;
  }

  /**
   * A comment to the time entry
   * @return comment
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_COMMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Formattable getComment() {
    return comment;
  }


  @JsonProperty(value = JSON_PROPERTY_COMMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setComment(@jakarta.annotation.Nullable Formattable comment) {
    this.comment = comment;
  }

  public TimeEntryModel spentOn(@jakarta.annotation.Nullable LocalDate spentOn) {
    
    this.spentOn = spentOn;
    return this;
  }

  /**
   * The date the expenditure is booked for
   * @return spentOn
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SPENT_ON, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getSpentOn() {
    return spentOn;
  }


  @JsonProperty(value = JSON_PROPERTY_SPENT_ON, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSpentOn(@jakarta.annotation.Nullable LocalDate spentOn) {
    this.spentOn = spentOn;
  }

  public TimeEntryModel hours(@jakarta.annotation.Nullable String hours) {
    
    this.hours = hours;
    return this;
  }

  /**
   * The time quantifying the expenditure
   * @return hours
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HOURS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getHours() {
    return hours;
  }


  @JsonProperty(value = JSON_PROPERTY_HOURS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHours(@jakarta.annotation.Nullable String hours) {
    this.hours = hours;
  }

  public TimeEntryModel ongoing(@jakarta.annotation.Nullable Boolean ongoing) {
    
    this.ongoing = ongoing;
    return this;
  }

  /**
   * Whether the time entry is actively tracking time
   * @return ongoing
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ONGOING, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getOngoing() {
    return ongoing;
  }


  @JsonProperty(value = JSON_PROPERTY_ONGOING, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOngoing(@jakarta.annotation.Nullable Boolean ongoing) {
    this.ongoing = ongoing;
  }

  public TimeEntryModel createdAt(@jakarta.annotation.Nullable OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * The time the time entry was created
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

  public TimeEntryModel startTime(@jakarta.annotation.Nullable OffsetDateTime startTime) {
    
    this.startTime = startTime;
    return this;
  }

  /**
   * The time the time entry was started, or null if the time entry does not have a start time.  The time is returned as UTC time, if presented to the user it should be converted to the user&#39;s timezone.  This field is only available if the global &#x60;allow_tracking_start_and_end_times&#x60; setting is enabled.
   * @return startTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_START_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getStartTime() {
    return startTime;
  }


  @JsonProperty(value = JSON_PROPERTY_START_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStartTime(@jakarta.annotation.Nullable OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public TimeEntryModel endTime(@jakarta.annotation.Nullable OffsetDateTime endTime) {
    
    this.endTime = endTime;
    return this;
  }

  /**
   * The time the time entry was ended, or null if the time entry does not have a start time.  The time is returned as UTC time, if presented to the user it should be converted to the user&#39;s timezone.  This field is only available if the global &#x60;allow_tracking_start_and_end_times&#x60; setting is enabled.
   * @return endTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_END_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getEndTime() {
    return endTime;
  }


  @JsonProperty(value = JSON_PROPERTY_END_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEndTime(@jakarta.annotation.Nullable OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public TimeEntryModel updatedAt(@jakarta.annotation.Nullable OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * The time the time entry was last updated
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

  public TimeEntryModel links(@jakarta.annotation.Nullable TimeEntryModelAllOfLinks links) {
    
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

  public TimeEntryModelAllOfLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable TimeEntryModelAllOfLinks links) {
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
    TimeEntryModel timeEntryModel = (TimeEntryModel) o;
    return Objects.equals(this.id, timeEntryModel.id) &&
        Objects.equals(this.comment, timeEntryModel.comment) &&
        Objects.equals(this.spentOn, timeEntryModel.spentOn) &&
        Objects.equals(this.hours, timeEntryModel.hours) &&
        Objects.equals(this.ongoing, timeEntryModel.ongoing) &&
        Objects.equals(this.createdAt, timeEntryModel.createdAt) &&
        Objects.equals(this.startTime, timeEntryModel.startTime) &&
        Objects.equals(this.endTime, timeEntryModel.endTime) &&
        Objects.equals(this.updatedAt, timeEntryModel.updatedAt) &&
        Objects.equals(this.links, timeEntryModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, comment, spentOn, hours, ongoing, createdAt, startTime, endTime, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimeEntryModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    spentOn: ").append(toIndentedString(spentOn)).append("\n");
    sb.append("    hours: ").append(toIndentedString(hours)).append("\n");
    sb.append("    ongoing: ").append(toIndentedString(ongoing)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
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

