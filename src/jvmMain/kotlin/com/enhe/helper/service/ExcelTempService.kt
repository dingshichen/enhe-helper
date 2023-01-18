// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.service

import com.alibaba.excel.EasyExcel
import com.alibaba.excel.support.ExcelTypeEnum
import java.io.File
import java.io.InputStream

/**
 * Excel 模版处理
 */
object ExcelTempService {

    val BUGS_TEMP = "temp${File.separator}bugs-temp.xlsx"

    suspend fun fill(temp: InputStream, out: String, data: List<Any>) {
        EasyExcel.write(out)
            .excelType(ExcelTypeEnum.XLSX)
            .withTemplate(temp)
            .sheet(1)
            .doFill(data)
    }
}