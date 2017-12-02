package com.senpure.io.protoc;

import com.senpure.io.protoc.bean.Author;
import com.senpure.io.protoc.bean.Book;
import com.senpure.io.protoc.bean.Student;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by 罗中正 on 2017/12/2 0002.
 */
public class ProtocSenpure {
    public static void main(String[] args) {

        Book readBook = new Book();
        readBook.setName("罗密欧与朱丽叶");
        //readBook.setName("ruomio and zhuliy");

        Author author=new Author();
        author.setPhone(123456);
        author.setName("jklLUo林");
        readBook.setAuthor(author);
        readBook.setPrice(25);
        Student student = new Student();
        student.setAge(23);
        student.setNum(10086);
        student.setName("xiaoming名");
        student.setReadBook(readBook);
        student.getLuckNums().add(101);
        student.getLuckNums().add(998);
        student.getProvide().add("nice to meeet you ");
        student.getProvide().add("nice to meeet you2 ");
        student.getProvide().add("nice to meeet you3 ");
        student.getProvide().add("nice to meeet you4 ");
        student.getLikeBooks().add(readBook);
        for (int i = 0; i < 2; i++) {
            Book book = new Book();
            book.setName("halibote哈利波特(" + (i + 1) + ")");
            book.setPrice(28);

                author=new Author();
                author.setName("xitulu"+i);
                author.setPhone(66666);
                book.setAuthor(author);

            student.getLikeBooks().add(book);
        }
       ByteBuf b= Unpooled.buffer();
       // b.ensureWritable(317);
        System.out.println("senpure协议");
        System.out.println("对象实体\n"+student.toString(""));


        student.write(b);
        System.out.println("序列化后大小 " + b.writerIndex() + "b");
        int count=100000;
        long time = System.currentTimeMillis();
        for (int i = 0; i <count ; i++) {
            ByteBuf buf = Unpooled.buffer();

            student.write(buf);
        }
        System.out.println("序列化  [" + count + "]次,用时" + (System.currentTimeMillis() - time));
        student=new Student();
        student.read(b);
        System.out.println(student);
        time = System.currentTimeMillis();
        b.resetReaderIndex();
        for (int i = 0; i < count; i++) {
           Student student1=new Student();
           student1.read(b);
           b.resetReaderIndex();
        }
        System.out.println("反序列化[" + count + "]次,用时" + (System.currentTimeMillis() - time));

    }
}
