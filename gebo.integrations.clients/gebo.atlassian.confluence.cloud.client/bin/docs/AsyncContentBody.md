# AsyncContentBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**value** | **String** |  |  [optional]
**representation** | [**RepresentationEnum**](#RepresentationEnum) |  |  [optional]
**renderTaskId** | **String** |  |  [optional]
**error** | **String** |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | Rerunning is reserved for when the job is working, but there is a previous run&#x27;s value in the cache. You may choose to continue polling, or use the cached value. |  [optional]
**embeddedContent** | [**List&lt;EmbeddedContent&gt;**](EmbeddedContent.md) |  |  [optional]
**webresource** | [**WebResourceDependencies**](WebResourceDependencies.md) |  |  [optional]
**mediaToken** | [**AsyncContentBodyMediaToken**](AsyncContentBodyMediaToken.md) |  |  [optional]
**_expandable** | [**AsyncContentBodyExpandable**](AsyncContentBodyExpandable.md) |  |  [optional]
**_links** | [**GenericLinks**](GenericLinks.md) |  |  [optional]

<a name="RepresentationEnum"></a>
## Enum: RepresentationEnum
Name | Value
---- | -----
VIEW | &quot;view&quot;
EXPORT_VIEW | &quot;export_view&quot;
STYLED_VIEW | &quot;styled_view&quot;
STORAGE | &quot;storage&quot;
EDITOR | &quot;editor&quot;
EDITOR2 | &quot;editor2&quot;
ANONYMOUS_EXPORT_VIEW | &quot;anonymous_export_view&quot;
WIKI | &quot;wiki&quot;
ATLAS_DOC_FORMAT | &quot;atlas_doc_format&quot;

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
WORKING | &quot;WORKING&quot;
QUEUED | &quot;QUEUED&quot;
FAILED | &quot;FAILED&quot;
COMPLETED | &quot;COMPLETED&quot;
RERUNNING | &quot;RERUNNING&quot;
