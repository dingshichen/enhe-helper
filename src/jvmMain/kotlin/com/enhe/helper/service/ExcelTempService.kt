// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.service

import com.alibaba.excel.EasyExcel

/**
 * Excel 模版处理
 */
object ExcelTempService {

    const val BUGS_TEMP = "temp/bugs-temp.xlsx"

    suspend fun fill(temp: String, out: String, data: List<Any>) {
        EasyExcel.write(out)
            .withTemplate(temp)
            .sheet(1)
            .doFill(data)
    }
}