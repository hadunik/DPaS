class CalcThread(
    val iterationsNum: Int,
    val idCurrentThread: Int,
    val threadsNum: Int
) : Thread() {
    var sum = 0.0
        private set

    override fun run() {
        for (i in 0..iterationsNum) {
            sum += calculate(idCurrentThread + i * threadsNum.toLong())
        }
    }

    private fun calculate(
        k: Long
    ) = if (k % 2 == 0L) {
        1
    } else {
        -1
    } / (1 + 2 * k).toDouble()

}