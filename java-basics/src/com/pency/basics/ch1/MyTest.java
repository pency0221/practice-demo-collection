package com.pency.basics.ch1;

import org.junit.Test;

import java.sql.SQLOutput;

/**
 * @Author Pency
 * @Description 数据类型 隐式类型转换、显式类型转换  及注意点
 *     总结：
 *        1、因为int和double是整型和浮点型的默认类型  所以定义long 和 float要加L(尽量大写 防止视觉和1混淆)和f后缀显式说明    double尽量也加d（可读性）
 *        2、对JVM看来，short,byte,int都是同一样的东西  所以虽然不是默认类型定义byte/short/char时也不用加后缀 根据int字面值得大小能存下就直接（转成需要的类型）存了  存不下当场报错
 *        3、当byte、short、char三者在计算后的结果都转换为了int类型（包含同类型之间的计算 例如byte+byte 最终结果也是int）
 *        4、==比较引用（有的话）  equals比较值   前提是自定义的类equals需要重写（object中定义 equals返回==的值）才能实现比较值  jdk部分类已经重写 比如String
 **/
public class MyTest {
    /**
     * TODO 类型转换  后缀问题  计算转换问题
     */
    @Test
    public void test1(){
        //TODO pency:Java中为什么给float类型变量赋值需要加F,而给byte、short赋值的时候却不需要呢？
        // “简单记就是这个int字面量（不能是int变量）能转成比int范围小数据类型的就隐式转了 不能转就报红 所以定义这些byte/short/char不用加后缀”
        /*JVM规范中所说，并没有说float，int,byte等占多少个字节，而是真正的有效位是多少。比如byte的有效位是1个字节，也就是-128到127。
        使用Java编程的时候，就只能用byte表示-128到127之间的数，而真正JVM实现，一般byte还是占用和int一样大小:4个字节。
        也就说在JVM看来，short,byte,int都是同一样的东西
        这也就解释了为什么byte,short使用int字面量赋值的时候会不用加
        (todo) 也就是说定义byte/short/char时就不用加后缀，因为此int型数值尚在数值类型范围内,jvm会自定进行一次隐式类型转换，将此int型数值转换成类型k,
          如果此int型数值超过了k的类型范围 就直接报错了 （和定义过大的int值直接报错一个道理）
        */
        System.out.println("========整型和char========");
        byte a=127;  //int字面量->byte 127 byte的最大值   byte a=128;直接报错 因为这个int128 隐式转不了byte
        short b=127; //int字面量->short
        int c=127;
        long d=127L;//或者12 会隐式转换 int->long
        char c1=127; //int字面量->char
        //byte b1=c; //TODO 报错  对于int变量就不能隐形向下转换了
        int i1=a;    //向上（类型大的）自动隐形转换
        //char c2=a; //报错 char和byte/short 不能自动互转 和int可以
        System.out.println(a==b);//true
        System.out.println(b==c);//true
        System.out.println(c==d);//true
        System.out.println(c==c1);//true
        System.out.println(c1);//true
        char c2=97;
        System.out.println(c2);//a

        System.out.println("========浮点型========");
        float e=127;  //int->float
        System.out.println(c==e);//true
        double f=127; //int->double
        System.out.println(e==f);//true
        //float g=127.0; // 语法检查不通过 报红 需要加F
        float g=127.000000f;
        double h=127.0d;
        System.out.println(g==h);//true 浮点型  小数位没有有效值 理解成整型部分相等即相等
        System.out.println(127.1f==127.1d);//false 小数位有有效值 理解成虽然小数值也一样 但double和float小数位在jvm存储不一样 所以不相等

        System.out.println("========特别的计算转换(byte/short/char/int计算结果默认都是int，又一次验证了对于jvm 整型的byte/short/int是一样的东西)========");
        int i=1;
        short s1=1;
        short s2=2;
        char c3=1;
        char c4=1;
        //todo 当byte、short、char三者在计算后的结果都转换为了int类型（包含同类型之间的计算 例如byte+byte 最终结果也是int）。
        //byte b1=i+1;    //报错
        //short s3=s1+s2; //报错
        //byte b2=i+s2;   //报错
        //char c5=c3+3;   //报错
        //char c6=c3+c4;  //报错
        byte b1=(byte) (i+1); //也就是说需要把最终结果 整体强制转成想要的类型

        System.out.println("==============强制类型转换==============");
        //除了容量小到大可以自动类型转换外 像容量大到小的类型以及char和short等不能自动转换的 如果不保证精度不丢失 都可以强制类型转换
        int i2=(int)200L;
        short s=(short)i2;
        byte b2=(byte)i2;
        char c5=(char)i2;
        char c6=(char)s;
        System.out.println(b2); //-56   byte最大才127  200损失精度 截取位数之后按2进制换算成10进制变成了 -56 了

        System.out.println("=======其他补充: == 和 equals 注意点=========");
        StringBuffer sb1 = new StringBuffer("aaa");
        String str1 = new String("aaa");
        String str2 = new String("aaa");
        System.out.println(str1==str2);//false
        System.out.println(str1.equals(str2));//true
        System.out.println(sb1.equals(str1));//false StringBuffer StringBuilder没有重写equals
        //ps:== 和 equals  ==比较引用（有的话） equals比较值(只能说对象设计目的上 本省定义在object实现还是调用的== jdk中的类有比较值相同特征的类大部分都重写了equals方法来实现比较值得目的
        // 我们自己定义的类想要比较值相同则需要自己重写equals)  基本数据类型不是类没有equals 比较大小只能用==
        //且基本数据类型在只在栈区开辟空间 存的是值 所以==此时比较的是就是值（可以把==直接理解成比较的是栈区存的内容 存的是值就比较值 对象时存的是引用地址 所以就比较引用）
    }
}
