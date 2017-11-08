package com.github.choonchernlim.statemachine.core

import groovy.util.logging.Slf4j
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
import org.springframework.statemachine.transition.Transition

@Slf4j
class StateMachineListener extends StateMachineListenerAdapter {

    @Override
    void stateMachineError(final StateMachine stateMachine, final Exception exception) {
        log.error("STATE MACHINE ERROR : " +
                  "[MACHINE ID: ${stateMachine.id}]".padRight(30) +
                  "[EXCEPTION: ${exception}]".padRight(30)
        )
    }

    @Override
    void stateChanged(final State from, final State to) {
        log.debug("STATE CHANGED      : " +
                  "[FROM: ${from?.id}]".padRight(30) +
                  "[TO: ${to.id}]".padRight(30)
        )
    }

    @Override
    void transition(final Transition transition) {
        log.debug("TRANSITION         : " +
                  "[FROM: ${transition.source?.id}]".padRight(30) +
                  "[TO: ${transition.target?.id}]".padRight(30) +
                  "[EVENT: ${transition.trigger?.event}]".padRight(30)
        )
    }

    @Override
    void eventNotAccepted(final Message message) {
        log.warn("EVENT NOT ACCEPTED  : " +
                 "[MESSAGE: ${message}]".padRight(30)
        )
    }


}
