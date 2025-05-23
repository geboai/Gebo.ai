# SpacePermissionV2Operation

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**key** | [**KeyEnum**](#KeyEnum) |  | 
**target** | [**TargetEnum**](#TargetEnum) | The space or content type that the operation applies to. | 

<a name="KeyEnum"></a>
## Enum: KeyEnum
Name | Value
---- | -----
ADMINISTER | &quot;administer&quot;
ARCHIVE | &quot;archive&quot;
COPY | &quot;copy&quot;
CREATE | &quot;create&quot;
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

<a name="TargetEnum"></a>
## Enum: TargetEnum
Name | Value
---- | -----
PAGE | &quot;page&quot;
BLOGPOST | &quot;blogpost&quot;
COMMENT | &quot;comment&quot;
ATTACHMENT | &quot;attachment&quot;
SPACE | &quot;space&quot;
