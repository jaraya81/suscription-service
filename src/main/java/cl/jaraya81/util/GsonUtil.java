package cl.jaraya81.util;

import java.util.Objects;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cl.jaraya81.enums.ErrorCode;
import cl.jaraya81.exception.SubscriptionException;

public class GsonUtil {

	public static String to(Object object) throws SubscriptionException {
		if (Objects.isNull(object)) {
			throw new SubscriptionException("object cannot be null", ErrorCode.ERROR);
		}
		return new GsonBuilder().setPrettyPrinting().create().toJson(object);
	}

	public static <T> T to(String json, Class<T> clazz) throws SubscriptionException {
		if (Strings.isNullOrEmpty(json)) {
			throw new SubscriptionException("JSON cannot be null or empty", ErrorCode.ERROR);
		}
		if (Objects.isNull(clazz)) {
			throw new SubscriptionException("Class required", ErrorCode.ERROR);
		}
		return new Gson().fromJson(json, clazz);
	}

}
