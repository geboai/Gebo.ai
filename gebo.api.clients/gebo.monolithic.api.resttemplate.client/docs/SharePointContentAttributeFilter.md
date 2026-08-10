# SharePointContentAttributeFilter

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**contentKinds** | [**List&lt;ContentKindsEnum&gt;**](#List&lt;ContentKindsEnum&gt;) |  |  [optional]
**textTerms** | **List&lt;String&gt;** |  |  [optional]
**textTermsMatchMode** | [**TextTermsMatchModeEnum**](#TextTermsMatchModeEnum) |  |  [optional]
**titleTerms** | **List&lt;String&gt;** |  |  [optional]
**titleTermsMatchMode** | [**TitleTermsMatchModeEnum**](#TitleTermsMatchModeEnum) |  |  [optional]
**siteUrls** | **List&lt;String&gt;** |  |  [optional]
**siteUrlsMatchMode** | [**SiteUrlsMatchModeEnum**](#SiteUrlsMatchModeEnum) |  |  [optional]
**pathPrefixes** | **List&lt;String&gt;** |  |  [optional]
**pathPrefixesMatchMode** | [**PathPrefixesMatchModeEnum**](#PathPrefixesMatchModeEnum) |  |  [optional]
**managedPropertyEquals** | [**Map&lt;String, List&lt;String&gt;&gt;**](List.md) |  |  [optional]
**managedPropertyContains** | [**Map&lt;String, List&lt;String&gt;&gt;**](List.md) |  |  [optional]
**managedPropertiesValuesMatchMode** | [**ManagedPropertiesValuesMatchModeEnum**](#ManagedPropertiesValuesMatchModeEnum) |  |  [optional]

<a name="List<ContentKindsEnum>"></a>
## Enum: List&lt;ContentKindsEnum&gt;
Name | Value
---- | -----
DOCUMENT | &quot;DOCUMENT&quot;
PAGE | &quot;PAGE&quot;

<a name="TextTermsMatchModeEnum"></a>
## Enum: TextTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="TitleTermsMatchModeEnum"></a>
## Enum: TitleTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="SiteUrlsMatchModeEnum"></a>
## Enum: SiteUrlsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="PathPrefixesMatchModeEnum"></a>
## Enum: PathPrefixesMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="ManagedPropertiesValuesMatchModeEnum"></a>
## Enum: ManagedPropertiesValuesMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;
