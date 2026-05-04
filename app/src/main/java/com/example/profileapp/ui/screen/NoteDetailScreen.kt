package com.example.profileapp.ui.screen

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.profileapp.data.PostListUiState
import com.example.profileapp.viewmodel.NoteViewModel
import com.example.profileapp.viewmodel.PostViewModel

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    postViewModel: PostViewModel,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {

    LaunchedEffect(noteId) {
        postViewModel.loadPostDetail(noteId)
    }

    val detailUiState by postViewModel.detailUiState.collectAsState()

    when (val state = detailUiState) {

        // 🔥 LOADING
        is PostListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // 🔥 SUCCESS
        is PostListUiState.Success -> {

            val post = state.posts.firstOrNull()

            if (post == null) {
                Text("Data tidak ditemukan")
                return
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                AsyncImage(
                    model = "https://picsum.photos/seed/${post.id}/800/400",
                    contentDescription = "Gambar detail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Post #${post.id} • User ${post.userId}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = post.title.replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = post.body,
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = onBack) {
                            Text("Kembali")
                        }
                        Button(onClick = { onEdit(noteId) }) {
                            Text("Edit")
                        }
                    }
                }
            }
        }

        // 🔥 ERROR (INI YANG KEMARIN KURANG)
        is PostListUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Gagal memuat detail",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.message,
                        color = Color.Gray
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = onBack) {
                            Text("Kembali")
                        }
                        Button(onClick = {
                            postViewModel.loadPostDetail(noteId)
                        }) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }
        }
    }
}