package types

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person-type", propOrder = ["spouse", "parents", "siblings", "children"])
class PersonType {
    var spouse: IdType? = null
    var parents: ParentsType? = null
    var siblings: SiblingsType? = null
    var children: ChildrenType? = null

    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(
        CollapsedStringAdapter::class
    )
    @XmlID
    @XmlSchemaType(name = "ID")
    var id: String? = null

    @XmlAttribute(name = "name", required = true)
    var name: String? = null

    @XmlAttribute(name = "gender", required = true)
    var gender: GenderType? = null
}