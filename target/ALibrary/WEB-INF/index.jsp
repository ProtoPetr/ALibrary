<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col align-self-start mt-3 mx-lg-5">
            <div class="m-3">
                <h4>Library of Alexandria welcomes you my young Christian! </h4>
                <br>
                <br>
                <h5>In order to use the services of the library, enter your credentials</h5>
                <h5>If you don't have an account yet, go through easy registration </h5>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/">
                <div class="mb-3">
                    <label for="Login" class="form-label">Login</label>
                    <input type="text" class="form-control" name="login" id="Login" maxlength="25">
                </div>
                <div class="mb-3">
                    <label for="userPassword" class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" id="userPassword" maxlength="25">
                </div>
                <div class="d-grid gap-2 col-6 mx-auto">
                    <button class="btn btn-primary" type="submit">Sign-in</button>
                    <a href="${pageContext.request.contextPath}/controller?command=REGISTRATION_PAGE"
                       class="btn btn-primary" type="submit">Registration</a>
                </div>
            </form>
        </div>
        <div class="col align-self-end">
            <img src="../images/index.jpg" class="rounded float-end" alt="Wall"/>
        </div>
    </div>
</div>
</body>
</html>
