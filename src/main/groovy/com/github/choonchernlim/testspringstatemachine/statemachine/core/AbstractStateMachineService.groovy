package com.github.choonchernlim.testspringstatemachine.statemachine.core

import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext

/**
 * State machine handler.
 */
abstract class AbstractStateMachineService {

    protected final String machineId
    private final StateMachineFactory<String, String> stateMachineFactory

    AbstractStateMachineService(final StateMachineFactory<String, String> stateMachineFactory,
                                final String machineId,
                                final Set<String> states,
                                final Set<String> events) {
        this.stateMachineFactory = stateMachineFactory
        this.machineId = machineId

        // ensures all states and events from the state machine are defined in the app
        final StateMachine<String, String> stateMachine = getStateMachine()
        assert stateMachine.states.collect { it.id }.toSet() == states
        assert stateMachine.transitions.findAll { it.trigger }.collect { it.trigger.event }.toSet() == events
    }

    // - get state machine
    // - set initial state
    // - start state machine
    // - execute events
    // - stop state machine
    boolean setInitialStateThenFireEvent(final Enum currentState,
                                         final Enum mailingEvent,
                                         final Map<String, Object> headerMap) {
        assert currentState
        assert mailingEvent

        final StateMachine<String, String> stateMachine = getStateMachine()

        stateMachine.stateMachineAccessor.withAllRegions().each {
            it.resetStateMachine(new DefaultStateMachineContext(currentState.name(),
                                                                null,
                                                                null,
                                                                null,
                                                                null,
                                                                machineId))
        }

        stateMachine.start()

        final MessageBuilder messageBuilder = MessageBuilder.withPayload(mailingEvent.name())

        if (headerMap) {
            headerMap.each {
                messageBuilder.setHeader(it.key, it.value)
            }
        }

        boolean isEventAccepted = stateMachine.sendEvent(messageBuilder.build())

        stateMachine.stop()

        return isEventAccepted
    }

    private StateMachine<String, String> getStateMachine() {
        return stateMachineFactory.getStateMachine(machineId)
    }
}