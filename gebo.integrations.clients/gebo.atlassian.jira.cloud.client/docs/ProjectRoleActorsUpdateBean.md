# ProjectRoleActorsUpdateBean

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**categorisedActors** | [**Map&lt;String, List&lt;String&gt;&gt;**](List.md) | The actors to add to the project role.  Add groups using:   *  &#x60;atlassian-group-role-actor&#x60; and a list of group names.  *  &#x60;atlassian-group-role-actor-id&#x60; and a list of group IDs.  As a group&#x27;s name can change, use of &#x60;atlassian-group-role-actor-id&#x60; is recommended. For example, &#x60;\&quot;atlassian-group-role-actor-id\&quot;:[\&quot;eef79f81-0b89-4fca-a736-4be531a10869\&quot;,\&quot;77f6ab39-e755-4570-a6ae-2d7a8df0bcb8\&quot;]&#x60;.  Add users using &#x60;atlassian-user-role-actor&#x60; and a list of account IDs. For example, &#x60;\&quot;atlassian-user-role-actor\&quot;:[\&quot;12345678-9abc-def1-2345-6789abcdef12\&quot;, \&quot;abcdef12-3456-789a-bcde-f123456789ab\&quot;]&#x60;. |  [optional]
**id** | **Long** | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |  [optional]
