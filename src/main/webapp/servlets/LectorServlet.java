package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.LectorDao;
import com.foxminded.university.dao.crud_dao_implementations.SubjectDao;
import com.foxminded.university.domain.Lector;
import com.foxminded.university.domain.Subject;

@WebServlet("/lector")
public class LectorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int lectorId = Integer.parseInt(request.getParameter("id"));
        Lector lector = new LectorDao().findById(lectorId);
        
        List<Subject> subjects = new SubjectDao().findAllByLectorId(lectorId);
        
        request.setAttribute("lector", lector);
        request.setAttribute("subjects", subjects);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/lector.jsp");
        dispatcher.forward(request, response);
    }
}
