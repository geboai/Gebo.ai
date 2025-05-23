# UpdatePriorityDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**avatarId** | **Long** | The ID for the avatar for the priority. This parameter is nullable and both iconUrl and avatarId cannot be defined. |  [optional]
**description** | **String** | The description of the priority. |  [optional]
**iconUrl** | [**IconUrlEnum**](#IconUrlEnum) | The URL of an icon for the priority. Accepted protocols are HTTP and HTTPS. Built in icons can also be used. Both iconUrl and avatarId cannot be defined. |  [optional]
**name** | **String** | The name of the priority. Must be unique. |  [optional]
**statusColor** | **String** | The status color of the priority in 3-digit or 6-digit hexadecimal format. |  [optional]

<a name="IconUrlEnum"></a>
## Enum: IconUrlEnum
Name | Value
---- | -----
BLOCKER_PNG | &quot;/images/icons/priorities/blocker.png&quot;
CRITICAL_PNG | &quot;/images/icons/priorities/critical.png&quot;
HIGH_PNG | &quot;/images/icons/priorities/high.png&quot;
HIGHEST_PNG | &quot;/images/icons/priorities/highest.png&quot;
LOW_PNG | &quot;/images/icons/priorities/low.png&quot;
LOWEST_PNG | &quot;/images/icons/priorities/lowest.png&quot;
MAJOR_PNG | &quot;/images/icons/priorities/major.png&quot;
MEDIUM_PNG | &quot;/images/icons/priorities/medium.png&quot;
MINOR_PNG | &quot;/images/icons/priorities/minor.png&quot;
TRIVIAL_PNG | &quot;/images/icons/priorities/trivial.png&quot;
BLOCKER_NEW_PNG | &quot;/images/icons/priorities/blocker_new.png&quot;
CRITICAL_NEW_PNG | &quot;/images/icons/priorities/critical_new.png&quot;
HIGH_NEW_PNG | &quot;/images/icons/priorities/high_new.png&quot;
HIGHEST_NEW_PNG | &quot;/images/icons/priorities/highest_new.png&quot;
LOW_NEW_PNG | &quot;/images/icons/priorities/low_new.png&quot;
LOWEST_NEW_PNG | &quot;/images/icons/priorities/lowest_new.png&quot;
MAJOR_NEW_PNG | &quot;/images/icons/priorities/major_new.png&quot;
MEDIUM_NEW_PNG | &quot;/images/icons/priorities/medium_new.png&quot;
MINOR_NEW_PNG | &quot;/images/icons/priorities/minor_new.png&quot;
TRIVIAL_NEW_PNG | &quot;/images/icons/priorities/trivial_new.png&quot;
