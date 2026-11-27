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
import ai.gebo.openproject.client.model.WorkPackageModelAllOfLinks;
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
 * WorkPackageModel
 */
@JsonPropertyOrder({
  WorkPackageModel.JSON_PROPERTY_ID,
  WorkPackageModel.JSON_PROPERTY_HAL_TYPE,
  WorkPackageModel.JSON_PROPERTY_DISPLAY_ID,
  WorkPackageModel.JSON_PROPERTY_LOCK_VERSION,
  WorkPackageModel.JSON_PROPERTY_SUBJECT,
  WorkPackageModel.JSON_PROPERTY_DESCRIPTION,
  WorkPackageModel.JSON_PROPERTY_SCHEDULE_MANUALLY,
  WorkPackageModel.JSON_PROPERTY_READONLY,
  WorkPackageModel.JSON_PROPERTY_HAS_PROJECT_ATTRIBUTES,
  WorkPackageModel.JSON_PROPERTY_START_DATE,
  WorkPackageModel.JSON_PROPERTY_DUE_DATE,
  WorkPackageModel.JSON_PROPERTY_DATE,
  WorkPackageModel.JSON_PROPERTY_DERIVED_START_DATE,
  WorkPackageModel.JSON_PROPERTY_DERIVED_DUE_DATE,
  WorkPackageModel.JSON_PROPERTY_DURATION,
  WorkPackageModel.JSON_PROPERTY_ESTIMATED_TIME,
  WorkPackageModel.JSON_PROPERTY_DERIVED_ESTIMATED_TIME,
  WorkPackageModel.JSON_PROPERTY_IGNORE_NON_WORKING_DAYS,
  WorkPackageModel.JSON_PROPERTY_POSITION,
  WorkPackageModel.JSON_PROPERTY_SPENT_TIME,
  WorkPackageModel.JSON_PROPERTY_ALLOCATED_TIME,
  WorkPackageModel.JSON_PROPERTY_STORY_POINTS,
  WorkPackageModel.JSON_PROPERTY_PERCENTAGE_DONE,
  WorkPackageModel.JSON_PROPERTY_DERIVED_PERCENTAGE_DONE,
  WorkPackageModel.JSON_PROPERTY_CREATED_AT,
  WorkPackageModel.JSON_PROPERTY_UPDATED_AT,
  WorkPackageModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class WorkPackageModel {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nonnull
  private Integer id;

  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    WORK_PACKAGE(String.valueOf("WorkPackage"));

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

  public static final String JSON_PROPERTY_DISPLAY_ID = "displayId";
  @jakarta.annotation.Nullable
  private String displayId;

  public static final String JSON_PROPERTY_LOCK_VERSION = "lockVersion";
  @jakarta.annotation.Nullable
  private Integer lockVersion;

  public static final String JSON_PROPERTY_SUBJECT = "subject";
  @jakarta.annotation.Nonnull
  private String subject;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  @jakarta.annotation.Nullable
  private Formattable description;

  public static final String JSON_PROPERTY_SCHEDULE_MANUALLY = "scheduleManually";
  @jakarta.annotation.Nullable
  private Boolean scheduleManually;

  public static final String JSON_PROPERTY_READONLY = "readonly";
  @jakarta.annotation.Nullable
  private Boolean readonly;

  public static final String JSON_PROPERTY_HAS_PROJECT_ATTRIBUTES = "hasProjectAttributes";
  @jakarta.annotation.Nullable
  private Boolean hasProjectAttributes;

  public static final String JSON_PROPERTY_START_DATE = "startDate";
  @jakarta.annotation.Nullable
  private LocalDate startDate;

  public static final String JSON_PROPERTY_DUE_DATE = "dueDate";
  @jakarta.annotation.Nullable
  private LocalDate dueDate;

  public static final String JSON_PROPERTY_DATE = "date";
  @jakarta.annotation.Nullable
  private LocalDate date;

  public static final String JSON_PROPERTY_DERIVED_START_DATE = "derivedStartDate";
  @jakarta.annotation.Nullable
  private LocalDate derivedStartDate;

  public static final String JSON_PROPERTY_DERIVED_DUE_DATE = "derivedDueDate";
  @jakarta.annotation.Nullable
  private LocalDate derivedDueDate;

  public static final String JSON_PROPERTY_DURATION = "duration";
  @jakarta.annotation.Nullable
  private String duration;

  public static final String JSON_PROPERTY_ESTIMATED_TIME = "estimatedTime";
  @jakarta.annotation.Nullable
  private String estimatedTime;

  public static final String JSON_PROPERTY_DERIVED_ESTIMATED_TIME = "derivedEstimatedTime";
  @jakarta.annotation.Nullable
  private String derivedEstimatedTime;

  public static final String JSON_PROPERTY_IGNORE_NON_WORKING_DAYS = "ignoreNonWorkingDays";
  @jakarta.annotation.Nullable
  private Boolean ignoreNonWorkingDays;

  public static final String JSON_PROPERTY_POSITION = "position";
  @jakarta.annotation.Nullable
  private Integer position;

  public static final String JSON_PROPERTY_SPENT_TIME = "spentTime";
  @jakarta.annotation.Nullable
  private String spentTime;

  public static final String JSON_PROPERTY_ALLOCATED_TIME = "allocatedTime";
  @jakarta.annotation.Nullable
  private String allocatedTime;

  public static final String JSON_PROPERTY_STORY_POINTS = "storyPoints";
  @jakarta.annotation.Nullable
  private Integer storyPoints;

  public static final String JSON_PROPERTY_PERCENTAGE_DONE = "percentageDone";
  @jakarta.annotation.Nullable
  private Integer percentageDone;

  public static final String JSON_PROPERTY_DERIVED_PERCENTAGE_DONE = "derivedPercentageDone";
  @jakarta.annotation.Nullable
  private Integer derivedPercentageDone;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nonnull
  private WorkPackageModelAllOfLinks links;

  public WorkPackageModel() {
  }

  public WorkPackageModel id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * Work package id
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

  public WorkPackageModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public WorkPackageModel displayId(@jakarta.annotation.Nullable String displayId) {
    
    this.displayId = displayId;
    return this;
  }

  /**
   * The user-facing identifier for the work package. Its format depends on the &#x60;work_packages_identifier&#x60; setting. When set to &#x60;semantic&#x60;: the project-based identifier (e.g. \&quot;PROJ-42\&quot;). When set to &#x60;classic&#x60;: the numeric ID as a string (e.g. \&quot;123\&quot;).
   * @return displayId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DISPLAY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDisplayId() {
    return displayId;
  }


  @JsonProperty(value = JSON_PROPERTY_DISPLAY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDisplayId(@jakarta.annotation.Nullable String displayId) {
    this.displayId = displayId;
  }

  public WorkPackageModel lockVersion(@jakarta.annotation.Nullable Integer lockVersion) {
    
    this.lockVersion = lockVersion;
    return this;
  }

  /**
   * The version of the item as used for optimistic locking
   * @return lockVersion
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LOCK_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getLockVersion() {
    return lockVersion;
  }


  @JsonProperty(value = JSON_PROPERTY_LOCK_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLockVersion(@jakarta.annotation.Nullable Integer lockVersion) {
    this.lockVersion = lockVersion;
  }

  public WorkPackageModel subject(@jakarta.annotation.Nonnull String subject) {
    
    this.subject = subject;
    return this;
  }

  /**
   * Work package subject
   * @return subject
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_SUBJECT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSubject() {
    return subject;
  }


  @JsonProperty(value = JSON_PROPERTY_SUBJECT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSubject(@jakarta.annotation.Nonnull String subject) {
    this.subject = subject;
  }

  public WorkPackageModel description(@jakarta.annotation.Nullable Formattable description) {
    
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

  public WorkPackageModel scheduleManually(@jakarta.annotation.Nullable Boolean scheduleManually) {
    
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

  public WorkPackageModel readonly(@jakarta.annotation.Nullable Boolean readonly) {
    
    this.readonly = readonly;
    return this;
  }

  /**
   * If true, the work package is in a readonly status so with the exception of the status, no other property can be altered.
   * @return readonly
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_READONLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getReadonly() {
    return readonly;
  }


  @JsonProperty(value = JSON_PROPERTY_READONLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setReadonly(@jakarta.annotation.Nullable Boolean readonly) {
    this.readonly = readonly;
  }

  public WorkPackageModel hasProjectAttributes(@jakarta.annotation.Nullable Boolean hasProjectAttributes) {
    
    this.hasProjectAttributes = hasProjectAttributes;
    return this;
  }

  /**
   * If true, the work package&#39;s type has at least one project custom field activated and visible to the current user.
   * @return hasProjectAttributes
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HAS_PROJECT_ATTRIBUTES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getHasProjectAttributes() {
    return hasProjectAttributes;
  }


  @JsonProperty(value = JSON_PROPERTY_HAS_PROJECT_ATTRIBUTES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHasProjectAttributes(@jakarta.annotation.Nullable Boolean hasProjectAttributes) {
    this.hasProjectAttributes = hasProjectAttributes;
  }

  public WorkPackageModel startDate(@jakarta.annotation.Nullable LocalDate startDate) {
    
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

  public WorkPackageModel dueDate(@jakarta.annotation.Nullable LocalDate dueDate) {
    
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

  public WorkPackageModel date(@jakarta.annotation.Nullable LocalDate date) {
    
    this.date = date;
    return this;
  }

  /**
   * Date on which a milestone is achieved
   * @return date
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getDate() {
    return date;
  }


  @JsonProperty(value = JSON_PROPERTY_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDate(@jakarta.annotation.Nullable LocalDate date) {
    this.date = date;
  }

  public WorkPackageModel derivedStartDate(@jakarta.annotation.Nullable LocalDate derivedStartDate) {
    
    this.derivedStartDate = derivedStartDate;
    return this;
  }

  /**
   * Similar to start date but is not set by a client but rather deduced by the work packages&#39; descendants. If manual scheduleManually is active, the two dates can deviate.
   * @return derivedStartDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getDerivedStartDate() {
    return derivedStartDate;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedStartDate(@jakarta.annotation.Nullable LocalDate derivedStartDate) {
    this.derivedStartDate = derivedStartDate;
  }

  public WorkPackageModel derivedDueDate(@jakarta.annotation.Nullable LocalDate derivedDueDate) {
    
    this.derivedDueDate = derivedDueDate;
    return this;
  }

  /**
   * Similar to due date but is not set by a client but rather deduced by the work packages&#39; descendants. If manual scheduleManually is active, the two dates can deviate.
   * @return derivedDueDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getDerivedDueDate() {
    return derivedDueDate;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedDueDate(@jakarta.annotation.Nullable LocalDate derivedDueDate) {
    this.derivedDueDate = derivedDueDate;
  }

  public WorkPackageModel duration(@jakarta.annotation.Nullable String duration) {
    
    this.duration = duration;
    return this;
  }

  /**
   * **(NOT IMPLEMENTED)** The amount of time in hours the work package needs to be completed. Not available for milestone type of work packages.
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

  public WorkPackageModel estimatedTime(@jakarta.annotation.Nullable String estimatedTime) {
    
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

  public WorkPackageModel derivedEstimatedTime(@jakarta.annotation.Nullable String derivedEstimatedTime) {
    
    this.derivedEstimatedTime = derivedEstimatedTime;
    return this;
  }

  /**
   * Time a work package likely needs to be completed including its descendants
   * @return derivedEstimatedTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDerivedEstimatedTime() {
    return derivedEstimatedTime;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedEstimatedTime(@jakarta.annotation.Nullable String derivedEstimatedTime) {
    this.derivedEstimatedTime = derivedEstimatedTime;
  }

  public WorkPackageModel ignoreNonWorkingDays(@jakarta.annotation.Nullable Boolean ignoreNonWorkingDays) {
    
    this.ignoreNonWorkingDays = ignoreNonWorkingDays;
    return this;
  }

  /**
   * **(NOT IMPLEMENTED)** When scheduling, whether or not to ignore the non working days being defined. A work package with the flag set to true will be allowed to be scheduled to a non working day.
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

  public WorkPackageModel position(@jakarta.annotation.Nullable Integer position) {
    
    this.position = position;
    return this;
  }

  /**
   * The position this work package has in a version configured to be backlog (sprint or product).  # Conditions  **Permission** Backlogs needs to be enabled in the work package&#39;s project and the work package&#39;s type is configured to be a backlog type.
   * @return position
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_POSITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPosition() {
    return position;
  }


  @JsonProperty(value = JSON_PROPERTY_POSITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPosition(@jakarta.annotation.Nullable Integer position) {
    this.position = position;
  }

  public WorkPackageModel spentTime(@jakarta.annotation.Nullable String spentTime) {
    
    this.spentTime = spentTime;
    return this;
  }

  /**
   * The time booked for this work package by users working on it  # Conditions  **Permission** view time entries
   * @return spentTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SPENT_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSpentTime() {
    return spentTime;
  }


  @JsonProperty(value = JSON_PROPERTY_SPENT_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSpentTime(@jakarta.annotation.Nullable String spentTime) {
    this.spentTime = spentTime;
  }

  public WorkPackageModel allocatedTime(@jakarta.annotation.Nullable String allocatedTime) {
    
    this.allocatedTime = allocatedTime;
    return this;
  }

  /**
   * The sum of the confirmed resource allocations of this work package  # Conditions  **Permission** view resource planners, requires the resource management module and an enterprise token
   * @return allocatedTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getAllocatedTime() {
    return allocatedTime;
  }


  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAllocatedTime(@jakarta.annotation.Nullable String allocatedTime) {
    this.allocatedTime = allocatedTime;
  }

  public WorkPackageModel storyPoints(@jakarta.annotation.Nullable Integer storyPoints) {
    
    this.storyPoints = storyPoints;
    return this;
  }

  /**
   * The estimation in story points on how long this work package will take to complete  # Conditions  **Permission** Backlogs needs to be enabled in the work package&#39;s project and the work package&#39;s type is configured to be a backlog type.
   * @return storyPoints
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STORY_POINTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getStoryPoints() {
    return storyPoints;
  }


  @JsonProperty(value = JSON_PROPERTY_STORY_POINTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStoryPoints(@jakarta.annotation.Nullable Integer storyPoints) {
    this.storyPoints = storyPoints;
  }

  public WorkPackageModel percentageDone(@jakarta.annotation.Nullable Integer percentageDone) {
    
    this.percentageDone = percentageDone;
    return this;
  }

  /**
   * Amount of total completion for a work package
   * minimum: 0
   * maximum: 100
   * @return percentageDone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPercentageDone() {
    return percentageDone;
  }


  @JsonProperty(value = JSON_PROPERTY_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPercentageDone(@jakarta.annotation.Nullable Integer percentageDone) {
    this.percentageDone = percentageDone;
  }

  public WorkPackageModel derivedPercentageDone(@jakarta.annotation.Nullable Integer derivedPercentageDone) {
    
    this.derivedPercentageDone = derivedPercentageDone;
    return this;
  }

  /**
   * Amount of total completion for a work package derived from itself and its descendant work packages
   * minimum: 0
   * maximum: 100
   * @return derivedPercentageDone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getDerivedPercentageDone() {
    return derivedPercentageDone;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedPercentageDone(@jakarta.annotation.Nullable Integer derivedPercentageDone) {
    this.derivedPercentageDone = derivedPercentageDone;
  }

  public WorkPackageModel createdAt(@jakarta.annotation.Nullable OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Time of creation. Can be writable by admins with the &#x60;apiv3_write_readonly_attributes&#x60; setting enabled.
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

  public WorkPackageModel updatedAt(@jakarta.annotation.Nullable OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change to the work package.
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

  public WorkPackageModel links(@jakarta.annotation.Nonnull WorkPackageModelAllOfLinks links) {
    
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

  public WorkPackageModelAllOfLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLinks(@jakarta.annotation.Nonnull WorkPackageModelAllOfLinks links) {
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
    WorkPackageModel workPackageModel = (WorkPackageModel) o;
    return Objects.equals(this.id, workPackageModel.id) &&
        Objects.equals(this.halType, workPackageModel.halType) &&
        Objects.equals(this.displayId, workPackageModel.displayId) &&
        Objects.equals(this.lockVersion, workPackageModel.lockVersion) &&
        Objects.equals(this.subject, workPackageModel.subject) &&
        Objects.equals(this.description, workPackageModel.description) &&
        Objects.equals(this.scheduleManually, workPackageModel.scheduleManually) &&
        Objects.equals(this.readonly, workPackageModel.readonly) &&
        Objects.equals(this.hasProjectAttributes, workPackageModel.hasProjectAttributes) &&
        Objects.equals(this.startDate, workPackageModel.startDate) &&
        Objects.equals(this.dueDate, workPackageModel.dueDate) &&
        Objects.equals(this.date, workPackageModel.date) &&
        Objects.equals(this.derivedStartDate, workPackageModel.derivedStartDate) &&
        Objects.equals(this.derivedDueDate, workPackageModel.derivedDueDate) &&
        Objects.equals(this.duration, workPackageModel.duration) &&
        Objects.equals(this.estimatedTime, workPackageModel.estimatedTime) &&
        Objects.equals(this.derivedEstimatedTime, workPackageModel.derivedEstimatedTime) &&
        Objects.equals(this.ignoreNonWorkingDays, workPackageModel.ignoreNonWorkingDays) &&
        Objects.equals(this.position, workPackageModel.position) &&
        Objects.equals(this.spentTime, workPackageModel.spentTime) &&
        Objects.equals(this.allocatedTime, workPackageModel.allocatedTime) &&
        Objects.equals(this.storyPoints, workPackageModel.storyPoints) &&
        Objects.equals(this.percentageDone, workPackageModel.percentageDone) &&
        Objects.equals(this.derivedPercentageDone, workPackageModel.derivedPercentageDone) &&
        Objects.equals(this.createdAt, workPackageModel.createdAt) &&
        Objects.equals(this.updatedAt, workPackageModel.updatedAt) &&
        Objects.equals(this.links, workPackageModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, halType, displayId, lockVersion, subject, description, scheduleManually, readonly, hasProjectAttributes, startDate, dueDate, date, derivedStartDate, derivedDueDate, duration, estimatedTime, derivedEstimatedTime, ignoreNonWorkingDays, position, spentTime, allocatedTime, storyPoints, percentageDone, derivedPercentageDone, createdAt, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkPackageModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    displayId: ").append(toIndentedString(displayId)).append("\n");
    sb.append("    lockVersion: ").append(toIndentedString(lockVersion)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    scheduleManually: ").append(toIndentedString(scheduleManually)).append("\n");
    sb.append("    readonly: ").append(toIndentedString(readonly)).append("\n");
    sb.append("    hasProjectAttributes: ").append(toIndentedString(hasProjectAttributes)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    derivedStartDate: ").append(toIndentedString(derivedStartDate)).append("\n");
    sb.append("    derivedDueDate: ").append(toIndentedString(derivedDueDate)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    estimatedTime: ").append(toIndentedString(estimatedTime)).append("\n");
    sb.append("    derivedEstimatedTime: ").append(toIndentedString(derivedEstimatedTime)).append("\n");
    sb.append("    ignoreNonWorkingDays: ").append(toIndentedString(ignoreNonWorkingDays)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    spentTime: ").append(toIndentedString(spentTime)).append("\n");
    sb.append("    allocatedTime: ").append(toIndentedString(allocatedTime)).append("\n");
    sb.append("    storyPoints: ").append(toIndentedString(storyPoints)).append("\n");
    sb.append("    percentageDone: ").append(toIndentedString(percentageDone)).append("\n");
    sb.append("    derivedPercentageDone: ").append(toIndentedString(derivedPercentageDone)).append("\n");
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

