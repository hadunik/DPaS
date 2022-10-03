public class Main{
    public static void main(String[] args) throws InterruptedException {
        ChildThread thr1 = new ChildThread();
        thr1.start();
        thr1.join();
        for(int i = 1; i <= 10; i++){
            System.out.printf("From main thread %d\n", i);
        }
    }
}
