package database;

import java.sql.*;
import java.util.ArrayList;

import model.Student;

public class StudentDB {

	private static String url = "jdbc:mysql://localhost:3306/example";
	private static String username = "root";
	private static String password = "root";

	
	public static ArrayList<Student> select(String tableName) {
		ArrayList<Student> students = new ArrayList<Student>();
		ArrayList<String> marks = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				Statement statement = conn.createStatement();
				{
					ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
					while (resultSet.next()) {
						
						String subject = resultSet.getString(1);
						Student st = new Student(tableName+" "+subject);
						
						for(int i = 2; i < 12; i++) {
							st.setMark(resultSet.getString(i));
						}
						
						students.add(st);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return students;
	}
}