package com.foxminded.university.servlets.group;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.crud_dao_implementations.GroupDao;
import com.foxminded.university.dao.crud_dao_implementations.StudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

@WebServlet("/group-find")
public class GroupFindServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Group group =  new GroupDao().findById(id);
        List<Student> students = new StudentDao().findAllByGroupId(id);
        
        request.setAttribute("group", group);
        request.setAttribute("students", students);
        request.getRequestDispatcher("jsp/group/group.jsp").forward(request, response);
    }
}
