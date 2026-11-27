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
import ai.gebo.openproject.client.model.WorkPackageModelAllOfLinksAllocatedPrincipals;
import ai.gebo.openproject.client.model.WorkPackageModelAllOfLinksAncestors;
import ai.gebo.openproject.client.model.WorkPackageModelAllOfLinksChildren;
import ai.gebo.openproject.client.model.WorkPackageModelAllOfLinksCustomActions;
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
 * WorkPackageModelAllOfLinks
 */
@JsonPropertyOrder({
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ADD_COMMENT,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ADD_RELATION,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ADD_WATCHER,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ALLOCATE_RESOURCE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_CUSTOM_ACTIONS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PREVIEW_MARKUP,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_REMOVE_WATCHER,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_DELETE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_LOG_TIME,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_MOVE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_COPY,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_UNWATCH,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_UPDATE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_UPDATE_IMMEDIATELY,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_WATCH,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_SELF,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_SCHEMA,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ALLOCATED_PRINCIPALS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ANCESTORS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ATTACHMENTS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ADD_ATTACHMENT,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PREPARE_ATTACHMENT,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_AUTHOR,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ASSIGNEE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_AVAILABLE_WATCHERS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_BUDGET,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_CATEGORY,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_CHILDREN,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_ADD_FILE_LINK,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_FILE_LINKS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_REPOSITORY_ISSUES,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_REPOSITORY_MERGE_REQUESTS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_REPOSITORY_BRANCHES,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_REPOSITORY_COMMITS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_LABELS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PARENT,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PRIORITY,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PROJECT,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PROJECT_PHASE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_PROJECT_PHASE_DEFINITION,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_RESPONSIBLE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_RELATIONS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_REVISIONS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_STATUS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_SPRINT,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_SHOW_RESOURCE_ALLOCATIONS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_TIME_ENTRIES,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_TYPE,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_VERSION,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_TARGET_VERSIONS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_OBSERVED_IN_VERSIONS,
  WorkPackageModelAllOfLinks.JSON_PROPERTY_WATCHERS
})
@JsonTypeName("WorkPackageModel_allOf__links")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class WorkPackageModelAllOfLinks {
  public static final String JSON_PROPERTY_ADD_COMMENT = "addComment";
  @jakarta.annotation.Nullable
  private Link addComment;

  public static final String JSON_PROPERTY_ADD_RELATION = "addRelation";
  @jakarta.annotation.Nullable
  private Link addRelation;

  public static final String JSON_PROPERTY_ADD_WATCHER = "addWatcher";
  @jakarta.annotation.Nullable
  private Link addWatcher;

  public static final String JSON_PROPERTY_ALLOCATE_RESOURCE = "allocateResource";
  @jakarta.annotation.Nullable
  private Link allocateResource;

  public static final String JSON_PROPERTY_CUSTOM_ACTIONS = "customActions";
  @jakarta.annotation.Nullable
  private List<WorkPackageModelAllOfLinksCustomActions> customActions = new ArrayList<>();

  public static final String JSON_PROPERTY_PREVIEW_MARKUP = "previewMarkup";
  @jakarta.annotation.Nullable
  private Link previewMarkup;

  public static final String JSON_PROPERTY_REMOVE_WATCHER = "removeWatcher";
  @jakarta.annotation.Nullable
  private Link removeWatcher;

  public static final String JSON_PROPERTY_DELETE = "delete";
  @jakarta.annotation.Nullable
  private Link delete;

  public static final String JSON_PROPERTY_LOG_TIME = "logTime";
  @jakarta.annotation.Nullable
  private Link logTime;

  public static final String JSON_PROPERTY_MOVE = "move";
  @jakarta.annotation.Nullable
  private Link move;

  public static final String JSON_PROPERTY_COPY = "copy";
  @jakarta.annotation.Nullable
  private Link copy;

  public static final String JSON_PROPERTY_UNWATCH = "unwatch";
  @jakarta.annotation.Nullable
  private Link unwatch;

  public static final String JSON_PROPERTY_UPDATE = "update";
  @jakarta.annotation.Nullable
  private Link update;

  public static final String JSON_PROPERTY_UPDATE_IMMEDIATELY = "updateImmediately";
  @jakarta.annotation.Nullable
  private Link updateImmediately;

  public static final String JSON_PROPERTY_WATCH = "watch";
  @jakarta.annotation.Nullable
  private Link watch;

  public static final String JSON_PROPERTY_SELF = "self";
  @jakarta.annotation.Nonnull
  private Link self;

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  @jakarta.annotation.Nonnull
  private Link schema;

  public static final String JSON_PROPERTY_ALLOCATED_PRINCIPALS = "allocatedPrincipals";
  @jakarta.annotation.Nullable
  private List<WorkPackageModelAllOfLinksAllocatedPrincipals> allocatedPrincipals = new ArrayList<>();

  public static final String JSON_PROPERTY_ANCESTORS = "ancestors";
  @jakarta.annotation.Nonnull
  private List<WorkPackageModelAllOfLinksAncestors> ancestors = new ArrayList<>();

  public static final String JSON_PROPERTY_ATTACHMENTS = "attachments";
  @jakarta.annotation.Nullable
  private Link attachments;

  public static final String JSON_PROPERTY_ADD_ATTACHMENT = "addAttachment";
  @jakarta.annotation.Nullable
  private Link addAttachment;

  public static final String JSON_PROPERTY_PREPARE_ATTACHMENT = "prepareAttachment";
  @jakarta.annotation.Nullable
  private Link prepareAttachment;

  public static final String JSON_PROPERTY_AUTHOR = "author";
  @jakarta.annotation.Nonnull
  private Link author;

  public static final String JSON_PROPERTY_ASSIGNEE = "assignee";
  @jakarta.annotation.Nullable
  private Link assignee;

  public static final String JSON_PROPERTY_AVAILABLE_WATCHERS = "availableWatchers";
  @jakarta.annotation.Nullable
  private Link availableWatchers;

  public static final String JSON_PROPERTY_BUDGET = "budget";
  @jakarta.annotation.Nullable
  private Link budget;

  public static final String JSON_PROPERTY_CATEGORY = "category";
  @jakarta.annotation.Nullable
  private Link category;

  public static final String JSON_PROPERTY_CHILDREN = "children";
  @jakarta.annotation.Nullable
  private List<WorkPackageModelAllOfLinksChildren> children = new ArrayList<>();

  public static final String JSON_PROPERTY_ADD_FILE_LINK = "addFileLink";
  @jakarta.annotation.Nullable
  private Link addFileLink;

  public static final String JSON_PROPERTY_FILE_LINKS = "fileLinks";
  @jakarta.annotation.Nullable
  private Link fileLinks;

  public static final String JSON_PROPERTY_REPOSITORY_ISSUES = "repositoryIssues";
  @jakarta.annotation.Nullable
  private Link repositoryIssues;

  public static final String JSON_PROPERTY_REPOSITORY_MERGE_REQUESTS = "repositoryMergeRequests";
  @jakarta.annotation.Nullable
  private Link repositoryMergeRequests;

  public static final String JSON_PROPERTY_REPOSITORY_BRANCHES = "repositoryBranches";
  @jakarta.annotation.Nullable
  private Link repositoryBranches;

  public static final String JSON_PROPERTY_REPOSITORY_COMMITS = "repositoryCommits";
  @jakarta.annotation.Nullable
  private Link repositoryCommits;

  public static final String JSON_PROPERTY_LABELS = "labels";
  @jakarta.annotation.Nullable
  private List<Link> labels = new ArrayList<>();

  public static final String JSON_PROPERTY_PARENT = "parent";
  @jakarta.annotation.Nullable
  private Link parent;

  public static final String JSON_PROPERTY_PRIORITY = "priority";
  @jakarta.annotation.Nonnull
  private Link priority;

  public static final String JSON_PROPERTY_PROJECT = "project";
  @jakarta.annotation.Nonnull
  private Link project;

  public static final String JSON_PROPERTY_PROJECT_PHASE = "projectPhase";
  @jakarta.annotation.Nullable
  private Link projectPhase;

  public static final String JSON_PROPERTY_PROJECT_PHASE_DEFINITION = "projectPhaseDefinition";
  @jakarta.annotation.Nullable
  private Link projectPhaseDefinition;

  public static final String JSON_PROPERTY_RESPONSIBLE = "responsible";
  @jakarta.annotation.Nullable
  private Link responsible;

  public static final String JSON_PROPERTY_RELATIONS = "relations";
  @jakarta.annotation.Nullable
  private Link relations;

  public static final String JSON_PROPERTY_REVISIONS = "revisions";
  @jakarta.annotation.Nullable
  private Link revisions;

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nonnull
  private Link status;

  public static final String JSON_PROPERTY_SPRINT = "sprint";
  @jakarta.annotation.Nullable
  private Link sprint;

  public static final String JSON_PROPERTY_SHOW_RESOURCE_ALLOCATIONS = "showResourceAllocations";
  @jakarta.annotation.Nullable
  private Link showResourceAllocations;

  public static final String JSON_PROPERTY_TIME_ENTRIES = "timeEntries";
  @jakarta.annotation.Nullable
  private Link timeEntries;

  public static final String JSON_PROPERTY_TYPE = "type";
  @jakarta.annotation.Nonnull
  private Link type;

  public static final String JSON_PROPERTY_VERSION = "version";
  @jakarta.annotation.Nullable
  private Link version;

  public static final String JSON_PROPERTY_TARGET_VERSIONS = "targetVersions";
  @jakarta.annotation.Nullable
  private List<Link> targetVersions = new ArrayList<>();

  public static final String JSON_PROPERTY_OBSERVED_IN_VERSIONS = "observedInVersions";
  @jakarta.annotation.Nullable
  private List<Link> observedInVersions = new ArrayList<>();

  public static final String JSON_PROPERTY_WATCHERS = "watchers";
  @jakarta.annotation.Nullable
  private Link watchers;

  public WorkPackageModelAllOfLinks() {
  }

  public WorkPackageModelAllOfLinks addComment(@jakarta.annotation.Nullable Link addComment) {
    
    this.addComment = addComment;
    return this;
  }

  /**
   * Post comment to WP  # Conditions  **Permission**: add work package notes
   * @return addComment
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ADD_COMMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAddComment() {
    return addComment;
  }


  @JsonProperty(value = JSON_PROPERTY_ADD_COMMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAddComment(@jakarta.annotation.Nullable Link addComment) {
    this.addComment = addComment;
  }

  public WorkPackageModelAllOfLinks addRelation(@jakarta.annotation.Nullable Link addRelation) {
    
    this.addRelation = addRelation;
    return this;
  }

  /**
   * Adds a relation to this work package.  # Conditions  **Permission**: manage wp relations
   * @return addRelation
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ADD_RELATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAddRelation() {
    return addRelation;
  }


  @JsonProperty(value = JSON_PROPERTY_ADD_RELATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAddRelation(@jakarta.annotation.Nullable Link addRelation) {
    this.addRelation = addRelation;
  }

  public WorkPackageModelAllOfLinks addWatcher(@jakarta.annotation.Nullable Link addWatcher) {
    
    this.addWatcher = addWatcher;
    return this;
  }

  /**
   * Add any user to WP watchers  # Conditions  **Permission**: add watcher
   * @return addWatcher
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ADD_WATCHER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAddWatcher() {
    return addWatcher;
  }


  @JsonProperty(value = JSON_PROPERTY_ADD_WATCHER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAddWatcher(@jakarta.annotation.Nullable Link addWatcher) {
    this.addWatcher = addWatcher;
  }

  public WorkPackageModelAllOfLinks allocateResource(@jakarta.annotation.Nullable Link allocateResource) {
    
    this.allocateResource = allocateResource;
    return this;
  }

  /**
   * A request returning the dialog to add a resource allocation to this work package. It is used by the frontend and not intended for API users.  # Conditions  **Permission**: allocate user resources, requires the resource management module and an enterprise token
   * @return allocateResource
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ALLOCATE_RESOURCE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAllocateResource() {
    return allocateResource;
  }


  @JsonProperty(value = JSON_PROPERTY_ALLOCATE_RESOURCE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAllocateResource(@jakarta.annotation.Nullable Link allocateResource) {
    this.allocateResource = allocateResource;
  }

  public WorkPackageModelAllOfLinks customActions(@jakarta.annotation.Nullable List<WorkPackageModelAllOfLinksCustomActions> customActions) {
    
    this.customActions = customActions;
    return this;
  }

  public WorkPackageModelAllOfLinks addCustomActionsItem(WorkPackageModelAllOfLinksCustomActions customActionsItem) {
    if (this.customActions == null) {
      this.customActions = new ArrayList<>();
    }
    this.customActions.add(customActionsItem);
    return this;
  }

  /**
   * Get customActions
   * @return customActions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CUSTOM_ACTIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<WorkPackageModelAllOfLinksCustomActions> getCustomActions() {
    return customActions;
  }


  @JsonProperty(value = JSON_PROPERTY_CUSTOM_ACTIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCustomActions(@jakarta.annotation.Nullable List<WorkPackageModelAllOfLinksCustomActions> customActions) {
    this.customActions = customActions;
  }

  public WorkPackageModelAllOfLinks previewMarkup(@jakarta.annotation.Nullable Link previewMarkup) {
    
    this.previewMarkup = previewMarkup;
    return this;
  }

  /**
   * Post markup (in markdown) here to receive an HTML-rendered response
   * @return previewMarkup
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PREVIEW_MARKUP, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getPreviewMarkup() {
    return previewMarkup;
  }


  @JsonProperty(value = JSON_PROPERTY_PREVIEW_MARKUP, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPreviewMarkup(@jakarta.annotation.Nullable Link previewMarkup) {
    this.previewMarkup = previewMarkup;
  }

  public WorkPackageModelAllOfLinks removeWatcher(@jakarta.annotation.Nullable Link removeWatcher) {
    
    this.removeWatcher = removeWatcher;
    return this;
  }

  /**
   * Remove any user from WP watchers  # Conditions  **Permission**: delete watcher
   * @return removeWatcher
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REMOVE_WATCHER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRemoveWatcher() {
    return removeWatcher;
  }


  @JsonProperty(value = JSON_PROPERTY_REMOVE_WATCHER, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRemoveWatcher(@jakarta.annotation.Nullable Link removeWatcher) {
    this.removeWatcher = removeWatcher;
  }

  public WorkPackageModelAllOfLinks delete(@jakarta.annotation.Nullable Link delete) {
    
    this.delete = delete;
    return this;
  }

  /**
   * Delete this work package  # Conditions  **Permission**: delete_work_packages
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

  public WorkPackageModelAllOfLinks logTime(@jakarta.annotation.Nullable Link logTime) {
    
    this.logTime = logTime;
    return this;
  }

  /**
   * Create time entries on the work package  # Conditions  **Permission**: log_time or log_own_time
   * @return logTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LOG_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getLogTime() {
    return logTime;
  }


  @JsonProperty(value = JSON_PROPERTY_LOG_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLogTime(@jakarta.annotation.Nullable Link logTime) {
    this.logTime = logTime;
  }

  public WorkPackageModelAllOfLinks move(@jakarta.annotation.Nullable Link move) {
    
    this.move = move;
    return this;
  }

  /**
   * Link to page for moving this work package  # Conditions  **Permission**: move_work_packages
   * @return move
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MOVE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getMove() {
    return move;
  }


  @JsonProperty(value = JSON_PROPERTY_MOVE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMove(@jakarta.annotation.Nullable Link move) {
    this.move = move;
  }

  public WorkPackageModelAllOfLinks copy(@jakarta.annotation.Nullable Link copy) {
    
    this.copy = copy;
    return this;
  }

  /**
   * Link to page for copying this work package  # Conditions  **Permission**: add_work_packages
   * @return copy
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_COPY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getCopy() {
    return copy;
  }


  @JsonProperty(value = JSON_PROPERTY_COPY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCopy(@jakarta.annotation.Nullable Link copy) {
    this.copy = copy;
  }

  public WorkPackageModelAllOfLinks unwatch(@jakarta.annotation.Nullable Link unwatch) {
    
    this.unwatch = unwatch;
    return this;
  }

  /**
   * Remove current user from WP watchers  # Conditions  logged in; watching
   * @return unwatch
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UNWATCH, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getUnwatch() {
    return unwatch;
  }


  @JsonProperty(value = JSON_PROPERTY_UNWATCH, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUnwatch(@jakarta.annotation.Nullable Link unwatch) {
    this.unwatch = unwatch;
  }

  public WorkPackageModelAllOfLinks update(@jakarta.annotation.Nullable Link update) {
    
    this.update = update;
    return this;
  }

  /**
   * Form endpoint that aids in preparing and performing edits on a work package  # Conditions  **Permission**: edit work package
   * @return update
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getUpdate() {
    return update;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdate(@jakarta.annotation.Nullable Link update) {
    this.update = update;
  }

  public WorkPackageModelAllOfLinks updateImmediately(@jakarta.annotation.Nullable Link updateImmediately) {
    
    this.updateImmediately = updateImmediately;
    return this;
  }

  /**
   * Directly perform edits on a work package  # Conditions  **Permission**: edit work package
   * @return updateImmediately
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATE_IMMEDIATELY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getUpdateImmediately() {
    return updateImmediately;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATE_IMMEDIATELY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdateImmediately(@jakarta.annotation.Nullable Link updateImmediately) {
    this.updateImmediately = updateImmediately;
  }

  public WorkPackageModelAllOfLinks watch(@jakarta.annotation.Nullable Link watch) {
    
    this.watch = watch;
    return this;
  }

  /**
   * Add current user to WP watchers  # Conditions  logged in; not watching
   * @return watch
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_WATCH, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getWatch() {
    return watch;
  }


  @JsonProperty(value = JSON_PROPERTY_WATCH, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWatch(@jakarta.annotation.Nullable Link watch) {
    this.watch = watch;
  }

  public WorkPackageModelAllOfLinks self(@jakarta.annotation.Nonnull Link self) {
    
    this.self = self;
    return this;
  }

  /**
   * This work package  **Resource**: WorkPackage
   * @return self
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_SELF, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getSelf() {
    return self;
  }


  @JsonProperty(value = JSON_PROPERTY_SELF, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSelf(@jakarta.annotation.Nonnull Link self) {
    this.self = self;
  }

  public WorkPackageModelAllOfLinks schema(@jakarta.annotation.Nonnull Link schema) {
    
    this.schema = schema;
    return this;
  }

  /**
   * The schema of this work package  **Resource**: Schema
   * @return schema
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_SCHEMA, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getSchema() {
    return schema;
  }


  @JsonProperty(value = JSON_PROPERTY_SCHEMA, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSchema(@jakarta.annotation.Nonnull Link schema) {
    this.schema = schema;
  }

  public WorkPackageModelAllOfLinks allocatedPrincipals(@jakarta.annotation.Nullable List<WorkPackageModelAllOfLinksAllocatedPrincipals> allocatedPrincipals) {
    
    this.allocatedPrincipals = allocatedPrincipals;
    return this;
  }

  public WorkPackageModelAllOfLinks addAllocatedPrincipalsItem(WorkPackageModelAllOfLinksAllocatedPrincipals allocatedPrincipalsItem) {
    if (this.allocatedPrincipals == null) {
      this.allocatedPrincipals = new ArrayList<>();
    }
    this.allocatedPrincipals.add(allocatedPrincipalsItem);
    return this;
  }

  /**
   * Get allocatedPrincipals
   * @return allocatedPrincipals
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_PRINCIPALS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<WorkPackageModelAllOfLinksAllocatedPrincipals> getAllocatedPrincipals() {
    return allocatedPrincipals;
  }


  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_PRINCIPALS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAllocatedPrincipals(@jakarta.annotation.Nullable List<WorkPackageModelAllOfLinksAllocatedPrincipals> allocatedPrincipals) {
    this.allocatedPrincipals = allocatedPrincipals;
  }

  public WorkPackageModelAllOfLinks ancestors(@jakarta.annotation.Nonnull List<WorkPackageModelAllOfLinksAncestors> ancestors) {
    
    this.ancestors = ancestors;
    return this;
  }

  public WorkPackageModelAllOfLinks addAncestorsItem(WorkPackageModelAllOfLinksAncestors ancestorsItem) {
    if (this.ancestors == null) {
      this.ancestors = new ArrayList<>();
    }
    this.ancestors.add(ancestorsItem);
    return this;
  }

  /**
   * Get ancestors
   * @return ancestors
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_ANCESTORS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<WorkPackageModelAllOfLinksAncestors> getAncestors() {
    return ancestors;
  }


  @JsonProperty(value = JSON_PROPERTY_ANCESTORS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAncestors(@jakarta.annotation.Nonnull List<WorkPackageModelAllOfLinksAncestors> ancestors) {
    this.ancestors = ancestors;
  }

  public WorkPackageModelAllOfLinks attachments(@jakarta.annotation.Nullable Link attachments) {
    
    this.attachments = attachments;
    return this;
  }

  /**
   * The files attached to this work package  **Resource**: Collection  # Conditions  - **Setting**: deactivate_work_package_attachments set to false in related workspace
   * @return attachments
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ATTACHMENTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAttachments() {
    return attachments;
  }


  @JsonProperty(value = JSON_PROPERTY_ATTACHMENTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAttachments(@jakarta.annotation.Nullable Link attachments) {
    this.attachments = attachments;
  }

  public WorkPackageModelAllOfLinks addAttachment(@jakarta.annotation.Nullable Link addAttachment) {
    
    this.addAttachment = addAttachment;
    return this;
  }

  /**
   * Attach a file to the work package  # Conditions  - **Permission**: edit work package
   * @return addAttachment
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ADD_ATTACHMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAddAttachment() {
    return addAttachment;
  }


  @JsonProperty(value = JSON_PROPERTY_ADD_ATTACHMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAddAttachment(@jakarta.annotation.Nullable Link addAttachment) {
    this.addAttachment = addAttachment;
  }

  public WorkPackageModelAllOfLinks prepareAttachment(@jakarta.annotation.Nullable Link prepareAttachment) {
    
    this.prepareAttachment = prepareAttachment;
    return this;
  }

  /**
   * Attach a file to the work package  # Conditions  - **Setting**: direct uploads enabled
   * @return prepareAttachment
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PREPARE_ATTACHMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getPrepareAttachment() {
    return prepareAttachment;
  }


  @JsonProperty(value = JSON_PROPERTY_PREPARE_ATTACHMENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPrepareAttachment(@jakarta.annotation.Nullable Link prepareAttachment) {
    this.prepareAttachment = prepareAttachment;
  }

  public WorkPackageModelAllOfLinks author(@jakarta.annotation.Nonnull Link author) {
    
    this.author = author;
    return this;
  }

  /**
   * The person that created the work package  **Resource**: User
   * @return author
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_AUTHOR, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getAuthor() {
    return author;
  }


  @JsonProperty(value = JSON_PROPERTY_AUTHOR, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAuthor(@jakarta.annotation.Nonnull Link author) {
    this.author = author;
  }

  public WorkPackageModelAllOfLinks assignee(@jakarta.annotation.Nullable Link assignee) {
    
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

  public WorkPackageModelAllOfLinks availableWatchers(@jakarta.annotation.Nullable Link availableWatchers) {
    
    this.availableWatchers = availableWatchers;
    return this;
  }

  /**
   * All users that can be added to the work package as watchers.  **Resource**: User  # Conditions  **Permission** add work package watchers
   * @return availableWatchers
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_AVAILABLE_WATCHERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAvailableWatchers() {
    return availableWatchers;
  }


  @JsonProperty(value = JSON_PROPERTY_AVAILABLE_WATCHERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAvailableWatchers(@jakarta.annotation.Nullable Link availableWatchers) {
    this.availableWatchers = availableWatchers;
  }

  public WorkPackageModelAllOfLinks budget(@jakarta.annotation.Nullable Link budget) {
    
    this.budget = budget;
    return this;
  }

  /**
   * The budget this work package is associated to  **Resource**: Budget  # Conditions  **Permission** view cost objects
   * @return budget
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_BUDGET, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getBudget() {
    return budget;
  }


  @JsonProperty(value = JSON_PROPERTY_BUDGET, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBudget(@jakarta.annotation.Nullable Link budget) {
    this.budget = budget;
  }

  public WorkPackageModelAllOfLinks category(@jakarta.annotation.Nullable Link category) {
    
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

  public WorkPackageModelAllOfLinks children(@jakarta.annotation.Nullable List<WorkPackageModelAllOfLinksChildren> children) {
    
    this.children = children;
    return this;
  }

  public WorkPackageModelAllOfLinks addChildrenItem(WorkPackageModelAllOfLinksChildren childrenItem) {
    if (this.children == null) {
      this.children = new ArrayList<>();
    }
    this.children.add(childrenItem);
    return this;
  }

  /**
   * Get children
   * @return children
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CHILDREN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<WorkPackageModelAllOfLinksChildren> getChildren() {
    return children;
  }


  @JsonProperty(value = JSON_PROPERTY_CHILDREN, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChildren(@jakarta.annotation.Nullable List<WorkPackageModelAllOfLinksChildren> children) {
    this.children = children;
  }

  public WorkPackageModelAllOfLinks addFileLink(@jakarta.annotation.Nullable Link addFileLink) {
    
    this.addFileLink = addFileLink;
    return this;
  }

  /**
   * Add a file link to the work package  # Conditions  **Permission**: manage_file_links
   * @return addFileLink
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ADD_FILE_LINK, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getAddFileLink() {
    return addFileLink;
  }


  @JsonProperty(value = JSON_PROPERTY_ADD_FILE_LINK, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAddFileLink(@jakarta.annotation.Nullable Link addFileLink) {
    this.addFileLink = addFileLink;
  }

  public WorkPackageModelAllOfLinks fileLinks(@jakarta.annotation.Nullable Link fileLinks) {
    
    this.fileLinks = fileLinks;
    return this;
  }

  /**
   * Gets the file link collection of this work package  # Conditions  **Permission**: view_file_links
   * @return fileLinks
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_FILE_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getFileLinks() {
    return fileLinks;
  }


  @JsonProperty(value = JSON_PROPERTY_FILE_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFileLinks(@jakarta.annotation.Nullable Link fileLinks) {
    this.fileLinks = fileLinks;
  }

  public WorkPackageModelAllOfLinks repositoryIssues(@jakarta.annotation.Nullable Link repositoryIssues) {
    
    this.repositoryIssues = repositoryIssues;
    return this;
  }

  /**
   * Gets the collection of repository issues linked to this work package  # Conditions  **Permission**: show_repository_content
   * @return repositoryIssues
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_ISSUES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRepositoryIssues() {
    return repositoryIssues;
  }


  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_ISSUES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRepositoryIssues(@jakarta.annotation.Nullable Link repositoryIssues) {
    this.repositoryIssues = repositoryIssues;
  }

  public WorkPackageModelAllOfLinks repositoryMergeRequests(@jakarta.annotation.Nullable Link repositoryMergeRequests) {
    
    this.repositoryMergeRequests = repositoryMergeRequests;
    return this;
  }

  /**
   * Gets the collection of repository merge requests linked to this work package  # Conditions  **Permission**: show_repository_content
   * @return repositoryMergeRequests
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_MERGE_REQUESTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRepositoryMergeRequests() {
    return repositoryMergeRequests;
  }


  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_MERGE_REQUESTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRepositoryMergeRequests(@jakarta.annotation.Nullable Link repositoryMergeRequests) {
    this.repositoryMergeRequests = repositoryMergeRequests;
  }

  public WorkPackageModelAllOfLinks repositoryBranches(@jakarta.annotation.Nullable Link repositoryBranches) {
    
    this.repositoryBranches = repositoryBranches;
    return this;
  }

  /**
   * Gets the collection of repository branches linked to this work package  # Conditions  **Permission**: show_repository_content
   * @return repositoryBranches
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_BRANCHES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRepositoryBranches() {
    return repositoryBranches;
  }


  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_BRANCHES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRepositoryBranches(@jakarta.annotation.Nullable Link repositoryBranches) {
    this.repositoryBranches = repositoryBranches;
  }

  public WorkPackageModelAllOfLinks repositoryCommits(@jakarta.annotation.Nullable Link repositoryCommits) {
    
    this.repositoryCommits = repositoryCommits;
    return this;
  }

  /**
   * Gets the collection of repository commits linked to this work package  # Conditions  **Permission**: show_repository_content
   * @return repositoryCommits
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_COMMITS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRepositoryCommits() {
    return repositoryCommits;
  }


  @JsonProperty(value = JSON_PROPERTY_REPOSITORY_COMMITS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRepositoryCommits(@jakarta.annotation.Nullable Link repositoryCommits) {
    this.repositoryCommits = repositoryCommits;
  }

  public WorkPackageModelAllOfLinks labels(@jakarta.annotation.Nullable List<Link> labels) {
    
    this.labels = labels;
    return this;
  }

  public WorkPackageModelAllOfLinks addLabelsItem(Link labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * List of labels attached to the work package  **Resource**: Collection of Label
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

  public WorkPackageModelAllOfLinks parent(@jakarta.annotation.Nullable Link parent) {
    
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

  public WorkPackageModelAllOfLinks priority(@jakarta.annotation.Nonnull Link priority) {
    
    this.priority = priority;
    return this;
  }

  /**
   * The priority of the work package  **Resource**: Priority
   * @return priority
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_PRIORITY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getPriority() {
    return priority;
  }


  @JsonProperty(value = JSON_PROPERTY_PRIORITY, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPriority(@jakarta.annotation.Nonnull Link priority) {
    this.priority = priority;
  }

  public WorkPackageModelAllOfLinks project(@jakarta.annotation.Nonnull Link project) {
    
    this.project = project;
    return this;
  }

  /**
   * The workspace to which the work package belongs  **Resource**: Workspace
   * @return project
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getProject() {
    return project;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setProject(@jakarta.annotation.Nonnull Link project) {
    this.project = project;
  }

  public WorkPackageModelAllOfLinks projectPhase(@jakarta.annotation.Nullable Link projectPhase) {
    
    this.projectPhase = projectPhase;
    return this;
  }

  /**
   * The project phase to which the work package belongs  **Resource**: ProjectPhase
   * @return projectPhase
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getProjectPhase() {
    return projectPhase;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProjectPhase(@jakarta.annotation.Nullable Link projectPhase) {
    this.projectPhase = projectPhase;
  }

  public WorkPackageModelAllOfLinks projectPhaseDefinition(@jakarta.annotation.Nullable Link projectPhaseDefinition) {
    
    this.projectPhaseDefinition = projectPhaseDefinition;
    return this;
  }

  /**
   * The definition of the project phase the work package belongs to  **Resource**: ProjectPhaseDefinition
   * @return projectPhaseDefinition
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE_DEFINITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getProjectPhaseDefinition() {
    return projectPhaseDefinition;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE_DEFINITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProjectPhaseDefinition(@jakarta.annotation.Nullable Link projectPhaseDefinition) {
    this.projectPhaseDefinition = projectPhaseDefinition;
  }

  public WorkPackageModelAllOfLinks responsible(@jakarta.annotation.Nullable Link responsible) {
    
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

  public WorkPackageModelAllOfLinks relations(@jakarta.annotation.Nullable Link relations) {
    
    this.relations = relations;
    return this;
  }

  /**
   * Relations this work package is involved in  **Resource**: Relation  # Conditions  **Permission** view work packages
   * @return relations
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_RELATIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRelations() {
    return relations;
  }


  @JsonProperty(value = JSON_PROPERTY_RELATIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRelations(@jakarta.annotation.Nullable Link relations) {
    this.relations = relations;
  }

  public WorkPackageModelAllOfLinks revisions(@jakarta.annotation.Nullable Link revisions) {
    
    this.revisions = revisions;
    return this;
  }

  /**
   * Revisions that are referencing the work package  **Resource**: Revision  # Conditions  **Permission** view changesets
   * @return revisions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REVISIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getRevisions() {
    return revisions;
  }


  @JsonProperty(value = JSON_PROPERTY_REVISIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRevisions(@jakarta.annotation.Nullable Link revisions) {
    this.revisions = revisions;
  }

  public WorkPackageModelAllOfLinks status(@jakarta.annotation.Nonnull Link status) {
    
    this.status = status;
    return this;
  }

  /**
   * The current status of the work package  **Resource**: Status
   * @return status
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_STATUS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getStatus() {
    return status;
  }


  @JsonProperty(value = JSON_PROPERTY_STATUS, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(@jakarta.annotation.Nonnull Link status) {
    this.status = status;
  }

  public WorkPackageModelAllOfLinks sprint(@jakarta.annotation.Nullable Link sprint) {
    
    this.sprint = sprint;
    return this;
  }

  /**
   * The sprint the work package is assigned to  **Resource**: Sprint  # Conditions  **Permission** view sprints
   * @return sprint
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SPRINT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getSprint() {
    return sprint;
  }


  @JsonProperty(value = JSON_PROPERTY_SPRINT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSprint(@jakarta.annotation.Nullable Link sprint) {
    this.sprint = sprint;
  }

  public WorkPackageModelAllOfLinks showResourceAllocations(@jakarta.annotation.Nullable Link showResourceAllocations) {
    
    this.showResourceAllocations = showResourceAllocations;
    return this;
  }

  /**
   * A request returning the dialog showing the resource allocations of this work package. It is used by the frontend and not intended for API users.  **Resource**: N/A  # Conditions  **Permission** view resource planners, requires the resource management module and an enterprise token
   * @return showResourceAllocations
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SHOW_RESOURCE_ALLOCATIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getShowResourceAllocations() {
    return showResourceAllocations;
  }


  @JsonProperty(value = JSON_PROPERTY_SHOW_RESOURCE_ALLOCATIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setShowResourceAllocations(@jakarta.annotation.Nullable Link showResourceAllocations) {
    this.showResourceAllocations = showResourceAllocations;
  }

  public WorkPackageModelAllOfLinks timeEntries(@jakarta.annotation.Nullable Link timeEntries) {
    
    this.timeEntries = timeEntries;
    return this;
  }

  /**
   * All time entries logged on the work package. Please note that this is a link to an HTML resource for now and as such, the link is subject to change.  **Resource**: N/A  # Conditions  **Permission** view time entries
   * @return timeEntries
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TIME_ENTRIES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getTimeEntries() {
    return timeEntries;
  }


  @JsonProperty(value = JSON_PROPERTY_TIME_ENTRIES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTimeEntries(@jakarta.annotation.Nullable Link timeEntries) {
    this.timeEntries = timeEntries;
  }

  public WorkPackageModelAllOfLinks type(@jakarta.annotation.Nonnull Link type) {
    
    this.type = type;
    return this;
  }

  /**
   * The type of the work package  **Resource**: Type
   * @return type
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_TYPE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getType() {
    return type;
  }


  @JsonProperty(value = JSON_PROPERTY_TYPE, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setType(@jakarta.annotation.Nonnull Link type) {
    this.type = type;
  }

  public WorkPackageModelAllOfLinks version(@jakarta.annotation.Nullable Link version) {
    
    this.version = version;
    return this;
  }

  /**
   * The version associated to the work package  **Resource**: Version  # Conditions  **Deprecated**: this single-valued field is being replaced by &#x60;targetVersions&#x60;. Read and write &#x60;targetVersions&#x60; instead. While multiple versions is not enabled, &#x60;version&#x60; must not be written together with &#x60;targetVersions&#x60; in the same request.
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

  public WorkPackageModelAllOfLinks targetVersions(@jakarta.annotation.Nullable List<Link> targetVersions) {
    
    this.targetVersions = targetVersions;
    return this;
  }

  public WorkPackageModelAllOfLinks addTargetVersionsItem(Link targetVersionsItem) {
    if (this.targetVersions == null) {
      this.targetVersions = new ArrayList<>();
    }
    this.targetVersions.add(targetVersionsItem);
    return this;
  }

  /**
   * List of versions associated to the work package  **Resource**: Collection of Version  # Conditions  - Transitioning from single to multiple version support - (Temporary) Only allows a single value for compatibility with the version field - (Temporary) Must not be written together with &#x60;version&#x60; in the same request
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

  public WorkPackageModelAllOfLinks observedInVersions(@jakarta.annotation.Nullable List<Link> observedInVersions) {
    
    this.observedInVersions = observedInVersions;
    return this;
  }

  public WorkPackageModelAllOfLinks addObservedInVersionsItem(Link observedInVersionsItem) {
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

  public WorkPackageModelAllOfLinks watchers(@jakarta.annotation.Nullable Link watchers) {
    
    this.watchers = watchers;
    return this;
  }

  /**
   * All users that are currently watching this work package  **Resource**: Collection  # Conditions  **Permission** view work package watchers
   * @return watchers
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_WATCHERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getWatchers() {
    return watchers;
  }


  @JsonProperty(value = JSON_PROPERTY_WATCHERS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWatchers(@jakarta.annotation.Nullable Link watchers) {
    this.watchers = watchers;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkPackageModelAllOfLinks workPackageModelAllOfLinks = (WorkPackageModelAllOfLinks) o;
    return Objects.equals(this.addComment, workPackageModelAllOfLinks.addComment) &&
        Objects.equals(this.addRelation, workPackageModelAllOfLinks.addRelation) &&
        Objects.equals(this.addWatcher, workPackageModelAllOfLinks.addWatcher) &&
        Objects.equals(this.allocateResource, workPackageModelAllOfLinks.allocateResource) &&
        Objects.equals(this.customActions, workPackageModelAllOfLinks.customActions) &&
        Objects.equals(this.previewMarkup, workPackageModelAllOfLinks.previewMarkup) &&
        Objects.equals(this.removeWatcher, workPackageModelAllOfLinks.removeWatcher) &&
        Objects.equals(this.delete, workPackageModelAllOfLinks.delete) &&
        Objects.equals(this.logTime, workPackageModelAllOfLinks.logTime) &&
        Objects.equals(this.move, workPackageModelAllOfLinks.move) &&
        Objects.equals(this.copy, workPackageModelAllOfLinks.copy) &&
        Objects.equals(this.unwatch, workPackageModelAllOfLinks.unwatch) &&
        Objects.equals(this.update, workPackageModelAllOfLinks.update) &&
        Objects.equals(this.updateImmediately, workPackageModelAllOfLinks.updateImmediately) &&
        Objects.equals(this.watch, workPackageModelAllOfLinks.watch) &&
        Objects.equals(this.self, workPackageModelAllOfLinks.self) &&
        Objects.equals(this.schema, workPackageModelAllOfLinks.schema) &&
        Objects.equals(this.allocatedPrincipals, workPackageModelAllOfLinks.allocatedPrincipals) &&
        Objects.equals(this.ancestors, workPackageModelAllOfLinks.ancestors) &&
        Objects.equals(this.attachments, workPackageModelAllOfLinks.attachments) &&
        Objects.equals(this.addAttachment, workPackageModelAllOfLinks.addAttachment) &&
        Objects.equals(this.prepareAttachment, workPackageModelAllOfLinks.prepareAttachment) &&
        Objects.equals(this.author, workPackageModelAllOfLinks.author) &&
        Objects.equals(this.assignee, workPackageModelAllOfLinks.assignee) &&
        Objects.equals(this.availableWatchers, workPackageModelAllOfLinks.availableWatchers) &&
        Objects.equals(this.budget, workPackageModelAllOfLinks.budget) &&
        Objects.equals(this.category, workPackageModelAllOfLinks.category) &&
        Objects.equals(this.children, workPackageModelAllOfLinks.children) &&
        Objects.equals(this.addFileLink, workPackageModelAllOfLinks.addFileLink) &&
        Objects.equals(this.fileLinks, workPackageModelAllOfLinks.fileLinks) &&
        Objects.equals(this.repositoryIssues, workPackageModelAllOfLinks.repositoryIssues) &&
        Objects.equals(this.repositoryMergeRequests, workPackageModelAllOfLinks.repositoryMergeRequests) &&
        Objects.equals(this.repositoryBranches, workPackageModelAllOfLinks.repositoryBranches) &&
        Objects.equals(this.repositoryCommits, workPackageModelAllOfLinks.repositoryCommits) &&
        Objects.equals(this.labels, workPackageModelAllOfLinks.labels) &&
        Objects.equals(this.parent, workPackageModelAllOfLinks.parent) &&
        Objects.equals(this.priority, workPackageModelAllOfLinks.priority) &&
        Objects.equals(this.project, workPackageModelAllOfLinks.project) &&
        Objects.equals(this.projectPhase, workPackageModelAllOfLinks.projectPhase) &&
        Objects.equals(this.projectPhaseDefinition, workPackageModelAllOfLinks.projectPhaseDefinition) &&
        Objects.equals(this.responsible, workPackageModelAllOfLinks.responsible) &&
        Objects.equals(this.relations, workPackageModelAllOfLinks.relations) &&
        Objects.equals(this.revisions, workPackageModelAllOfLinks.revisions) &&
        Objects.equals(this.status, workPackageModelAllOfLinks.status) &&
        Objects.equals(this.sprint, workPackageModelAllOfLinks.sprint) &&
        Objects.equals(this.showResourceAllocations, workPackageModelAllOfLinks.showResourceAllocations) &&
        Objects.equals(this.timeEntries, workPackageModelAllOfLinks.timeEntries) &&
        Objects.equals(this.type, workPackageModelAllOfLinks.type) &&
        Objects.equals(this.version, workPackageModelAllOfLinks.version) &&
        Objects.equals(this.targetVersions, workPackageModelAllOfLinks.targetVersions) &&
        Objects.equals(this.observedInVersions, workPackageModelAllOfLinks.observedInVersions) &&
        Objects.equals(this.watchers, workPackageModelAllOfLinks.watchers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addComment, addRelation, addWatcher, allocateResource, customActions, previewMarkup, removeWatcher, delete, logTime, move, copy, unwatch, update, updateImmediately, watch, self, schema, allocatedPrincipals, ancestors, attachments, addAttachment, prepareAttachment, author, assignee, availableWatchers, budget, category, children, addFileLink, fileLinks, repositoryIssues, repositoryMergeRequests, repositoryBranches, repositoryCommits, labels, parent, priority, project, projectPhase, projectPhaseDefinition, responsible, relations, revisions, status, sprint, showResourceAllocations, timeEntries, type, version, targetVersions, observedInVersions, watchers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkPackageModelAllOfLinks {\n");
    sb.append("    addComment: ").append(toIndentedString(addComment)).append("\n");
    sb.append("    addRelation: ").append(toIndentedString(addRelation)).append("\n");
    sb.append("    addWatcher: ").append(toIndentedString(addWatcher)).append("\n");
    sb.append("    allocateResource: ").append(toIndentedString(allocateResource)).append("\n");
    sb.append("    customActions: ").append(toIndentedString(customActions)).append("\n");
    sb.append("    previewMarkup: ").append(toIndentedString(previewMarkup)).append("\n");
    sb.append("    removeWatcher: ").append(toIndentedString(removeWatcher)).append("\n");
    sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
    sb.append("    logTime: ").append(toIndentedString(logTime)).append("\n");
    sb.append("    move: ").append(toIndentedString(move)).append("\n");
    sb.append("    copy: ").append(toIndentedString(copy)).append("\n");
    sb.append("    unwatch: ").append(toIndentedString(unwatch)).append("\n");
    sb.append("    update: ").append(toIndentedString(update)).append("\n");
    sb.append("    updateImmediately: ").append(toIndentedString(updateImmediately)).append("\n");
    sb.append("    watch: ").append(toIndentedString(watch)).append("\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    allocatedPrincipals: ").append(toIndentedString(allocatedPrincipals)).append("\n");
    sb.append("    ancestors: ").append(toIndentedString(ancestors)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    addAttachment: ").append(toIndentedString(addAttachment)).append("\n");
    sb.append("    prepareAttachment: ").append(toIndentedString(prepareAttachment)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    availableWatchers: ").append(toIndentedString(availableWatchers)).append("\n");
    sb.append("    budget: ").append(toIndentedString(budget)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    children: ").append(toIndentedString(children)).append("\n");
    sb.append("    addFileLink: ").append(toIndentedString(addFileLink)).append("\n");
    sb.append("    fileLinks: ").append(toIndentedString(fileLinks)).append("\n");
    sb.append("    repositoryIssues: ").append(toIndentedString(repositoryIssues)).append("\n");
    sb.append("    repositoryMergeRequests: ").append(toIndentedString(repositoryMergeRequests)).append("\n");
    sb.append("    repositoryBranches: ").append(toIndentedString(repositoryBranches)).append("\n");
    sb.append("    repositoryCommits: ").append(toIndentedString(repositoryCommits)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    projectPhase: ").append(toIndentedString(projectPhase)).append("\n");
    sb.append("    projectPhaseDefinition: ").append(toIndentedString(projectPhaseDefinition)).append("\n");
    sb.append("    responsible: ").append(toIndentedString(responsible)).append("\n");
    sb.append("    relations: ").append(toIndentedString(relations)).append("\n");
    sb.append("    revisions: ").append(toIndentedString(revisions)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    sprint: ").append(toIndentedString(sprint)).append("\n");
    sb.append("    showResourceAllocations: ").append(toIndentedString(showResourceAllocations)).append("\n");
    sb.append("    timeEntries: ").append(toIndentedString(timeEntries)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    targetVersions: ").append(toIndentedString(targetVersions)).append("\n");
    sb.append("    observedInVersions: ").append(toIndentedString(observedInVersions)).append("\n");
    sb.append("    watchers: ").append(toIndentedString(watchers)).append("\n");
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

