package cl.jaraya81.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.google.common.base.Strings;

import cl.jaraya81.exception.SubscriptionException;
import cl.jaraya81.vo.Subscription;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuscriptionUtil {

	private static final String FILE_SEPARATOR = "/";
	private static final int COURTESY_DAYS = 2;

	public static Subscription get(String pathSuscription, String user) throws SubscriptionException {
		if (Strings.isNullOrEmpty(pathSuscription) || Strings.isNullOrEmpty(user)) {
			return null;
		}

		String userLower = user.toLowerCase();

		File file = FileUtil.get(pathSuscription + FILE_SEPARATOR + userLower);
		if (!file.exists()) {
			createSuscription(file, user);
		}

		log.info("Getting subscription for: " + user);

		Subscription suscripcion = GsonUtil.to(FileUtil.readFileToString(file, StandardCharsets.UTF_8),
				Subscription.class);
		if (Objects.isNull(suscripcion.getExpiration())
				|| suscripcion.getExpiration().isBefore(LocalDateTime.now(Clock.systemUTC()))) {
			suscripcion.setValid(false);
			suscripcion.setMessage("Your membership has expired, request a renewal");
			write(suscripcion, pathSuscription, userLower);
		}
		return suscripcion;

	}

	private static void createSuscription(File file, String user) throws SubscriptionException {
		log.info("Creating new subscription: " + user);

		FileUtil.mkdirs(file.getParent());

		Subscription suscripcion = Subscription.builder()

				.message("Welcome, you have a courtesy membership.")

				.secret(EncryptUtils.eBase64(UUID.randomUUID().toString()))

				.valid(true)

				.user(user.toLowerCase())

				.expiration(LocalDateTime.now(Clock.systemUTC()).plusDays(COURTESY_DAYS))

				.build();

		write(suscripcion, file.getAbsolutePath(), user.toLowerCase());
	}

	private static void write(Subscription suscripcion, String pathSuscriptions, String userLower)
			throws SubscriptionException {
		FileUtil.write(FileUtil.path(pathSuscriptions + FILE_SEPARATOR + userLower),
				GsonUtil.to(suscripcion).getBytes());
	}

}
