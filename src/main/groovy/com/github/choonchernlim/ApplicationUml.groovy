package com.github.choonchernlim

import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext

@Slf4j
// @SpringBootApplication
class ApplicationUml implements CommandLineRunner {

    final StateMachineFactory<String, String> stateMachineFactory

    @Autowired
    ApplicationUml(@Qualifier('UmlStateMachineFactory') final StateMachineFactory<String, String> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory
    }

    @Override
    void run(final String... strings) {
        final StateMachine stateMachine = stateMachineFactory.getStateMachine(MailingMetadata.MACHINE_ID)
        stateMachine.states.each {
            println "States: ${it.id} - ${it.pseudoState?.kind?.name()}"
        }

        stateMachine.transitions.
                findAll { it.trigger }.
                each {
                    println "Events: ${it.trigger.event}"
                }

        setCurrentStateAndSendEvent(MailingMetadata.State.WAITING, MessageBuilder.
                withPayload(MailingMetadata.Event.SEND_MAIL.name()).
                setHeader(MailingMetadata.SendMailEvent.MAIL_ID, 1).
                build())

        setCurrentStateAndSendEvent(MailingMetadata.State.SEND_SUCCESS, MessageBuilder.
                withPayload(MailingMetadata.Event.SEND_MAIL.name()).
                setHeader(MailingMetadata.SendMailEvent.MAIL_ID, 1).
                build())
    }

    private void setCurrentStateAndSendEvent(final MailingMetadata.State currentState, final Message event) {
        final StateMachine stateMachine = stateMachineFactory.getStateMachine(MailingMetadata.MACHINE_ID)

        stateMachine.stateMachineAccessor.withAllRegions().each {
            it.resetStateMachine(new DefaultStateMachineContext(currentState.name(),
                                                                null,
                                                                null,
                                                                null,
                                                                null,
                                                                MailingMetadata.MACHINE_ID))
        }

        stateMachine.start()
        stateMachine.sendEvent(event)
        stateMachine.stop()
    }

    static void main(String[] args) {
        SpringApplication.run(ApplicationUml, args)
    }
}