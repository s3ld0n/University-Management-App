package servlets.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.StudentDao;
import com.foxminded.university.domain.Student;

@WebServlet("/student-update-servlet")
public class StudentUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String group = request.getParameter("group");
        
        new StudentDao().update(new Student(id, firstName, lastName, group));
        response.sendRedirect(request.getContextPath() + "/students");
    }

}
