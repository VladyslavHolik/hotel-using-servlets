package holik.hotel.servlet.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.impl.DefaultUserRepository;
import holik.hotel.servlet.service.EncoderService;
import holik.hotel.servlet.service.UserService;

/**
 * Default realization of user service.
 */
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final EncoderService encoderService;

    public DefaultUserService(UserRepository userRepository, EncoderService encoderService) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
    }

    @Override
    public boolean createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public boolean areCredentialsTrue(String email, String password) {
        boolean result = false;
        Optional<User> userOptional = getUserByEmail(email);

        if (userOptional != null && userOptional.isPresent()) {
            User user = userOptional.get();
            String salt = user.getSalt();
            String hash = user.getPasswordHash();

            if (encoderService.areHashesEqual(salt, hash, password)) {
                result = true;
            }
        }
        return result;
    }
}
