package com.github.choonchernlim.config

import com.github.choonchernlim.statemachine.core.StateMachineListener
import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer
import org.springframework.statemachine.config.model.StateMachineModelFactory
import org.springframework.statemachine.uml.UmlStateMachineModelFactory

@Configuration
@EnableStateMachineFactory(name = "UmlStateMachineFactory")
class UmlMailingStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

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
        return new UmlStateMachineModelFactory('file:/Users/limc/Documents/development/workspace/eclipse/sts/sts.uml')
    }
}
