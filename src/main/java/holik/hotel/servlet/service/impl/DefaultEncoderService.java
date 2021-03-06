package holik.hotel.servlet.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import holik.hotel.servlet.service.EncoderService;
import org.apache.catalina.tribes.util.Arrays;

/**
 * Default realization of encoder.
 */
public class DefaultEncoderService implements EncoderService {
	private static final Logger LOG = Logger.getLogger(DefaultEncoderService.class.getName());
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

	public byte[] generateRandomSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	public byte[] generateHash(byte[] salt, String password) {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		byte[] hash;
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			LOG.severe("Cannot find algorithm for hashing");
			throw new IllegalStateException("Invalid algorithm");
		} catch (InvalidKeySpecException e) {
			LOG.severe("Invalid keySpec");
			throw new IllegalStateException("Invalid keySpec");
		}
		return hash;
	}

	public boolean areHashesEqual(String salt, String hash, String password) {
		byte[] saltMassive = Base64.getDecoder().decode(salt);
		byte[] hashMassive = Base64.getDecoder().decode(hash);

		byte[] hashOfCurrentPassword = generateHash(saltMassive, password);
		return Arrays.equals(hashMassive, hashOfCurrentPassword);
	}
}
