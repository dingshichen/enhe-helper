// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.enhe.helper.consts.Res
import com.enhe.helper.model.FileNode
import com.enhe.helper.service.FileService
import java.io.File

/**
 * 文件选择器
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileChooser(node: MutableState<FileNode>, onClickFile: File.() -> Unit) {
    Box {
        val state = rememberLazyListState(0)
        LazyColumn(state = state) {
            items(node.value.files) {
                Row(
                    modifier = Modifier.fillMaxWidth().combinedClickable(
                        onClick = {
                            if (!it.isDirectory) {
                                it.onClickFile()
                            }
                        },
                        onDoubleClick = {
                            if (it.isDirectory) {
                                node.value = FileNode(it, FileService.listChildren(it))
                            }
                        }
                    )
                ) {
                    Icon(
                        painterResource(if (it.isDirectory) Res.Icons.FOLDER else Res.Icons.TEXT),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(it.name)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(state),
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight()
        )
    }
}