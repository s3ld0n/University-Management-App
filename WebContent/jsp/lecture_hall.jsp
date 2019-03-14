<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

h3 {
    text-align: center;
}

</style>

<title>Lecture Hall ${lectureHall.name}</title>
</head>
<body>
    <h3>ID: ${lectureHall.id}</h3>
    <h3>Name: ${lectureHall.name}</h3>
    
    <h3>Booked periods:</h3>
    <table border="1" align="center">
        <tr>
            <th>Start</th>
            <th>End</th>
        </tr>
        
        <c:forEach var="period" items="${periods}">
        <tr>
           <td>${period.start}</td>
           <td>${period.end}</td>
        </tr>
            
        </c:forEach>
    </table>
</body>
</html>
