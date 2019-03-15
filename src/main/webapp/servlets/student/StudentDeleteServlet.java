package servlets.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.StudentDao;

@WebServlet("/student-delete-servlet")
public class StudentDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        new StudentDao().deleteById(id);
        response.sendRedirect(request.getContextPath() + "/students");
    }

}
