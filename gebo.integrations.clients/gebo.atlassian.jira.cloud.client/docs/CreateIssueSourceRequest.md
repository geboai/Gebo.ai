# CreateIssueSourceRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**TypeEnum**](#TypeEnum) | The issue source type. This must be \&quot;Board\&quot;, \&quot;Project\&quot; or \&quot;Filter\&quot;. | 
**value** | **Long** | The issue source value. This must be a board ID if the type is \&quot;Board\&quot;, a project ID if the type is \&quot;Project\&quot; or a filter ID if the type is \&quot;Filter\&quot;. | 

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
BOARD | &quot;Board&quot;
PROJECT | &quot;Project&quot;
FILTER | &quot;Filter&quot;
