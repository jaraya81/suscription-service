package cl.jaraya81.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import cl.jaraya81.exception.SuscriptionException;

public class EncryptUtils {

	private static final String SHA_1 = "SHA-1";
	private static final String AES = "AES";
	private static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

	private static SecretKeySpec secretKey;

	private EncryptUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static void setKey(String myKey) throws SuscriptionException {
		byte[] key;

		MessageDigest sha = null;
		try {
			key = myKey.getBytes(StandardCharsets.UTF_8);
			sha = MessageDigest.getInstance(SHA_1);
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, AES);

		} catch (NoSuchAlgorithmException e) {
			throw new SuscriptionException(e);
		}
	}

	public static String encrypt(String strToEncrypt, String secret) throws SuscriptionException {
		setKey(secret);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new SuscriptionException(e);
		}
	}

	public static String decrypt(String strToDecrypt, String secret) throws SuscriptionException {
		setKey(secret);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new SuscriptionException(e);
		}
	}

	public static String eBase64(String text) {
		return new String(Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8)));
	}

	public static String dBase64(String cipheredText) {
		return new String(Base64.getDecoder().decode(cipheredText.getBytes(StandardCharsets.UTF_8)));
	}
}
