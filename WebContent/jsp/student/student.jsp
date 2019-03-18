<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/style.css">

<c:set var="currentStudent" value="${student}"></c:set>

<title>Student ${currentStudent.firstName} ${currentStudent.lastName}</title>
</head>
<body>
    <h3>Student id: ${currentStudent.id}</h3>
    <h3>First Name: ${currentStudent.firstName}</h3>
    <h3>Last Name: ${currentStudent.lastName}</h3>
    <h3>Group: ${currentStudent.group}</h3>
    <br><br>
    
    <div class="container update border">
    <form action="student" class="needs-validation" method="post" novalidate>
      <input type="hidden" name="id" value="${currentStudent.id}" />

      <div class="form-group">
        <label for="firstName">First Name</label>
        <input type="text" class="form-control" id="firstName" name="firstName" required>
        <div class="valid-feedback">Valid.</div>
        <div class="invalid-feedback">Please fill out this field.</div>
      </div>

      <div class="form-group">
        <label for="lastName">Last Name</label>
        <input type="text" class="form-control" id="lastName" name="lastName" required>
        <div class="valid-feedback">Valid.</div>
        <div class="invalid-feedback">Please fill out this field.</div>
      </div>
      
      <div class="form-group">
        <label for="group">Group</label>
       <select id="group" id="group" name="group">
        <c:forEach var="group" items="${groups}">
          <option value="${group.name}">${group.name}</option>
        </c:forEach>
       </select> 
      </div>
        
      <button type="submit" class="btn btn-primary">Update</button>
    </form>
    </div>
    
    <br><br>
    
    <form action="student/delete?id=${currentStudent.id}" method="post">
        <input type="submit" value="Delete" />
    </form>
    
<script src="js/form_validator.js"></script>
</body>
</html>
