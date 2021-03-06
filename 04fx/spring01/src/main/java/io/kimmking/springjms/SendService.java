package io.kimmking.springjms;

import io.kimmking.spring01.Student;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class SendService {
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    ActiveMQQueue queue;
    public void send(final Student user) {
        jmsTemplate.send(queue, new MessageCreator() {
            
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(user);
            }
        });
    }
}