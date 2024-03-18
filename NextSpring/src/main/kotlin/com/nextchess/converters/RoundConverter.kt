package com.nextchess.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RoundConverter : AttributeConverter<String?, String> {

    override fun convertToDatabaseColumn(round: String?): String {
        return round?.toString() ?: "?"
    }

    override fun convertToEntityAttribute(dbData: String): String? {
        return dbData.toString()
    }
}