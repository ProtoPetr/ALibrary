<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="userService" class="com.epam.serviсe.mysql.MySqlUserService" scope="application"/>
<jsp:useBean id="deskService" class="com.epam.serviсe.mysql.MySqlDeliveryDeskService" scope="application"/>
<jsp:useBean id="bookService" class="com.epam.serviсe.mysql.MySqlBookService" scope="application"/>

<c:set value="${userService.getUserByLoginPassword(sessionScope['login'], sessionScope['password'])}" var="user"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Librarian page</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="header row bg-light justify-content-between align-items-center">
        <div class="col-11">
            <h2>Librarian manage panel</h2>
        </div>
        <div class="col-1"><a href="${pageContext.request.contextPath}/controller?command=INDEX_PAGE"
                              class="btn btn-outline-primary">Exit</a></div>
    </div>
    <div class="accordion mt-3" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne"
                        aria-expanded="true" aria-controls="collapseOne">
                    <h3>List of orders</h3>
                </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse ${sessionScope['ordersInfoAccordion']}"
                 aria-labelledby="headingOne"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <td>#</td>
                            <td>Delivery desk №</td>
                            <td>User name</td>
                            <td>User surname</td>
                            <td>Book Id</td>
                            <td>Book name</td>
                            <td>Book count</td>
                        </tr>
                        </thead>
                        <c:set var="deliveryDeskList" value="${deskService.getAllDeliveryDesk()}"/>
                        <c:forEach var="deliveryDesk" items="${deliveryDeskList}">
                            <c:set var="currentUser" value="${userService.findUserById(deliveryDesk.getUserId())}"/>
                            <c:set var="currentBook" value="${bookService.getBookById(deliveryDesk.getBookId())}"/>
                            <tr>
                                <td>${deliveryDeskList.indexOf(deliveryDesk) + 1}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/controller?command=CURRENT_DELIVERY_DESK_PAGE&userId=${currentUser.id}&userName=${currentUser.name}&userSurname=${currentUser.surname}&deskId=${deliveryDesk.id}">
                                            ${deliveryDesk.id}</a></td>
                                <td>${currentUser.name}</td>
                                <td>${currentUser.surname}</td>
                                <td>${currentBook.id}</td>
                                <td>${currentBook.name}</td>
                                <td>${currentBook.count}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    <h3>List of users</h3>
                </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse ${sessionScope['usersInfoAccordion']}"
                 aria-labelledby="headingTwo"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <!--Search Form -->
                    <form action="${pageContext.request.contextPath}/controller?command=LIBRARIAN_PAGE&searchUserAction=searchUser"
                          method="post" id="searchUserForm" role="form">
                        <div class="row justify-content-start">
                            <div class="form-group col-6">
                                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                                <input type="text" name="searchedUserName" id="searchedUserName" class="form-control"
                                       required maxlength="25"
                                       placeholder="Type the Name or Last Name of the user"/>
                            </div>
                            <div class="col-2">
                                <button type="submit" class="btn btn-info">
                                    <span class="glyphicon glyphicon-search"></span>Search
                                </button>
                            </div>
                        </div>
                    </form>
                    <br/>
                    <br/>

                    <c:choose>
                        <c:when test="${not empty sessionScope['searchedUsersList']}">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Surname</td>
                                    <td>mail</td>
                                    <td>Delivery desk №</td>
                                    <td>User Status</td>
                                </tr>
                                </thead>
                                <c:set var="SearchedUserDeskList" value="${sessionScope['searchedUsersList']}"/>
                                <c:forEach var="SearchedUserDesk" items="${SearchedUserDeskList}">
                                    <c:set var="currentDeskBySearched"
                                           value="${deskService.getUserDeliveryDesk(SearchedUserDesk.getId())}"/>
                                    <tr>
                                        <td>${SearchedUserDesk.id}</td>
                                        <td>${SearchedUserDesk.name}</td>
                                        <td>${SearchedUserDesk.surname}</td>
                                        <td>${SearchedUserDesk.mail}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=CURRENT_DELIVERY_DESK_PAGE&userId=${SearchedUserDesk.id}&userName=${SearchedUserDesk.name}&userSurname=${SearchedUserDesk.surname}&deskId=${currentDeskBySearched.get(0).getId()}&accordionInfo=2">
                                                    ${currentDeskBySearched.get(0).getId()}</a></td>
                                        <td>${SearchedUserDesk.status}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>

                    <c:if test="${sessionScope['search_user_status'] ne null}">
                        <div class="alert alert-success">
                                ${sessionScope['search_user_status']}
                        </div>
                    </c:if>

                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <td>#</td>
                            <td>Name</td>
                            <td>Surname</td>
                            <td>mail</td>
                            <td>Delivery desk №</td>
                            <td>User Status</td>
                        </tr>
                        </thead>
                        <c:set var="userDeskList" value="${userService.findAllLibraryUsers()}"/>
                        <c:forEach var="userDesk" items="${userDeskList}">
                            <c:set var="currentDesk" value="${deskService.getUserDeliveryDesk(userDesk.getId())}"/>
                            <tr>
                                <td>${userDesk.id}</td>
                                <td>${userDesk.name}</td>
                                <td>${userDesk.surname}</td>
                                <td>${userDesk.mail}</td>
                                <td>
                                    <c:if test="${!currentDesk.isEmpty()}">

                                        <a href="${pageContext.request.contextPath}/controller?command=CURRENT_DELIVERY_DESK_PAGE&userId=${userDesk.id}&userName=${userDesk.name}&userSurname=${userDesk.surname}&deskId=${currentDesk.get(0).getId()}&accordionInfo=2">
                                                ${currentDesk.get(0).getId()}</a>

                                    </c:if>
                                </td>
                                <td>${userDesk.status}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
