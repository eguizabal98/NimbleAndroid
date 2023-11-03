@file:OptIn(ExperimentalMaterial3Api::class)

package com.eem.nimble.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eem.nimble.R
import com.eem.nimble.presentation.theme.NimbleAndroidTheme

@Composable
fun TextFieldApp(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    isPassword: Boolean = false,
    onTextChange: (String) -> Unit,
    endComposable: @Composable () -> Unit = {}
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0x2EFFFFFF), RoundedCornerShape(12.dp)),
            value = value,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                fontSize = 17.sp,
                lineHeight = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal
            ),
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.neuzeit)),
                        fontWeight = FontWeight.Normal,
                        color = Color(0x80FFFFFF),
                    )
                )
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default
        )
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            endComposable()
        }
    }
}

@Preview
@Composable
fun TextFieldAppPreview() {
    NimbleAndroidTheme {
        TextFieldApp(modifier = Modifier, value = "", placeholder = "Email", isPassword = false, {})
    }
}