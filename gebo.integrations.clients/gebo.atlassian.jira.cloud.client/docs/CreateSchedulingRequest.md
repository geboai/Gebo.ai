# CreateSchedulingRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dependencies** | [**DependenciesEnum**](#DependenciesEnum) | The dependencies for the plan. This must be \&quot;Sequential\&quot; or \&quot;Concurrent\&quot;. |  [optional]
**endDate** | **AllOfCreateSchedulingRequestEndDate** | The end date field for the plan. |  [optional]
**estimation** | [**EstimationEnum**](#EstimationEnum) | The estimation unit for the plan. This must be \&quot;StoryPoints\&quot;, \&quot;Days\&quot; or \&quot;Hours\&quot;. | 
**inferredDates** | [**InferredDatesEnum**](#InferredDatesEnum) | The inferred dates for the plan. This must be \&quot;None\&quot;, \&quot;SprintDates\&quot; or \&quot;ReleaseDates\&quot;. |  [optional]
**startDate** | **AllOfCreateSchedulingRequestStartDate** | The start date field for the plan. |  [optional]

<a name="DependenciesEnum"></a>
## Enum: DependenciesEnum
Name | Value
---- | -----
SEQUENTIAL | &quot;Sequential&quot;
CONCURRENT | &quot;Concurrent&quot;

<a name="EstimationEnum"></a>
## Enum: EstimationEnum
Name | Value
---- | -----
STORYPOINTS | &quot;StoryPoints&quot;
DAYS | &quot;Days&quot;
HOURS | &quot;Hours&quot;

<a name="InferredDatesEnum"></a>
## Enum: InferredDatesEnum
Name | Value
---- | -----
NONE | &quot;None&quot;
SPRINTDATES | &quot;SprintDates&quot;
RELEASEDATES | &quot;ReleaseDates&quot;
