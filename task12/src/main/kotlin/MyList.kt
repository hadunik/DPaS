import java.util.concurrent.locks.ReentrantLock

class MyList<T : Comparable<T>> {
    private data class Node<T>(var value: T, val next: Node<T>?, var mutex: ReentrantLock)

    @Volatile
    private var head: Node<T>? = null

    fun add(forAdd: T) {
        head = Node(forAdd, head, ReentrantLock())
    }

    fun print() {
        var it = head;
        while (it != null) {
            it.mutex.lock()
            try {
                println(it.value)
            } finally {
                it.mutex.unlock()
                it = it.next
            }
        }
    }

    fun sort() {
        var i = head
        while (i != null) {
            i.mutex.lock()
            try {
                var j = i.next
                while (j != null) {
                    j.mutex.lock()
                    try {
                        if (i.value > j.value) {
                            val tmp = i.value
                            i.value = j.value
                            j.value = tmp
                        }
                    } finally {
                        j.mutex.unlock()
                        j = j.next
                    }
                }
            } finally {
                i.mutex.unlock()
                i = i.next
            }
        }

    }
}