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
 * Temporary-chat glyph (batch-10 G1, tail flipped outward in batch-11 H1):
 * a circle outline broken by THREE strip gaps — top-left, top-right and
 * bottom-middle — leaving three arc segments, plus a small FILLED triangle
 * at the bottom-left that sits OUTSIDE the circle and points away from it:
 * the speech-bubble tail. Reads as "a transient chat". 24dp viewport; arcs
 * use the IconDefaultFill stroke and the tail an IconDefaultFill fill, so
 * `Icon(tint = ...)` recolors both paths.
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

// Tail: filled triangle OUTSIDE the circle at its bottom-left (apex along
// 225°). Base chord ON the ring at 210°→240°, apex at r≈12 along 225°
// (3.51,20.49) pointing AWAY from the center, so the glyph reads as a
// bubble with its tail hanging off the ring (batch-11 H1 — batch-10 G1's
// apex pointed inward and read as a notch cut into the ring).
private const val TAIL = "M4.21,16.5 L7.5,19.79 L3.51,20.49 Z"
