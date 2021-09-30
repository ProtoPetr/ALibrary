<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="deskService" class="com.epam.serviсe.mysql.MySqlDeliveryDeskService" scope="page"/>
<c:set value="${param.userId}" var="userId"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/bootstrap.bundle.js"></script>
</head>
<body>
<div class="container">
    <div class="header row bg-light justify-content-between align-items-center mt-5">
        <div class="col-5">
            <br>
            <h3>User: ${param.userName} ${param.userSurname}</h3>
            <br>
        </div>
        <div class="col-5">
            <br>
            <h3>Delivery desk №${param.deskId}</h3>
            <br>
        </div>
        <div class="col-1">
            <c:choose>
                <c:when test="${param.accordionInfo eq 2}">
                    <a href="${pageContext.request.contextPath}/controller?command=LIBRARIAN_PAGE" role="button" class="btn btn-outline-primary"
                        ${sessionScope['ordersInfoAccordion'] = ""; sessionScope['usersInfoAccordion'] = "show"}>&lt;&lt; Back</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/controller?command=LIBRARIAN_PAGE" role="button" class="btn btn-outline-primary"
                        ${sessionScope['ordersInfoAccordion'] = "show"; sessionScope['usersInfoAccordion'] = ""}>&lt;&lt; Back</a>
                </c:otherwise>
            </c:choose>

        </div>
        <div class="col-1"><a href="${pageContext.request.contextPath}/controller?command=INDEX_PAGE" class="btn btn-outline-primary">Exit</a></div>
    </div>
    <div class="mt-3">
        <%@include file="../../WEB-INF/jspf/delivery_desk.jspf" %>
    </div>
</div>
</body>
</html>

