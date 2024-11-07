package com.kb.playground.core.models.impl;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.adobe.cq.wcm.core.components.models.datalayer.builder.DataLayerBuilder;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.drew.lang.annotations.NotNull;

import org.apache.commons.lang3.StringUtils;
import com.kb.playground.core.models.Comment;

@Model(
        adaptables = Resource.class,
        adapters = {Comment.class, ComponentExporter.class},
        resourceType = CommentImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CommentImpl extends AbstractComponentImpl implements Comment {
    
    protected static final String RESOURCE_TYPE = "/apps/playground/components/comment";
    
    @Inject
    private String authorName;
    
    @Inject
    @Required
    private String text;
    
    @Self
    private Resource resource;
    
    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }
    
    @Override
    @NotNull
    public String getExportedType() {
        return resource.getResourceType();
    }
    
    @Override
    protected ComponentData getComponentData() {
        return DataLayerBuilder.extending(super.getComponentData()).asComponent()
                .withText(() -> StringUtils.defaultIfEmpty(this.getText(), StringUtils.EMPTY))
                .build();
    }
}
