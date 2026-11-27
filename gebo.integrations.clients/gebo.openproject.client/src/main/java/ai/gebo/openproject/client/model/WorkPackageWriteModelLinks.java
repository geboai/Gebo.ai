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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * WorkPackageWriteModelLinks
 */
@JsonPropertyOrder({
  WorkPackageWriteModelLinks.JSON_PROPERTY_CATEGORY,
  WorkPackageWriteModelLinks.JSON_PROPERTY_TYPE,
  WorkPackageWriteModelLinks.JSON_PROPERTY_PRIORITY,
  WorkPackageWriteModelLinks.JSON_PROPERTY_PROJECT,
  WorkPackageWriteModelLinks.JSON_PROPERTY_STATUS,
  WorkPackageWriteModelLinks.JSON_PROPERTY_RESPONSIBLE,
  WorkPackageWriteModelLinks.JSON_PROPERTY_ASSIGNEE,
  WorkPackageWriteModelLinks.JSON_PROPERTY_VERSION,
  WorkPackageWriteModelLinks.JSON_PROPERTY_TARGET_VERSIONS,
  WorkPackageWriteModelLinks.JSON_PROPERTY_OBSERVED_IN_VERSIONS,
  WorkPackageWriteModelLinks.JSON_PROPERTY_LABELS,
  WorkPackageWriteModelLinks.JSON_PROPERTY_PARENT
})
@JsonTypeName("WorkPackageWriteModel__links")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class WorkPackageWriteModelLinks {
  public static final String JSON_PROPERTY_CATEGORY = "category";
  @jakarta.annotation.Nullable
  private Link category;

  public static final String JSON_PROPERTY_TYPE = "type";
  @jakarta.annotation.Nullable
  private Link type;

  public static final String JSON_PROPERTY_PRIORITY = "priority";
  @jakarta.annotation.Nullable
  private Link priority;

  public static final String JSON_PROPERTY_PROJECT = "project";
  @jakarta.annotation.Nullable
  private Link project;

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nullable
  private Link status;

  public static final String JSON_PROPERTY_RESPONSIBLE = "responsible";
  @jakarta.annotation.Nullable
  private Link responsible;

  public static final String JSON_PROPERTY_ASSIGNEE = "assignee";
  @jakarta.annotation.Nullable
  private Link assignee;

  public static final String JSON_PROPERTY_VERSION = "version";
  @jakarta.annotation.Nullable
  private Link version;

  public static final String JSON_PROPERTY_TARGET_VERSIONS = "targetVersions";
  @jakarta.annotation.Nullable
  private List<Link> targetVersions = new ArrayList<>();

  public static final String JSON_PROPERTY_OBSERVED_IN_VERSIONS = "observedInVersions";
  @jakarta.annotation.Nullable
  private List<Link> observedInVersions = new ArrayList<>();

  public static final String JSON_PROPERTY_LABELS = "labels";
  @jakarta.annotation.Nullable
  private List<Link> labels = new ArrayList<>();

  public static final String JSON_PROPERTY_PARENT = "parent";
  @jakarta.annotation.Nullable
  private Link parent;

  public WorkPackageWriteModelLinks() {
  }

  public WorkPackageWriteModelLinks category(@jakarta.annotation.Nullable Link category) {
    
    this.category = category;
    return this;
  }

  /**
   * The category of the work package  **Resource**: Category
   * @return category
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CATEGORY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getCategory() {
    return category;
  }


  @JsonProperty(value = JSON_PROPERTY_CATEGORY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCategory(@jakarta.annotation.Nullable Link category) {
    this.category = category;
  }

  public WorkPackageWriteModelLinks type(@jakarta.annotation.Nullable Link type) {
    
    this.type = type;
    return this;
  }

  /**
   * The type of the work package  **Resource**: Type
   * @return type
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getType() {
    return type;
  }


  @JsonProperty(value = JSON_PROPERTY_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(@jakarta.annotation.Nullable Link type) {
    this.type = type;
  }

  public WorkPackageWriteModelLinks priority(@jakarta.annotation.Nullable Link priority) {
    
    this.priority = priority;
    return this;
  }

  /**
   * The priority of the work package  **Resource**: Priority
   * @return priority
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PRIORITY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getPriority() {
    return priority;
  }


  @JsonProperty(value = JSON_PROPERTY_PRIORITY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPriority(@jakarta.annotation.Nullable Link priority) {
    this.priority = priority;
  }

  public WorkPackageWriteModelLinks project(@jakarta.annotation.Nullable Link project) {
    
    this.project = project;
    return this;
  }

  /**
   * The project to which the work package belongs  **Resource**: Project
   * @return project
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getProject() {
    return project;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProject(@jakarta.annotation.Nullable Link project) {
    this.project = project;
  }

  public WorkPackageWriteModelLinks status(@jakarta.annotation.Nullable Link status) {
    
    this.status = status;
    return this;
  }

  /**
   * The current status of the work package  **Resource**: Status
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

  public WorkPackageWriteModelLinks responsible(@jakarta.annotation.Nullable Link responsible) {
    
    this.responsible = responsible;
    return this;
  }

  /**
   * The person that is responsible for the overall outcome  **Resource**: User
   * @return responsible
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_RESPONSIBLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getResponsible() {
    return responsible;
  }


  @JsonProperty(value = JSON_PROPERTY_RESPONSIBLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setResponsible(@jakarta.annotation.Nullable Link responsible) {
    this.responsible = responsible;
  }

  public WorkPackageWriteModelLinks assignee(@jakarta.annotation.Nullable Link assignee) {
    
    this.assignee = assignee;
    return this;
  }

  /**
   * The person that is intended to work on the work package  **Resource**: User
   * @return assignee
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ASSIGNEE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAssignee() {
    return assignee;
  }


  @JsonProperty(value = JSON_PROPERTY_ASSIGNEE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAssignee(@jakarta.annotation.Nullable Link assignee) {
    this.assignee = assignee;
  }

  public WorkPackageWriteModelLinks version(@jakarta.annotation.Nullable Link version) {
    
    this.version = version;
    return this;
  }

  /**
   * The version associated to the work package  **Resource**: Version
   * @return version
   * @deprecated
   */
  @Deprecated
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getVersion() {
    return version;
  }


  @JsonProperty(value = JSON_PROPERTY_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(@jakarta.annotation.Nullable Link version) {
    this.version = version;
  }

  public WorkPackageWriteModelLinks targetVersions(@jakarta.annotation.Nullable List<Link> targetVersions) {
    
    this.targetVersions = targetVersions;
    return this;
  }

  public WorkPackageWriteModelLinks addTargetVersionsItem(Link targetVersionsItem) {
    if (this.targetVersions == null) {
      this.targetVersions = new ArrayList<>();
    }
    this.targetVersions.add(targetVersionsItem);
    return this;
  }

  /**
   * List of versions associated to the work package  **Resource**: Collection of Version  # Conditions  - Transitioning from single to multiple version support - (Temporary) Only allows a single value for compatibility with the version field
   * @return targetVersions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TARGET_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Link> getTargetVersions() {
    return targetVersions;
  }


  @JsonProperty(value = JSON_PROPERTY_TARGET_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTargetVersions(@jakarta.annotation.Nullable List<Link> targetVersions) {
    this.targetVersions = targetVersions;
  }

  public WorkPackageWriteModelLinks observedInVersions(@jakarta.annotation.Nullable List<Link> observedInVersions) {
    
    this.observedInVersions = observedInVersions;
    return this;
  }

  public WorkPackageWriteModelLinks addObservedInVersionsItem(Link observedInVersionsItem) {
    if (this.observedInVersions == null) {
      this.observedInVersions = new ArrayList<>();
    }
    this.observedInVersions.add(observedInVersionsItem);
    return this;
  }

  /**
   * List of versions in which the behavior described by the work package was observed  **Resource**: Collection of Version
   * @return observedInVersions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_OBSERVED_IN_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Link> getObservedInVersions() {
    return observedInVersions;
  }


  @JsonProperty(value = JSON_PROPERTY_OBSERVED_IN_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setObservedInVersions(@jakarta.annotation.Nullable List<Link> observedInVersions) {
    this.observedInVersions = observedInVersions;
  }

  public WorkPackageWriteModelLinks labels(@jakarta.annotation.Nullable List<Link> labels) {
    
    this.labels = labels;
    return this;
  }

  public WorkPackageWriteModelLinks addLabelsItem(Link labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * List of labels attached to the work package  An entry whose &#x60;href&#x60; is &#x60;null&#x60; and whose &#x60;title&#x60; is a non-blank name creates that label when the work package is saved, or reuses an existing label with the same name (case-insensitive). The form endpoint echoes such entries back without creating anything. Creating a label requires the user to be allowed to edit work packages in at least one project, as for &#x60;POST /api/v3/labels&#x60;. Names are limited to 255 characters, and a single request may contain at most 50 entries without an &#x60;href&#x60;. A &#x60;title&#x60; is ignored on entries that have an &#x60;href&#x60;.  **Resource**: Collection of Label
   * @return labels
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LABELS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Link> getLabels() {
    return labels;
  }


  @JsonProperty(value = JSON_PROPERTY_LABELS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLabels(@jakarta.annotation.Nullable List<Link> labels) {
    this.labels = labels;
  }

  public WorkPackageWriteModelLinks parent(@jakarta.annotation.Nullable Link parent) {
    
    this.parent = parent;
    return this;
  }

  /**
   * Parent work package  **Resource**: WorkPackage
   * @return parent
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PARENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getParent() {
    return parent;
  }


  @JsonProperty(value = JSON_PROPERTY_PARENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setParent(@jakarta.annotation.Nullable Link parent) {
    this.parent = parent;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkPackageWriteModelLinks workPackageWriteModelLinks = (WorkPackageWriteModelLinks) o;
    return Objects.equals(this.category, workPackageWriteModelLinks.category) &&
        Objects.equals(this.type, workPackageWriteModelLinks.type) &&
        Objects.equals(this.priority, workPackageWriteModelLinks.priority) &&
        Objects.equals(this.project, workPackageWriteModelLinks.project) &&
        Objects.equals(this.status, workPackageWriteModelLinks.status) &&
        Objects.equals(this.responsible, workPackageWriteModelLinks.responsible) &&
        Objects.equals(this.assignee, workPackageWriteModelLinks.assignee) &&
        Objects.equals(this.version, workPackageWriteModelLinks.version) &&
        Objects.equals(this.targetVersions, workPackageWriteModelLinks.targetVersions) &&
        Objects.equals(this.observedInVersions, workPackageWriteModelLinks.observedInVersions) &&
        Objects.equals(this.labels, workPackageWriteModelLinks.labels) &&
        Objects.equals(this.parent, workPackageWriteModelLinks.parent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, type, priority, project, status, responsible, assignee, version, targetVersions, observedInVersions, labels, parent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkPackageWriteModelLinks {\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    responsible: ").append(toIndentedString(responsible)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    targetVersions: ").append(toIndentedString(targetVersions)).append("\n");
    sb.append("    observedInVersions: ").append(toIndentedString(observedInVersions)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
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

