# JexpJqlIssues

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**maxResults** | **Integer** | The maximum number of issues to return from the JQL query. Inspect &#x60;meta.issues.jql.maxResults&#x60; in the response to ensure the maximum value has not been exceeded. |  [optional]
**query** | **String** | The JQL query. |  [optional]
**startAt** | **Long** | The index of the first issue to return from the JQL query. |  [optional]
**validation** | [**ValidationEnum**](#ValidationEnum) | Determines how to validate the JQL query and treat the validation results. |  [optional]

<a name="ValidationEnum"></a>
## Enum: ValidationEnum
Name | Value
---- | -----
STRICT | &quot;strict&quot;
WARN | &quot;warn&quot;
NONE | &quot;none&quot;
