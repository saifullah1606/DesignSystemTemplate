package com.example.designsystemtemplate.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Top-level response structure from the API.
 * It contains a list of [MainCategory] objects.
 */
@Serializable
data class StoreFrontResponse(
    @SerialName("MainCategoryList")
    val mainCategoryList: List<MainCategory>
)

/**
 * Represents a main category in the store front.
 * Examples: "Postpaid", "Prepaid", "Numbers", "MBB", "Activate SIM", "Top Up Number".
 */
@Serializable
data class MainCategory(
    @SerialName("Id")
    val id: Int,
    @SerialName("DeepLink")
    val deepLink: String? = null,
    @SerialName("Name")
    val name: String,
    @SerialName("DisplayInChangePlan")
    val displayInChangePlan: Boolean? = null,
    @SerialName("Order")
    val order: Int,
    @SerialName("NameKey")
    val nameKey: String? = null,
    @SerialName("NameColor")
    val nameColor: String? = null,
    @SerialName("Icon")
    val icon: String? = null,
    @SerialName("SubCategoryList")
    val subCategoryList: List<SubCategory>? = null
)

/**
 * Represents a sub-category within a main category.
 * Examples: "Switch Postpaid Plans", "Postpaid plans", "Handpicked plans for you", "Basic Numbers".
 */
@Serializable
data class SubCategory(
    @SerialName("Id")
    val id: Int,
    @SerialName("Name")
    val name: String? = null, // Can be null in some cases in your JSON
    @SerialName("NameKey")
    val nameKey: String? = null,
    @SerialName("NameColor")
    val nameColor: String? = null,
    @SerialName("Description")
    val description: String? = null,
    @SerialName("DescriptionKey")
    val descriptionKey: String? = null,
    @SerialName("DescriptionColor")
    val descriptionColor: String? = null,
    @SerialName("Order")
    val order: Int,
    @SerialName("CTA")
    val cta: String? = null,
    @SerialName("Vanity")
    val vanity: Int? = null, // Specific to "Numbers" categories
    @SerialName("Cards")
    val cards: List<CardWrapper>? = null
)

/**
 * A wrapper for a list of [CardItem]s.
 * The API uses "Cards" which contains objects with a "CardList" array.
 */
@Serializable
data class CardWrapper(
    @SerialName("Id")
    val id: Int,
    @SerialName("Order")
    val order: Int,
    @SerialName("Shape")
    val shape: String, // e.g., "SingleBanner", "HorizontalCard", "NumberCard", "VerticalCard"
    @SerialName("CardList")
    val cardList: List<CardItem>
)

/**
 * Represents an individual card item within a CardList.
 * This is a highly polymorphic structure, so we need to define all possible fields
 * and make them nullable.
 */
@Serializable
data class CardItem(
    @SerialName("Id")
    val id: Int,
    @SerialName("Type")
    val type: String, // e.g., "InformationBanner", "PlanInformationBanner", "NumbersBanner", "PredefinedDenominationCard", "RightImageBanner"
    @SerialName("ImageKey")
    val imageKey: String? = null,
    @SerialName("BannerBackgroundColor")
    val bannerBackgroundColor: String? = null,
    @SerialName("Order")
    val order: Int,
    @SerialName("CTA")
    val cta: String? = null,
    @SerialName("Title")
    val title: String? = null,
    @SerialName("Title_Key")
    val titleKey: String? = null,
    @SerialName("Description1")
    val description1: String? = null,
    @SerialName("Description1Key")
    val description1Key: String? = null,
    @SerialName("EnableOptima")
    val enableOptima: Boolean? = null,
    // Fields specific to "PlanInformationBanner"
    @SerialName("PlanName")
    val planName: String? = null,
    @SerialName("ProductCode")
    val productCode: Int? = null,
    @SerialName("ImageSize")
    val imageSize: String? = null, // e.g., "Medium"
    @SerialName("WebImage")
    val webImage: Boolean? = null,
    @SerialName("ExtraDetails")
    val extraDetails: String? = null, // This is a JSON string itself, might need further parsing
    @SerialName("eSIM")
    val esim: Boolean? = null,
    // Fields specific to "NumbersBanner"
    @SerialName("Number")
    val number: String? = null,
    // Fields specific to "PredefinedDenominationCard"
    @SerialName("PredefinedAmount")
    val predefinedAmount: Int? = null
)
