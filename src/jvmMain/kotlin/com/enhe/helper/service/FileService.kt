// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.service

import cn.hutool.core.io.FileUtil
import java.io.File

/**
 * 文件处理
 */
object FileService {

    fun home(): File {
        return FileUtil.getUserHomeDir()
    }

    fun getParent(file: File): File {
        return file.parentFile ?: file
    }

    fun listHomeFiles(): List<File> {
        return listFile(FileUtil.getUserHomeDir().listFiles())
    }

    fun listChildren(file: File): List<File> {
        return listFile(file.listFiles())
    }

    /**
     * 目录放在前，文件放在后
     */
    private fun listFile(files: Array<File>?): List<File> {
        // 过滤隐藏文件 & osx 回收站文件
        return files?.filter { !it.name.startsWith(".") && it.name != "\$RECYCLE.BIN" }
            ?.sortedWith(
                Comparator.comparing<File?, Boolean?> { !it.isDirectory }
                    .thenComparing { o1, o2 -> o1.name.compareTo(o2.name) }
            )
            ?.toList()
            ?: emptyList()
    }
}