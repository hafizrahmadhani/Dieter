/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dieter.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DieterProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    color: Color = MaterialTheme.colors.primaryVariant,
    borderColor: Color = MaterialTheme.colors.primary
) {
    val progressBarShape = RoundedCornerShape(percent = 50)
    Column(modifier = modifier) {
        Box {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .border(2.dp, borderColor, progressBarShape)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(16.dp)
                    .clip(progressBarShape)
                    .background(
                        color,
                        progressBarShape.copy(
                            topEnd = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
            )
        }
    }
}

@Composable
fun DieterVerticalBarChart(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    color: Color = MaterialTheme.colors.primaryVariant
) {
    val progressBarShape = RoundedCornerShape(50)
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Spacer(
                modifier = Modifier.fillMaxHeight()
                    .width(46.dp)
            )
            Spacer(
                modifier = Modifier.fillMaxHeight(progress)
                    .width(46.dp)
                    .clip(progressBarShape)
                    .background(color)
            )
        }
    }
}
