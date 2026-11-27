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
import ai.gebo.openproject.client.model.SchemaPropertyModel;
import ai.gebo.openproject.client.model.WorkPackageSchemaModelLinks;
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
 * A schema for a work package. This schema defines the attributes of a work package.  TODO: Incomplete, needs to be updated with the real behavior of schemas (when does which attribute appear?).
 */
@JsonPropertyOrder({
  WorkPackageSchemaModel.JSON_PROPERTY_HAL_TYPE,
  WorkPackageSchemaModel.JSON_PROPERTY_DEPENDENCIES,
  WorkPackageSchemaModel.JSON_PROPERTY_ATTRIBUTE_GROUPS,
  WorkPackageSchemaModel.JSON_PROPERTY_LOCK_VERSION,
  WorkPackageSchemaModel.JSON_PROPERTY_ID,
  WorkPackageSchemaModel.JSON_PROPERTY_SUBJECT,
  WorkPackageSchemaModel.JSON_PROPERTY_DESCRIPTION,
  WorkPackageSchemaModel.JSON_PROPERTY_DURATION,
  WorkPackageSchemaModel.JSON_PROPERTY_SCHEDULE_MANUALLY,
  WorkPackageSchemaModel.JSON_PROPERTY_IGNORE_NON_WORKING_DAYS,
  WorkPackageSchemaModel.JSON_PROPERTY_START_DATE,
  WorkPackageSchemaModel.JSON_PROPERTY_DUE_DATE,
  WorkPackageSchemaModel.JSON_PROPERTY_DERIVED_START_DATE,
  WorkPackageSchemaModel.JSON_PROPERTY_DERIVED_DUE_DATE,
  WorkPackageSchemaModel.JSON_PROPERTY_ESTIMATED_TIME,
  WorkPackageSchemaModel.JSON_PROPERTY_DERIVED_ESTIMATED_TIME,
  WorkPackageSchemaModel.JSON_PROPERTY_REMAINING_TIME,
  WorkPackageSchemaModel.JSON_PROPERTY_DERIVED_REMAINING_TIME,
  WorkPackageSchemaModel.JSON_PROPERTY_PERCENTAGE_DONE,
  WorkPackageSchemaModel.JSON_PROPERTY_DERIVED_PERCENTAGE_DONE,
  WorkPackageSchemaModel.JSON_PROPERTY_ALLOCATED_TIME,
  WorkPackageSchemaModel.JSON_PROPERTY_ALLOCATED_PRINCIPALS,
  WorkPackageSchemaModel.JSON_PROPERTY_READONLY,
  WorkPackageSchemaModel.JSON_PROPERTY_CREATED_AT,
  WorkPackageSchemaModel.JSON_PROPERTY_UPDATED_AT,
  WorkPackageSchemaModel.JSON_PROPERTY_AUTHOR,
  WorkPackageSchemaModel.JSON_PROPERTY_POSITION,
  WorkPackageSchemaModel.JSON_PROPERTY_PROJECT,
  WorkPackageSchemaModel.JSON_PROPERTY_PROJECT_PHASE,
  WorkPackageSchemaModel.JSON_PROPERTY_PROJECT_PHASE_DEFINITION,
  WorkPackageSchemaModel.JSON_PROPERTY_PARENT,
  WorkPackageSchemaModel.JSON_PROPERTY_SPRINT,
  WorkPackageSchemaModel.JSON_PROPERTY_STORY_POINTS,
  WorkPackageSchemaModel.JSON_PROPERTY_ASSIGNEE,
  WorkPackageSchemaModel.JSON_PROPERTY_RESPONSIBLE,
  WorkPackageSchemaModel.JSON_PROPERTY_TYPE,
  WorkPackageSchemaModel.JSON_PROPERTY_STATUS,
  WorkPackageSchemaModel.JSON_PROPERTY_CATEGORY,
  WorkPackageSchemaModel.JSON_PROPERTY_VERSION,
  WorkPackageSchemaModel.JSON_PROPERTY_TARGET_VERSIONS,
  WorkPackageSchemaModel.JSON_PROPERTY_PRIORITY,
  WorkPackageSchemaModel.JSON_PROPERTY_LINKS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class WorkPackageSchemaModel {
  /**
   * Gets or Sets halType
   */
  public enum HalTypeEnum {
    SCHEMA(String.valueOf("Schema"));

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
  @jakarta.annotation.Nullable
  private HalTypeEnum halType;

  public static final String JSON_PROPERTY_DEPENDENCIES = "_dependencies";
  @jakarta.annotation.Nullable
  private List<String> dependencies = new ArrayList<>();

  public static final String JSON_PROPERTY_ATTRIBUTE_GROUPS = "_attributeGroups";
  @jakarta.annotation.Nullable
  private List<Object> attributeGroups = new ArrayList<>();

  public static final String JSON_PROPERTY_LOCK_VERSION = "lockVersion";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel lockVersion;

  public static final String JSON_PROPERTY_ID = "id";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel id;

  public static final String JSON_PROPERTY_SUBJECT = "subject";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel subject;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel description;

  public static final String JSON_PROPERTY_DURATION = "duration";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel duration;

  public static final String JSON_PROPERTY_SCHEDULE_MANUALLY = "scheduleManually";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel scheduleManually;

  public static final String JSON_PROPERTY_IGNORE_NON_WORKING_DAYS = "ignoreNonWorkingDays";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel ignoreNonWorkingDays;

  public static final String JSON_PROPERTY_START_DATE = "startDate";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel startDate;

  public static final String JSON_PROPERTY_DUE_DATE = "dueDate";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel dueDate;

  public static final String JSON_PROPERTY_DERIVED_START_DATE = "derivedStartDate";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel derivedStartDate;

  public static final String JSON_PROPERTY_DERIVED_DUE_DATE = "derivedDueDate";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel derivedDueDate;

  public static final String JSON_PROPERTY_ESTIMATED_TIME = "estimatedTime";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel estimatedTime;

  public static final String JSON_PROPERTY_DERIVED_ESTIMATED_TIME = "derivedEstimatedTime";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel derivedEstimatedTime;

  public static final String JSON_PROPERTY_REMAINING_TIME = "remainingTime";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel remainingTime;

  public static final String JSON_PROPERTY_DERIVED_REMAINING_TIME = "derivedRemainingTime";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel derivedRemainingTime;

  public static final String JSON_PROPERTY_PERCENTAGE_DONE = "percentageDone";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel percentageDone;

  public static final String JSON_PROPERTY_DERIVED_PERCENTAGE_DONE = "derivedPercentageDone";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel derivedPercentageDone;

  public static final String JSON_PROPERTY_ALLOCATED_TIME = "allocatedTime";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel allocatedTime;

  public static final String JSON_PROPERTY_ALLOCATED_PRINCIPALS = "allocatedPrincipals";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel allocatedPrincipals;

  public static final String JSON_PROPERTY_READONLY = "readonly";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel readonly;

  public static final String JSON_PROPERTY_CREATED_AT = "createdAt";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel createdAt;

  public static final String JSON_PROPERTY_UPDATED_AT = "updatedAt";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel updatedAt;

  public static final String JSON_PROPERTY_AUTHOR = "author";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel author;

  public static final String JSON_PROPERTY_POSITION = "position";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel position;

  public static final String JSON_PROPERTY_PROJECT = "project";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel project;

  public static final String JSON_PROPERTY_PROJECT_PHASE = "projectPhase";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel projectPhase;

  public static final String JSON_PROPERTY_PROJECT_PHASE_DEFINITION = "projectPhaseDefinition";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel projectPhaseDefinition;

  public static final String JSON_PROPERTY_PARENT = "parent";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel parent;

  public static final String JSON_PROPERTY_SPRINT = "sprint";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel sprint;

  public static final String JSON_PROPERTY_STORY_POINTS = "storyPoints";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel storyPoints;

  public static final String JSON_PROPERTY_ASSIGNEE = "assignee";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel assignee;

  public static final String JSON_PROPERTY_RESPONSIBLE = "responsible";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel responsible;

  public static final String JSON_PROPERTY_TYPE = "type";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel type;

  public static final String JSON_PROPERTY_STATUS = "status";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel status;

  public static final String JSON_PROPERTY_CATEGORY = "category";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel category;

  public static final String JSON_PROPERTY_VERSION = "version";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel version;

  public static final String JSON_PROPERTY_TARGET_VERSIONS = "targetVersions";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel targetVersions;

  public static final String JSON_PROPERTY_PRIORITY = "priority";
  @jakarta.annotation.Nullable
  private SchemaPropertyModel priority;

  public static final String JSON_PROPERTY_LINKS = "_links";
  @jakarta.annotation.Nullable
  private WorkPackageSchemaModelLinks links;

  public WorkPackageSchemaModel() {
  }

  public WorkPackageSchemaModel halType(@jakarta.annotation.Nullable HalTypeEnum halType) {
    
    this.halType = halType;
    return this;
  }

  /**
   * Get halType
   * @return halType
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_HAL_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public HalTypeEnum getHalType() {
    return halType;
  }


  @JsonProperty(value = JSON_PROPERTY_HAL_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHalType(@jakarta.annotation.Nullable HalTypeEnum halType) {
    this.halType = halType;
  }

  public WorkPackageSchemaModel dependencies(@jakarta.annotation.Nullable List<String> dependencies) {
    
    this.dependencies = dependencies;
    return this;
  }

  public WorkPackageSchemaModel addDependenciesItem(String dependenciesItem) {
    if (this.dependencies == null) {
      this.dependencies = new ArrayList<>();
    }
    this.dependencies.add(dependenciesItem);
    return this;
  }

  /**
   * TBD
   * @return dependencies
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DEPENDENCIES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getDependencies() {
    return dependencies;
  }


  @JsonProperty(value = JSON_PROPERTY_DEPENDENCIES, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDependencies(@jakarta.annotation.Nullable List<String> dependencies) {
    this.dependencies = dependencies;
  }

  public WorkPackageSchemaModel attributeGroups(@jakarta.annotation.Nullable List<Object> attributeGroups) {
    
    this.attributeGroups = attributeGroups;
    return this;
  }

  public WorkPackageSchemaModel addAttributeGroupsItem(Object attributeGroupsItem) {
    if (this.attributeGroups == null) {
      this.attributeGroups = new ArrayList<>();
    }
    this.attributeGroups.add(attributeGroupsItem);
    return this;
  }

  /**
   * TBD (WorkPackageFormAttributeGroup)
   * @return attributeGroups
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ATTRIBUTE_GROUPS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Object> getAttributeGroups() {
    return attributeGroups;
  }


  @JsonProperty(value = JSON_PROPERTY_ATTRIBUTE_GROUPS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAttributeGroups(@jakarta.annotation.Nullable List<Object> attributeGroups) {
    this.attributeGroups = attributeGroups;
  }

  public WorkPackageSchemaModel lockVersion(@jakarta.annotation.Nullable SchemaPropertyModel lockVersion) {
    
    this.lockVersion = lockVersion;
    return this;
  }

  /**
   * Get lockVersion
   * @return lockVersion
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_LOCK_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getLockVersion() {
    return lockVersion;
  }


  @JsonProperty(value = JSON_PROPERTY_LOCK_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLockVersion(@jakarta.annotation.Nullable SchemaPropertyModel lockVersion) {
    this.lockVersion = lockVersion;
  }

  public WorkPackageSchemaModel id(@jakarta.annotation.Nullable SchemaPropertyModel id) {
    
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getId() {
    return id;
  }


  @JsonProperty(value = JSON_PROPERTY_ID, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@jakarta.annotation.Nullable SchemaPropertyModel id) {
    this.id = id;
  }

  public WorkPackageSchemaModel subject(@jakarta.annotation.Nullable SchemaPropertyModel subject) {
    
    this.subject = subject;
    return this;
  }

  /**
   * Get subject
   * @return subject
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SUBJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getSubject() {
    return subject;
  }


  @JsonProperty(value = JSON_PROPERTY_SUBJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSubject(@jakarta.annotation.Nullable SchemaPropertyModel subject) {
    this.subject = subject;
  }

  public WorkPackageSchemaModel description(@jakarta.annotation.Nullable SchemaPropertyModel description) {
    
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDescription() {
    return description;
  }


  @JsonProperty(value = JSON_PROPERTY_DESCRIPTION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDescription(@jakarta.annotation.Nullable SchemaPropertyModel description) {
    this.description = description;
  }

  public WorkPackageSchemaModel duration(@jakarta.annotation.Nullable SchemaPropertyModel duration) {
    
    this.duration = duration;
    return this;
  }

  /**
   * Get duration
   * @return duration
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DURATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDuration() {
    return duration;
  }


  @JsonProperty(value = JSON_PROPERTY_DURATION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDuration(@jakarta.annotation.Nullable SchemaPropertyModel duration) {
    this.duration = duration;
  }

  public WorkPackageSchemaModel scheduleManually(@jakarta.annotation.Nullable SchemaPropertyModel scheduleManually) {
    
    this.scheduleManually = scheduleManually;
    return this;
  }

  /**
   * Get scheduleManually
   * @return scheduleManually
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SCHEDULE_MANUALLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getScheduleManually() {
    return scheduleManually;
  }


  @JsonProperty(value = JSON_PROPERTY_SCHEDULE_MANUALLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setScheduleManually(@jakarta.annotation.Nullable SchemaPropertyModel scheduleManually) {
    this.scheduleManually = scheduleManually;
  }

  public WorkPackageSchemaModel ignoreNonWorkingDays(@jakarta.annotation.Nullable SchemaPropertyModel ignoreNonWorkingDays) {
    
    this.ignoreNonWorkingDays = ignoreNonWorkingDays;
    return this;
  }

  /**
   * Get ignoreNonWorkingDays
   * @return ignoreNonWorkingDays
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_IGNORE_NON_WORKING_DAYS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getIgnoreNonWorkingDays() {
    return ignoreNonWorkingDays;
  }


  @JsonProperty(value = JSON_PROPERTY_IGNORE_NON_WORKING_DAYS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIgnoreNonWorkingDays(@jakarta.annotation.Nullable SchemaPropertyModel ignoreNonWorkingDays) {
    this.ignoreNonWorkingDays = ignoreNonWorkingDays;
  }

  public WorkPackageSchemaModel startDate(@jakarta.annotation.Nullable SchemaPropertyModel startDate) {
    
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getStartDate() {
    return startDate;
  }


  @JsonProperty(value = JSON_PROPERTY_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStartDate(@jakarta.annotation.Nullable SchemaPropertyModel startDate) {
    this.startDate = startDate;
  }

  public WorkPackageSchemaModel dueDate(@jakarta.annotation.Nullable SchemaPropertyModel dueDate) {
    
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Get dueDate
   * @return dueDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDueDate() {
    return dueDate;
  }


  @JsonProperty(value = JSON_PROPERTY_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDueDate(@jakarta.annotation.Nullable SchemaPropertyModel dueDate) {
    this.dueDate = dueDate;
  }

  public WorkPackageSchemaModel derivedStartDate(@jakarta.annotation.Nullable SchemaPropertyModel derivedStartDate) {
    
    this.derivedStartDate = derivedStartDate;
    return this;
  }

  /**
   * Get derivedStartDate
   * @return derivedStartDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDerivedStartDate() {
    return derivedStartDate;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_START_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedStartDate(@jakarta.annotation.Nullable SchemaPropertyModel derivedStartDate) {
    this.derivedStartDate = derivedStartDate;
  }

  public WorkPackageSchemaModel derivedDueDate(@jakarta.annotation.Nullable SchemaPropertyModel derivedDueDate) {
    
    this.derivedDueDate = derivedDueDate;
    return this;
  }

  /**
   * Get derivedDueDate
   * @return derivedDueDate
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDerivedDueDate() {
    return derivedDueDate;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_DUE_DATE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedDueDate(@jakarta.annotation.Nullable SchemaPropertyModel derivedDueDate) {
    this.derivedDueDate = derivedDueDate;
  }

  public WorkPackageSchemaModel estimatedTime(@jakarta.annotation.Nullable SchemaPropertyModel estimatedTime) {
    
    this.estimatedTime = estimatedTime;
    return this;
  }

  /**
   * Get estimatedTime
   * @return estimatedTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getEstimatedTime() {
    return estimatedTime;
  }


  @JsonProperty(value = JSON_PROPERTY_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEstimatedTime(@jakarta.annotation.Nullable SchemaPropertyModel estimatedTime) {
    this.estimatedTime = estimatedTime;
  }

  public WorkPackageSchemaModel derivedEstimatedTime(@jakarta.annotation.Nullable SchemaPropertyModel derivedEstimatedTime) {
    
    this.derivedEstimatedTime = derivedEstimatedTime;
    return this;
  }

  /**
   * Get derivedEstimatedTime
   * @return derivedEstimatedTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDerivedEstimatedTime() {
    return derivedEstimatedTime;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_ESTIMATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedEstimatedTime(@jakarta.annotation.Nullable SchemaPropertyModel derivedEstimatedTime) {
    this.derivedEstimatedTime = derivedEstimatedTime;
  }

  public WorkPackageSchemaModel remainingTime(@jakarta.annotation.Nullable SchemaPropertyModel remainingTime) {
    
    this.remainingTime = remainingTime;
    return this;
  }

  /**
   * Get remainingTime
   * @return remainingTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_REMAINING_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getRemainingTime() {
    return remainingTime;
  }


  @JsonProperty(value = JSON_PROPERTY_REMAINING_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRemainingTime(@jakarta.annotation.Nullable SchemaPropertyModel remainingTime) {
    this.remainingTime = remainingTime;
  }

  public WorkPackageSchemaModel derivedRemainingTime(@jakarta.annotation.Nullable SchemaPropertyModel derivedRemainingTime) {
    
    this.derivedRemainingTime = derivedRemainingTime;
    return this;
  }

  /**
   * Get derivedRemainingTime
   * @return derivedRemainingTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_REMAINING_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDerivedRemainingTime() {
    return derivedRemainingTime;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_REMAINING_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedRemainingTime(@jakarta.annotation.Nullable SchemaPropertyModel derivedRemainingTime) {
    this.derivedRemainingTime = derivedRemainingTime;
  }

  public WorkPackageSchemaModel percentageDone(@jakarta.annotation.Nullable SchemaPropertyModel percentageDone) {
    
    this.percentageDone = percentageDone;
    return this;
  }

  /**
   * Get percentageDone
   * @return percentageDone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getPercentageDone() {
    return percentageDone;
  }


  @JsonProperty(value = JSON_PROPERTY_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPercentageDone(@jakarta.annotation.Nullable SchemaPropertyModel percentageDone) {
    this.percentageDone = percentageDone;
  }

  public WorkPackageSchemaModel derivedPercentageDone(@jakarta.annotation.Nullable SchemaPropertyModel derivedPercentageDone) {
    
    this.derivedPercentageDone = derivedPercentageDone;
    return this;
  }

  /**
   * Get derivedPercentageDone
   * @return derivedPercentageDone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_DERIVED_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getDerivedPercentageDone() {
    return derivedPercentageDone;
  }


  @JsonProperty(value = JSON_PROPERTY_DERIVED_PERCENTAGE_DONE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDerivedPercentageDone(@jakarta.annotation.Nullable SchemaPropertyModel derivedPercentageDone) {
    this.derivedPercentageDone = derivedPercentageDone;
  }

  public WorkPackageSchemaModel allocatedTime(@jakarta.annotation.Nullable SchemaPropertyModel allocatedTime) {
    
    this.allocatedTime = allocatedTime;
    return this;
  }

  /**
   * Get allocatedTime
   * @return allocatedTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getAllocatedTime() {
    return allocatedTime;
  }


  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_TIME, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAllocatedTime(@jakarta.annotation.Nullable SchemaPropertyModel allocatedTime) {
    this.allocatedTime = allocatedTime;
  }

  public WorkPackageSchemaModel allocatedPrincipals(@jakarta.annotation.Nullable SchemaPropertyModel allocatedPrincipals) {
    
    this.allocatedPrincipals = allocatedPrincipals;
    return this;
  }

  /**
   * Get allocatedPrincipals
   * @return allocatedPrincipals
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_PRINCIPALS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getAllocatedPrincipals() {
    return allocatedPrincipals;
  }


  @JsonProperty(value = JSON_PROPERTY_ALLOCATED_PRINCIPALS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAllocatedPrincipals(@jakarta.annotation.Nullable SchemaPropertyModel allocatedPrincipals) {
    this.allocatedPrincipals = allocatedPrincipals;
  }

  public WorkPackageSchemaModel readonly(@jakarta.annotation.Nullable SchemaPropertyModel readonly) {
    
    this.readonly = readonly;
    return this;
  }

  /**
   * Get readonly
   * @return readonly
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_READONLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getReadonly() {
    return readonly;
  }


  @JsonProperty(value = JSON_PROPERTY_READONLY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setReadonly(@jakarta.annotation.Nullable SchemaPropertyModel readonly) {
    this.readonly = readonly;
  }

  public WorkPackageSchemaModel createdAt(@jakarta.annotation.Nullable SchemaPropertyModel createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getCreatedAt() {
    return createdAt;
  }


  @JsonProperty(value = JSON_PROPERTY_CREATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreatedAt(@jakarta.annotation.Nullable SchemaPropertyModel createdAt) {
    this.createdAt = createdAt;
  }

  public WorkPackageSchemaModel updatedAt(@jakarta.annotation.Nullable SchemaPropertyModel updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getUpdatedAt() {
    return updatedAt;
  }


  @JsonProperty(value = JSON_PROPERTY_UPDATED_AT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdatedAt(@jakarta.annotation.Nullable SchemaPropertyModel updatedAt) {
    this.updatedAt = updatedAt;
  }

  public WorkPackageSchemaModel author(@jakarta.annotation.Nullable SchemaPropertyModel author) {
    
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_AUTHOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getAuthor() {
    return author;
  }


  @JsonProperty(value = JSON_PROPERTY_AUTHOR, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAuthor(@jakarta.annotation.Nullable SchemaPropertyModel author) {
    this.author = author;
  }

  public WorkPackageSchemaModel position(@jakarta.annotation.Nullable SchemaPropertyModel position) {
    
    this.position = position;
    return this;
  }

  /**
   * Get position
   * @return position
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_POSITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getPosition() {
    return position;
  }


  @JsonProperty(value = JSON_PROPERTY_POSITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPosition(@jakarta.annotation.Nullable SchemaPropertyModel position) {
    this.position = position;
  }

  public WorkPackageSchemaModel project(@jakarta.annotation.Nullable SchemaPropertyModel project) {
    
    this.project = project;
    return this;
  }

  /**
   * Get project
   * @return project
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getProject() {
    return project;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProject(@jakarta.annotation.Nullable SchemaPropertyModel project) {
    this.project = project;
  }

  public WorkPackageSchemaModel projectPhase(@jakarta.annotation.Nullable SchemaPropertyModel projectPhase) {
    
    this.projectPhase = projectPhase;
    return this;
  }

  /**
   * Get projectPhase
   * @return projectPhase
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getProjectPhase() {
    return projectPhase;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProjectPhase(@jakarta.annotation.Nullable SchemaPropertyModel projectPhase) {
    this.projectPhase = projectPhase;
  }

  public WorkPackageSchemaModel projectPhaseDefinition(@jakarta.annotation.Nullable SchemaPropertyModel projectPhaseDefinition) {
    
    this.projectPhaseDefinition = projectPhaseDefinition;
    return this;
  }

  /**
   * Get projectPhaseDefinition
   * @return projectPhaseDefinition
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE_DEFINITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getProjectPhaseDefinition() {
    return projectPhaseDefinition;
  }


  @JsonProperty(value = JSON_PROPERTY_PROJECT_PHASE_DEFINITION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProjectPhaseDefinition(@jakarta.annotation.Nullable SchemaPropertyModel projectPhaseDefinition) {
    this.projectPhaseDefinition = projectPhaseDefinition;
  }

  public WorkPackageSchemaModel parent(@jakarta.annotation.Nullable SchemaPropertyModel parent) {
    
    this.parent = parent;
    return this;
  }

  /**
   * Get parent
   * @return parent
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PARENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getParent() {
    return parent;
  }


  @JsonProperty(value = JSON_PROPERTY_PARENT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setParent(@jakarta.annotation.Nullable SchemaPropertyModel parent) {
    this.parent = parent;
  }

  public WorkPackageSchemaModel sprint(@jakarta.annotation.Nullable SchemaPropertyModel sprint) {
    
    this.sprint = sprint;
    return this;
  }

  /**
   * Get sprint
   * @return sprint
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_SPRINT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getSprint() {
    return sprint;
  }


  @JsonProperty(value = JSON_PROPERTY_SPRINT, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSprint(@jakarta.annotation.Nullable SchemaPropertyModel sprint) {
    this.sprint = sprint;
  }

  public WorkPackageSchemaModel storyPoints(@jakarta.annotation.Nullable SchemaPropertyModel storyPoints) {
    
    this.storyPoints = storyPoints;
    return this;
  }

  /**
   * Get storyPoints
   * @return storyPoints
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STORY_POINTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getStoryPoints() {
    return storyPoints;
  }


  @JsonProperty(value = JSON_PROPERTY_STORY_POINTS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStoryPoints(@jakarta.annotation.Nullable SchemaPropertyModel storyPoints) {
    this.storyPoints = storyPoints;
  }

  public WorkPackageSchemaModel assignee(@jakarta.annotation.Nullable SchemaPropertyModel assignee) {
    
    this.assignee = assignee;
    return this;
  }

  /**
   * Get assignee
   * @return assignee
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_ASSIGNEE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getAssignee() {
    return assignee;
  }


  @JsonProperty(value = JSON_PROPERTY_ASSIGNEE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAssignee(@jakarta.annotation.Nullable SchemaPropertyModel assignee) {
    this.assignee = assignee;
  }

  public WorkPackageSchemaModel responsible(@jakarta.annotation.Nullable SchemaPropertyModel responsible) {
    
    this.responsible = responsible;
    return this;
  }

  /**
   * Get responsible
   * @return responsible
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_RESPONSIBLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getResponsible() {
    return responsible;
  }


  @JsonProperty(value = JSON_PROPERTY_RESPONSIBLE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setResponsible(@jakarta.annotation.Nullable SchemaPropertyModel responsible) {
    this.responsible = responsible;
  }

  public WorkPackageSchemaModel type(@jakarta.annotation.Nullable SchemaPropertyModel type) {
    
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getType() {
    return type;
  }


  @JsonProperty(value = JSON_PROPERTY_TYPE, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(@jakarta.annotation.Nullable SchemaPropertyModel type) {
    this.type = type;
  }

  public WorkPackageSchemaModel status(@jakarta.annotation.Nullable SchemaPropertyModel status) {
    
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_STATUS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getStatus() {
    return status;
  }


  @JsonProperty(value = JSON_PROPERTY_STATUS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStatus(@jakarta.annotation.Nullable SchemaPropertyModel status) {
    this.status = status;
  }

  public WorkPackageSchemaModel category(@jakarta.annotation.Nullable SchemaPropertyModel category) {
    
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_CATEGORY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getCategory() {
    return category;
  }


  @JsonProperty(value = JSON_PROPERTY_CATEGORY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCategory(@jakarta.annotation.Nullable SchemaPropertyModel category) {
    this.category = category;
  }

  public WorkPackageSchemaModel version(@jakarta.annotation.Nullable SchemaPropertyModel version) {
    
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getVersion() {
    return version;
  }


  @JsonProperty(value = JSON_PROPERTY_VERSION, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(@jakarta.annotation.Nullable SchemaPropertyModel version) {
    this.version = version;
  }

  public WorkPackageSchemaModel targetVersions(@jakarta.annotation.Nullable SchemaPropertyModel targetVersions) {
    
    this.targetVersions = targetVersions;
    return this;
  }

  /**
   * Get targetVersions
   * @return targetVersions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_TARGET_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getTargetVersions() {
    return targetVersions;
  }


  @JsonProperty(value = JSON_PROPERTY_TARGET_VERSIONS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTargetVersions(@jakarta.annotation.Nullable SchemaPropertyModel targetVersions) {
    this.targetVersions = targetVersions;
  }

  public WorkPackageSchemaModel priority(@jakarta.annotation.Nullable SchemaPropertyModel priority) {
    
    this.priority = priority;
    return this;
  }

  /**
   * Get priority
   * @return priority
   */
  @jakarta.annotation.Nullable
  @JsonProperty(value = JSON_PROPERTY_PRIORITY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SchemaPropertyModel getPriority() {
    return priority;
  }


  @JsonProperty(value = JSON_PROPERTY_PRIORITY, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPriority(@jakarta.annotation.Nullable SchemaPropertyModel priority) {
    this.priority = priority;
  }

  public WorkPackageSchemaModel links(@jakarta.annotation.Nullable WorkPackageSchemaModelLinks links) {
    
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

  public WorkPackageSchemaModelLinks getLinks() {
    return links;
  }


  @JsonProperty(value = JSON_PROPERTY_LINKS, required = false)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLinks(@jakarta.annotation.Nullable WorkPackageSchemaModelLinks links) {
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
    WorkPackageSchemaModel workPackageSchemaModel = (WorkPackageSchemaModel) o;
    return Objects.equals(this.halType, workPackageSchemaModel.halType) &&
        Objects.equals(this.dependencies, workPackageSchemaModel.dependencies) &&
        Objects.equals(this.attributeGroups, workPackageSchemaModel.attributeGroups) &&
        Objects.equals(this.lockVersion, workPackageSchemaModel.lockVersion) &&
        Objects.equals(this.id, workPackageSchemaModel.id) &&
        Objects.equals(this.subject, workPackageSchemaModel.subject) &&
        Objects.equals(this.description, workPackageSchemaModel.description) &&
        Objects.equals(this.duration, workPackageSchemaModel.duration) &&
        Objects.equals(this.scheduleManually, workPackageSchemaModel.scheduleManually) &&
        Objects.equals(this.ignoreNonWorkingDays, workPackageSchemaModel.ignoreNonWorkingDays) &&
        Objects.equals(this.startDate, workPackageSchemaModel.startDate) &&
        Objects.equals(this.dueDate, workPackageSchemaModel.dueDate) &&
        Objects.equals(this.derivedStartDate, workPackageSchemaModel.derivedStartDate) &&
        Objects.equals(this.derivedDueDate, workPackageSchemaModel.derivedDueDate) &&
        Objects.equals(this.estimatedTime, workPackageSchemaModel.estimatedTime) &&
        Objects.equals(this.derivedEstimatedTime, workPackageSchemaModel.derivedEstimatedTime) &&
        Objects.equals(this.remainingTime, workPackageSchemaModel.remainingTime) &&
        Objects.equals(this.derivedRemainingTime, workPackageSchemaModel.derivedRemainingTime) &&
        Objects.equals(this.percentageDone, workPackageSchemaModel.percentageDone) &&
        Objects.equals(this.derivedPercentageDone, workPackageSchemaModel.derivedPercentageDone) &&
        Objects.equals(this.allocatedTime, workPackageSchemaModel.allocatedTime) &&
        Objects.equals(this.allocatedPrincipals, workPackageSchemaModel.allocatedPrincipals) &&
        Objects.equals(this.readonly, workPackageSchemaModel.readonly) &&
        Objects.equals(this.createdAt, workPackageSchemaModel.createdAt) &&
        Objects.equals(this.updatedAt, workPackageSchemaModel.updatedAt) &&
        Objects.equals(this.author, workPackageSchemaModel.author) &&
        Objects.equals(this.position, workPackageSchemaModel.position) &&
        Objects.equals(this.project, workPackageSchemaModel.project) &&
        Objects.equals(this.projectPhase, workPackageSchemaModel.projectPhase) &&
        Objects.equals(this.projectPhaseDefinition, workPackageSchemaModel.projectPhaseDefinition) &&
        Objects.equals(this.parent, workPackageSchemaModel.parent) &&
        Objects.equals(this.sprint, workPackageSchemaModel.sprint) &&
        Objects.equals(this.storyPoints, workPackageSchemaModel.storyPoints) &&
        Objects.equals(this.assignee, workPackageSchemaModel.assignee) &&
        Objects.equals(this.responsible, workPackageSchemaModel.responsible) &&
        Objects.equals(this.type, workPackageSchemaModel.type) &&
        Objects.equals(this.status, workPackageSchemaModel.status) &&
        Objects.equals(this.category, workPackageSchemaModel.category) &&
        Objects.equals(this.version, workPackageSchemaModel.version) &&
        Objects.equals(this.targetVersions, workPackageSchemaModel.targetVersions) &&
        Objects.equals(this.priority, workPackageSchemaModel.priority) &&
        Objects.equals(this.links, workPackageSchemaModel.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(halType, dependencies, attributeGroups, lockVersion, id, subject, description, duration, scheduleManually, ignoreNonWorkingDays, startDate, dueDate, derivedStartDate, derivedDueDate, estimatedTime, derivedEstimatedTime, remainingTime, derivedRemainingTime, percentageDone, derivedPercentageDone, allocatedTime, allocatedPrincipals, readonly, createdAt, updatedAt, author, position, project, projectPhase, projectPhaseDefinition, parent, sprint, storyPoints, assignee, responsible, type, status, category, version, targetVersions, priority, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkPackageSchemaModel {\n");
    sb.append("    halType: ").append(toIndentedString(halType)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    attributeGroups: ").append(toIndentedString(attributeGroups)).append("\n");
    sb.append("    lockVersion: ").append(toIndentedString(lockVersion)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    scheduleManually: ").append(toIndentedString(scheduleManually)).append("\n");
    sb.append("    ignoreNonWorkingDays: ").append(toIndentedString(ignoreNonWorkingDays)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    derivedStartDate: ").append(toIndentedString(derivedStartDate)).append("\n");
    sb.append("    derivedDueDate: ").append(toIndentedString(derivedDueDate)).append("\n");
    sb.append("    estimatedTime: ").append(toIndentedString(estimatedTime)).append("\n");
    sb.append("    derivedEstimatedTime: ").append(toIndentedString(derivedEstimatedTime)).append("\n");
    sb.append("    remainingTime: ").append(toIndentedString(remainingTime)).append("\n");
    sb.append("    derivedRemainingTime: ").append(toIndentedString(derivedRemainingTime)).append("\n");
    sb.append("    percentageDone: ").append(toIndentedString(percentageDone)).append("\n");
    sb.append("    derivedPercentageDone: ").append(toIndentedString(derivedPercentageDone)).append("\n");
    sb.append("    allocatedTime: ").append(toIndentedString(allocatedTime)).append("\n");
    sb.append("    allocatedPrincipals: ").append(toIndentedString(allocatedPrincipals)).append("\n");
    sb.append("    readonly: ").append(toIndentedString(readonly)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    projectPhase: ").append(toIndentedString(projectPhase)).append("\n");
    sb.append("    projectPhaseDefinition: ").append(toIndentedString(projectPhaseDefinition)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    sprint: ").append(toIndentedString(sprint)).append("\n");
    sb.append("    storyPoints: ").append(toIndentedString(storyPoints)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    responsible: ").append(toIndentedString(responsible)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    targetVersions: ").append(toIndentedString(targetVersions)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
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

