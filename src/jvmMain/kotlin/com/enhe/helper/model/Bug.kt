// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

package com.enhe.helper.model

/**
 * 缺陷
 */
data class Bug(

    val sort: Int,

    val demand: String,

    val id: String,

    val title: String,

    val principal: String,

    val cause: String,

    val remark: String,
)
