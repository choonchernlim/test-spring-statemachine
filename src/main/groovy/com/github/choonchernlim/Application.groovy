package com.github.choonchernlim

import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine

@Slf4j
@SpringBootApplication
class Application implements CommandLineRunner {

    final StateMachine<MailingMetadata.State, MailingMetadata.Event> stateMachine

    @Autowired
    Application(final StateMachine<MailingMetadata.State, MailingMetadata.Event> stateMachine) {
        this.stateMachine = stateMachine
    }

    @Override
    void run(final String... strings) {
//        stateMachine.sendEvent(
//                MessageBuilder.
//                        withPayload(MailingMetadata.Event.SEND_MAIL).
//                        build())

        stateMachine.sendEvent(
                MessageBuilder.
                        withPayload(MailingMetadata.Event.SEND_MAIL).
                        setHeader(MailingMetadata.SendMailEvent.MAIL_ID, 1).
                        build())
    }

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}