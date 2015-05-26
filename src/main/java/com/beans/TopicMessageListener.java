package com.beans;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

/**
 * Created with IntelliJ IDEA.
 * User: bnayar
 * Date: 11/21/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopicMessageListener implements MessageListener<Object> {

    @Override
    public void onMessage(Message message) {
        System.out.println("message = "+message.getMessageObject());
    }

}
