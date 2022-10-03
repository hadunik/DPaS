public class ChildThread extends Thread{
    public void run(){
        for(int i = 1; i <= 10; i++){
            System.out.printf("From second thread %d\n", i);
        }
    }
}
