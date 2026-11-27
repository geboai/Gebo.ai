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
import ai.gebo.openproject.client.model.ConfigurationModelEmbedded;
import ai.gebo.openproject.client.model.ConfigurationModelLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ProjectConfigurationModel
 */
@JsonPropertyOrder({
  ProjectConfigurationModel.JSON_PROPERTY_HAL_TYPE,
  ProjectConfigurationModel.JSON_PROPERTY_MAXIMUM_ATTACHMENT_FILE_SIZE,
  ProjectConfigurationModel.JSON_PROPERTY_MAXIMUM_A_P_I_V3_PAGE_SIZE,
  ProjectConfigurationModel.JSON_PROPERTY_PER_PAGE_OPTIONS,
  ProjectConfigurationModel.JSON_PROPERTY_HOST_NAME,
  ProjectConfigurationModel.JSON_PROPERTY_DURATION_FORMAT,
  ProjectConfigurationModel.JSON_PROPERTY_DATE_FORMAT,
  ProjectConfigurationModel.JSON_PROPERTY_TIME_FORMAT,
  ProjectConfigurationModel.JSON_PROPERTY_USER_DEFAULT_TIMEZONE,
  ProjectConfigurationModel.JSON_PROPERTY_START_OF_WEEK,
  ProjectConfigurationModel.JSON_PROPERTY_HOURS_PER_DAY,
  ProjectConfigurationModel.JSON_PROPERTY_DAYS_PER_MONTH,
  ProjectConfigurationModel.JSON_PROPERTY_ACTIVE_FEATURE_FLAGS,
  ProjectConfigurationModel.JSON_PROPERTY_AVAILABLE_FEATURES,
  ProjectConfigurationModel.JSON_PROPERTY_TRIALLING_FEATURES,
  ProjectConfigurationModel.JSON_PROPERTY_ALLOWED_LINK_PROTOCOLS,
  ProjectConfigurationModel.JSON_PROPERTY_WIKIS_AVAILABLE,
  ProjectConfigurationModel.JSON_PROPERTY_EMBEDDED,
  ProjectConfigurationModel.JSON_PROPERTY_LINKS,
  ProjectConfigurationModel.JSON_PROPERTY_ENABLED_INTERNAL_COMMENTS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class ProjectConfigurationModel {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    CONFIGURATION(String.valueOf("Configuration"));

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
  @jakarta.annotation.Nullable
  private HalTypeEnum halType;

  public static final String JSON_PROPERTY_MAXIMUM_ATTACHMENT_FILE_SIZE = "maximumAttachmentFileSize";
  @jakarta.annotation.Nullable
  private Integer maximumAttachmentFileSize;

  public static final String JSON_PROPERTY_MAXIMUM_A_P_I_V3_PAGE_SIZE = "maximumAPIV3PageSize";
  @jakarta.annotation.Nullable
  private Integer maximumAPIV3PageSize;

  public static final String JSON_PROPERTY_PER_PAGE_OPTIONS = "perPageOptions";
  @jakarta.annotation.Nullable
  private List<Integer> perPageOptions = new ArrayList<>();

  public static final String JSON_PROPERTY_HOST_NAME = "hostName";
  @jakarta.annotation.Nullable
  private String hostName;

  /**
   * The format used to display Work, Remaining Work, and Spent time durations
   */
  public enum DurationFormatEnum {
    DAYS_AND_HOURS(String.valueOf("days_and_hours")),
    
    HOURS_ONLY(String.valueOf("hours_only"));

    private String value;

    DurationFormatEnum(String value) {
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
    public static DurationFormatEnum fromValue(String value) {
      for (DurationFormatEnum b : DurationFormatEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  public static final String JSON_PROPERTY_DURATION_FORMAT = "durationFormat";
  @jakarta.annotation.Nullable
  private DurationFormatEnum durationFormat;

  /**
   * The format used to display dates in UI
   */
  public enum DateFormatEnum {
    _Y_M_D(String.valueOf("%Y-%m-%d")),
    
    _D_M_Y(String.valueOf("%d/%m/%Y")),
    
    _D_M_Y2(String.valueOf("%d.%m.%Y")),
    
    _D_M_Y3(String.valueOf("%d-%m-%Y")),
    
    _M_D_Y(String.valueOf("%m/%d/%Y")),
    
    _D_B_Y(String.valueOf("%d %b %Y")),
    
    _D_B_Y2(String.valueOf("%d %B %Y")),
    
    _B_D_Y(String.valueOf("%b %d, %Y")),
    
    _B_D_Y2(String.valueOf("%B %d, %Y"));

    private String value;

    DateFormatEnum(String value) {
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
    public static DateFormatEnum fromValue(String value) {
      for (DateFormatEnum b : DateFormatEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  public static final String JSON_PROPERTY_DATE_FORMAT = "dateFormat";
  @jakarta.annotation.Nullable
  private DateFormatEnum dateFormat;

  /**
   * The format used to display time values
   */
  public enum TimeFormatEnum {
    _H_M(String.valueOf("%H:%M")),
    
    _I_M_P(String.valueOf("%I:%M %p"));

    private String value;

    TimeFormatEnum(String value) {
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
    public static TimeFormatEnum fromValue(String value) {
      for (TimeFormatEnum b : TimeFormatEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  public static final String JSON_PROPERTY_TIME_FORMAT = "timeFormat";
  @jakarta.annotation.Nullable
  private TimeFormatEnum timeFormat;

  public static final String JSON_PROPERTY_USER_DEFAULT_TIMEZONE = "userDefaultTimezone";
  @jakarta.annotation.Nullable
  private String userDefaultTimezone;

  /**
   * The first day of the week. Can be Monday (1), Saturday (6), or Sunday (7)
   */
  public enum StartOfWeekEnum {
    NUMBER_1(1),
    
    NUMBER_6(6),
    
    NUMBER_7(7);

    private Integer value;

    StartOfWeekEnum(Integer value) {
      this.value = value;
    }

    @JsonValue
    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StartOfWeekEnum fromValue(Integer value) {
      for (StartOfWeekEnum b : StartOfWeekEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  public static final String JSON_PROPERTY_START_OF_WEEK = "startOfWeek";
  @jakarta.annotation.Nullable
  private StartOfWeekEnum startOfWeek;

  public static final String JSON_PROPERTY_HOURS_PER_DAY = "hoursPerDay";
  @jakarta.annotation.Nullable
  private Integer hoursPerDay;

  public static final String JSON_PROPERTY_DAYS_PER_MONTH = "daysPerMonth";
  @jakarta.annotation.Nullable
  private Integer daysPerMonth;

  public static final String JSON_PROPERTY_ACTIVE_FEATURE_FLAGS = "activeFeatureFlags";
  @jakarta.annotation.Nullable
  private List<String> activeFeatureFlags = new ArrayList<>();

  public static final String JSON_PROPERTY_AVAILABLE_FEATURES = "availableFeatures";
  @jakarta.annotation.Nullable
  private List<String> availableFeatures = new ArrayList<>();

  public static final String JSON_PROPERTY_TRIALLING_FEATURES = "triallingFeatures";
  @jakarta.annotation.Nullable
  private List<String> triallingFeatures = new ArrayList<>();

  public static final String JSON_PROPERTY_ALLOWED_LINK_PROTOCOLS = "allowedLinkProtocols";
  @jakarta.annotation.Nullable
  private List<String> allowedLinkProtocols = new ArrayList<>();

  public static final String JSON_PROPERTY_WIKIS_AVAILABLE = "wikisAvailable";
  @jakarta.annotation.Nullable
  private Boolean wikisAvailable;

  public static final String JSON_PROPERTY_EMBEDDED = "_embedded";
  @jakarta.annotation.Nullable
  private ConfigurationModelEmbedded embedded;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private ConfigurationModelLinks links;

  public static final String JSON_PROPERTY_ENABLED_INTERNAL_COMMENTS = "enabledInternalComments";
  @jakarta.annotation.Nullable
  private Boolean enabledInternalComments;

  public ProjectConfigurationModel() {
  }
  /**
   * Constructor with only readonly parameters
   */
  @JsonCreator
  public ProjectConfigurationModel(
    @JsonProperty(JSON_PROPERTY_ENABLED_INTERNAL_COMMENTS) Boolean enabledInternalComments
  ) {
    this();
    this.enabledInternalComments = enabledInternalComments;
  }

  public ProjectConfigurationModel halType(@jakarta.annotation.Nullable HalTypeEnum halType) {
    
    this.halType = halType;
    return this;
  }

  /**
   * Get halType
   * @return halType
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HAL_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public HalTypeEnum getHalType() {
    return halType;
  }


  @JsonProperty(value = JSON_PROPERTY_HAL_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHalType(@jakarta.annotation.Nullable HalTypeEnum halType) {
    this.halType = halType;
  }

  public ProjectConfigurationModel maximumAttachmentFileSize(@jakarta.annotation.Nullable Integer maximumAttachmentFileSize) {
    
    this.maximumAttachmentFileSize = maximumAttachmentFileSize;
    return this;
  }

  /**
   * The maximum allowed size of an attachment in Bytes
   * @return maximumAttachmentFileSize
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MAXIMUM_ATTACHMENT_FILE_SIZE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getMaximumAttachmentFileSize() {
    return maximumAttachmentFileSize;
  }


  @JsonProperty(value = JSON_PROPERTY_MAXIMUM_ATTACHMENT_FILE_SIZE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMaximumAttachmentFileSize(@jakarta.annotation.Nullable Integer maximumAttachmentFileSize) {
    this.maximumAttachmentFileSize = maximumAttachmentFileSize;
  }

  public ProjectConfigurationModel maximumAPIV3PageSize(@jakarta.annotation.Nullable Integer maximumAPIV3PageSize) {
    
    this.maximumAPIV3PageSize = maximumAPIV3PageSize;
    return this;
  }

  /**
   * The maximum allowed page size for API v3 collections
   * @return maximumAPIV3PageSize
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MAXIMUM_A_P_I_V3_PAGE_SIZE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getMaximumAPIV3PageSize() {
    return maximumAPIV3PageSize;
  }


  @JsonProperty(value = JSON_PROPERTY_MAXIMUM_A_P_I_V3_PAGE_SIZE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMaximumAPIV3PageSize(@jakarta.annotation.Nullable Integer maximumAPIV3PageSize) {
    this.maximumAPIV3PageSize = maximumAPIV3PageSize;
  }

  public ProjectConfigurationModel perPageOptions(@jakarta.annotation.Nullable List<Integer> perPageOptions) {
    
    this.perPageOptions = perPageOptions;
    return this;
  }

  public ProjectConfigurationModel addPerPageOptionsItem(Integer perPageOptionsItem) {
    if (this.perPageOptions == null) {
      this.perPageOptions = new ArrayList<>();
    }
    this.perPageOptions.add(perPageOptionsItem);
    return this;
  }

  /**
   * Page size steps to be offered in UI for paginated lists
   * @return perPageOptions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PER_PAGE_OPTIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Integer> getPerPageOptions() {
    return perPageOptions;
  }


  @JsonProperty(value = JSON_PROPERTY_PER_PAGE_OPTIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPerPageOptions(@jakarta.annotation.Nullable List<Integer> perPageOptions) {
    this.perPageOptions = perPageOptions;
  }

  public ProjectConfigurationModel hostName(@jakarta.annotation.Nullable String hostName) {
    
    this.hostName = hostName;
    return this;
  }

  /**
   * The host name configured for the system
   * @return hostName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HOST_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getHostName() {
    return hostName;
  }


  @JsonProperty(value = JSON_PROPERTY_HOST_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHostName(@jakarta.annotation.Nullable String hostName) {
    this.hostName = hostName;
  }

  public ProjectConfigurationModel durationFormat(@jakarta.annotation.Nullable DurationFormatEnum durationFormat) {
    
    this.durationFormat = durationFormat;
    return this;
  }

  /**
   * The format used to display Work, Remaining Work, and Spent time durations
   * @return durationFormat
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DURATION_FORMAT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DurationFormatEnum getDurationFormat() {
    return durationFormat;
  }


  @JsonProperty(value = JSON_PROPERTY_DURATION_FORMAT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDurationFormat(@jakarta.annotation.Nullable DurationFormatEnum durationFormat) {
    this.durationFormat = durationFormat;
  }

  public ProjectConfigurationModel dateFormat(@jakarta.annotation.Nullable DateFormatEnum dateFormat) {
    
    this.dateFormat = dateFormat;
    return this;
  }

  /**
   * The format used to display dates in UI
   * @return dateFormat
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DATE_FORMAT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DateFormatEnum getDateFormat() {
    return dateFormat;
  }


  @JsonProperty(value = JSON_PROPERTY_DATE_FORMAT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDateFormat(@jakarta.annotation.Nullable DateFormatEnum dateFormat) {
    this.dateFormat = dateFormat;
  }

  public ProjectConfigurationModel timeFormat(@jakarta.annotation.Nullable TimeFormatEnum timeFormat) {
    
    this.timeFormat = timeFormat;
    return this;
  }

  /**
   * The format used to display time values
   * @return timeFormat
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIME_FORMAT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TimeFormatEnum getTimeFormat() {
    return timeFormat;
  }


  @JsonProperty(value = JSON_PROPERTY_TIME_FORMAT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTimeFormat(@jakarta.annotation.Nullable TimeFormatEnum timeFormat) {
    this.timeFormat = timeFormat;
  }

  public ProjectConfigurationModel userDefaultTimezone(@jakarta.annotation.Nullable String userDefaultTimezone) {
    
    this.userDefaultTimezone = userDefaultTimezone;
    return this;
  }

  /**
   * The default timezone for users, who does not have a timezone set for themselves
   * @return userDefaultTimezone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_USER_DEFAULT_TIMEZONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserDefaultTimezone() {
    return userDefaultTimezone;
  }


  @JsonProperty(value = JSON_PROPERTY_USER_DEFAULT_TIMEZONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserDefaultTimezone(@jakarta.annotation.Nullable String userDefaultTimezone) {
    this.userDefaultTimezone = userDefaultTimezone;
  }

  public ProjectConfigurationModel startOfWeek(@jakarta.annotation.Nullable StartOfWeekEnum startOfWeek) {
    
    this.startOfWeek = startOfWeek;
    return this;
  }

  /**
   * The first day of the week. Can be Monday (1), Saturday (6), or Sunday (7)
   * @return startOfWeek
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_START_OF_WEEK, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public StartOfWeekEnum getStartOfWeek() {
    return startOfWeek;
  }


  @JsonProperty(value = JSON_PROPERTY_START_OF_WEEK, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStartOfWeek(@jakarta.annotation.Nullable StartOfWeekEnum startOfWeek) {
    this.startOfWeek = startOfWeek;
  }

  public ProjectConfigurationModel hoursPerDay(@jakarta.annotation.Nullable Integer hoursPerDay) {
    
    this.hoursPerDay = hoursPerDay;
    return this;
  }

  /**
   * The number of hours each working day
   * @return hoursPerDay
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HOURS_PER_DAY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getHoursPerDay() {
    return hoursPerDay;
  }


  @JsonProperty(value = JSON_PROPERTY_HOURS_PER_DAY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHoursPerDay(@jakarta.annotation.Nullable Integer hoursPerDay) {
    this.hoursPerDay = hoursPerDay;
  }

  public ProjectConfigurationModel daysPerMonth(@jakarta.annotation.Nullable Integer daysPerMonth) {
    
    this.daysPerMonth = daysPerMonth;
    return this;
  }

  /**
   * The number of days that are considered a month for a more natural way of displaying durations
   * @return daysPerMonth
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DAYS_PER_MONTH, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getDaysPerMonth() {
    return daysPerMonth;
  }


  @JsonProperty(value = JSON_PROPERTY_DAYS_PER_MONTH, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDaysPerMonth(@jakarta.annotation.Nullable Integer daysPerMonth) {
    this.daysPerMonth = daysPerMonth;
  }

  public ProjectConfigurationModel activeFeatureFlags(@jakarta.annotation.Nullable List<String> activeFeatureFlags) {
    
    this.activeFeatureFlags = activeFeatureFlags;
    return this;
  }

  public ProjectConfigurationModel addActiveFeatureFlagsItem(String activeFeatureFlagsItem) {
    if (this.activeFeatureFlags == null) {
      this.activeFeatureFlags = new ArrayList<>();
    }
    this.activeFeatureFlags.add(activeFeatureFlagsItem);
    return this;
  }

  /**
   * The list of all feature flags that are active
   * @return activeFeatureFlags
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ACTIVE_FEATURE_FLAGS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getActiveFeatureFlags() {
    return activeFeatureFlags;
  }


  @JsonProperty(value = JSON_PROPERTY_ACTIVE_FEATURE_FLAGS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setActiveFeatureFlags(@jakarta.annotation.Nullable List<String> activeFeatureFlags) {
    this.activeFeatureFlags = activeFeatureFlags;
  }

  public ProjectConfigurationModel availableFeatures(@jakarta.annotation.Nullable List<String> availableFeatures) {
    
    this.availableFeatures = availableFeatures;
    return this;
  }

  public ProjectConfigurationModel addAvailableFeaturesItem(String availableFeaturesItem) {
    if (this.availableFeatures == null) {
      this.availableFeatures = new ArrayList<>();
    }
    this.availableFeatures.add(availableFeaturesItem);
    return this;
  }

  /**
   * The list of features that are active due to the current subscription plan
   * @return availableFeatures
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_AVAILABLE_FEATURES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getAvailableFeatures() {
    return availableFeatures;
  }


  @JsonProperty(value = JSON_PROPERTY_AVAILABLE_FEATURES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAvailableFeatures(@jakarta.annotation.Nullable List<String> availableFeatures) {
    this.availableFeatures = availableFeatures;
  }

  public ProjectConfigurationModel triallingFeatures(@jakarta.annotation.Nullable List<String> triallingFeatures) {
    
    this.triallingFeatures = triallingFeatures;
    return this;
  }

  public ProjectConfigurationModel addTriallingFeaturesItem(String triallingFeaturesItem) {
    if (this.triallingFeatures == null) {
      this.triallingFeatures = new ArrayList<>();
    }
    this.triallingFeatures.add(triallingFeaturesItem);
    return this;
  }

  /**
   * The list of features that are active due to the current trial phase
   * @return triallingFeatures
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TRIALLING_FEATURES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getTriallingFeatures() {
    return triallingFeatures;
  }


  @JsonProperty(value = JSON_PROPERTY_TRIALLING_FEATURES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTriallingFeatures(@jakarta.annotation.Nullable List<String> triallingFeatures) {
    this.triallingFeatures = triallingFeatures;
  }

  public ProjectConfigurationModel allowedLinkProtocols(@jakarta.annotation.Nullable List<String> allowedLinkProtocols) {
    
    this.allowedLinkProtocols = allowedLinkProtocols;
    return this;
  }

  public ProjectConfigurationModel addAllowedLinkProtocolsItem(String allowedLinkProtocolsItem) {
    if (this.allowedLinkProtocols == null) {
      this.allowedLinkProtocols = new ArrayList<>();
    }
    this.allowedLinkProtocols.add(allowedLinkProtocolsItem);
    return this;
  }

  /**
   * The list of allowed URL schemas for links
   * @return allowedLinkProtocols
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ALLOWED_LINK_PROTOCOLS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getAllowedLinkProtocols() {
    return allowedLinkProtocols;
  }


  @JsonProperty(value = JSON_PROPERTY_ALLOWED_LINK_PROTOCOLS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAllowedLinkProtocols(@jakarta.annotation.Nullable List<String> allowedLinkProtocols) {
    this.allowedLinkProtocols = allowedLinkProtocols;
  }

  public ProjectConfigurationModel wikisAvailable(@jakarta.annotation.Nullable Boolean wikisAvailable) {
    
    this.wikisAvailable = wikisAvailable;
    return this;
  }

  /**
   * True, if wiki functionality is enabled globally. False, if it is deactivated - e.g. because external wikis should be used.
   * @return wikisAvailable
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_WIKIS_AVAILABLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getWikisAvailable() {
    return wikisAvailable;
  }


  @JsonProperty(value = JSON_PROPERTY_WIKIS_AVAILABLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWikisAvailable(@jakarta.annotation.Nullable Boolean wikisAvailable) {
    this.wikisAvailable = wikisAvailable;
  }

  public ProjectConfigurationModel embedded(@jakarta.annotation.Nullable ConfigurationModelEmbedded embedded) {
    
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

  public ConfigurationModelEmbedded getEmbedded() {
    return embedded;
  }


  @JsonProperty(value = JSON_PROPERTY_EMBEDDED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEmbedded(@jakarta.annotation.Nullable ConfigurationModelEmbedded embedded) {
    this.embedded = embedded;
  }

  public ProjectConfigurationModel links(@jakarta.annotation.Nullable ConfigurationModelLinks links) {
    
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

  public ConfigurationModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable ConfigurationModelLinks links) {
    this.links = links;
  }

  /**
   * Whether internal comments are enabled for this project
   * @return enabledInternalComments
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ENABLED_INTERNAL_COMMENTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getEnabledInternalComments() {
    return enabledInternalComments;
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectConfigurationModel projectConfigurationModel = (ProjectConfigurationModel) o;
    return Objects.equals(this.halType, projectConfigurationModel.halType) &&
        Objects.equals(this.maximumAttachmentFileSize, projectConfigurationModel.maximumAttachmentFileSize) &&
        Objects.equals(this.maximumAPIV3PageSize, projectConfigurationModel.maximumAPIV3PageSize) &&
        Objects.equals(this.perPageOptions, projectConfigurationModel.perPageOptions) &&
        Objects.equals(this.hostName, projectConfigurationModel.hostName) &&
        Objects.equals(this.durationFormat, projectConfigurationModel.durationFormat) &&
        Objects.equals(this.dateFormat, projectConfigurationModel.dateFormat) &&
        Objects.equals(this.timeFormat, projectConfigurationModel.timeFormat) &&
        Objects.equals(this.userDefaultTimezone, projectConfigurationModel.userDefaultTimezone) &&
        Objects.equals(this.startOfWeek, projectConfigurationModel.startOfWeek) &&
        Objects.equals(this.hoursPerDay, projectConfigurationModel.hoursPerDay) &&
        Objects.equals(this.daysPerMonth, projectConfigurationModel.daysPerMonth) &&
        Objects.equals(this.activeFeatureFlags, projectConfigurationModel.activeFeatureFlags) &&
        Objects.equals(this.availableFeatures, projectConfigurationModel.availableFeatures) &&
        Objects.equals(this.triallingFeatures, projectConfigurationModel.triallingFeatures) &&
        Objects.equals(this.allowedLinkProtocols, projectConfigurationModel.allowedLinkProtocols) &&
        Objects.equals(this.wikisAvailable, projectConfigurationModel.wikisAvailable) &&
        Objects.equals(this.embedded, projectConfigurationModel.embedded) &&
        Objects.equals(this.links, projectConfigurationModel.links) &&
        Objects.equals(this.enabledInternalComments, projectConfigurationModel.enabledInternalComments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, maximumAttachmentFileSize, maximumAPIV3PageSize, perPageOptions, hostName, durationFormat, dateFormat, timeFormat, userDefaultTimezone, startOfWeek, hoursPerDay, daysPerMonth, activeFeatureFlags, availableFeatures, triallingFeatures, allowedLinkProtocols, wikisAvailable, embedded, links, enabledInternalComments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectConfigurationModel {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    maximumAttachmentFileSize: ").append(toIndentedString(maximumAttachmentFileSize)).append("\n");
    sb.append("    maximumAPIV3PageSize: ").append(toIndentedString(maximumAPIV3PageSize)).append("\n");
    sb.append("    perPageOptions: ").append(toIndentedString(perPageOptions)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
    sb.append("    durationFormat: ").append(toIndentedString(durationFormat)).append("\n");
    sb.append("    dateFormat: ").append(toIndentedString(dateFormat)).append("\n");
    sb.append("    timeFormat: ").append(toIndentedString(timeFormat)).append("\n");
    sb.append("    userDefaultTimezone: ").append(toIndentedString(userDefaultTimezone)).append("\n");
    sb.append("    startOfWeek: ").append(toIndentedString(startOfWeek)).append("\n");
    sb.append("    hoursPerDay: ").append(toIndentedString(hoursPerDay)).append("\n");
    sb.append("    daysPerMonth: ").append(toIndentedString(daysPerMonth)).append("\n");
    sb.append("    activeFeatureFlags: ").append(toIndentedString(activeFeatureFlags)).append("\n");
    sb.append("    availableFeatures: ").append(toIndentedString(availableFeatures)).append("\n");
    sb.append("    triallingFeatures: ").append(toIndentedString(triallingFeatures)).append("\n");
    sb.append("    allowedLinkProtocols: ").append(toIndentedString(allowedLinkProtocols)).append("\n");
    sb.append("    wikisAvailable: ").append(toIndentedString(wikisAvailable)).append("\n");
    sb.append("    embedded: ").append(toIndentedString(embedded)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    enabledInternalComments: ").append(toIndentedString(enabledInternalComments)).append("\n");
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

