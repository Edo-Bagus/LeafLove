package com.example.leaflove.utils

import com.google.firebase.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

fun calculatePlantAgeInDays(createdTimestamp: Timestamp): String {
    // Convert Timestamp to Instant
    val createdInstant = createdTimestamp.toDate().toInstant()

    // Get the current time
    val nowInstant = Instant.now()

    // Calculate the total days between the created time and now
    val totalDays = ChronoUnit.DAYS.between(createdInstant, nowInstant)

    // Return the age as a formatted string
    return "$totalDays days"
}

fun cleanString(input: String?): String {
    return input?.let {
        val cleaned = it.replace(Regex("\\\\u([0-9A-Fa-f]{4})")) { matchResult ->
            val codePoint = matchResult.groupValues[1].toInt(16) // Convert hex to decimal
            codePoint.toChar().toString() // Convert code point to character
        }
            .replace(Regex("[^\\x20-\\x7E]+"), "") // Remove non-printable characters (optional)
            .replace(Regex("\\[|\\]"), "") // Removes square brackets
            .replace(Regex("\\s+"), " ") // Replaces multiple spaces with a single space
            .trim() // Removes leading and trailing spaces

        // Capitalize the first letter if the string is not empty
        if (cleaned.isNotEmpty()) {
            cleaned.replaceFirstChar { it.uppercase() }
        } else {
            cleaned
        }
    } ?: ""
}



fun parseStringList(input: String): List<String> {
    return try {
        // Check if input is an empty list representation "[ ]"
        if (input == "[]") return emptyList()

        // Regex to extract the content inside the brackets and split the items by commas
        Regex("""\[(.*)]""").find(input)
            ?.groupValues?.get(1) // Extract content between the brackets
            ?.split(",") // Split by comma
            ?.map { it.trim().removeSurrounding("\"") } // Clean each item (trim spaces, remove quotes)
            ?: emptyList() // Return empty list if parsing fails
    } catch (e: Exception) {
        emptyList() // Return empty list if an error occurs during parsing
    }
}

fun formatDate(timestamp: Timestamp): String {
    val instant = timestamp.toDate().toInstant()
    val date = java.util.Date.from(instant)
    val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
    return dateFormat.format(date)
}


