package com.github.choonchernlim.statemachine.mailing

class MailingMetadata {
    public static final String MACHINE_ID = 'MAILING_STATE_MACHINE'

    static enum Event {
        SEND_MAIL
    }

    enum State {
        WAITING,
        SEND_SUCCESS,
        SEND_FAILED,
        SHOULD_SEND_MAIL_CHOICE
    }

    static class SendMailEvent {
        public static final String MAIL_ID = 'MAIL_ID'
    }
}
