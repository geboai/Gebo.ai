# GlpiSearchFilter

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**itemKinds** | [**List&lt;ItemKindsEnum&gt;**](#List&lt;ItemKindsEnum&gt;) |  |  [optional]
**entityIds** | **List&lt;Long&gt;** |  |  [optional]
**ticketIds** | **List&lt;Long&gt;** |  |  [optional]
**titleTerms** | **List&lt;String&gt;** |  |  [optional]
**titleTermsMatchMode** | [**TitleTermsMatchModeEnum**](#TitleTermsMatchModeEnum) |  |  [optional]
**contentTerms** | **List&lt;String&gt;** |  |  [optional]
**contentTermsMatchMode** | [**ContentTermsMatchModeEnum**](#ContentTermsMatchModeEnum) |  |  [optional]
**ticketStatuses** | [**List&lt;TicketStatusesEnum&gt;**](#List&lt;TicketStatusesEnum&gt;) |  |  [optional]
**ticketTypes** | [**List&lt;TicketTypesEnum&gt;**](#List&lt;TicketTypesEnum&gt;) |  |  [optional]
**priorities** | **List&lt;Integer&gt;** |  |  [optional]
**categoryNames** | **List&lt;String&gt;** |  |  [optional]
**createdFrom** | **String** |  |  [optional]
**createdTo** | **String** |  |  [optional]

<a name="List<ItemKindsEnum>"></a>
## Enum: List&lt;ItemKindsEnum&gt;
Name | Value
---- | -----
TICKET | &quot;TICKET&quot;
KB_ARTICLE | &quot;KB_ARTICLE&quot;

<a name="TitleTermsMatchModeEnum"></a>
## Enum: TitleTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="ContentTermsMatchModeEnum"></a>
## Enum: ContentTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="List<TicketStatusesEnum>"></a>
## Enum: List&lt;TicketStatusesEnum&gt;
Name | Value
---- | -----
NEW | &quot;NEW&quot;
PROCESSING_ASSIGNED | &quot;PROCESSING_ASSIGNED&quot;
PROCESSING_PLANNED | &quot;PROCESSING_PLANNED&quot;
PENDING | &quot;PENDING&quot;
SOLVED | &quot;SOLVED&quot;
CLOSED | &quot;CLOSED&quot;
APPROVAL | &quot;APPROVAL&quot;

<a name="List<TicketTypesEnum>"></a>
## Enum: List&lt;TicketTypesEnum&gt;
Name | Value
---- | -----
INCIDENT | &quot;INCIDENT&quot;
REQUEST | &quot;REQUEST&quot;
