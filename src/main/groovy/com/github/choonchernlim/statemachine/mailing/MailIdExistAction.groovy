package com.github.choonchernlim.statemachine.mailing

import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action
import org.springframework.stereotype.Service

@Service
class MailIdExistAction implements Action {
    @Override
    void execute(final StateContext context) {
        println "mail id [${context.getMessageHeader(MailingMetadata.SendMailEvent.MAIL_ID)}] exist action..."
    }
}
