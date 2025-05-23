# FieldReferenceData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**auto** | [**AutoEnum**](#AutoEnum) | Whether the field provide auto-complete suggestions. |  [optional]
**cfid** | **String** | If the item is a custom field, the ID of the custom field. |  [optional]
**deprecated** | [**DeprecatedEnum**](#DeprecatedEnum) | Whether this field has been deprecated. |  [optional]
**deprecatedSearcherKey** | **String** | The searcher key of the field, only passed when the field is deprecated. |  [optional]
**displayName** | **String** | The display name contains the following:   *  for system fields, the field name. For example, &#x60;Summary&#x60;.  *  for collapsed custom fields, the field name followed by a hyphen and then the field name and field type. For example, &#x60;Component - Component[Dropdown]&#x60;.  *  for other custom fields, the field name followed by a hyphen and then the custom field ID. For example, &#x60;Component - cf[10061]&#x60;. |  [optional]
**operators** | **List&lt;String&gt;** | The valid search operators for the field. |  [optional]
**orderable** | [**OrderableEnum**](#OrderableEnum) | Whether the field can be used in a query&#x27;s &#x60;ORDER BY&#x60; clause. |  [optional]
**searchable** | [**SearchableEnum**](#SearchableEnum) | Whether the content of this field can be searched. |  [optional]
**types** | **List&lt;String&gt;** | The data types of items in the field. |  [optional]
**value** | **String** | The field identifier. |  [optional]

<a name="AutoEnum"></a>
## Enum: AutoEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;

<a name="DeprecatedEnum"></a>
## Enum: DeprecatedEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;

<a name="OrderableEnum"></a>
## Enum: OrderableEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;

<a name="SearchableEnum"></a>
## Enum: SearchableEnum
Name | Value
---- | -----
TRUE | &quot;true&quot;
FALSE | &quot;false&quot;
