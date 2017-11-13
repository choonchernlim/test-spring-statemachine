package com.github.choonchernlim.statemachine.mailing

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext
import org.springframework.stereotype.Service

@Slf4j
@Service
class MailingStateMachineService {

    final StateMachineFactory<String, String> stateMachineFactory

    @Autowired
    MailingStateMachineService(final StateMachineFactory<String, String> stateMachineFactory) {
        validateStateMachine(stateMachineFactory)

        this.stateMachineFactory = stateMachineFactory
    }

    // ensure states and events from the generated state machine are defined in the app
    private static void validateStateMachine(StateMachineFactory<String, String> stateMachineFactory) {
        final StateMachine stateMachine = stateMachineFactory.getStateMachine(MailingMetadata.MACHINE_ID)
        MailingState.validate(stateMachine)
        MailingEvent.validate(stateMachine)
    }

    // - get state machine
    // - set initial state
    // - start state machine
    // - execute events
    // - stop state machine
    Map<String, Boolean> setInitialStateThenExecute(
            final MailingState currentState,
            final List<Message<String>> events) {
        assert currentState
        assert events

        final StateMachine<String, String> stateMachine = stateMachineFactory.getStateMachine(
                MailingMetadata.MACHINE_ID)

        stateMachine.stateMachineAccessor.withAllRegions().each {
            it.resetStateMachine(new DefaultStateMachineContext(currentState.name(),
                                                                null,
                                                                null,
                                                                null,
                                                                null,
                                                                MailingMetadata.MACHINE_ID))
        }

        stateMachine.start()

        final Map<String, Boolean> eventResults = events.collectEntries {
            [(it.payload): stateMachine.sendEvent(it)]
        }

        stateMachine.stop()

        return eventResults
    }
}