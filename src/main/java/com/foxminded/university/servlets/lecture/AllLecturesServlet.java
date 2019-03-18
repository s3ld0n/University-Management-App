package com.foxminded.university.servlets.lecture;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.impl.LectureDaoImpl;
import com.foxminded.university.domain.Lecture;

@WebServlet("/lectures")
public class AllLecturesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Lecture> lectures = new LectureDaoImpl().findAll();

        request.setAttribute("lectures", lectures);
        request.getRequestDispatcher("jsp/lecture/all_lectures.jsp").forward(request, response);
    }
}
