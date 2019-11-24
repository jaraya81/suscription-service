package jaraya81.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jaraya81.exception.SuscriptionException;
import jaraya81.util.EncryptUtils;
import jaraya81.util.GsonUtil;
import jaraya81.util.SuscriptionUtil;
import jaraya81.vo.Suscription;
import jaraya81.vo.SuscriptionBox;
import jaraya81.vo.SuscriptionRequest;
import jaraya81.vo.SuscriptionResponse;

@RestController
@RequestMapping(path = "/suscription")
public class SuscriptionController {

	private static final String SECRET = "Lhm9Uzm3";

	@Value(value = "${path.suscriptions:suscriptions}")
	private String pathSuscriptions;

	@PostMapping("/get")
	@ResponseBody
	public SuscriptionBox getSuscription(@RequestBody SuscriptionBox request) throws SuscriptionException {
		if (Objects.isNull(request)) {
			return null;
		}

		SuscriptionRequest susc = new Gson().fromJson(
				EncryptUtils.decrypt(EncryptUtils.dBase64(request.getSecret()), SECRET), SuscriptionRequest.class);
		Suscription suscription = SuscriptionUtil.get(pathSuscriptions, susc.getUser());
		if (Objects.isNull(request)) {
			return null;
		}

		return SuscriptionBox.builder()

				.secret(EncryptUtils.eBase64(EncryptUtils.encrypt(GsonUtil.to(SuscriptionResponse.builder()

						.message(suscription.getMessage())

						.secret(suscription.getSecret())

						.user(suscription.getUser())

						.expiration(suscription.getExpiration())

						.valid(suscription.getValid())

						.build()), SECRET)))

				.build();

	}

}
