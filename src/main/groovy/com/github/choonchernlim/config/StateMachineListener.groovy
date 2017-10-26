package com.github.choonchernlim.config

import groovy.util.logging.Slf4j
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State

@Slf4j
class StateMachineListener extends StateMachineListenerAdapter {
    @Override
    void stateChanged(State from, State to) {
        log.info(">>>>> Transitioned from ${from?.id} to ${to.id}")
    }
}
