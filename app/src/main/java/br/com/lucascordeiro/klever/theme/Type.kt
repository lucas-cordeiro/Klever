package br.com.lucascordeiro.klever.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.lucascordeiro.klever.R

val poppinsFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.poppins_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.poppins_italic,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.poppins_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.poppins_semi_bold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.poppins_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        ),
        Font(
          resId = R.font.poppins_extra_bold,
          weight = FontWeight.ExtraBold,
          style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.poppins_light,
            weight = FontWeight.Light,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.poppins_black,
            weight = FontWeight.Black,
            style = FontStyle.Normal
        )
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = poppinsFamily,
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)