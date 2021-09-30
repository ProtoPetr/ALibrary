<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="userService" class="com.epam.serviсe.mysql.MySqlUserService" scope="page" />

<c:set value="${userService.findUserById(sessionScope['idUser'])}" var="user" />

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col align-self-start mt-3">
            <c:choose>
                <c:when test="${sessionScope['role'] eq 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/controller?command=ADMIN_PAGE" role="button" class="btn btn-outline-primary mt-3"
                        ${sessionScope['accordionUser'] = "show"; sessionScope['accordionBook'] = ""}>&lt;&lt; Back</a>
                </c:when>
                <c:when test="${sessionScope['role'] eq 'USER'}">
                    <a href="${pageContext.request.contextPath}/controller?command=USER_ACCOUNT_PAGE" role="button" class="btn btn-outline-primary mt-3"
                        ${sessionScope['registerInfoAccordion'] = "show"; sessionScope['deliveryDeskInfoAccordion'] = ""}>&lt;&lt; Back</a>
                </c:when>
            </c:choose>

            <form class="mt-3" method="post" action="${pageContext.request.contextPath}/controller?command=USER_MANAGE_PAGE&manageAction=update&idUser=${sessionScope['idUser']}">
                <div class="mb-3">
                    <label for="NameM" class="form-label">Name</label>
                    <input type="text" class="form-control" required name="nameM" id="NameM" value="${user.name}">
                </div>
                <div class="mb-3">
                    <label for="SurNameM" class="form-label">SurName</label>
                    <input type="text" class="form-control" required name="surnameM" id="SurNameM" value="${user.surname}">
                </div>
                <div class="mb-3">
                    <label for="LoginM" class="form-label">Login</label>
                    <input type="text" class="form-control" required name="loginM" id="LoginM" value="${user.login}">
                </div>
                <div class="mb-3">
                    <label for="PasswordM" class="form-label">Password</label>
                    <input type="${sessionScope['role'] eq 'ADMIN' ? 'password' : 'text'}" class="form-control" required name="passwordM" id="PasswordM" value="${user.password}">
                </div>
                <c:if test="${sessionScope['role'] eq 'ADMIN'}">
                    <div class="mb-3">
                        <label for="RoleM" class="form-label">Role</label>
                        <input type="text" class="form-control" required name="roleM" id="RoleM" value="${user.role}">
                    </div>
                </c:if>
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Email address</label>
                    <input type="email" class="form-control" required name="mailM" id="exampleInputEmail1"
                           aria-describedby="emailHelp" value="${user.mail}">
                </div>
                <c:if test="${sessionScope['role'] eq 'ADMIN'}">
                    <div class="mb-3">
                        <label for="StatusM" class="form-label">Status</label>
                        <input type="text" class="form-control" required name="statusM" id="StatusM" value="${user.status}">
                    </div>
                </c:if>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
            <c:if test="${sessionScope['change_user_info'] ne null}">
                <div class="alert alert-success">
                        ${sessionScope['change_user_info']}
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
