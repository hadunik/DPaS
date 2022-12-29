package types

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["person"])
@XmlRootElement(name = "people")
class People {
    var person: MutableList<PersonType>? = ArrayList()
}