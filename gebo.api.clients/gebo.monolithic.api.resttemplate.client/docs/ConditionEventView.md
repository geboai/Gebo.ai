# ConditionEventView

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**correlationId** | **String** |  |  [optional]
**sessionCode** | **String** |  |  [optional]
**conditionId** | **String** |  |  [optional]
**conditionType** | **String** |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) |  |  [optional]
**reason** | **String** |  |  [optional]
**blockCode** | **String** |  |  [optional]
**sourceSystem** | **String** |  |  [optional]
**source** | [**ConditionSource**](ConditionSource.md) |  |  [optional]
**sourceEventId** | **String** |  |  [optional]
**sourceTimestamp** | [**Date**](Date.md) |  |  [optional]
**receivedAt** | [**Date**](Date.md) |  |  [optional]
**lastReceivedAt** | [**Date**](Date.md) |  |  [optional]
**deliveries** | **Integer** |  |  [optional]
**acceptedAt** | [**Date**](Date.md) |  |  [optional]
**appliedAt** | [**Date**](Date.md) |  |  [optional]
**respawnedToBlockCode** | **String** |  |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
PENDING | &quot;PENDING&quot;
ACCEPTED | &quot;ACCEPTED&quot;
DISPATCHED | &quot;DISPATCHED&quot;
APPLIED | &quot;APPLIED&quot;
REFUSED | &quot;REFUSED&quot;
STALE | &quot;STALE&quot;
EXPIRED | &quot;EXPIRED&quot;
FAILED | &quot;FAILED&quot;
