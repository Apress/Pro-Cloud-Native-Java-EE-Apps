package com.example.jwallet.account.user.control;

import com.example.jwallet.account.user.entity.UserResponse;
import com.example.jwallet.wallet.wallet.boundary.MessageOption;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.enterprise.event.Reception;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.enterprise.inject.spi.EventMetadata;

@RequestScoped
public class MessageCenter {

	public void sendUserSms(
			@Observes(during = TransactionPhase.AFTER_SUCCESS, notifyObserver = Reception.IF_EXISTS) @MessageOption(MessageOption.MessageType.SMS)
			final UserResponse userResponse, final EventMetadata eventMetadata) {

		//TODO --> Send SMS through gateway... Blocking task
	}

	public void sendUserSmsAsync(
			@ObservesAsync @MessageOption(MessageOption.MessageType.SMS) final UserResponse userResponse) {
		//TODO --> Send SMS through gateway... Async
	}

	public void sendEmail(
			@ObservesAsync @MessageOption(MessageOption.MessageType.EMAIL) final UserResponse userResponse) {
		//TODO --> send email asynchronously
	}

	public void sendMessage(@ObservesAsync final UserResponse userResponse) {
		//This observer will be called for ALL events fired by the Event<UserResponse> objects,
		// irrespective of qualifiers because this observer implicitly has the @Any qualifier.
	}
}
