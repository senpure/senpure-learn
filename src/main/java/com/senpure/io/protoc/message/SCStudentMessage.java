package com.senpure.io.protoc.message;

import com.senpure.io.protoc.bean.Student;
import com.senpure.io.message.Message;
import io.netty.buffer.ByteBuf;

/**
 * 学生信息
 * 
 * @author senpure-generator
 * @version 2017-12-2 14:35:41
 */
public class SCStudentMessage extends  Message {
    //学生信息
    private Student student;

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //学生信息

        writeBean(buf,student,true);
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //学生信息
        this.student = (Student)readBean(buf,Student.class,true);
    }

    /**
     * get 学生信息
     * @return
     */
    public  Student getStudent() {
        return student;
    }

    /**
     * set 学生信息
     */
    public SCStudentMessage setStudent(Student student) {
        this.student=student;
        return this;
    }

    @Override
    public int getMessageId() {
    return 66102;
    }

    @Override
    public String toString() {
        return "SCStudentMessage{"
                +"student=" + student
                + "}";
   }

    //7 + 3 = 10 个空格
    private String nextIndent ="          ";
    //最长字段长度 7
    private int filedPad = 7;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCStudentMessage").append("{");
        //学生信息
        sb.append("\n");
        sb.append(indent).append(rightPad("student", filedPad)).append(" = ");
        if(student!=null){
            sb.append(student.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}