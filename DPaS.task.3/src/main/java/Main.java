public class Main {
    public static void main(String[] argc){
        for(int i = 0; i < 4; i++){
            new SecondThread(Integer.toString(i)).start();
        }
    }
}
