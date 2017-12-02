package com.senpure.io.protoc.handler;

import com.senpure.io.protoc.message.CSStudentMessage;
import com.senpure.io.message.AbstractMessageHandler
;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * 请求学生信息处理器
 * 
 * @author senpure-generator
 * @version 2017-12-2 14:35:41
 */
@Component
public class CSStudentMessageHandler extends AbstractMessageHandler<CSStudentMessage> {

    @Override
    public void execute(Channel channel, CSStudentMessage message) {
        //TODO 请在这里写下你的代码

    }

    @Override
    public int handlerId() {
    return 66101;
    }

    @Override
    public CSStudentMessage getEmptyMessage() {
    return new CSStudentMessage();
    }

    }