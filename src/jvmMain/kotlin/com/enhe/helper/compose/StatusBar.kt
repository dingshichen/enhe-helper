// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-18

package com.enhe.helper.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * 底部状态栏
 */
@Composable
fun StatusBar(primaryText: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(STATE_BAR_HEIGHT)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(primaryText)
            }
        }
    }
}