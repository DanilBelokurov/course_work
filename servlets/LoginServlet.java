package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Database;
import model.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String password = request.getParameter("password");

		RequestDispatcher dispatcher;

		User user = Database.isLogin(login, password);
		HttpSession session = request.getSession();

		if (user == null) {
			session.setAttribute("error", "Login or password is incorrect");
			dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
			dispatcher.forward(request, response);
		} else {

			session.setAttribute("user", user);
			
			if (user.getRole() == 0) {
				session.setAttribute("role", user.getId());
				response.sendRedirect("lk");
			} else {
				session.setAttribute("role", user.getId());
				response.sendRedirect("teacher_cabinet");
			}

		}
	}
}