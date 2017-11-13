package com.github.choonchernlim.testspringstatemachine.config

import com.github.choonchernlim.testspringstatemachine.statemachine.core.StateMachineListener
import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.*
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

/**
 * Default state machine configuration where states and transitions are all defined here.
 *
 * Instead of using Enums for states and events, String is used to allow services to use UML-based state machine
 * factory interchangeably.
 */
@Profile('default')
@Slf4j
@Configuration
@EnableStateMachineFactory
class MailingStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    MailIdExistGuard mailIdExistGuard

    @Autowired
    MailIdExistAction mailIdExistAction

    MailingStateMachineConfig() {
        log.info('Activating MailingStateMachineConfig...')
    }

    @Override
    void configure(final StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.
                withConfiguration().
                machineId(MailingMetadata.MACHINE_ID).
                listener(new StateMachineListener())
    }

    @Override
    void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.
                withStates().
                initial(MailingState.WAITING.name()).
                states(MailingState.values().collect { it.name() }.toSet()).
                choice(MailingState.SHOULD_SEND_MAIL_CHOICE.name())
    }

    @Override
    void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.
                withExternal().
                source(MailingState.WAITING.name()).
                target(MailingState.SHOULD_SEND_MAIL_CHOICE.name()).
                event(MailingEvent.SEND_MAIL.name()).
                and().
                withChoice().
                source(MailingState.SHOULD_SEND_MAIL_CHOICE.name()).
                first(MailingState.SEND_SUCCESS.name(), mailIdExistGuard, mailIdExistAction).
                last(MailingState.SEND_FAILED.name())
    }
}
