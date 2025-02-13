package com.kb.playground.core.models.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.adobe.cq.wcm.core.components.models.datalayer.builder.DataLayerBuilder;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;

import com.kb.playground.core.models.Comment;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = {Comment.class},
        resourceType = CommentImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CommentImpl extends AbstractComponentImpl implements Comment {
    
    protected static final String RESOURCE_TYPE = "playground/components/comment";
    
    @ValueMapValue
    private String authorName;
    
    @ValueMapValue
    private String text;
    
    public String getAuthorName() {
        return authorName;
    }
    
    public String getText() {
        return text;
    }
    
    public boolean isEmpty() {
        return StringUtils.isBlank(authorName) && StringUtils.isBlank(text);
    }
    
    /**
     * Method to define data layer content for the component (uses DataLayerBuilder)
     *
     * @return The component data.
     */
    @Override
    public ComponentData getComponentData() {
        return DataLayerBuilder.extending(super.getComponentData()).asComponent()
                .withText(this::getText)
                .build();
    }
}