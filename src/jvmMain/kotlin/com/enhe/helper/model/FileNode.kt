// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.model

import java.io.File

data class FileNode(
    val presentFile: File,
    val files: List<File>,
)