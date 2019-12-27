
package database;

import java.sql.*;
import java.util.ArrayList;

import model.User;

public class Database {

	@SuppressWarnings("deprecation")
	public static User isLogin(String login, String password) {

		try {
			String findQuery = "SELECT * FROM auth WHERE login = \"" + login + "\" AND password = \"" + password + "\"";

			String subject = new String();

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/course", "root", "root");
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(findQuery);

			if (!resultSet.next()) {
				return null;
			} else {

				if (resultSet.getInt(4) == 1) {
					int idx = resultSet.getString(5).indexOf("_");
					subject = resultSet.getString(5).substring(0, idx);
				} else
					subject = null;

				User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), subject,
						resultSet.getInt(4), resultSet.getString(5));

				connection.close();
				statement.close();

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static boolean export(User user) {

		try {
			/* Export to DB */
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/course", "root", "root");
			Statement statement = connection.createStatement();

			String findQuery = "SELECT * FROM auth WHERE login = \"" + user.getLogin() + "\" AND password = \""
					+ user.getPassword() + "\"";

			ResultSet resultSet = statement.executeQuery(findQuery);

			if (!resultSet.next()) {

				String sizeQuery = "SELECT COUNT( * ) FROM  auth";
				resultSet = statement.executeQuery(sizeQuery);
				resultSet.next();

				String insertQuery = "INSERT INTO course.auth (name, login, password, type, id) VALUES ('"
						+ user.getName() + "', '" + user.getLogin() + "', '" + user.getPassword() + "', '"
						+ user.getRole() + "', '" + user.getId() + "')";

				statement.executeUpdate(insertQuery);

				if (user.getRole() == 0) {

					String databaseChange = " use example;";
					statement.execute(databaseChange);

					String userName = user.getName().replace(" ", "_");
					
					String tableName = userName + "_" + user.getId();

					String query = "CREATE TABLE " + userName + "_" + user.getId()
							+ " (subject varchar(100) NOT NULL, mark0 varchar(15) NOT NULL,mark1 varchar(15), mark2 varchar(15), mark3 varchar(15), mark4 varchar(15), mark5 varchar(15), mark6 varchar(15),mark7 varchar(15), mark8 varchar(15), mark9 varchar(15));";

					statement.execute(query);
					
					statement = connection.createStatement();
					
					ResultSet list_tmp = statement.executeQuery("SHOW TABLES;");
					ResultSet list = null;

					while (list_tmp.next()) {
							if (list_tmp.getString(1).contains(User.getId())) {
								System.out.println("dsafasdfadfafasdfasd");
								insertQuery = "SELECT * FROM " + list_tmp.getString(1) + ";";
								list = statement.executeQuery(insertQuery);
								break;
							}
							statement = connection.createStatement();
					}
					
					
					statement = connection.createStatement();
					
					String qString = "INSERT INTO example." + tableName//
						+ "(subject, mark0 ,mark1, mark2, mark3, mark4, mark5, mark6,mark7, mark8, mark9 )"//
						+ "VALUES ( '" + list.getString(1) + "', "//
						+ "( '"  + list.getString(2) + "', "//
						+ "( '"  + list.getString(3) + "', " //
						+ "( '"  + list.getString(4) + "', " //
						+ "( '"  + list.getString(5) + "', " //
						+ "( '"  + list.getString(6) + "', " //
						+ "( '"  + list.getString(7) + "', " //
						+ "( '"  + list.getString(8) + "', " //
						+ "( '"  + list.getString(9) + "', " //
						+ "( '"  + list.getString(10) + "', " //
						+ "( '"  + list.getString(11) + "'); ";
					
					statement.executeQuery(qString);

				}

				statement.close();
				connection.close();

				return true;

			} else {

				statement.close();
				connection.close();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void exportDates(ArrayList<String> dates, String teacherID) {

		String[] groups = teacherID.split("_");

		StringBuilder tmpDates = new StringBuilder();
		for (int i = 0; i < dates.size(); i++) {
			if (i == 0) {
				tmpDates.append("mark='" + dates.get(i) + "'");
			} else {
				tmpDates.append(", mark" + i + "='" + dates.get(i) + "'");
			}
		}
		String updateQuery = tmpDates.toString();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/example", "root", "root");
			Statement statement = connection.createStatement();

			ResultSet list = statement.executeQuery("SHOW TABLES;");

			while (list.next()) {
				for (int i = 1; i < groups.length; i++) {
					if (list.getString(1).contains(groups[i])) {
						String insertQuery = "UPDATE example." + list.getString(1) + " SET " + updateQuery
								+ "where subject='" + groups[0] + "';";
						statement.executeUpdate(insertQuery);
					}
					statement = connection.createStatement();
				}
			}

			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static ArrayList<String> inportDates(String teacherID) {

		String[] groups = teacherID.split("_");

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/example", "root", "root");
			Statement statement = connection.createStatement();

			ResultSet list = statement.executeQuery("SHOW TABLES;");
			statement = connection.createStatement();

			while (list.next()) {
				for (int i = 1; i < groups.length; i++) {
					if (list.getString(1).contains(groups[i])) {

						String inportQuery = "SELECT * FROM example." + list.getString(1) + " WHERE subject='"
								+ groups[0] + "';";

						ResultSet row = statement.executeQuery(inportQuery);
						
						row.next();
						
						int colCount = row.getMetaData().getColumnCount();

						ArrayList<String> result = new ArrayList<String>();
						
						for(int j = 2; j <= colCount; j++) {
							
							String date = row.getString(j);
							
							if(date.length() > 10)
								result.add(date.substring(2,12));
							else
								result.add(date.substring(0, 10));
						}
						
						return result;
					}
					statement = connection.createStatement();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}