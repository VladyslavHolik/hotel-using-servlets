package holik.hotel.servlet.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class DefaultEncoderServiceTest {
    @InjectMocks
    private DefaultEncoderService encoderService;

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