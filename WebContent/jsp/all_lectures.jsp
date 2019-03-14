<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

h1, table {
    text-align: center;
}

</style>
<title>Lectures</title>
</head>
<body>
    <h1>LECTURES</h1><br>
    
    <table border="1" align="center">
        <tr>
          <th>ID</th>
          <th>Subject</th>
          <th>Lector</th>
        </tr>

        <c:forEach var="lecture" items="${lectures}">
        
            <tr>
              <td>${lecture.id}</td>
              <td><a href="lecture?id=${lecture.id}">${lecture.subject.name}</a></td>
              <td>${lecture.lector.firstName} ${lecture.lector.lastName}</td>
            </tr>
            
        </c:forEach>
    </table>
</body>
</html>
