# ConditionEventAck

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**outcome** | [**OutcomeEnum**](#OutcomeEnum) |  |  [optional]
**correlationId** | **String** |  |  [optional]
**sessionCode** | **String** |  |  [optional]
**conditionId** | **String** |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) |  |  [optional]
**reason** | **String** |  |  [optional]

<a name="OutcomeEnum"></a>
## Enum: OutcomeEnum
Name | Value
---- | -----
ACCEPTED | &quot;ACCEPTED&quot;
PENDING | &quot;PENDING&quot;
DUPLICATE | &quot;DUPLICATE&quot;
SUPERSEDED | &quot;SUPERSEDED&quot;
REFUSED | &quot;REFUSED&quot;
STALE | &quot;STALE&quot;

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
