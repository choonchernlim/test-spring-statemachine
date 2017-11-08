package com.github.choonchernlim.statemachine

import com.github.choonchernlim.config.MailingStateMachineConfig
import com.github.choonchernlim.statemachine.mailing.MailingMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.test.StateMachineTestPlanBuilder
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@Import(MailingStateMachineConfig)
@ComponentScan('com.github.choonchernlim.statemachine')
class MailingSpec extends Specification {

    @Autowired
    StateMachine<MailingMetadata.State, MailingMetadata.Event> stateMachine

    StateMachineTestPlanBuilder<MailingMetadata.State, MailingMetadata.Event> builder

    def setup() {
        builder = StateMachineTestPlanBuilder.builder().
                defaultAwaitTime(2).
                stateMachine(stateMachine).
                step().
                expectStates(MailingMetadata.State.WAITING).
                and()
    }

    def "given 2 SEND_MAIL event, only 1 event triggers state change"() {
        expect:
        builder.
                step().
                sendEvent(MailingMetadata.Event.SEND_MAIL).
                expectStateChanged(1).
                expectStates(MailingMetadata.State.SENT_SUCCESSFULLY).
                and().
                step().
                sendEvent(MailingMetadata.Event.SEND_MAIL).
                expectEventNotAccepted(1).
                and().
                build().
                test()
    }

}
