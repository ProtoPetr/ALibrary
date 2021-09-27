package com.epam.web_jstl;

import com.epam.entity.Book;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class PaginationTag extends BodyTagSupport {
    List<Book> pageBookList;
    String var;
    private int totalPages;
    private int pageItemsCount;
    private int currentPage = 1;

    public void setVar(String var) {
        this.var = var;
    }

    public void setPageItemsCount(int pageItemsCount) {
        this.pageItemsCount = pageItemsCount;
    }

    public void setPageBookList(List<Book> pageBookList) {
        this.pageBookList = pageBookList;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int doStartTag() throws JspException {
        if (null != pageBookList && pageBookList.size() != 0) {
            Iterator<Book> it = pageBookList.iterator();
            Object next = it.next();
            pageContext.setAttribute(var, next);
            pageContext.setAttribute("it", it);
            try {
                pageContext.getOut().write("<div class=\"row row-cols-1 row-cols-md-4 g-4 mt-3\">");
            } catch (IOException e) {
                throw new JspTagException(e.getMessage());
            }
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        Iterator<Book> it = (Iterator<Book>) pageContext.getAttribute("it");
        while (it.hasNext()) {
            Book next = it.next();
            pageContext.setAttribute(var, next);
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("</div>");
            pageContext.getOut().write("<div class=\"row justify-content-center\">");
            pageContext.getOut().write("<div class=\"col-4\">");
            pageContext.getOut().write("<nav aria-label=\"Page navigation example\">");
            pageContext.getOut().write("<br>");
            pageContext.getOut().write("<br>");
            pageContext.getOut().write("<ul class=\"pagination\">");

            if (totalPages == 0) {
            } else if (currentPage == 1 && totalPages == 1) {
                pageContext.getOut().write("<li class=\"page-item active\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + currentPage + "\">" + currentPage + "</a></li>");
            } else if (currentPage == 1 && totalPages == 2) {
                pageContext.getOut().write("<li class=\"page-item disabled\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">Previous</a></li>");
                pageContext.getOut().write("<li class=\"page-item active\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + currentPage + "\">" + currentPage + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">" + (currentPage + 1) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">Next</a></li>");
            } else if (currentPage == 2 && totalPages == 2) {
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">Previous</a></li>");
                pageContext.getOut().write("<li class=\"page-item \"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">" + (currentPage - 1) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item active\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + currentPage + "\">" + currentPage + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item disabled\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">Next</a></li>");
            } else if (currentPage == 1) {
                pageContext.getOut().write("<li class=\"page-item disabled\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">Previous</a></li>");
                pageContext.getOut().write("<li class=\"page-item active\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + currentPage + "\">" + currentPage + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">" + (currentPage + 1) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 2) + "\">" + (currentPage + 2) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">Next</a></li>");
            } else if (currentPage == totalPages) {
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">Previous</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 2) + "\">" + (currentPage - 2) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item \"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">" + (currentPage - 1) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item active\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + currentPage + "\">" + currentPage + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item disabled\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">Next</a></li>");
            } else {
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">Previous</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage - 1) + "\">" + (currentPage - 1) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item active\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + currentPage + "\">" + currentPage + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">" + (currentPage + 1) + "</a></li>");
                pageContext.getOut().write("<li class=\"page-item\"><a class=\"page-link\" href=\"/controller?command=USER_PAGE&pagination=true&currentPage=" + (currentPage + 1) + "\">Next</a></li>");
            }

            pageContext.getOut().write("</ul>");
            pageContext.getOut().write("</nav>");
            pageContext.getOut().write("</div>");
            pageContext.getOut().write("</div>");
            pageContext.getOut().write("<br>");
            pageContext.getOut().write("<br>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
