package com.eem.nimble.presentation.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eem.nimble.presentation.theme.NimbleAndroidTheme
import com.eem.nimble.presentation.theme.robotoFamily

@Composable
fun ButtonApp(modifier: Modifier = Modifier, onClick: () -> Unit, text: String) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF15151A)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = text,
            style = TextStyle(
                fontSize = 17.sp,
                lineHeight = 22.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview
@Composable
fun ButtonAppPreview() {
    NimbleAndroidTheme {
        ButtonApp(onClick = {}, text = "Log In")
    }
}