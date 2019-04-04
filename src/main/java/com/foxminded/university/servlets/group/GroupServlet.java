package com.foxminded.university.servlets.group;

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

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDao studentDao;
    private GroupDao groupDao;
    
    @Override
    public void init() {
        studentDao = new StudentDaoImpl();
        groupDao = new GroupDaoImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int groupId = Integer.parseInt(request.getParameter("id"));

        Group group = groupDao.findById(groupId);
        List<Student> students = studentDao.findAllByGroupId(groupId);

        request.setAttribute("group", group);
        request.setAttribute("students", students);

        request.getRequestDispatcher("jsp/group/group.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        
        groupDao.update(new Group(id, name));
        response.sendRedirect(request.getContextPath() + "/groups");
    }
}
