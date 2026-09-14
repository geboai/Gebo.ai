# JiraIssueAttributeFilter

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**projectCodes** | **List&lt;String&gt;** |  |  [optional]
**issuetypeCodes** | **List&lt;String&gt;** |  |  [optional]
**issueKeys** | **List&lt;String&gt;** |  |  [optional]
**summaryTerms** | **List&lt;String&gt;** |  |  [optional]
**summaryTermsMatchMode** | [**SummaryTermsMatchModeEnum**](#SummaryTermsMatchModeEnum) |  |  [optional]
**descriptionTerms** | **List&lt;String&gt;** |  |  [optional]
**descriptionTermsMatchMode** | [**DescriptionTermsMatchModeEnum**](#DescriptionTermsMatchModeEnum) |  |  [optional]
**labels** | **List&lt;String&gt;** |  |  [optional]
**labelsMatchMode** | [**LabelsMatchModeEnum**](#LabelsMatchModeEnum) |  |  [optional]
**priorityCodes** | **List&lt;String&gt;** |  |  [optional]
**statusCodes** | **List&lt;String&gt;** |  |  [optional]
**affectedVersions** | **List&lt;String&gt;** |  |  [optional]
**fixVersions** | **List&lt;String&gt;** |  |  [optional]

<a name="SummaryTermsMatchModeEnum"></a>
## Enum: SummaryTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="DescriptionTermsMatchModeEnum"></a>
## Enum: DescriptionTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="LabelsMatchModeEnum"></a>
## Enum: LabelsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;
