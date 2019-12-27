
package servlets;

import java.io.IOException;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;*/
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Database;
import model.User;

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/signup.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* Name + Login + Password getting */
		String name = request.getParameter("name");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String subject = new String();
		int role = 0;
		String id;
		String[] dates;
		ArrayList<Integer> group = new ArrayList<Integer>();

		/* Role getting: 0 -- student */
		String roleTmp = request.getParameter("role");
		if (roleTmp.equals("Teacher")) {
			role = 1;
		}

		/* Subject, if teacher */
		if (role == 1) {
			subject = request.getParameter("subject");
		}

		/* id = [subject]_group/groups */
		String[] groups = request.getParameterValues("groups[]");
		StringBuilder idTmp = new StringBuilder();

		if (role == 1)
			idTmp.append(subject).append("_");

		for (String data : groups) {
			idTmp.append(data);
			group.add(Integer.parseInt(data));
			if (role == 1)
				idTmp.append("_");
		}

		if (role == 1)
			id = idTmp.substring(0, idTmp.length() - 1);
		else
			id = idTmp.toString();
		
		/* Export lessons dates to DB */
		if(role == 1) {
			dates = request.getParameterValues("dates[]");
			
			ArrayList<String> datesList = new ArrayList<String>();
			
			for (int i = 0; i < dates.length; i++) {
				StringBuilder str = new StringBuilder();
				String[] tmp = dates[i].split("-");
				str.append(tmp[2]).append(".");
				str.append(tmp[1]).append(".");
				str.append(tmp[0]);
				datesList.add(str.toString());
			}
			
			Database.exportDates(datesList, id);
		}

		User user = new User(name, login, password, subject, role, id);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		if (user.getRole() == 0)
			response.sendRedirect("lk");
		else
			response.sendRedirect("teacher_cabinet");
		
	}
}