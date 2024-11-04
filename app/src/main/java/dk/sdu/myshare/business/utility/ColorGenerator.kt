package dk.sdu.myshare.business.utility

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.random.Random

object ColorGenerator {
    /**
     * Generates a random pastel color.
     *
     * The function generates random RGB values, calculates the luminance of the color,
     * to adjusts the RGB values if the contrast to white is not good enough.
     *
     * @return A Color object representing the generated pastel color.
     */
    fun getRandomPastelColor(): Color {
        val random = Random.Default
        var red: Int
        var green: Int
        var blue: Int
        var luminance: Double

        do {
            // Generate random RGB values for a pastel color
            red = (random.nextInt(256) + 255) / 2
            green = (random.nextInt(256) + 255) / 2
            blue = (random.nextInt(256) + 255) / 2

            // Calculate the luminance of the color
            luminance = 0.2126 * (red / 255.0).pow(2.2) +
                    0.7152 * (green / 255.0).pow(2.2) +
                    0.0722 * (blue / 255.0).pow(2.2)
        } while (luminance > 0.5) // Repeat if luminance not jas enough contrast with white

        return Color(red, green, blue)
    }
}