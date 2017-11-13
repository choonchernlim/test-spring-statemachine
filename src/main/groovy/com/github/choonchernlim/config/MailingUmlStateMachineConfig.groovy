package com.github.choonchernlim.config

import com.github.choonchernlim.statemachine.core.StateMachineListener
import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer
import org.springframework.statemachine.config.model.StateMachineModelFactory
import org.springframework.statemachine.uml.UmlStateMachineModelFactory

/**
 * UML-based configuration where states and transitions are defined in the UML file created by Eclipse Papyrus.
 */
@Profile('uml')
@Slf4j
@Configuration
@EnableStateMachineFactory
class MailingUmlStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    MailingUmlStateMachineConfig() {
        log.info('Activating MailingUmlStateMachineConfig...')
    }

    @Override
    void configure(final StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.
                withConfiguration().
                machineId(MailingMetadata.MACHINE_ID).
                listener(new StateMachineListener())
    }

    @Override
    void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
        model.
                withModel().
                factory(modelFactory())
    }

    @Bean
    StateMachineModelFactory<String, String> modelFactory() {
        return new UmlStateMachineModelFactory('classpath:uml/mailing.uml')
    }
}
