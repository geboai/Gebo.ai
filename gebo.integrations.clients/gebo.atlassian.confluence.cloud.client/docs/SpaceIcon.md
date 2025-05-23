# SpaceIcon

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | The path (relative to base URL) at which the space&#x27;s icon can be retrieved. The format should be like &#x60;/wiki/download/...&#x60; or &#x60;/wiki/aa-avatar/...&#x60; |  [optional]
**apiDownloadLink** | **String** | The path (relative to base URL) that can be used to retrieve a link to download the space icon. 3LO apps should use this link instead of the value provided in the &#x60;path&#x60; property to retrieve the icon.  Currently this field is only returned for &#x60;global&#x60; spaces and not &#x60;personal&#x60; spaces.  |  [optional]
