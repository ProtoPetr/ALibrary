<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

${sessionScope['manage_book_info'] = null}

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Admin page</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
</head>

<body>
<div class="container mt-5">
    <div class="header row bg-light justify-content-between align-items-center">
        <div class="col-11">
            <h2> Administrator control panel</h2>
        </div>
        <div class="col-1"><a href="/controller?command=INDEX_PAGE" class="btn btn-outline-primary">Exit</a></div>
    </div>
    <div class="accordion mt-3" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne"
                        aria-expanded="true" aria-controls="collapseOne">
                    <h2>Users manage panel</h2>
                </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse ${sessionScope['accordionUser']}"
                 aria-labelledby="headingOne"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <!--Search Form -->
                    <form action="${pageContext.request.contextPath}/controller?command=ADMIN_PAGE&searchUserAction=searchUser"
                          method="post" id="searchUserForm" role="form">
                        <div class="row justify-content-start">
                            <div class="form-group col-6">
                                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                                <input type="text" name="userName" id="userName" class="form-control" required
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
                                    <td>Last name</td>
                                    <td>Login</td>
                                    <td>Role</td>
                                    <td>E-mail</td>
                                    <td>Status</td>
                                </tr>
                                </thead>
                                <c:forEach var="foundUser" items="${sessionScope['searchedUsersList']}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=USER_MANAGE_PAGE&idUser=${foundUser.id}&searchAction=searchById">
                                                    ${foundUser.id}</a>
                                        </td>
                                        <td>${foundUser.name}</td>
                                        <td>${foundUser.surname}</td>
                                        <td>${foundUser.login}</td>
                                        <td>${foundUser.role}</td>
                                        <td>${foundUser.mail}</td>
                                        <c:choose>
                                            <c:when test="${'active' eq foundUser.status}">
                                                <td style="background-color: #146c43">${foundUser.status}</td>
                                            </c:when>
                                            <c:when test="${'block' eq foundUser.status}">
                                                <td style="background-color: red">${foundUser.status}</td>
                                            </c:when>
                                        </c:choose>
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

                    <jsp:useBean id="userService" class="com.epam.service.mysql.MySqlUserService" scope="page"/>
                    <c:set value="${userService.findAllUsers()}" var="userList"/>

                    <!--Users List-->
                    <c:choose>
                        <c:when test="${not empty userList}">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Last name</td>
                                    <td>Login</td>
                                    <td>Role</td>
                                    <td>E-mail</td>
                                    <td>Status</td>
                                </tr>
                                </thead>
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=USER_MANAGE_PAGE&idUser=${user.id}&searchAction=searchById">
                                                    ${user.id}</a>
                                        </td>
                                        <td>${user.name}</td>
                                        <td>${user.surname}</td>
                                        <td>${user.login}</td>
                                        <td>${user.role}</td>
                                        <td>${user.mail}</td>
                                        <c:choose>
                                            <c:when test="${'active' eq user.status}">
                                                <td style="background-color: #146c43">${user.status}</td>
                                            </c:when>
                                            <c:when test="${'block' eq user.status}">
                                                <td style="background-color: red">${user.status}</td>
                                            </c:when>
                                        </c:choose>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    <h2>Books manage panel</h2>
                </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse ${sessionScope['accordionBook']}"
                 aria-labelledby="headingTwo"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <!--Search Form -->
                    <div class="row">
                        <div class="col-10">
                            <form action="${pageContext.request.contextPath}/controller?command=ADMIN_PAGE&searchBookAction=searchBook"
                                  method="post" id="searchBookForm" role="form">
                                <div class="row justify-content-start">
                                    <div class="form-group col-6">
                                        <input type="text" name="bookName" class="form-control" required
                                               placeholder="Type the Name of the book"/>
                                    </div>
                                    <div class="col-2">
                                        <button type="submit" class="btn btn-info">
                                            <span class="glyphicon glyphicon-search"></span>Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-2">
                            <a href="${pageContext.request.contextPath}/controller?command=BOOK_MANAGE_PAGE&manageBookAction=create"
                               class="btn btn-outline-success">Add book</a>
                        </div>
                    </div>
                    <br/>
                    <br/>

                    <c:choose>
                        <c:when test="${not empty sessionScope['searchedBooksList']}">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Author</td>
                                    <td>Genre</td>
                                    <td>Publisher</td>
                                    <td>Year of publishing</td>
                                    <td>Count</td>
                                    <td>Delete</td>
                                </tr>
                                </thead>
                                <c:forEach var="foundBook" items="${sessionScope['searchedBooksList']}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=BOOK_MANAGE_PAGE&idBook=${foundBook.id}&manageBookAction=update">
                                                    ${foundBook.id}</a>
                                        </td>
                                        <td>${foundBook.name}</td>
                                        <td>${foundBook.author}</td>
                                        <td>${foundBook.genre}</td>
                                        <td>${foundBook.publisher}</td>
                                        <td>${foundBook.yearOfPublishing}</td>
                                        <td>${foundBook.count}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=ADMIN_PAGE&idBook=${foundBook.id}&deleteAction=delete">
                                                <img src="../../images/delete.png" style="width: 20px;height: 20px"
                                                     alt="trash"></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>

                    <jsp:useBean id="bookService" class="com.epam.service.mysql.MySqlBookService" scope="page"/>
                    <c:set value="${bookService.getAllBooks()}" var="bookList"/>

                    <!--Books List-->
                    <c:choose>
                        <c:when test="${not empty bookList}">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Author</td>
                                    <td>Genre</td>
                                    <td>Publisher</td>
                                    <td>Year of publishing</td>
                                    <td>Count</td>
                                    <td>Delete</td>
                                </tr>
                                </thead>
                                <c:forEach var="book" items="${bookList}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=BOOK_MANAGE_PAGE&idBook=${book.id}&manageBookAction=update">${book.id}</a>
                                        </td>
                                        <td>${book.name}</td>
                                        <td>${book.author}</td>
                                        <td>${book.genre}</td>
                                        <td>${book.publisher}</td>
                                        <td>${book.yearOfPublishing}</td>
                                        <td>${book.count}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/controller?command=ADMIN_PAGE&idBook=${book.id}&deleteAction=delete">
                                                <img src="../../images/delete.png" style="width: 20px;height: 20px" alt="trash"></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
