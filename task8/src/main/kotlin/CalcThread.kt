class CalcThread(
    val idCurrentThread: Int,
    val threadsNum: Int,
    val sync: Sync
) : Thread() {
    var sum = 0.0
        private set

    override fun run() {
        var start = idCurrentThread.toLong()
        var counter: Long = 0
        var limit = Long.MAX_VALUE
        var syncFlag = true

        while (counter < limit || syncFlag) {
            sum += calculate(start)
            start += threadsNum
            counter++
            if(!sync.flagForContinue && syncFlag){
                syncFlag = false
                limit = sync.waitThreads(counter)
            }
        }
        println("$idCurrentThread stopped with $counter iteration number")
    }

    private fun calculate(
        k: Long
    ) = if (k % 2 == 0L) {
        1
    } else {
        -1
    } / (1 + 2 * k).toDouble()

}