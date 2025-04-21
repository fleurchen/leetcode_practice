// 3个线程循环打印100个数
public class CyclicPrinting {
    private static final int MAX_NUMBER=100;
    private static int current = 1;
    private static final Object lock = new Object();

    public static void main(String[] args){
        Thread t1 = new Thread(new Printer(0),"t1");
        Thread t2 = new Thread(new Printer(1),"t3");

        Thread t3 = new Thread(new Printer(2),"t3");
        t1.start();
        t2.start();
        t3.start();
    }

    static class Printer implements Runnable{
        private final int reminder;
        public Printer(int reminder){
            this.reminder = reminder;
        }
        @Override
        public void run(){
            while(true){
                synchronized(lock){
                    if(current > MAX_NUMBER){
                        break;
                    }
                    while(current%3 != reminder){
                        try {
                            lock.wait();
                        }catch(Exception e){
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println(current);
                    current++;
                    lock.notifyAll();
                }
            }
        }
    }
}
