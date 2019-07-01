package UESTC.multithread.producerconsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ProducerAndConsumer {
    //缓存队列
    public static LinkedList<Integer> list = new LinkedList<>();

    //上限
    public static final int FULL = 10;

    //下限
    public static final int EMPTY = 0;

    //生产者
    private static class Producer implements  Runnable{
        public void run(){
            synchronized (list){
                while(list.size()==FULL){
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(new Random().nextInt());
                System.out.println("生产者线程："+
                        Thread.currentThread().getName()+
                        "加入任务,"+"当前任务数："+
                        list.size());
                list.notifyAll();
            }
        }
    }

    //消费者
    private static class Consumer implements  Runnable{
        public void run(){
            synchronized (list){
                while(list.size()<=EMPTY){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.remove();
                System.out.println("消费者线程："+
                        Thread.currentThread().getName()+
                        "消费任务,"+"当前任务数："+
                        list.size());
                list.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0;i<10;i++){
            new Thread(new Producer(),"生产者"+i).start();
        }
        for (int i = 0;i<10;i++){
            new Thread(new Consumer(),"消费者"+i).start();
        }
        //运行结果
        /**
         * 生产者线程：生产者0加入任务,当前任务数：1
         * 生产者线程：生产者1加入任务,当前任务数：2
         * 生产者线程：生产者2加入任务,当前任务数：3
         * 生产者线程：生产者6加入任务,当前任务数：4
         * 消费者线程：消费者0消费任务,当前任务数：3
         * 消费者线程：消费者1消费任务,当前任务数：2
         * 生产者线程：生产者3加入任务,当前任务数：3
         * 生产者线程：生产者7加入任务,当前任务数：4
         * 生产者线程：生产者5加入任务,当前任务数：5
         * 生产者线程：生产者9加入任务,当前任务数：6
         * 生产者线程：生产者4加入任务,当前任务数：7
         * 生产者线程：生产者8加入任务,当前任务数：8
         * 消费者线程：消费者3消费任务,当前任务数：7
         * 消费者线程：消费者5消费任务,当前任务数：6
         * 消费者线程：消费者4消费任务,当前任务数：5
         * 消费者线程：消费者2消费任务,当前任务数：4
         * 消费者线程：消费者7消费任务,当前任务数：3
         * 消费者线程：消费者6消费任务,当前任务数：2
         * 消费者线程：消费者8消费任务,当前任务数：1
         * 消费者线程：消费者9消费任务,当前任务数：0
         */
    }
}
