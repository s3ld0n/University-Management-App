<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">

h3, h4 {
    text-align: center;
}

</style>
<c:set var="currentLector" value="${lector}"></c:set>

<title>Student ${currentLector.firstName} ${currentLector.lastName}</title>
</head>
<body>
    <h3>Lector id: ${currentLector.id}</h3>
    <h3>First Name: ${currentLector.firstName}</h3>
    <h3>Last Name: ${currentLector.lastName}</h3>
    <h3>Subjects of the lector: </h3>
    
        <c:forEach var="subject" items="${subjects}">
        
            <h4>${subject.name}</h4>
            
        </c:forEach>
    
</body>
</html>
