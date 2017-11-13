package com.github.choonchernlim.testspringstatemachine.statemachine

import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingService
import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MailingServiceSpec extends Specification {

    @Autowired
    MailingService mailingService

    def "given initial state as WAITING, should return true"() {
        when:
        def bool = mailingService.fireSendMailEvent(MailingState.WAITING, 1)

        then:
        bool
    }

    def "given initial state as SEND_SUCCESS, should return false"() {
        when:
        def bool = mailingService.fireSendMailEvent(MailingState.SEND_SUCCESS, 1)

        then:
        !bool
    }

}
