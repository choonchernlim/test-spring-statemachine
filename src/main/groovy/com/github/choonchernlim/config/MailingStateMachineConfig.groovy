package com.github.choonchernlim.config

import com.github.choonchernlim.statemachine.core.StateMachineListener
import com.github.choonchernlim.statemachine.mailing.MailingMetadata
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
@SuppressWarnings(['GroovyUnusedDeclaration', 'GrMethodMayBeStatic'])
class MailingMachineConfig extends StateMachineConfigurerAdapter<MailingMetadata.State, MailingMetadata.Event> {

    @Override
    void configure(
            final StateMachineConfigurationConfigurer<MailingMetadata.State, MailingMetadata.Event> config) throws Exception {
        config.
                withConfiguration().
                autoStartup(true).
                machineId(MailingMetadata.MACHINE_ID).
                listener(new StateMachineListener())
    }

    @Override
    void configure(StateMachineStateConfigurer<MailingMetadata.State, MailingMetadata.Event> states) throws Exception {
        states.
                withStates().
                initial(MailingMetadata.State.WAITING).
                states(EnumSet.allOf(MailingMetadata.State))
    }

    @Override
    void configure(
            StateMachineTransitionConfigurer<MailingMetadata.State, MailingMetadata.Event> transitions) throws Exception {
        transitions.
                withExternal().
                source(MailingMetadata.State.WAITING).
                target(MailingMetadata.State.SENT_SUCCESSFULLY).
                event(MailingMetadata.Event.SEND_MAIL)
    }
}
