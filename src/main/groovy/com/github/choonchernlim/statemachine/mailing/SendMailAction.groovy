package com.github.choonchernlim.statemachine.mailing

import groovy.util.logging.Slf4j
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.annotation.EventHeaders
import org.springframework.statemachine.annotation.WithStateMachine

@Slf4j
@WithStateMachine(id = MailingMetadata.MACHINE_ID)
@SuppressWarnings(['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'])
class SendMailAction {
    @MailingOnTransition(source = MailingMetadata.State.WAITING, target = MailingMetadata.State.SENT_SUCCESSFULLY)
    void run(final StateMachine stateMachine, @EventHeaders final Map<String, Object> headers) {
        log.info(
                "ACTION: ${MailingMetadata.State.WAITING} -> ${MailingMetadata.State.SENT_SUCCESSFULLY}: Sending mail ${headers}..")

        Thread.sleep(2000)

        log.info('ACTION DONE!')
    }
}
