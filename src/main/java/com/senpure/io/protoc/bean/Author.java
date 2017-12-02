package com.senpure.io.protoc.bean;

import com.senpure.io.message.Bean;
import io.netty.buffer.ByteBuf;

/**
* 作者
* 
* @author senpure-generator
* @version 2017-12-2 14:35:41
*/
public class Author extends  Bean {
    //书本名
    private String name;
    //电话
    private int phone;

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //书本名
        writeStr(buf,name);
        //电话
        writeInt(buf,phone);
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //书本名
        this.name= readStr(buf);
        //电话
        this.phone = readInt(buf);
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
    public Author setName(String name) {
        this.name=name;
        return this;
    }
    public  int getPhone() {
        return phone;
    }

    public Author setPhone(int phone) {
        this.phone=phone;
        return this;
    }

    @Override
    public String toString() {
        return "Author{"
                +"name=" + name
                +",phone=" + phone
                + "}";
   }

    //最长字段长度 5
    private int filedPad = 5;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("Author").append("{");
        //书本名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        //电话
        sb.append("\n");
        sb.append(indent).append(rightPad("phone", filedPad)).append(" = ").append(phone);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}