package types

import jakarta.xml.bind.annotation.XmlRegistry

@XmlRegistry
class ObjectFactory {
    fun createPeople(): People {
        return People()
    }

    fun createPersonType(): PersonType {
        return PersonType()
    }

    fun createIdType(): IdType {
        return IdType()
    }

    fun createParentsType(): ParentsType {
        return ParentsType()
    }

    fun createChildrenType(): ChildrenType {
        return ChildrenType()
    }

    fun createSiblingsType(): SiblingsType {
        return SiblingsType()
    }
}