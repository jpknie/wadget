package com.jn.wadget.state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.jn.domain.Tag
import com.jn.wadget.models.generateId
import com.jn.wadget.repository.TagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TagEditorViewModel(private val repository: TagRepository) : ViewModel() {

    // _state is a MutableState<TagEditorState>
    private var _state = mutableStateOf(TagEditorState())
    val state: State<TagEditorState> get() = _state
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    init {
        coroutineScope.launch {
            loadTags()
        }

    }

    private suspend fun loadTags() {
        _state.value = _state.value.copy(
            tags = repository.getAll().map { it.copy() }
        )
    }

    suspend fun onAddTag(name: String) {
        repository.add(Tag(
            id = generateId(), name = name,
            weight = 0.0,
            capCents = 0,
            mandatory = false,
            mandatoryCents = 0,
            softness = 0.0
        ))
        loadTags()
    }

    suspend fun onEditTag(tag: Tag) {
        repository.update(tag)
        loadTags()
    }

    suspend fun onToggleMandatory(tag: Tag, isMandatory: Boolean) {
        repository.update(tag.copy(mandatory = isMandatory))
        loadTags()
    }

    suspend fun onWeightChange(tag: Tag, weight: Double) {
        repository.update(tag.copy(weight = weight))
        loadTags()
    }

    suspend fun onSoftnessChange(tag: Tag, softness: Double) {
        repository.update(tag.copy(softness = softness))
        loadTags()
    }

    suspend fun onDeleteTag(tag: Tag) {
        repository.delete(tag.id)
        loadTags()
    }
}