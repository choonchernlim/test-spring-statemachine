package com.github.choonchernlim.statemachine.mailing

class MailingMetadata {
    public static final String MACHINE_ID = 'MAILING_STATE_MACHINE'

    static enum Event {
        SEND_MAIL
    }

    enum State {
        WAITING,
        SENT_SUCCESSFULLY
    }

    static class SendMailEvent {
        public static final String MAIL_ID = 'MAIL_ID'
    }
}
