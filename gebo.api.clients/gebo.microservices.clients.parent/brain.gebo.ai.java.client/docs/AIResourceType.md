# AIResourceType

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**description** | **String** |  | 
**consented** | [**List&lt;ConsentedEnum&gt;**](#List&lt;ConsentedEnum&gt;) |  | 
**metaData** | **Map&lt;String, Object&gt;** |  |  [optional]
**category** | [**AIResourceCategory**](AIResourceCategory.md) |  | 
**downloadable** | **Boolean** |  |  [optional]

<a name="List<ConsentedEnum>"></a>
## Enum: List&lt;ConsentedEnum&gt;
Name | Value
---- | -----
READ_INFORMATIONS | &quot;READ_INFORMATIONS&quot;
EXECUTE_CODE | &quot;EXECUTE_CODE&quot;
WRITE_INFORMATIONS | &quot;WRITE_INFORMATIONS&quot;
