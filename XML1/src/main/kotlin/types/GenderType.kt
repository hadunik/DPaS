package types

import jakarta.xml.bind.annotation.XmlEnum
import jakarta.xml.bind.annotation.XmlEnumValue
import jakarta.xml.bind.annotation.XmlType

@XmlType(name = "gender-type")
@XmlEnum
enum class GenderType(private val value: String) {
    M("M"), F("F"), @XmlEnumValue("other")
    OTHER("other");

    fun value(): String {
        return value
    }

    companion object {
        fun fromValue(v: String): GenderType {
            for (c in values()) {
                if (c.value == v) {
                    return c
                }
            }
            throw IllegalArgumentException(v)
        }
    }
}