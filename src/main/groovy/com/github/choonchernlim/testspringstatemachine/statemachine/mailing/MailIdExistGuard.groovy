package com.github.choonchernlim.testspringstatemachine.statemachine.mailing

import org.springframework.statemachine.StateContext
import org.springframework.statemachine.guard.Guard
import org.springframework.stereotype.Service

/**
 * MAIL_ID should exist.
 */
@Service
class MailIdExistGuard implements Guard {
    @Override
    boolean evaluate(final StateContext context) {
        return context.getMessageHeader(MailingMetadata.MAIL_ID)
    }
}
