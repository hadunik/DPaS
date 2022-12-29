import java.io.InputStream
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamException

class PeopleParser {
    @Throws(XMLStreamException::class)
    fun parse(stream: InputStream?): HashMap<String, PersonInfo?> {
        val data = ArrayList<PersonInfo?>()

        // XML reader init
        val streamFactory = XMLInputFactory.newInstance()
        val reader = streamFactory.createXMLStreamReader(stream)
        var peopleCount = 0
        var info: PersonInfo? = null
        println("=== Start parsing... ===")
        while (reader.hasNext()) {
            reader.next()
            var temp: Array<String>
            when (reader.eventType) {
                XMLStreamConstants.START_ELEMENT -> {
                    when (reader.localName) {
                        "people" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "count") {
                                    peopleCount = reader.getAttributeValue(i).toInt()
                                    println("Total people count: $peopleCount")
                                } else {
                                    println("Unknown attribute in <people>")
                                }
                                i++
                            }
                        }

                        "person" -> {
                            info = PersonInfo()
                            var i = 0
                            while (i < reader.attributeCount) {
                                when (reader.getAttributeLocalName(i)) {
                                    "name" -> {
                                        val full =
                                            reader.getAttributeValue(i).trim { it <= ' ' }.split("\\s+".toRegex())
                                                .dropLastWhile { it.isEmpty() }
                                                .toTypedArray()
                                        info.firstName = full[0]
                                        info.lastName = full[1]
                                    }

                                    "id" -> {
                                        info.id = reader.getAttributeValue(i).trim { it <= ' ' }
                                    }

                                    else -> println("Unknown attribute in <person>")
                                }
                                i++
                            }
                        }

                        "id" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.id = reader.getAttributeValue(i).trim { it <= ' ' }
                                } else {
                                    println("Unknown attribute in <id>")
                                }
                                i++
                            }
                        }

                        "firstname" -> if (reader.attributeCount > 0) {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.firstName = reader.getAttributeValue(i).trim { it <= ' ' }
                                } else {
                                    println("Unknown attribute in <firstname>")
                                }
                                i++
                            }
                        } else {
                            reader.next()
                            assert(info != null)
                            info!!.firstName = reader.text.trim { it <= ' ' }
                        }

                        "surname" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.lastName = reader.getAttributeValue(i).trim { it <= ' ' }
                                } else {
                                    println("Unknown attribute in <surname>")
                                }
                                i++
                            }
                        }

                        "fullname" -> {}
                        "first" -> {
                            reader.next()
                            assert(info != null)
                            info!!.firstName = reader.text.trim { it <= ' ' }
                        }

                        "family", "family-name" -> {
                            reader.next()
                            assert(info != null)
                            info!!.lastName = reader.text.trim { it <= ' ' }
                        }

                        "gender" -> if (reader.attributeCount > 0) {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.gender =
                                        reader.getAttributeValue(i).trim { it <= ' ' }.uppercase(Locale.getDefault())
                                            .substring(0, 1)
                                } else {
                                    println("Unknown attribute in <gender>")
                                }
                                i++
                            }
                        } else {
                            reader.next()
                            assert(info != null)
                            info!!.gender =
                                reader.text.trim { it <= ' ' }.uppercase(Locale.getDefault()).substring(0, 1)
                        }

                        "spouce" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    if (reader.getAttributeValue(i).trim { it <= ' ' } != "NONE") {
                                        assert(info != null)
                                        info!!.spouseName = reader.getAttributeValue(i)
                                    }
                                } else {
                                    println("Unknown attribute in <spouce>")
                                }
                                i++
                            }
                            if (reader.hasText()) {
                                if (reader.text.trim { it <= ' ' } != "NONE") {
                                    assert(info != null)
                                    info!!.spouseName = reader.text
                                }
                            }
                        }

                        "husband" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.husbandId = reader.getAttributeValue(i).trim { it <= ' ' }
                                } else {
                                    println("Unknown attribute in <husband>")
                                }
                                i++
                            }
                        }

                        "wife" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if ("value" == reader.getAttributeLocalName(i)) {
                                    assert(info != null)
                                    info!!.wifeId = reader.getAttributeValue(i).trim { it <= ' ' }
                                } else {
                                    println("Unknown attribute in <wife>")
                                }
                                i++
                            }
                        }

                        "siblings" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "val") {
                                    val siblings = Arrays.asList(*reader.getAttributeValue(i).trim { it <= ' ' }
                                        .split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                                    assert(info != null)
                                    info!!.siblingsId.addAll(siblings)
                                } else {
                                    println("Unknown attribute in <siblings>")
                                }
                                i++
                            }
                        }

                        "brother" -> {
                            reader.next()
                            temp = reader.text.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                            assert(info != null)
                            info!!.brothersName.add(temp[0] + " " + temp[1])
                        }

                        "sister" -> {
                            reader.next()
                            temp = reader.text.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                            assert(info != null)
                            info!!.sistersName.add(temp[0] + " " + temp[1])
                        }

                        "siblings-number" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.siblingsCount = reader.getAttributeValue(i).trim { it <= ' ' }.toInt()
                                } else {
                                    println(
                                        reader.localName + " has unknown attribute: " + reader.getAttributeLocalName(
                                            i
                                        )
                                    )
                                }
                                i++
                            }
                        }

                        "children" -> {}
                        "child" -> {
                            reader.next()
                            temp = reader.text.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                            assert(info != null)
                            info!!.childrenName.add(temp[0] + " " + temp[1])
                        }

                        "son" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "id") {
                                    assert(info != null)
                                    info!!.sonsId.add(reader.getAttributeValue(i).trim { it <= ' ' })
                                } else {
                                    println("Unknown attribute in <son>")
                                }
                                i++
                            }
                        }

                        "daughter" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "id") {
                                    assert(info != null)
                                    info!!.daughtersId.add(reader.getAttributeValue(i).trim { it <= ' ' })
                                } else {
                                    println("Unknown attribute in <daughter>")
                                }
                                i++
                            }
                        }

                        "parent" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    if (reader.getAttributeValue(i).trim { it <= ' ' } != "UNKNOWN") {
                                        assert(info != null)
                                        info!!.parentsId.add(reader.getAttributeValue(i).trim { it <= ' ' })
                                    }
                                } else {
                                    println("Unknown attribute in <parent>")
                                }
                                i++
                            }
                        }

                        "father" -> {
                            reader.next()
                            temp = reader.text.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                            assert(info != null)
                            info!!.fatherName = temp[0] + " " + temp[1]
                        }

                        "mother" -> {
                            reader.next()
                            temp = reader.text.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                            assert(info != null)
                            info!!.motherName = temp[0] + " " + temp[1]
                        }

                        "children-number" -> {
                            var i = 0
                            while (i < reader.attributeCount) {
                                if (reader.getAttributeLocalName(i) == "value") {
                                    assert(info != null)
                                    info!!.childrenCount = reader.getAttributeValue(i).trim { it <= ' ' }.toInt()
                                } else {
                                    println("Unknown attribute in <children-number>")
                                }
                                i++
                            }
                        }
                    }
                }

                XMLStreamConstants.END_ELEMENT -> {
                    if (reader.localName == "person") {
                        data.add(info)
                        info = null
                    }
                }

                else -> {}
            }
        }
        reader.close()
        return normalize(data, peopleCount)
    }

    private fun normalize(data: ArrayList<PersonInfo?>, peopleCount: Int): HashMap<String, PersonInfo?> {
        var data = data
        val id_records = HashMap<String, PersonInfo?>()
        var temp_records = ArrayList<PersonInfo?>()
        println("=== Normalizing ===")

        // fill up records with known id
        for (i in data) {
            if (i!!.id != null) {
                if (id_records.containsKey(i.id)) {
                    id_records[i.id]!!.merge(i)
                } else {
                    id_records[i.id!!] = i
                }
            } else {
                temp_records.add(i)
            }
        }
        assert(id_records.size == peopleCount)

        data = temp_records
        temp_records = ArrayList()

        // merge all people that hasn't tezka
        for (p in data) {
            val found = findInRecords(
                { x: PersonInfo? -> x!!.firstName == p!!.firstName && x.lastName == p.lastName },
                id_records.values
            )
            if (found.size == 1) {
                val foundPerson = found[0]
                foundPerson!!.merge(p)
                id_records[foundPerson.id!!] = foundPerson
            } else if (found.size > 1) {
                val foundPerson = found[0]
                temp_records.addAll(found)
                if (foundPerson!!.gender == null && p!!.gender != null) {
                    foundPerson.merge(p)
                }
            }
        }

        data = temp_records
        temp_records = ArrayList()

        //merge all people by siblings
        for (person in data) {
            val siblings = HashSet(person!!.siblingsId)
            val found = findInRecords(
                { x ->
                    val xsib = HashSet(x!!.siblingsId)
                    xsib.retainAll(siblings)
                    xsib.size > 0
                }, id_records.values
            )
            if (found.size == 1) {
                found[0]!!.merge(person)
            } else {
                temp_records.add(person)
            }
        }
        println("Normalized!!!")

//        println("Temp buffer size: " + temp_records.size)

        childrenAssertion(id_records)
        siblingsAssertion(id_records)
        genderAssertion(id_records)
        return id_records
    }

    private fun childrenAssertion(id_records: HashMap<String, PersonInfo?>) {
        println("=== Children assertion ===")
        for (key in id_records.keys) {
            val p = id_records[key]!!
            p.childrenId.addAll(p.sonsId)
            p.childrenId.addAll(p.daughtersId)
            for (s in p.daughtersName) {
                val f = findInRecords(
                    { x: PersonInfo? -> s == x!!.firstName + " " + x.lastName },
                    id_records.values
                )
                if (f.isNotEmpty()) {
                    val daughter = f[0]
                    if (daughter != null) {
                        p.childrenId.add(daughter.id!!)
                    }
                }
            }
            for (s in p.sonsName) {
                val f = findInRecords(
                    { x: PersonInfo? -> s == x!!.firstName + " " + x.lastName },
                    id_records.values
                )
                if (f.isNotEmpty()) {
                    val son = f[0]
                    if (son != null) {
                        p.childrenId.add(son.id!!)
                    }
                }
            }
            for (s in p.childrenName) {
                val f = findInRecords(
                    { x: PersonInfo? -> s == x!!.firstName + " " + x.lastName },
                    id_records.values
                )
                if (f.isNotEmpty()) {
                    val child = f[0]
                    if (child != null) {
                        p.childrenId.add(child.id!!)
                    }
                }
            }
            if (p.childrenCount != null) {
                if (p.childrenCount != p.childrenId.size) {
                    println(p.firstName + " " + p.lastName + " " + p.childrenId.size + " " + p.childrenCount)
                }
                try {
                    assert(p.childrenId.size == p.childrenCount)
                } catch (e: AssertionError) {
                    println("CHILDREN ASSERTION FAILED: in $p")
                }
            }
        }
        println("Children assertion finished!")
    }

    private fun siblingsAssertion(id_records: HashMap<String, PersonInfo?>) {
        println("=== Siblings assertion ===")
        for (key in id_records.keys) {
            val p = id_records[key]
            p!!.siblingsId.addAll(p.brothersId)
            p.siblingsId.addAll(p.sistersId)
            for (s in p.sistersName) {
                val f = findInRecords(
                    { x: PersonInfo? -> s == x!!.firstName + " " + x.lastName },
                    id_records.values
                )
                if (f.isNotEmpty()) {
                    val sister = f[0]
                    if (sister != null) {
                        p.siblingsId.add(sister.id!!)
                    }
                }
            }
            for (s in p.brothersName) {
                val f = findInRecords(
                    { x: PersonInfo? -> s == x!!.firstName + " " + x.lastName },
                    id_records.values
                )
                if (f.isNotEmpty()) {
                    val brother = f[0]
                    if (brother != null) {
                        p.siblingsId.add(brother.id!!)
                    }
                }
            }
            for (s in p.siblingsName) {
                val f = findInRecords(
                    { x: PersonInfo? -> s == x!!.firstName + " " + x.lastName },
                    id_records.values
                )
                if (f.isNotEmpty()) {
                    val sibling = f[0]
                    if (sibling != null) {
                        p.siblingsId.add(sibling.id!!)
                    }
                }
            }
        }
        println("Siblings assertion finished!")
    }

    private fun genderAssertion(id_records: HashMap<String, PersonInfo?>) {
        println("=== Gender assertion ===")
        for (p in id_records.values) {
            if (p!!.gender == null) {
                if (p.wifeId != null || p.wifeName != null) {
                    p.gender = "M"
                } else if (p.husbandId != null || p.husbandName != null) {
                    p.gender = "F"
                } else if (p.spouseId != null) {
                    val pp = id_records[p.spouseId]
                    if (pp!!.gender != null) {
                        if (pp.gender == "M") {
                            p.gender = "F"
                        }
                        if (pp.gender == "F") {
                            p.gender = "M"
                        }
                    } else if (pp.husbandName != null || pp.husbandId != null) {
                        p.gender = "M"
                    } else if (pp.wifeName != null || pp.wifeId != null) {
                        p.gender = "F"
                    }
                } else {
                    p.gender = "M" //for Tonya Loschiavo
                }
            }
        }
        for (p in id_records.values) {
            try {
                assert(p!!.gender != null && (p.gender == "M" || p.gender == "F"))
            } catch (e: AssertionError) {
                println("This person hasn't gender: $p")
            }
        }
        println("Gender assertion finished!")
    }

    private fun findInRecords(pred: Predicate<PersonInfo?>, coll: Collection<PersonInfo?>): List<PersonInfo?> {
        return coll.parallelStream().filter(pred).collect(Collectors.toList())
    }
}