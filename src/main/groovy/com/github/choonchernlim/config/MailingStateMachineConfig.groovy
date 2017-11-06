package com.github.choonchernlim.config

import com.github.choonchernlim.statemachine.core.StateMachineListener
import com.github.choonchernlim.statemachine.mailing.MailingEvent
import com.github.choonchernlim.statemachine.mailing.MailingState
import com.github.choonchernlim.statemachine.mailing.MailingStateMachineConstant
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

@Slf4j
@Configuration
@EnableStateMachine
class MailingStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Override
    void configure(final StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.
                withConfiguration().
                autoStartup(true).
                machineId(MailingStateMachineConstant.MACHINE_ID).
                listener(new StateMachineListener())
    }

    @Override
    void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.
                withStates().
                initial(MailingState.WAITING).
                states(MailingState.allStates)
    }

    @Override
    void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.
                withExternal().
                source(MailingState.WAITING).
                target(MailingState.SENT_SUCCESSFULLY).
                event(MailingEvent.SendMail.EVENT_NAME)
    }
}
