<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

h1, h2, table, form {
    text-align: center;
}

</style>
<title>Students</title>
</head>
<body>
    <h1>STUDENTS</h1><br>
    
    <h2>Find by ID</h2>
    
    <form action="student-read">
      ID:
      <input type="number" name="id">
      <input type="submit" value="Find">
    </form>
    <br><br>
    
    <table border="1" align="center">
        <tr>
          <th>ID</th>
          <th>Full Name</th>
        </tr>
        <c:forEach var="student" items="${students}">
        
            <tr>
              <td>${student.id}</td>
              <td><a href="student?id=${student.id}">${student.firstName} ${student.lastName}</a></td>
            </tr>
            
        </c:forEach>
    </table>
</body>
</html>
