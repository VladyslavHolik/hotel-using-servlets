package holik.hotel.servlet.persistence.impl;

import java.util.Optional;

import holik.hotel.servlet.models.User;
import holik.hotel.servlet.persistence.UserRepository;

public class UserRepositoryImpl implements UserRepository {

	@Override
	public boolean createUser(User user) {
		System.out.println("user created " + user);
		return false;
	}

	@Override
	public Optional<User> getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
