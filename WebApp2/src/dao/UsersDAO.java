package dao;

import java.util.List;

import model.User;

public interface UsersDAO {
	boolean createUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(User user);
	User getUserById(User user);
	User getUserByName(User user);
	User getUserByEmail(User user);
	
	List<User> getAllUsers();
	 boolean setActive(User user, boolean isActive);
}
