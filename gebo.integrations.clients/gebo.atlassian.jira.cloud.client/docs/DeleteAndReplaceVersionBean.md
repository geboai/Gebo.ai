# DeleteAndReplaceVersionBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**customFieldReplacementList** | [**List&lt;CustomFieldReplacement&gt;**](CustomFieldReplacement.md) | An array of custom field IDs (&#x60;customFieldId&#x60;) and version IDs (&#x60;moveTo&#x60;) to update when the fields contain the deleted version. |  [optional]
**moveAffectedIssuesTo** | **Long** | The ID of the version to update &#x60;affectedVersion&#x60; to when the field contains the deleted version. |  [optional]
**moveFixIssuesTo** | **Long** | The ID of the version to update &#x60;fixVersion&#x60; to when the field contains the deleted version. |  [optional]
