import java.util.concurrent.Semaphore

fun main() {
    val mainSem = Semaphore(0)
    val childSem = Semaphore(0)
    val ct = Thread{
        for (i in 0..9) {
            childSem.acquire()
            println("$i from child")
            mainSem.release()
        }
    }
    ct.start()
    for(i in 0..9){
        println("$i from main")
        childSem.release()
        mainSem.acquire()
    }
}