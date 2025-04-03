package com.example.gotoit.presentation.theme.icons.vectorImages

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Map: ImageVector
    get() {
        if (_Map != null) {
            return _Map!!
        }
        _Map = ImageVector.Builder(
            name = "Map",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveToRelative(600f, 840f)
                lineToRelative(-240f, -84f)
                lineToRelative(-186f, 72f)
                quadToRelative(-20f, 8f, -37f, -4.5f)
                reflectiveQuadTo(120f, 790f)
                verticalLineToRelative(-560f)
                quadToRelative(0f, -13f, 7.5f, -23f)
                reflectiveQuadToRelative(20.5f, -15f)
                lineToRelative(212f, -72f)
                lineToRelative(240f, 84f)
                lineToRelative(186f, -72f)
                quadToRelative(20f, -8f, 37f, 4.5f)
                reflectiveQuadToRelative(17f, 33.5f)
                verticalLineToRelative(560f)
                quadToRelative(0f, 13f, -7.5f, 23f)
                reflectiveQuadTo(812f, 768f)
                lineToRelative(-212f, 72f)
                close()
                moveTo(560f, 742f)
                verticalLineToRelative(-468f)
                lineToRelative(-160f, -56f)
                verticalLineToRelative(468f)
                lineToRelative(160f, 56f)
                close()
                moveTo(640f, 742f)
                lineTo(760f, 702f)
                verticalLineToRelative(-474f)
                lineToRelative(-120f, 46f)
                verticalLineToRelative(468f)
                close()
                moveTo(200f, 732f)
                lineTo(320f, 686f)
                verticalLineToRelative(-468f)
                lineToRelative(-120f, 40f)
                verticalLineToRelative(474f)
                close()
                moveTo(640f, 274f)
                verticalLineToRelative(468f)
                verticalLineToRelative(-468f)
                close()
                moveTo(320f, 218f)
                verticalLineToRelative(468f)
                verticalLineToRelative(-468f)
                close()
            }
        }.build()

        return _Map!!
    }

@Suppress("ObjectPropertyName")
private var _Map: ImageVector? = null
