import java.util.Timer
import kotlin.concurrent.timerTask

fun main() {
    val myList = MyList<String>()
    val timer = Timer()
    timer.schedule(timerTask {
        myList.sort()
    }, 5000, 5000)
    while (true) {
        val str = readln()
        if (str.isEmpty()) {
            myList.print()
        } else {
            myList.add(str)
        }
    }
}