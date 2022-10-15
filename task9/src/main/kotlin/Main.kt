import java.util.concurrent.locks.ReentrantLock

fun main(args: Array<String>) {
    var forks = (0 until 5).map {
        ReentrantLock()
    }.toList()
    var philosophers = listOf(
        Philosopher(forks[0], forks[1], 0),
        Philosopher(forks[1], forks[2], 1),
        Philosopher(forks[2], forks[3], 2),
        Philosopher(forks[3], forks[4], 3),
        Philosopher(forks[0], forks[4], 4)
    )
    philosophers.forEach(Thread::start)
}