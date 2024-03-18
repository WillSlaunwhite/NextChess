package com.nextchess.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class DateConverter : AttributeConverter<String, String> {
    override fun convertToDatabaseColumn(dateStr: String?): String {
        // Replace any '?' characters with '01'
        return dateStr?.replace("?", "01") ?: "2023-01-01"
    }

    override fun convertToEntityAttribute(dbData: String?): String? {
        return dbData
    }
}
