// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-18

package com.enhe.helper.service

import cn.hutool.core.text.csv.CsvUtil
import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import com.enhe.helper.exception.JsonReadFailException
import com.enhe.helper.exception.UnSupportFileException
import com.enhe.helper.model.Bug
import java.io.File
import java.nio.charset.Charset

object BugService {

    fun getData(file: File): List<Bug> {
        return when (file.name.substringAfterLast(".")) {
            "json" -> getDataByJsonFile(file)
            "csv" -> getDataByCsvFile(file)
            else -> throw UnSupportFileException()
        }
    }

    private fun getDataByJsonFile(file: File): List<Bug> {
        val jsonArray = JSONUtil.readJSONArray(file, Charset.defaultCharset())
        return jsonArray.mapIndexed { idx, json ->
            when (json) {
                is JSONObject -> Bug(idx + 1, "", "", json.getStr("title"), json.getStr("assigners"), "", "")
                else -> throw JsonReadFailException()
            }
        }.toList()
    }

    private fun getDataByCsvFile(file: File): List<Bug> {
        val rows = CsvUtil.getReader().read(file).rows
        val idxId = rows[0].rawList.indexOf("任务ID")
        val idxTitle = rows[0].rawList.indexOf("任务标题")
        val idxPrincipal = rows[0].rawList.indexOf("任务负责人")
        return rows.filterIndexed { idx, _ -> idx > 0 }
            .mapIndexed { idx, row ->  Bug(idx + 1, "", row.rawList[idxId], row.rawList[idxTitle], row.rawList[idxPrincipal], "", "")}
            .toList()
    }
}