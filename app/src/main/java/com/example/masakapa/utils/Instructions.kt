package com.example.masakapa.utils

object Instructions {
    fun getFormattedInstructions(instructions: String): String {
        // Pisahkan instruksi berdasarkan karakter baris baru
        val instructionLines = instructions.split("\r\n")

        // Tambahkan tanda bullet di awal setiap baris
        return instructionLines.joinToString("\n\n") { "• $it" }
    }
}
