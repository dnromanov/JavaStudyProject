package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.User;
import util.DBUtils;

public class RolesDAOImpl implements RolesDAO{

	@Override
	public boolean createRole(Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRole(Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role getRoleById(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Role dbRole = new Role();
        String sql = "SELECT ID, NAME FROM USERS_DB.ROLES WHERE ID = ?";
        
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, role.getId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                dbRole.setId(role.getId());
                dbRole.setName(rs.getString("NAME"));
                return dbRole;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return null;
	}

	@Override
    public Role getRoleByName(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Role dbRole = new Role();
        String sql = "SELECT ID, NAME FROM USERS_DB.ROLES WHERE NAME = ?";
        
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role.getName().toUpperCase());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                dbRole.setId(rs.getInt("ID"));
                dbRole.setName(role.getName());
                return dbRole;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return null;
    }

	@Override
	public List<Role> getAllRoles() {
		Connection conn = null;
        PreparedStatement pstmt = null;
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT ID, NAME FROM USERS_DB.ROLES";
        
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Role dbRole = new Role();
                dbRole.setId(rs.getInt("ID"));
                dbRole.setName(rs.getString("NAME"));
                roles.add(dbRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
	}



}
