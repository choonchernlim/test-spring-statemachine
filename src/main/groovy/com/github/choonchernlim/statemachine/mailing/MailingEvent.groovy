package com.github.choonchernlim.statemachine.mailing

import org.springframework.statemachine.StateMachine

/**
 * All state machine events.
 */
enum MailingEvent {
    SEND_MAIL

    static void validate(final StateMachine<String, String> stateMachine) {
        assert stateMachine.transitions.findAll { it.trigger }.collect { it.trigger.event }.toSet() == values().
                collect { it.name() }.
                toSet()
    }
}
