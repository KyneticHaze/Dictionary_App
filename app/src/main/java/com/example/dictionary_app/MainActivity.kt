package com.example.dictionary_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionary_app.feature_dictionary.presentation.UiEvent
import com.example.dictionary_app.feature_dictionary.presentation.WordInfoItem
import com.example.dictionary_app.feature_dictionary.presentation.WordInfoViewModel
import com.example.dictionary_app.ui.theme.Dictionary_AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dictionary_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel: WordInfoViewModel = hiltViewModel()
                    val state = viewModel.state.value
                    val scaffoldState = SnackbarHostState()

                    LaunchedEffect(key1 = true) {
                        viewModel.eventFlow.collectLatest { event ->

                            when (event) {

                                is UiEvent.ShowSnackbar -> {
                                    scaffoldState.showSnackbar(
                                        message = event.message
                                    )
                                }
                            }
                        }
                    }
                    Scaffold(
                        snackbarHost = { scaffoldState }
                    ) { scaffoldPadding ->

                        Box(
                            modifier = Modifier
                                .padding(scaffoldPadding)
                                .background(MaterialTheme.colorScheme.background)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {

                                TextField(
                                    value = viewModel.searchQuery.value,
                                    onValueChange = viewModel::onSearch,
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = {
                                        Text(text = "Search...")
                                    })
                                
                                Spacer(modifier = Modifier.height(16.dp))

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(state.wordInfoItems.size) {i ->
                                        val wordInfo = state.wordInfoItems[i]

                                        if (i > 0) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }

                                        WordInfoItem(wordInfo = wordInfo)

                                        if (i < state.wordInfoItems.size - 1) {
                                            Divider()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}