Comment
====
Comment component written in HTL that provides ability to style content as comment section.

## Features

* Dialog to define author name and comment text
* Styles

### Use Object
The Comment component uses the `com.kb.playground.core.components.models.Comment` Sling model as its Use-object. The current implementation reads
the following resource properties:

1. `./authorName` - the author name
2. `./text` - the comment text
3. `./id` - defines the component HTML ID attribute.

## BEM Description
```
BLOCK cmp-comment
    ELEMENT cmp-comment__author
    ELEMENT cmp-comment__text
```
