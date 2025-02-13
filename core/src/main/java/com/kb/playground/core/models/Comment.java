package com.kb.playground.core.models;

import com.adobe.cq.wcm.core.components.models.Component;

/**
 * Defines the {@code Comment} Sling Model used for the {@code /apps/playground/components/comment} component.
 *
 */

public interface Comment extends Component {

    /**
     * Retrieves the author name to be displayed.
     *
     * @return the author name to be displayed, or {@code null} if no value can be returned
     */
    default String getAuthorName() {
        return null;
    }
    
    /**
     * Retrieves the comment text to be displayed.
     *
     * @return the comment text to be displayed, or {@code null} if no value can be returned
     */
    default String getText() {
        return null;
    }
    
    default boolean isEmpty() {
        return true;
    }
}
