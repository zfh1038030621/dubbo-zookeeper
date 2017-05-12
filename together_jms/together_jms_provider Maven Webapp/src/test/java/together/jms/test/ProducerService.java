package together.jms.test;


import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
 
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:ApplicationContext.xml" })
@Component
public class ProducerService  {
	@Resource
	private JmsTemplate jmsTemplate;
    public void sendMessage(Destination destination, final String message) {
        System.out.println("---------------生产者发送消息-----------------");
        System.out.println("---------------生产者发了一个消息：" + message);
     /*   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        applicationContext.start();  */
     /*   jmsTemplate template = (JmsTemplate) applicationContext.getBean("jmsTemplate");*/
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    } 

}
