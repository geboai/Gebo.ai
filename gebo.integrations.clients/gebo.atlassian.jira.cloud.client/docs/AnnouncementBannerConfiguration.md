# AnnouncementBannerConfiguration

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**hashId** | **String** | Hash of the banner data. The client detects updates by comparing hash IDs. |  [optional]
**isDismissible** | **Boolean** | Flag indicating if the announcement banner can be dismissed by the user. |  [optional]
**isEnabled** | **Boolean** | Flag indicating if the announcement banner is enabled or not. |  [optional]
**message** | **String** | The text on the announcement banner. |  [optional]
**visibility** | [**VisibilityEnum**](#VisibilityEnum) | Visibility of the announcement banner. |  [optional]

<a name="VisibilityEnum"></a>
## Enum: VisibilityEnum
Name | Value
---- | -----
PUBLIC | &quot;PUBLIC&quot;
PRIVATE | &quot;PRIVATE&quot;
