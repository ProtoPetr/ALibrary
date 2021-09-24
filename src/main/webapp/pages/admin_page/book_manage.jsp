<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="bookService" class="com.epam.service.mysql.MySqlBookService" scope="page"/>
<c:set value="${bookService.getBookById(sessionScope['idBook'])}" var="book"/>

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
            <a href="${pageContext.request.contextPath}/controller?command=ADMIN_PAGE" role="button"
               class="btn btn-outline-primary mt-3"
            ${sessionScope['accordionUser'] = ""; sessionScope['accordionBook'] = "show"}>&lt;&lt; Back</a>
            <form class="mt-3" method="post"
                  action="${pageContext.request.contextPath}/controller?command=BOOK_MANAGE_PAGE&manageAction=${param.manageBookAction}&idBook=${sessionScope['idBook']}">
                <div class="mb-3">
                    <div class="mb-3">
                        <label for="NameM" class="form-label">Name</label>
                        <input type="text" class="form-control" required name="nameM" id="NameM"
                               value="${book.name}">
                    </div>
                    <div class="mb-3">
                        <label for="AuthorM" class="form-label">Author</label>
                        <input type="text" class="form-control" required name="authorM" id="AuthorM"
                               value="${book.author}">
                    </div>
                    <div class="mb-3">
                        <label for="GenreM" class="form-label">Genre</label>
                        <input type="text" class="form-control" required name="genreM" id="GenreM"
                               value="${book.genre}">
                    </div>
                    <div class="mb-3">
                        <label for="PublisherM" class="form-label">Publisher</label>
                        <input type="text" class="form-control" required name="publisherM" id="PublisherM"
                               value="${book.publisher}">
                    </div>
                    <div class="mb-3">
                        <label for="YearOfPublishingM" class="form-label">YearOfPublishing</label>
                        <input type="text" class="form-control" required name="yearOfPublishingM"
                               id="YearOfPublishingM"
                               value="${book.yearOfPublishing eq 0 ? "" : book.yearOfPublishing}">
                    </div>
                    <div class="mb-3">
                        <label for="CountM" class="form-label">Count</label>
                        <input type="text" class="form-control" required name="countM" id="CountM"
                               value="${book.count}">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
            <c:if test="${sessionScope['manage_book_info'] ne null}">
                <div class="alert alert-success">
                        ${sessionScope['manage_book_info']}
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>

