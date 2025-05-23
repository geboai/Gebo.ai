# OperationCheckResult

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**operation** | [**OperationEnum**](#OperationEnum) | The operation itself. | 
**targetType** | **String** | The space or content type that the operation applies to. Could be one of- - application - page - blogpost - comment - attachment - space | 

<a name="OperationEnum"></a>
## Enum: OperationEnum
Name | Value
---- | -----
ADMINISTER | &quot;administer&quot;
ARCHIVE | &quot;archive&quot;
CLEAR_PERMISSIONS | &quot;clear_permissions&quot;
COPY | &quot;copy&quot;
CREATE | &quot;create&quot;
CREATE_SPACE | &quot;create_space&quot;
DELETE | &quot;delete&quot;
EXPORT | &quot;export&quot;
MOVE | &quot;move&quot;
PURGE | &quot;purge&quot;
PURGE_VERSION | &quot;purge_version&quot;
READ | &quot;read&quot;
RESTORE | &quot;restore&quot;
RESTRICT_CONTENT | &quot;restrict_content&quot;
UPDATE | &quot;update&quot;
USE | &quot;use&quot;
