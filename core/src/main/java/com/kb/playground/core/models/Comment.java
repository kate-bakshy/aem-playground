package com.kb.playground.core.models;

import java.util.List;

import com.adobe.cq.wcm.core.components.models.Component;
import com.adobe.cq.wcm.core.components.models.Image;

/**
 * Defines the {@code Comment} Sling Model used for the {@code /apps/playground/components/comment} component.
 *
 */
public interface Comment extends Component {
    
    default String getAuthorName() {
        return null;
    }
    
    default List<String>getAuthorOccupations() {
        return null;
    }
    
    default String getText() {
        return null;
    }
    
    default Image getImage() {
        return null;
    }
    
    default boolean hasContent() {
        return false;
    }
}
