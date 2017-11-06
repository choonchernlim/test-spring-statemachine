package com.github.choonchernlim.statemachine.core

import groovy.util.logging.Slf4j
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State

@Slf4j
class StateMachineListener extends StateMachineListenerAdapter {

    @Override
    void stateMachineStarted(final StateMachine stateMachine) {
        log.debug("LOGGER: STATE MACHINE STARTED: [MACHINE ID: ${stateMachine.id}]")
    }

    @Override
    void stateMachineStopped(final StateMachine stateMachine) {
        log.debug("LOGGER: STATE MACHINE STOPPED: [MACHINE ID: ${stateMachine.id}]")
    }

    @Override
    void stateMachineError(final StateMachine stateMachine, final Exception exception) {
        log.error("LOGGER: STATE MACHINE ERROR: [MACHINE ID: ${stateMachine.id}] [EXCEPTION: ${exception}]")
    }

    @Override
    void stateChanged(final State from, final State to) {
        log.debug("LOGGER: STATE CHANGED: [FROM: ${from?.id}] [TO: ${to.id}]")
    }

    @Override
    void eventNotAccepted(final Message message) {
        log.warn("LOGGER: EVENT NOT ACCEPTED: [MESSAGE: ${message}]")
    }


}
