package com.foxminded.university.servlets.lector;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.LectorDao;
import com.foxminded.university.dao.impl.LectorDaoImpl;
import com.foxminded.university.domain.Lector;

@WebServlet("/lectors")
public class AllLectorsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LectorDao lectorDao;
    
    @Override
    public void init() throws ServletException {
        lectorDao = new LectorDaoImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Lector> lectors= lectorDao.findAll();

        request.setAttribute("lectors", lectors);
        request.getRequestDispatcher("jsp/lector/all_lectors.jsp").forward(request, response);
    }
}
