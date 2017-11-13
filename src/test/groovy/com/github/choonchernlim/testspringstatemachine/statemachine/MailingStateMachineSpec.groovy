package com.github.choonchernlim.testspringstatemachine.statemachine

import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingEvent
import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingMetadata
import com.github.choonchernlim.testspringstatemachine.statemachine.mailing.MailingState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.test.StateMachineTestPlanBuilder
import spock.lang.Specification

@SpringBootTest
class MailingStateMachineSpec extends Specification {

    @Autowired
    StateMachineFactory<String, String> stateMachineFactory

    StateMachineTestPlanBuilder<String, String> builder

    def setup() {
        builder = StateMachineTestPlanBuilder.builder().
                defaultAwaitTime(2).
                stateMachine(stateMachineFactory.getStateMachine(MailingMetadata.MACHINE_ID)).
                step().
                expectStates(MailingState.WAITING.name()).
                and()
    }

    def "given SEND_MAIL event with no MAIL_ID, should results SEND_FAILED"() {
        expect:
        builder.
                step().
                sendEvent(MailingEvent.SEND_MAIL.name()).
                expectStateChanged(1).
                expectStates(MailingState.SEND_FAILED.name()).
                and().
                build().
                test()
    }

    def "given SEND_MAIL event with MAIL_ID, should results SEND_SUCCESS"() {
        expect:
        builder.
                step().
                sendEvent(MessageBuilder.
                                  withPayload(MailingEvent.SEND_MAIL.name()).
                                  setHeader(MailingMetadata.MAIL_ID, 1).
                                  build()).
                expectStateChanged(1).
                expectStates(MailingState.SEND_SUCCESS.name()).
                and().
                build().
                test()
    }

    def "given 2 valid SEND_MAIL events, should results 1 SEND_SUCCESS and ignore second event"() {
        expect:
        builder.
                step().
                sendEvent(MessageBuilder.
                                  withPayload(MailingEvent.SEND_MAIL.name()).
                                  setHeader(MailingMetadata.MAIL_ID, 1).
                                  build()).
                expectStateChanged(1).
                expectStates(MailingState.SEND_SUCCESS.name()).
                and().
                step().
                sendEvent(MessageBuilder.
                                  withPayload(MailingEvent.SEND_MAIL.name()).
                                  setHeader(MailingMetadata.MAIL_ID, 1).
                                  build()).
                expectEventNotAccepted(1).
                expectStates(MailingState.SEND_SUCCESS.name()).
                and().
                build().
                test()
    }

}
