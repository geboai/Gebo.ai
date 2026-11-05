import { AgentSuspendedWaitingConditionDto } from './agentSuspendedWaitingConditionDto';

/**
 * A network of agents suspended in the conversation: the block it was saved as and
 * the conditions it waits for, each answered through a reaction on the respawn.
 */
export interface NetworkOfAgentsSuspendedState {
    networkOfAgentsBlockId?: string;
    conditions?: Array<AgentSuspendedWaitingConditionDto>;
}
