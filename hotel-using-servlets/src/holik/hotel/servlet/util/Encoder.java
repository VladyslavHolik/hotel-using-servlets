package holik.hotel.servlet.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface Encoder {
	byte[] generateRandomSalt();
	byte[] generateHash(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
	boolean areHashesEqual(String salt, String hash, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
