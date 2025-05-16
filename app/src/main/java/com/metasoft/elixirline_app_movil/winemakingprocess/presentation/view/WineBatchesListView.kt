package com.metasoft.elixirline_app_movil.winemakingprocess.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.di.PresentationModuleWinemaking
import com.metasoft.elixirline_app_movil.winemakingprocess.presentation.viewmodel.WineBatchesListViewModel

@Preview
@Composable
fun WineBatchesListView(
    wineBatchesListViewModel: WineBatchesListViewModel = PresentationModuleWinemaking.getWineBatchesListViewModel(),
    onTap: (String?) -> Unit = {},
    modifier: Modifier
) {

    wineBatchesListViewModel.getWineBatches()

    val wineBatches = wineBatchesListViewModel.wineBatches.collectAsState()


    LazyColumn(modifier = Modifier.padding()) {
        items(wineBatches.value) { wineBatch ->
            Card(modifier = Modifier.padding(8.dp), onClick = { onTap(wineBatch.internalCode) }) {
                Row {
                    Column(modifier = Modifier.padding(8.dp)) {
                        wineBatch.internalCode?.let {
                            Text(it, modifier = modifier.padding(8.dp))
                        }

                    }
                }
            }

        }
    }

}