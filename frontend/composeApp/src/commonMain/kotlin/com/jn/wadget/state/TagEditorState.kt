package com.jn.wadget.state

import com.jn.domain.Tag

data class TagEditorState(
    val tags: List<Tag> = emptyList(),
    val editingTag: Tag? = null,
    val errorMessage: String? = null
)