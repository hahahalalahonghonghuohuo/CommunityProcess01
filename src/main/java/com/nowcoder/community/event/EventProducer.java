package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: yyp
 * @Date: 2024/4/26 - 04 - 26 - 13:38
 * @Description: com.nowcoder.community.event
 * @version: 1.0
 */

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        // 将事件发布到指定的主题中
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }



}
