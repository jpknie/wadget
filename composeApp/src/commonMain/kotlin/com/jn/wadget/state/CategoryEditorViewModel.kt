package com.jn.wadget.state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.jn.wadget.models.Category
import com.jn.wadget.models.CategoryType
import com.jn.wadget.models.generateId
import com.jn.wadget.repository.InMemoryCategoryRepository

class CategoryEditorViewModel(private val repository: InMemoryCategoryRepository) {

    // _state is a MutableState<CategoryEditorState>
    private var _state = mutableStateOf(CategoryEditorState())
    val state: State<CategoryEditorState> get() = _state

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _state.value = _state.value.copy(
            categories = repository.getAll().map { it.copy() }
        )
    }

    fun onAddCategory(name: String, type: CategoryType) {
        repository.add(Category(id = generateId(), name = name, type = type))
        loadCategories()
    }

    fun onEditCategory(category: Category) {
        repository.update(category)
        loadCategories()
    }

    fun onToggleMandatory(category: Category, isMandatory: Boolean) {
        repository.update(category.copy(isMandatory = isMandatory))
        loadCategories()
    }

    fun onWeightChange(category: Category, weight: Float) {
        repository.update(category.copy(weight = weight))
        loadCategories()
    }
}