package com.senpure.io.protoc.message;

import com.senpure.io.message.Message;
import io.netty.buffer.ByteBuf;

/**
 * 请求学生信息
 * 
 * @author senpure-generator
 * @version 2017-12-2 14:35:41
 */
public class CSStudentMessage extends  Message {
    //学生名
    private String name;

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //学生名
        writeStr(buf,name);
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //学生名
        this.name= readStr(buf);
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
    public CSStudentMessage setName(String name) {
        this.name=name;
        return this;
    }

    @Override
    public int getMessageId() {
    return 66101;
    }

    @Override
    public String toString() {
        return "CSStudentMessage{"
                +"name=" + name
                + "}";
   }

    //最长字段长度 4
    private int filedPad = 4;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("CSStudentMessage").append("{");
        //学生名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}