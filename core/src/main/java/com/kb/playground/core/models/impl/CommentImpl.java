package com.kb.playground.core.models.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Image;
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
    
    @OSGiService
    private ModelFactory modelFactory;
    
    @Self
    SlingHttpServletRequest request;
    
    @ValueMapValue
    private String authorName;
    
    @ValueMapValue
    private List<String> authorOccupations;
    
    @ValueMapValue
    private String text;
    
    private Image image;
    
    @PostConstruct
    private void init() {
        image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
    }
    
    @Override
    public String getAuthorName() {
        return authorName;
    }
    
    @Override
    public List<String> getAuthorOccupations() {
        return authorOccupations;
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public Image getImage() {
        return image;
    }
    
    @Override
    public boolean hasContent() {
        return StringUtils.isNotBlank(authorName)
                || StringUtils.isNotBlank(text)
                || (authorOccupations != null && authorOccupations.size() > 0)
                || (image != null && StringUtils.isNotBlank(image.getSrc()));
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