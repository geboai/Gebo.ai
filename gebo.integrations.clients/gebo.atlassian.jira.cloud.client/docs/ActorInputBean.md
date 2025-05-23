# ActorInputBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**group** | **List&lt;String&gt;** | The name of the group to add as a default actor. This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change,use of &#x60;groupId&#x60; is recommended. This parameter accepts a comma-separated list. For example, &#x60;\&quot;group\&quot;:[\&quot;project-admin\&quot;, \&quot;jira-developers\&quot;]&#x60;. |  [optional]
**groupId** | **List&lt;String&gt;** | The ID of the group to add as a default actor. This parameter cannot be used with the &#x60;group&#x60; parameter This parameter accepts a comma-separated list. For example, &#x60;\&quot;groupId\&quot;:[\&quot;77f6ab39-e755-4570-a6ae-2d7a8df0bcb8\&quot;, \&quot;0c011f85-69ed-49c4-a801-3b18d0f771bc\&quot;]&#x60;. |  [optional]
**user** | **List&lt;String&gt;** | The account IDs of the users to add as default actors. This parameter accepts a comma-separated list. For example, &#x60;\&quot;user\&quot;:[\&quot;5b10a2844c20165700ede21g\&quot;, \&quot;5b109f2e9729b51b54dc274d\&quot;]&#x60;. |  [optional]
