package com.foxminded.university.servlets.subject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.SubjectDao;
import com.foxminded.university.dao.sql.SubjectDaoImpl;
import com.foxminded.university.domain.Subject;

@WebServlet("/subjects")
public class AllSubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private SubjectDao subjectDao;
    
    @Override
    public void init() throws ServletException {
        subjectDao = new SubjectDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Subject> subjects = subjectDao.findAll();

        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("jsp/subject/all_subjects.jsp").forward(request, response);
    }
}
