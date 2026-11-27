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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * SpecificationModel
 */
@JsonPropertyOrder({
  SpecificationModel.JSON_PROPERTY_OPENAPI,
  SpecificationModel.JSON_PROPERTY_INFO,
  SpecificationModel.JSON_PROPERTY_SERVERS,
  SpecificationModel.JSON_PROPERTY_PATHS,
  SpecificationModel.JSON_PROPERTY_COMPONENTS,
  SpecificationModel.JSON_PROPERTY_TAGS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class SpecificationModel {
  public static final String JSON_PROPERTY_OPENAPI = "openapi";
  @jakarta.annotation.Nonnull
  private String openapi;

  public static final String JSON_PROPERTY_INFO = "info";
  @jakarta.annotation.Nonnull
  private Object info;

  public static final String JSON_PROPERTY_SERVERS = "servers";
  @jakarta.annotation.Nullable
  private List<Object> servers = new ArrayList<>();

  public static final String JSON_PROPERTY_PATHS = "paths";
  @jakarta.annotation.Nullable
  private Object paths;

  public static final String JSON_PROPERTY_COMPONENTS = "components";
  @jakarta.annotation.Nullable
  private Object components;

  public static final String JSON_PROPERTY_TAGS = "tags";
  @jakarta.annotation.Nullable
  private List<Object> tags = new ArrayList<>();

  public SpecificationModel() {
  }

  public SpecificationModel openapi(@jakarta.annotation.Nonnull String openapi) {
    
    this.openapi = openapi;
    return this;
  }

  /**
   * The OpenAPI specification version number.
   * @return openapi
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_OPENAPI, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getOpenapi() {
    return openapi;
  }


  @JsonProperty(value = JSON_PROPERTY_OPENAPI, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOpenapi(@jakarta.annotation.Nonnull String openapi) {
    this.openapi = openapi;
  }

  public SpecificationModel info(@jakarta.annotation.Nonnull Object info) {
    
    this.info = info;
    return this;
  }

  /**
   * Provides metadata about the API.
   * @return info
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_INFO, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Object getInfo() {
    return info;
  }


  @JsonProperty(value = JSON_PROPERTY_INFO, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setInfo(@jakarta.annotation.Nonnull Object info) {
    this.info = info;
  }

  public SpecificationModel servers(@jakarta.annotation.Nullable List<Object> servers) {
    
    this.servers = servers;
    return this;
  }

  public SpecificationModel addServersItem(Object serversItem) {
    if (this.servers == null) {
      this.servers = new ArrayList<>();
    }
    this.servers.add(serversItem);
    return this;
  }

  /**
   * An array of Server Objects.
   * @return servers
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SERVERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Object> getServers() {
    return servers;
  }


  @JsonProperty(value = JSON_PROPERTY_SERVERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setServers(@jakarta.annotation.Nullable List<Object> servers) {
    this.servers = servers;
  }

  public SpecificationModel paths(@jakarta.annotation.Nullable Object paths) {
    
    this.paths = paths;
    return this;
  }

  /**
   * The available paths and operations for the API.
   * @return paths
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PATHS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getPaths() {
    return paths;
  }


  @JsonProperty(value = JSON_PROPERTY_PATHS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPaths(@jakarta.annotation.Nullable Object paths) {
    this.paths = paths;
  }

  public SpecificationModel components(@jakarta.annotation.Nullable Object components) {
    
    this.components = components;
    return this;
  }

  /**
   * An element to hold various Objects for the OpenAPI Description.
   * @return components
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_COMPONENTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getComponents() {
    return components;
  }


  @JsonProperty(value = JSON_PROPERTY_COMPONENTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setComponents(@jakarta.annotation.Nullable Object components) {
    this.components = components;
  }

  public SpecificationModel tags(@jakarta.annotation.Nullable List<Object> tags) {
    
    this.tags = tags;
    return this;
  }

  public SpecificationModel addTagsItem(Object tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * A list of tags used by the OpenAPI Description with additional metadata.
   * @return tags
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TAGS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Object> getTags() {
    return tags;
  }


  @JsonProperty(value = JSON_PROPERTY_TAGS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTags(@jakarta.annotation.Nullable List<Object> tags) {
    this.tags = tags;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpecificationModel specificationModel = (SpecificationModel) o;
    return Objects.equals(this.openapi, specificationModel.openapi) &&
        Objects.equals(this.info, specificationModel.info) &&
        Objects.equals(this.servers, specificationModel.servers) &&
        Objects.equals(this.paths, specificationModel.paths) &&
        Objects.equals(this.components, specificationModel.components) &&
        Objects.equals(this.tags, specificationModel.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(openapi, info, servers, paths, components, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpecificationModel {\n");
    sb.append("    openapi: ").append(toIndentedString(openapi)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    servers: ").append(toIndentedString(servers)).append("\n");
    sb.append("    paths: ").append(toIndentedString(paths)).append("\n");
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

