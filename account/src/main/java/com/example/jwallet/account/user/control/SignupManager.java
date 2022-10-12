package com.example.jwallet.account.user.control;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import lombok.NoArgsConstructor;

@ConversationScoped
@Stateful
@NoArgsConstructor
public class SignupManager implements Serializable {

    Conversation conversation;

    @Inject
    public SignupManager(final Conversation conversation) {
        this.conversation = conversation;
    }


    public void beginSignup() {
        beginConversation();
    }

    public void submit() {
        endConversation();
    }

    private void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
}
