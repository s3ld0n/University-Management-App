package com.foxminded.university.servlets.lecture_hall;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.LectureHallDao;
import com.foxminded.university.dao.PeriodDao;
import com.foxminded.university.dao.impl.LectureHallDaoImpl;
import com.foxminded.university.dao.impl.PeriodDaoImpl;
import com.foxminded.university.domain.LectureHall;
import com.foxminded.university.utils.Period;

@WebServlet("/lecture-hall")
public class LectureHallServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LectureHallDao lectureHallDao;
    private PeriodDao periodDao;
    
    @Override
    public void init() throws ServletException {
        lectureHallDao = new LectureHallDaoImpl();
        periodDao = new PeriodDaoImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int lectureHallId = Integer.parseInt(request.getParameter("id"));
        LectureHall lectureHall = lectureHallDao.findById(lectureHallId);
        
        Set<Period> periods = periodDao.findAllByLectureHallId(lectureHallId);
        
        request.setAttribute("lectureHall", lectureHall);
        request.setAttribute("periods", periods);
        
        request.getRequestDispatcher("jsp/lecture_hall/lecture_hall.jsp").forward(request, response);
    }
}
