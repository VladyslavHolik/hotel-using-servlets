package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.exception.EntityExistsException;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.EncoderService;
import holik.hotel.servlet.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EncoderService encoderService;
    @InjectMocks
    private DefaultUserService userService;

    @Test
    public void createUser() throws EntityExistsException {
        User user = new User();
        userService.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void getUserById() {
        userService.getUserById(2);
        verify(userRepository).getUserById(2);
    }

    @Test
    public void getUserByEmail() {
        userService.getUserByEmail("email");
        verify(userRepository).getUserByEmail("email");
    }

    @Test
    public void areCredentialsTrueIfTrue() {
        User user = new User();
        String salt = "salt";
        String hash = "hash";
        user.setSalt(salt);
        user.setPasswordHash(hash);

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(encoderService.areHashesEqual(salt, hash, "password")).thenReturn(true);

        boolean result = userService.areCredentialsTrue("email", "password");
        assertTrue(result);
    }

    @Test
    public void areCredentialsTrueIfFalseCase1() {
        User user = new User();
        String salt = "salt";
        String hash = "hash";
        user.setSalt(salt);
        user.setPasswordHash(hash);

        when(userRepository.getUserByEmail("email")).thenReturn(Optional.of(user));
        when(encoderService.areHashesEqual(salt, hash, "password")).thenReturn(false);

        boolean result = userService.areCredentialsTrue("email", "password");
        assertFalse(result);
    }

    @Test
    public void areCredentialsTrueIfFalseCase2() {
        when(userRepository.getUserByEmail("email")).thenReturn(Optional.empty());

        boolean result = userService.areCredentialsTrue("email", "password");
        assertFalse(result);
    }
}