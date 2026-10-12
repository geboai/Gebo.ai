# GSystemMessage

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**source** | **String** |  | 
**key** | **String** |  | 
**severity** | [**SeverityEnum**](#SeverityEnum) |  | 
**summary** | **String** |  | 
**detail** | **String** |  |  [optional]
**audience** | [**AudienceEnum**](#AudienceEnum) |  | 
**dismissible** | **Boolean** |  |  [optional]
**revision** | **Long** |  |  [optional]
**createdAt** | [**Date**](Date.md) |  |  [optional]
**updatedAt** | [**Date**](Date.md) |  |  [optional]
**expiresAt** | [**Date**](Date.md) |  |  [optional]

<a name="SeverityEnum"></a>
## Enum: SeverityEnum
Name | Value
---- | -----
INFO | &quot;info&quot;
WARN | &quot;warn&quot;
ERROR | &quot;error&quot;
SUCCESS | &quot;success&quot;

<a name="AudienceEnum"></a>
## Enum: AudienceEnum
Name | Value
---- | -----
ADMINS | &quot;ADMINS&quot;
USERS | &quot;USERS&quot;
ALL | &quot;ALL&quot;
