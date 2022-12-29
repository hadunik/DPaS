class PersonInfo {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var gender: String? = null

    // parents
    var parentsId: MutableSet<String> = HashSet()
    var motherId: String? = null
    var fatherId: String? = null
    var parentsName: MutableSet<String> = HashSet()
    var motherName: String? = null
    var fatherName: String? = null

    // children
    var childrenId: MutableSet<String> = HashSet()
    var sonsId: MutableSet<String> = HashSet()
    var daughtersId: MutableSet<String> = HashSet()
    var childrenName: MutableSet<String> = HashSet()
    var sonsName: MutableSet<String> = HashSet()
    var daughtersName: MutableSet<String> = HashSet()

    // siblings
    var siblingsId: MutableSet<String> = HashSet()
    var brothersId: MutableSet<String> = HashSet()
    var sistersId: MutableSet<String> = HashSet()
    var siblingsName: MutableSet<String> = HashSet()
    var brothersName: MutableSet<String> = HashSet()
    var sistersName: MutableSet<String> = HashSet()

    // spouse
    var spouseId: String? = null
    var husbandId: String? = null
    var wifeId: String? = null
    var spouseName: String? = null
    var husbandName: String? = null
    var wifeName: String? = null
    var childrenCount: Int? = null
    var siblingsCount: Int? = null
    fun merge(p: PersonInfo?) {
        if (p == null) {
            return
        }
        if (id == null) id = p.id
        if (firstName == null) firstName = p.firstName
        if (lastName == null) lastName = p.lastName
        if (gender == null) gender = p.gender
        parentsId.addAll(p.parentsId)
        if (motherId == null) motherId = p.motherId
        if (fatherId == null) fatherId = p.fatherId
        parentsName.addAll(p.parentsName)
        if (motherName == null) motherName = p.motherName
        if (fatherName == null) fatherName = p.fatherName
        childrenId.addAll(p.childrenId)
        sonsId.addAll(p.sonsId)
        daughtersId.addAll(p.daughtersId)
        childrenName.addAll(p.childrenName)
        sonsName.addAll(p.sonsName)
        daughtersName.addAll(p.daughtersName)
        siblingsId.addAll(p.siblingsId)
        brothersId.addAll(p.brothersId)
        sistersId.addAll(p.sistersId)
        siblingsName.addAll(p.siblingsName)
        brothersName.addAll(p.brothersName)
        sistersName.addAll(p.sistersName)
        if (spouseId == null) spouseId = p.spouseId
        if (husbandId == null) husbandId = p.husbandId
        if (wifeId == null) wifeId = p.wifeId
        if (spouseName == null) spouseName = p.spouseName
        if (husbandName == null) husbandName = p.husbandName
        if (wifeName == null) wifeName = p.wifeName
        if (childrenCount == null) childrenCount = p.childrenCount
        if (siblingsCount == null) siblingsCount = p.siblingsCount
    }

    val fullName: String
        get() = "$firstName $lastName"

    override fun toString(): String {
        return "ID: " + id + " " + firstName + " " + lastName + " " + gender +
                " | siblings: " + siblingsId.toString() + " " + sistersId.toString() + " " + brothersId +
                " | spouse: " + fatherId + " " + motherId + " " + parentsId +
                " | sn: " + siblingsCount + " cn: " + childrenCount
    }
}