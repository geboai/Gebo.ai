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
import ai.gebo.openproject.client.model.Link;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * FileLinkReadModelLinks
 */
@JsonPropertyOrder({
  FileLinkReadModelLinks.JSON_PROPERTY_SELF,
  FileLinkReadModelLinks.JSON_PROPERTY_STORAGE,
  FileLinkReadModelLinks.JSON_PROPERTY_CONTAINER,
  FileLinkReadModelLinks.JSON_PROPERTY_CREATOR,
  FileLinkReadModelLinks.JSON_PROPERTY_DELETE,
  FileLinkReadModelLinks.JSON_PROPERTY_STATUS,
  FileLinkReadModelLinks.JSON_PROPERTY_ORIGIN_OPEN,
  FileLinkReadModelLinks.JSON_PROPERTY_STATIC_ORIGIN_OPEN,
  FileLinkReadModelLinks.JSON_PROPERTY_ORIGIN_OPEN_LOCATION,
  FileLinkReadModelLinks.JSON_PROPERTY_STATIC_ORIGIN_OPEN_LOCATION,
  FileLinkReadModelLinks.JSON_PROPERTY_STATIC_ORIGIN_DOWNLOAD
})
@JsonTypeName("FileLinkReadModel__links")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class FileLinkReadModelLinks {
  public static final String JSON_PROPERTY_SELF = "self";
  @jakarta.annotation.Nullable
  private Link self;

  public static final String JSON_PROPERTY_STORAGE = "storage";
  @jakarta.annotation.Nullable
  private Link storage;

  public static final String JSON_PROPERTY_CONTAINER = "container";
  @jakarta.annotation.Nullable
  private Link container;

  public static final String JSON_PROPERTY_CREATOR = "creator";
  @jakarta.annotation.Nullable
  private Link creator;

  public static final String JSON_PROPERTY_DELETE = "delete";
  @jakarta.annotation.Nullable
  private Link delete;

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nullable
  private Link status;

  public static final String JSON_PROPERTY_ORIGIN_OPEN = "originOpen";
  @jakarta.annotation.Nullable
  private Link originOpen;

  public static final String JSON_PROPERTY_STATIC_ORIGIN_OPEN = "staticOriginOpen";
  @jakarta.annotation.Nullable
  private Link staticOriginOpen;

  public static final String JSON_PROPERTY_ORIGIN_OPEN_LOCATION = "originOpenLocation";
  @jakarta.annotation.Nullable
  private Link originOpenLocation;

  public static final String JSON_PROPERTY_STATIC_ORIGIN_OPEN_LOCATION = "staticOriginOpenLocation";
  @jakarta.annotation.Nullable
  private Link staticOriginOpenLocation;

  public static final String JSON_PROPERTY_STATIC_ORIGIN_DOWNLOAD = "staticOriginDownload";
  @jakarta.annotation.Nullable
  private Link staticOriginDownload;

  public FileLinkReadModelLinks() {
  }

  public FileLinkReadModelLinks self(@jakarta.annotation.Nullable Link self) {
    
    this.self = self;
    return this;
  }

  /**
   * This file link.  **Resource**: FileLink
   * @return self
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SELF, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getSelf() {
    return self;
  }


  @JsonProperty(value = JSON_PROPERTY_SELF, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSelf(@jakarta.annotation.Nullable Link self) {
    this.self = self;
  }

  public FileLinkReadModelLinks storage(@jakarta.annotation.Nullable Link storage) {
    
    this.storage = storage;
    return this;
  }

  /**
   * The storage resource of the linked file.  **Resource**: Storage
   * @return storage
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STORAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getStorage() {
    return storage;
  }


  @JsonProperty(value = JSON_PROPERTY_STORAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStorage(@jakarta.annotation.Nullable Link storage) {
    this.storage = storage;
  }

  public FileLinkReadModelLinks container(@jakarta.annotation.Nullable Link container) {
    
    this.container = container;
    return this;
  }

  /**
   * The container the origin file is linked to.  Can be one of the following **Resources**:  - WorkPackage
   * @return container
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CONTAINER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getContainer() {
    return container;
  }


  @JsonProperty(value = JSON_PROPERTY_CONTAINER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setContainer(@jakarta.annotation.Nullable Link container) {
    this.container = container;
  }

  public FileLinkReadModelLinks creator(@jakarta.annotation.Nullable Link creator) {
    
    this.creator = creator;
    return this;
  }

  /**
   * The creator of the file link.  **Resource**: User
   * @return creator
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getCreator() {
    return creator;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreator(@jakarta.annotation.Nullable Link creator) {
    this.creator = creator;
  }

  public FileLinkReadModelLinks delete(@jakarta.annotation.Nullable Link delete) {
    
    this.delete = delete;
    return this;
  }

  /**
   * The uri to delete the file link.  **Resource**: N/A
   * @return delete
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DELETE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getDelete() {
    return delete;
  }


  @JsonProperty(value = JSON_PROPERTY_DELETE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDelete(@jakarta.annotation.Nullable Link delete) {
    this.delete = delete;
  }

  public FileLinkReadModelLinks status(@jakarta.annotation.Nullable Link status) {
    
    this.status = status;
    return this;
  }

  /**
   * The urn of the user specific file link status on its storage. Can be one of:  - urn:openproject-org:api:v3:file-links:permission:ViewAllowed - urn:openproject-org:api:v3:file-links:permission:ViewNotAllowed - urn:openproject-org:api:v3:file-links:NotFound - urn:openproject-org:api:v3:file-links:Error  **Resource**: N/A
   * @return status
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATUS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getStatus() {
    return status;
  }


  @JsonProperty(value = JSON_PROPERTY_STATUS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStatus(@jakarta.annotation.Nullable Link status) {
    this.status = status;
  }

  public FileLinkReadModelLinks originOpen(@jakarta.annotation.Nullable Link originOpen) {
    
    this.originOpen = originOpen;
    return this;
  }

  /**
   * The uri to open the origin file on the origin itself.  **Resource**: N/A
   * @return originOpen
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ORIGIN_OPEN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getOriginOpen() {
    return originOpen;
  }


  @JsonProperty(value = JSON_PROPERTY_ORIGIN_OPEN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOriginOpen(@jakarta.annotation.Nullable Link originOpen) {
    this.originOpen = originOpen;
  }

  public FileLinkReadModelLinks staticOriginOpen(@jakarta.annotation.Nullable Link staticOriginOpen) {
    
    this.staticOriginOpen = staticOriginOpen;
    return this;
  }

  /**
   * A static uri to open the origin file on the storage. Responds with a redirect.  **Resource**: N/A
   * @return staticOriginOpen
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATIC_ORIGIN_OPEN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getStaticOriginOpen() {
    return staticOriginOpen;
  }


  @JsonProperty(value = JSON_PROPERTY_STATIC_ORIGIN_OPEN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStaticOriginOpen(@jakarta.annotation.Nullable Link staticOriginOpen) {
    this.staticOriginOpen = staticOriginOpen;
  }

  public FileLinkReadModelLinks originOpenLocation(@jakarta.annotation.Nullable Link originOpenLocation) {
    
    this.originOpenLocation = originOpenLocation;
    return this;
  }

  /**
   * The uri to open the location of origin file on the origin itself.  **Resource**: N/A
   * @return originOpenLocation
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ORIGIN_OPEN_LOCATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getOriginOpenLocation() {
    return originOpenLocation;
  }


  @JsonProperty(value = JSON_PROPERTY_ORIGIN_OPEN_LOCATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOriginOpenLocation(@jakarta.annotation.Nullable Link originOpenLocation) {
    this.originOpenLocation = originOpenLocation;
  }

  public FileLinkReadModelLinks staticOriginOpenLocation(@jakarta.annotation.Nullable Link staticOriginOpenLocation) {
    
    this.staticOriginOpenLocation = staticOriginOpenLocation;
    return this;
  }

  /**
   * A static uri to open the location of the origin file on the storage. Responds with a redirect.  **Resource**: N/A
   * @return staticOriginOpenLocation
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATIC_ORIGIN_OPEN_LOCATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getStaticOriginOpenLocation() {
    return staticOriginOpenLocation;
  }


  @JsonProperty(value = JSON_PROPERTY_STATIC_ORIGIN_OPEN_LOCATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStaticOriginOpenLocation(@jakarta.annotation.Nullable Link staticOriginOpenLocation) {
    this.staticOriginOpenLocation = staticOriginOpenLocation;
  }

  public FileLinkReadModelLinks staticOriginDownload(@jakarta.annotation.Nullable Link staticOriginDownload) {
    
    this.staticOriginDownload = staticOriginDownload;
    return this;
  }

  /**
   * A static uri to generate a new download URL from the storage. Responds with a redirect.  **Resource**: N/A
   * @return staticOriginDownload
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATIC_ORIGIN_DOWNLOAD, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getStaticOriginDownload() {
    return staticOriginDownload;
  }


  @JsonProperty(value = JSON_PROPERTY_STATIC_ORIGIN_DOWNLOAD, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStaticOriginDownload(@jakarta.annotation.Nullable Link staticOriginDownload) {
    this.staticOriginDownload = staticOriginDownload;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileLinkReadModelLinks fileLinkReadModelLinks = (FileLinkReadModelLinks) o;
    return Objects.equals(this.self, fileLinkReadModelLinks.self) &&
        Objects.equals(this.storage, fileLinkReadModelLinks.storage) &&
        Objects.equals(this.container, fileLinkReadModelLinks.container) &&
        Objects.equals(this.creator, fileLinkReadModelLinks.creator) &&
        Objects.equals(this.delete, fileLinkReadModelLinks.delete) &&
        Objects.equals(this.status, fileLinkReadModelLinks.status) &&
        Objects.equals(this.originOpen, fileLinkReadModelLinks.originOpen) &&
        Objects.equals(this.staticOriginOpen, fileLinkReadModelLinks.staticOriginOpen) &&
        Objects.equals(this.originOpenLocation, fileLinkReadModelLinks.originOpenLocation) &&
        Objects.equals(this.staticOriginOpenLocation, fileLinkReadModelLinks.staticOriginOpenLocation) &&
        Objects.equals(this.staticOriginDownload, fileLinkReadModelLinks.staticOriginDownload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, storage, container, creator, delete, status, originOpen, staticOriginOpen, originOpenLocation, staticOriginOpenLocation, staticOriginDownload);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileLinkReadModelLinks {\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    storage: ").append(toIndentedString(storage)).append("\n");
    sb.append("    container: ").append(toIndentedString(container)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    originOpen: ").append(toIndentedString(originOpen)).append("\n");
    sb.append("    staticOriginOpen: ").append(toIndentedString(staticOriginOpen)).append("\n");
    sb.append("    originOpenLocation: ").append(toIndentedString(originOpenLocation)).append("\n");
    sb.append("    staticOriginOpenLocation: ").append(toIndentedString(staticOriginOpenLocation)).append("\n");
    sb.append("    staticOriginDownload: ").append(toIndentedString(staticOriginDownload)).append("\n");
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

