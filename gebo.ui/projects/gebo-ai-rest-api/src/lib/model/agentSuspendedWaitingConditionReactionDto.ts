/**
 * The reaction to a condition a suspended network of agents waits for: the same
 * kind and id as the condition, the attributes of the answer (chosenOption or
 * customExplanation for a clarification, value for any other kind, declined to
 * leave a condition that is not mandatory unanswered).
 */
export interface AgentSuspendedWaitingConditionReactionDto {
    conditionType?: string;
    conditionId?: string;
    attributes?: { [key: string]: any; };
}
