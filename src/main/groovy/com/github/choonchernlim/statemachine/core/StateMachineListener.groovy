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
    void stateMachineStarted(final StateMachine stateMachine) {
        log.debug(padRight("STATE MACHINE STARTED") +
                  " : " +
                  padRight("[MACHINE ID: ${stateMachine.id}]")
        )
    }

    @Override
    void stateMachineStopped(final StateMachine stateMachine) {
        log.debug(padRight("STATE MACHINE STOPPED") +
                  " : " +
                  padRight("[MACHINE ID: ${stateMachine.id}]")
        )
    }

    @Override
    void stateMachineError(final StateMachine stateMachine, final Exception exception) {
        log.error(padRight("STATE MACHINE ERROR") +
                  " : " +
                  padRight("[MACHINE ID: ${stateMachine.id}]") +
                  padRight("[EXCEPTION: ${exception}]")
        )
    }

    @Override
    void stateChanged(final State from, final State to) {
        log.debug(padRight("STATE CHANGED") +
                  " : " +
                  padRight("[FROM: ${from?.id}]") +
                  padRight("[TO: ${to.id}]")
        )
    }

    @Override
    void transition(final Transition transition) {
        log.debug(padRight("TRANSITION") +
                  " : " +
                  padRight("[FROM: ${transition.source?.id}]") +
                  padRight("[TO: ${transition.target?.id}]") +
                  padRight("[EVENT: ${transition.trigger?.event}]")
        )
    }

    @Override
    void eventNotAccepted(final Message message) {
        log.warn(padRight("EVENT NOT ACCEPTED") +
                 " : " +
                 padRight("[MESSAGE: ${message}]")
        )
    }

    private static String padRight(final String s) {
        return s.padRight(50)
    }
}
