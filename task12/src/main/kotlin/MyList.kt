import java.util.concurrent.locks.ReentrantLock

class MyList<T : Comparable<T>> {
    private data class Node<T>(var value: T, val next: Node<T>?)
//    private val mutex = ReentrantLock() another way of implementation

    @Volatile
    private var head: Node<T>? = null

    fun add(forAdd: T) {
        head = Node(forAdd, head)
    }

    fun print() {
        synchronized(this) {
            var it = head
            while (it != null) {
                println(it.value)
                it = it.next
            }
        }
    }

    fun sort() {
        synchronized(this) {
            var i = head
            while (i != null) {
                var j = i.next
                while (j != null) {
                    if (i.value > j.value) {
                        val tmp = i.value
                        i.value = j.value
                        j.value = tmp
                    }
                    j = j.next
                }
                i = i.next
            }
        }
    }
}