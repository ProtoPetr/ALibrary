<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Title</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col align-self-start mt-3">
            <a href="${pageContext.request.contextPath}/controller?command=INDEX_PAGE" role="button" class="btn btn-outline-primary mt-3" >&lt;&lt; Back</a>
            <form class="mt-3" method="post" action="${pageContext.request.contextPath}/controller?command=CREATE_USER">
                <div class="mb-3">
                    <label for="Login" class="form-label">Login</label>
                    <input type="text" class="form-control" required name="loginR" id="Login" maxlength="25">
                </div>
                <div class="mb-3">
                    <label for="NameR" class="form-label">Name</label>
                    <input type="text" class="form-control" name="nameR" required id="NameR" maxlength="25">
                </div>
                <div class="mb-3">
                    <label for="SurNameR" class="form-label">SurName</label>
                    <input type="text" class="form-control" name="surnameR" required id="SurNameR" maxlength="25">
                </div>
                <div class="mb-3">
                    <label for="mailR" class="form-label">Email address</label>
                    <input type="email" class="form-control" name="mailR" required id="mailR" aria-describedby="emailHelp" maxlength="25">
                    <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                </div>
                <div class="mb-3">
                    <label for="userPassword" class="form-label">Password</label>
                    <input type="password" class="form-control" required name="passwordR" id="userPassword" maxlength="25">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
            <c:choose>
                <c:when test="${sessionScope['registrationStatus'] eq 'success'}">
                    <p>The user was successfully registered</p>
                </c:when>
                <c:when test="${sessionScope['registrationStatus'] eq 'failed'}">
                    <p>A user with this login already exists<br>Please enter another username</p>
                </c:when>
            </c:choose>
        </div>
        <div class="col align-self-end">
            <img src="../images/registration.jpg" class="rounded float-end" alt="стена"/>
        </div>
    </div>
</div>
</body>
</html>
