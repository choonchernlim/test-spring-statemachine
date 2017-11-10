package com.github.choonchernlim.statemachine.mailing

import org.springframework.statemachine.StateContext
import org.springframework.statemachine.guard.Guard
import org.springframework.stereotype.Service

@Service
class MailIdExistGuard implements Guard {
    @Override
    boolean evaluate(final StateContext context) {
        return context.getMessageHeader(MailingMetadata.SendMailEvent.MAIL_ID)
    }
}
