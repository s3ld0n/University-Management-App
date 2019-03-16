package com.foxminded.university.servlets.group;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.impl.GroupDao;
import com.foxminded.university.domain.Group;

@WebServlet("/group-create-servlet")
public class GroupCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        
        new GroupDao().create(new Group(1, name));
        response.sendRedirect(request.getContextPath() + "/groups");
    }
}
