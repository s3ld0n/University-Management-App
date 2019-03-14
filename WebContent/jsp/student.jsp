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
<c:set var="currentStudent" value="${student}"></c:set>

<title>Student ${currentStudent.firstName} ${currentStudent.lastName}</title>
</head>
<body>
    <h3>Student id: ${currentStudent.id}</h3>
    <h3>First Name: ${currentStudent.firstName}</h3>
    <h3>Last Name: ${currentStudent.lastName}</h3>
    <h3>Group: ${currentStudent.group}</h3>
</body>
</html>
