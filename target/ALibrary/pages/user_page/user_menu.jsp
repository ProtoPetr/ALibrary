<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/bootstrap.bundle.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="header row bg-light justify-content-between align-items-end">
        <div class="col-4 ">
            <br>
            <h3>Library of Alexandria</h3>
            <br>
        </div>
        <div class="col-4">
            <br>
            <h5>Welcome, ${sessionScope['login']}!</h5>
            <br>
        </div>
        <div class="col-2"><br><h5><a href="${pageContext.request.contextPath}/controller?command=USER_ACCOUNT_PAGE">Personal
            account</a></h5><br></div>
        <div class="col-2"><br><h5><a href="${pageContext.request.contextPath}/controller?command=INDEX_PAGE">Exit</a>
        </h5><br></div>
        <br>
        <br>
        <h3>Выберите раздел или используйте поиск книги</h3>
        <br>
        <form name="search_form" method="POST" action="${pageContext.request.contextPath}/controller?command=USER_PAGE">
            <div class="row justify-content-center no-gutters">
                <div class="col-2">
                    <select name="search_option" class="form-select no-gutters">
                        <option value="1">Name</option>
                        <option value="2">Author</option>
                    </select>
                </div>
                <div class="col-8 no-gutters">
                    <input class="form-control" type="text" name="search_string"
                           value="${sessionScope['searchString']}"/>
                </div>
                <div class="col-2">
                    <input class="btn btn-outline-success no-gutters" type="submit" value="Search"/>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-3">
            <div class="list-group">
                <h4>Жанры:</h4>
                <jsp:useBean id="genreList" class="com.epam.service.mysql.MySqlGenreService" scope="application"/>

                <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE"
                   class="list-group-item list-group-item-action">Все книги</a>

                <c:forEach var="genre" items="${genreList.findAllGenres()}">
                    <c:choose>
                        <c:when test="${sessionScope['selectedGenreId'] ne 0 && sessionScope['selectedGenreId'] eq genre.getId()}">
                            <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE&genre_id=${genre.getId()}"
                               class="list-group-item list-group-item-action active"
                               aria-current="true">${genre.getName()}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE&genre_id=${genre.getId()}"
                               class="list-group-item list-group-item-action">${genre.getName()}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </div>
        <div class="col-9">
            <div class="justify-content-center align-items-center">
                <jsp:useBean id="letterList" class="com.epam.supplies.LetterList" scope="application"/>
                <c:forEach var="letter" items="${letterList.russianLetters}">
                    <c:choose>
                        <c:when test="${sessionScope['searchLetter'] ne null && sessionScope['searchLetter'].toUpperCase().charAt(0) eq letter}">
                            <a style="color:red; width: 100%; text-decoration: none; font-size: 15px; font-weight: bold; margin: 5px;"
                               href="${pageContext.request.contextPath}/controller?command=USER_PAGE&letter=${letter}">${letter}</a>
                        </c:when>
                        <c:otherwise>
                            <a style="color: #4d6cd3; width: 100%; text-decoration: none; font-size: 15px; font-weight: bold; margin: 5px;"
                               href="${pageContext.request.contextPath}/controller?command=USER_PAGE&letter=${letter}">${letter}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="row mt-3">
                <div class="col-10">
                    <h5>Найдено книг: ${sessionScope['currentBookList'].size()}</h5>
                </div>
                <div class="col-2">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            Oder by
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByName">Name</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByAuthor">Author</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByPublisher">Publisher</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByYear">Year of publishing</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row row-cols-1 row-cols-md-4 g-4 mt-3">
                <c:forEach var="book" items="${sessionScope['currentBookList']}">
                    <div class="col">
                        <div class="card h-100">
                            <img src="${pageContext.request.contextPath}/controller?command=SHOW_IMAGE&index=${sessionScope['currentBookList'].indexOf(book)}"
                                 class="card-img-top" alt="#">
                            <div class="card-body">
                                <h5 class="card-title">${book.getName()}</h5>
                                <p class="card-text"><strong>Издательство:</strong> ${book.getPublisher()}</p>
                                <p class="card-text"><strong>Год издания:</strong> ${book.getYearOfPublishing()}</p>
                                <p class="card-text"><strong>Автор:</strong> ${book.getAuthor()}</p>
                                <p><a href="${pageContext.request.contextPath}/controller?command=BOOK_VS_DELIVERY_DESK&deskAction=addBook&bookId=${book.getId()}">
                                    Добавить в абонемент</a></p>
                                <c:if test="${sessionScope['addBookToDeskStatus'] ne null and book.getId() eq sessionScope['bookId']}">
                                    <p>${sessionScope['addBookToDeskStatus']}</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
