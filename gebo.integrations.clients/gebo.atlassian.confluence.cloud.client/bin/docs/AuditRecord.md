# AuditRecord

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**author** | [**AuditRecordAuthor**](AuditRecordAuthor.md) |  | 
**remoteAddress** | **String** |  | 
**creationDate** | **Long** | The creation date-time of the audit record, as a timestamp. | 
**summary** | **String** |  | 
**description** | **String** |  | 
**category** | **String** |  | 
**sysAdmin** | **Boolean** |  | 
**superAdmin** | **Boolean** |  |  [optional]
**affectedObject** | [**AffectedObject**](AffectedObject.md) |  | 
**changedValues** | [**List&lt;ChangedValue&gt;**](ChangedValue.md) |  | 
**associatedObjects** | [**List&lt;AffectedObject&gt;**](AffectedObject.md) |  | 
