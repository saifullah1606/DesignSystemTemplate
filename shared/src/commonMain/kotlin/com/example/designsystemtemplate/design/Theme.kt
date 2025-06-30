package design

data class AppTheme(
    val colors: AppColors,
    val typography: Typography,
    val spacing: AppSpacing
)

object Themes {
    val Light = AppTheme(
        colors = ColorTokens.Light,
        typography = TypographyTokens.Default,
        spacing = SpacingTokens
    )

    val Dark = AppTheme(
        colors = ColorTokens.Dark,
        typography = TypographyTokens.Default,
        spacing = SpacingTokens
    )
}

fun getTheme(scheme: AppColorScheme): AppTheme {
    return when (scheme) {
        AppColorScheme.Light -> Themes.Light
        AppColorScheme.Dark -> Themes.Dark
    }
}