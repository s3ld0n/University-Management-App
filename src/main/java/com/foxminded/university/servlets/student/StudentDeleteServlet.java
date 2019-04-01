package com.foxminded.university.servlets.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.sql.StudentDaoImpl;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDao studentDao;
    
    @Override
    public void init() {
        studentDao = new StudentDaoImpl();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        studentDao.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/students");
    }

}
