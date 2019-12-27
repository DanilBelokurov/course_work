package database;

import java.sql.*;
import java.util.ArrayList;

import model.Teacher;

public class TeacherDB {
	private static String url = "jdbc:mysql://localhost:3306/example";
	private static String username = "root";
	private static String password = "root";
	private static String subjectName;
	private static int group;
	private static ArrayList<Integer> groups = new ArrayList<>();
	private static ArrayList<String> dates = new ArrayList<>();

	public TeacherDB() {

	}

	public TeacherDB(String id, int idx, ArrayList<String> dates) {

		String[] tmp = id.split("_");
		TeacherDB.subjectName = tmp[0];

		if (groups.size() < 5) {
			for (int i = 0; i < tmp.length; i++) {
				if (i != 0)
					groups.add(Integer.parseInt(tmp[i]));
			}
		}

		if (idx == 0)
			TeacherDB.group = groups.get(idx);
		else
			TeacherDB.group = idx;

		if (TeacherDB.dates.size() == 0) {
			for (int i = 0; i < dates.size(); i++) {
				TeacherDB.dates.add(dates.get(i));
			}
		}
	}

	public int getGroup(int idx) {
		return groups.get(idx);
	}

	public int getCurrGroup() {
		return group;
	}

	public int getNumberGroup() {
		return groups.size();
	}

	public String getDate(int ind) {
		return dates.get(ind);
	}

	public int getNumberOfDates() {
		return dates.size();
	}

	public ArrayList<Teacher> select() {
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				Statement statement = conn.createStatement();
				ResultSet list = statement.executeQuery("SHOW TABLES;");
				ArrayList<String> stTables = new ArrayList<String>();

				while (list.next()) {
					if (list.getString(1).contains(Integer.toString(group))) {
						stTables.add(list.getString(1));
					}
				}

				for (int i = 0; i < stTables.size(); i++) {

					ResultSet resultSet = statement
							.executeQuery("SELECT * FROM " + stTables.get(i) + " WHERE subject='" + subjectName + "';");

					Teacher t = new Teacher(stTables.get(i) + " " + this.subjectName);
					int k = 2;

					while (resultSet.next()) {
						while (k < 12) {
							String mark = resultSet.getString(k);
							t.setMark(mark);
							k++;
						}
					}
					teachers.add(t);

				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return teachers;
	}

	public int update(String up, String mark) {
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url, username, password)) {
				String[] tmp = up.split("_");
				String stId = tmp[0] + "_" + tmp[1] + "_" + tmp[2];
				String field = "mark" + tmp[4];
				String newMark = mark + " " + tmp[3] + " " + tmp[5];
				String sql = "UPDATE " + stId + " SET " + field + "= ? WHERE subject = ?";
				try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
					preparedStatement.setString(1, newMark);
					preparedStatement.setString(2, subjectName);
					return preparedStatement.executeUpdate();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return 0;
	}
}