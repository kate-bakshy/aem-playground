package com.kb.playground.core.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,
           property = {
                 "sling.servlet.paths=" + RequestInfoServlet.SERVLET_PATH,
                 "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class RequestInfoServlet extends SlingSafeMethodsServlet {
    
    private static final long serialVersionUID = 1L;
    
    protected static final String SERVLET_PATH = "/bin/test-servlet";
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        response.setContentType("text/plain");
        
        PrintWriter writer = response.getWriter();
        writer.write("Hello! I am RequestInfoServlet servlet. Here is request informaton:" +
                "\nRequest path - " + request.getPathInfo() +
                "\nRequest method - " + request.getMethod() +
                "\nRequest extension - " + request.getRequestPathInfo().getExtension() +
                "\nRequest suffix - " + request.getRequestPathInfo().getSuffix() +
                "\nRequest selectors - " + request.getRequestPathInfo().getSelectorString());
    }
}