import java.util.concurrent.CyclicBarrier

class Sync(
    numThreads: Int
) {
    @Volatile
    private var maxPoint: Long = 0

    @Volatile
    var flagForContinue: Boolean = true
        private set

    private var cyclicBarrier = CyclicBarrier(numThreads)
    fun waitThreads(iterationValue: Long): Long {
        registerMaxPoint(iterationValue)
        cyclicBarrier.await()
        flagForContinue = true
        return maxPoint
    }

    private var maxPointLock = Object()
    fun registerMaxPoint(newMaxPoint: Long) {
        synchronized(maxPointLock) {
            if (maxPoint < newMaxPoint) {
                maxPoint = newMaxPoint
            }
        }
    }

    fun finishThread() {
        flagForContinue = false
    }
}