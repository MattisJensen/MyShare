package dk.sdu.myshare.business.utility

object ProfileFormatter {
    /**
     * If the input name consists of only one word, the first two letters of the word are returned.
     *
     * @param name The full name to extract the letters from.
     * @return The first letter of a names first and last name.
     */
    fun getNameLetters(name: String): String {
        val words = name.split(" ")
        return if (words.size == 1) {
            words[0].take(2).uppercase()
        } else {
            "${words[0][0]}${words[words.size - 1][0]}".uppercase()
        }
    }
}