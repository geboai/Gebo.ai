# EventNotification

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**emailAddress** | **String** | The email address. |  [optional]
**expand** | **String** | Expand options that include additional event notification details in the response. |  [optional]
**field** | **AllOfEventNotificationField** | The custom user or group field. |  [optional]
**group** | **AllOfEventNotificationGroup** | The specified group. |  [optional]
**id** | **Long** | The ID of the notification. |  [optional]
**notificationType** | [**NotificationTypeEnum**](#NotificationTypeEnum) | Identifies the recipients of the notification. |  [optional]
**parameter** | **String** | As a group&#x27;s name can change, use of &#x60;recipient&#x60; is recommended. The identifier associated with the &#x60;notificationType&#x60; value that defines the receiver of the notification, where the receiver isn&#x27;t implied by &#x60;notificationType&#x60; value. So, when &#x60;notificationType&#x60; is:   *  &#x60;User&#x60; The &#x60;parameter&#x60; is the user account ID.  *  &#x60;Group&#x60; The &#x60;parameter&#x60; is the group name.  *  &#x60;ProjectRole&#x60; The &#x60;parameter&#x60; is the project role ID.  *  &#x60;UserCustomField&#x60; The &#x60;parameter&#x60; is the ID of the custom field.  *  &#x60;GroupCustomField&#x60; The &#x60;parameter&#x60; is the ID of the custom field. |  [optional]
**projectRole** | **AllOfEventNotificationProjectRole** | The specified project role. |  [optional]
**recipient** | **String** | The identifier associated with the &#x60;notificationType&#x60; value that defines the receiver of the notification, where the receiver isn&#x27;t implied by the &#x60;notificationType&#x60; value. So, when &#x60;notificationType&#x60; is:   *  &#x60;User&#x60;, &#x60;recipient&#x60; is the user account ID.  *  &#x60;Group&#x60;, &#x60;recipient&#x60; is the group ID.  *  &#x60;ProjectRole&#x60;, &#x60;recipient&#x60; is the project role ID.  *  &#x60;UserCustomField&#x60;, &#x60;recipient&#x60; is the ID of the custom field.  *  &#x60;GroupCustomField&#x60;, &#x60;recipient&#x60; is the ID of the custom field. |  [optional]
**user** | **AllOfEventNotificationUser** | The specified user. |  [optional]

<a name="NotificationTypeEnum"></a>
## Enum: NotificationTypeEnum
Name | Value
---- | -----
CURRENTASSIGNEE | &quot;CurrentAssignee&quot;
REPORTER | &quot;Reporter&quot;
CURRENTUSER | &quot;CurrentUser&quot;
PROJECTLEAD | &quot;ProjectLead&quot;
COMPONENTLEAD | &quot;ComponentLead&quot;
USER | &quot;User&quot;
GROUP | &quot;Group&quot;
PROJECTROLE | &quot;ProjectRole&quot;
EMAILADDRESS | &quot;EmailAddress&quot;
ALLWATCHERS | &quot;AllWatchers&quot;
USERCUSTOMFIELD | &quot;UserCustomField&quot;
GROUPCUSTOMFIELD | &quot;GroupCustomField&quot;
