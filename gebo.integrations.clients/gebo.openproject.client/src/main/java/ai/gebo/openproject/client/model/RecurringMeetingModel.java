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
import ai.gebo.openproject.client.model.RecurringMeetingModelLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * RecurringMeetingModel
 */
@JsonPropertyOrder({
  RecurringMeetingModel.JSON_PROPERTY_HAL_TYPE,
  RecurringMeetingModel.JSON_PROPERTY_ID,
  RecurringMeetingModel.JSON_PROPERTY_TITLE,
  RecurringMeetingModel.JSON_PROPERTY_FREQUENCY,
  RecurringMeetingModel.JSON_PROPERTY_INTERVAL,
  RecurringMeetingModel.JSON_PROPERTY_MONTHLY_DAY,
  RecurringMeetingModel.JSON_PROPERTY_MONTHLY_ORDINAL,
  RecurringMeetingModel.JSON_PROPERTY_MONTHLY_WEEKDAY,
  RecurringMeetingModel.JSON_PROPERTY_END_AFTER,
  RecurringMeetingModel.JSON_PROPERTY_END_DATE,
  RecurringMeetingModel.JSON_PROPERTY_ITERATIONS,
  RecurringMeetingModel.JSON_PROPERTY_TIME_ZONE,
  RecurringMeetingModel.JSON_PROPERTY_START_TIME,
  RecurringMeetingModel.JSON_PROPERTY_LOCATION,
  RecurringMeetingModel.JSON_PROPERTY_DURATION,
  RecurringMeetingModel.JSON_PROPERTY_NOTIFY,
  RecurringMeetingModel.JSON_PROPERTY_CREATED_AT,
  RecurringMeetingModel.JSON_PROPERTY_UPDATED_AT,
  RecurringMeetingModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class RecurringMeetingModel {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    RECURRING_MEETING(String.valueOf("RecurringMeeting"));

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

  /**
   * The recurrence frequency. Possible values:  - *daily*: repeats every day (or every N days depending on interval) - *working_days*: repeats on working days only - *weekly*: repeats every week (or every N weeks depending on interval) - *monthly_day_of_month*: repeats monthly on a specific day of month (for example, the 16th) - *monthly_nth_weekday*: repeats monthly by weekday pattern (for example, first Tuesday, last Friday)
   */
  public enum FrequencyEnum {
    DAILY(String.valueOf("daily")),
    
    WORKING_DAYS(String.valueOf("working_days")),
    
    WEEKLY(String.valueOf("weekly")),
    
    MONTHLY_DAY_OF_MONTH(String.valueOf("monthly_day_of_month")),
    
    MONTHLY_NTH_WEEKDAY(String.valueOf("monthly_nth_weekday"));

    private String value;

    FrequencyEnum(String value) {
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
    public static FrequencyEnum fromValue(String value) {
      for (FrequencyEnum b : FrequencyEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_FREQUENCY = "frequency";
  @jakarta.annotation.Nonnull
  private FrequencyEnum frequency;

  public static final String JSON_PROPERTY_INTERVAL = "interval";
  @jakarta.annotation.Nonnull
  private Integer interval;

  public static final String JSON_PROPERTY_MONTHLY_DAY = "monthlyDay";
  @jakarta.annotation.Nullable
  private Integer monthlyDay;

  /**
   * Ordinal position for &#x60;monthly_nth_weekday&#x60; frequency. Allowed values: - &#x60;1&#x60; (first) - &#x60;2&#x60; (second) - &#x60;3&#x60; (third) - &#x60;4&#x60; (fourth) - &#x60;-1&#x60; (last) Used when frequency is &#x60;monthly_nth_weekday&#x60;.
   */
  public enum MonthlyOrdinalEnum {
    NUMBER_1(1),
    
    NUMBER_2(2),
    
    NUMBER_3(3),
    
    NUMBER_4(4),
    
    NUMBER_MINUS_1(-1);

    private Integer value;

    MonthlyOrdinalEnum(Integer value) {
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
    public static MonthlyOrdinalEnum fromValue(Integer value) {
      for (MonthlyOrdinalEnum b : MonthlyOrdinalEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_MONTHLY_ORDINAL = "monthlyOrdinal";
  @jakarta.annotation.Nullable
  private MonthlyOrdinalEnum monthlyOrdinal;

  /**
   * Weekday for &#x60;monthly_nth_weekday&#x60; frequency. Used when frequency is &#x60;monthly_nth_weekday&#x60;.
   */
  public enum MonthlyWeekdayEnum {
    SUNDAY(String.valueOf("sunday")),
    
    MONDAY(String.valueOf("monday")),
    
    TUESDAY(String.valueOf("tuesday")),
    
    WEDNESDAY(String.valueOf("wednesday")),
    
    THURSDAY(String.valueOf("thursday")),
    
    FRIDAY(String.valueOf("friday")),
    
    SATURDAY(String.valueOf("saturday"));

    private String value;

    MonthlyWeekdayEnum(String value) {
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
    public static MonthlyWeekdayEnum fromValue(String value) {
      for (MonthlyWeekdayEnum b : MonthlyWeekdayEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_MONTHLY_WEEKDAY = "monthlyWeekday";
  @jakarta.annotation.Nullable
  private MonthlyWeekdayEnum monthlyWeekday;

  /**
   * How the recurrence ends. Possible values:  - *specific_date*: ends on a specific date - *iterations*: ends after a specific number of occurrences - *never*: never ends
   */
  public enum EndAfterEnum {
    SPECIFIC_DATE(String.valueOf("specific_date")),
    
    ITERATIONS(String.valueOf("iterations")),
    
    NEVER(String.valueOf("never"));

    private String value;

    EndAfterEnum(String value) {
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
    public static EndAfterEnum fromValue(String value) {
      for (EndAfterEnum b : EndAfterEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_END_AFTER = "endAfter";
  @jakarta.annotation.Nonnull
  private EndAfterEnum endAfter;

  public static final String JSON_PROPERTY_END_DATE = "endDate";
  @jakarta.annotation.Nullable
  private LocalDate endDate;

  public static final String JSON_PROPERTY_ITERATIONS = "iterations";
  @jakarta.annotation.Nullable
  private Integer iterations;

  public static final String JSON_PROPERTY_TIME_ZONE = "timeZone";
  @jakarta.annotation.Nullable
  private String timeZone;

  public static final String JSON_PROPERTY_START_TIME = "startTime";
  @jakarta.annotation.Nonnull
  private OffsetDateTime startTime;

  public static final String JSON_PROPERTY_LOCATION = "location";
  @jakarta.annotation.Nullable
  private String location;

  public static final String JSON_PROPERTY_DURATION = "duration";
  @jakarta.annotation.Nullable
  private BigDecimal duration;

  public static final String JSON_PROPERTY_NOTIFY = "notify";
  @jakarta.annotation.Nullable
  private Boolean notify;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private RecurringMeetingModelLinks links;

  public RecurringMeetingModel() {
  }

  public RecurringMeetingModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public RecurringMeetingModel id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * Identifier of this recurring meeting
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

  public RecurringMeetingModel title(@jakarta.annotation.Nonnull String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The recurring meeting&#39;s title
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

  public RecurringMeetingModel frequency(@jakarta.annotation.Nonnull FrequencyEnum frequency) {
    
    this.frequency = frequency;
    return this;
  }

  /**
   * The recurrence frequency. Possible values:  - *daily*: repeats every day (or every N days depending on interval) - *working_days*: repeats on working days only - *weekly*: repeats every week (or every N weeks depending on interval) - *monthly_day_of_month*: repeats monthly on a specific day of month (for example, the 16th) - *monthly_nth_weekday*: repeats monthly by weekday pattern (for example, first Tuesday, last Friday)
   * @return frequency
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_FREQUENCY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public FrequencyEnum getFrequency() {
    return frequency;
  }


  @JsonProperty(value = JSON_PROPERTY_FREQUENCY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFrequency(@jakarta.annotation.Nonnull FrequencyEnum frequency) {
    this.frequency = frequency;
  }

  public RecurringMeetingModel interval(@jakarta.annotation.Nonnull Integer interval) {
    
    this.interval = interval;
    return this;
  }

  /**
   * The interval between occurrences. For example, an interval of 2 with a weekly frequency means the meeting occurs every 2 weeks. Not applicable for working_days frequency.
   * minimum: 1
   * @return interval
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_INTERVAL, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getInterval() {
    return interval;
  }


  @JsonProperty(value = JSON_PROPERTY_INTERVAL, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setInterval(@jakarta.annotation.Nonnull Integer interval) {
    this.interval = interval;
  }

  public RecurringMeetingModel monthlyDay(@jakarta.annotation.Nullable Integer monthlyDay) {
    
    this.monthlyDay = monthlyDay;
    return this;
  }

  /**
   * Day of month for &#x60;monthly_day_of_month&#x60; frequency. Used when frequency is &#x60;monthly_day_of_month&#x60;.
   * minimum: 1
   * maximum: 31
   * @return monthlyDay
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MONTHLY_DAY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getMonthlyDay() {
    return monthlyDay;
  }


  @JsonProperty(value = JSON_PROPERTY_MONTHLY_DAY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMonthlyDay(@jakarta.annotation.Nullable Integer monthlyDay) {
    this.monthlyDay = monthlyDay;
  }

  public RecurringMeetingModel monthlyOrdinal(@jakarta.annotation.Nullable MonthlyOrdinalEnum monthlyOrdinal) {
    
    this.monthlyOrdinal = monthlyOrdinal;
    return this;
  }

  /**
   * Ordinal position for &#x60;monthly_nth_weekday&#x60; frequency. Allowed values: - &#x60;1&#x60; (first) - &#x60;2&#x60; (second) - &#x60;3&#x60; (third) - &#x60;4&#x60; (fourth) - &#x60;-1&#x60; (last) Used when frequency is &#x60;monthly_nth_weekday&#x60;.
   * @return monthlyOrdinal
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MONTHLY_ORDINAL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MonthlyOrdinalEnum getMonthlyOrdinal() {
    return monthlyOrdinal;
  }


  @JsonProperty(value = JSON_PROPERTY_MONTHLY_ORDINAL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMonthlyOrdinal(@jakarta.annotation.Nullable MonthlyOrdinalEnum monthlyOrdinal) {
    this.monthlyOrdinal = monthlyOrdinal;
  }

  public RecurringMeetingModel monthlyWeekday(@jakarta.annotation.Nullable MonthlyWeekdayEnum monthlyWeekday) {
    
    this.monthlyWeekday = monthlyWeekday;
    return this;
  }

  /**
   * Weekday for &#x60;monthly_nth_weekday&#x60; frequency. Used when frequency is &#x60;monthly_nth_weekday&#x60;.
   * @return monthlyWeekday
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MONTHLY_WEEKDAY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MonthlyWeekdayEnum getMonthlyWeekday() {
    return monthlyWeekday;
  }


  @JsonProperty(value = JSON_PROPERTY_MONTHLY_WEEKDAY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMonthlyWeekday(@jakarta.annotation.Nullable MonthlyWeekdayEnum monthlyWeekday) {
    this.monthlyWeekday = monthlyWeekday;
  }

  public RecurringMeetingModel endAfter(@jakarta.annotation.Nonnull EndAfterEnum endAfter) {
    
    this.endAfter = endAfter;
    return this;
  }

  /**
   * How the recurrence ends. Possible values:  - *specific_date*: ends on a specific date - *iterations*: ends after a specific number of occurrences - *never*: never ends
   * @return endAfter
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_END_AFTER, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public EndAfterEnum getEndAfter() {
    return endAfter;
  }


  @JsonProperty(value = JSON_PROPERTY_END_AFTER, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEndAfter(@jakarta.annotation.Nonnull EndAfterEnum endAfter) {
    this.endAfter = endAfter;
  }

  public RecurringMeetingModel endDate(@jakarta.annotation.Nullable LocalDate endDate) {
    
    this.endDate = endDate;
    return this;
  }

  /**
   * The date on which the recurrence ends. Only present when endAfter is &#x60;specific_date&#x60;.
   * @return endDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_END_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDate getEndDate() {
    return endDate;
  }


  @JsonProperty(value = JSON_PROPERTY_END_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEndDate(@jakarta.annotation.Nullable LocalDate endDate) {
    this.endDate = endDate;
  }

  public RecurringMeetingModel iterations(@jakarta.annotation.Nullable Integer iterations) {
    
    this.iterations = iterations;
    return this;
  }

  /**
   * The number of occurrences after which the recurrence ends. Only present when endAfter is &#x60;iterations&#x60;.
   * minimum: 1
   * @return iterations
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ITERATIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getIterations() {
    return iterations;
  }


  @JsonProperty(value = JSON_PROPERTY_ITERATIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIterations(@jakarta.annotation.Nullable Integer iterations) {
    this.iterations = iterations;
  }

  public RecurringMeetingModel timeZone(@jakarta.annotation.Nullable String timeZone) {
    
    this.timeZone = timeZone;
    return this;
  }

  /**
   * The time zone in which the recurring meeting is scheduled (e.g. \&quot;Europe/Berlin\&quot;).
   * @return timeZone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIME_ZONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTimeZone() {
    return timeZone;
  }


  @JsonProperty(value = JSON_PROPERTY_TIME_ZONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTimeZone(@jakarta.annotation.Nullable String timeZone) {
    this.timeZone = timeZone;
  }

  public RecurringMeetingModel startTime(@jakarta.annotation.Nonnull OffsetDateTime startTime) {
    
    this.startTime = startTime;
    return this;
  }

  /**
   * The scheduled start time of the recurring meeting.
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

  public RecurringMeetingModel location(@jakarta.annotation.Nullable String location) {
    
    this.location = location;
    return this;
  }

  /**
   * The meeting&#39;s location (inherited from the template meeting).
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

  public RecurringMeetingModel duration(@jakarta.annotation.Nullable BigDecimal duration) {
    
    this.duration = duration;
    return this;
  }

  /**
   * The meeting duration in hours (inherited from the template meeting).
   * @return duration
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DURATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public BigDecimal getDuration() {
    return duration;
  }


  @JsonProperty(value = JSON_PROPERTY_DURATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDuration(@jakarta.annotation.Nullable BigDecimal duration) {
    this.duration = duration;
  }

  public RecurringMeetingModel notify(@jakarta.annotation.Nullable Boolean notify) {
    
    this.notify = notify;
    return this;
  }

  /**
   * Whether email notifications are enabled for the series (read from the template meeting). Read-only on this resource: set it when completing the template via &#x60;POST /recurring_meetings/{id}/template_completed&#x60;, or change it later by updating the template meeting (&#x60;PATCH /meetings/{template_id}&#x60;).
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

  public RecurringMeetingModel createdAt(@jakarta.annotation.Nonnull OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Time of creation.
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

  public RecurringMeetingModel updatedAt(@jakarta.annotation.Nonnull OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change to the recurring meeting.
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

  public RecurringMeetingModel links(@jakarta.annotation.Nullable RecurringMeetingModelLinks links) {
    
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

  public RecurringMeetingModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable RecurringMeetingModelLinks links) {
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
    RecurringMeetingModel recurringMeetingModel = (RecurringMeetingModel) o;
    return Objects.equals(this.halType, recurringMeetingModel.halType) &&
        Objects.equals(this.id, recurringMeetingModel.id) &&
        Objects.equals(this.title, recurringMeetingModel.title) &&
        Objects.equals(this.frequency, recurringMeetingModel.frequency) &&
        Objects.equals(this.interval, recurringMeetingModel.interval) &&
        Objects.equals(this.monthlyDay, recurringMeetingModel.monthlyDay) &&
        Objects.equals(this.monthlyOrdinal, recurringMeetingModel.monthlyOrdinal) &&
        Objects.equals(this.monthlyWeekday, recurringMeetingModel.monthlyWeekday) &&
        Objects.equals(this.endAfter, recurringMeetingModel.endAfter) &&
        Objects.equals(this.endDate, recurringMeetingModel.endDate) &&
        Objects.equals(this.iterations, recurringMeetingModel.iterations) &&
        Objects.equals(this.timeZone, recurringMeetingModel.timeZone) &&
        Objects.equals(this.startTime, recurringMeetingModel.startTime) &&
        Objects.equals(this.location, recurringMeetingModel.location) &&
        Objects.equals(this.duration, recurringMeetingModel.duration) &&
        Objects.equals(this.notify, recurringMeetingModel.notify) &&
        Objects.equals(this.createdAt, recurringMeetingModel.createdAt) &&
        Objects.equals(this.updatedAt, recurringMeetingModel.updatedAt) &&
        Objects.equals(this.links, recurringMeetingModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, id, title, frequency, interval, monthlyDay, monthlyOrdinal, monthlyWeekday, endAfter, endDate, iterations, timeZone, startTime, location, duration, notify, createdAt, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecurringMeetingModel {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    frequency: ").append(toIndentedString(frequency)).append("\n");
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
    sb.append("    monthlyDay: ").append(toIndentedString(monthlyDay)).append("\n");
    sb.append("    monthlyOrdinal: ").append(toIndentedString(monthlyOrdinal)).append("\n");
    sb.append("    monthlyWeekday: ").append(toIndentedString(monthlyWeekday)).append("\n");
    sb.append("    endAfter: ").append(toIndentedString(endAfter)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    iterations: ").append(toIndentedString(iterations)).append("\n");
    sb.append("    timeZone: ").append(toIndentedString(timeZone)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    notify: ").append(toIndentedString(notify)).append("\n");
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

