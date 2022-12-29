package types

import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "id-type")
class IdType {
    @XmlAttribute(name = "id", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    var id: Any? = null
}