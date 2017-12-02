package com.senpure.io.protoc.bean;

import com.senpure.io.message.Bean;
import com.senpure.io.protoc.Varint;
import io.netty.buffer.ByteBuf;

/**
* 书本
* 
* @author senpure-generator
* @version 2017-12-2 14:35:41
*/
public class Book extends  Bean {
    //书本名
    private String name;
    //书本单价
    private int price;
    //作者
    private Author author;

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //书本名
        writeStr(buf,name);
        //书本单价
       // writeInt(buf,price);
        Varint.writeVarint(buf, price);
        //作者
        writeBean(buf,author,true);
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //书本名
        this.name= readStr(buf);
        //书本单价
        //this.price = readInt(buf);
        this.price=Varint.readVarint(buf);
        //作者
        this.author = (Author)readBean(buf,Author.class,true);
    }

    /**
     * get 书本名
     * @return
     */
    public  String getName() {
        return name;
    }

    /**
     * set 书本名
     */
    public Book setName(String name) {
        this.name=name;
        return this;
    }
    /**
     * get 书本单价
     * @return
     */
    public  int getPrice() {
        return price;
    }

    /**
     * set 书本单价
     */
    public Book setPrice(int price) {
        this.price=price;
        return this;
    }
    public  Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author=author;
        return this;
    }

    @Override
    public String toString() {
        return "Book{"
                +"name=" + name
                +",price=" + price
                +",author=" + author
                + "}";
   }

    //6 + 3 = 9 个空格
    private String nextIndent ="         ";
    //最长字段长度 6
    private int filedPad = 6;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("Book").append("{");
        //书本名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        //书本单价
        sb.append("\n");
        sb.append(indent).append(rightPad("price", filedPad)).append(" = ").append(price);
        //作者
        sb.append("\n");
        sb.append(indent).append(rightPad("author", filedPad)).append(" = ");
        if(author!=null){
            sb.append(author.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}