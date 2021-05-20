package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.service.EncoderService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultEncoderServiceTest {
    private EncoderService encoderService;

    @Before
    public void setUp() {
        encoderService = new DefaultEncoderService();
    }

    @Test
    public void generateRandomSalt() {
        byte[] salt = encoderService.generateRandomSalt();
        assertEquals(16, salt.length);
    }

    @Test
    public void generateHash() {
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        byte[] hash = encoderService.generateHash(salt, "password");
        assertNotEquals(0, hash.length);
    }
}