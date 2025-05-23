# JiraExpressionEvalContextBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**board** | **Long** | The ID of the board that is available under the &#x60;board&#x60; variable when evaluating the expression. |  [optional]
**custom** | [**List&lt;CustomContextVariable&gt;**](CustomContextVariable.md) | Custom context variables and their types. These variable types are available for use in a custom context:   *  &#x60;user&#x60;: A [user](https://developer.atlassian.com/cloud/jira/platform/jira-expressions-type-reference#user) specified as an Atlassian account ID.  *  &#x60;issue&#x60;: An [issue](https://developer.atlassian.com/cloud/jira/platform/jira-expressions-type-reference#issue) specified by ID or key. All the fields of the issue object are available in the Jira expression.  *  &#x60;json&#x60;: A JSON object containing custom content.  *  &#x60;list&#x60;: A JSON list of &#x60;user&#x60;, &#x60;issue&#x60;, or &#x60;json&#x60; variable types. |  [optional]
**customerRequest** | **Long** | The ID of the customer request that is available under the &#x60;customerRequest&#x60; variable when evaluating the expression. This is the same as the ID of the underlying Jira issue, but the customer request context variable will have a different type. |  [optional]
**issue** | **AllOfJiraExpressionEvalContextBeanIssue** | The issue that is available under the &#x60;issue&#x60; variable when evaluating the expression. |  [optional]
**issues** | **AllOfJiraExpressionEvalContextBeanIssues** | The collection of issues that is available under the &#x60;issues&#x60; variable when evaluating the expression. |  [optional]
**project** | **AllOfJiraExpressionEvalContextBeanProject** | The project that is available under the &#x60;project&#x60; variable when evaluating the expression. |  [optional]
**serviceDesk** | **Long** | The ID of the service desk that is available under the &#x60;serviceDesk&#x60; variable when evaluating the expression. |  [optional]
**sprint** | **Long** | The ID of the sprint that is available under the &#x60;sprint&#x60; variable when evaluating the expression. |  [optional]
