public class Main{
    public static void main(String[] args){
        ChildThread thr1 = new ChildThread();
        thr1.start();
        for(int i = 1; i <= 10; i++){
            System.out.printf("From main thread %d\n", i);
        }
    }
}
