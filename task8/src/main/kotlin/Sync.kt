import java.util.concurrent.CyclicBarrier

class Sync(
    numThreads: Int
) {
    @Volatile
    private var maxPoint: Long = 0

    @Volatile
    var flagForContinue: Boolean = true
        private set

    private var waitOther: Boolean = true
    private var cyclicBarrier = CyclicBarrier(numThreads)
    fun waitThreads(iterationValue: Long): Long{
        registerMaxPoint(iterationValue)
        if(waitOther){
            cyclicBarrier.await()
            waitOther = false
        }
        return maxPoint
    }

    private var maxPointLock = Object()
    fun registerMaxPoint(newMaxPoint: Long){
        synchronized(maxPointLock){
            if(maxPoint < newMaxPoint){
                maxPoint = newMaxPoint
            }
        }
    }

    fun finishThread(){
        waitOther = true
        flagForContinue = false
    }
}