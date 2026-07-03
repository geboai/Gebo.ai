# AclGrantAssignment

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ownerType** | [**OwnerTypeEnum**](#OwnerTypeEnum) |  |  [optional]
**ownerCode** | **String** |  |  [optional]
**grants** | [**List&lt;GrantsEnum&gt;**](#List&lt;GrantsEnum&gt;) |  |  [optional]

<a name="OwnerTypeEnum"></a>
## Enum: OwnerTypeEnum
Name | Value
---- | -----
EVERYONE | &quot;EVERYONE&quot;
GROUP | &quot;GROUP&quot;
USER | &quot;USER&quot;

<a name="List<GrantsEnum>"></a>
## Enum: List&lt;GrantsEnum&gt;
Name | Value
---- | -----
READ | &quot;READ&quot;
WRITE | &quot;WRITE&quot;
EXECUTE | &quot;EXECUTE&quot;
