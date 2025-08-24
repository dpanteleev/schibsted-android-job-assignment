package com.schibsted.nde.feature.details

import android.icu.text.BreakIterator
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import com.schibsted.nde.feature.common.MealImage
import com.schibsted.nde.model.MealResponse
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealDetailsScreen(meal: MealResponse, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = meal.strMeal
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackClick()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back button"
                            )
                        }
                    )
                }
            )
        },
        content = { contentPadding ->
            Surface(
                modifier = Modifier.padding(contentPadding),
            ) {
                MealsDetailsScreenContent(meal = meal)
            }
        }
    )
}

@Composable
fun MealsDetailsScreenContent(meal: MealResponse) {

    val instructions = prepareInstructionList(meal)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            MealImage(
                thumb = meal.strMealThumb,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp)
            )
        }

        item {
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            Text(
                text = "Cooking Instructions:",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        itemsIndexed(instructions) { index, instruction ->
            Text(
                text = "${index + 1}. $instruction",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

fun prepareInstructionList(meal: MealResponse): List<String> {
    val text = meal.strInstructions

    val locale: Locale = Locale.getDefault()

    val sentences = mutableListOf<String>()
    val iterator = BreakIterator.getSentenceInstance(locale)
    iterator.setText(text)

    var start = iterator.first()
    var end = iterator.next()

    while (end != BreakIterator.DONE) {
        val sentence = text.substring(start, end)
            .replace("\n", "")
            .replace("\r", "")
            .trim()

        if (sentence.isNotBlank()) {
            sentences.add(sentence)
        }
        start = end
        end = iterator.next()
    }
    return sentences
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    MaterialTheme {
        MealsDetailsScreenContent(
            meal = MealResponse(
                "test",
                "test",
                "test",
                "test",
                "test",
                "Test first line. Test second line.",
            )
        )
    }
}
