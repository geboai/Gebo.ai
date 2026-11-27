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
import ai.gebo.openproject.client.model.MeetingModelEmbedded;
import ai.gebo.openproject.client.model.MeetingModelLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MeetingModel
 */
@JsonPropertyOrder({
  MeetingModel.JSON_PROPERTY_HAL_TYPE,
  MeetingModel.JSON_PROPERTY_ID,
  MeetingModel.JSON_PROPERTY_TITLE,
  MeetingModel.JSON_PROPERTY_LOCATION,
  MeetingModel.JSON_PROPERTY_LOCK_VERSION,
  MeetingModel.JSON_PROPERTY_START_TIME,
  MeetingModel.JSON_PROPERTY_END_TIME,
  MeetingModel.JSON_PROPERTY_DURATION,
  MeetingModel.JSON_PROPERTY_STATE,
  MeetingModel.JSON_PROPERTY_AGENDA_SORTING_MODE,
  MeetingModel.JSON_PROPERTY_SHARING,
  MeetingModel.JSON_PROPERTY_TEMPLATE,
  MeetingModel.JSON_PROPERTY_NOTIFY,
  MeetingModel.JSON_PROPERTY_CREATED_AT,
  MeetingModel.JSON_PROPERTY_UPDATED_AT,
  MeetingModel.JSON_PROPERTY_EMBEDDED,
  MeetingModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class MeetingModel {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    MEETING(String.valueOf("Meeting"));

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

  public static final String JSON_PROPERTY_TITLE = "title";
  @jakarta.annotation.Nonnull
  private String title;

  public static final String JSON_PROPERTY_LOCATION = "location";
  @jakarta.annotation.Nullable
  private String location;

  public static final String JSON_PROPERTY_LOCK_VERSION = "lockVersion";
  @jakarta.annotation.Nonnull
  private Integer lockVersion;

  public static final String JSON_PROPERTY_START_TIME = "startTime";
  @jakarta.annotation.Nonnull
  private OffsetDateTime startTime;

  public static final String JSON_PROPERTY_END_TIME = "endTime";
  @jakarta.annotation.Nonnull
  private OffsetDateTime endTime;

  public static final String JSON_PROPERTY_DURATION = "duration";
  @jakarta.annotation.Nonnull
  private String duration;

  /**
   * The current state of the meeting. Possible values:  - *open*: the meeting is open - *draft*: the meeting is in draft state - *in_progress*: the meeting is currently in progress - *cancelled*: the meeting has been cancelled - *closed*: the meeting is closed
   */
  public enum StateEnum {
    OPEN(String.valueOf("open")),
    
    DRAFT(String.valueOf("draft")),
    
    IN_PROGRESS(String.valueOf("in_progress")),
    
    CANCELLED(String.valueOf("cancelled")),
    
    CLOSED(String.valueOf("closed"));

    private String value;

    StateEnum(String value) {
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
    public static StateEnum fromValue(String value) {
      for (StateEnum b : StateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_STATE = "state";
  @jakarta.annotation.Nullable
  private StateEnum state;

  /**
   * The agenda sorting mode. Defaults to &#x60;manual&#x60;. In &#x60;vote_based&#x60; mode, items are ordered by their net vote score within each section; sections remain manually sorted.
   */
  public enum AgendaSortingModeEnum {
    MANUAL(String.valueOf("manual")),
    
    VOTE_BASED(String.valueOf("vote_based"));

    private String value;

    AgendaSortingModeEnum(String value) {
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
    public static AgendaSortingModeEnum fromValue(String value) {
      for (AgendaSortingModeEnum b : AgendaSortingModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_AGENDA_SORTING_MODE = "agendaSortingMode";
  @jakarta.annotation.Nonnull
  private AgendaSortingModeEnum agendaSortingMode;

  /**
   * How the meeting template is shared. Only applicable for one-time templates. Possible values:  - *none*: not shared - *descendants*: shared with descendant projects - *system*: shared globally
   */
  public enum SharingEnum {
    NONE(String.valueOf("none")),
    
    DESCENDANTS(String.valueOf("descendants")),
    
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
  @jakarta.annotation.Nullable
  private SharingEnum sharing;

  public static final String JSON_PROPERTY_TEMPLATE = "template";
  @jakarta.annotation.Nullable
  private Boolean template;

  public static final String JSON_PROPERTY_NOTIFY = "notify";
  @jakarta.annotation.Nullable
  private Boolean notify;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_EMBEDDED = "_embedded";
  @jakarta.annotation.Nullable
  private MeetingModelEmbedded embedded;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private MeetingModelLinks links;

  public MeetingModel() {
  }

  public MeetingModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public MeetingModel id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * Identifier of this meeting
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

  public MeetingModel title(@jakarta.annotation.Nonnull String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The meeting&#39;s title
   * @return title
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_TITLE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTitle() {
    return title;
  }


  @JsonProperty(value = JSON_PROPERTY_TITLE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTitle(@jakarta.annotation.Nonnull String title) {
    this.title = title;
  }

  public MeetingModel location(@jakarta.annotation.Nullable String location) {
    
    this.location = location;
    return this;
  }

  /**
   * The meeting&#39;s location
   * @return location
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LOCATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLocation() {
    return location;
  }


  @JsonProperty(value = JSON_PROPERTY_LOCATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocation(@jakarta.annotation.Nullable String location) {
    this.location = location;
  }

  public MeetingModel lockVersion(@jakarta.annotation.Nonnull Integer lockVersion) {
    
    this.lockVersion = lockVersion;
    return this;
  }

  /**
   * The version of the item as used for optimistic locking
   * @return lockVersion
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_LOCK_VERSION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getLockVersion() {
    return lockVersion;
  }


  @JsonProperty(value = JSON_PROPERTY_LOCK_VERSION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLockVersion(@jakarta.annotation.Nonnull Integer lockVersion) {
    this.lockVersion = lockVersion;
  }

  public MeetingModel startTime(@jakarta.annotation.Nonnull OffsetDateTime startTime) {
    
    this.startTime = startTime;
    return this;
  }

  /**
   * The scheduled meeting start time.
   * @return startTime
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_START_TIME, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getStartTime() {
    return startTime;
  }


  @JsonProperty(value = JSON_PROPERTY_START_TIME, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStartTime(@jakarta.annotation.Nonnull OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public MeetingModel endTime(@jakarta.annotation.Nonnull OffsetDateTime endTime) {
    
    this.endTime = endTime;
    return this;
  }

  /**
   * The scheduled meeting end time.
   * @return endTime
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_END_TIME, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getEndTime() {
    return endTime;
  }


  @JsonProperty(value = JSON_PROPERTY_END_TIME, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEndTime(@jakarta.annotation.Nonnull OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public MeetingModel duration(@jakarta.annotation.Nonnull String duration) {
    
    this.duration = duration;
    return this;
  }

  /**
   * The meeting duration in hours.
   * @return duration
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_DURATION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getDuration() {
    return duration;
  }


  @JsonProperty(value = JSON_PROPERTY_DURATION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDuration(@jakarta.annotation.Nonnull String duration) {
    this.duration = duration;
  }

  public MeetingModel state(@jakarta.annotation.Nullable StateEnum state) {
    
    this.state = state;
    return this;
  }

  /**
   * The current state of the meeting. Possible values:  - *open*: the meeting is open - *draft*: the meeting is in draft state - *in_progress*: the meeting is currently in progress - *cancelled*: the meeting has been cancelled - *closed*: the meeting is closed
   * @return state
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public StateEnum getState() {
    return state;
  }


  @JsonProperty(value = JSON_PROPERTY_STATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setState(@jakarta.annotation.Nullable StateEnum state) {
    this.state = state;
  }

  public MeetingModel agendaSortingMode(@jakarta.annotation.Nonnull AgendaSortingModeEnum agendaSortingMode) {
    
    this.agendaSortingMode = agendaSortingMode;
    return this;
  }

  /**
   * The agenda sorting mode. Defaults to &#x60;manual&#x60;. In &#x60;vote_based&#x60; mode, items are ordered by their net vote score within each section; sections remain manually sorted.
   * @return agendaSortingMode
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_AGENDA_SORTING_MODE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public AgendaSortingModeEnum getAgendaSortingMode() {
    return agendaSortingMode;
  }


  @JsonProperty(value = JSON_PROPERTY_AGENDA_SORTING_MODE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAgendaSortingMode(@jakarta.annotation.Nonnull AgendaSortingModeEnum agendaSortingMode) {
    this.agendaSortingMode = agendaSortingMode;
  }

  public MeetingModel sharing(@jakarta.annotation.Nullable SharingEnum sharing) {
    
    this.sharing = sharing;
    return this;
  }

  /**
   * How the meeting template is shared. Only applicable for one-time templates. Possible values:  - *none*: not shared - *descendants*: shared with descendant projects - *system*: shared globally
   * @return sharing
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SHARING, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SharingEnum getSharing() {
    return sharing;
  }


  @JsonProperty(value = JSON_PROPERTY_SHARING, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSharing(@jakarta.annotation.Nullable SharingEnum sharing) {
    this.sharing = sharing;
  }

  public MeetingModel template(@jakarta.annotation.Nullable Boolean template) {
    
    this.template = template;
    return this;
  }

  /**
   * Whether this meeting is a template.
   * @return template
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TEMPLATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getTemplate() {
    return template;
  }


  @JsonProperty(value = JSON_PROPERTY_TEMPLATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTemplate(@jakarta.annotation.Nullable Boolean template) {
    this.template = template;
  }

  public MeetingModel notify(@jakarta.annotation.Nullable Boolean notify) {
    
    this.notify = notify;
    return this;
  }

  /**
   * Whether email notifications are enabled for this meeting. When enabled, changes made through the API (leaving draft, updating the schedule, adding or removing participants, deleting the meeting) notify participants, mirroring the web application.
   * @return notify
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_NOTIFY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getNotify() {
    return notify;
  }


  @JsonProperty(value = JSON_PROPERTY_NOTIFY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNotify(@jakarta.annotation.Nullable Boolean notify) {
    this.notify = notify;
  }

  public MeetingModel createdAt(@jakarta.annotation.Nonnull OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Time of creation. Can be writable by admins with the &#x60;apiv3_write_readonly_attributes&#x60; setting enabled.
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

  public MeetingModel updatedAt(@jakarta.annotation.Nonnull OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change to the meeting.
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

  public MeetingModel embedded(@jakarta.annotation.Nullable MeetingModelEmbedded embedded) {
    
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

  public MeetingModelEmbedded getEmbedded() {
    return embedded;
  }


  @JsonProperty(value = JSON_PROPERTY_EMBEDDED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEmbedded(@jakarta.annotation.Nullable MeetingModelEmbedded embedded) {
    this.embedded = embedded;
  }

  public MeetingModel links(@jakarta.annotation.Nullable MeetingModelLinks links) {
    
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

  public MeetingModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable MeetingModelLinks links) {
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
    MeetingModel meetingModel = (MeetingModel) o;
    return Objects.equals(this.halType, meetingModel.halType) &&
        Objects.equals(this.id, meetingModel.id) &&
        Objects.equals(this.title, meetingModel.title) &&
        Objects.equals(this.location, meetingModel.location) &&
        Objects.equals(this.lockVersion, meetingModel.lockVersion) &&
        Objects.equals(this.startTime, meetingModel.startTime) &&
        Objects.equals(this.endTime, meetingModel.endTime) &&
        Objects.equals(this.duration, meetingModel.duration) &&
        Objects.equals(this.state, meetingModel.state) &&
        Objects.equals(this.agendaSortingMode, meetingModel.agendaSortingMode) &&
        Objects.equals(this.sharing, meetingModel.sharing) &&
        Objects.equals(this.template, meetingModel.template) &&
        Objects.equals(this.notify, meetingModel.notify) &&
        Objects.equals(this.createdAt, meetingModel.createdAt) &&
        Objects.equals(this.updatedAt, meetingModel.updatedAt) &&
        Objects.equals(this.embedded, meetingModel.embedded) &&
        Objects.equals(this.links, meetingModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, id, title, location, lockVersion, startTime, endTime, duration, state, agendaSortingMode, sharing, template, notify, createdAt, updatedAt, embedded, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingModel {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    lockVersion: ").append(toIndentedString(lockVersion)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    agendaSortingMode: ").append(toIndentedString(agendaSortingMode)).append("\n");
    sb.append("    sharing: ").append(toIndentedString(sharing)).append("\n");
    sb.append("    template: ").append(toIndentedString(template)).append("\n");
    sb.append("    notify: ").append(toIndentedString(notify)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

