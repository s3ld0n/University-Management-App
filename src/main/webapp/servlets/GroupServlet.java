package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.GroupDao;
import com.foxminded.university.dao.crud_dao_implementations.StudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/group")
public class GroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int groupId = Integer.parseInt(request.getParameter("id"));

        Group group = new GroupDao().findById(groupId);
        List<Student> students = new StudentDao().findAllByGroupId(groupId);

        request.setAttribute("group", group);
        request.setAttribute("students", students);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/group.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
