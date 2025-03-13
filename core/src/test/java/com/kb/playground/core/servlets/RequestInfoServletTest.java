package com.kb.playground.core.servlets;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class RequestInfoServletTest {
    
    private RequestInfoServlet requestInfoServlet  = new RequestInfoServlet();
    
    private static final String EXPECTED_RESPONSE = "Hello! I am RequestInfoServlet servlet. Here is request informaton:\n"
            + "Request path - /bin/test-servlet\n"
            + "Request method - GET\n"
            + "Request extension - null\n"
            + "Request suffix - null\n"
            + "Request selectors - null";
    
    @Test
    public void testDoGet(AemContext context) throws IOException {
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();
        
        request.setPathInfo(RequestInfoServlet.SERVLET_PATH);
        
        requestInfoServlet.doGet(request, response);
        
        assertEquals(EXPECTED_RESPONSE, response.getOutputAsString());
    }
}