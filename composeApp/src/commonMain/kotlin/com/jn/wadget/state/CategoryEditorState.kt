package com.jn.wadget.state

import com.jn.wadget.models.dto.Category

data class CategoryEditorState(
    val categories: List<Category> = emptyList(),
    val editingCategory: Category? = null,
    val errorMessage: String? = null
)