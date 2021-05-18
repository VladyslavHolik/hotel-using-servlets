package holik.hotel.servlet.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Interface for encoder.
 */
public interface EncoderService {
	byte[] generateRandomSalt();
	byte[] generateHash(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
	boolean areHashesEqual(String salt, String hash, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
}