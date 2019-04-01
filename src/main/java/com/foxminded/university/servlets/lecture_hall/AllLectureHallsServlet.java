package com.foxminded.university.servlets.lecture_hall;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.LectureHallDao;
import com.foxminded.university.dao.sql.LectureHallDaoImpl;
import com.foxminded.university.domain.LectureHall;

@WebServlet("/lecture-halls")
public class AllLectureHallsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LectureHallDao lectureHallDao;
    
    @Override
    public void init() throws ServletException {
        lectureHallDao = new LectureHallDaoImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<LectureHall> lectureHalls = lectureHallDao.findAll();

        request.setAttribute("lectureHalls", lectureHalls);
        request.getRequestDispatcher("jsp/lecture_hall/all_lecture_halls.jsp").forward(request, response);
    }
}
