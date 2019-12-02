package cl.jaraya81.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cl.jaraya81.exception.SubscriptionException;
import cl.jaraya81.util.EncryptUtils;
import cl.jaraya81.util.GsonUtil;
import cl.jaraya81.util.SuscriptionUtil;
import cl.jaraya81.vo.Subscription;
import cl.jaraya81.vo.SubscriptionBox;
import cl.jaraya81.vo.SubscriptionRequest;
import cl.jaraya81.vo.SubscriptionResponse;

@RestController
@RequestMapping(path = "/subscription")
class SubscriptionController {

	private static final String SECRET = "Lhm9Uzm3";

	@Value(value = "${path.subscriptions:subscriptions}")
	private String pathSubscriptions;

	@PostMapping("/get")
	@ResponseBody
	public SubscriptionBox getSubscription(@RequestBody SubscriptionBox request) throws SubscriptionException {
		if (Objects.isNull(request)) {
			return null;
		}

		SubscriptionRequest susc = new Gson().fromJson(
				EncryptUtils.decrypt(EncryptUtils.dBase64(request.getSecret()), SECRET), SubscriptionRequest.class);
		Subscription subscription = SuscriptionUtil.get(pathSubscriptions, susc.getUser());

		return SubscriptionBox.builder()

				.secret(EncryptUtils.eBase64(EncryptUtils.encrypt(GsonUtil.to(SubscriptionResponse.builder()

						.message(subscription.getMessage())

						.secret(subscription.getSecret())

						.user(subscription.getUser())

						.expiration(subscription.getExpiration())

						.valid(subscription.getValid())

						.build()), SECRET)))

				.build();

	}

}
