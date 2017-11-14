package com.github.choonchernlim.testspringstatemachine.statemachine.mailing
/**
 * All state machine events.
 */
enum MailingEvent {
    SEND_MAIL

    static Set<String> getNames() {
        return values().collect { it.name() }.toSet()
    }
}
