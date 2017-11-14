package com.github.choonchernlim.testspringstatemachine.statemachine.mailing
/**
 * All state machine states.
 */
enum MailingState {
    WAITING,
    SEND_SUCCESS,
    SEND_FAILED,
    SHOULD_SEND_MAIL_CHOICE

    static Set<String> getNames() {
        return values().collect { it.name() }.toSet()
    }
}
