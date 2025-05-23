# Comment

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**author** | **AllOfCommentAuthor** | The ID of the user who created the comment. |  [optional]
**body** | **Object** | The comment text in [Atlassian Document Format](https://developer.atlassian.com/cloud/jira/platform/apis/document/structure/). |  [optional]
**created** | [**Date**](Date.md) | The date and time at which the comment was created. |  [optional]
**id** | **String** | The ID of the comment. |  [optional]
**jsdAuthorCanSeeRequest** | **Boolean** | Whether the comment was added from an email sent by a person who is not part of the issue. See [Allow external emails to be added as comments on issues](https://support.atlassian.com/jira-service-management-cloud/docs/allow-external-emails-to-be-added-as-comments-on-issues/)for information on setting up this feature. |  [optional]
**jsdPublic** | **Boolean** | Whether the comment is visible in Jira Service Desk. Defaults to true when comments are created in the Jira Cloud Platform. This includes when the site doesn&#x27;t use Jira Service Desk or the project isn&#x27;t a Jira Service Desk project and, therefore, there is no Jira Service Desk for the issue to be visible on. To create a comment with its visibility in Jira Service Desk set to false, use the Jira Service Desk REST API [Create request comment](https://developer.atlassian.com/cloud/jira/service-desk/rest/#api-rest-servicedeskapi-request-issueIdOrKey-comment-post) operation. |  [optional]
**properties** | [**List&lt;EntityProperty&gt;**](EntityProperty.md) | A list of comment properties. Optional on create and update. |  [optional]
**renderedBody** | **String** | The rendered version of the comment. |  [optional]
**self** | **String** | The URL of the comment. |  [optional]
**updateAuthor** | **AllOfCommentUpdateAuthor** | The ID of the user who updated the comment last. |  [optional]
**updated** | [**Date**](Date.md) | The date and time at which the comment was updated last. |  [optional]
**visibility** | **AllOfCommentVisibility** | The group or role to which this comment is visible. Optional on create and update. |  [optional]
