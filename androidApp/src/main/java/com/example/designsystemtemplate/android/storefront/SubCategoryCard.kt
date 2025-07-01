package com.example.designsystemtemplate.android.storefront

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystemtemplate.data.SubCategory

@Composable
fun SubCategoryCard(subCategory: SubCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = subCategory.name ?: "Unnamed Subcategory",
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
            subCategory.description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
            subCategory.cards?.takeIf { it.isNotEmpty() }?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cards: ${it.size}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}