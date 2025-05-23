# SpacePermissionAssignmentOperation

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**key** | [**KeyEnum**](#KeyEnum) | The type of operation. |  [optional]
**targetType** | [**TargetTypeEnum**](#TargetTypeEnum) | The type of entity the operation type targets. |  [optional]

<a name="KeyEnum"></a>
## Enum: KeyEnum
Name | Value
---- | -----
USE | &quot;use&quot;
CREATE | &quot;create&quot;
READ | &quot;read&quot;
UPDATE | &quot;update&quot;
DELETE | &quot;delete&quot;
COPY | &quot;copy&quot;
MOVE | &quot;move&quot;
EXPORT | &quot;export&quot;
PURGE | &quot;purge&quot;
PURGE_VERSION | &quot;purge_version&quot;
ADMINISTER | &quot;administer&quot;
RESTORE | &quot;restore&quot;
CREATE_SPACE | &quot;create_space&quot;
RESTRICT_CONTENT | &quot;restrict_content&quot;
ARCHIVE | &quot;archive&quot;

<a name="TargetTypeEnum"></a>
## Enum: TargetTypeEnum
Name | Value
---- | -----
PAGE | &quot;page&quot;
BLOGPOST | &quot;blogpost&quot;
COMMENT | &quot;comment&quot;
ATTACHMENT | &quot;attachment&quot;
WHITEBOARD | &quot;whiteboard&quot;
DATABASE | &quot;database&quot;
EMBED | &quot;embed&quot;
FOLDER | &quot;folder&quot;
SPACE | &quot;space&quot;
APPLICATION | &quot;application&quot;
USERPROFILE | &quot;userProfile&quot;
