// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-01-17

import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.enhe.helper.compose.App
import com.enhe.helper.compose.WINDOW_HEIGHT
import com.enhe.helper.compose.WINDOW_WIDTH
import com.enhe.helper.consts.Res

fun main() = application {
    val trayState = rememberTrayState()
    Tray(
        state = trayState,
        icon = painterResource(Res.Icons.APP),
        menu = {
            Item(text = "退出", onClick = ::exitApplication)
        }
    )
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(WINDOW_HEIGHT, WINDOW_WIDTH)
        ),
        title = "Enhe Helper",
        icon = painterResource(Res.Icons.APP)
    ) {
        App(trayState)
    }
}
