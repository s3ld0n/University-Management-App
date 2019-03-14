package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.LectureDao;
import com.foxminded.university.domain.Lecture;

@WebServlet("/lectures")
public class AllLecturesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Lecture> lectures = new LectureDao().findAll();

        request.setAttribute("lecturesList", lectures);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/all_lectures.jsp");
        dispatcher.forward(request, response);
    }

}
