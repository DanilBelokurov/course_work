package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.FilesToDB;
import database.StudentDB;
import model.Student;
import model.User;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user.getRole() == 1) {
			response.sendRedirect("teacher_cabinet");
		} else {

			request.setAttribute("user", user);

			ArrayList<Student> students = StudentDB.select(user.getName().replace(" ", "_") + "_" + user.getId());
			request.setAttribute("students", students);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/student.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//response.getWriter().write(request.getParameter("work_id"));

		FilesToDB exToDB = new FilesToDB();
		if (exToDB.export(request))
			System.out.println("Export complete");
		else
			System.out.println("Something went wrong");

		doGet(request, response);
	}
}