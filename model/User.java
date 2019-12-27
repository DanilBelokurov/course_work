package model;

import java.util.ArrayList;

public class User {

	private String name;
	private String login;
	private String password;
	private String subject;
	private int role;
	private static String id;
	private ArrayList<Integer> group = new ArrayList<Integer>();

	public User(String name, String login, String password, String subject, int role, String groups) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
		this.subject = subject;
		this.role = role;
		id = groups;
		
		if (role == 0)
			group.add(Integer.getInteger(groups));

		if(role == 1) {
			int idx = groups.indexOf("_");
			subject = groups.substring(0,idx);
			groups = groups.substring(idx + 1);
			
			String[] groupsTmp = groups.split("_");
			for (int i = 0; i < groupsTmp.length; i++) {
				group.add(Integer.parseInt(groupsTmp[i]));
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public static String getId() {
		return id;
	}

	public void setRole(int role) {
		this.role = role;
	}
}