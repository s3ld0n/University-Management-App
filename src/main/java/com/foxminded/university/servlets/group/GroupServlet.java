package com.foxminded.university.servlets.group;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.impl.GroupDao;
import com.foxminded.university.dao.impl.StudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int groupId = Integer.parseInt(request.getParameter("id"));

        Group group = new GroupDao().findById(groupId);
        List<Student> students = new StudentDao().findAllByGroupId(groupId);

        request.setAttribute("group", group);
        request.setAttribute("students", students);

        request.getRequestDispatcher("jsp/group/group.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        
        new GroupDao().update(new Group(1, name));
        response.sendRedirect(request.getContextPath() + "/groups");
    }
}
