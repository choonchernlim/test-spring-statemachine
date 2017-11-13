package com.github.choonchernlim.testspringstatemachine.statemachine.mailing

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext
import org.springframework.stereotype.Service

/**
 * Service class.
 */
@Slf4j
@Service
@SuppressWarnings("GrMethodMayBeStatic")
class MailingService {

    final StateMachineFactory<String, String> stateMachineFactory

    @Autowired
    MailingService(final StateMachineFactory<String, String> stateMachineFactory) {
        validateStateMachine(stateMachineFactory)

        this.stateMachineFactory = stateMachineFactory
    }

    // Fires SEND_MAIL event with parameter and returns boolean indicating whether event is accepted or not
    boolean fireSendMailEvent(final MailingState initialState, final Long mailId) {
        assert initialState
        assert mailId

        final String event = MailingEvent.SEND_MAIL.name()

        return setInitialStateThenFireEvents(initialState, [
                MessageBuilder.
                        withPayload(event).
                        setHeader(MailingMetadata.MAIL_ID, mailId).
                        build()
        ])
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
    private boolean setInitialStateThenFireEvents(
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

        boolean allEventsAccepted = events.inject(true) { result, event -> result && stateMachine.sendEvent(event) }

        stateMachine.stop()

        return allEventsAccepted
    }
}