package types

import jakarta.xml.bind.annotation.*
import java.math.BigInteger

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "siblings-type", propOrder = ["siblingId"])
class SiblingsType {
    @XmlElement(name = "sibling-id")
    var siblingId: MutableList<IdType>? = ArrayList()

    @XmlAttribute(name = "count", required = true)
    var count: BigInteger? = null
}