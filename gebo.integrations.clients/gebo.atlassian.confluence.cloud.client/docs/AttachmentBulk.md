# AttachmentBulk

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | ID of the attachment. |  [optional]
**status** | [**ContentStatus**](ContentStatus.md) |  |  [optional]
**title** | **String** | Title of the comment. |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Date and time when the attachment was created. In format \&quot;YYYY-MM-DDTHH:mm:ss.sssZ\&quot;. |  [optional]
**pageId** | **String** | ID of the containing page.  Note: This is only returned if the attachment has a container that is a page. |  [optional]
**blogPostId** | **String** | ID of the containing blog post.  Note: This is only returned if the attachment has a container that is a blog post. |  [optional]
**customContentId** | **String** | ID of the containing custom content.  Note: This is only returned if the attachment has a container that is custom content. |  [optional]
**mediaType** | **String** | Media Type for the attachment. |  [optional]
**mediaTypeDescription** | **String** | Media Type description for the attachment. |  [optional]
**comment** | **String** | Comment for the attachment. |  [optional]
**fileId** | **String** | File ID of the attachment. This is the ID referenced in &#x60;atlas_doc_format&#x60; bodies and is distinct from the attachment ID. |  [optional]
**fileSize** | **Long** | File size of the attachment. |  [optional]
**webuiLink** | **String** | WebUI link of the attachment. |  [optional]
**downloadLink** | **String** | Download link of the attachment. |  [optional]
**version** | [**Version**](Version.md) |  |  [optional]
**_links** | [**AttachmentLinks**](AttachmentLinks.md) |  |  [optional]
