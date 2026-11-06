package com.m57.hermescontrol.ui.common

import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp
import com.m57.hermescontrol.theme.IconDefaultFill

/**
 * Temporary-chat glyph (batch-9 F1): a circle split by a horizontal gap at
 * its vertical middle — a top arc and a bottom arc, stroke-only. The split
 * reads as "the circle is incomplete", i.e. a transient chat. 24dp
 * viewport, IconDefaultFill stroke so `Icon(tint = ...)` recolors it.
 */
val TempChatIcon: ImageVector by lazy {
    ImageVector
        .Builder(
            name = "TempChat",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).addPath(
            pathData = addPathNodes(PATH),
            pathFillType = PathFillType.NonZero,
            name = "BubblePath",
            fill = null,
            fillAlpha = 1f,
            stroke = SolidColor(IconDefaultFill),
            strokeAlpha = 1f,
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4f,
            trimPathStart = 0f,
            trimPathEnd = 1f,
            trimPathOffset = 0f,
        ).build()
}

// Geometry: circle center (12,12), radius 9. The horizontal gap band
// y∈[10.5,13.5] (~19° total around the horizontal axis) splits it into a
// top arc (left gap → right gap over the top) and a bottom arc (right gap →
// left gap under the bottom). Gap-edge x = 12 ± √(81−2.25) ≈ 3.13/20.87.
private const val PATH =
    "M3.13,10.5 A9,9 0 0 1 20.87,10.5 M20.87,13.5 A9,9 0 0 1 3.13,13.5"
