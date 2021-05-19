package holik.hotel.servlet.service;

/**
 * Interface for encoder.
 */
public interface EncoderService {
	byte[] generateRandomSalt();
	byte[] generateHash(byte[] salt, String password);
	boolean areHashesEqual(String salt, String hash, String password);
}
