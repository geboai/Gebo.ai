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
import ai.gebo.openproject.client.model.ProgramModelAllOfLinksAncestors;
import ai.gebo.openproject.client.model.ProgramModelAllOfLinksStorages;
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
 * ProgramModelAllOfLinks
 */
@JsonPropertyOrder({
  ProgramModelAllOfLinks.JSON_PROPERTY_UPDATE,
  ProgramModelAllOfLinks.JSON_PROPERTY_UPDATE_IMMEDIATELY,
  ProgramModelAllOfLinks.JSON_PROPERTY_DELETE,
  ProgramModelAllOfLinks.JSON_PROPERTY_FAVOR,
  ProgramModelAllOfLinks.JSON_PROPERTY_DISFAVOR,
  ProgramModelAllOfLinks.JSON_PROPERTY_CREATE_WORK_PACKAGE,
  ProgramModelAllOfLinks.JSON_PROPERTY_CREATE_WORK_PACKAGE_IMMEDIATELY,
  ProgramModelAllOfLinks.JSON_PROPERTY_SELF,
  ProgramModelAllOfLinks.JSON_PROPERTY_CATEGORIES,
  ProgramModelAllOfLinks.JSON_PROPERTY_TYPES,
  ProgramModelAllOfLinks.JSON_PROPERTY_VERSIONS,
  ProgramModelAllOfLinks.JSON_PROPERTY_MEMBERSHIPS,
  ProgramModelAllOfLinks.JSON_PROPERTY_WORK_PACKAGES,
  ProgramModelAllOfLinks.JSON_PROPERTY_PARENT,
  ProgramModelAllOfLinks.JSON_PROPERTY_STATUS,
  ProgramModelAllOfLinks.JSON_PROPERTY_STORAGES,
  ProgramModelAllOfLinks.JSON_PROPERTY_PROJECT_STORAGES,
  ProgramModelAllOfLinks.JSON_PROPERTY_ANCESTORS
})
@JsonTypeName("ProgramModel_allOf__links")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class ProgramModelAllOfLinks {
  public static final String JSON_PROPERTY_UPDATE = "update";
  @jakarta.annotation.Nullable
  private Link update;

  public static final String JSON_PROPERTY_UPDATE_IMMEDIATELY = "updateImmediately";
  @jakarta.annotation.Nullable
  private Link updateImmediately;

  public static final String JSON_PROPERTY_DELETE = "delete";
  @jakarta.annotation.Nullable
  private Link delete;

  public static final String JSON_PROPERTY_FAVOR = "favor";
  @jakarta.annotation.Nullable
  private Link favor;

  public static final String JSON_PROPERTY_DISFAVOR = "disfavor";
  @jakarta.annotation.Nullable
  private Link disfavor;

  public static final String JSON_PROPERTY_CREATE_WORK_PACKAGE = "createWorkPackage";
  @jakarta.annotation.Nullable
  private Link createWorkPackage;

  public static final String JSON_PROPERTY_CREATE_WORK_PACKAGE_IMMEDIATELY = "createWorkPackageImmediately";
  @jakarta.annotation.Nullable
  private Link createWorkPackageImmediately;

  public static final String JSON_PROPERTY_SELF = "self";
  @jakarta.annotation.Nonnull
  private Link self;

  public static final String JSON_PROPERTY_CATEGORIES = "categories";
  @jakarta.annotation.Nonnull
  private Link categories;

  public static final String JSON_PROPERTY_TYPES = "types";
  @jakarta.annotation.Nullable
  private Link types;

  public static final String JSON_PROPERTY_VERSIONS = "versions";
  @jakarta.annotation.Nullable
  private Link versions;

  public static final String JSON_PROPERTY_MEMBERSHIPS = "memberships";
  @jakarta.annotation.Nullable
  private Link memberships;

  public static final String JSON_PROPERTY_WORK_PACKAGES = "workPackages";
  @jakarta.annotation.Nullable
  private Link workPackages;

  public static final String JSON_PROPERTY_PARENT = "parent";
  @jakarta.annotation.Nullable
  private Link parent;

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nullable
  private Link status;

  public static final String JSON_PROPERTY_STORAGES = "storages";
  @jakarta.annotation.Nullable
  private List<ProgramModelAllOfLinksStorages> storages = new ArrayList<>();

  public static final String JSON_PROPERTY_PROJECT_STORAGES = "projectStorages";
  @jakarta.annotation.Nullable
  private Link projectStorages;

  public static final String JSON_PROPERTY_ANCESTORS = "ancestors";
  @jakarta.annotation.Nullable
  private List<ProgramModelAllOfLinksAncestors> ancestors = new ArrayList<>();

  public ProgramModelAllOfLinks() {
  }

  public ProgramModelAllOfLinks update(@jakarta.annotation.Nullable Link update) {
    
    this.update = update;
    return this;
  }

  /**
   * Form endpoint that aids in updating this program  # Conditions  **Permission**: edit workspace
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

  public ProgramModelAllOfLinks updateImmediately(@jakarta.annotation.Nullable Link updateImmediately) {
    
    this.updateImmediately = updateImmediately;
    return this;
  }

  /**
   * Directly update this program  # Conditions  **Permission**: edit workspace
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

  public ProgramModelAllOfLinks delete(@jakarta.annotation.Nullable Link delete) {
    
    this.delete = delete;
    return this;
  }

  /**
   * Delete this program  # Conditions  **Permission**: admin
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

  public ProgramModelAllOfLinks favor(@jakarta.annotation.Nullable Link favor) {
    
    this.favor = favor;
    return this;
  }

  /**
   * Mark this program as favorited by the current user  # Conditions  Only present if the program is not yet favorited  Permission**: none but login is required
   * @return favor
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_FAVOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getFavor() {
    return favor;
  }


  @JsonProperty(value = JSON_PROPERTY_FAVOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFavor(@jakarta.annotation.Nullable Link favor) {
    this.favor = favor;
  }

  public ProgramModelAllOfLinks disfavor(@jakarta.annotation.Nullable Link disfavor) {
    
    this.disfavor = disfavor;
    return this;
  }

  /**
   * Mark this program as not favorited by the current user  # Conditions Only present if the program is favorited by the current user  Permission**: none but login is required
   * @return disfavor
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DISFAVOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getDisfavor() {
    return disfavor;
  }


  @JsonProperty(value = JSON_PROPERTY_DISFAVOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDisfavor(@jakarta.annotation.Nullable Link disfavor) {
    this.disfavor = disfavor;
  }

  public ProgramModelAllOfLinks createWorkPackage(@jakarta.annotation.Nullable Link createWorkPackage) {
    
    this.createWorkPackage = createWorkPackage;
    return this;
  }

  /**
   * Form endpoint that aids in preparing and creating a work package  # Conditions  **Permission**: add work packages
   * @return createWorkPackage
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATE_WORK_PACKAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getCreateWorkPackage() {
    return createWorkPackage;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATE_WORK_PACKAGE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreateWorkPackage(@jakarta.annotation.Nullable Link createWorkPackage) {
    this.createWorkPackage = createWorkPackage;
  }

  public ProgramModelAllOfLinks createWorkPackageImmediately(@jakarta.annotation.Nullable Link createWorkPackageImmediately) {
    
    this.createWorkPackageImmediately = createWorkPackageImmediately;
    return this;
  }

  /**
   * Directly creates a work package in the program  # Conditions  **Permission**: add work packages
   * @return createWorkPackageImmediately
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATE_WORK_PACKAGE_IMMEDIATELY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getCreateWorkPackageImmediately() {
    return createWorkPackageImmediately;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATE_WORK_PACKAGE_IMMEDIATELY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreateWorkPackageImmediately(@jakarta.annotation.Nullable Link createWorkPackageImmediately) {
    this.createWorkPackageImmediately = createWorkPackageImmediately;
  }

  public ProgramModelAllOfLinks self(@jakarta.annotation.Nonnull Link self) {
    
    this.self = self;
    return this;
  }

  /**
   * This program  **Resource**: Program
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

  public ProgramModelAllOfLinks categories(@jakarta.annotation.Nonnull Link categories) {
    
    this.categories = categories;
    return this;
  }

  /**
   * Categories available in this program  **Resource**: Collection
   * @return categories
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(value = JSON_PROPERTY_CATEGORIES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Link getCategories() {
    return categories;
  }


  @JsonProperty(value = JSON_PROPERTY_CATEGORIES, required = true)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCategories(@jakarta.annotation.Nonnull Link categories) {
    this.categories = categories;
  }

  public ProgramModelAllOfLinks types(@jakarta.annotation.Nullable Link types) {
    
    this.types = types;
    return this;
  }

  /**
   * Types available in this program  **Resource**: Collection  # Conditions  **Permission**: view work packages or manage types
   * @return types
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TYPES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getTypes() {
    return types;
  }


  @JsonProperty(value = JSON_PROPERTY_TYPES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTypes(@jakarta.annotation.Nullable Link types) {
    this.types = types;
  }

  public ProgramModelAllOfLinks versions(@jakarta.annotation.Nullable Link versions) {
    
    this.versions = versions;
    return this;
  }

  /**
   * Versions available in this program  **Resource**: Collection  # Conditions  **Permission**: view work packages or manage versions
   * @return versions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getVersions() {
    return versions;
  }


  @JsonProperty(value = JSON_PROPERTY_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersions(@jakarta.annotation.Nullable Link versions) {
    this.versions = versions;
  }

  public ProgramModelAllOfLinks memberships(@jakarta.annotation.Nullable Link memberships) {
    
    this.memberships = memberships;
    return this;
  }

  /**
   * Memberships in the  program  **Resource**: Collection  # Conditions  **Permission**: view members
   * @return memberships
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_MEMBERSHIPS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getMemberships() {
    return memberships;
  }


  @JsonProperty(value = JSON_PROPERTY_MEMBERSHIPS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMemberships(@jakarta.annotation.Nullable Link memberships) {
    this.memberships = memberships;
  }

  public ProgramModelAllOfLinks workPackages(@jakarta.annotation.Nullable Link workPackages) {
    
    this.workPackages = workPackages;
    return this;
  }

  /**
   * Work Packages of this program  **Resource**: Collection
   * @return workPackages
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_WORK_PACKAGES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getWorkPackages() {
    return workPackages;
  }


  @JsonProperty(value = JSON_PROPERTY_WORK_PACKAGES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWorkPackages(@jakarta.annotation.Nullable Link workPackages) {
    this.workPackages = workPackages;
  }

  public ProgramModelAllOfLinks parent(@jakarta.annotation.Nullable Link parent) {
    
    this.parent = parent;
    return this;
  }

  /**
   * Parent of the program  **Resource**: Program  # Conditions  **Permission** edit workspace
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

  public ProgramModelAllOfLinks status(@jakarta.annotation.Nullable Link status) {
    
    this.status = status;
    return this;
  }

  /**
   * Denotes the status of the program, so whether the program is on track, at risk or is having trouble.  **Resource**: ProjectStatus  # Conditions  **Permission** edit workspace
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

  public ProgramModelAllOfLinks storages(@jakarta.annotation.Nullable List<ProgramModelAllOfLinksStorages> storages) {
    
    this.storages = storages;
    return this;
  }

  public ProgramModelAllOfLinks addStoragesItem(ProgramModelAllOfLinksStorages storagesItem) {
    if (this.storages == null) {
      this.storages = new ArrayList<>();
    }
    this.storages.add(storagesItem);
    return this;
  }

  /**
   * Get storages
   * @return storages
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STORAGES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ProgramModelAllOfLinksStorages> getStorages() {
    return storages;
  }


  @JsonProperty(value = JSON_PROPERTY_STORAGES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStorages(@jakarta.annotation.Nullable List<ProgramModelAllOfLinksStorages> storages) {
    this.storages = storages;
  }

  public ProgramModelAllOfLinks projectStorages(@jakarta.annotation.Nullable Link projectStorages) {
    
    this.projectStorages = projectStorages;
    return this;
  }

  /**
   * The project storage collection of this program.  **Resource**: Collection  # Conditions  **Permission**: view_file_links
   * @return projectStorages
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT_STORAGES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Link getProjectStorages() {
    return projectStorages;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT_STORAGES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProjectStorages(@jakarta.annotation.Nullable Link projectStorages) {
    this.projectStorages = projectStorages;
  }

  public ProgramModelAllOfLinks ancestors(@jakarta.annotation.Nullable List<ProgramModelAllOfLinksAncestors> ancestors) {
    
    this.ancestors = ancestors;
    return this;
  }

  public ProgramModelAllOfLinks addAncestorsItem(ProgramModelAllOfLinksAncestors ancestorsItem) {
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
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ANCESTORS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ProgramModelAllOfLinksAncestors> getAncestors() {
    return ancestors;
  }


  @JsonProperty(value = JSON_PROPERTY_ANCESTORS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAncestors(@jakarta.annotation.Nullable List<ProgramModelAllOfLinksAncestors> ancestors) {
    this.ancestors = ancestors;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProgramModelAllOfLinks programModelAllOfLinks = (ProgramModelAllOfLinks) o;
    return Objects.equals(this.update, programModelAllOfLinks.update) &&
        Objects.equals(this.updateImmediately, programModelAllOfLinks.updateImmediately) &&
        Objects.equals(this.delete, programModelAllOfLinks.delete) &&
        Objects.equals(this.favor, programModelAllOfLinks.favor) &&
        Objects.equals(this.disfavor, programModelAllOfLinks.disfavor) &&
        Objects.equals(this.createWorkPackage, programModelAllOfLinks.createWorkPackage) &&
        Objects.equals(this.createWorkPackageImmediately, programModelAllOfLinks.createWorkPackageImmediately) &&
        Objects.equals(this.self, programModelAllOfLinks.self) &&
        Objects.equals(this.categories, programModelAllOfLinks.categories) &&
        Objects.equals(this.types, programModelAllOfLinks.types) &&
        Objects.equals(this.versions, programModelAllOfLinks.versions) &&
        Objects.equals(this.memberships, programModelAllOfLinks.memberships) &&
        Objects.equals(this.workPackages, programModelAllOfLinks.workPackages) &&
        Objects.equals(this.parent, programModelAllOfLinks.parent) &&
        Objects.equals(this.status, programModelAllOfLinks.status) &&
        Objects.equals(this.storages, programModelAllOfLinks.storages) &&
        Objects.equals(this.projectStorages, programModelAllOfLinks.projectStorages) &&
        Objects.equals(this.ancestors, programModelAllOfLinks.ancestors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(update, updateImmediately, delete, favor, disfavor, createWorkPackage, createWorkPackageImmediately, self, categories, types, versions, memberships, workPackages, parent, status, storages, projectStorages, ancestors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProgramModelAllOfLinks {\n");
    sb.append("    update: ").append(toIndentedString(update)).append("\n");
    sb.append("    updateImmediately: ").append(toIndentedString(updateImmediately)).append("\n");
    sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
    sb.append("    favor: ").append(toIndentedString(favor)).append("\n");
    sb.append("    disfavor: ").append(toIndentedString(disfavor)).append("\n");
    sb.append("    createWorkPackage: ").append(toIndentedString(createWorkPackage)).append("\n");
    sb.append("    createWorkPackageImmediately: ").append(toIndentedString(createWorkPackageImmediately)).append("\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    categories: ").append(toIndentedString(categories)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
    sb.append("    memberships: ").append(toIndentedString(memberships)).append("\n");
    sb.append("    workPackages: ").append(toIndentedString(workPackages)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    storages: ").append(toIndentedString(storages)).append("\n");
    sb.append("    projectStorages: ").append(toIndentedString(projectStorages)).append("\n");
    sb.append("    ancestors: ").append(toIndentedString(ancestors)).append("\n");
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

