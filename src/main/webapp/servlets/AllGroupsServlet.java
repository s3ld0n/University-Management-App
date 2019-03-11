package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.foxminded.university.dao.crud_dao_implementations.GroupDao;
import com.foxminded.university.domain.Group;

@WebServlet("/groups")
public class AllGroupsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Group> groups = new GroupDao().findAll();

        request.setAttribute("groupList", groups);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/all_groups.jsp");
        dispatcher.forward(request, response);
    }
}
