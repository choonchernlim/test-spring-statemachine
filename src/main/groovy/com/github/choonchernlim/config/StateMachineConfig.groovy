package com.github.choonchernlim.config

import com.github.choonchernlim.service.Events
import com.github.choonchernlim.service.States
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer

@Slf4j
@Configuration
@EnableStateMachine
class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    void configure(final StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.
                withConfiguration().
                autoStartup(true).
                listener(new StateMachineListener())
    }

    @Override
    void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states.
                withStates().
                initial(States.STATE1).
                end(States.END).
                states(EnumSet.allOf(States)).
                state(States.STATE1, { ctx ->
                    log.info("########## Handling ${States.STATE1}: " + ctx.extendedState.variables.put('mycounter', 0))
                }).
                state(States.STATE2, { ctx ->
                    log.info("########## Handling ${States.STATE2}: " + ctx.extendedState.variables.put('mycounter', 0))
                })
    }

    @Override
    void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions.
                withExternal().
                source(States.STATE1).target(States.STATE2).event(Events.EVENT1).
                and().
                withExternal().
                source(States.STATE2).target(States.STATE1).event(Events.EVENT2).
                and().
                withExternal().
                source(States.STATE1).target(States.END).event(Events.DONE)
    }
}
