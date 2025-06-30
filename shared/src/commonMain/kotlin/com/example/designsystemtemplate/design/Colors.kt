package design

enum class AppColorScheme {
    Light, Dark
}

data class AppColors(
    val primary: Long,
    val secondary: Long,
    val background: Long,
    val surface: Long,
    val error: Long,
    val onPrimary: Long,
    val onSecondary: Long,
    val onBackground: Long,
    val onSurface: Long,
    val onError: Long
)

object ColorTokens {
    val Light = AppColors(
        primary = 0xFF6200EE,
        secondary = 0xFF03DAC6,
        background = 0xFFFFFFFF,
        surface = 0xFFFFFFFF,
        error = 0xFFB00020,
        onPrimary = 0xFFFFFFFF,
        onSecondary = 0xFF000000,
        onBackground = 0xFF000000,
        onSurface = 0xFF000000,
        onError = 0xFFFFFFFF
    )

    val Dark = AppColors(
        primary = 0xFFBB86FC,
        secondary = 0xFF03DAC6,
        background = 0xFF121212,
        surface = 0xFF03DAC6,
        error = 0xFFCF6679,
        onPrimary = 0xFF000000,
        onSecondary = 0xFF000000,
        onBackground = 0xFFFFFFFF,
        onSurface = 0xFFFFFFFF,
        onError = 0xFF000000
    )
}