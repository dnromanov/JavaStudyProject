package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Role;
import model.User;
import util.DBUtils;

public class UsersDAOImpl implements UsersDAO {

	@Override
	public boolean createUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(
					"INSERT INTO " + DBUtils.DB_NAME + ".users(NAME, PASSWORD, EMAIL, TOKEN) VALUES (?, ?, ?, ?)");
			pstmt.setString(1, user.getName().toLowerCase().trim());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getToken());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				System.out.println("USER INSERTED");
				return true;
			}

			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			String sql = "UPDATE " + DBUtils.DB_NAME + ".users SET NAME = ? , PASSWORD = ? , EMAIL = ? , STATUS = ? , TOKEN = ? , TOKEN_TIMESTAMP = ? ,  ROLE_ID = ? WHERE UID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getStatus());
			pstmt.setString(5, user.getToken());
			pstmt.setTimestamp(6, user.getTokenTimestamp());
			pstmt.setInt(7, user.getRole().getId());
			pstmt.setInt(8, user.getUid());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				DBUtils.release(conn, null, pstmt);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM " + DBUtils.DB_NAME + ".users" + " WHERE UID = ?");
			pstmt.setInt(1, user.getUid());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				DBUtils.release(conn, null, pstmt);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}

	@Override
	public User getUserById(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT  U.UID, U.NAME, U.PASSWORD, U.EMAIL, U.STATUS, U.TOKEN, U.TOKEN_TIMESTAMP, R.NAME as 'ROLE NAME', R.ID" + 
					" FROM USERS_DB.USERS U" + 
					" LEFT JOIN USERS_DB.ROLES R" + 
					" ON U.ROLE_ID = R.ID  WHERE U.UID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getUid());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setName(rs.getString("NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setStatus(rs.getString("STATUS"));
				user.setToken(rs.getString("TOKEN"));
				user.setTokenTimestamp(rs.getTimestamp("TOKEN_TIMESTAMP"));
				Role role1 = new Role(rs.getString("ROLE NAME"));
				role1.setId(rs.getInt("ID"));
				user.setRole(role1);
				return user;
			} else {
				System.out.println("CAN'T FIND CLIENT WITH UID = " + user.getUid());
				user = null;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserByName(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
//			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + ".users WHERE NAME = ?");
			
			String sql = "SELECT  U.UID, U.NAME, U.PASSWORD, U.EMAIL, U.STATUS, U.TOKEN, U.TOKEN_TIMESTAMP, R.NAME as 'ROLE NAME', R.ID" + 
					" FROM USERS_DB.USERS U" + 
					" LEFT JOIN USERS_DB.ROLES R" + 
					" ON U.ROLE_ID = R.ID  WHERE U.NAME = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName().toLowerCase());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setUid(rs.getInt("UID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setStatus(rs.getString("STATUS"));
				user.setToken(rs.getString("TOKEN"));
				user.setTokenTimestamp(rs.getTimestamp("TOKEN_TIMESTAMP"));
				
				
				Role role1 = new Role(rs.getString("ROLE NAME"));
				role1.setId(rs.getInt("ID"));
				user.setRole(role1);
				return user;
			} else {
				System.out.println("CAN'T FIND CLIENT WITH NAME = " + user.getName());
				user = null;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUserByToken(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT  U.UID, U.NAME, U.PASSWORD, U.EMAIL, U.STATUS, U.TOKEN, U.TOKEN_TIMESTAMP, R.NAME as 'ROLE NAME', R.ID" + 
					" FROM USERS_DB.USERS U" + 
					" LEFT JOIN USERS_DB.ROLES R" + 
					" ON U.ROLE_ID = R.ID  WHERE U.TOKEN = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getToken());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setUid(rs.getInt("UID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setStatus(rs.getString("STATUS"));
				user.setName(rs.getString("NAME"));
				user.setTokenTimestamp(rs.getTimestamp("TOKEN_TIMESTAMP"));
				Role role1 = new Role(rs.getString("ROLE NAME"));
				role1.setId(rs.getInt("ID"));
				user.setRole(role1);
				return user;
			} else {
				System.out.println("CAN'T FIND CLIENT WITH NAME = " + user.getName());
				user = null;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserByEmail(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + ".users WHERE EMAIL = ?");
			pstmt.setString(1, user.getEmail());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setUid(rs.getInt("UID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setName(rs.getString("NAME"));
				user.setStatus(rs.getString("STATUS"));
				user.setToken(rs.getString("TOKEN"));
				user.setTokenTimestamp(rs.getTimestamp("TOKEN_TIMESTAMP"));
				return user;
			} else {
				System.out.println("CAN'T FIND CLIENT WITH EMAIL = " + user.getEmail());
				user = null;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<User> users = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT  U.UID, U.NAME, U.PASSWORD, U.EMAIL, U.STATUS, U.TOKEN, U.TOKEN_TIMESTAMP, R.NAME as 'ROLE NAME', R.ID" + 
					" FROM USERS_DB.USERS U" + 
					" LEFT JOIN USERS_DB.ROLES R" + 
					" ON U.ROLE_ID = R.ID";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUid(rs.getInt("UID"));
				user.setName(rs.getString("NAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setStatus(rs.getString("STATUS"));
				user.setToken(rs.getString("TOKEN"));
				user.setTokenTimestamp(rs.getTimestamp("TOKEN_TIMESTAMP"));
				Role role1 = new Role(rs.getString("ROLE NAME"));
				role1.setId(rs.getInt("ID"));
				user.setRole(role1);
				users.add(user);
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}



	@Override
	public boolean setActive(User user, boolean isActive) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE " + DBUtils.DB_NAME + ".users" + " SET STATUS = ?" + " WHERE UID = ?");

			pstmt.setString(1, isActive? "T" : "F");
			pstmt.setInt(2, user.getUid());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				System.out.println("USER " + user.getName() + " was " + (isActive? "ACTIVATED" : "DEACTIVATED"));
				return true;
			}
			else {
				throw new IllegalArgumentException("USER NOT FOUND " + user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBUtils.release(conn, null, pstmt);
		return false;

	}
	
	public boolean checkUserParameter(User newUser, String param, User userExist) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + ".users WHERE " + param + " = ? AND NOT UID = ?");
			if(param.equals("EMAIL")) {
				pstmt.setString(1, newUser.getEmail());
			}
			else if(param.equals("NAME")) {
				pstmt.setString(1, newUser.getName());
			}
			else {
				throw new SQLException();
			}
			pstmt.setInt(2, userExist.getUid());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				DBUtils.release(conn, null, pstmt);
				return true;
			} else {
				DBUtils.release(conn, null, pstmt);
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return true;
	}
	
	public static void main(String[] args) {
		User user = new User("Bill", "123", "asdf");
		user.setToken("123");
		UsersDAOImpl userImpl = new UsersDAOImpl();
		userImpl.createUser(user);
		User user2 = userImpl.getUserByName(user);
		System.out.println(user2);
//		if(new Date().getTime() > user2.getTokenTimestamp().getTime()) {
//			System.out.println("You are late");
//		}
//		String result = "";
//		try {
//			FileReader fr = new FileReader("input/messageContent.txt");
//			BufferedReader br = new BufferedReader(fr);
//			result = br.readLine();
//			String a = "";
//			while ((a = br.readLine()) != null) {
//				result += a;
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String messageStart = result.substring(0, result.indexOf("actionlink"));
//		String messageMiddle = result.substring(result.indexOf("actionlink")+10, result.indexOf("token"));
//		String messageEnd = result.substring(result.indexOf("token")+5);
//		String message = messageStart + "" + messageMiddle + "" + messageEnd;
//		System.out.println(message);
	}
}
