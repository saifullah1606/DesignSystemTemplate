package design

data class AppSpacing(
    val none: Int = 0,
    val xs: Int = 4,
    val sm: Int = 8,
    val md: Int = 16,
    val lg: Int = 24,
    val xl: Int = 32
)

val SpacingTokens = AppSpacing()