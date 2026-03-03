package com.jn.wadget.state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.jn.wadget.models.Category
import com.jn.wadget.models.generateId
import com.jn.wadget.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CategoryEditorViewModel(private val repository: CategoryRepository) : ViewModel() {

    // _state is a MutableState<CategoryEditorState>
    private var _state = mutableStateOf(CategoryEditorState())
    val state: State<CategoryEditorState> get() = _state
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    init {
        coroutineScope.launch {
            loadCategories()
        }

    }

    private suspend fun loadCategories() {
        _state.value = _state.value.copy(
            categories = repository.getAll().map { it.copy() }
        )
    }

    suspend fun onAddCategory(name: String) {
        repository.add(Category(
            id = generateId(), name = name,
            weight = 0f,
            capCents = 0,
            isMandatory = false,
            mandatoryCents = 0
        ))
        loadCategories()
    }

    suspend fun onEditCategory(category: Category) {
        repository.update(category)
        loadCategories()
    }

    suspend fun onToggleMandatory(category: Category, isMandatory: Boolean) {
        repository.update(category.copy(isMandatory = isMandatory))
        loadCategories()
    }

    suspend fun onWeightChange(category: Category, weight: Float) {
        repository.update(category.copy(weight = weight))
        loadCategories()
    }
}