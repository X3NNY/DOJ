package pers.dreamer.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class ConfirmCallBackHandler implements RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(ConfirmCallBackHandler.class);

    @Async
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //根据回调的correlationDataId判断发送的哪条消息投递成功，哪条没有成功
        String correlationDataId =null;
        if(correlationData!=null){
            correlationDataId = correlationData.getId();
        }
        if(ack){
            logger.info("CONFIRM机制:消息投递成功:correlationId:{}",correlationDataId);
        }else{
            //TODO 消息投递失败,重新投递
            logger.warn("CONFIRM机制:消息投递失败:correlationId:{},cause:{}",correlationDataId,cause);
        }
    }
}