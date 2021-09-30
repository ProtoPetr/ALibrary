<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="ptl" uri="/WEB-INF/tld/taglib.tld" %>

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
            <ptl:welcome name="${sessionScope['login']}"/>
        </div>
        <div class="col-2"><br><h5><a href="${pageContext.request.contextPath}/controller?command=USER_ACCOUNT_PAGE">Personal
            account</a></h5><br></div>
        <div class="col-2"><br><h5><a href="${pageContext.request.contextPath}/controller?command=INDEX_PAGE">Exit</a>
        </h5><br></div>
        <br>
        <br>
        <h3>Выберите раздел или используйте поиск книги</h3>
        <br>
        <form name="search_form" method="POST" action="${pageContext.request.contextPath}/controller?command=USER_PAGE&clearList=clear">
            <div class="row justify-content-center no-gutters">
                <div class="col-2">
                    <select name="search_option" class="form-select no-gutters">
                        <option value="1">Name</option>
                        <option value="2">Author</option>
                    </select>
                </div>
                <div class="col-8 no-gutters">
                    <input class="form-control" type="text" name="search_string" maxlength="50"
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
                <jsp:useBean id="genreList" class="com.epam.serviсe.mysql.MySqlGenreService" scope="application"/>

                <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE&clearList=clear"
                   class="list-group-item list-group-item-action">Все книги</a>

                <c:forEach var="genre" items="${genreList.findAllGenres()}">
                    <c:choose>
                        <c:when test="${sessionScope['selectedGenreId'] ne 0 && sessionScope['selectedGenreId'] eq genre.getId()}">
                            <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE&genre_id=${genre.getId()}&clearList=clear"
                               class="list-group-item list-group-item-action active"
                               aria-current="true">${genre.getName()}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/controller?command=USER_PAGE&genre_id=${genre.getId()}&clearList=clear"
                               class="list-group-item list-group-item-action">${genre.getName()}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </div>
        <div class="col-9">
            <div class="row justify-content-center">
                <jsp:useBean id="letterList" class="com.epam.supplies.LetterList" scope="application"/>
                <c:forEach var="letter" items="${letterList.russianLetters}">
                    <div class="col">
                        <c:choose>
                            <c:when test="${sessionScope['searchLetter'] ne null && sessionScope['searchLetter'].toUpperCase().charAt(0) eq letter}">
                                <a style="color:red; width: 100%; text-decoration: none; font-size: 15px; font-weight: bold;"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&letter=${letter}&clearList=clear">${letter}</a>
                            </c:when>
                            <c:otherwise>
                                <a style="color: #4d6cd3; width: 100%; text-decoration: none; font-size: 15px; font-weight: bold;"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&letter=${letter}&clearList=clear">${letter}</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
            <div class="row mt-3">
                <div class="col-8">
                    <h5>Найдено книг: ${sessionScope['currentBookList'].size()}</h5>
                </div>
                <div class="col-2">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            Books on page
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&pagination=true&booksPerPage=4">4</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&pagination=true&booksPerPage=12">12</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&pagination=true&booksPerPage=36">36</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&pagination=false">all</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-2">
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton2"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            Oder by
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByName">Name</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByAuthor">Author</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByPublisher">Publisher</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=USER_PAGE&sortAction=sortByYear">Year
                                of publishing</a></li>
                        </ul>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${sessionScope['pagination'] eq true}">
                        <ptl:pagination totalPages="${sessionScope['totalPages']}"
                                        currentPage="${sessionScope['currentPage']}"
                                        pageBookList="${sessionScope['pageBookList']}"
                                        var="book"
                                        pageItemsCount="${sessionScope['pageItemsCount']}">
                            <%@include file="../../WEB-INF/jspf/book_card.jspf" %>
                        </ptl:pagination>
                    </c:when>
                    <c:otherwise>
                        <div class="row row-cols-1 row-cols-md-4 g-4 mt-3">
                            <c:forEach var="book" items="${sessionScope['currentBookList']}">
                                <%@include file="../../WEB-INF/jspf/book_card.jspf" %>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</div>
</body>
</html>
