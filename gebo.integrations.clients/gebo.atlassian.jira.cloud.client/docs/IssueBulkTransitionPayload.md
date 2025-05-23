# IssueBulkTransitionPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**bulkTransitionInputs** | [**List&lt;BulkTransitionSubmitInput&gt;**](BulkTransitionSubmitInput.md) | List of objects and each object has two properties:   *  Issues that will be bulk transitioned.  *  TransitionId that corresponds to a specific transition of issues that share the same workflow. | 
**sendBulkNotification** | **Boolean** | A boolean value that indicates whether to send a bulk change notification when the issues are being transitioned.  If &#x60;true&#x60;, dispatches a bulk notification email to users about the updates. |  [optional]
