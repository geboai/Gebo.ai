/**
 * A condition a suspended network of agents waits for, as the client reads it: its
 * kind (the translator that made it), its id, matched to the saved condition when
 * answered, and the attributes of its kind (statement, explanation, mandatory for
 * every kind; question, options and customAnswerAllowed for a clarification).
 */
export interface AgentSuspendedWaitingConditionDto {
    conditionType?: string;
    conditionId?: string;
    attributes?: { [key: string]: any; };
}
