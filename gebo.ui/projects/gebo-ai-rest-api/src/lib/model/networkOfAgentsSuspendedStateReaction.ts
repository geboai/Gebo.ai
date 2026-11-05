import { AgentSuspendedWaitingConditionReactionDto } from './agentSuspendedWaitingConditionReactionDto';

/**
 * The reaction to a suspended network of agents, sent with the request that
 * respawns it: the block answered and one reaction per condition answered.
 */
export interface NetworkOfAgentsSuspendedStateReaction {
    networkOfAgentsBlockId?: string;
    reactions?: Array<AgentSuspendedWaitingConditionReactionDto>;
}
