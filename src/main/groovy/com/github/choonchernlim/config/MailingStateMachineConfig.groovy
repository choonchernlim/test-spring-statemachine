package com.github.choonchernlim.config

import com.github.choonchernlim.statemachine.core.StateMachineListener
import com.github.choonchernlim.statemachine.mailing.MailIdExistAction
import com.github.choonchernlim.statemachine.mailing.MailIdExistGuard
import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

//@Slf4j
@Configuration
@EnableStateMachineFactory(name = 'stateMachineFactory')
//@SuppressWarnings(['GroovyUnusedDeclaration', 'GrMethodMayBeStatic'])
class MailingStateMachineConfig extends StateMachineConfigurerAdapter<MailingMetadata.State, MailingMetadata.Event> {

    @Autowired
    MailIdExistGuard mailIdExistGuard

    @Autowired
    MailIdExistAction mailIdExistAction

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
                first(MailingMetadata.State.SEND_SUCCESS, mailIdExistGuard, mailIdExistAction).
                last(MailingMetadata.State.SEND_FAILED)
    }
}
