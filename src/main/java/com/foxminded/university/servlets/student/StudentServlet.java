package com.foxminded.university.servlets.student;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.impl.GroupDaoImpl;
import com.foxminded.university.dao.impl.StudentDaoImpl;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private StudentDao studentDao;
    private GroupDao groupDao;
    private int currentStudentId;
    
    @Override
    public void init() {
        studentDao = new StudentDaoImpl();
        groupDao = new GroupDaoImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.currentStudentId = Integer.parseInt(request.getParameter("id"));
        
        Student student = studentDao.findById(currentStudentId);
        List<Group> groups = groupDao.findAll();

        request.setAttribute("groups", groups);
        request.setAttribute("student", student);
        request.getRequestDispatcher("jsp/student/student.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int group = Integer.parseInt(request.getParameter("group"));
        
        studentDao.update(new Student(this.currentStudentId, firstName, lastName, groupDao.findById(group)));
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
