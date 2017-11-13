package com.github.choonchernlim.statemachine.mailing

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Slf4j
@Service
@SuppressWarnings("GrMethodMayBeStatic")
class MailingService {

    final MailingStateMachineService mailingStateMachineService

    @Autowired
    MailingService(MailingStateMachineService mailingStateMachineService) {
        this.mailingStateMachineService = mailingStateMachineService
    }

    boolean sendMail(final MailingState initialState, final Long mailId) {
        assert initialState
        assert mailId

        final String event = MailingEvent.SEND_MAIL.name()

        final Map<String, Boolean> eventResults = mailingStateMachineService.setInitialStateThenExecute(initialState, [
                MessageBuilder.
                        withPayload(event).
                        setHeader(MailingMetadata.MAIL_ID, mailId).
                        build()
        ])

        return eventResults[event]
    }
}