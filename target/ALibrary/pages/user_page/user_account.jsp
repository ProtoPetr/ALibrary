<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="userService" class="com.epam.servise.mysql.MySqlUserService" scope="application" />
<jsp:useBean id="deskService" class="com.epam.servise.mysql.MySqlDeliveryDeskService" scope="application" />

<c:set value="${userService.getUserByLoginPassword(sessionScope['login'], sessionScope['password'])}" var="user"/>
<c:set value="${user.getId()}" var="userId"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>User account page</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="header row bg-light justify-content-between align-items-center">
        <div class="col-10">
            <h2>Personal account</h2>
        </div>
        <div class="col-1">
            <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE" role="button" class="btn btn-outline-primary"
            ${sessionScope['accordionUser'] = "show"; sessionScope['accordionBook'] = ""}>&lt;&lt; Back</a>
        </div>
        <div class="col-1"><a href="${pageContext.request.contextPath}/controller?command=INDEX_PAGE" class="btn btn-outline-primary">Exit</a></div>
    </div>
    <div class="accordion mt-3" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne"
                        aria-expanded="true" aria-controls="collapseOne">
                    <h2>Registration information</h2>
                </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse ${sessionScope['registerInfoAccordion']}"
                 aria-labelledby="headingOne"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <p>Name: ${user.getName()}</p>
                    <p>Surname: ${user.getSurname()}</p>
                    <p>Login: ${user.getLogin()}</p>
                    <p>Password: ${user.getPassword()}</p>
                    <p>Mail: ${user.getMail()}</p>
                    <div class="row justify-content-end">
                        <div class="col-1">
                            <a href="${pageContext.request.contextPath}/controller?command=USER_MANAGE_PAGE&idUser=${userId}"
                               class="btn btn-outline-primary">Edit</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    <h2>Delivery desk information</h2>
                </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse ${sessionScope['deliveryDeskInfoAccordion']}"
                 aria-labelledby="headingTwo"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <%@include file="../../WEB-INF/jspf/delivery_desk.jspf" %>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
