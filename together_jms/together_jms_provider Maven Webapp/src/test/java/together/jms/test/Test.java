package together.jms.test;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:ApplicationContext.xml" })
public class Test {
	@Resource
	 private Destination queueDestination;  
@org.junit.Test
public void test(){
	ProducerService producerService=new ProducerService();
	producerService.sendMessage(queueDestination, "測試");
}
}
