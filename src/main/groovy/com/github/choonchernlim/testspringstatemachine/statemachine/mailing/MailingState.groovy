package com.github.choonchernlim.testspringstatemachine.statemachine.mailing

import org.springframework.statemachine.StateMachine

/**
 * All state machine states.
 */
enum MailingState {
    WAITING,
    SEND_SUCCESS,
    SEND_FAILED,
    SHOULD_SEND_MAIL_CHOICE

    static void validate(final StateMachine<String, String> stateMachine) {
        assert stateMachine.states.collect { it.id }.toSet() == values().collect { it.name() }.toSet()
    }
}
