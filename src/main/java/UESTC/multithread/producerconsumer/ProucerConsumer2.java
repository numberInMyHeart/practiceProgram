package UESTC.multithread.producerconsumer;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProucerConsumer2 {
    //缓存队列
    public static final LinkedList<Integer> LIST = new LinkedList<>();

    //上限
    private static final int FULL=10;

    //下限
    private static final int EMPTY=0;

    //锁
    private static ReentrantLock lock = new ReentrantLock();

    //判空
    private static Condition isEmpty = lock.newCondition();
    //判满
    private static Condition isFull = lock.newCondition();

    //生产者
    private static class Producer implements  Runnable{
        public void run(){
            lock.lock();
            try {
                while(LIST.size()==FULL){
                    //加睡眠是为了加强效果
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        isFull.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //添加任务
                LIST.add(new Random().nextInt());
                System.out.println("生产者线程："+
                        Thread.currentThread().getName()+
                        "生产任务,"+"当前任务数："+
                        LIST.size());
                //唤醒消费者
                isEmpty.signalAll();
            }finally {
                lock.unlock();
            }
        }
    }

    //消费者
    private static class Consumer implements  Runnable{
        public void run(){
            lock.lock();
            try {
                while(LIST.size()<=EMPTY){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        isEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //消费
                LIST.remove();
                System.out.println("消费者线程："+
                        Thread.currentThread().getName()+
                        "消费任务,"+"当前任务数："+
                        LIST.size());
                //唤醒
                isFull.signalAll();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            new Thread(new Producer(),"生产者"+i).start();
        }
        for(int i=0;i<10;i++){
            new Thread(new Consumer(),"消费者"+i).start();
        }
        //运行结果
        /**
         * 生产者线程：生产者0生产任务,当前任务数：1
         * 生产者线程：生产者4生产任务,当前任务数：2
         * 生产者线程：生产者3生产任务,当前任务数：3
         * 生产者线程：生产者7生产任务,当前任务数：4
         * 生产者线程：生产者8生产任务,当前任务数：5
         * 消费者线程：消费者2消费任务,当前任务数：4
         * 消费者线程：消费者5消费任务,当前任务数：3
         * 消费者线程：消费者8消费任务,当前任务数：2
         * 生产者线程：生产者2生产任务,当前任务数：3
         * 生产者线程：生产者1生产任务,当前任务数：4
         * 消费者线程：消费者9消费任务,当前任务数：3
         * 生产者线程：生产者6生产任务,当前任务数：4
         * 生产者线程：生产者5生产任务,当前任务数：5
         * 消费者线程：消费者0消费任务,当前任务数：4
         * 生产者线程：生产者9生产任务,当前任务数：5
         * 消费者线程：消费者1消费任务,当前任务数：4
         * 消费者线程：消费者3消费任务,当前任务数：3
         * 消费者线程：消费者4消费任务,当前任务数：2
         * 消费者线程：消费者6消费任务,当前任务数：1
         * 消费者线程：消费者7消费任务,当前任务数：0
         */
    }
}
