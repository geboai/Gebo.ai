# Project

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**archived** | **Boolean** | Whether the project is archived. |  [optional]
**archivedBy** | **AllOfProjectArchivedBy** | The user who archived the project. |  [optional]
**archivedDate** | [**Date**](Date.md) | The date when the project was archived. |  [optional]
**assigneeType** | [**AssigneeTypeEnum**](#AssigneeTypeEnum) | The default assignee when creating issues for this project. |  [optional]
**avatarUrls** | **AllOfProjectAvatarUrls** | The URLs of the project&#x27;s avatars. |  [optional]
**components** | [**List&lt;ProjectComponent&gt;**](ProjectComponent.md) | List of the components contained in the project. |  [optional]
**deleted** | **Boolean** | Whether the project is marked as deleted. |  [optional]
**deletedBy** | **AllOfProjectDeletedBy** | The user who marked the project as deleted. |  [optional]
**deletedDate** | [**Date**](Date.md) | The date when the project was marked as deleted. |  [optional]
**description** | **String** | A brief description of the project. |  [optional]
**email** | **String** | An email address associated with the project. |  [optional]
**expand** | **String** | Expand options that include additional project details in the response. |  [optional]
**favourite** | **Boolean** | Whether the project is selected as a favorite. |  [optional]
**id** | **String** | The ID of the project. |  [optional]
**insight** | **AllOfProjectInsight** | Insights about the project. |  [optional]
**isPrivate** | **Boolean** | Whether the project is private from the user&#x27;s perspective. This means the user can&#x27;t see the project or any associated issues. |  [optional]
**issueTypeHierarchy** | **AllOfProjectIssueTypeHierarchy** | The issue type hierarchy for the project. |  [optional]
**issueTypes** | [**List&lt;IssueTypeDetails&gt;**](IssueTypeDetails.md) | List of the issue types available in the project. |  [optional]
**key** | **String** | The key of the project. |  [optional]
**landingPageInfo** | **AllOfProjectLandingPageInfo** | The project landing page info. |  [optional]
**lead** | **AllOfProjectLead** | The username of the project lead. |  [optional]
**name** | **String** | The name of the project. |  [optional]
**permissions** | **AllOfProjectPermissions** | User permissions on the project |  [optional]
**projectCategory** | **AllOfProjectProjectCategory** | The category the project belongs to. |  [optional]
**projectTypeKey** | [**ProjectTypeKeyEnum**](#ProjectTypeKeyEnum) | The [project type](https://confluence.atlassian.com/x/GwiiLQ#Jiraapplicationsoverview-Productfeaturesandprojecttypes) of the project. |  [optional]
**properties** | **Map&lt;String, Object&gt;** | Map of project properties |  [optional]
**retentionTillDate** | [**Date**](Date.md) | The date when the project is deleted permanently. |  [optional]
**roles** | **Map&lt;String, String&gt;** | The name and self URL for each role defined in the project. For more information, see [Create project role](#api-rest-api-3-role-post). |  [optional]
**self** | **String** | The URL of the project details. |  [optional]
**simplified** | **Boolean** | Whether the project is simplified. |  [optional]
**style** | [**StyleEnum**](#StyleEnum) | The type of the project. |  [optional]
**url** | **String** | A link to information about this project, such as project documentation. |  [optional]
**uuid** | [**UUID**](UUID.md) | Unique ID for next-gen projects. |  [optional]
**versions** | [**List&lt;Version&gt;**](Version.md) | The versions defined in the project. For more information, see [Create version](#api-rest-api-3-version-post). |  [optional]

<a name="AssigneeTypeEnum"></a>
## Enum: AssigneeTypeEnum
Name | Value
---- | -----
PROJECT_LEAD | &quot;PROJECT_LEAD&quot;
UNASSIGNED | &quot;UNASSIGNED&quot;

<a name="ProjectTypeKeyEnum"></a>
## Enum: ProjectTypeKeyEnum
Name | Value
---- | -----
SOFTWARE | &quot;software&quot;
SERVICE_DESK | &quot;service_desk&quot;
BUSINESS | &quot;business&quot;

<a name="StyleEnum"></a>
## Enum: StyleEnum
Name | Value
---- | -----
CLASSIC | &quot;classic&quot;
NEXT_GEN | &quot;next-gen&quot;
