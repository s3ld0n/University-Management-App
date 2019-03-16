package com.foxminded.university.servlets.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.impl.StudentDao;
import com.foxminded.university.domain.Student;

@WebServlet("/student-create-servlet")
public class StudentCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String group = request.getParameter("group");
        
        new StudentDao().create(new Student(1, firstName, lastName, group));
        response.sendRedirect(request.getContextPath() + "/students");
    }

}
