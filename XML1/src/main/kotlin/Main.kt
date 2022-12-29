import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.JAXBException
import jakarta.xml.bind.Marshaller
import org.xml.sax.SAXException
import types.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.math.BigInteger
import java.util.*
import javax.xml.XMLConstants
import javax.xml.stream.XMLStreamException
import javax.xml.validation.SchemaFactory

fun main(args: Array<String>) {
    var data = HashMap<String, PersonInfo?>()
    try {
        FileInputStream("./people.xml").use { stream -> data = PeopleParser().parse(stream) }
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: XMLStreamException) {
        e.printStackTrace()
    }
    val people = People()
    val table = HashMap<String?, PersonType>()
    for (p in data.values) {
        val pp = PersonType()

        // set main info
        pp.id = p!!.id
        pp.name = p.fullName
        pp.gender = GenderType.fromValue(p.gender!!)
        table[p.id] = pp
    }
    for (p in table.values) {
        val person = data[p.id]

        // set spouse
        if (person!!.spouseId != null) {
            val id = IdType()
            id.id = table[person.spouseId]
            p.spouse = id
        }
        if (person.wifeId != null) {
            val id = IdType()
            id.id = table[person.wifeId]
            p.spouse = id
        }
        if (person.husbandId != null) {
            val id = IdType()
            id.id = table[person.husbandId]
            p.spouse = id
        }

        // set children
        val childrenType = ChildrenType()
        childrenType.count = BigInteger.valueOf(Objects.requireNonNullElse(person.childrenCount, -1)!!.toLong())
        for (i in person.childrenId) {
            val id = IdType()
            id.id = table[i]
            childrenType.childId?.add(id)
        }
        p.children = childrenType


        // set siblings
        val siblingsType = SiblingsType()
        siblingsType.count = BigInteger.valueOf(Objects.requireNonNullElse(person.siblingsCount, -1)!!.toLong())
        for (i in person.siblingsId) {
            val id = IdType()
            id.id = table[i]
            siblingsType.siblingId?.add(id)
        }
        p.siblings = siblingsType

        // set parents
        val parentsType = ParentsType()
        for (i in person.parentsId) {
            val id = IdType()
            id.id = table[i]
            parentsType.parentId?.add(id)
        }
        p.parents = parentsType
    }
    people.person?.addAll(table.values)

    try {
        var jc: JAXBContext? = null
        println(People::class.java)
        val classLoader = People::class.java.classLoader
        jc = JAXBContext.newInstance("types", classLoader)
        val writer = jc.createMarshaller()
        val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        val schemaFile = File("./schema.xml")
        writer.schema = schemaFactory.newSchema(schemaFile)
        writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        writer.marshal(people, File("./output.xml"))
    } catch (e: JAXBException) {
        e.printStackTrace()
    } catch (e: SAXException) {
        e.printStackTrace()
    }
}
