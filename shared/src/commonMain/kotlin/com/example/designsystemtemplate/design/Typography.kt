package design

enum class FontWeight {
    Light, Normal, Medium, Bold
}

data class AppTextStyle(
    val fontSize: Int,
    val fontWeight: FontWeight,
)

data class Typography(
    val h1: AppTextStyle,
    val h2: AppTextStyle,
    val body: AppTextStyle,
    val caption: AppTextStyle
)

object TypographyTokens {
    val Default = Typography(
        h1 = AppTextStyle(fontSize = 32, fontWeight = FontWeight.Bold),
        h2 = AppTextStyle(fontSize = 24, fontWeight = FontWeight.Medium),
        body = AppTextStyle(fontSize = 16, fontWeight = FontWeight.Normal),
        caption = AppTextStyle(fontSize = 12, fontWeight = FontWeight.Light)
    )
}