fun main(args: Array<String>) {
    val threadNum = if (args.isEmpty()) {
        2
    } else {
        args[0].toInt()
    }

    val sync = Sync(threadNum)

    val threads = (0..threadNum - 1).map {
        CalcThread(it, threadNum, sync)
    }
    println("Create $threadNum threads")
    threads.forEach { it.start() }

    Runtime.getRuntime().addShutdownHook(
        Thread {
            sync.finishThread()
            threads.forEach { it.join() }
            var sum = 0.0
            threads.forEach {
                sum += it.sum
            }
            println("Your sum $sum")
        }
    )
}