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
 * Temporary-chat glyph (batch-10 G1): a circle outline broken by THREE
 * strip gaps — top-left, top-right and bottom-middle — leaving three arc
 * segments, plus a small FILLED triangle at the bottom-left that points
 * into the circle: the speech-bubble tail notch. Reads as "a transient
 * chat". 24dp viewport; arcs use the IconDefaultFill stroke and the tail
 * an IconDefaultFill fill, so `Icon(tint = ...)` recolors both paths.
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
        ).addPath(
            pathData = addPathNodes(TAIL),
            pathFillType = PathFillType.NonZero,
            name = "TailPath",
            fill = SolidColor(IconDefaultFill),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 4f,
            trimPathStart = 0f,
            trimPathEnd = 1f,
            trimPathOffset = 0f,
        ).build()
}

// Geometry: circle center (12,12), radius 9 (same as batch-9). Three gaps
// ~22° wide, named by screen angle (0° = east, 90° = north):
//   top-right at 45°, top-left at 135°, bottom-middle at 270°.
// The three remaining arc segments are traversed counter-clockwise on
// screen (SVG sweep-flag 0; batch-9's split-circle used the mirrored
// clockwise direction, hence its sweep-flag 1), each the minor arc
// between adjacent gap edges:
//   top   56°  → 124°  M17.03,4.54 → 6.97,4.54   (over 12 o'clock)
//   left  146° → 259°  M4.54,6.97  → 10.28,20.83 (down the left flank)
//   right 281° → 34°   M13.72,20.83 → 19.46,6.97 (up the right flank)
private const val PATH =
    "M17.03,4.54 A9,9 0 0 0 6.97,4.54 M4.54,6.97 A9,9 0 0 0 10.28,20.83 M13.72,20.83 A9,9 0 0 0 19.46,6.97"

// Tail: filled triangle at the circle's bottom-left (~225°). Its base
// chord sits ON the ring (215°/235°) and the apex points toward the
// center, so the notch reads as cut into the ring (batch-10 G1).
private const val TAIL = "M4.63,17.16 L8.82,15.18 L6.84,19.37 Z"
