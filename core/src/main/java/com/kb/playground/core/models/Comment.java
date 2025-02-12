package com.kb.playground.core.models;

/**
 * Defines the {@code Comment} Sling Model used for the {@code /apps/playground/components/comment} component.
 *
 */

public interface Comment {

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
    
    default String getId() {
        return null;
    }
    
    default boolean isEmpty() {
        return true;
    }
    
    /**
     * Retrieves the comment text to be displayed.
     *
     * @return the comment text to be displayed, or {@code null} if no value can be returned
     */
    default String getData() {
        return null;
    }
}
