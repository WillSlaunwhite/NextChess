package com.nextchess.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(stringList: List<String>): String {
//        val pairedMoves = mutableListOf<String>()
//        for (i in stringList.indices step 2) {
//            pairedMoves.add("${stringList[i]} ${stringList.getOrNull(i + 1) ?: ""}")
//        }
        return stringList.joinToString(",")
    }

    override fun convertToEntityAttribute(stringData: String): List<String> {
        return stringData.split(",")
    }
}
