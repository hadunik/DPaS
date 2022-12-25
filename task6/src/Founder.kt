import java.util.concurrent.CyclicBarrier

class Founder(company: Company) {
    private val workers: MutableList<Runnable> = ArrayList(company.getDepartmentsCount())
    private val cyclicBarrier = CyclicBarrier(company.getDepartmentsCount()) {
        company.showCollaborativeResult()
    }

    init {
        for (i in 0 until company.getDepartmentsCount()) {
            val newWorker = Worker(cyclicBarrier, company.getFreeDepartment(i))
            workers.add(i, newWorker)
        }
    }

    fun start() {
        for (worker in workers) {
            Thread(worker).start()
        }
    }

    class Worker(private val cyclicBarrier: CyclicBarrier, private val department: Department) : Runnable {
        override fun run() {
            department.performCalculations()
            println("The department ${department.identifier + 1} has finished its work. it took him ${department.workingSeconds} seconds to do this.")
            try {
                cyclicBarrier.await()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Exception in run method")
            }
        }
    }
}

