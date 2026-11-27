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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * GridWidgetModel
 */
@JsonPropertyOrder({
  GridWidgetModel.JSON_PROPERTY_HAL_TYPE,
  GridWidgetModel.JSON_PROPERTY_ID,
  GridWidgetModel.JSON_PROPERTY_IDENTIFIER,
  GridWidgetModel.JSON_PROPERTY_START_ROW,
  GridWidgetModel.JSON_PROPERTY_END_ROW,
  GridWidgetModel.JSON_PROPERTY_START_COLUMN,
  GridWidgetModel.JSON_PROPERTY_END_COLUMN,
  GridWidgetModel.JSON_PROPERTY_OPTIONS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class GridWidgetModel {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    GRID_WIDGET(String.valueOf("GridWidget"));

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
  @jakarta.annotation.Nullable
  private Integer id;

  public static final String JSON_PROPERTY_IDENTIFIER = "identifier";
  @jakarta.annotation.Nonnull
  private String identifier;

  public static final String JSON_PROPERTY_START_ROW = "startRow";
  @jakarta.annotation.Nonnull
  private Integer startRow;

  public static final String JSON_PROPERTY_END_ROW = "endRow";
  @jakarta.annotation.Nonnull
  private Integer endRow;

  public static final String JSON_PROPERTY_START_COLUMN = "startColumn";
  @jakarta.annotation.Nonnull
  private Integer startColumn;

  public static final String JSON_PROPERTY_END_COLUMN = "endColumn";
  @jakarta.annotation.Nonnull
  private Integer endColumn;

  public static final String JSON_PROPERTY_OPTIONS = "options";
  @jakarta.annotation.Nullable
  private Object options;

  public GridWidgetModel() {
  }

  public GridWidgetModel halType(@jakarta.annotation.Nonnull HalTypeEnum halType) {
    
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

  public GridWidgetModel id(@jakarta.annotation.Nullable Integer id) {
    
    this.id = id;
    return this;
  }

  /**
   * The grid widget&#39;s unique identifier. Can be null, if a new widget is created within a grid.
   * minimum: 1
   * @return id
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getId() {
    return id;
  }


  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(@jakarta.annotation.Nullable Integer id) {
    this.id = id;
  }

  public GridWidgetModel identifier(@jakarta.annotation.Nonnull String identifier) {
    
    this.identifier = identifier;
    return this;
  }

  /**
   * An alternative, human legible, and unique identifier.
   * @return identifier
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_IDENTIFIER, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getIdentifier() {
    return identifier;
  }


  @JsonProperty(value = JSON_PROPERTY_IDENTIFIER, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIdentifier(@jakarta.annotation.Nonnull String identifier) {
    this.identifier = identifier;
  }

  public GridWidgetModel startRow(@jakarta.annotation.Nonnull Integer startRow) {
    
    this.startRow = startRow;
    return this;
  }

  /**
   * The index of the starting row of the widget. The row is inclusive.
   * minimum: 1
   * @return startRow
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_START_ROW, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getStartRow() {
    return startRow;
  }


  @JsonProperty(value = JSON_PROPERTY_START_ROW, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStartRow(@jakarta.annotation.Nonnull Integer startRow) {
    this.startRow = startRow;
  }

  public GridWidgetModel endRow(@jakarta.annotation.Nonnull Integer endRow) {
    
    this.endRow = endRow;
    return this;
  }

  /**
   * The index of the ending row of the widget. The row is exclusive.
   * minimum: 1
   * @return endRow
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_END_ROW, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getEndRow() {
    return endRow;
  }


  @JsonProperty(value = JSON_PROPERTY_END_ROW, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEndRow(@jakarta.annotation.Nonnull Integer endRow) {
    this.endRow = endRow;
  }

  public GridWidgetModel startColumn(@jakarta.annotation.Nonnull Integer startColumn) {
    
    this.startColumn = startColumn;
    return this;
  }

  /**
   * The index of the starting column of the widget. The column is inclusive.
   * minimum: 1
   * @return startColumn
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_START_COLUMN, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getStartColumn() {
    return startColumn;
  }


  @JsonProperty(value = JSON_PROPERTY_START_COLUMN, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStartColumn(@jakarta.annotation.Nonnull Integer startColumn) {
    this.startColumn = startColumn;
  }

  public GridWidgetModel endColumn(@jakarta.annotation.Nonnull Integer endColumn) {
    
    this.endColumn = endColumn;
    return this;
  }

  /**
   * The index of the ending column of the widget. The column is exclusive.
   * minimum: 1
   * @return endColumn
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_END_COLUMN, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getEndColumn() {
    return endColumn;
  }


  @JsonProperty(value = JSON_PROPERTY_END_COLUMN, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEndColumn(@jakarta.annotation.Nonnull Integer endColumn) {
    this.endColumn = endColumn;
  }

  public GridWidgetModel options(@jakarta.annotation.Nullable Object options) {
    
    this.options = options;
    return this;
  }

  /**
   * Get options
   * @return options
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_OPTIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getOptions() {
    return options;
  }


  @JsonProperty(value = JSON_PROPERTY_OPTIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOptions(@jakarta.annotation.Nullable Object options) {
    this.options = options;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GridWidgetModel gridWidgetModel = (GridWidgetModel) o;
    return Objects.equals(this.halType, gridWidgetModel.halType) &&
        Objects.equals(this.id, gridWidgetModel.id) &&
        Objects.equals(this.identifier, gridWidgetModel.identifier) &&
        Objects.equals(this.startRow, gridWidgetModel.startRow) &&
        Objects.equals(this.endRow, gridWidgetModel.endRow) &&
        Objects.equals(this.startColumn, gridWidgetModel.startColumn) &&
        Objects.equals(this.endColumn, gridWidgetModel.endColumn) &&
        Objects.equals(this.options, gridWidgetModel.options);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, id, identifier, startRow, endRow, startColumn, endColumn, options);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GridWidgetModel {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    identifier: ").append(toIndentedString(identifier)).append("\n");
    sb.append("    startRow: ").append(toIndentedString(startRow)).append("\n");
    sb.append("    endRow: ").append(toIndentedString(endRow)).append("\n");
    sb.append("    startColumn: ").append(toIndentedString(startColumn)).append("\n");
    sb.append("    endColumn: ").append(toIndentedString(endColumn)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
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

