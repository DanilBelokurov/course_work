package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Blob;

import database.Attachment;
import database.Database;
import database.FilesToDB;
import database.TeacherDB;
import model.User;

public class TeacherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private User user;
	private ArrayList<String> dates;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		user = (User) session.getAttribute("user");
		dates = Database.inportDates(user.getId());

		if (user.getRole() == 0) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/student.jsp");
			dispatcher.forward(request, response);
		} else {

			TeacherDB TDB = new TeacherDB();

			if (request.getParameter("work_id") != null) {
				FilesToDB fToDB = new FilesToDB();
				Attachment attachment = fToDB.inport(request, response, request.getParameter("work_id"));

				if (attachment == null)
					response.getWriter().write("No data found");

				String fileName = attachment.getFileName();

				String contentType = URLConnection.guessContentTypeFromName(fileName);
				response.setHeader("Content-Type", contentType);
				try {
					response.setHeader("Content-Length", String.valueOf(attachment.getFileData().length()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getFileName() + "\"");

				Blob fileData = attachment.getFileData();
				InputStream is = null;
				try {
					is = fileData.getBinaryStream();
				} catch (SQLException e) {
				}

				byte[] bytes = new byte[1024];
				int bytesRead;

				while ((bytesRead = is.read(bytes)) != -1) {
					response.getOutputStream().write(bytes, 0, bytesRead);
				}

				is.close();

			} else {

				if (request.getParameter("group") != null) {
					TDB = new TeacherDB(user.getId(), Integer.parseInt((String) request.getParameter("group")), dates);
				} else {
					TDB = new TeacherDB(user.getId(), 0, dates);
				}

				request.setAttribute("tdb", TDB);
				request.setAttribute("name", user.getName());

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/teacher.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		TeacherDB TDB = new TeacherDB();
		try {

			if (request.getParameter("work_check") != null || request.getParameter("work_check1") != null) {

				System.out.println(request.getParameter("work_check"));
				System.out.println(request.getParameter("work_check1"));
				
				String[] tmp;
				
				if(request.getParameter("work_check") != null)
					tmp = request.getParameter("work_check").split("__");
				else
					tmp = request.getParameter("work_check1").split("__");
					
				FilesToDB toDB = new FilesToDB();

				toDB.setReportStatus(tmp[0], tmp[1]);

			} else {
				String up = request.getParameter("currValue");
				String mark = request.getParameter("newMark");
				String up1 = request.getParameter("currValue1");
				String mark1 = request.getParameter("newMark1");
				String id = user.getId();

				TDB = new TeacherDB(id, 0, dates);

				if (up != null)
					TDB.update(up, mark);
				else
					TDB.update(up1, mark1);
			}

			request.setAttribute("tdb", TDB);
			request.setAttribute("name", user.getName());

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/teacher.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}