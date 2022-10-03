fun main(args: Array<String>) {
    val iterationsNum = 50000
    val threadNum = if (args.isEmpty()) {
        2
    } else {
        args[0].toInt()
    }
    val threads = (0..threadNum - 1).map {
        CalcThread(iterationsNum, it, threadNum)
    }.toList()
    println("Create $threadNum threads")
    threads.forEach { it.start() }
    threads.forEach ( Thread :: join)
    var answer = 0.0
    threads.forEach { answer += it.sum }
    println("Calculate value = $answer")
}