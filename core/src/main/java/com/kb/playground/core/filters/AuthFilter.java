package com.kb.playground.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.servlets.annotations.SlingServletFilter;
import org.apache.sling.servlets.annotations.SlingServletFilterScope;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

@Component
@SlingServletFilter(scope = SlingServletFilterScope.REQUEST,
                    pattern = "/bin/test-servlet",
                    methods = "GET")
@ServiceDescription("Filter to grant access to the servlet just for Administrators user group")
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) servletRequest;
        final SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) servletResponse;
        final ResourceResolver resourceResolver = slingRequest.getResourceResolver();
        final User currentUser = resourceResolver.adaptTo(User.class);
        
        if (currentUser.isAdmin()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            slingResponse.sendError(401, "You are not authorized to access servlet");
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) {}
    
    @Override
    public void destroy() {}
}