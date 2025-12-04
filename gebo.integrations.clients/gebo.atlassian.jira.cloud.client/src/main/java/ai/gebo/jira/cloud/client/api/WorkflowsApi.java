/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.CreateWorkflowDetails;
import ai.gebo.jira.cloud.client.model.DeprecatedWorkflow;
import ai.gebo.jira.cloud.client.model.PageBeanWorkflow;
import ai.gebo.jira.cloud.client.model.WorkflowCapabilities;
import ai.gebo.jira.cloud.client.model.WorkflowCreateRequest;
import ai.gebo.jira.cloud.client.model.WorkflowCreateResponse;
import ai.gebo.jira.cloud.client.model.WorkflowCreateValidateRequest;
import ai.gebo.jira.cloud.client.model.WorkflowIDs;
import ai.gebo.jira.cloud.client.model.WorkflowProjectIssueTypeUsageDTO;
import ai.gebo.jira.cloud.client.model.WorkflowProjectUsageDTO;
import ai.gebo.jira.cloud.client.model.WorkflowReadRequest;
import ai.gebo.jira.cloud.client.model.WorkflowReadResponse;
import ai.gebo.jira.cloud.client.model.WorkflowSchemeUsageDTO;
import ai.gebo.jira.cloud.client.model.WorkflowSearchResponse;
import ai.gebo.jira.cloud.client.model.WorkflowUpdateRequest;
import ai.gebo.jira.cloud.client.model.WorkflowUpdateResponse;
import ai.gebo.jira.cloud.client.model.WorkflowUpdateValidateRequestBean;
import ai.gebo.jira.cloud.client.model.WorkflowValidationErrorList;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")

public class WorkflowsApi {
    private ApiClient apiClient;

    public WorkflowsApi() {
        this(new ApiClient());
    }

    //@Autowired
    public WorkflowsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create workflow
     * Creates a workflow. You can define transition rules using the shapes detailed in the following sections. If no transitional rules are specified the default system transition rules are used. Note: This only applies to company-managed scoped workflows. Use [bulk create workflows](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-workflows/#api-rest-api-3-workflows-create-post) to create both team and company-managed scoped workflows.  #### Conditions ####  Conditions enable workflow rules that govern whether a transition can execute.  ##### Always false condition #####  A condition that always fails.      {        \&quot;type\&quot;: \&quot;AlwaysFalseCondition\&quot;      }  ##### Block transition until approval #####  A condition that blocks issue transition if there is a pending approval.      {        \&quot;type\&quot;: \&quot;BlockInProgressApprovalCondition\&quot;      }  ##### Compare number custom field condition #####  A condition that allows transition if a comparison between a number custom field and a value is true.      {        \&quot;type\&quot;: \&quot;CompareNumberCFCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;comparator\&quot;: \&quot;&#x3D;\&quot;,          \&quot;fieldId\&quot;: \&quot;customfield_10029\&quot;,          \&quot;fieldValue\&quot;: 2        }      }   *  &#x60;comparator&#x60; One of the supported comparator: &#x60;&#x3D;&#x60;, &#x60;&gt;&#x60;, and &#x60;&lt;&#x60;.  *  &#x60;fieldId&#x60; The custom numeric field ID. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:float&#x60;      *  &#x60;com.pyxis.greenhopper.jira:jsw-story-points&#x60;  *  &#x60;fieldValue&#x60; The value for comparison.  ##### Hide from user condition #####  A condition that hides a transition from users. The transition can only be triggered from a workflow function or REST API operation.      {        \&quot;type\&quot;: \&quot;RemoteOnlyCondition\&quot;      }  ##### Only assignee condition #####  A condition that allows only the assignee to execute a transition.      {        \&quot;type\&quot;: \&quot;AllowOnlyAssignee\&quot;      }  ##### Only Bamboo notifications workflow condition (deprecated) #####  A condition that makes the transition available only to Bamboo build notifications.      {        \&quot;type\&quot;: \&quot;OnlyBambooNotificationsCondition\&quot;      }  ##### Only reporter condition #####  A condition that allows only the reporter to execute a transition.      {        \&quot;type\&quot;: \&quot;AllowOnlyReporter\&quot;      }  ##### Permission condition #####  A condition that allows only users with a permission to execute a transition.      {        \&quot;type\&quot;: \&quot;PermissionCondition\&quot;,        \&quot;configuration\&quot;: {            \&quot;permissionKey\&quot;: \&quot;BROWSE_PROJECTS\&quot;        }      }   *  &#x60;permissionKey&#x60; The permission required to perform the transition. Allowed values: [built-in](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions) or app defined permissions.  ##### Previous status condition #####  A condition that allows a transition based on whether an issue has or has not transitioned through a status.      {        \&quot;type\&quot;: \&quot;PreviousStatusCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;ignoreLoopTransitions\&quot;: true,          \&quot;includeCurrentStatus\&quot;: true,          \&quot;mostRecentStatusOnly\&quot;: true,          \&quot;reverseCondition\&quot;: true,          \&quot;previousStatus\&quot;: {            \&quot;id\&quot;: \&quot;5\&quot;          }        }      }  By default this condition allows the transition if the status, as defined by its ID in the &#x60;previousStatus&#x60; object, matches any previous issue status, unless:   *  &#x60;ignoreLoopTransitions&#x60; is &#x60;true&#x60;, then loop transitions (from and to the same status) are ignored.  *  &#x60;includeCurrentStatus&#x60; is &#x60;true&#x60;, then the current issue status is also checked.  *  &#x60;mostRecentStatusOnly&#x60; is &#x60;true&#x60;, then only the issue&#x27;s preceding status (the one immediately before the current status) is checked.  *  &#x60;reverseCondition&#x60; is &#x60;true&#x60;, then the status must not be present.  ##### Separation of duties condition #####  A condition that prevents a user to perform the transition, if the user has already performed a transition on the issue.      {        \&quot;type\&quot;: \&quot;SeparationOfDutiesCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;fromStatus\&quot;: {            \&quot;id\&quot;: \&quot;5\&quot;          },          \&quot;toStatus\&quot;: {            \&quot;id\&quot;: \&quot;6\&quot;          }        }      }   *  &#x60;fromStatus&#x60; OPTIONAL. An object containing the ID of the source status of the transition that is blocked. If omitted any transition to &#x60;toStatus&#x60; is blocked.  *  &#x60;toStatus&#x60; An object containing the ID of the target status of the transition that is blocked.  ##### Subtask blocking condition #####  A condition that blocks transition on a parent issue if any of its subtasks are in any of one or more statuses.      {        \&quot;type\&quot;: \&quot;SubTaskBlockingCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;statuses\&quot;: [            {              \&quot;id\&quot;: \&quot;1\&quot;            },            {              \&quot;id\&quot;: \&quot;3\&quot;            }          ]        }      }   *  &#x60;statuses&#x60; A list of objects containing status IDs.  ##### User is in any group condition #####  A condition that allows users belonging to any group from a list of groups to execute a transition.      {        \&quot;type\&quot;: \&quot;UserInAnyGroupCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;groups\&quot;: [            \&quot;administrators\&quot;,            \&quot;atlassian-addons-admin\&quot;          ]        }      }   *  &#x60;groups&#x60; A list of group names.  ##### User is in any project role condition #####  A condition that allows only users with at least one project roles from a list of project roles to execute a transition.      {        \&quot;type\&quot;: \&quot;InAnyProjectRoleCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;projectRoles\&quot;: [            {              \&quot;id\&quot;: \&quot;10002\&quot;            },            {              \&quot;id\&quot;: \&quot;10003\&quot;            },            {              \&quot;id\&quot;: \&quot;10012\&quot;            },            {              \&quot;id\&quot;: \&quot;10013\&quot;            }          ]        }      }   *  &#x60;projectRoles&#x60; A list of objects containing project role IDs.  ##### User is in custom field condition #####  A condition that allows only users listed in a given custom field to execute the transition.      {        \&quot;type\&quot;: \&quot;UserIsInCustomFieldCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;allowUserInField\&quot;: false,          \&quot;fieldId\&quot;: \&quot;customfield_10010\&quot;        }      }   *  &#x60;allowUserInField&#x60; If &#x60;true&#x60; only a user who is listed in &#x60;fieldId&#x60; can perform the transition, otherwise, only a user who is not listed in &#x60;fieldId&#x60; can perform the transition.  *  &#x60;fieldId&#x60; The ID of the field containing the list of users.  ##### User is in group condition #####  A condition that allows users belonging to a group to execute a transition.      {        \&quot;type\&quot;: \&quot;UserInGroupCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;group\&quot;: \&quot;administrators\&quot;        }      }   *  &#x60;group&#x60; The name of the group.  ##### User is in group custom field condition #####  A condition that allows users belonging to a group specified in a custom field to execute a transition.      {        \&quot;type\&quot;: \&quot;InGroupCFCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;customfield_10012\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of the field. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multigrouppicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:grouppicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:select&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multiselect&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes&#x60;      *  &#x60;com.pyxis.greenhopper.jira:gh-epic-status&#x60;  ##### User is in project role condition #####  A condition that allows users with a project role to execute a transition.      {        \&quot;type\&quot;: \&quot;InProjectRoleCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;projectRole\&quot;: {            \&quot;id\&quot;: \&quot;10002\&quot;          }        }      }   *  &#x60;projectRole&#x60; An object containing the ID of a project role.  ##### Value field condition #####  A conditions that allows a transition to execute if the value of a field is equal to a constant value or simply set.      {        \&quot;type\&quot;: \&quot;ValueFieldCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;assignee\&quot;,          \&quot;fieldValue\&quot;: \&quot;qm:6e1ecee6-8e64-4db6-8c85-916bb3275f51:54b56885-2bd2-4381-8239-78263442520f\&quot;,          \&quot;comparisonType\&quot;: \&quot;NUMBER\&quot;,          \&quot;comparator\&quot;: \&quot;&#x3D;\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of a field used in the comparison.  *  &#x60;fieldValue&#x60; The expected value of the field.  *  &#x60;comparisonType&#x60; The type of the comparison. Allowed values: &#x60;STRING&#x60;, &#x60;NUMBER&#x60;, &#x60;DATE&#x60;, &#x60;DATE_WITHOUT_TIME&#x60;, or &#x60;OPTIONID&#x60;.  *  &#x60;comparator&#x60; One of the supported comparator: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, &#x60;!&#x3D;&#x60;.  **Notes:**   *  If you choose the comparison type &#x60;STRING&#x60;, only &#x60;&#x3D;&#x60; and &#x60;!&#x3D;&#x60; are valid options.  *  You may leave &#x60;fieldValue&#x60; empty when comparison type is &#x60;!&#x3D;&#x60; to indicate that a value is required in the field.  *  For date fields without time format values as &#x60;yyyy-MM-dd&#x60;, and for those with time as &#x60;yyyy-MM-dd HH:mm&#x60;. For example, for July 16 2021 use &#x60;2021-07-16&#x60;, for 8:05 AM use &#x60;2021-07-16 08:05&#x60;, and for 4 PM: &#x60;2021-07-16 16:00&#x60;.  #### Validators ####  Validators check that any input made to the transition is valid before the transition is performed.  ##### Date field validator #####  A validator that compares two dates.      {        \&quot;type\&quot;: \&quot;DateFieldValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;comparator\&quot;: \&quot;&gt;\&quot;,            \&quot;date1\&quot;: \&quot;updated\&quot;,            \&quot;date2\&quot;: \&quot;created\&quot;,            \&quot;expression\&quot;: \&quot;1d\&quot;,            \&quot;includeTime\&quot;: true          }      }   *  &#x60;comparator&#x60; One of the supported comparator: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, or &#x60;!&#x3D;&#x60;.  *  &#x60;date1&#x60; The date field to validate. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;date2&#x60; The second date field. Required, if &#x60;expression&#x60; is not passed. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;expression&#x60; An expression specifying an offset. Required, if &#x60;date2&#x60; is not passed. Offsets are built with a number, with &#x60;-&#x60; as prefix for the past, and one of these time units: &#x60;d&#x60; for day, &#x60;w&#x60; for week, &#x60;m&#x60; for month, or &#x60;y&#x60; for year. For example, -2d means two days into the past and 1w means one week into the future. The &#x60;now&#x60; keyword enables a comparison with the current date.  *  &#x60;includeTime&#x60; If &#x60;true&#x60;, then the time part of the data is included for the comparison. If the field doesn&#x27;t have a time part, 00:00:00 is used.  ##### Windows date validator #####  A validator that checks that a date falls on or after a reference date and before or on the reference date plus a number of days.      {        \&quot;type\&quot;: \&quot;WindowsDateValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;date1\&quot;: \&quot;customfield_10009\&quot;,            \&quot;date2\&quot;: \&quot;created\&quot;,            \&quot;windowsDays\&quot;: 5          }      }   *  &#x60;date1&#x60; The date field to validate. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;date2&#x60; The reference date. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;windowsDays&#x60; A positive integer indicating a number of days.  ##### Field required validator #####  A validator that checks fields are not empty. By default, if a field is not included in the current context it&#x27;s ignored and not validated.      {          \&quot;type\&quot;: \&quot;FieldRequiredValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;ignoreContext\&quot;: true,              \&quot;errorMessage\&quot;: \&quot;Hey\&quot;,              \&quot;fieldIds\&quot;: [                  \&quot;versions\&quot;,                  \&quot;customfield_10037\&quot;,                  \&quot;customfield_10003\&quot;              ]          }      }   *  &#x60;ignoreContext&#x60; If &#x60;true&#x60;, then the context is ignored and all the fields are validated.  *  &#x60;errorMessage&#x60; OPTIONAL. The error message displayed when one or more fields are empty. A default error message is shown if an error message is not provided.  *  &#x60;fieldIds&#x60; The list of fields to validate.  ##### Field changed validator #####  A validator that checks that a field value is changed. However, this validation can be ignored for users from a list of groups.      {          \&quot;type\&quot;: \&quot;FieldChangedValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;fieldId\&quot;: \&quot;comment\&quot;,              \&quot;errorMessage\&quot;: \&quot;Hey\&quot;,              \&quot;exemptedGroups\&quot;: [                  \&quot;administrators\&quot;,                  \&quot;atlassian-addons-admin\&quot;              ]          }      }   *  &#x60;fieldId&#x60; The ID of a field.  *  &#x60;errorMessage&#x60; OPTIONAL. The error message displayed if the field is not changed. A default error message is shown if the error message is not provided.  *  &#x60;exemptedGroups&#x60; OPTIONAL. The list of groups.  ##### Field has single value validator #####  A validator that checks that a multi-select field has only one value. Optionally, the validation can ignore values copied from subtasks.      {          \&quot;type\&quot;: \&quot;FieldHasSingleValueValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;fieldId\&quot;: \&quot;attachment,              \&quot;excludeSubtasks\&quot;: true          }      }   *  &#x60;fieldId&#x60; The ID of a field.  *  &#x60;excludeSubtasks&#x60; If &#x60;true&#x60;, then values copied from subtasks are ignored.  ##### Parent status validator #####  A validator that checks the status of the parent issue of a subtask. Ìf the issue is not a subtask, no validation is performed.      {          \&quot;type\&quot;: \&quot;ParentStatusValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;parentStatuses\&quot;: [                  {                    \&quot;id\&quot;:\&quot;1\&quot;                  },                  {                    \&quot;id\&quot;:\&quot;2\&quot;                  }              ]          }      }   *  &#x60;parentStatus&#x60; The list of required parent issue statuses.  ##### Permission validator #####  A validator that checks the user has a permission.      {        \&quot;type\&quot;: \&quot;PermissionValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;permissionKey\&quot;: \&quot;ADMINISTER_PROJECTS\&quot;        }      }   *  &#x60;permissionKey&#x60; The permission required to perform the transition. Allowed values: [built-in](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions) or app defined permissions.  ##### Previous status validator #####  A validator that checks if the issue has held a status.      {        \&quot;type\&quot;: \&quot;PreviousStatusValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;mostRecentStatusOnly\&quot;: false,            \&quot;previousStatus\&quot;: {                \&quot;id\&quot;: \&quot;15\&quot;            }        }      }   *  &#x60;mostRecentStatusOnly&#x60; If &#x60;true&#x60;, then only the issue&#x27;s preceding status (the one immediately before the current status) is checked.  *  &#x60;previousStatus&#x60; An object containing the ID of an issue status.  ##### Regular expression validator #####  A validator that checks the content of a field against a regular expression.      {        \&quot;type\&quot;: \&quot;RegexpFieldValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;regExp\&quot;: \&quot;[0-9]\&quot;,            \&quot;fieldId\&quot;: \&quot;customfield_10029\&quot;        }      }   *  &#x60;regExp&#x60;A regular expression.  *  &#x60;fieldId&#x60; The ID of a field. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:select&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multiselect&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:textarea&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:textfield&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:url&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:float&#x60;      *  &#x60;com.pyxis.greenhopper.jira:jsw-story-points&#x60;      *  &#x60;com.pyxis.greenhopper.jira:gh-epic-status&#x60;      *  &#x60;description&#x60;      *  &#x60;summary&#x60;  ##### User permission validator #####  A validator that checks if a user has a permission. Obsolete. You may encounter this validator when getting transition rules and can pass it when updating or creating rules, for example, when you want to duplicate the rules from a workflow on a new workflow.      {          \&quot;type\&quot;: \&quot;UserPermissionValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;permissionKey\&quot;: \&quot;BROWSE_PROJECTS\&quot;,              \&quot;nullAllowed\&quot;: false,              \&quot;username\&quot;: \&quot;TestUser\&quot;          }      }   *  &#x60;permissionKey&#x60; The permission to be validated. Allowed values: [built-in](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions) or app defined permissions.  *  &#x60;nullAllowed&#x60; If &#x60;true&#x60;, allows the transition when &#x60;username&#x60; is empty.  *  &#x60;username&#x60; The username to validate against the &#x60;permissionKey&#x60;.  #### Post functions ####  Post functions carry out any additional processing required after a Jira workflow transition is executed.  ##### Fire issue event function #####  A post function that fires an event that is processed by the listeners.      {        \&quot;type\&quot;: \&quot;FireIssueEventFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;event\&quot;: {            \&quot;id\&quot;:\&quot;1\&quot;          }        }      }  **Note:** If provided, this post function overrides the default &#x60;FireIssueEventFunction&#x60;. Can be included once in a transition.   *  &#x60;event&#x60; An object containing the ID of the issue event.  ##### Update issue status #####  A post function that sets issue status to the linked status of the destination workflow status.      {        \&quot;type\&quot;: \&quot;UpdateIssueStatusFunction\&quot;      }  **Note:** This post function is a default function in global and directed transitions. It can only be added to the initial transition and can only be added once.  ##### Create comment #####  A post function that adds a comment entered during the transition to an issue.      {        \&quot;type\&quot;: \&quot;CreateCommentFunction\&quot;      }  **Note:** This post function is a default function in global and directed transitions. It can only be added to the initial transition and can only be added once.  ##### Store issue #####  A post function that stores updates to an issue.      {        \&quot;type\&quot;: \&quot;IssueStoreFunction\&quot;      }  **Note:** This post function can only be added to the initial transition and can only be added once.  ##### Assign to current user function #####  A post function that assigns the issue to the current user if the current user has the &#x60;ASSIGNABLE_USER&#x60; permission.      {          \&quot;type\&quot;: \&quot;AssignToCurrentUserFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Assign to lead function #####  A post function that assigns the issue to the project or component lead developer.      {          \&quot;type\&quot;: \&quot;AssignToLeadFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Assign to reporter function #####  A post function that assigns the issue to the reporter.      {          \&quot;type\&quot;: \&quot;AssignToReporterFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Clear field value function #####  A post function that clears the value from a field.      {        \&quot;type\&quot;: \&quot;ClearFieldValuePostFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;assignee\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of the field.  ##### Copy value from other field function #####  A post function that copies the value of one field to another, either within an issue or from parent to subtask.      {        \&quot;type\&quot;: \&quot;CopyValueFromOtherFieldPostFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;sourceFieldId\&quot;: \&quot;assignee\&quot;,          \&quot;destinationFieldId\&quot;: \&quot;creator\&quot;,          \&quot;copyType\&quot;: \&quot;same\&quot;        }      }   *  &#x60;sourceFieldId&#x60; The ID of the source field.  *  &#x60;destinationFieldId&#x60; The ID of the destination field.  *  &#x60;copyType&#x60; Use &#x60;same&#x60; to copy the value from a field inside the issue, or &#x60;parent&#x60; to copy the value from the parent issue.  ##### Create Crucible review workflow function (deprecated) #####  A post function that creates a Crucible review for all unreviewed code for the issue.      {          \&quot;type\&quot;: \&quot;CreateCrucibleReviewWorkflowFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Set issue security level based on user&#x27;s project role function #####  A post function that sets the issue&#x27;s security level if the current user has a project role.      {        \&quot;type\&quot;: \&quot;SetIssueSecurityFromRoleFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;projectRole\&quot;: {              \&quot;id\&quot;:\&quot;10002\&quot;          },          \&quot;issueSecurityLevel\&quot;: {              \&quot;id\&quot;:\&quot;10000\&quot;          }        }      }   *  &#x60;projectRole&#x60; An object containing the ID of the project role.  *  &#x60;issueSecurityLevel&#x60; OPTIONAL. The object containing the ID of the security level. If not passed, then the security level is set to &#x60;none&#x60;.  ##### Trigger a webhook function #####  A post function that triggers a webhook.      {        \&quot;type\&quot;: \&quot;TriggerWebhookFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;webhook\&quot;: {            \&quot;id\&quot;: \&quot;1\&quot;          }        }      }   *  &#x60;webhook&#x60; An object containing the ID of the webhook listener to trigger.  ##### Update issue custom field function #####  A post function that updates the content of an issue custom field.      {        \&quot;type\&quot;: \&quot;UpdateIssueCustomFieldPostFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;mode\&quot;: \&quot;append\&quot;,          \&quot;fieldId\&quot;: \&quot;customfield_10003\&quot;,          \&quot;fieldValue\&quot;: \&quot;yikes\&quot;        }      }   *  &#x60;mode&#x60; Use &#x60;replace&#x60; to override the field content with &#x60;fieldValue&#x60; or &#x60;append&#x60; to add &#x60;fieldValue&#x60; to the end of the field content.  *  &#x60;fieldId&#x60; The ID of the field.  *  &#x60;fieldValue&#x60; The update content.  ##### Update issue field function #####  A post function that updates a simple issue field.      {        \&quot;type\&quot;: \&quot;UpdateIssueFieldFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;assignee\&quot;,          \&quot;fieldValue\&quot;: \&quot;5f0c277e70b8a90025a00776\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of the field. Allowed field types:           *  &#x60;assignee&#x60;      *  &#x60;description&#x60;      *  &#x60;environment&#x60;      *  &#x60;priority&#x60;      *  &#x60;resolution&#x60;      *  &#x60;summary&#x60;      *  &#x60;timeoriginalestimate&#x60;      *  &#x60;timeestimate&#x60;      *  &#x60;timespent&#x60;  *  &#x60;fieldValue&#x60; The update value.  *  If the &#x60;fieldId&#x60; is &#x60;assignee&#x60;, the &#x60;fieldValue&#x60; should be one of these values:           *  an account ID.      *  &#x60;automatic&#x60;.      *  a blank string, which sets the value to &#x60;unassigned&#x60;.  #### Connect rules ####  Connect rules are conditions, validators, and post functions of a transition that are registered by Connect apps. To create a rule registered by the app, the app must be enabled and the rule&#x27;s module must exist.      {        \&quot;type\&quot;: \&quot;appKey__moduleKey\&quot;,        \&quot;configuration\&quot;: {          \&quot;value\&quot;:\&quot;{\\\&quot;isValid\\\&quot;:\\\&quot;true\\\&quot;}\&quot;        }      }   *  &#x60;type&#x60; A Connect rule key in a form of &#x60;appKey__moduleKey&#x60;.  *  &#x60;value&#x60; The stringified JSON configuration of a Connect rule.  #### Forge rules ####  Forge transition rules are not yet supported.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the workflow is created.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the required permissions.
     * <p><b>404</b> - Returned if one or more statuses is not found.
     * @param body The workflow details. (required)
     * @return WorkflowIDs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public WorkflowIDs createWorkflow(CreateWorkflowDetails body) throws RestClientException {
        return createWorkflowWithHttpInfo(body).getBody();
    }

    /**
     * Create workflow
     * Creates a workflow. You can define transition rules using the shapes detailed in the following sections. If no transitional rules are specified the default system transition rules are used. Note: This only applies to company-managed scoped workflows. Use [bulk create workflows](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-workflows/#api-rest-api-3-workflows-create-post) to create both team and company-managed scoped workflows.  #### Conditions ####  Conditions enable workflow rules that govern whether a transition can execute.  ##### Always false condition #####  A condition that always fails.      {        \&quot;type\&quot;: \&quot;AlwaysFalseCondition\&quot;      }  ##### Block transition until approval #####  A condition that blocks issue transition if there is a pending approval.      {        \&quot;type\&quot;: \&quot;BlockInProgressApprovalCondition\&quot;      }  ##### Compare number custom field condition #####  A condition that allows transition if a comparison between a number custom field and a value is true.      {        \&quot;type\&quot;: \&quot;CompareNumberCFCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;comparator\&quot;: \&quot;&#x3D;\&quot;,          \&quot;fieldId\&quot;: \&quot;customfield_10029\&quot;,          \&quot;fieldValue\&quot;: 2        }      }   *  &#x60;comparator&#x60; One of the supported comparator: &#x60;&#x3D;&#x60;, &#x60;&gt;&#x60;, and &#x60;&lt;&#x60;.  *  &#x60;fieldId&#x60; The custom numeric field ID. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:float&#x60;      *  &#x60;com.pyxis.greenhopper.jira:jsw-story-points&#x60;  *  &#x60;fieldValue&#x60; The value for comparison.  ##### Hide from user condition #####  A condition that hides a transition from users. The transition can only be triggered from a workflow function or REST API operation.      {        \&quot;type\&quot;: \&quot;RemoteOnlyCondition\&quot;      }  ##### Only assignee condition #####  A condition that allows only the assignee to execute a transition.      {        \&quot;type\&quot;: \&quot;AllowOnlyAssignee\&quot;      }  ##### Only Bamboo notifications workflow condition (deprecated) #####  A condition that makes the transition available only to Bamboo build notifications.      {        \&quot;type\&quot;: \&quot;OnlyBambooNotificationsCondition\&quot;      }  ##### Only reporter condition #####  A condition that allows only the reporter to execute a transition.      {        \&quot;type\&quot;: \&quot;AllowOnlyReporter\&quot;      }  ##### Permission condition #####  A condition that allows only users with a permission to execute a transition.      {        \&quot;type\&quot;: \&quot;PermissionCondition\&quot;,        \&quot;configuration\&quot;: {            \&quot;permissionKey\&quot;: \&quot;BROWSE_PROJECTS\&quot;        }      }   *  &#x60;permissionKey&#x60; The permission required to perform the transition. Allowed values: [built-in](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions) or app defined permissions.  ##### Previous status condition #####  A condition that allows a transition based on whether an issue has or has not transitioned through a status.      {        \&quot;type\&quot;: \&quot;PreviousStatusCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;ignoreLoopTransitions\&quot;: true,          \&quot;includeCurrentStatus\&quot;: true,          \&quot;mostRecentStatusOnly\&quot;: true,          \&quot;reverseCondition\&quot;: true,          \&quot;previousStatus\&quot;: {            \&quot;id\&quot;: \&quot;5\&quot;          }        }      }  By default this condition allows the transition if the status, as defined by its ID in the &#x60;previousStatus&#x60; object, matches any previous issue status, unless:   *  &#x60;ignoreLoopTransitions&#x60; is &#x60;true&#x60;, then loop transitions (from and to the same status) are ignored.  *  &#x60;includeCurrentStatus&#x60; is &#x60;true&#x60;, then the current issue status is also checked.  *  &#x60;mostRecentStatusOnly&#x60; is &#x60;true&#x60;, then only the issue&#x27;s preceding status (the one immediately before the current status) is checked.  *  &#x60;reverseCondition&#x60; is &#x60;true&#x60;, then the status must not be present.  ##### Separation of duties condition #####  A condition that prevents a user to perform the transition, if the user has already performed a transition on the issue.      {        \&quot;type\&quot;: \&quot;SeparationOfDutiesCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;fromStatus\&quot;: {            \&quot;id\&quot;: \&quot;5\&quot;          },          \&quot;toStatus\&quot;: {            \&quot;id\&quot;: \&quot;6\&quot;          }        }      }   *  &#x60;fromStatus&#x60; OPTIONAL. An object containing the ID of the source status of the transition that is blocked. If omitted any transition to &#x60;toStatus&#x60; is blocked.  *  &#x60;toStatus&#x60; An object containing the ID of the target status of the transition that is blocked.  ##### Subtask blocking condition #####  A condition that blocks transition on a parent issue if any of its subtasks are in any of one or more statuses.      {        \&quot;type\&quot;: \&quot;SubTaskBlockingCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;statuses\&quot;: [            {              \&quot;id\&quot;: \&quot;1\&quot;            },            {              \&quot;id\&quot;: \&quot;3\&quot;            }          ]        }      }   *  &#x60;statuses&#x60; A list of objects containing status IDs.  ##### User is in any group condition #####  A condition that allows users belonging to any group from a list of groups to execute a transition.      {        \&quot;type\&quot;: \&quot;UserInAnyGroupCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;groups\&quot;: [            \&quot;administrators\&quot;,            \&quot;atlassian-addons-admin\&quot;          ]        }      }   *  &#x60;groups&#x60; A list of group names.  ##### User is in any project role condition #####  A condition that allows only users with at least one project roles from a list of project roles to execute a transition.      {        \&quot;type\&quot;: \&quot;InAnyProjectRoleCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;projectRoles\&quot;: [            {              \&quot;id\&quot;: \&quot;10002\&quot;            },            {              \&quot;id\&quot;: \&quot;10003\&quot;            },            {              \&quot;id\&quot;: \&quot;10012\&quot;            },            {              \&quot;id\&quot;: \&quot;10013\&quot;            }          ]        }      }   *  &#x60;projectRoles&#x60; A list of objects containing project role IDs.  ##### User is in custom field condition #####  A condition that allows only users listed in a given custom field to execute the transition.      {        \&quot;type\&quot;: \&quot;UserIsInCustomFieldCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;allowUserInField\&quot;: false,          \&quot;fieldId\&quot;: \&quot;customfield_10010\&quot;        }      }   *  &#x60;allowUserInField&#x60; If &#x60;true&#x60; only a user who is listed in &#x60;fieldId&#x60; can perform the transition, otherwise, only a user who is not listed in &#x60;fieldId&#x60; can perform the transition.  *  &#x60;fieldId&#x60; The ID of the field containing the list of users.  ##### User is in group condition #####  A condition that allows users belonging to a group to execute a transition.      {        \&quot;type\&quot;: \&quot;UserInGroupCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;group\&quot;: \&quot;administrators\&quot;        }      }   *  &#x60;group&#x60; The name of the group.  ##### User is in group custom field condition #####  A condition that allows users belonging to a group specified in a custom field to execute a transition.      {        \&quot;type\&quot;: \&quot;InGroupCFCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;customfield_10012\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of the field. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multigrouppicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:grouppicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:select&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multiselect&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes&#x60;      *  &#x60;com.pyxis.greenhopper.jira:gh-epic-status&#x60;  ##### User is in project role condition #####  A condition that allows users with a project role to execute a transition.      {        \&quot;type\&quot;: \&quot;InProjectRoleCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;projectRole\&quot;: {            \&quot;id\&quot;: \&quot;10002\&quot;          }        }      }   *  &#x60;projectRole&#x60; An object containing the ID of a project role.  ##### Value field condition #####  A conditions that allows a transition to execute if the value of a field is equal to a constant value or simply set.      {        \&quot;type\&quot;: \&quot;ValueFieldCondition\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;assignee\&quot;,          \&quot;fieldValue\&quot;: \&quot;qm:6e1ecee6-8e64-4db6-8c85-916bb3275f51:54b56885-2bd2-4381-8239-78263442520f\&quot;,          \&quot;comparisonType\&quot;: \&quot;NUMBER\&quot;,          \&quot;comparator\&quot;: \&quot;&#x3D;\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of a field used in the comparison.  *  &#x60;fieldValue&#x60; The expected value of the field.  *  &#x60;comparisonType&#x60; The type of the comparison. Allowed values: &#x60;STRING&#x60;, &#x60;NUMBER&#x60;, &#x60;DATE&#x60;, &#x60;DATE_WITHOUT_TIME&#x60;, or &#x60;OPTIONID&#x60;.  *  &#x60;comparator&#x60; One of the supported comparator: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, &#x60;!&#x3D;&#x60;.  **Notes:**   *  If you choose the comparison type &#x60;STRING&#x60;, only &#x60;&#x3D;&#x60; and &#x60;!&#x3D;&#x60; are valid options.  *  You may leave &#x60;fieldValue&#x60; empty when comparison type is &#x60;!&#x3D;&#x60; to indicate that a value is required in the field.  *  For date fields without time format values as &#x60;yyyy-MM-dd&#x60;, and for those with time as &#x60;yyyy-MM-dd HH:mm&#x60;. For example, for July 16 2021 use &#x60;2021-07-16&#x60;, for 8:05 AM use &#x60;2021-07-16 08:05&#x60;, and for 4 PM: &#x60;2021-07-16 16:00&#x60;.  #### Validators ####  Validators check that any input made to the transition is valid before the transition is performed.  ##### Date field validator #####  A validator that compares two dates.      {        \&quot;type\&quot;: \&quot;DateFieldValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;comparator\&quot;: \&quot;&gt;\&quot;,            \&quot;date1\&quot;: \&quot;updated\&quot;,            \&quot;date2\&quot;: \&quot;created\&quot;,            \&quot;expression\&quot;: \&quot;1d\&quot;,            \&quot;includeTime\&quot;: true          }      }   *  &#x60;comparator&#x60; One of the supported comparator: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, or &#x60;!&#x3D;&#x60;.  *  &#x60;date1&#x60; The date field to validate. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;date2&#x60; The second date field. Required, if &#x60;expression&#x60; is not passed. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;expression&#x60; An expression specifying an offset. Required, if &#x60;date2&#x60; is not passed. Offsets are built with a number, with &#x60;-&#x60; as prefix for the past, and one of these time units: &#x60;d&#x60; for day, &#x60;w&#x60; for week, &#x60;m&#x60; for month, or &#x60;y&#x60; for year. For example, -2d means two days into the past and 1w means one week into the future. The &#x60;now&#x60; keyword enables a comparison with the current date.  *  &#x60;includeTime&#x60; If &#x60;true&#x60;, then the time part of the data is included for the comparison. If the field doesn&#x27;t have a time part, 00:00:00 is used.  ##### Windows date validator #####  A validator that checks that a date falls on or after a reference date and before or on the reference date plus a number of days.      {        \&quot;type\&quot;: \&quot;WindowsDateValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;date1\&quot;: \&quot;customfield_10009\&quot;,            \&quot;date2\&quot;: \&quot;created\&quot;,            \&quot;windowsDays\&quot;: 5          }      }   *  &#x60;date1&#x60; The date field to validate. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;date2&#x60; The reference date. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datepicker&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:datetime&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-end&#x60;      *  &#x60;com.atlassian.jpo:jpo-custom-field-baseline-start&#x60;      *  &#x60;duedate&#x60;      *  &#x60;created&#x60;      *  &#x60;updated&#x60;      *  &#x60;resolutiondate&#x60;  *  &#x60;windowsDays&#x60; A positive integer indicating a number of days.  ##### Field required validator #####  A validator that checks fields are not empty. By default, if a field is not included in the current context it&#x27;s ignored and not validated.      {          \&quot;type\&quot;: \&quot;FieldRequiredValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;ignoreContext\&quot;: true,              \&quot;errorMessage\&quot;: \&quot;Hey\&quot;,              \&quot;fieldIds\&quot;: [                  \&quot;versions\&quot;,                  \&quot;customfield_10037\&quot;,                  \&quot;customfield_10003\&quot;              ]          }      }   *  &#x60;ignoreContext&#x60; If &#x60;true&#x60;, then the context is ignored and all the fields are validated.  *  &#x60;errorMessage&#x60; OPTIONAL. The error message displayed when one or more fields are empty. A default error message is shown if an error message is not provided.  *  &#x60;fieldIds&#x60; The list of fields to validate.  ##### Field changed validator #####  A validator that checks that a field value is changed. However, this validation can be ignored for users from a list of groups.      {          \&quot;type\&quot;: \&quot;FieldChangedValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;fieldId\&quot;: \&quot;comment\&quot;,              \&quot;errorMessage\&quot;: \&quot;Hey\&quot;,              \&quot;exemptedGroups\&quot;: [                  \&quot;administrators\&quot;,                  \&quot;atlassian-addons-admin\&quot;              ]          }      }   *  &#x60;fieldId&#x60; The ID of a field.  *  &#x60;errorMessage&#x60; OPTIONAL. The error message displayed if the field is not changed. A default error message is shown if the error message is not provided.  *  &#x60;exemptedGroups&#x60; OPTIONAL. The list of groups.  ##### Field has single value validator #####  A validator that checks that a multi-select field has only one value. Optionally, the validation can ignore values copied from subtasks.      {          \&quot;type\&quot;: \&quot;FieldHasSingleValueValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;fieldId\&quot;: \&quot;attachment,              \&quot;excludeSubtasks\&quot;: true          }      }   *  &#x60;fieldId&#x60; The ID of a field.  *  &#x60;excludeSubtasks&#x60; If &#x60;true&#x60;, then values copied from subtasks are ignored.  ##### Parent status validator #####  A validator that checks the status of the parent issue of a subtask. Ìf the issue is not a subtask, no validation is performed.      {          \&quot;type\&quot;: \&quot;ParentStatusValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;parentStatuses\&quot;: [                  {                    \&quot;id\&quot;:\&quot;1\&quot;                  },                  {                    \&quot;id\&quot;:\&quot;2\&quot;                  }              ]          }      }   *  &#x60;parentStatus&#x60; The list of required parent issue statuses.  ##### Permission validator #####  A validator that checks the user has a permission.      {        \&quot;type\&quot;: \&quot;PermissionValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;permissionKey\&quot;: \&quot;ADMINISTER_PROJECTS\&quot;        }      }   *  &#x60;permissionKey&#x60; The permission required to perform the transition. Allowed values: [built-in](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions) or app defined permissions.  ##### Previous status validator #####  A validator that checks if the issue has held a status.      {        \&quot;type\&quot;: \&quot;PreviousStatusValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;mostRecentStatusOnly\&quot;: false,            \&quot;previousStatus\&quot;: {                \&quot;id\&quot;: \&quot;15\&quot;            }        }      }   *  &#x60;mostRecentStatusOnly&#x60; If &#x60;true&#x60;, then only the issue&#x27;s preceding status (the one immediately before the current status) is checked.  *  &#x60;previousStatus&#x60; An object containing the ID of an issue status.  ##### Regular expression validator #####  A validator that checks the content of a field against a regular expression.      {        \&quot;type\&quot;: \&quot;RegexpFieldValidator\&quot;,        \&quot;configuration\&quot;: {            \&quot;regExp\&quot;: \&quot;[0-9]\&quot;,            \&quot;fieldId\&quot;: \&quot;customfield_10029\&quot;        }      }   *  &#x60;regExp&#x60;A regular expression.  *  &#x60;fieldId&#x60; The ID of a field. Allowed field types:           *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:select&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multiselect&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:radiobuttons&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:textarea&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:textfield&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:url&#x60;      *  &#x60;com.atlassian.jira.plugin.system.customfieldtypes:float&#x60;      *  &#x60;com.pyxis.greenhopper.jira:jsw-story-points&#x60;      *  &#x60;com.pyxis.greenhopper.jira:gh-epic-status&#x60;      *  &#x60;description&#x60;      *  &#x60;summary&#x60;  ##### User permission validator #####  A validator that checks if a user has a permission. Obsolete. You may encounter this validator when getting transition rules and can pass it when updating or creating rules, for example, when you want to duplicate the rules from a workflow on a new workflow.      {          \&quot;type\&quot;: \&quot;UserPermissionValidator\&quot;,          \&quot;configuration\&quot;: {              \&quot;permissionKey\&quot;: \&quot;BROWSE_PROJECTS\&quot;,              \&quot;nullAllowed\&quot;: false,              \&quot;username\&quot;: \&quot;TestUser\&quot;          }      }   *  &#x60;permissionKey&#x60; The permission to be validated. Allowed values: [built-in](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions) or app defined permissions.  *  &#x60;nullAllowed&#x60; If &#x60;true&#x60;, allows the transition when &#x60;username&#x60; is empty.  *  &#x60;username&#x60; The username to validate against the &#x60;permissionKey&#x60;.  #### Post functions ####  Post functions carry out any additional processing required after a Jira workflow transition is executed.  ##### Fire issue event function #####  A post function that fires an event that is processed by the listeners.      {        \&quot;type\&quot;: \&quot;FireIssueEventFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;event\&quot;: {            \&quot;id\&quot;:\&quot;1\&quot;          }        }      }  **Note:** If provided, this post function overrides the default &#x60;FireIssueEventFunction&#x60;. Can be included once in a transition.   *  &#x60;event&#x60; An object containing the ID of the issue event.  ##### Update issue status #####  A post function that sets issue status to the linked status of the destination workflow status.      {        \&quot;type\&quot;: \&quot;UpdateIssueStatusFunction\&quot;      }  **Note:** This post function is a default function in global and directed transitions. It can only be added to the initial transition and can only be added once.  ##### Create comment #####  A post function that adds a comment entered during the transition to an issue.      {        \&quot;type\&quot;: \&quot;CreateCommentFunction\&quot;      }  **Note:** This post function is a default function in global and directed transitions. It can only be added to the initial transition and can only be added once.  ##### Store issue #####  A post function that stores updates to an issue.      {        \&quot;type\&quot;: \&quot;IssueStoreFunction\&quot;      }  **Note:** This post function can only be added to the initial transition and can only be added once.  ##### Assign to current user function #####  A post function that assigns the issue to the current user if the current user has the &#x60;ASSIGNABLE_USER&#x60; permission.      {          \&quot;type\&quot;: \&quot;AssignToCurrentUserFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Assign to lead function #####  A post function that assigns the issue to the project or component lead developer.      {          \&quot;type\&quot;: \&quot;AssignToLeadFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Assign to reporter function #####  A post function that assigns the issue to the reporter.      {          \&quot;type\&quot;: \&quot;AssignToReporterFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Clear field value function #####  A post function that clears the value from a field.      {        \&quot;type\&quot;: \&quot;ClearFieldValuePostFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;assignee\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of the field.  ##### Copy value from other field function #####  A post function that copies the value of one field to another, either within an issue or from parent to subtask.      {        \&quot;type\&quot;: \&quot;CopyValueFromOtherFieldPostFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;sourceFieldId\&quot;: \&quot;assignee\&quot;,          \&quot;destinationFieldId\&quot;: \&quot;creator\&quot;,          \&quot;copyType\&quot;: \&quot;same\&quot;        }      }   *  &#x60;sourceFieldId&#x60; The ID of the source field.  *  &#x60;destinationFieldId&#x60; The ID of the destination field.  *  &#x60;copyType&#x60; Use &#x60;same&#x60; to copy the value from a field inside the issue, or &#x60;parent&#x60; to copy the value from the parent issue.  ##### Create Crucible review workflow function (deprecated) #####  A post function that creates a Crucible review for all unreviewed code for the issue.      {          \&quot;type\&quot;: \&quot;CreateCrucibleReviewWorkflowFunction\&quot;      }  **Note:** This post function can be included once in a transition.  ##### Set issue security level based on user&#x27;s project role function #####  A post function that sets the issue&#x27;s security level if the current user has a project role.      {        \&quot;type\&quot;: \&quot;SetIssueSecurityFromRoleFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;projectRole\&quot;: {              \&quot;id\&quot;:\&quot;10002\&quot;          },          \&quot;issueSecurityLevel\&quot;: {              \&quot;id\&quot;:\&quot;10000\&quot;          }        }      }   *  &#x60;projectRole&#x60; An object containing the ID of the project role.  *  &#x60;issueSecurityLevel&#x60; OPTIONAL. The object containing the ID of the security level. If not passed, then the security level is set to &#x60;none&#x60;.  ##### Trigger a webhook function #####  A post function that triggers a webhook.      {        \&quot;type\&quot;: \&quot;TriggerWebhookFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;webhook\&quot;: {            \&quot;id\&quot;: \&quot;1\&quot;          }        }      }   *  &#x60;webhook&#x60; An object containing the ID of the webhook listener to trigger.  ##### Update issue custom field function #####  A post function that updates the content of an issue custom field.      {        \&quot;type\&quot;: \&quot;UpdateIssueCustomFieldPostFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;mode\&quot;: \&quot;append\&quot;,          \&quot;fieldId\&quot;: \&quot;customfield_10003\&quot;,          \&quot;fieldValue\&quot;: \&quot;yikes\&quot;        }      }   *  &#x60;mode&#x60; Use &#x60;replace&#x60; to override the field content with &#x60;fieldValue&#x60; or &#x60;append&#x60; to add &#x60;fieldValue&#x60; to the end of the field content.  *  &#x60;fieldId&#x60; The ID of the field.  *  &#x60;fieldValue&#x60; The update content.  ##### Update issue field function #####  A post function that updates a simple issue field.      {        \&quot;type\&quot;: \&quot;UpdateIssueFieldFunction\&quot;,        \&quot;configuration\&quot;: {          \&quot;fieldId\&quot;: \&quot;assignee\&quot;,          \&quot;fieldValue\&quot;: \&quot;5f0c277e70b8a90025a00776\&quot;        }      }   *  &#x60;fieldId&#x60; The ID of the field. Allowed field types:           *  &#x60;assignee&#x60;      *  &#x60;description&#x60;      *  &#x60;environment&#x60;      *  &#x60;priority&#x60;      *  &#x60;resolution&#x60;      *  &#x60;summary&#x60;      *  &#x60;timeoriginalestimate&#x60;      *  &#x60;timeestimate&#x60;      *  &#x60;timespent&#x60;  *  &#x60;fieldValue&#x60; The update value.  *  If the &#x60;fieldId&#x60; is &#x60;assignee&#x60;, the &#x60;fieldValue&#x60; should be one of these values:           *  an account ID.      *  &#x60;automatic&#x60;.      *  a blank string, which sets the value to &#x60;unassigned&#x60;.  #### Connect rules ####  Connect rules are conditions, validators, and post functions of a transition that are registered by Connect apps. To create a rule registered by the app, the app must be enabled and the rule&#x27;s module must exist.      {        \&quot;type\&quot;: \&quot;appKey__moduleKey\&quot;,        \&quot;configuration\&quot;: {          \&quot;value\&quot;:\&quot;{\\\&quot;isValid\\\&quot;:\\\&quot;true\\\&quot;}\&quot;        }      }   *  &#x60;type&#x60; A Connect rule key in a form of &#x60;appKey__moduleKey&#x60;.  *  &#x60;value&#x60; The stringified JSON configuration of a Connect rule.  #### Forge rules ####  Forge transition rules are not yet supported.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>201</b> - Returned if the workflow is created.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the required permissions.
     * <p><b>404</b> - Returned if one or more statuses is not found.
     * @param body The workflow details. (required)
     * @return ResponseEntity&lt;WorkflowIDs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public ResponseEntity<WorkflowIDs> createWorkflowWithHttpInfo(CreateWorkflowDetails body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createWorkflow");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowIDs> returnType = new ParameterizedTypeReference<WorkflowIDs>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk create workflows
     * Create workflows and related statuses.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body  (required)
     * @return WorkflowCreateResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowCreateResponse createWorkflows(WorkflowCreateRequest body) throws RestClientException {
        return createWorkflowsWithHttpInfo(body).getBody();
    }

    /**
     * Bulk create workflows
     * Create workflows and related statuses.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body  (required)
     * @return ResponseEntity&lt;WorkflowCreateResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowCreateResponse> createWorkflowsWithHttpInfo(WorkflowCreateRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createWorkflows");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows/create").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowCreateResponse> returnType = new ParameterizedTypeReference<WorkflowCreateResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete inactive workflow
     * Deletes a workflow.  The workflow cannot be deleted if it is:   *  an active workflow.  *  a system workflow.  *  associated with any workflow scheme.  *  associated with any draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the workflow is deleted.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the required permissions.
     * <p><b>404</b> - Returned if the workflow is not found.
     * @param entityId The entity ID of the workflow. (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteInactiveWorkflow(String entityId) throws RestClientException {
        deleteInactiveWorkflowWithHttpInfo(entityId);
    }

    /**
     * Delete inactive workflow
     * Deletes a workflow.  The workflow cannot be deleted if it is:   *  an active workflow.  *  a system workflow.  *  associated with any workflow scheme.  *  associated with any draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>204</b> - Returned if the workflow is deleted.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the required permissions.
     * <p><b>404</b> - Returned if the workflow is not found.
     * @param entityId The entity ID of the workflow. (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteInactiveWorkflowWithHttpInfo(String entityId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'entityId' when calling deleteInactiveWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("entityId", entityId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/{entityId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get all workflows
     * Returns all workflows in Jira or a workflow. Deprecated, use [Get workflows paginated](#api-rest-api-3-workflow-search-get).  If the &#x60;workflowName&#x60; parameter is specified, the workflow is returned as an object (not in an array). Otherwise, an array of workflow objects is returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user does not have the necessary permission.
     * @param workflowName The name of the workflow to be returned. Only one workflow can be specified. (optional)
     * @return List&lt;DeprecatedWorkflow&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public List<DeprecatedWorkflow> getAllWorkflows(String workflowName) throws RestClientException {
        return getAllWorkflowsWithHttpInfo(workflowName).getBody();
    }

    /**
     * Get all workflows
     * Returns all workflows in Jira or a workflow. Deprecated, use [Get workflows paginated](#api-rest-api-3-workflow-search-get).  If the &#x60;workflowName&#x60; parameter is specified, the workflow is returned as an object (not in an array). Otherwise, an array of workflow objects is returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the user does not have the necessary permission.
     * @param workflowName The name of the workflow to be returned. Only one workflow can be specified. (optional)
     * @return ResponseEntity&lt;List&lt;DeprecatedWorkflow&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    @Deprecated
    public ResponseEntity<List<DeprecatedWorkflow>> getAllWorkflowsWithHttpInfo(String workflowName) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowName", workflowName));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<List<DeprecatedWorkflow>> returnType = new ParameterizedTypeReference<List<DeprecatedWorkflow>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get projects using a given workflow
     * Returns a page of projects using a given workflow.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the workflow with the given ID does not exist.
     * @param workflowId The workflow ID (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return WorkflowProjectUsageDTO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowProjectUsageDTO getProjectUsagesForWorkflow(String workflowId, String nextPageToken, Integer maxResults) throws RestClientException {
        return getProjectUsagesForWorkflowWithHttpInfo(workflowId, nextPageToken, maxResults).getBody();
    }

    /**
     * Get projects using a given workflow
     * Returns a page of projects using a given workflow.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the workflow with the given ID does not exist.
     * @param workflowId The workflow ID (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return ResponseEntity&lt;WorkflowProjectUsageDTO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowProjectUsageDTO> getProjectUsagesForWorkflowWithHttpInfo(String workflowId, String nextPageToken, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowId' when calling getProjectUsagesForWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowId", workflowId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/{workflowId}/projectUsages").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowProjectUsageDTO> returnType = new ParameterizedTypeReference<WorkflowProjectUsageDTO>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get issue types in a project that are using a given workflow
     * Returns a page of issue types using a given workflow within a project.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the workflow with the given ID does not exist.
     * @param workflowId The workflow ID (required)
     * @param projectId The project ID (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return WorkflowProjectIssueTypeUsageDTO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowProjectIssueTypeUsageDTO getWorkflowProjectIssueTypeUsages(String workflowId, Long projectId, String nextPageToken, Integer maxResults) throws RestClientException {
        return getWorkflowProjectIssueTypeUsagesWithHttpInfo(workflowId, projectId, nextPageToken, maxResults).getBody();
    }

    /**
     * Get issue types in a project that are using a given workflow
     * Returns a page of issue types using a given workflow within a project.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the workflow with the given ID does not exist.
     * @param workflowId The workflow ID (required)
     * @param projectId The project ID (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return ResponseEntity&lt;WorkflowProjectIssueTypeUsageDTO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowProjectIssueTypeUsageDTO> getWorkflowProjectIssueTypeUsagesWithHttpInfo(String workflowId, Long projectId, String nextPageToken, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowId' when calling getWorkflowProjectIssueTypeUsages");
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectId' when calling getWorkflowProjectIssueTypeUsages");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowId", workflowId);
        uriVariables.put("projectId", projectId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/{workflowId}/project/{projectId}/issueTypeUsages").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowProjectIssueTypeUsageDTO> returnType = new ParameterizedTypeReference<WorkflowProjectIssueTypeUsageDTO>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get workflow schemes which are using a given workflow
     * Returns a page of workflow schemes using a given workflow.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the workflow with the given ID does not exist.
     * @param workflowId The workflow ID (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return WorkflowSchemeUsageDTO
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowSchemeUsageDTO getWorkflowSchemeUsagesForWorkflow(String workflowId, String nextPageToken, Integer maxResults) throws RestClientException {
        return getWorkflowSchemeUsagesForWorkflowWithHttpInfo(workflowId, nextPageToken, maxResults).getBody();
    }

    /**
     * Get workflow schemes which are using a given workflow
     * Returns a page of workflow schemes using a given workflow.
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>404</b> - Returned if the workflow with the given ID does not exist.
     * @param workflowId The workflow ID (required)
     * @param nextPageToken The cursor for pagination (optional)
     * @param maxResults The maximum number of results to return. Must be an integer between 1 and 200. (optional, default to 50)
     * @return ResponseEntity&lt;WorkflowSchemeUsageDTO&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowSchemeUsageDTO> getWorkflowSchemeUsagesForWorkflowWithHttpInfo(String workflowId, String nextPageToken, Integer maxResults) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowId' is set
        if (workflowId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowId' when calling getWorkflowSchemeUsagesForWorkflow");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowId", workflowId);
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/{workflowId}/workflowSchemes").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nextPageToken", nextPageToken));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowSchemeUsageDTO> returnType = new ParameterizedTypeReference<WorkflowSchemeUsageDTO>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get workflows paginated
     * Returns a [paginated](#pagination) list of published classic workflows. When workflow names are specified, details of those workflows are returned. Otherwise, all published classic workflows are returned.  This operation does not return next-gen workflows.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param workflowName The name of a workflow to return. To include multiple workflows, provide an ampersand-separated list. For example, &#x60;workflowName&#x3D;name1&amp;workflowName&#x3D;name2&#x60;. (optional)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;transitions&#x60; For each workflow, returns information about the transitions inside the workflow.  *  &#x60;transitions.rules&#x60; For each workflow transition, returns information about its rules. Transitions are included automatically if this expand is requested.  *  &#x60;transitions.properties&#x60; For each workflow transition, returns information about its properties. Transitions are included automatically if this expand is requested.  *  &#x60;statuses&#x60; For each workflow, returns information about the statuses inside the workflow.  *  &#x60;statuses.properties&#x60; For each workflow status, returns information about its properties. Statuses are included automatically if this expand is requested.  *  &#x60;default&#x60; For each workflow, returns information about whether this is the default workflow.  *  &#x60;schemes&#x60; For each workflow, returns information about the workflow schemes the workflow is assigned to.  *  &#x60;projects&#x60; For each workflow, returns information about the projects the workflow is assigned to, through workflow schemes.  *  &#x60;hasDraftWorkflow&#x60; For each workflow, returns information about whether the workflow has a draft version.  *  &#x60;operations&#x60; For each workflow, returns information about the actions that can be undertaken on the workflow. (optional)
     * @param queryString String used to perform a case-insensitive partial match with workflow name. (optional)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;name&#x60; Sorts by workflow name.  *  &#x60;created&#x60; Sorts by create time.  *  &#x60;updated&#x60; Sorts by update time. (optional)
     * @param isActive Filters active and inactive workflows. (optional)
     * @return PageBeanWorkflow
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PageBeanWorkflow getWorkflowsPaginated(Long startAt, Integer maxResults, List<String> workflowName, String expand, String queryString, String orderBy, Boolean isActive) throws RestClientException {
        return getWorkflowsPaginatedWithHttpInfo(startAt, maxResults, workflowName, expand, queryString, orderBy, isActive).getBody();
    }

    /**
     * Get workflows paginated
     * Returns a [paginated](#pagination) list of published classic workflows. When workflow names are specified, details of those workflows are returned. Otherwise, all published classic workflows are returned.  This operation does not return next-gen workflows.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing.
     * <p><b>403</b> - Returned if the user does not have the necessary permission.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional, default to 0)
     * @param maxResults The maximum number of items to return per page. (optional, default to 50)
     * @param workflowName The name of a workflow to return. To include multiple workflows, provide an ampersand-separated list. For example, &#x60;workflowName&#x3D;name1&amp;workflowName&#x3D;name2&#x60;. (optional)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;transitions&#x60; For each workflow, returns information about the transitions inside the workflow.  *  &#x60;transitions.rules&#x60; For each workflow transition, returns information about its rules. Transitions are included automatically if this expand is requested.  *  &#x60;transitions.properties&#x60; For each workflow transition, returns information about its properties. Transitions are included automatically if this expand is requested.  *  &#x60;statuses&#x60; For each workflow, returns information about the statuses inside the workflow.  *  &#x60;statuses.properties&#x60; For each workflow status, returns information about its properties. Statuses are included automatically if this expand is requested.  *  &#x60;default&#x60; For each workflow, returns information about whether this is the default workflow.  *  &#x60;schemes&#x60; For each workflow, returns information about the workflow schemes the workflow is assigned to.  *  &#x60;projects&#x60; For each workflow, returns information about the projects the workflow is assigned to, through workflow schemes.  *  &#x60;hasDraftWorkflow&#x60; For each workflow, returns information about whether the workflow has a draft version.  *  &#x60;operations&#x60; For each workflow, returns information about the actions that can be undertaken on the workflow. (optional)
     * @param queryString String used to perform a case-insensitive partial match with workflow name. (optional)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;name&#x60; Sorts by workflow name.  *  &#x60;created&#x60; Sorts by create time.  *  &#x60;updated&#x60; Sorts by update time. (optional)
     * @param isActive Filters active and inactive workflows. (optional)
     * @return ResponseEntity&lt;PageBeanWorkflow&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PageBeanWorkflow> getWorkflowsPaginatedWithHttpInfo(Long startAt, Integer maxResults, List<String> workflowName, String expand, String queryString, String orderBy, Boolean isActive) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflow/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "workflowName", workflowName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "queryString", queryString));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderBy", orderBy));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "isActive", isActive));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<PageBeanWorkflow> returnType = new ParameterizedTypeReference<PageBeanWorkflow>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk get workflows
     * Returns a list of workflows and related statuses by providing workflow names, workflow IDs, or project and issue types.  **[Permissions](#permissions) required:**   *  *Administer Jira* global permission to access all, including project-scoped, workflows  *  At least one of the *Administer projects* and *View (read-only) workflow* project permissions to access project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param body  (required)
     * @param expand Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;workflows.usages&#x60; Returns the project and issue types that each workflow is associated with.  *  &#x60;statuses.usages&#x60; Returns the project and issue types that each status is associated with. (optional)
     * @param useApprovalConfiguration Return the new field &#x60;approvalConfiguration&#x60; instead of the deprecated status properties for approval configuration. (optional, default to false)
     * @return WorkflowReadResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowReadResponse readWorkflows(WorkflowReadRequest body, String expand, Boolean useApprovalConfiguration) throws RestClientException {
        return readWorkflowsWithHttpInfo(body, expand, useApprovalConfiguration).getBody();
    }

    /**
     * Bulk get workflows
     * Returns a list of workflows and related statuses by providing workflow names, workflow IDs, or project and issue types.  **[Permissions](#permissions) required:**   *  *Administer Jira* global permission to access all, including project-scoped, workflows  *  At least one of the *Administer projects* and *View (read-only) workflow* project permissions to access project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param body  (required)
     * @param expand Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;workflows.usages&#x60; Returns the project and issue types that each workflow is associated with.  *  &#x60;statuses.usages&#x60; Returns the project and issue types that each status is associated with. (optional)
     * @param useApprovalConfiguration Return the new field &#x60;approvalConfiguration&#x60; instead of the deprecated status properties for approval configuration. (optional, default to false)
     * @return ResponseEntity&lt;WorkflowReadResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowReadResponse> readWorkflowsWithHttpInfo(WorkflowReadRequest body, String expand, Boolean useApprovalConfiguration) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling readWorkflows");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "useApprovalConfiguration", useApprovalConfiguration));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowReadResponse> returnType = new ParameterizedTypeReference<WorkflowReadResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Search workflows
     * Returns a [paginated](#pagination) list of global and project workflows. If workflow names are specified in query string, details of those workflows are returned. Otherwise, all workflows are returned.  **[Permissions](#permissions) required:**   *  *Administer Jira* global permission to access all, including project-scoped, workflows  *  At least one of the *Administer projects* and *View (read-only) workflow* project permissions to access project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional)
     * @param maxResults The maximum number of items to return per page. (optional)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;values.transitions&#x60; Returns the transitions that each workflow is associated with. (optional)
     * @param queryString String used to perform a case-insensitive partial match with workflow name. (optional)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;name&#x60; Sorts by workflow name.  *  &#x60;created&#x60; Sorts by create time.  *  &#x60;updated&#x60; Sorts by update time. (optional)
     * @param scope The scope of the workflow. Global for company-managed projects and Project for team-managed projects. (optional)
     * @param isActive Filters active and inactive workflows. (optional)
     * @return WorkflowSearchResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowSearchResponse searchWorkflows(Long startAt, Integer maxResults, String expand, String queryString, String orderBy, String scope, Boolean isActive) throws RestClientException {
        return searchWorkflowsWithHttpInfo(startAt, maxResults, expand, queryString, orderBy, scope, isActive).getBody();
    }

    /**
     * Search workflows
     * Returns a [paginated](#pagination) list of global and project workflows. If workflow names are specified in query string, details of those workflows are returned. Otherwise, all workflows are returned.  **[Permissions](#permissions) required:**   *  *Administer Jira* global permission to access all, including project-scoped, workflows  *  At least one of the *Administer projects* and *View (read-only) workflow* project permissions to access project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param startAt The index of the first item to return in a page of results (page offset). (optional)
     * @param maxResults The maximum number of items to return per page. (optional)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;values.transitions&#x60; Returns the transitions that each workflow is associated with. (optional)
     * @param queryString String used to perform a case-insensitive partial match with workflow name. (optional)
     * @param orderBy [Order](#ordering) the results by a field:   *  &#x60;name&#x60; Sorts by workflow name.  *  &#x60;created&#x60; Sorts by create time.  *  &#x60;updated&#x60; Sorts by update time. (optional)
     * @param scope The scope of the workflow. Global for company-managed projects and Project for team-managed projects. (optional)
     * @param isActive Filters active and inactive workflows. (optional)
     * @return ResponseEntity&lt;WorkflowSearchResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowSearchResponse> searchWorkflowsWithHttpInfo(Long startAt, Integer maxResults, String expand, String queryString, String orderBy, String scope, Boolean isActive) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startAt", startAt));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxResults", maxResults));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "queryString", queryString));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderBy", orderBy));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "isActive", isActive));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowSearchResponse> returnType = new ParameterizedTypeReference<WorkflowSearchResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bulk update workflows
     * Update workflows and related statuses.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body  (required)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;workflows.usages&#x60; Returns the project and issue types that each workflow is associated with.  *  &#x60;statuses.usages&#x60; Returns the project and issue types that each status is associated with. (optional)
     * @return WorkflowUpdateResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowUpdateResponse updateWorkflows(WorkflowUpdateRequest body, String expand) throws RestClientException {
        return updateWorkflowsWithHttpInfo(body, expand).getBody();
    }

    /**
     * Bulk update workflows
     * Update workflows and related statuses.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * <p><b>409</b> - Returned if another workflow configuration update task is ongoing.
     * @param body  (required)
     * @param expand Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;workflows.usages&#x60; Returns the project and issue types that each workflow is associated with.  *  &#x60;statuses.usages&#x60; Returns the project and issue types that each status is associated with. (optional)
     * @return ResponseEntity&lt;WorkflowUpdateResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowUpdateResponse> updateWorkflowsWithHttpInfo(WorkflowUpdateRequest body, String expand) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateWorkflows");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows/update").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expand", expand));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowUpdateResponse> returnType = new ParameterizedTypeReference<WorkflowUpdateResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Validate create workflows
     * Validate the payload for bulk create workflows.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param body  (required)
     * @return WorkflowValidationErrorList
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowValidationErrorList validateCreateWorkflows(WorkflowCreateValidateRequest body) throws RestClientException {
        return validateCreateWorkflowsWithHttpInfo(body).getBody();
    }

    /**
     * Validate create workflows
     * Validate the payload for bulk create workflows.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param body  (required)
     * @return ResponseEntity&lt;WorkflowValidationErrorList&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowValidationErrorList> validateCreateWorkflowsWithHttpInfo(WorkflowCreateValidateRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling validateCreateWorkflows");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows/create/validation").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowValidationErrorList> returnType = new ParameterizedTypeReference<WorkflowValidationErrorList>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Validate update workflows
     * Validate the payload for bulk update workflows.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param body  (required)
     * @return WorkflowValidationErrorList
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowValidationErrorList validateUpdateWorkflows(WorkflowUpdateValidateRequestBean body) throws RestClientException {
        return validateUpdateWorkflowsWithHttpInfo(body).getBody();
    }

    /**
     * Validate update workflows
     * Validate the payload for bulk update workflows.  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to create all, including global-scoped, workflows  *  *Administer projects* project permissions to create project-scoped workflows
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param body  (required)
     * @return ResponseEntity&lt;WorkflowValidationErrorList&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowValidationErrorList> validateUpdateWorkflowsWithHttpInfo(WorkflowUpdateValidateRequestBean body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling validateUpdateWorkflows");
        }
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows/update/validation").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowValidationErrorList> returnType = new ParameterizedTypeReference<WorkflowValidationErrorList>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get available workflow capabilities
     * Get the list of workflow capabilities for a specific workflow using either the workflow ID, or the project and issue type ID pair. The response includes the scope of the workflow, defined as global/project-based, and a list of project types that the workflow is scoped to. It also includes all rules organised into their broad categories (conditions, validators, actions, triggers, screens) as well as the source location (Atlassian-provided, Connect, Forge).  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to access all, including global-scoped, workflows  *  *Administer projects* project permissions to access project-scoped workflows  The current list of Atlassian-provided rules:  #### Validators ####  A validator rule that checks if a user has the required permissions to execute the transition in the workflow.  ##### Permission validator #####  A validator rule that checks if a user has the required permissions to execute the transition in the workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:check-permission-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;permissionKey\&quot;: \&quot;ADMINISTER_PROJECTS\&quot;        }      }  Parameters:   *  &#x60;permissionKey&#x60; The permission required to perform the transition. Allowed values: [built-in Jira permissions](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions).  ##### Parent or child blocking validator #####  A validator to block the child issue&#x27;s transition depending on the parent issue&#x27;s status.      {        \&quot;ruleKey\&quot; : \&quot;system:parent-or-child-blocking-validator\&quot;        \&quot;parameters\&quot; : {          \&quot;blocker\&quot; : \&quot;PARENT\&quot;          \&quot;statusIds\&quot; : \&quot;1,2,3\&quot;        }      }  Parameters:   *  &#x60;blocker&#x60; currently only supports &#x60;PARENT&#x60;.  *  &#x60;statusIds&#x60; a comma-separated list of status IDs.  ##### Previous status validator #####  A validator that checks if an issue has transitioned through specified previous status(es) before allowing the current transition to occur.      {        \&quot;ruleKey\&quot;: \&quot;system:previous-status-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;previousStatusIds\&quot;: \&quot;10014\&quot;,          \&quot;mostRecentStatusOnly\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;previousStatusIds&#x60; a comma-separated list of status IDs, currently only support one ID.  *  &#x60;mostRecentStatusOnly&#x60; when &#x60;true&#x60; only considers the most recent status for the condition evaluation. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ##### Validate a field value #####  A validation that ensures a specific field&#x27;s value meets the defined criteria before allowing an issue to transition in the workflow.  Depending on the rule type, the result will vary:  ###### Field required ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldRequired\&quot;,          \&quot;fieldsRequired\&quot;: \&quot;assignee\&quot;,          \&quot;ignoreContext\&quot;: \&quot;true\&quot;,          \&quot;errorMessage\&quot;: \&quot;An assignee must be set!\&quot;        }      }  Parameters:   *  &#x60;fieldsRequired&#x60; the ID of the field that is required. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;ignoreContext&#x60; controls the impact of context settings on field validation. When set to &#x60;true&#x60;, the validator doesn&#x27;t check a required field if its context isn&#x27;t configured for the current issue. When set to &#x60;false&#x60;, the validator requires a field even if its context is invalid. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;errorMessage&#x60; is the error message to display if the user does not provide a value during the transition. A default error message will be shown if you don&#x27;t provide one (Optional).  ###### Field changed ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldChanged\&quot;,          \&quot;groupsExemptFromValidation\&quot;: \&quot;6862ac20-8672-4f68-896d-4854f5efb79e\&quot;,          \&quot;fieldKey\&quot;: \&quot;versions\&quot;,          \&quot;errorMessage\&quot;: \&quot;Affect versions must be modified before transition\&quot;        }      }  Parameters:   *  &#x60;groupsExemptFromValidation&#x60; a comma-separated list of group IDs to be exempt from the validation.  *  &#x60;fieldKey&#x60; the ID of the field that has changed. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;errorMessage&#x60; the error message to display if the user does not provide a value during the transition. A default error message will be shown if you don&#x27;t provide one (Optional).  ###### Field has a single value ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldHasSingleValue\&quot;,          \&quot;fieldKey\&quot;: \&quot;created\&quot;,          \&quot;excludeSubtasks\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;fieldKey&#x60; the ID of the field to validate. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;excludeSubtasks&#x60; Option to exclude values copied from sub-tasks. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ###### Field matches regular expression ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldMatchesRegularExpression\&quot;,          \&quot;regexp\&quot;: \&quot;[0-9]{4}\&quot;,          \&quot;fieldKey\&quot;: \&quot;description\&quot;        }      }  Parameters:   *  &#x60;regexp&#x60; the regular expression used to validate the field\\\\u2019s content.  *  &#x60;fieldKey&#x60; the ID of the field to validate. For a custom field, it would look like &#x60;customfield_123&#x60;.  ###### Date field comparison ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;dateFieldComparison\&quot;,          \&quot;date1FieldKey\&quot;: \&quot;duedate\&quot;,          \&quot;date2FieldKey\&quot;: \&quot;customfield_10054\&quot;,          \&quot;includeTime\&quot;: \&quot;true\&quot;,          \&quot;conditionSelected\&quot;: \&quot;&gt;&#x3D;\&quot;        }      }  Parameters:   *  &#x60;date1FieldKey&#x60; the ID of the first field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;date2FieldKey&#x60; the ID of the second field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;includeTime&#x60; if &#x60;true&#x60;, compares both date and time. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;conditionSelected&#x60; the condition to compare with. Allowed values: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, &#x60;!&#x3D;&#x60;.  ###### Date range comparison ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;windowDateComparison\&quot;,          \&quot;date1FieldKey\&quot;: \&quot;customfield_10009\&quot;,          \&quot;date2FieldKey\&quot;: \&quot;customfield_10054\&quot;,          \&quot;numberOfDays\&quot;: \&quot;3\&quot;        }      }  Parameters:   *  &#x60;date1FieldKey&#x60; the ID of the first field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;date2FieldKey&#x60; the ID of the second field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;numberOfDays&#x60; maximum number of days past the reference date (&#x60;date2FieldKey&#x60;) to pass validation.  This rule is composed by aggregating the following legacy rules:   *  FieldRequiredValidator  *  FieldChangedValidator  *  FieldHasSingleValueValidator  *  RegexpFieldValidator  *  DateFieldValidator  *  WindowsDateValidator  ##### Pro forma: Forms attached validator #####  Validates that one or more forms are attached to the issue.      {        \&quot;ruleKey\&quot; : \&quot;system:proforma-forms-attached\&quot;        \&quot;parameters\&quot; : {}      }  ##### Proforma: Forms submitted validator #####  Validates that all forms attached to the issue have been submitted.      {        \&quot;ruleKey\&quot; : \&quot;system:proforma-forms-submitted\&quot;        \&quot;parameters\&quot; : {}      }  #### Conditions ####  Conditions enable workflow rules that govern whether a transition can execute.  ##### Check field value #####  A condition rule evaluates as true if a specific field&#x27;s value meets the defined criteria. This rule ensures that an issue can only transition to the next step in the workflow if the field&#x27;s value matches the desired condition.      {        \&quot;ruleKey\&quot;: \&quot;system:check-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;fieldId\&quot;: \&quot;description\&quot;,          \&quot;fieldValue\&quot;: \&quot;[\\\&quot;Done\\\&quot;]\&quot;,          \&quot;comparator\&quot;: \&quot;&#x3D;\&quot;,          \&quot;comparisonType\&quot;: \&quot;STRING\&quot;        }      }  Parameters:   *  &#x60;fieldId&#x60; The ID of the field to check the value of. For non-system fields, it will look like &#x60;customfield_123&#x60;. Note: &#x60;fieldId&#x60; is used interchangeably with the idea of &#x60;fieldKey&#x60; here, they refer to the same field.  *  &#x60;fieldValue&#x60; the list of values to check against the field\\\\u2019s value.  *  &#x60;comparator&#x60; The comparison logic. Allowed values: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, &#x60;!&#x3D;&#x60;.  *  &#x60;comparisonType&#x60; The type of data being compared. Allowed values: &#x60;STRING&#x60;, &#x60;NUMBER&#x60;, &#x60;DATE&#x60;, &#x60;DATE_WITHOUT_TIME&#x60;, &#x60;OPTIONID&#x60;.  ##### Restrict issue transition #####  This rule ensures that issue transitions are restricted based on user accounts, roles, group memberships, and permissions, maintaining control over who can transition an issue. This condition evaluates as &#x60;true&#x60; if any of the following criteria is met.      {        \&quot;ruleKey\&quot;: \&quot;system:restrict-issue-transition\&quot;,        \&quot;parameters\&quot;: {          \&quot;accountIds\&quot;: \&quot;allow-reporter,5e68ac137d64450d01a77fa0\&quot;,          \&quot;roleIds\&quot;: \&quot;10002,10004\&quot;,          \&quot;groupIds\&quot;: \&quot;703ff44a-7dc8-4f4b-9aa6-a65bf3574fa4\&quot;,          \&quot;permissionKeys\&quot;: \&quot;ADMINISTER_PROJECTS\&quot;,          \&quot;groupCustomFields\&quot;: \&quot;customfield_10028\&quot;,          \&quot;allowUserCustomFields\&quot;: \&quot;customfield_10072,customfield_10144,customfield_10007\&quot;,          \&quot;denyUserCustomFields\&quot;: \&quot;customfield_10107\&quot;        }      }  Parameters:   *  &#x60;accountIds&#x60; a comma-separated list of the user account IDs. It also allows generic values like: &#x60;allow-assignee&#x60;, &#x60;allow-reporter&#x60;, and &#x60;accountIds&#x60; Note: This is only supported in team-managed projects  *  &#x60;roleIds&#x60; a comma-separated list of role IDs.  *  &#x60;groupIds&#x60; a comma-separated list of group IDs.  *  &#x60;permissionKeys&#x60; a comma-separated list of permission keys. Allowed values: [built-in Jira permissions](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions).  *  &#x60;groupCustomFields&#x60; a comma-separated list of group custom field IDs.  *  &#x60;allowUserCustomFields&#x60; a comma-separated list of user custom field IDs to allow for issue transition.  *  &#x60;denyUserCustomFields&#x60; a comma-separated list of user custom field IDs to deny for issue transition.  This rule is composed by aggregating the following legacy rules:   *  AllowOnlyAssignee  *  AllowOnlyReporter  *  InAnyProjectRoleCondition  *  InProjectRoleCondition  *  UserInAnyGroupCondition  *  UserInGroupCondition  *  PermissionCondtion  *  InGroupCFCondition  *  UserIsInCustomFieldCondition  ##### Previous status condition #####  A condition that evaluates based on an issue&#x27;s previous status(es) and specific criteria.      {        \&quot;ruleKey\&quot; : \&quot;system:previous-status-condition\&quot;        \&quot;parameters\&quot; : {          \&quot;previousStatusIds\&quot; : \&quot;10004\&quot;,          \&quot;not\&quot;: \&quot;true\&quot;,          \&quot;mostRecentStatusOnly\&quot; : \&quot;true\&quot;,          \&quot;includeCurrentStatus\&quot;: \&quot;true\&quot;,          \&quot;ignoreLoopTransitions\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;previousStatusIds&#x60; a comma-separated list of status IDs, current only support one ID.  *  &#x60;not&#x60; indicates if the condition should be reversed. When &#x60;true&#x60; it checks that the issue has not been in the selected statuses. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;mostRecentStatusOnly&#x60; when true only considers the most recent status for the condition evaluation. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;includeCurrentStatus&#x60; includes the current status when evaluating if the issue has been through the selected statuses. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;ignoreLoopTransitions&#x60; ignore loop transitions. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ##### Parent or child blocking condition #####  A condition to block the parent\\\\u2019s issue transition depending on the child\\\\u2019s issue status.      {        \&quot;ruleKey\&quot; : \&quot;system:parent-or-child-blocking-condition\&quot;        \&quot;parameters\&quot; : {          \&quot;blocker\&quot; : \&quot;CHILD\&quot;,          \&quot;statusIds\&quot; : \&quot;1,2,3\&quot;        }      }  Parameters:   *  &#x60;blocker&#x60; currently only supports &#x60;CHILD&#x60;.  *  &#x60;statusIds&#x60; a comma-separated list of status IDs.  ##### Separation of duties #####  A condition preventing the user from performing, if the user has already performed a transition on the issue.      {        \&quot;ruleKey\&quot;: \&quot;system:separation-of-duties\&quot;,        \&quot;parameters\&quot;: {          \&quot;fromStatusId\&quot;: \&quot;10161\&quot;,          \&quot;toStatusId\&quot;: \&quot;10160\&quot;        }      }  Parameters:   *  &#x60;fromStatusId&#x60; represents the status ID from which the issue is transitioning. It ensures that the user performing the current transition has not performed any actions when the issue was in the specified status.  *  &#x60;toStatusId&#x60; represents the status ID to which the issue is transitioning. It ensures that the user performing the current transition is not the same user who has previously transitioned the issue.  ##### Restrict transitions #####  A condition preventing all users from transitioning the issue can also optionally include APIs as well.      {        \&quot;ruleKey\&quot;: \&quot;system:restrict-from-all-users\&quot;,        \&quot;parameters\&quot;: {          \&quot;restrictMode\&quot;: \&quot;users\&quot;        }      }  Parameters:   *  &#x60;restrictMode&#x60; restricts the issue transition including/excluding APIs. Allowed values: &#x60;\&quot;users\&quot;&#x60;, &#x60;\&quot;usersAndAPI\&quot;&#x60;.  ##### Jira Service Management block until approved #####  Block an issue transition until approval. Note: This is only supported in team-managed projects.      {        \&quot;ruleKey\&quot;: \&quot;system:jsd-approvals-block-until-approved\&quot;,        \&quot;parameters\&quot;: {          \&quot;approvalConfigurationJson\&quot;: \&quot;{\&quot;statusExternalUuid...}\&quot;        }      }  Parameters:   *  &#x60;approvalConfigurationJson&#x60; a stringified JSON holding the Jira Service Management approval configuration.  ##### Jira Service Management block until rejected #####  Block an issue transition until rejected. Note: This is only supported in team-managed projects.      {        \&quot;ruleKey\&quot;: \&quot;system:jsd-approvals-block-until-rejected\&quot;,        \&quot;parameters\&quot;: {          \&quot;approvalConfigurationJson\&quot;: \&quot;{\&quot;statusExternalUuid...}\&quot;        }      }  Parameters:   *  &#x60;approvalConfigurationJson&#x60; a stringified JSON holding the Jira Service Management approval configuration.  ##### Block in progress approval #####  Condition to block issue transition if there is pending approval. Note: This is only supported in company-managed projects.      {        \&quot;ruleKey\&quot;: \&quot;system:block-in-progress-approval\&quot;,        \&quot;parameters\&quot;: {}      }  #### Post functions ####  Post functions carry out any additional processing required after a workflow transition is executed.  ##### Change assignee #####  A post function rule that changes the assignee of an issue after a transition.      {        \&quot;ruleKey\&quot;: \&quot;system:change-assignee\&quot;,        \&quot;parameters\&quot;: {          \&quot;type\&quot;: \&quot;to-selected-user\&quot;,          \&quot;accountId\&quot;: \&quot;example-account-id\&quot;        }      }  Parameters:   *  &#x60;type&#x60; the parameter used to determine the new assignee. Allowed values: &#x60;to-selected-user&#x60;, &#x60;to-unassigned&#x60;, &#x60;to-current-user&#x60;, &#x60;to-current-user&#x60;, &#x60;to-default-user&#x60;, &#x60;to-default-user&#x60;  *  &#x60;accountId&#x60; the account ID of the user to assign the issue to. This parameter is required only when the type is &#x60;\&quot;to-selected-user\&quot;&#x60;.  ##### Copy field value #####  A post function that automates the process of copying values between fields during a specific transition, ensuring data consistency and reducing manual effort.      {        \&quot;ruleKey\&quot;: \&quot;system:copy-value-from-other-field\&quot;,        \&quot;parameters\&quot;: {          \&quot;sourceFieldKey\&quot;: \&quot;description\&quot;,          \&quot;targetFieldKey\&quot;: \&quot;components\&quot;,          \&quot;issueSource\&quot;: \&quot;SAME\&quot;        }      }  Parameters:   *  &#x60;sourceFieldKey&#x60; the field key to copy from. For a custom field, it would look like &#x60;customfield_123&#x60;  *  &#x60;targetFieldKey&#x60; the field key to copy to. For a custom field, it would look like &#x60;customfield_123&#x60;  *  &#x60;issueSource&#x60; &#x60;SAME&#x60; or &#x60;PARENT&#x60;. Defaults to &#x60;SAME&#x60; if no value is provided.  ##### Update field #####  A post function that updates or appends a specific field with the given value.      {        \&quot;ruleKey\&quot;: \&quot;system:update-field\&quot;,        \&quot;parameters\&quot;: {          \&quot;field\&quot;: \&quot;customfield_10056\&quot;,          \&quot;value\&quot;: \&quot;asdf\&quot;,          \&quot;mode\&quot;: \&quot;append\&quot;        }      }  Parameters:   *  &#x60;field&#x60; the ID of the field to update. For a custom field, it would look like &#x60;customfield_123&#x60;  *  &#x60;value&#x60; the value to update the field with.  *  &#x60;mode&#x60; &#x60;append&#x60; or &#x60;replace&#x60;. Determines if a value will be appended to the current value, or if the current value will be replaced.  ##### Trigger webhook #####  A post function that automatically triggers a predefined webhook when a transition occurs in the workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:trigger-webhook\&quot;,        \&quot;parameters\&quot;: {          \&quot;webhookId\&quot;: \&quot;1\&quot;        }      }  Parameters:   *  &#x60;webhookId&#x60; the ID of the webhook.  #### Screen ####  ##### Remind people to update fields #####  A screen rule that prompts users to update a specific field when they interact with an issue screen during a transition. This rule is useful for ensuring that users provide or modify necessary information before moving an issue to the next step in the workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:remind-people-to-update-fields\&quot;,        \&quot;params\&quot;: {          \&quot;remindingFieldIds\&quot;: \&quot;assignee,customfield_10025\&quot;,          \&quot;remindingMessage\&quot;: \&quot;The message\&quot;,          \&quot;remindingAlwaysAsk\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;remindingFieldIds&#x60; a comma-separated list of field IDs. Note: &#x60;fieldId&#x60; is used interchangeably with the idea of &#x60;fieldKey&#x60; here, they refer to the same field.  *  &#x60;remindingMessage&#x60; the message to display when prompting the users to update the fields.  *  &#x60;remindingAlwaysAsk&#x60; always remind to update fields. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ##### Shared transition screen #####  A common screen that is shared between transitions in a workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:transition-screen\&quot;,        \&quot;params\&quot;: {          \&quot;screenId\&quot;: \&quot;3\&quot;        }      }  Parameters:   *  &#x60;screenId&#x60; the ID of the screen.  #### Connect &amp; Forge ####  ##### Connect rules #####  Validator/Condition/Post function for Connect app.      {        \&quot;ruleKey\&quot;: \&quot;connect:expression-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;appKey\&quot;: \&quot;com.atlassian.app\&quot;,          \&quot;config\&quot;: \&quot;\&quot;,          \&quot;id\&quot;: \&quot;90ce590f-e90c-4cd3-8281-165ce41f2ac3\&quot;,          \&quot;disabled\&quot;: \&quot;false\&quot;,          \&quot;tag\&quot;: \&quot;\&quot;        }      }  Parameters:   *  &#x60;ruleKey&#x60; Validator: &#x60;connect:expression-validator&#x60;, Condition: &#x60;connect:expression-condition&#x60;, and Post function: &#x60;connect:remote-workflow-function&#x60;  *  &#x60;appKey&#x60; the reference to the Connect app  *  &#x60;config&#x60; a JSON payload string describing the configuration  *  &#x60;id&#x60; the ID of the rule  *  &#x60;disabled&#x60; determine if the Connect app is disabled. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;tag&#x60; additional tags for the Connect app  ##### Forge rules #####  Validator/Condition/Post function for Forge app.      {        \&quot;ruleKey\&quot;: \&quot;forge:expression-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;key\&quot;: \&quot;ari:cloud:ecosystem::extension/{appId}/{environmentId}/static/{moduleKey}\&quot;,          \&quot;config\&quot;: \&quot;{\&quot;searchString\&quot;:\&quot;workflow validator\&quot;}\&quot;,          \&quot;id\&quot;: \&quot;a865ddf6-bb3f-4a7b-9540-c2f8b3f9f6c2\&quot;        }      }  Parameters:   *  &#x60;ruleKey&#x60; Validator: &#x60;forge:expression-validator&#x60;, Condition: &#x60;forge:expression-condition&#x60;, and Post function: &#x60;forge:workflow-post-function&#x60;  *  &#x60;key&#x60; the identifier for the Forge app  *  &#x60;config&#x60; the persistent stringified JSON configuration for the Forge rule  *  &#x60;id&#x60; the ID of the Forge rule
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param workflowId  (optional)
     * @param projectId  (optional)
     * @param issueTypeId  (optional)
     * @return WorkflowCapabilities
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowCapabilities workflowCapabilities(String workflowId, String projectId, String issueTypeId) throws RestClientException {
        return workflowCapabilitiesWithHttpInfo(workflowId, projectId, issueTypeId).getBody();
    }

    /**
     * Get available workflow capabilities
     * Get the list of workflow capabilities for a specific workflow using either the workflow ID, or the project and issue type ID pair. The response includes the scope of the workflow, defined as global/project-based, and a list of project types that the workflow is scoped to. It also includes all rules organised into their broad categories (conditions, validators, actions, triggers, screens) as well as the source location (Atlassian-provided, Connect, Forge).  **[Permissions](#permissions) required:**   *  *Administer Jira* project permission to access all, including global-scoped, workflows  *  *Administer projects* project permissions to access project-scoped workflows  The current list of Atlassian-provided rules:  #### Validators ####  A validator rule that checks if a user has the required permissions to execute the transition in the workflow.  ##### Permission validator #####  A validator rule that checks if a user has the required permissions to execute the transition in the workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:check-permission-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;permissionKey\&quot;: \&quot;ADMINISTER_PROJECTS\&quot;        }      }  Parameters:   *  &#x60;permissionKey&#x60; The permission required to perform the transition. Allowed values: [built-in Jira permissions](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions).  ##### Parent or child blocking validator #####  A validator to block the child issue&#x27;s transition depending on the parent issue&#x27;s status.      {        \&quot;ruleKey\&quot; : \&quot;system:parent-or-child-blocking-validator\&quot;        \&quot;parameters\&quot; : {          \&quot;blocker\&quot; : \&quot;PARENT\&quot;          \&quot;statusIds\&quot; : \&quot;1,2,3\&quot;        }      }  Parameters:   *  &#x60;blocker&#x60; currently only supports &#x60;PARENT&#x60;.  *  &#x60;statusIds&#x60; a comma-separated list of status IDs.  ##### Previous status validator #####  A validator that checks if an issue has transitioned through specified previous status(es) before allowing the current transition to occur.      {        \&quot;ruleKey\&quot;: \&quot;system:previous-status-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;previousStatusIds\&quot;: \&quot;10014\&quot;,          \&quot;mostRecentStatusOnly\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;previousStatusIds&#x60; a comma-separated list of status IDs, currently only support one ID.  *  &#x60;mostRecentStatusOnly&#x60; when &#x60;true&#x60; only considers the most recent status for the condition evaluation. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ##### Validate a field value #####  A validation that ensures a specific field&#x27;s value meets the defined criteria before allowing an issue to transition in the workflow.  Depending on the rule type, the result will vary:  ###### Field required ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldRequired\&quot;,          \&quot;fieldsRequired\&quot;: \&quot;assignee\&quot;,          \&quot;ignoreContext\&quot;: \&quot;true\&quot;,          \&quot;errorMessage\&quot;: \&quot;An assignee must be set!\&quot;        }      }  Parameters:   *  &#x60;fieldsRequired&#x60; the ID of the field that is required. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;ignoreContext&#x60; controls the impact of context settings on field validation. When set to &#x60;true&#x60;, the validator doesn&#x27;t check a required field if its context isn&#x27;t configured for the current issue. When set to &#x60;false&#x60;, the validator requires a field even if its context is invalid. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;errorMessage&#x60; is the error message to display if the user does not provide a value during the transition. A default error message will be shown if you don&#x27;t provide one (Optional).  ###### Field changed ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldChanged\&quot;,          \&quot;groupsExemptFromValidation\&quot;: \&quot;6862ac20-8672-4f68-896d-4854f5efb79e\&quot;,          \&quot;fieldKey\&quot;: \&quot;versions\&quot;,          \&quot;errorMessage\&quot;: \&quot;Affect versions must be modified before transition\&quot;        }      }  Parameters:   *  &#x60;groupsExemptFromValidation&#x60; a comma-separated list of group IDs to be exempt from the validation.  *  &#x60;fieldKey&#x60; the ID of the field that has changed. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;errorMessage&#x60; the error message to display if the user does not provide a value during the transition. A default error message will be shown if you don&#x27;t provide one (Optional).  ###### Field has a single value ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldHasSingleValue\&quot;,          \&quot;fieldKey\&quot;: \&quot;created\&quot;,          \&quot;excludeSubtasks\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;fieldKey&#x60; the ID of the field to validate. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;excludeSubtasks&#x60; Option to exclude values copied from sub-tasks. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ###### Field matches regular expression ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;fieldMatchesRegularExpression\&quot;,          \&quot;regexp\&quot;: \&quot;[0-9]{4}\&quot;,          \&quot;fieldKey\&quot;: \&quot;description\&quot;        }      }  Parameters:   *  &#x60;regexp&#x60; the regular expression used to validate the field\\\\u2019s content.  *  &#x60;fieldKey&#x60; the ID of the field to validate. For a custom field, it would look like &#x60;customfield_123&#x60;.  ###### Date field comparison ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;dateFieldComparison\&quot;,          \&quot;date1FieldKey\&quot;: \&quot;duedate\&quot;,          \&quot;date2FieldKey\&quot;: \&quot;customfield_10054\&quot;,          \&quot;includeTime\&quot;: \&quot;true\&quot;,          \&quot;conditionSelected\&quot;: \&quot;&gt;&#x3D;\&quot;        }      }  Parameters:   *  &#x60;date1FieldKey&#x60; the ID of the first field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;date2FieldKey&#x60; the ID of the second field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;includeTime&#x60; if &#x60;true&#x60;, compares both date and time. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;conditionSelected&#x60; the condition to compare with. Allowed values: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, &#x60;!&#x3D;&#x60;.  ###### Date range comparison ######      {        \&quot;ruleKey\&quot;: \&quot;system:validate-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;ruleType\&quot;: \&quot;windowDateComparison\&quot;,          \&quot;date1FieldKey\&quot;: \&quot;customfield_10009\&quot;,          \&quot;date2FieldKey\&quot;: \&quot;customfield_10054\&quot;,          \&quot;numberOfDays\&quot;: \&quot;3\&quot;        }      }  Parameters:   *  &#x60;date1FieldKey&#x60; the ID of the first field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;date2FieldKey&#x60; the ID of the second field to compare. For a custom field, it would look like &#x60;customfield_123&#x60;.  *  &#x60;numberOfDays&#x60; maximum number of days past the reference date (&#x60;date2FieldKey&#x60;) to pass validation.  This rule is composed by aggregating the following legacy rules:   *  FieldRequiredValidator  *  FieldChangedValidator  *  FieldHasSingleValueValidator  *  RegexpFieldValidator  *  DateFieldValidator  *  WindowsDateValidator  ##### Pro forma: Forms attached validator #####  Validates that one or more forms are attached to the issue.      {        \&quot;ruleKey\&quot; : \&quot;system:proforma-forms-attached\&quot;        \&quot;parameters\&quot; : {}      }  ##### Proforma: Forms submitted validator #####  Validates that all forms attached to the issue have been submitted.      {        \&quot;ruleKey\&quot; : \&quot;system:proforma-forms-submitted\&quot;        \&quot;parameters\&quot; : {}      }  #### Conditions ####  Conditions enable workflow rules that govern whether a transition can execute.  ##### Check field value #####  A condition rule evaluates as true if a specific field&#x27;s value meets the defined criteria. This rule ensures that an issue can only transition to the next step in the workflow if the field&#x27;s value matches the desired condition.      {        \&quot;ruleKey\&quot;: \&quot;system:check-field-value\&quot;,        \&quot;parameters\&quot;: {          \&quot;fieldId\&quot;: \&quot;description\&quot;,          \&quot;fieldValue\&quot;: \&quot;[\\\&quot;Done\\\&quot;]\&quot;,          \&quot;comparator\&quot;: \&quot;&#x3D;\&quot;,          \&quot;comparisonType\&quot;: \&quot;STRING\&quot;        }      }  Parameters:   *  &#x60;fieldId&#x60; The ID of the field to check the value of. For non-system fields, it will look like &#x60;customfield_123&#x60;. Note: &#x60;fieldId&#x60; is used interchangeably with the idea of &#x60;fieldKey&#x60; here, they refer to the same field.  *  &#x60;fieldValue&#x60; the list of values to check against the field\\\\u2019s value.  *  &#x60;comparator&#x60; The comparison logic. Allowed values: &#x60;&gt;&#x60;, &#x60;&gt;&#x3D;&#x60;, &#x60;&#x3D;&#x60;, &#x60;&lt;&#x3D;&#x60;, &#x60;&lt;&#x60;, &#x60;!&#x3D;&#x60;.  *  &#x60;comparisonType&#x60; The type of data being compared. Allowed values: &#x60;STRING&#x60;, &#x60;NUMBER&#x60;, &#x60;DATE&#x60;, &#x60;DATE_WITHOUT_TIME&#x60;, &#x60;OPTIONID&#x60;.  ##### Restrict issue transition #####  This rule ensures that issue transitions are restricted based on user accounts, roles, group memberships, and permissions, maintaining control over who can transition an issue. This condition evaluates as &#x60;true&#x60; if any of the following criteria is met.      {        \&quot;ruleKey\&quot;: \&quot;system:restrict-issue-transition\&quot;,        \&quot;parameters\&quot;: {          \&quot;accountIds\&quot;: \&quot;allow-reporter,5e68ac137d64450d01a77fa0\&quot;,          \&quot;roleIds\&quot;: \&quot;10002,10004\&quot;,          \&quot;groupIds\&quot;: \&quot;703ff44a-7dc8-4f4b-9aa6-a65bf3574fa4\&quot;,          \&quot;permissionKeys\&quot;: \&quot;ADMINISTER_PROJECTS\&quot;,          \&quot;groupCustomFields\&quot;: \&quot;customfield_10028\&quot;,          \&quot;allowUserCustomFields\&quot;: \&quot;customfield_10072,customfield_10144,customfield_10007\&quot;,          \&quot;denyUserCustomFields\&quot;: \&quot;customfield_10107\&quot;        }      }  Parameters:   *  &#x60;accountIds&#x60; a comma-separated list of the user account IDs. It also allows generic values like: &#x60;allow-assignee&#x60;, &#x60;allow-reporter&#x60;, and &#x60;accountIds&#x60; Note: This is only supported in team-managed projects  *  &#x60;roleIds&#x60; a comma-separated list of role IDs.  *  &#x60;groupIds&#x60; a comma-separated list of group IDs.  *  &#x60;permissionKeys&#x60; a comma-separated list of permission keys. Allowed values: [built-in Jira permissions](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-permission-schemes/#built-in-permissions).  *  &#x60;groupCustomFields&#x60; a comma-separated list of group custom field IDs.  *  &#x60;allowUserCustomFields&#x60; a comma-separated list of user custom field IDs to allow for issue transition.  *  &#x60;denyUserCustomFields&#x60; a comma-separated list of user custom field IDs to deny for issue transition.  This rule is composed by aggregating the following legacy rules:   *  AllowOnlyAssignee  *  AllowOnlyReporter  *  InAnyProjectRoleCondition  *  InProjectRoleCondition  *  UserInAnyGroupCondition  *  UserInGroupCondition  *  PermissionCondtion  *  InGroupCFCondition  *  UserIsInCustomFieldCondition  ##### Previous status condition #####  A condition that evaluates based on an issue&#x27;s previous status(es) and specific criteria.      {        \&quot;ruleKey\&quot; : \&quot;system:previous-status-condition\&quot;        \&quot;parameters\&quot; : {          \&quot;previousStatusIds\&quot; : \&quot;10004\&quot;,          \&quot;not\&quot;: \&quot;true\&quot;,          \&quot;mostRecentStatusOnly\&quot; : \&quot;true\&quot;,          \&quot;includeCurrentStatus\&quot;: \&quot;true\&quot;,          \&quot;ignoreLoopTransitions\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;previousStatusIds&#x60; a comma-separated list of status IDs, current only support one ID.  *  &#x60;not&#x60; indicates if the condition should be reversed. When &#x60;true&#x60; it checks that the issue has not been in the selected statuses. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;mostRecentStatusOnly&#x60; when true only considers the most recent status for the condition evaluation. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;includeCurrentStatus&#x60; includes the current status when evaluating if the issue has been through the selected statuses. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;ignoreLoopTransitions&#x60; ignore loop transitions. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ##### Parent or child blocking condition #####  A condition to block the parent\\\\u2019s issue transition depending on the child\\\\u2019s issue status.      {        \&quot;ruleKey\&quot; : \&quot;system:parent-or-child-blocking-condition\&quot;        \&quot;parameters\&quot; : {          \&quot;blocker\&quot; : \&quot;CHILD\&quot;,          \&quot;statusIds\&quot; : \&quot;1,2,3\&quot;        }      }  Parameters:   *  &#x60;blocker&#x60; currently only supports &#x60;CHILD&#x60;.  *  &#x60;statusIds&#x60; a comma-separated list of status IDs.  ##### Separation of duties #####  A condition preventing the user from performing, if the user has already performed a transition on the issue.      {        \&quot;ruleKey\&quot;: \&quot;system:separation-of-duties\&quot;,        \&quot;parameters\&quot;: {          \&quot;fromStatusId\&quot;: \&quot;10161\&quot;,          \&quot;toStatusId\&quot;: \&quot;10160\&quot;        }      }  Parameters:   *  &#x60;fromStatusId&#x60; represents the status ID from which the issue is transitioning. It ensures that the user performing the current transition has not performed any actions when the issue was in the specified status.  *  &#x60;toStatusId&#x60; represents the status ID to which the issue is transitioning. It ensures that the user performing the current transition is not the same user who has previously transitioned the issue.  ##### Restrict transitions #####  A condition preventing all users from transitioning the issue can also optionally include APIs as well.      {        \&quot;ruleKey\&quot;: \&quot;system:restrict-from-all-users\&quot;,        \&quot;parameters\&quot;: {          \&quot;restrictMode\&quot;: \&quot;users\&quot;        }      }  Parameters:   *  &#x60;restrictMode&#x60; restricts the issue transition including/excluding APIs. Allowed values: &#x60;\&quot;users\&quot;&#x60;, &#x60;\&quot;usersAndAPI\&quot;&#x60;.  ##### Jira Service Management block until approved #####  Block an issue transition until approval. Note: This is only supported in team-managed projects.      {        \&quot;ruleKey\&quot;: \&quot;system:jsd-approvals-block-until-approved\&quot;,        \&quot;parameters\&quot;: {          \&quot;approvalConfigurationJson\&quot;: \&quot;{\&quot;statusExternalUuid...}\&quot;        }      }  Parameters:   *  &#x60;approvalConfigurationJson&#x60; a stringified JSON holding the Jira Service Management approval configuration.  ##### Jira Service Management block until rejected #####  Block an issue transition until rejected. Note: This is only supported in team-managed projects.      {        \&quot;ruleKey\&quot;: \&quot;system:jsd-approvals-block-until-rejected\&quot;,        \&quot;parameters\&quot;: {          \&quot;approvalConfigurationJson\&quot;: \&quot;{\&quot;statusExternalUuid...}\&quot;        }      }  Parameters:   *  &#x60;approvalConfigurationJson&#x60; a stringified JSON holding the Jira Service Management approval configuration.  ##### Block in progress approval #####  Condition to block issue transition if there is pending approval. Note: This is only supported in company-managed projects.      {        \&quot;ruleKey\&quot;: \&quot;system:block-in-progress-approval\&quot;,        \&quot;parameters\&quot;: {}      }  #### Post functions ####  Post functions carry out any additional processing required after a workflow transition is executed.  ##### Change assignee #####  A post function rule that changes the assignee of an issue after a transition.      {        \&quot;ruleKey\&quot;: \&quot;system:change-assignee\&quot;,        \&quot;parameters\&quot;: {          \&quot;type\&quot;: \&quot;to-selected-user\&quot;,          \&quot;accountId\&quot;: \&quot;example-account-id\&quot;        }      }  Parameters:   *  &#x60;type&#x60; the parameter used to determine the new assignee. Allowed values: &#x60;to-selected-user&#x60;, &#x60;to-unassigned&#x60;, &#x60;to-current-user&#x60;, &#x60;to-current-user&#x60;, &#x60;to-default-user&#x60;, &#x60;to-default-user&#x60;  *  &#x60;accountId&#x60; the account ID of the user to assign the issue to. This parameter is required only when the type is &#x60;\&quot;to-selected-user\&quot;&#x60;.  ##### Copy field value #####  A post function that automates the process of copying values between fields during a specific transition, ensuring data consistency and reducing manual effort.      {        \&quot;ruleKey\&quot;: \&quot;system:copy-value-from-other-field\&quot;,        \&quot;parameters\&quot;: {          \&quot;sourceFieldKey\&quot;: \&quot;description\&quot;,          \&quot;targetFieldKey\&quot;: \&quot;components\&quot;,          \&quot;issueSource\&quot;: \&quot;SAME\&quot;        }      }  Parameters:   *  &#x60;sourceFieldKey&#x60; the field key to copy from. For a custom field, it would look like &#x60;customfield_123&#x60;  *  &#x60;targetFieldKey&#x60; the field key to copy to. For a custom field, it would look like &#x60;customfield_123&#x60;  *  &#x60;issueSource&#x60; &#x60;SAME&#x60; or &#x60;PARENT&#x60;. Defaults to &#x60;SAME&#x60; if no value is provided.  ##### Update field #####  A post function that updates or appends a specific field with the given value.      {        \&quot;ruleKey\&quot;: \&quot;system:update-field\&quot;,        \&quot;parameters\&quot;: {          \&quot;field\&quot;: \&quot;customfield_10056\&quot;,          \&quot;value\&quot;: \&quot;asdf\&quot;,          \&quot;mode\&quot;: \&quot;append\&quot;        }      }  Parameters:   *  &#x60;field&#x60; the ID of the field to update. For a custom field, it would look like &#x60;customfield_123&#x60;  *  &#x60;value&#x60; the value to update the field with.  *  &#x60;mode&#x60; &#x60;append&#x60; or &#x60;replace&#x60;. Determines if a value will be appended to the current value, or if the current value will be replaced.  ##### Trigger webhook #####  A post function that automatically triggers a predefined webhook when a transition occurs in the workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:trigger-webhook\&quot;,        \&quot;parameters\&quot;: {          \&quot;webhookId\&quot;: \&quot;1\&quot;        }      }  Parameters:   *  &#x60;webhookId&#x60; the ID of the webhook.  #### Screen ####  ##### Remind people to update fields #####  A screen rule that prompts users to update a specific field when they interact with an issue screen during a transition. This rule is useful for ensuring that users provide or modify necessary information before moving an issue to the next step in the workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:remind-people-to-update-fields\&quot;,        \&quot;params\&quot;: {          \&quot;remindingFieldIds\&quot;: \&quot;assignee,customfield_10025\&quot;,          \&quot;remindingMessage\&quot;: \&quot;The message\&quot;,          \&quot;remindingAlwaysAsk\&quot;: \&quot;true\&quot;        }      }  Parameters:   *  &#x60;remindingFieldIds&#x60; a comma-separated list of field IDs. Note: &#x60;fieldId&#x60; is used interchangeably with the idea of &#x60;fieldKey&#x60; here, they refer to the same field.  *  &#x60;remindingMessage&#x60; the message to display when prompting the users to update the fields.  *  &#x60;remindingAlwaysAsk&#x60; always remind to update fields. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  ##### Shared transition screen #####  A common screen that is shared between transitions in a workflow.      {        \&quot;ruleKey\&quot;: \&quot;system:transition-screen\&quot;,        \&quot;params\&quot;: {          \&quot;screenId\&quot;: \&quot;3\&quot;        }      }  Parameters:   *  &#x60;screenId&#x60; the ID of the screen.  #### Connect &amp; Forge ####  ##### Connect rules #####  Validator/Condition/Post function for Connect app.      {        \&quot;ruleKey\&quot;: \&quot;connect:expression-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;appKey\&quot;: \&quot;com.atlassian.app\&quot;,          \&quot;config\&quot;: \&quot;\&quot;,          \&quot;id\&quot;: \&quot;90ce590f-e90c-4cd3-8281-165ce41f2ac3\&quot;,          \&quot;disabled\&quot;: \&quot;false\&quot;,          \&quot;tag\&quot;: \&quot;\&quot;        }      }  Parameters:   *  &#x60;ruleKey&#x60; Validator: &#x60;connect:expression-validator&#x60;, Condition: &#x60;connect:expression-condition&#x60;, and Post function: &#x60;connect:remote-workflow-function&#x60;  *  &#x60;appKey&#x60; the reference to the Connect app  *  &#x60;config&#x60; a JSON payload string describing the configuration  *  &#x60;id&#x60; the ID of the rule  *  &#x60;disabled&#x60; determine if the Connect app is disabled. Allowed values: &#x60;true&#x60;, &#x60;false&#x60;.  *  &#x60;tag&#x60; additional tags for the Connect app  ##### Forge rules #####  Validator/Condition/Post function for Forge app.      {        \&quot;ruleKey\&quot;: \&quot;forge:expression-validator\&quot;,        \&quot;parameters\&quot;: {          \&quot;key\&quot;: \&quot;ari:cloud:ecosystem::extension/{appId}/{environmentId}/static/{moduleKey}\&quot;,          \&quot;config\&quot;: \&quot;{\&quot;searchString\&quot;:\&quot;workflow validator\&quot;}\&quot;,          \&quot;id\&quot;: \&quot;a865ddf6-bb3f-4a7b-9540-c2f8b3f9f6c2\&quot;        }      }  Parameters:   *  &#x60;ruleKey&#x60; Validator: &#x60;forge:expression-validator&#x60;, Condition: &#x60;forge:expression-condition&#x60;, and Post function: &#x60;forge:workflow-post-function&#x60;  *  &#x60;key&#x60; the identifier for the Forge app  *  &#x60;config&#x60; the persistent stringified JSON configuration for the Forge rule  *  &#x60;id&#x60; the ID of the Forge rule
     * <p><b>200</b> - Returned if the request is successful.
     * <p><b>400</b> - Returned if the request is not valid.
     * <p><b>401</b> - Returned if the authentication credentials are incorrect or missing, or the caller doesn&#x27;t have permissions to perform the operation.
     * @param workflowId  (optional)
     * @param projectId  (optional)
     * @param issueTypeId  (optional)
     * @return ResponseEntity&lt;WorkflowCapabilities&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowCapabilities> workflowCapabilitiesWithHttpInfo(String workflowId, String projectId, String issueTypeId) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/rest/api/3/workflows/capabilities").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "workflowId", workflowId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectId", projectId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "issueTypeId", issueTypeId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames =ApiClient.AUTH_NAMES;

        ParameterizedTypeReference<WorkflowCapabilities> returnType = new ParameterizedTypeReference<WorkflowCapabilities>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
