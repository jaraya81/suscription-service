package cl.jaraya81.util;

import java.util.Objects;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cl.jaraya81.enums.ErrorCode;
import cl.jaraya81.exception.SuscriptionException;

public class GsonUtil {

	private GsonUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String to(Object object) throws SuscriptionException {
		if (Objects.isNull(object)) {
			throw new SuscriptionException("object cannot be null", ErrorCode.ERROR);
		}
		return new GsonBuilder().setPrettyPrinting().create().toJson(object);
	}

	public static <T> T to(String json, Class<T> clazz) throws SuscriptionException {
		if (Strings.isNullOrEmpty(json)) {
			throw new SuscriptionException("JSON cannot be null or empty", ErrorCode.ERROR);
		}
		if (Objects.isNull(clazz)) {
			throw new SuscriptionException("Class required", ErrorCode.ERROR);
		}
		return new Gson().fromJson(json, clazz);
	}

}
