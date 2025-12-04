package ai.gebo.document.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FastHashUtil {

	public static String hash(String data) {
		if (data == null || data.trim().length() == 0)
			return "0x0";
		try {
			MessageDigest digest;
			digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(data.toUpperCase().trim().getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("No Algorithm implemented", e);
		}
	}

	public static String hash(String... data) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; data != null && i < data.length; i++) {
			buffer.append(data[i].toLowerCase());
			if (i < data.length - 1) {
				buffer.append("|");
			}
		}
		return hash(buffer.toString());
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
