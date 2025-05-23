# ProjectFeature

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**feature** | **String** | The key of the feature. |  [optional]
**imageUri** | **String** | URI for the image representing the feature. |  [optional]
**localisedDescription** | **String** | Localized display description for the feature. |  [optional]
**localisedName** | **String** | Localized display name for the feature. |  [optional]
**prerequisites** | **List&lt;String&gt;** | List of keys of the features required to enable the feature. |  [optional]
**projectId** | **Long** | The ID of the project. |  [optional]
**state** | [**StateEnum**](#StateEnum) | The state of the feature. When updating the state of a feature, only ENABLED and DISABLED are supported. Responses can contain all values |  [optional]
**toggleLocked** | **Boolean** | Whether the state of the feature can be updated. |  [optional]

<a name="StateEnum"></a>
## Enum: StateEnum
Name | Value
---- | -----
ENABLED | &quot;ENABLED&quot;
DISABLED | &quot;DISABLED&quot;
COMING_SOON | &quot;COMING_SOON&quot;
