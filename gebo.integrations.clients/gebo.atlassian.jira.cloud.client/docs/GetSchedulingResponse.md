# GetSchedulingResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dependencies** | [**DependenciesEnum**](#DependenciesEnum) | The dependencies for the plan. This is \&quot;Sequential\&quot; or \&quot;Concurrent\&quot;. | 
**endDate** | **AllOfGetSchedulingResponseEndDate** | The end date field for the plan. | 
**estimation** | [**EstimationEnum**](#EstimationEnum) | The estimation unit for the plan. This is \&quot;StoryPoints\&quot;, \&quot;Days\&quot; or \&quot;Hours\&quot;. | 
**inferredDates** | [**InferredDatesEnum**](#InferredDatesEnum) | The inferred dates for the plan. This is \&quot;None\&quot;, \&quot;SprintDates\&quot; or \&quot;ReleaseDates\&quot;. | 
**startDate** | **AllOfGetSchedulingResponseStartDate** | The start date field for the plan. | 

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
