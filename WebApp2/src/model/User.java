package model;

import java.sql.Timestamp;

public class User {
	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", password=" + password + ", email=" + email + ", status="
				+ status + ", token=" + token + ", tokenTimestamp=" + tokenTimestamp + ", role=" + role + "]";
	}

	private int uid;
	private String name;
	private String password;
	private String email;
	private String status;
	private String token;
	private Timestamp tokenTimestamp;
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int uid, String name, String password, String email) {
		super();
		this.uid = uid;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User(String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTokenTimestamp() {
		return tokenTimestamp;
	}

	public void setTokenTimestamp(Timestamp tokenTimestamp) {
		this.tokenTimestamp = tokenTimestamp;
	}

}
