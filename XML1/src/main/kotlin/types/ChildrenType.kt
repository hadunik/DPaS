package types

import jakarta.xml.bind.annotation.*
import java.math.BigInteger

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "children-type", propOrder = ["childId"])
class ChildrenType {
    @XmlElement(name = "child-id")
    var childId: MutableList<IdType>? = ArrayList()

    @XmlAttribute(name = "count", required = true)
    var count: BigInteger? = null
}