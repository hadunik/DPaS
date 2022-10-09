fun main() {
    val mainObj = Object()
    val childObj = Object()
    var flag = 1
    val ct = Thread{
        for (i in 0..9) {
            synchronized(childObj){
                while (flag == 1){
                    childObj.wait(1000)
                }
            }
            println("$i from child")
            flag = 1
            synchronized(mainObj){
                mainObj.notify()
            }
        }
    }
    ct.start()
    for(i in 0..9){
        println("$i from main")
        flag = 0
        synchronized(childObj){
            childObj.notify()
        }
        synchronized(mainObj){
            while(flag == 0){
                mainObj.wait(1000)
            }
        }
    }
}