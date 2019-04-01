package com.foxminded.university.servlets.group;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.sql.GroupDaoImpl;

@WebServlet("/group/delete")
public class GroupDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private GroupDao groupDao;
    
    @Override
    public void init() {
        groupDao = new GroupDaoImpl();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        groupDao.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/groups");
    }

}
