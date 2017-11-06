package com.github.choonchernlim

import com.github.choonchernlim.statemachine.mailing.MailingEvent
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

    final StateMachine<String, String> stateMachine

    @Autowired
    Application(final StateMachine<String, String> stateMachine) {
        this.stateMachine = stateMachine
    }

    @Override
    void run(final String... strings) {
        stateMachine.sendEvent(
                MessageBuilder.
                        withPayload(MailingEvent.SendMail.EVENT_NAME).
                        setHeader(MailingEvent.SendMail.MAIL_ID, 1).
                        build())

        stateMachine.sendEvent(
                MessageBuilder.
                        withPayload(MailingEvent.SendMail.EVENT_NAME).
                        setHeader(MailingEvent.SendMail.MAIL_ID, 1).
                        build())

    }

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}