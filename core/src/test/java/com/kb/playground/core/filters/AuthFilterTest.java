package com.kb.playground.core.filters;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthFilterTest {

    private AuthFilter filter;
    
    @Mock
    SlingHttpServletRequest request;
    
    @Mock
    SlingHttpServletResponse response;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    User user;
    
    @Mock
    FilterChain filterChain;
    
    @BeforeEach
    public void setUp() {
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(User.class)).thenReturn(user);
        
        filter = new AuthFilter();
        filter.init(mock(FilterConfig.class));
    }
    
    @AfterEach
    public void cleanUp() {
        filter.destroy();
    }
    
    @Test
    public void testDoFilterForAdminUser() throws IOException, ServletException {
        when(user.isAdmin()).thenReturn(true);
        filter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    public void testDoFilterForNonAdminUser() throws IOException, ServletException {
        when(user.isAdmin()).thenReturn(false);
        filter.doFilter(request, response, filterChain);
        verify(response).sendError(eq(401), any(String.class));
    }
}