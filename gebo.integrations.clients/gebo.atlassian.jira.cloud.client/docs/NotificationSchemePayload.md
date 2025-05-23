# NotificationSchemePayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | The description of the notification scheme |  [optional]
**name** | **String** | The name of the notification scheme |  [optional]
**notificationSchemeEvents** | [**List&lt;NotificationSchemeEventPayload&gt;**](NotificationSchemeEventPayload.md) | The events and notifications for the notification scheme |  [optional]
**onConflict** | [**OnConflictEnum**](#OnConflictEnum) | The strategy to use when there is a conflict with an existing entity |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]

<a name="OnConflictEnum"></a>
## Enum: OnConflictEnum
Name | Value
---- | -----
FAIL | &quot;FAIL&quot;
USE | &quot;USE&quot;
NEW | &quot;NEW&quot;
