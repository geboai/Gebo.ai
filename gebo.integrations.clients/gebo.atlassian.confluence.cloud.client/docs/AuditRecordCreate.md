# AuditRecordCreate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**author** | [**AuditRecordCreateAuthor**](AuditRecordCreateAuthor.md) |  |  [optional]
**remoteAddress** | **String** | The IP address of the computer where the event was initiated from. | 
**creationDate** | **Long** | The creation date-time of the audit record, as a timestamp. This is converted to a date-time display in the Confluence UI. If the &#x60;creationDate&#x60; is not specified, then it will be set to the timestamp for the current date-time. |  [optional]
**summary** | **String** | The summary of the event, which is displayed in the &#x27;Change&#x27; column on the audit log in the Confluence UI. |  [optional]
**description** | **String** | A long description of the event, which is displayed in the &#x27;Description&#x27; field on the audit log in the Confluence UI. |  [optional]
**category** | **String** | The category of the event, which is displayed in the &#x27;Event type&#x27; column on the audit log in the Confluence UI. |  [optional]
**sysAdmin** | **Boolean** | Indicates whether the event was actioned by a system administrator. |  [optional]
**affectedObject** | [**AffectedObject**](AffectedObject.md) |  |  [optional]
**changedValues** | [**List&lt;ChangedValue&gt;**](ChangedValue.md) | The values that were changed in the event. |  [optional]
**associatedObjects** | [**List&lt;AffectedObject&gt;**](AffectedObject.md) | Objects that were associated with the event. For example, if the event was a space permission change then the associated object would be the space. |  [optional]
