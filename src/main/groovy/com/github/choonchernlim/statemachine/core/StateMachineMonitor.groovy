package com.github.choonchernlim.statemachine.core

import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import groovy.util.logging.Slf4j
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor
import org.springframework.statemachine.transition.Transition

@Slf4j
class StateMachineMonitor extends AbstractStateMachineMonitor<MailingMetadata.State, MailingMetadata.Event> {
    @Override
    void transition(final StateMachine<MailingMetadata.State, MailingMetadata.Event> stateMachine,
                    final Transition<MailingMetadata.State, MailingMetadata.Event> transition,
                    final long duration) {
        log.info(
                "MONITOR: ${transition.trigger?.event}: ${transition.source?.id} -> ${transition.target?.id} in ${duration}ms")
    }
}
