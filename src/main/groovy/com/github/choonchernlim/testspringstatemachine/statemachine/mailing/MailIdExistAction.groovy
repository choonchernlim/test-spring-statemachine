package com.github.choonchernlim.testspringstatemachine.statemachine.mailing

import groovy.util.logging.Slf4j
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action
import org.springframework.stereotype.Service

/**
 * Action to send mail based on the given MAIL_ID.
 */
@Slf4j
@Service
class MailIdExistAction implements Action {
    @Override
    void execute(final StateContext context) {
        final Long mailId = (Long) context.getMessageHeader(MailingMetadata.MAIL_ID)

        log.info("Action: Sending mail -> Mail ID [${mailId}]...")
    }
}
