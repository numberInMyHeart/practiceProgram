package UESTC.multithread.others;

/**
 * 测试i++的字节码
 * 在autoincrementClass中的字节码指令文件中可以看到
 * i++是通过指令iinc完成的，局部变量自增指令
 * 1.如果想要解释为什么i++不是线程安全的
 *      从java内存模型来解释，i++指令并不是原子的，而多个线程对i操作并没有
 *      进行同步（工作内存和主内存之间的交互）
 *      所以不会是线程安全的
 */
public class TestIncrementClassCode {
    public static void main(String[] args) {
        int i=0;
        i++;
        System.out.println(i);
    }

}
