package com.github.choonchernlim.statemachine.mailing

import java.lang.reflect.Modifier

class MailingState {
    public static final String WAITING = 'WAITING'
    public static final String SENT_SUCCESSFULLY = 'SENT_SUCCESSFULLY'

    static Set<String> getAllStates() {
        return MailingState.declaredFields.
                findAll {
                    Modifier.isPublic(it.modifiers) &&
                    Modifier.isStatic(it.modifiers) &&
                    Modifier.isFinal(it.modifiers) &&
                    it.type == String
                }.
                collect { it.name }.
                toSet()
    }
}
