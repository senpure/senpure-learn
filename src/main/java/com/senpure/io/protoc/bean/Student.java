package com.senpure.io.protoc.bean;

import com.senpure.io.message.Bean;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
* 学生
* 
* @author senpure-generator
* @version 2017-12-2 14:35:41
*/
public class Student extends  Bean {
    //学生名
    private String name;
    //学生年龄
    private int age;
    //学号
    private int num;
    //幸运数字
    private List<Integer> luckNums=new ArrayList();
    //格言
    private List<String> provide=new ArrayList();
    //正在读的书
    private Book readBook;
    //喜欢的书
    private List<Book> likeBooks=new ArrayList();

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //学生名
        writeStr(buf,name);
        //学生年龄
        writeInt(buf,age);
        //学号
        writeInt(buf,num);
        //幸运数字
        int luckNumsSize=luckNums.size();
        writeShort(buf,luckNumsSize);
        for(int i=0;i< luckNumsSize;i++){
            writeInt(buf,luckNums.get(i));
           }
        //格言
        int provideSize=provide.size();
        writeShort(buf,provideSize);
        for(int i=0;i< provideSize;i++){
            writeStr(buf,provide.get(i));
           }
        //正在读的书
        writeBean(buf,readBook,true);
        //喜欢的书
        int likeBooksSize=likeBooks.size();
        writeShort(buf,likeBooksSize);
        for(int i=0;i< likeBooksSize;i++){
            writeBean(buf,likeBooks.get(i),false);
           }
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //学生名
        this.name= readStr(buf);
        //学生年龄
        this.age = readInt(buf);
        //学号
        this.num = readInt(buf);
        //幸运数字
        int luckNumsSize=readShort(buf);
        for(int i=0;i<luckNumsSize;i++){
            this.luckNums.add(readInt(buf));
         }
        //格言
        int provideSize=readShort(buf);
        for(int i=0;i<provideSize;i++){
            this.provide.add(readStr(buf));
         }
        //正在读的书
        this.readBook = (Book)readBean(buf,Book.class,true);
        //喜欢的书
        int likeBooksSize=readShort(buf);
        for(int i=0;i<likeBooksSize;i++){
            this.likeBooks.add((Book)readBean(buf,Book.class,false));
         }
    }

    /**
     * get 学生名
     * @return
     */
    public  String getName() {
        return name;
    }

    /**
     * set 学生名
     */
    public Student setName(String name) {
        this.name=name;
        return this;
    }
    /**
     * get 学生年龄
     * @return
     */
    public  int getAge() {
        return age;
    }

    /**
     * set 学生年龄
     */
    public Student setAge(int age) {
        this.age=age;
        return this;
    }
    public  int getNum() {
        return num;
    }

    public Student setNum(int num) {
        this.num=num;
        return this;
    }
     /**
      * get 幸运数字
      * @return
      */
    public List<Integer> getLuckNums(){
        return luckNums;
    }
     /**
      * set 幸运数字
      */
    public Student setLuckNums (List<Integer> luckNums){
        this.luckNums=luckNums;
        return this;
    }

    public List<String> getProvide(){
        return provide;
    }
    public Student setProvide (List<String> provide){
        this.provide=provide;
        return this;
    }

    /**
     * get 正在读的书
     * @return
     */
    public  Book getReadBook() {
        return readBook;
    }

    /**
     * set 正在读的书
     */
    public Student setReadBook(Book readBook) {
        this.readBook=readBook;
        return this;
    }
     /**
      * get 喜欢的书
      * @return
      */
    public List<Book> getLikeBooks(){
        return likeBooks;
    }
     /**
      * set 喜欢的书
      */
    public Student setLikeBooks (List<Book> likeBooks){
        this.likeBooks=likeBooks;
        return this;
    }


    @Override
    public String toString() {
        return "Student{"
                +"name=" + name
                +",age=" + age
                +",num=" + num
                +",luckNums=" + luckNums
                +",provide=" + provide
                +",readBook=" + readBook
                +",likeBooks=" + likeBooks
                + "}";
   }

    //9 + 3 = 12 个空格
    private String nextIndent ="            ";
    //最长字段长度 9
    private int filedPad = 9;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("Student").append("{");
        //学生名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        //学生年龄
        sb.append("\n");
        sb.append(indent).append(rightPad("age", filedPad)).append(" = ").append(age);
        //学号
        sb.append("\n");
        sb.append(indent).append(rightPad("num", filedPad)).append(" = ").append(num);
        //幸运数字
        sb.append("\n");
        sb.append(indent).append(rightPad("luckNums", filedPad)).append(" = ");
        int luckNumsSize = luckNums.size();
        if (luckNumsSize > 0) {
            sb.append("[");
            for (int i = 0; i<luckNumsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(luckNums.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //格言
        sb.append("\n");
        sb.append(indent).append(rightPad("provide", filedPad)).append(" = ");
        int provideSize = provide.size();
        if (provideSize > 0) {
            sb.append("[");
            for (int i = 0; i<provideSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(provide.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //正在读的书
        sb.append("\n");
        sb.append(indent).append(rightPad("readBook", filedPad)).append(" = ");
        if(readBook!=null){
            sb.append(readBook.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        //喜欢的书
        sb.append("\n");
        sb.append(indent).append(rightPad("likeBooks", filedPad)).append(" = ");
        int likeBooksSize = likeBooks.size();
        if (likeBooksSize > 0) {
            sb.append("[");
            for (int i = 0; i<likeBooksSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(likeBooks.get(i).toString(indent + nextIndent));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}