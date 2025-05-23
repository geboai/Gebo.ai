# IssueUpdateDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fields** | **Map&lt;String, Object&gt;** | List of issue screen fields to update, specifying the sub-field to update and its value for each field. This field provides a straightforward option when setting a sub-field. When multiple sub-fields or other operations are required, use &#x60;update&#x60;. Fields included in here cannot be included in &#x60;update&#x60;. |  [optional]
**historyMetadata** | **AllOfIssueUpdateDetailsHistoryMetadata** | Additional issue history details. |  [optional]
**properties** | [**List&lt;EntityProperty&gt;**](EntityProperty.md) | Details of issue properties to be add or update. |  [optional]
**transition** | **AllOfIssueUpdateDetailsTransition** | Details of a transition. Required when performing a transition, optional when creating or editing an issue. |  [optional]
**update** | [**Map&lt;String, List&lt;FieldUpdateOperation&gt;&gt;**](List.md) | A Map containing the field field name and a list of operations to perform on the issue screen field. Note that fields included in here cannot be included in &#x60;fields&#x60;. |  [optional]
