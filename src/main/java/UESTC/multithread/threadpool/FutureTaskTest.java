package UESTC.multithread.threadpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName FutureTaskTest
 * @Author YQQ
 * @Date 2019/7/3 10:47
 * @Version
 * 1.测试futuretask接口
 * 2.futuretask实现了runnable和future，即包含了线程本身和返回结果
 * 3.有两种使用方式，直接作为Runnable,或者作为future
 **/
public class FutureTaskTest {
    private  static class MyCallable implements Callable<Integer> {
        private  String name;
        public MyCallable(String name){
            this.name = name;
        }
        public Integer call() throws Exception{
            System.out.println("task"+this.name+"开始计算");
            int sum = new Random().nextInt(300);
            int result = 0;
            for(int i=0;i<sum;i++){
                result+=i;
            }
            return result;
        }
    }

    //作为runnable使用
    public static void doRunnable(){
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);

        //作为runnable，运行实际是用的callable
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyCallable("task1"));
        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyCallable("task2"));
        new Thread(futureTask1).start();
        new Thread(futureTask2).start();

        try {
            Thread.sleep(1000);
            System.out.println(futureTask1.get());
            System.out.println(futureTask2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        System.out.println(endTime-startTime);
    }

    public static void doThreadPoll(){
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        //线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        //作为future来用
        FutureTask<Integer> futureTask1 = (FutureTask<Integer>) executor.submit(new MyCallable("task1"));
        FutureTask<Integer> futureTask2 = (FutureTask<Integer>) executor.submit(new MyCallable("task2"));
        executor.shutdown();

        try {
            Thread.sleep(1000);
            try {
                System.out.println(futureTask1.get());
                System.out.println(futureTask2.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        System.out.println(endTime-startTime);
    }

    public static void main(String[] args) {
        //doRunnable();
        /**
         * 结果：
         * 1562122836726
         * tasktask1开始计算
         * tasktask2开始计算
         * 2145
         * 22791
         * 1562122837733
         * 1007
         */

        doThreadPoll();

    }
}
