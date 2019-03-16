package servlets.student;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.GroupDao;
import com.foxminded.university.dao.crud_dao_implementations.StudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

@WebServlet("/student-find")
public class StudentFindServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Student student = new StudentDao().findById(id);
        List<Group> groups = new GroupDao().findAll();

        request.setAttribute("groups", groups);
        request.setAttribute("student", student);
        request.getRequestDispatcher("jsp/student/student.jsp").forward(request, response);
    }
}
