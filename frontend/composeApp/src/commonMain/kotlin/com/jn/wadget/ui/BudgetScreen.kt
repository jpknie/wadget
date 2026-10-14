package com.jn.wadget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jn.wadget.state.BudgetScreenState
import com.jn.wadget.state.BudgetScreenViewModel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Alignment

@Composable
fun BudgetWell(
    tag: String,
    allocated: Double,
    spent: Double
) {
    val fraction = if(allocated > 0.0) (spent / allocated).toFloat().coerceIn(0f, 1f) else 0f
    val shape = RoundedCornerShape(8.dp)
    // Outer Box, with Column layout, Text for Tag, and meter for allocation
    Box(
        modifier = Modifier
        .fillMaxWidth(0.8f)
        .padding(5.dp)
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = RoundedCornerShape(8.dp)
        )) {
        Column {
            Text(
                tag,
                modifier = Modifier
                    .padding(5.dp)
            )
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape
                    )
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction)
                        .fillMaxHeight()
                        .background(Color.Green)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    state: BudgetScreenState,
    budgetViewModel: BudgetScreenViewModel
) {
    val allocations = state.budgetComputationResult?.allocations

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Your spending overview",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Here's how your spending compares to your allocated budget for each tag.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(24.dp))

            val allocations = budgetViewModel.getResult()?.allocations

            allocations?.forEach { allocation ->
                BudgetWell(
                    tag = allocation.tag,
                    allocated = allocation.amount,
                    spent = 10.0
                )
            }
        }
    }
}