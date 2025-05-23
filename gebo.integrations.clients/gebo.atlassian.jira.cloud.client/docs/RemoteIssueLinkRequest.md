# RemoteIssueLinkRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**application** | **AllOfRemoteIssueLinkRequestApplication** | Details of the remote application the linked item is in. For example, trello. |  [optional]
**globalId** | **String** | An identifier for the remote item in the remote system. For example, the global ID for a remote item in Confluence would consist of the app ID and page ID, like this: &#x60;appId&#x3D;456&amp;pageId&#x3D;123&#x60;.  Setting this field enables the remote issue link details to be updated or deleted using remote system and item details as the record identifier, rather than using the record&#x27;s Jira ID.  The maximum length is 255 characters. |  [optional]
**object** | **AllOfRemoteIssueLinkRequestObject** | Details of the item linked to. | 
**relationship** | **String** | Description of the relationship between the issue and the linked item. If not set, the relationship description \&quot;links to\&quot; is used in Jira. |  [optional]
