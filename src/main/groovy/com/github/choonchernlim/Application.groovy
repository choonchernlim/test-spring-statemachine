package com.github.choonchernlim

import com.github.choonchernlim.service.Events
import com.github.choonchernlim.service.States
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.statemachine.StateMachine

@Slf4j
@SpringBootApplication
class Application implements CommandLineRunner {

    final StateMachine<States, Events> stateMachine

    @Autowired
    Application(final StateMachine<States, Events> stateMachine) {
        this.stateMachine = stateMachine
    }

    @Override
    void run(final String... strings) {
        stateMachine.start()
        stateMachine.sendEvent(Events.EVENT1)
        // stateMachine.sendEvent(Events.EVENT2)
        stateMachine.sendEvent(Events.DONE)
    }

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}