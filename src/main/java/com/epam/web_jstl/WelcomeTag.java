package com.epam.web_jstl;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;

/**
 * This class creates a tag
 * for displaying user greeting
 */
public class WelcomeTag extends SimpleTagSupport {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void doTag() throws JspTagException {
        try {
            JspWriter out = getJspContext().getOut();
            out.write("<br>");
            out.write("<h5>Welcome, " + name + "!</h5>");
            out.write("<br>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
