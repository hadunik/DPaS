class Company(departmentsCount: Int) {
    private val departments: MutableList<Department>

    init {
        departments = ArrayList(departmentsCount)
        for (i in 0 until departmentsCount) {
            departments.add(i, Department(i))
        }
    }

    /**
     * Вывод результата по всем отделам.
     * P.S. Актуально после того, как все отделы выполнят свою работу.
     */
    fun showCollaborativeResult() {
        println("All departments have completed their work.")
        val result = departments
            .map(Department::calculationResult)
            .reduce { a, b -> a + b }
        println("The sum of all calculations is: $result")
    }

    /**
     * @return Количество доступных отделов для симуляции выполнения
     * работы.
     */
    fun getDepartmentsCount(): Int {
        return departments.size
    }

    /**
     * @param index Индекс для текущего свободного отдела.
     * @return Свободный отдел для симуляции выполнения работы.
     */
    fun getFreeDepartment(index: Int): Department {
        return departments[index]
    }
}