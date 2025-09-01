package ai.gebo.security.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;

@Service
public class GPasswordEncoder implements PasswordEncoder {
	private final IGeboCryptingService cryptService;

	public GPasswordEncoder(IGeboCryptingService cryptService) {
		this.cryptService = cryptService;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			// Decrypt the encoded password and compare with the raw password
			String decoded = cryptService.decrypt(encodedPassword);
			return rawPassword.toString().equals(decoded);
		} catch (GeboCryptSecretException e) {
			throw new RuntimeException("Problems in the underlying crypting system", e);
		}
	}

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			// Encrypt the raw password
			return cryptService.crypt(rawPassword.toString());
		} catch (GeboCryptSecretException e) {
			throw new RuntimeException("Problems in the underlying crypting system", e);
		}
	}
}
