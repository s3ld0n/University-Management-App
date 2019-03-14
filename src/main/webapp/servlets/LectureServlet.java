package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.LectureDao;
import com.foxminded.university.domain.Lecture;

@WebServlet("/lecture")
public class LectureServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int lectureId = Integer.parseInt(request.getParameter("id"));
        Lecture lecture = new LectureDao().findById(lectureId);
        
        request.setAttribute("lecture", lecture);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/lecture.jsp");
        dispatcher.forward(request, response);
    }
}
