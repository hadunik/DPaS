import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

val forks = ReentrantLock()
val conditionVariable: Condition = forks.newCondition()

fun main() {
    val numOfPhilosophers = 5
    val forks = (0 until numOfPhilosophers).map {
        ReentrantLock()
    }.toList()
    val philosophers = (0 until numOfPhilosophers).map {
        Philisopher(
            forks[it],
            forks[(it + 1) % numOfPhilosophers],
            it
        )
    }
    philosophers.forEach(Thread::start)
}

class Philisopher(private val firstFork: ReentrantLock, private val secondFork: ReentrantLock, private val id: Int) :
    Thread() {
    override fun run() {
        while (true) {
            println("$id started getting of forks")
            forks.lock()
            try {
                while (firstFork.isLocked || secondFork.isLocked) {
                    conditionVariable.await()
                }
                firstFork.lock()
                secondFork.lock()
            } catch (e: InterruptedException) {
                println("something happened to philosopher $id");
            } finally {
                forks.unlock()
            }

            println("$id philosopher was able to take forks")
            sleep(kotlin.random.Random.nextInt(5_000).toLong())

            forks.lock()
            try {
                firstFork.unlock()
                secondFork.unlock()
                conditionVariable.signalAll()
            } catch (e: InterruptedException) {
                println("something happened to philosopher $id");
            } finally {
                forks.unlock()
            }

            println("philosopher $id fell asleep")
            sleep(kotlin.random.Random.nextInt(5_000).toLong())
        }
    }
}

