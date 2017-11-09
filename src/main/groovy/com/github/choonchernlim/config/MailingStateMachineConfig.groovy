package com.github.choonchernlim.config

import com.github.choonchernlim.statemachine.core.StateMachineListener
import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

@Slf4j
@Configuration
@EnableStateMachineFactory
@SuppressWarnings(['GroovyUnusedDeclaration', 'GrMethodMayBeStatic'])
class MailingStateMachineConfig extends StateMachineConfigurerAdapter<MailingMetadata.State, MailingMetadata.Event> {

    @Override
    void configure(final StateMachineConfigurationConfigurer<MailingMetadata.State, MailingMetadata.Event> config)
            throws Exception {
        config.
                withConfiguration().
                machineId(MailingMetadata.MACHINE_ID).
                listener(new StateMachineListener())
    }

    @Override
    void configure(StateMachineStateConfigurer<MailingMetadata.State, MailingMetadata.Event> states)
            throws Exception {
        states.
                withStates().
                initial(MailingMetadata.State.WAITING).
                states(EnumSet.allOf(MailingMetadata.State)).
                choice(MailingMetadata.State.SHOULD_SEND_MAIL_CHOICE)
    }

    @Override
    void configure(StateMachineTransitionConfigurer<MailingMetadata.State, MailingMetadata.Event> transitions)
            throws Exception {
        transitions.
                withExternal().
                source(MailingMetadata.State.WAITING).
                target(MailingMetadata.State.SHOULD_SEND_MAIL_CHOICE).
                event(MailingMetadata.Event.SEND_MAIL).
                and().
                withChoice().
                source(MailingMetadata.State.SHOULD_SEND_MAIL_CHOICE).
                first(MailingMetadata.State.SEND_SUCCESS,
                      { ctx -> ctx.getMessageHeader(MailingMetadata.SendMailEvent.MAIL_ID) != null },
                      { ctx -> log.info("Sending mail...") }).
                last(MailingMetadata.State.SEND_FAILED)
    }
}
