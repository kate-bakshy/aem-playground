package com.kb.playground.core.models.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.util.ComponentUtils;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import com.kb.playground.core.models.Comment;

@Model(
        adaptables = Resource.class,
        adapters = {Comment.class},
        resourceType = CommentImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CommentImpl implements Comment {
    
    protected static final String RESOURCE_TYPE = "playground/components/comment";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentImpl.class);
    
    @ValueMapValue
    private String authorName;
    
    @ValueMapValue
    private String text;
    
    @ValueMapValue
    private String id;
    
    @Self
    private Resource resource;
    
    @ScriptVariable
    private Page currentPage;
    
    @ScriptVariable
    private ComponentContext componentContext;
    
    public String getAuthorName() {
        return authorName;
    }
    
    public String getText() {
        return text;
    }
    
    public String getId() {
        return id;
    }
    
    public boolean isEmpty() {
        return StringUtils.isBlank(authorName) && StringUtils.isBlank(text);
    }
    
    public String getData() {
        String data = null;
        
        if (ComponentUtils.isDataLayerEnabled(resource)) {
            Map<String, Object> dataProperties = new HashMap<String, Object>();
            dataProperties.put("@type", resource.getResourceType());
            dataProperties.put("authorName", this.getAuthorName());
            dataProperties.put("commentText", this.getText());
            
            LOGGER.error("Data layer step 1. data properties - " + dataProperties.toString());
            
            String componentID = ComponentUtils.getId(resource, this.currentPage,this.componentContext);
            
            LOGGER.error("Data layer step 2. component ID - " + componentID);
            
            try {
                data = String.format("{\"%s\":%s}", componentID, new ObjectMapper().writeValueAsString(dataProperties));
                LOGGER.error("Data layer step 3. Final data - " + data);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error during generation of data layer value", e);
            }
        }
        
        return data;
    }
}
