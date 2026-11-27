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
import ai.gebo.openproject.client.model.MeetingAgendaItemModelLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MeetingAgendaItemModel
 */
@JsonPropertyOrder({
  MeetingAgendaItemModel.JSON_PROPERTY_HAL_TYPE,
  MeetingAgendaItemModel.JSON_PROPERTY_ID,
  MeetingAgendaItemModel.JSON_PROPERTY_TITLE,
  MeetingAgendaItemModel.JSON_PROPERTY_NOTES,
  MeetingAgendaItemModel.JSON_PROPERTY_POSITION,
  MeetingAgendaItemModel.JSON_PROPERTY_VOTE_SCORE,
  MeetingAgendaItemModel.JSON_PROPERTY_DURATION_IN_MINUTES,
  MeetingAgendaItemModel.JSON_PROPERTY_ITEM_TYPE,
  MeetingAgendaItemModel.JSON_PROPERTY_LOCK_VERSION,
  MeetingAgendaItemModel.JSON_PROPERTY_CREATED_AT,
  MeetingAgendaItemModel.JSON_PROPERTY_UPDATED_AT,
  MeetingAgendaItemModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class MeetingAgendaItemModel {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    MEETING_AGENDA_ITEM(String.valueOf("MeetingAgendaItem"));

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

  public static final String JSON_PROPERTY_NOTES = "notes";
  @jakarta.annotation.Nullable
  private Formattable notes;

  public static final String JSON_PROPERTY_POSITION = "position";
  @jakarta.annotation.Nonnull
  private Integer position;

  public static final String JSON_PROPERTY_VOTE_SCORE = "voteScore";
  @jakarta.annotation.Nullable
  private Integer voteScore;

  public static final String JSON_PROPERTY_DURATION_IN_MINUTES = "durationInMinutes";
  @jakarta.annotation.Nullable
  private Integer durationInMinutes;

  /**
   * The type of this agenda item. Possible values:  - *simple*: a simple text agenda item - *work_package*: an agenda item linked to a work package
   */
  public enum ItemTypeEnum {
    SIMPLE(String.valueOf("simple")),
    
    WORK_PACKAGE(String.valueOf("work_package"));

    private String value;

    ItemTypeEnum(String value) {
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
    public static ItemTypeEnum fromValue(String value) {
      for (ItemTypeEnum b : ItemTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_ITEM_TYPE = "itemType";
  @jakarta.annotation.Nonnull
  private ItemTypeEnum itemType;

  public static final String JSON_PROPERTY_LOCK_VERSION = "lockVersion";
  @jakarta.annotation.Nullable
  private Integer lockVersion;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private MeetingAgendaItemModelLinks links;

  public MeetingAgendaItemModel() {
  }
  /**
   * Constructor with only readonly parameters
   */
  @JsonCreator
  public MeetingAgendaItemModel(
    @JsonProperty(JSON_PROPERTY_VOTE_SCORE) Integer voteScore
  ) {
    this();
    this.voteScore = voteScore;
  }

  public MeetingAgendaItemModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public MeetingAgendaItemModel id(@jakarta.annotation.Nonnull Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * Identifier of this agenda item
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

  public MeetingAgendaItemModel title(@jakarta.annotation.Nonnull String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The agenda item&#39;s title. Required for simple items.
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

  public MeetingAgendaItemModel notes(@jakarta.annotation.Nullable Formattable notes) {
    
    this.notes = notes;
    return this;
  }

  /**
   * Get notes
   * @return notes
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_NOTES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Formattable getNotes() {
    return notes;
  }


  @JsonProperty(value = JSON_PROPERTY_NOTES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNotes(@jakarta.annotation.Nullable Formattable notes) {
    this.notes = notes;
  }

  public MeetingAgendaItemModel position(@jakarta.annotation.Nonnull Integer position) {
    
    this.position = position;
    return this;
  }

  /**
   * The manual position within the section, also used to break ties in vote-based sorting.
   * @return position
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_POSITION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getPosition() {
    return position;
  }


  @JsonProperty(value = JSON_PROPERTY_POSITION, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPosition(@jakarta.annotation.Nonnull Integer position) {
    this.position = position;
  }

  /**
   * The number of upvotes minus downvotes. Can be negative.
   * @return voteScore
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_VOTE_SCORE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getVoteScore() {
    return voteScore;
  }



  public MeetingAgendaItemModel durationInMinutes(@jakarta.annotation.Nullable Integer durationInMinutes) {
    
    this.durationInMinutes = durationInMinutes;
    return this;
  }

  /**
   * The agenda item&#39;s duration in minutes.
   * @return durationInMinutes
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DURATION_IN_MINUTES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getDurationInMinutes() {
    return durationInMinutes;
  }


  @JsonProperty(value = JSON_PROPERTY_DURATION_IN_MINUTES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDurationInMinutes(@jakarta.annotation.Nullable Integer durationInMinutes) {
    this.durationInMinutes = durationInMinutes;
  }

  public MeetingAgendaItemModel itemType(@jakarta.annotation.Nonnull ItemTypeEnum itemType) {
    
    this.itemType = itemType;
    return this;
  }

  /**
   * The type of this agenda item. Possible values:  - *simple*: a simple text agenda item - *work_package*: an agenda item linked to a work package
   * @return itemType
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_ITEM_TYPE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public ItemTypeEnum getItemType() {
    return itemType;
  }


  @JsonProperty(value = JSON_PROPERTY_ITEM_TYPE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setItemType(@jakarta.annotation.Nonnull ItemTypeEnum itemType) {
    this.itemType = itemType;
  }

  public MeetingAgendaItemModel lockVersion(@jakarta.annotation.Nullable Integer lockVersion) {
    
    this.lockVersion = lockVersion;
    return this;
  }

  /**
   * The version of the item as used for optimistic locking.
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

  public MeetingAgendaItemModel createdAt(@jakarta.annotation.Nonnull OffsetDateTime createdAt) {
    
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

  public MeetingAgendaItemModel updatedAt(@jakarta.annotation.Nonnull OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Time of the most recent change.
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

  public MeetingAgendaItemModel links(@jakarta.annotation.Nullable MeetingAgendaItemModelLinks links) {
    
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

  public MeetingAgendaItemModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable MeetingAgendaItemModelLinks links) {
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
    MeetingAgendaItemModel meetingAgendaItemModel = (MeetingAgendaItemModel) o;
    return Objects.equals(this.halType, meetingAgendaItemModel.halType) &&
        Objects.equals(this.id, meetingAgendaItemModel.id) &&
        Objects.equals(this.title, meetingAgendaItemModel.title) &&
        Objects.equals(this.notes, meetingAgendaItemModel.notes) &&
        Objects.equals(this.position, meetingAgendaItemModel.position) &&
        Objects.equals(this.voteScore, meetingAgendaItemModel.voteScore) &&
        Objects.equals(this.durationInMinutes, meetingAgendaItemModel.durationInMinutes) &&
        Objects.equals(this.itemType, meetingAgendaItemModel.itemType) &&
        Objects.equals(this.lockVersion, meetingAgendaItemModel.lockVersion) &&
        Objects.equals(this.createdAt, meetingAgendaItemModel.createdAt) &&
        Objects.equals(this.updatedAt, meetingAgendaItemModel.updatedAt) &&
        Objects.equals(this.links, meetingAgendaItemModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, id, title, notes, position, voteScore, durationInMinutes, itemType, lockVersion, createdAt, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingAgendaItemModel {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    voteScore: ").append(toIndentedString(voteScore)).append("\n");
    sb.append("    durationInMinutes: ").append(toIndentedString(durationInMinutes)).append("\n");
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
    sb.append("    lockVersion: ").append(toIndentedString(lockVersion)).append("\n");
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

