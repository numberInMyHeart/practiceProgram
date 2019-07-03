package UESTC.multithread.threadpool;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName FutureAndCallable
 * @Author YQQ
 * @Date 2019/7/3 9:43
 * @Version
 * 1.该类测试的是Future和Callable
 * 2.Future不仅是一个泛型类，还是一种模式，表异步执行
 * 3.Callable在线程池中执行，有返回结果，并且返回结果一般被Future给包装起来
 * 4.future接口的get方法是阻塞等待返回结果
 **/
public class FutureAndCallable {
    //Callable实现类
    private  static class MyCallable implements Callable<Integer>{
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

    public static void main(String[] args) {
        //主线程开始
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        //线程池
        ExecutorService excecutor = Executors.newFixedThreadPool(10);

        //提交任务
        Future<Integer>[] futures = new Future[10];
        for(int i=0;i<10;i++){
            MyCallable task = new MyCallable("task"+i);
            futures[i] = excecutor.submit(task);
        }
        excecutor.shutdown();

        try {
            Thread.sleep(1000);
            for(int i =0 ;i<10;i++){
                System.out.print("task执行完"+"结果：");
                try {
                   System.out.println(futures[i].get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //主线程结束
        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        System.out.println("用时："+(endTime-startTime));


        /**
         * 执行结果：
         * 1562119909264
         * tasktask1开始计算
         * tasktask2开始计算
         * tasktask0开始计算
         * tasktask4开始计算
         * tasktask8开始计算
         * tasktask3开始计算
         * tasktask7开始计算
         * tasktask5开始计算
         * tasktask9开始计算
         * tasktask6开始计算
         * task执行完结果：36856
         * task执行完结果：28
         * task执行完结果：24753
         * task执行完结果：4465
         * task执行完结果：34453
         * task执行完结果：276
         * task执行完结果：2850
         * task执行完结果：36046
         * task执行完结果：7260
         * task执行完结果：38226
         * 1562119910278
         * 用时：1014
         */
    }




}
