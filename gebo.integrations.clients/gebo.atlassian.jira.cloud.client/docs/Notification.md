# Notification

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**htmlBody** | **String** | The HTML body of the email notification for the issue. |  [optional]
**restrict** | **AllOfNotificationRestrict** | Restricts the notifications to users with the specified permissions. |  [optional]
**subject** | **String** | The subject of the email notification for the issue. If this is not specified, then the subject is set to the issue key and summary. |  [optional]
**textBody** | **String** | The plain text body of the email notification for the issue. |  [optional]
**to** | **AllOfNotificationTo** | The recipients of the email notification for the issue. |  [optional]
