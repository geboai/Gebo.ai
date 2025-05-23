# CreateProjectDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**assigneeType** | [**AssigneeTypeEnum**](#AssigneeTypeEnum) | The default assignee when creating issues for this project. |  [optional]
**avatarId** | **Long** | An integer value for the project&#x27;s avatar. |  [optional]
**categoryId** | **Long** | The ID of the project&#x27;s category. A complete list of category IDs is found using the [Get all project categories](#api-rest-api-3-projectCategory-get) operation. |  [optional]
**description** | **String** | A brief description of the project. |  [optional]
**fieldConfigurationScheme** | **Long** | The ID of the field configuration scheme for the project. Use the [Get all field configuration schemes](#api-rest-api-3-fieldconfigurationscheme-get) operation to get a list of field configuration scheme IDs. If you specify the field configuration scheme you cannot specify the project template key. |  [optional]
**issueSecurityScheme** | **Long** | The ID of the issue security scheme for the project, which enables you to control who can and cannot view issues. Use the [Get issue security schemes](#api-rest-api-3-issuesecurityschemes-get) resource to get all issue security scheme IDs. |  [optional]
**issueTypeScheme** | **Long** | The ID of the issue type scheme for the project. Use the [Get all issue type schemes](#api-rest-api-3-issuetypescheme-get) operation to get a list of issue type scheme IDs. If you specify the issue type scheme you cannot specify the project template key. |  [optional]
**issueTypeScreenScheme** | **Long** | The ID of the issue type screen scheme for the project. Use the [Get all issue type screen schemes](#api-rest-api-3-issuetypescreenscheme-get) operation to get a list of issue type screen scheme IDs. If you specify the issue type screen scheme you cannot specify the project template key. |  [optional]
**key** | **String** | Project keys must be unique and start with an uppercase letter followed by one or more uppercase alphanumeric characters. The maximum length is 10 characters. | 
**lead** | **String** | This parameter is deprecated because of privacy changes. Use &#x60;leadAccountId&#x60; instead. See the [migration guide](https://developer.atlassian.com/cloud/jira/platform/deprecation-notice-user-privacy-api-migration-guide/) for details. The user name of the project lead. Either &#x60;lead&#x60; or &#x60;leadAccountId&#x60; must be set when creating a project. Cannot be provided with &#x60;leadAccountId&#x60;. |  [optional]
**leadAccountId** | **String** | The account ID of the project lead. Either &#x60;lead&#x60; or &#x60;leadAccountId&#x60; must be set when creating a project. Cannot be provided with &#x60;lead&#x60;. |  [optional]
**name** | **String** | The name of the project. | 
**notificationScheme** | **Long** | The ID of the notification scheme for the project. Use the [Get notification schemes](#api-rest-api-3-notificationscheme-get) resource to get a list of notification scheme IDs. |  [optional]
**permissionScheme** | **Long** | The ID of the permission scheme for the project. Use the [Get all permission schemes](#api-rest-api-3-permissionscheme-get) resource to see a list of all permission scheme IDs. |  [optional]
**projectTemplateKey** | [**ProjectTemplateKeyEnum**](#ProjectTemplateKeyEnum) | A predefined configuration for a project. The type of the &#x60;projectTemplateKey&#x60; must match with the type of the &#x60;projectTypeKey&#x60;. |  [optional]
**projectTypeKey** | [**ProjectTypeKeyEnum**](#ProjectTypeKeyEnum) | The [project type](https://confluence.atlassian.com/x/GwiiLQ#Jiraapplicationsoverview-Productfeaturesandprojecttypes), which defines the application-specific feature set. If you don&#x27;t specify the project template you have to specify the project type. |  [optional]
**url** | **String** | A link to information about this project, such as project documentation |  [optional]
**workflowScheme** | **Long** | The ID of the workflow scheme for the project. Use the [Get all workflow schemes](#api-rest-api-3-workflowscheme-get) operation to get a list of workflow scheme IDs. If you specify the workflow scheme you cannot specify the project template key. |  [optional]

<a name="AssigneeTypeEnum"></a>
## Enum: AssigneeTypeEnum
Name | Value
---- | -----
PROJECT_LEAD | &quot;PROJECT_LEAD&quot;
UNASSIGNED | &quot;UNASSIGNED&quot;

<a name="ProjectTemplateKeyEnum"></a>
## Enum: ProjectTemplateKeyEnum
Name | Value
---- | -----
PYXIS_GREENHOPPER_JIRA_GH_SIMPLIFIED_AGILITY_KANBAN | &quot;com.pyxis.greenhopper.jira:gh-simplified-agility-kanban&quot;
PYXIS_GREENHOPPER_JIRA_GH_SIMPLIFIED_AGILITY_SCRUM | &quot;com.pyxis.greenhopper.jira:gh-simplified-agility-scrum&quot;
PYXIS_GREENHOPPER_JIRA_GH_SIMPLIFIED_BASIC | &quot;com.pyxis.greenhopper.jira:gh-simplified-basic&quot;
PYXIS_GREENHOPPER_JIRA_GH_SIMPLIFIED_KANBAN_CLASSIC | &quot;com.pyxis.greenhopper.jira:gh-simplified-kanban-classic&quot;
PYXIS_GREENHOPPER_JIRA_GH_SIMPLIFIED_SCRUM_CLASSIC | &quot;com.pyxis.greenhopper.jira:gh-simplified-scrum-classic&quot;
PYXIS_GREENHOPPER_JIRA_GH_CROSS_TEAM_TEMPLATE | &quot;com.pyxis.greenhopper.jira:gh-cross-team-template&quot;
PYXIS_GREENHOPPER_JIRA_GH_CROSS_TEAM_PLANNING_TEMPLATE | &quot;com.pyxis.greenhopper.jira:gh-cross-team-planning-template&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_IT_SERVICE_MANAGEMENT | &quot;com.atlassian.servicedesk:simplified-it-service-management&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_IT_SERVICE_MANAGEMENT_BASIC | &quot;com.atlassian.servicedesk:simplified-it-service-management-basic&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_IT_SERVICE_MANAGEMENT_OPERATIONS | &quot;com.atlassian.servicedesk:simplified-it-service-management-operations&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_GENERAL_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-general-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_GENERAL_SERVICE_DESK_IT | &quot;com.atlassian.servicedesk:simplified-general-service-desk-it&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_GENERAL_SERVICE_DESK_BUSINESS | &quot;com.atlassian.servicedesk:simplified-general-service-desk-business&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_INTERNAL_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-internal-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_EXTERNAL_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-external-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_HR_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-hr-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_FACILITIES_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-facilities-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_LEGAL_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-legal-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_MARKETING_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-marketing-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_FINANCE_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-finance-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_ANALYTICS_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-analytics-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_DESIGN_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-design-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_SALES_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-sales-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_HALP_SERVICE_DESK | &quot;com.atlassian.servicedesk:simplified-halp-service-desk&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_BLANK_PROJECT_IT | &quot;com.atlassian.servicedesk:simplified-blank-project-it&quot;
ATLASSIAN_SERVICEDESK_SIMPLIFIED_BLANK_PROJECT_BUSINESS | &quot;com.atlassian.servicedesk:simplified-blank-project-business&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_IT_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-it-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_HR_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-hr-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_LEGAL_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-legal-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_MARKETING_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-marketing-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_FACILITIES_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-facilities-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_GENERAL_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-general-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_GENERAL_IT_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-general-it-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_GENERAL_BUSINESS_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-general-business-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_ANALYTICS_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-analytics-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_FINANCE_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-finance-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_DESIGN_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-design-service-desk&quot;
ATLASSIAN_SERVICEDESK_NEXT_GEN_SALES_SERVICE_DESK | &quot;com.atlassian.servicedesk:next-gen-sales-service-desk&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_CONTENT_MANAGEMENT | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-content-management&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_DOCUMENT_APPROVAL | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-document-approval&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_LEAD_TRACKING | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-lead-tracking&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_PROCESS_CONTROL | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-process-control&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_PROCUREMENT | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-procurement&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_PROJECT_MANAGEMENT | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-project-management&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_RECRUITMENT | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-recruitment&quot;
ATLASSIAN_JIRA_CORE_PROJECT_TEMPLATES_JIRA_CORE_SIMPLIFIED_TASK_ | &quot;com.atlassian.jira-core-project-templates:jira-core-simplified-task-&quot;

<a name="ProjectTypeKeyEnum"></a>
## Enum: ProjectTypeKeyEnum
Name | Value
---- | -----
SOFTWARE | &quot;software&quot;
SERVICE_DESK | &quot;service_desk&quot;
BUSINESS | &quot;business&quot;
