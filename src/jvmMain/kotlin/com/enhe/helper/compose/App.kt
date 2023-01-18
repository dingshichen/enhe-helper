// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.rememberNotification
import cn.hutool.core.date.DateTime
import cn.hutool.core.date.DateUtil
import cn.hutool.core.io.FileUtil
import com.enhe.helper.model.FileNode
import com.enhe.helper.service.BugService
import com.enhe.helper.service.ExcelTempService
import com.enhe.helper.service.FileService
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset

// 默认窗口尺寸
val WINDOW_HEIGHT = 800.dp
val WINDOW_WIDTH = 600.dp

// 底部状态栏尺寸
val STATE_BAR_HEIGHT = 30.dp

@Composable
fun App(trayState: TrayState) {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        val info = rememberNotification("Enhe Helper", "导出成功！文件保存在源文件所在目录。", Notification.Type.Info)
        val error = rememberNotification("Enhe Helper", "导出失败!", Notification.Type.Error)
        // 点击导入，文件选择器和导航按钮展开
        var exportButtonText by remember { mutableStateOf("导入") }
        // 记住当前目录和展示的子目录
        val node = remember { mutableStateOf(FileNode(FileService.home(), FileService.listHomeFiles())) }
        // 记住选择的源文件
        var chooseFile by remember { mutableStateOf<File?>(null) }
        Box {
            Column(modifier = Modifier.height(WINDOW_HEIGHT - STATE_BAR_HEIGHT)) {
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Spacer(modifier = Modifier.width(25.dp))
                    Column {
                        Button(onClick = {
                            exportButtonText = if (exportButtonText == "导入") "取消" else "导入"
                        }
                        ) {
                            Text(exportButtonText)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        AnimatedVisibility(exportButtonText == "取消") {
                            Button(onClick = { node.value = FileService.home().run { FileNode(this, FileService.listChildren(this)) } }) {
                                Text("返回家目录")
                            }
                        }
                        AnimatedVisibility(exportButtonText == "取消") {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        AnimatedVisibility(exportButtonText == "取消") {
                            Button(onClick = {
                                node.value = FileService.getParent(node.value.presentFile).run {
                                    FileNode(this, FileService.listChildren(this))
                                }
                            }
                            ) {
                                Text("返回上一级")
                            }
                        }
                        AnimatedVisibility(chooseFile != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        AnimatedVisibility(chooseFile != null) {
                            Button(onClick = {
                                // 导出
                                chooseFile?.let {
                                    scope.launch {
                                        export(it, trayState, info, error)
                                    }
                                }
                            }) {
                                Text("确定导出")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    AnimatedVisibility(exportButtonText == "取消") {
                        FileChooser(node) {
                            chooseFile = if (chooseFile == this) null else this
                        }
                    }
                }
            }
            // 状态栏
            StatusBar("选择的源文件：${chooseFile?.name.orEmpty()}")
        }
    }
}

suspend fun export(file: File, trayState: TrayState, info: Notification, error: Notification) {
    try {
        ExcelTempService.javaClass.classLoader.getResourceAsStream(ExcelTempService.BUGS_TEMP)?.let {
            // 填充
            ExcelTempService.fill(it,
                "${file.parent}${File.separator}bugs-${DateUtil.format(DateTime(), "yyMMddHHmmss")}.xlsx",
                BugService.getData(file))
        }
        // 发送通知
        trayState.sendNotification(info)
    } catch (e: Exception) {
        mutableListOf("", "----", "", e.message)
            .also { it.addAll(e.stackTrace.map { s -> s.toString() }.toList()) }
            .run {
                FileUtil.appendLines(this, "${file.parent}${File.separator}logs${File.separator}error.log", Charset.defaultCharset())
            }
        trayState.sendNotification(error)
    }
}
