package com.github.choonchernlim

import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext

@Slf4j
@SpringBootApplication
class Application implements CommandLineRunner {

    final StateMachineFactory<MailingMetadata.State, MailingMetadata.Event> stateMachineFactory

    @Autowired
    Application(final StateMachineFactory<MailingMetadata.State, MailingMetadata.Event> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory
    }

    @Override
    void run(final String... strings) {
        setCurrentStateAndSendEvent(MailingMetadata.State.WAITING, MessageBuilder.
                withPayload(MailingMetadata.Event.SEND_MAIL).
                setHeader(MailingMetadata.SendMailEvent.MAIL_ID, 1).
                build())

        setCurrentStateAndSendEvent(MailingMetadata.State.SEND_SUCCESS, MessageBuilder.
                withPayload(MailingMetadata.Event.SEND_MAIL).
                setHeader(MailingMetadata.SendMailEvent.MAIL_ID, 1).
                build())
    }

    private void setCurrentStateAndSendEvent(final MailingMetadata.State currentState,
                                             final Message<MailingMetadata.Event> event) {

        final StateMachine stateMachine = stateMachineFactory.getStateMachine(MailingMetadata.MACHINE_ID)

        stateMachine.stateMachineAccessor.withAllRegions().each {
            it.resetStateMachine(new DefaultStateMachineContext(currentState,
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
        SpringApplication.run(Application, args)
    }
}