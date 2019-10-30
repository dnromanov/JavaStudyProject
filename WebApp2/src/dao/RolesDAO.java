package dao;

import java.util.List;

import model.Role;
import model.User;

public interface RolesDAO {
	boolean createRole(Role role);
	boolean updateRole(Role role);
	boolean deleteRole(Role role);
	Role getRoleById(Role role);
	Role getRoleByName(Role role);
	
	List<Role> getAllRoles();
}
