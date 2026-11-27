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
import ai.gebo.openproject.client.model.RepositoryIssueModelLabelsInner;
import ai.gebo.openproject.client.model.RepositoryIssueModelLinks;
import ai.gebo.openproject.client.model.RepositoryUserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * RepositoryIssueModel
 */
@JsonPropertyOrder({
  RepositoryIssueModel.JSON_PROPERTY_ID,
  RepositoryIssueModel.JSON_PROPERTY_DISPLAY_ID,
  RepositoryIssueModel.JSON_PROPERTY_TITLE,
  RepositoryIssueModel.JSON_PROPERTY_DESCRIPTION,
  RepositoryIssueModel.JSON_PROPERTY_STATE,
  RepositoryIssueModel.JSON_PROPERTY_LABELS,
  RepositoryIssueModel.JSON_PROPERTY_PROJECT_NAME,
  RepositoryIssueModel.JSON_PROPERTY_WEB_URL,
  RepositoryIssueModel.JSON_PROPERTY_AUTHOR,
  RepositoryIssueModel.JSON_PROPERTY_EXTERNAL_UPDATED_AT,
  RepositoryIssueModel.JSON_PROPERTY_CREATED_AT,
  RepositoryIssueModel.JSON_PROPERTY_UPDATED_AT,
  RepositoryIssueModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class RepositoryIssueModel {
  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nullable
  private Integer id;

  public static final String JSON_PROPERTY_DISPLAY_ID = "displayId";
  @jakarta.annotation.Nullable
  private String displayId;

  public static final String JSON_PROPERTY_TITLE = "title";
  @jakarta.annotation.Nullable
  private String title;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  @jakarta.annotation.Nullable
  private Formattable description;

  public static final String JSON_PROPERTY_STATE = "state";
  @jakarta.annotation.Nullable
  private String state;

  public static final String JSON_PROPERTY_LABELS = "labels";
  @jakarta.annotation.Nullable
  private List<RepositoryIssueModelLabelsInner> labels = new ArrayList<>();

  public static final String JSON_PROPERTY_PROJECT_NAME = "projectName";
  @jakarta.annotation.Nullable
  private String projectName;

  public static final String JSON_PROPERTY_WEB_URL = "webUrl";
  @jakarta.annotation.Nullable
  private URI webUrl;

  public static final String JSON_PROPERTY_AUTHOR = "author";
  @jakarta.annotation.Nullable
  private RepositoryUserModel author;

  public static final String JSON_PROPERTY_EXTERNAL_UPDATED_AT = "externalUpdatedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime externalUpdatedAt;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nullable
  private OffsetDateTime updatedAt;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private RepositoryIssueModelLinks links;

  public RepositoryIssueModel() {
  }
  /**
   * Constructor with only readonly parameters
   */
  @JsonCreator
  public RepositoryIssueModel(
    @JsonProperty(JSON_PROPERTY_ID) Integer id, 
    @JsonProperty(JSON_PROPERTY_DISPLAY_ID) String displayId, 
    @JsonProperty(JSON_PROPERTY_TITLE) String title, 
    @JsonProperty(JSON_PROPERTY_DESCRIPTION) Formattable description, 
    @JsonProperty(JSON_PROPERTY_STATE) String state, 
    @JsonProperty(JSON_PROPERTY_LABELS) List<RepositoryIssueModelLabelsInner> labels, 
    @JsonProperty(JSON_PROPERTY_PROJECT_NAME) String projectName, 
    @JsonProperty(JSON_PROPERTY_WEB_URL) URI webUrl, 
    @JsonProperty(JSON_PROPERTY_EXTERNAL_UPDATED_AT) OffsetDateTime externalUpdatedAt, 
    @JsonProperty(JSON_PROPERTY_CREATED_AT) OffsetDateTime createdAt, 
    @JsonProperty(JSON_PROPERTY_UPDATED_AT) OffsetDateTime updatedAt
  ) {
    this();
    this.id = id;
    this.displayId = displayId;
    this.title = title;
    this.description = description;
    this.state = state;
    this.labels = labels;
    this.projectName = projectName;
    this.webUrl = webUrl;
    this.externalUpdatedAt = externalUpdatedAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  /**
   * Identifier of the resource within OpenProject
   * @return id
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getId() {
    return id;
  }



  /**
   * Identifier of the issue as displayed by the provider, e.g. &#x60;42&#x60;
   * @return displayId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DISPLAY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDisplayId() {
    return displayId;
  }



  /**
   * Title of the issue
   * @return title
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TITLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTitle() {
    return title;
  }



  /**
   * Description of the issue
   * @return description
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Formattable getDescription() {
    return description;
  }



  /**
   * State of the issue as reported by the provider, e.g. &#x60;opened&#x60;
   * @return state
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getState() {
    return state;
  }



  /**
   * Labels attached to the resource on the provider
   * @return labels
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LABELS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<RepositoryIssueModelLabelsInner> getLabels() {
    return labels;
  }



  /**
   * Name of the project on the provider, e.g. &#x60;my-group/my-project&#x60;
   * @return projectName
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT_NAME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getProjectName() {
    return projectName;
  }



  /**
   * URL of the resource on the provider&#39;s website
   * @return webUrl
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_WEB_URL, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public URI getWebUrl() {
    return webUrl;
  }



  public RepositoryIssueModel author(@jakarta.annotation.Nullable RepositoryUserModel author) {
    
    this.author = author;
    return this;
  }

  /**
   * The user on the provider who created the resource. Null if unknown.
   * @return author
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_AUTHOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public RepositoryUserModel getAuthor() {
    return author;
  }


  @JsonProperty(value = JSON_PROPERTY_AUTHOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAuthor(@jakarta.annotation.Nullable RepositoryUserModel author) {
    this.author = author;
  }

  /**
   * Time the resource was last updated on the provider
   * @return externalUpdatedAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_EXTERNAL_UPDATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getExternalUpdatedAt() {
    return externalUpdatedAt;
  }



  /**
   * Time the resource was first synchronized into OpenProject
   * @return createdAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }



  /**
   * Time the resource was last synchronized into OpenProject
   * @return updatedAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }



  public RepositoryIssueModel links(@jakarta.annotation.Nullable RepositoryIssueModelLinks links) {
    
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

  public RepositoryIssueModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable RepositoryIssueModelLinks links) {
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
    RepositoryIssueModel repositoryIssueModel = (RepositoryIssueModel) o;
    return Objects.equals(this.id, repositoryIssueModel.id) &&
        Objects.equals(this.displayId, repositoryIssueModel.displayId) &&
        Objects.equals(this.title, repositoryIssueModel.title) &&
        Objects.equals(this.description, repositoryIssueModel.description) &&
        Objects.equals(this.state, repositoryIssueModel.state) &&
        Objects.equals(this.labels, repositoryIssueModel.labels) &&
        Objects.equals(this.projectName, repositoryIssueModel.projectName) &&
        Objects.equals(this.webUrl, repositoryIssueModel.webUrl) &&
        Objects.equals(this.author, repositoryIssueModel.author) &&
        Objects.equals(this.externalUpdatedAt, repositoryIssueModel.externalUpdatedAt) &&
        Objects.equals(this.createdAt, repositoryIssueModel.createdAt) &&
        Objects.equals(this.updatedAt, repositoryIssueModel.updatedAt) &&
        Objects.equals(this.links, repositoryIssueModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, displayId, title, description, state, labels, projectName, webUrl, author, externalUpdatedAt, createdAt, updatedAt, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RepositoryIssueModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    displayId: ").append(toIndentedString(displayId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    projectName: ").append(toIndentedString(projectName)).append("\n");
    sb.append("    webUrl: ").append(toIndentedString(webUrl)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    externalUpdatedAt: ").append(toIndentedString(externalUpdatedAt)).append("\n");
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

