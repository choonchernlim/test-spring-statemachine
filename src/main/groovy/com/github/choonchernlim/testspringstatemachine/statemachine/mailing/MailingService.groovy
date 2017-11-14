package com.github.choonchernlim.testspringstatemachine.statemachine.mailing

import com.github.choonchernlim.testspringstatemachine.statemachine.core.AbstractStateMachineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.stereotype.Service

/**
 * Service class.
 */
@Service
class MailingService extends AbstractStateMachineService {

    @Autowired
    MailingService(final StateMachineFactory<String, String> stateMachineFactory) {
        super(stateMachineFactory, MailingMetadata.MACHINE_ID, MailingState.names, MailingEvent.names)
    }

    // Fires SEND_MAIL event with parameter and returns boolean indicating whether event is accepted or not
    boolean fireSendMailEvent(final MailingState initialState, final Long mailId) {
        assert initialState
        assert mailId

        return setInitialStateThenFireEvent(initialState, MailingEvent.SEND_MAIL, [(MailingMetadata.MAIL_ID): mailId])
    }
}