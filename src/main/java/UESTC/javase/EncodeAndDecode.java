package UESTC.javase;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName EncodeAndDecode
 * @Author YQQ
 * @Date 2019/7/7 10:07
 * @Version
 **/
public class EncodeAndDecode {
    /**
     * 测试STRING将某种编码的值转化为另外编码的值并输出，trim函数表示去除首尾空格
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = new String("abc".getBytes("ISO-8859-1"),"GBK");
        System.out.println(str.trim());

    }
}
