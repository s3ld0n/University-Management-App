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
<title>Groups</title>
</head>
<body>
    <h1>GROUPS</h1><br>
    
    <table border="1" align="center">
        <tr>
          <th>ID</th>
          <th>Name</th>
        </tr>
        <c:forEach var="group" items="${groups}">
        
            <tr>
              <td>${group.id}</td>
              <td><a href="group?id=${group.id}">${group.name}</a></td>
            </tr>
            
        </c:forEach>
    </table>
</body>
</html>
