package com.foxminded.university.servlets.lecture_hall;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.LectureHallDao;
import com.foxminded.university.dao.crud_dao_implementations.PeriodDao;
import com.foxminded.university.domain.LectureHall;
import com.foxminded.university.utils.Period;

@WebServlet("/lecture-hall")
public class LectureHallServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int lectureHallId = Integer.parseInt(request.getParameter("id"));
        LectureHall lectureHall = new LectureHallDao().findById(lectureHallId);
        
        Set<Period> periods = new PeriodDao().findAllByLectureHallId(lectureHallId);
        
        request.setAttribute("lectureHall", lectureHall);
        request.setAttribute("periods", periods);
        
        request.getRequestDispatcher("jsp/lecture_hall/lecture_hall.jsp").forward(request, response);
    }
}
