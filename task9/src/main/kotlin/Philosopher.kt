import java.util.concurrent.locks.ReentrantLock

class Philosopher(var firstFork: ReentrantLock, var secondFork: ReentrantLock, val id : Int) : Thread() {
    override fun run() {
        while (true) {
            firstFork.lock()
            println("philosopher $id take a first fork")
            try {
                secondFork.lock()
                println("philosopher $id take a second fork")
                try {
                    sleep(kotlin.random.Random.nextInt(2_000).toLong())
                    println("philosopher $id eating")
                } finally {
                    secondFork.unlock()
                    println("philosopher $id put down second fork")
                }
            } finally {
                firstFork.unlock()
                println("philosopher $id put down first fork")
            }
            sleep(kotlin.random.Random.nextInt(5_000).toLong())
            println("philosopher $id fell asleep")
        }
    }
}