package cl.jaraya81.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.google.common.base.Strings;

import cl.jaraya81.enums.ErrorCode;
import cl.jaraya81.exception.SuscriptionException;

public class FileUtil {

	public static final String FILE_SEPARATOR = "/";

	private FileUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean mkdirs(String dirPath) throws SuscriptionException {
		return get(dirPath).mkdirs();
	}

	public static File get(String path) throws SuscriptionException {
		if (Strings.isNullOrEmpty(path)) {
			throw new SuscriptionException("File cannot be null or empty", ErrorCode.ERROR);
		}
		return new File(path);
	}

	public static Path write(Path path, byte[] strToBytes) throws SuscriptionException {
		if (Objects.isNull(path)) {
			throw new SuscriptionException("Path cannot be null", ErrorCode.ERROR);
		}

		try {
			return Files.write(path, strToBytes);
		} catch (IOException e) {
			throw new SuscriptionException(e);
		}
	}

	public static Path path(String path) throws SuscriptionException {
		if (Strings.isNullOrEmpty(path)) {
			throw new SuscriptionException("Path cannot be null or empty", ErrorCode.ERROR);
		}
		return Paths.get(path);
	}

	public static String readFileToString(File file, Charset utf8) throws SuscriptionException {
		if (Objects.isNull(file)) {
			throw new SuscriptionException("File cannot be null", ErrorCode.ERROR);
		}
		try {
			return org.apache.commons.io.FileUtils.readFileToString(file, utf8 != null ? utf8 : StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new SuscriptionException(e);
		}
	}

}
