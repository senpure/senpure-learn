package com.senpure.io.protoc;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2017/12/2 0002.
 */
public class ProtocGoogle {

    public static void main(String[] args) {

        DemoMessage.Author.Builder author = DemoMessage.Author.newBuilder();
        author.setName("jklLUo林");
        author.setPhone(123456);
        DemoMessage.Book.Builder readBook = DemoMessage.Book.newBuilder();
        readBook.setAuthor(author);
        readBook.setName("罗密欧与朱丽叶");
        readBook.setPrice(25);
        DemoMessage.Student.Builder student = DemoMessage.Student.newBuilder();
        student.setAge(23);
        student.setNum(10086);
        student.setName("xiaoming名");
        student.setReadBook(readBook);
        List<Integer> nums = new ArrayList<>();
        nums.add(101);
        nums.add(998);
        student.addAllLuckNums(nums);

        List<String> provides = new ArrayList<>();
        provides.add("nice to meeet you ");
        provides.add("nice to meeet you2 ");
        provides.add("nice to meeet you3 ");
        provides.add("nice to meeet you4 ");
        student.addAllProvide(provides);
        student.setReadBook(readBook);
        List<DemoMessage.Book> books = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DemoMessage.Book.Builder book = DemoMessage.Book.newBuilder();
            book.setName("halibote哈利波特(" + (i + 1) + ")");
            book.setPrice(28);

            author = DemoMessage.Author.newBuilder();
            author.setName("xitulu" + i);
            author.setPhone(66666);
            book.setAuthor(author);

            books.add(book.build());

        }
        student.addAllLikeBooks(books);


        System.out.println("google协议");
        DemoMessage.Student s = student.build();
        System.out.println("对象实体\n" + s);

        s.toString();
        byte[] bytes = s.toByteArray();
        System.out.println("序列化后大小 " + bytes.length + "b");
        int count = 100000;
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            s.toByteArray();
        }
        System.out.println(" 序列化[" + count + "]次,用时" + (System.currentTimeMillis() - time));
        try {
            time = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                DemoMessage.Student.parseFrom(bytes);
            }
            System.out.println("反序列化[" + count + "]次,用时" + (System.currentTimeMillis() - time));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
