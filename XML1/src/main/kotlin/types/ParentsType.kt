package types

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parents-type", propOrder = ["parentId"])
class ParentsType {
    @XmlElement(name = "parent-id")
    var parentId: MutableList<IdType>? = ArrayList()
}