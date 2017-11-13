package com.github.choonchernlim.testspringstatemachine

import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingService
import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingState
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
class Application implements CommandLineRunner {

    final MailingService mailingService

    @Autowired
    Application(final MailingService mailingService) {
        this.mailingService = mailingService
    }

    @Override
    void run(final String... strings) {
        log.info("Accepted? " + mailingService.fireSendMailEvent(MailingState.WAITING, 1))
        log.info("Accepted? " + mailingService.fireSendMailEvent(MailingState.SEND_SUCCESS, 1))
    }

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}

