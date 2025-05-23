# ContentRestrictionUpdate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**operation** | [**OperationEnum**](#OperationEnum) | The restriction operation applied to content. | 
**restrictions** | [**ContentRestrictionUpdateRestrictions**](ContentRestrictionUpdateRestrictions.md) |  | 
**content** | [**Content**](Content.md) |  |  [optional]

<a name="OperationEnum"></a>
## Enum: OperationEnum
Name | Value
---- | -----
ADMINISTER | &quot;administer&quot;
COPY | &quot;copy&quot;
CREATE | &quot;create&quot;
DELETE | &quot;delete&quot;
EXPORT | &quot;export&quot;
MOVE | &quot;move&quot;
PURGE | &quot;purge&quot;
PURGE_VERSION | &quot;purge_version&quot;
READ | &quot;read&quot;
RESTORE | &quot;restore&quot;
UPDATE | &quot;update&quot;
USE | &quot;use&quot;
