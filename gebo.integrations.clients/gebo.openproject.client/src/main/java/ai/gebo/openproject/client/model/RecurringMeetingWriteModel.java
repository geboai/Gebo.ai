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
import ai.gebo.openproject.client.model.RecurringMeetingWriteModelLinks;
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
 * RecurringMeetingWriteModel
 */
@JsonPropertyOrder({
  RecurringMeetingWriteModel.JSON_PROPERTY_TITLE,
  RecurringMeetingWriteModel.JSON_PROPERTY_FREQUENCY,
  RecurringMeetingWriteModel.JSON_PROPERTY_INTERVAL,
  RecurringMeetingWriteModel.JSON_PROPERTY_MONTHLY_DAY,
  RecurringMeetingWriteModel.JSON_PROPERTY_MONTHLY_ORDINAL,
  RecurringMeetingWriteModel.JSON_PROPERTY_MONTHLY_WEEKDAY,
  RecurringMeetingWriteModel.JSON_PROPERTY_END_AFTER,
  RecurringMeetingWriteModel.JSON_PROPERTY_END_DATE,
  RecurringMeetingWriteModel.JSON_PROPERTY_ITERATIONS,
  RecurringMeetingWriteModel.JSON_PROPERTY_START_TIME,
  RecurringMeetingWriteModel.JSON_PROPERTY_LOCATION,
  RecurringMeetingWriteModel.JSON_PROPERTY_DURATION,
  RecurringMeetingWriteModel.JSON_PROPERTY_NOTIFY,
  RecurringMeetingWriteModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class RecurringMeetingWriteModel {
  public static final String JSON_PROPERTY_TITLE = "title";
  @jakarta.annotation.Nullable
  private String title;

  /**
   * The recurrence frequency. Possible values:  - *daily* - *working_days* - *weekly* - *monthly_day_of_month* - *monthly_nth_weekday*
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
  @jakarta.annotation.Nullable
  private FrequencyEnum frequency;

  public static final String JSON_PROPERTY_INTERVAL = "interval";
  @jakarta.annotation.Nullable
  private Integer interval;

  public static final String JSON_PROPERTY_MONTHLY_DAY = "monthlyDay";
  @jakarta.annotation.Nullable
  private Integer monthlyDay;

  /**
   * Ordinal position for &#x60;monthly_nth_weekday&#x60; frequency. Required for &#x60;monthly_nth_weekday&#x60; unless inferred from &#x60;startTime&#x60;. Allowed values: - &#x60;1&#x60; (first) - &#x60;2&#x60; (second) - &#x60;3&#x60; (third) - &#x60;4&#x60; (fourth) - &#x60;-1&#x60; (last)
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
   * Weekday for &#x60;monthly_nth_weekday&#x60; frequency. Required for &#x60;monthly_nth_weekday&#x60; unless inferred from &#x60;startTime&#x60;.
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
   * How the recurrence ends. Possible values:  - *specific_date* - *iterations* - *never*
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
  @jakarta.annotation.Nullable
  private EndAfterEnum endAfter;

  public static final String JSON_PROPERTY_END_DATE = "endDate";
  @jakarta.annotation.Nullable
  private LocalDate endDate;

  public static final String JSON_PROPERTY_ITERATIONS = "iterations";
  @jakarta.annotation.Nullable
  private Integer iterations;

  public static final String JSON_PROPERTY_START_TIME = "startTime";
  @jakarta.annotation.Nullable
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

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private RecurringMeetingWriteModelLinks links;

  public RecurringMeetingWriteModel() {
  }

  public RecurringMeetingWriteModel title(@jakarta.annotation.Nullable String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The recurring meeting&#39;s title
   * @return title
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TITLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTitle() {
    return title;
  }


  @JsonProperty(value = JSON_PROPERTY_TITLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTitle(@jakarta.annotation.Nullable String title) {
    this.title = title;
  }

  public RecurringMeetingWriteModel frequency(@jakarta.annotation.Nullable FrequencyEnum frequency) {
    
    this.frequency = frequency;
    return this;
  }

  /**
   * The recurrence frequency. Possible values:  - *daily* - *working_days* - *weekly* - *monthly_day_of_month* - *monthly_nth_weekday*
   * @return frequency
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_FREQUENCY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public FrequencyEnum getFrequency() {
    return frequency;
  }


  @JsonProperty(value = JSON_PROPERTY_FREQUENCY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFrequency(@jakarta.annotation.Nullable FrequencyEnum frequency) {
    this.frequency = frequency;
  }

  public RecurringMeetingWriteModel interval(@jakarta.annotation.Nullable Integer interval) {
    
    this.interval = interval;
    return this;
  }

  /**
   * The interval between occurrences. Not applicable for working_days frequency.
   * minimum: 1
   * @return interval
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_INTERVAL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getInterval() {
    return interval;
  }


  @JsonProperty(value = JSON_PROPERTY_INTERVAL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInterval(@jakarta.annotation.Nullable Integer interval) {
    this.interval = interval;
  }

  public RecurringMeetingWriteModel monthlyDay(@jakarta.annotation.Nullable Integer monthlyDay) {
    
    this.monthlyDay = monthlyDay;
    return this;
  }

  /**
   * Day of month for &#x60;monthly_day_of_month&#x60; frequency. Required for &#x60;monthly_day_of_month&#x60; unless inferred from &#x60;startTime&#x60;.
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

  public RecurringMeetingWriteModel monthlyOrdinal(@jakarta.annotation.Nullable MonthlyOrdinalEnum monthlyOrdinal) {
    
    this.monthlyOrdinal = monthlyOrdinal;
    return this;
  }

  /**
   * Ordinal position for &#x60;monthly_nth_weekday&#x60; frequency. Required for &#x60;monthly_nth_weekday&#x60; unless inferred from &#x60;startTime&#x60;. Allowed values: - &#x60;1&#x60; (first) - &#x60;2&#x60; (second) - &#x60;3&#x60; (third) - &#x60;4&#x60; (fourth) - &#x60;-1&#x60; (last)
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

  public RecurringMeetingWriteModel monthlyWeekday(@jakarta.annotation.Nullable MonthlyWeekdayEnum monthlyWeekday) {
    
    this.monthlyWeekday = monthlyWeekday;
    return this;
  }

  /**
   * Weekday for &#x60;monthly_nth_weekday&#x60; frequency. Required for &#x60;monthly_nth_weekday&#x60; unless inferred from &#x60;startTime&#x60;.
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

  public RecurringMeetingWriteModel endAfter(@jakarta.annotation.Nullable EndAfterEnum endAfter) {
    
    this.endAfter = endAfter;
    return this;
  }

  /**
   * How the recurrence ends. Possible values:  - *specific_date* - *iterations* - *never*
   * @return endAfter
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_END_AFTER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public EndAfterEnum getEndAfter() {
    return endAfter;
  }


  @JsonProperty(value = JSON_PROPERTY_END_AFTER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEndAfter(@jakarta.annotation.Nullable EndAfterEnum endAfter) {
    this.endAfter = endAfter;
  }

  public RecurringMeetingWriteModel endDate(@jakarta.annotation.Nullable LocalDate endDate) {
    
    this.endDate = endDate;
    return this;
  }

  /**
   * The date on which the recurrence ends. Required when endAfter is &#x60;specific_date&#x60;.
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

  public RecurringMeetingWriteModel iterations(@jakarta.annotation.Nullable Integer iterations) {
    
    this.iterations = iterations;
    return this;
  }

  /**
   * The number of occurrences after which the recurrence ends. Required when endAfter is &#x60;iterations&#x60;.
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

  public RecurringMeetingWriteModel startTime(@jakarta.annotation.Nullable OffsetDateTime startTime) {
    
    this.startTime = startTime;
    return this;
  }

  /**
   * The scheduled start time of the recurring meeting.
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

  public RecurringMeetingWriteModel location(@jakarta.annotation.Nullable String location) {
    
    this.location = location;
    return this;
  }

  /**
   * The meeting&#39;s location.
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

  public RecurringMeetingWriteModel duration(@jakarta.annotation.Nullable BigDecimal duration) {
    
    this.duration = duration;
    return this;
  }

  /**
   * The meeting duration in hours.
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

  public RecurringMeetingWriteModel notify(@jakarta.annotation.Nullable Boolean notify) {
    
    this.notify = notify;
    return this;
  }

  /**
   * Whether to send email notifications to participants.
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

  public RecurringMeetingWriteModel links(@jakarta.annotation.Nullable RecurringMeetingWriteModelLinks links) {
    
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

  public RecurringMeetingWriteModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable RecurringMeetingWriteModelLinks links) {
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
    RecurringMeetingWriteModel recurringMeetingWriteModel = (RecurringMeetingWriteModel) o;
    return Objects.equals(this.title, recurringMeetingWriteModel.title) &&
        Objects.equals(this.frequency, recurringMeetingWriteModel.frequency) &&
        Objects.equals(this.interval, recurringMeetingWriteModel.interval) &&
        Objects.equals(this.monthlyDay, recurringMeetingWriteModel.monthlyDay) &&
        Objects.equals(this.monthlyOrdinal, recurringMeetingWriteModel.monthlyOrdinal) &&
        Objects.equals(this.monthlyWeekday, recurringMeetingWriteModel.monthlyWeekday) &&
        Objects.equals(this.endAfter, recurringMeetingWriteModel.endAfter) &&
        Objects.equals(this.endDate, recurringMeetingWriteModel.endDate) &&
        Objects.equals(this.iterations, recurringMeetingWriteModel.iterations) &&
        Objects.equals(this.startTime, recurringMeetingWriteModel.startTime) &&
        Objects.equals(this.location, recurringMeetingWriteModel.location) &&
        Objects.equals(this.duration, recurringMeetingWriteModel.duration) &&
        Objects.equals(this.notify, recurringMeetingWriteModel.notify) &&
        Objects.equals(this.links, recurringMeetingWriteModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, frequency, interval, monthlyDay, monthlyOrdinal, monthlyWeekday, endAfter, endDate, iterations, startTime, location, duration, notify, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecurringMeetingWriteModel {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    frequency: ").append(toIndentedString(frequency)).append("\n");
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
    sb.append("    monthlyDay: ").append(toIndentedString(monthlyDay)).append("\n");
    sb.append("    monthlyOrdinal: ").append(toIndentedString(monthlyOrdinal)).append("\n");
    sb.append("    monthlyWeekday: ").append(toIndentedString(monthlyWeekday)).append("\n");
    sb.append("    endAfter: ").append(toIndentedString(endAfter)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    iterations: ").append(toIndentedString(iterations)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    notify: ").append(toIndentedString(notify)).append("\n");
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

