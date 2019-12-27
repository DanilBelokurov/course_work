package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final HttpSession session = req.getSession();
        
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");
        session.invalidate();
        
        
        resp.sendRedirect("login");
    }
}