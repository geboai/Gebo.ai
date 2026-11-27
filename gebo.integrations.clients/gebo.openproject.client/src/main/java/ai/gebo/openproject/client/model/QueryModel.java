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
import ai.gebo.openproject.client.model.QueryFilterInstanceModel;
import ai.gebo.openproject.client.model.QueryModelLinks;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * QueryModel
 */
@JsonPropertyOrder({
  QueryModel.JSON_PROPERTY_ID,
  QueryModel.JSON_PROPERTY_NAME,
  QueryModel.JSON_PROPERTY_FILTERS,
  QueryModel.JSON_PROPERTY_SUMS,
  QueryModel.JSON_PROPERTY_TIMELINE_VISIBLE,
  QueryModel.JSON_PROPERTY_TIMELINE_LABELS,
  QueryModel.JSON_PROPERTY_TIMELINE_ZOOM_LEVEL,
  QueryModel.JSON_PROPERTY_TIMESTAMPS,
  QueryModel.JSON_PROPERTY_HIGHLIGHTING_MODE,
  QueryModel.JSON_PROPERTY_SHOW_HIERARCHIES,
  QueryModel.JSON_PROPERTY_HIDDEN,
  QueryModel.JSON_PROPERTY_PUBLIC,
  QueryModel.JSON_PROPERTY_STARRED,
  QueryModel.JSON_PROPERTY_CREATED_AT,
  QueryModel.JSON_PROPERTY_UPDATED_AT,
  QueryModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class QueryModel {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nullable
  private Integer id;

  public static final String JSON_PROPERTY_NAME = "name";
  @jakarta.annotation.Nullable
  private String name;

  public static final String JSON_PROPERTY_FILTERS = "filters";
  @jakarta.annotation.Nullable
  private List<QueryFilterInstanceModel> filters = new ArrayList<>();

  public static final String JSON_PROPERTY_SUMS = "sums";
  @jakarta.annotation.Nullable
  private Boolean sums;

  public static final String JSON_PROPERTY_TIMELINE_VISIBLE = "timelineVisible";
  @jakarta.annotation.Nullable
  private Boolean timelineVisible;

  public static final String JSON_PROPERTY_TIMELINE_LABELS = "timelineLabels";
  @jakarta.annotation.Nullable
  private List<String> timelineLabels = new ArrayList<>();

  public static final String JSON_PROPERTY_TIMELINE_ZOOM_LEVEL = "timelineZoomLevel";
  @jakarta.annotation.Nullable
  private String timelineZoomLevel;

  public static final String JSON_PROPERTY_TIMESTAMPS = "timestamps";
  @jakarta.annotation.Nullable
  private List<Object> timestamps = new ArrayList<>();

  public static final String JSON_PROPERTY_HIGHLIGHTING_MODE = "highlightingMode";
  @jakarta.annotation.Nullable
  private String highlightingMode;

  public static final String JSON_PROPERTY_SHOW_HIERARCHIES = "showHierarchies";
  @jakarta.annotation.Nullable
  private Boolean showHierarchies;

  public static final String JSON_PROPERTY_HIDDEN = "hidden";
  @jakarta.annotation.Nullable
  private Boolean hidden;

  public static final String JSON_PROPERTY_PUBLIC = "public";
  @jakarta.annotation.Nullable
  private Boolean _public;

  public static final String JSON_PROPERTY_STARRED = "starred";
  @jakarta.annotation.Nullable
  private Boolean starred;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nonnull
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private QueryModelLinks links;

  public QueryModel() {
  }
  /**
   * Constructor with only readonly parameters
   */
  @JsonCreator
  public QueryModel(
    @JsonProperty(JSON_PROPERTY_ID) Integer id, 
    @JsonProperty(JSON_PROPERTY_NAME) String name, 
    @JsonProperty(JSON_PROPERTY_SUMS) Boolean sums, 
    @JsonProperty(JSON_PROPERTY_TIMELINE_VISIBLE) Boolean timelineVisible, 
    @JsonProperty(JSON_PROPERTY_TIMELINE_LABELS) List<String> timelineLabels, 
    @JsonProperty(JSON_PROPERTY_TIMELINE_ZOOM_LEVEL) String timelineZoomLevel, 
    @JsonProperty(JSON_PROPERTY_HIGHLIGHTING_MODE) String highlightingMode, 
    @JsonProperty(JSON_PROPERTY_SHOW_HIERARCHIES) Boolean showHierarchies, 
    @JsonProperty(JSON_PROPERTY_HIDDEN) Boolean hidden, 
    @JsonProperty(JSON_PROPERTY_PUBLIC) Boolean _public, 
    @JsonProperty(JSON_PROPERTY_STARRED) Boolean starred, 
    @JsonProperty(JSON_PROPERTY_CREATED_AT) OffsetDateTime createdAt, 
    @JsonProperty(JSON_PROPERTY_UPDATED_AT) OffsetDateTime updatedAt
  ) {
    this();
    this.id = id;
    this.name = name;
    this.sums = sums;
    this.timelineVisible = timelineVisible;
    this.timelineLabels = timelineLabels;
    this.timelineZoomLevel = timelineZoomLevel;
    this.highlightingMode = highlightingMode;
    this.showHierarchies = showHierarchies;
    this.hidden = hidden;
    this._public = _public;
    this.starred = starred;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  /**
   * Query id
   * minimum: 0
   * @return id
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getId() {
    return id;
  }



  /**
   * Query name
   * @return name
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }



  public QueryModel filters(@jakarta.annotation.Nullable List<QueryFilterInstanceModel> filters) {
    
    this.filters = filters;
    return this;
  }

  public QueryModel addFiltersItem(QueryFilterInstanceModel filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * A set of QueryFilters which will be applied to the work packages to determine the resulting work packages
   * @return filters
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_FILTERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<QueryFilterInstanceModel> getFilters() {
    return filters;
  }


  @JsonProperty(value = JSON_PROPERTY_FILTERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFilters(@jakarta.annotation.Nullable List<QueryFilterInstanceModel> filters) {
    this.filters = filters;
  }

  /**
   * Should sums (of supported properties) be shown?
   * @return sums
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SUMS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getSums() {
    return sums;
  }



  /**
   * Should the timeline mode be shown?
   * @return timelineVisible
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIMELINE_VISIBLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getTimelineVisible() {
    return timelineVisible;
  }



  /**
   * Which labels are shown in the timeline, empty when default
   * @return timelineLabels
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIMELINE_LABELS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getTimelineLabels() {
    return timelineLabels;
  }



  /**
   * Which zoom level should the timeline be rendered in?
   * @return timelineZoomLevel
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIMELINE_ZOOM_LEVEL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTimelineZoomLevel() {
    return timelineZoomLevel;
  }



  public QueryModel timestamps(@jakarta.annotation.Nullable List<Object> timestamps) {
    
    this.timestamps = timestamps;
    return this;
  }

  public QueryModel addTimestampsItem(Object timestampsItem) {
    if (this.timestamps == null) {
      this.timestamps = new ArrayList<>();
    }
    this.timestamps.add(timestampsItem);
    return this;
  }

  /**
   * Timestamps to filter by when showing changed attributes on work packages.
   * @return timestamps
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIMESTAMPS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Object> getTimestamps() {
    return timestamps;
  }


  @JsonProperty(value = JSON_PROPERTY_TIMESTAMPS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTimestamps(@jakarta.annotation.Nullable List<Object> timestamps) {
    this.timestamps = timestamps;
  }

  /**
   * Which highlighting mode should the table have?
   * @return highlightingMode
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HIGHLIGHTING_MODE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getHighlightingMode() {
    return highlightingMode;
  }



  /**
   * Should the hierarchy mode be enabled?
   * @return showHierarchies
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SHOW_HIERARCHIES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getShowHierarchies() {
    return showHierarchies;
  }



  /**
   * Should the query be hidden from the query list?
   * @return hidden
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HIDDEN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getHidden() {
    return hidden;
  }



  /**
   * Can users besides the owner see the query?
   * @return _public
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PUBLIC, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getPublic() {
    return _public;
  }



  /**
   * Should the query be highlighted to the user?
   * @return starred
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STARRED, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getStarred() {
    return starred;
  }



  /**
   * Time of creation
   * @return createdAt
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }



  /**
   * Time of the most recent change to the query
   * @return updatedAt
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }



  public QueryModel links(@jakarta.annotation.Nullable QueryModelLinks links) {
    
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

  public QueryModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable QueryModelLinks links) {
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
    QueryModel queryModel = (QueryModel) o;
    return Objects.equals(this.id, queryModel.id) &&
        Objects.equals(this.name, queryModel.name) &&
        Objects.equals(this.filters, queryModel.filters) &&
        Objects.equals(this.sums, queryModel.sums) &&
        Objects.equals(this.timelineVisible, queryModel.timelineVisible) &&
        Objects.equals(this.timelineLabels, queryModel.timelineLabels) &&
        Objects.equals(this.timelineZoomLevel, queryModel.timelineZoomLevel) &&
        Objects.equals(this.timestamps, queryModel.timestamps) &&
        Objects.equals(this.highlightingMode, queryModel.highlightingMode) &&
        Objects.equals(this.showHierarchies, queryModel.showHierarchies) &&
        Objects.equals(this.hidden, queryModel.hidden) &&
        Objects.equals(this._public, queryModel._public) &&
        Objects.equals(this.starred, queryModel.starred) &&
        Objects.equals(this.createdAt, queryModel.createdAt) &&
        Objects.equals(this.updatedAt, queryModel.updatedAt) &&
        Objects.equals(this.links, queryModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, filters, sums, timelineVisible, timelineLabels, timelineZoomLevel, timestamps, highlightingMode, showHierarchies, hidden, _public, starred, createdAt, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    sums: ").append(toIndentedString(sums)).append("\n");
    sb.append("    timelineVisible: ").append(toIndentedString(timelineVisible)).append("\n");
    sb.append("    timelineLabels: ").append(toIndentedString(timelineLabels)).append("\n");
    sb.append("    timelineZoomLevel: ").append(toIndentedString(timelineZoomLevel)).append("\n");
    sb.append("    timestamps: ").append(toIndentedString(timestamps)).append("\n");
    sb.append("    highlightingMode: ").append(toIndentedString(highlightingMode)).append("\n");
    sb.append("    showHierarchies: ").append(toIndentedString(showHierarchies)).append("\n");
    sb.append("    hidden: ").append(toIndentedString(hidden)).append("\n");
    sb.append("    _public: ").append(toIndentedString(_public)).append("\n");
    sb.append("    starred: ").append(toIndentedString(starred)).append("\n");
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

