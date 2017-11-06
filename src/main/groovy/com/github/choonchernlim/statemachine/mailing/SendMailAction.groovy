package com.github.choonchernlim.statemachine.mailing

import groovy.util.logging.Slf4j
import org.springframework.statemachine.annotation.EventHeaders
import org.springframework.statemachine.annotation.OnTransition
import org.springframework.statemachine.annotation.WithStateMachine

@Slf4j
@WithStateMachine(id = MailingStateMachineConstant.MACHINE_ID)
@SuppressWarnings(['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'])
class SendMailAction {
    @OnTransition(source = MailingState.WAITING, target = MailingState.SENT_SUCCESSFULLY)
    void run(@EventHeaders final Map<String, Object> headers) {
        log.info("ACTION: ${MailingState.WAITING} -> ${MailingState.SENT_SUCCESSFULLY}: Sending mail ${headers}..")
    }
}
