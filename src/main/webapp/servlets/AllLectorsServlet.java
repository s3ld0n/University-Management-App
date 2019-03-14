package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.LectorDao;
import com.foxminded.university.domain.Lector;

@WebServlet("/lectors")
public class AllLectorsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Lector> lectors= new LectorDao().findAll();

        request.setAttribute("lectors", lectors);
        request.getRequestDispatcher("jsp/all_lectors.jsp").forward(request, response);
    }
}
