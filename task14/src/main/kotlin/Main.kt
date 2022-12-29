import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReentrantLock

fun main() {
    val a = SemaphoreWithLimit(5)
    val b = SemaphoreWithLimit(3)
    val c = SemaphoreWithLimit(2)
    val modules = SemaphoreWithLimit(2)

    // making a details
    var aCount = 0
    Thread {
        while(true) {
            println("started making A")
            Thread.sleep(1000)
            println("${++aCount} A has been made")
            a.release()
        }
    }.start()

    // making b details
    var bCount = 0
    Thread {
        while(true) {
            println("started making B")
            Thread.sleep(2000)
            println("${++bCount} B has been made")
            b.release()
        }
    }.start()

    // making c details
    var cCount = 0
    Thread {
        while(true) {
            println("started making C")
            Thread.sleep(3000)
            println("${++cCount} C has been made")
            c.release()
        }
    }.start()

    // making modules
    var moduleCount = 0
    Thread {
        while(true) {
            a.acquire()
            b.acquire()
            println("${++moduleCount} module has been made")
            modules.release()
        }
    }.start()

    var widgetCount = 0

    Thread {
        while(true) {
            c.acquire()
            modules.acquire()
            println("${++widgetCount} widget has been produced")
        }
    }.start()
}


class SemaphoreWithLimit(
    private val Limit: Int
) {
    private val semaphore = Semaphore(0)
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    fun release() {
        lock.lock()
        try {
            while (semaphore.availablePermits() >= Limit) {
                println("One semaphore was locked")
                condition.await()
            }
            semaphore.release()
        } finally {
            lock.unlock()
        }
    }

    fun acquire() {
        semaphore.acquire()
        lock.lock()
        try {
            condition.signalAll()
        } finally {
            lock.unlock()
        }
    }
}