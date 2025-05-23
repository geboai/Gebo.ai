# GlobalScopeBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**attributes** | [**List&lt;AttributesEnum&gt;**](#List&lt;AttributesEnum&gt;) | Defines the behavior of the option in the global context.If notSelectable is set, the option cannot be set as the field&#x27;s value. This is useful for archiving an option that has previously been selected but shouldn&#x27;t be used anymore.If defaultValue is set, the option is selected by default. |  [optional]

<a name="List<AttributesEnum>"></a>
## Enum: List&lt;AttributesEnum&gt;
Name | Value
---- | -----
NOTSELECTABLE | &quot;notSelectable&quot;
DEFAULTVALUE | &quot;defaultValue&quot;
