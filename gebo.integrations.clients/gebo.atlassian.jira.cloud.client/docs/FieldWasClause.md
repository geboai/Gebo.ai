# FieldWasClause

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**field** | [**JqlQueryField**](JqlQueryField.md) |  | 
**operand** | [**JqlQueryClauseOperand**](JqlQueryClauseOperand.md) |  | 
**operator** | [**OperatorEnum**](#OperatorEnum) | The operator between the field and operand. | 
**predicates** | [**List&lt;JqlQueryClauseTimePredicate&gt;**](JqlQueryClauseTimePredicate.md) | The list of time predicates. | 

<a name="OperatorEnum"></a>
## Enum: OperatorEnum
Name | Value
---- | -----
WAS | &quot;was&quot;
WAS_IN | &quot;was in&quot;
WAS_NOT_IN | &quot;was not in&quot;
WAS_NOT | &quot;was not&quot;
