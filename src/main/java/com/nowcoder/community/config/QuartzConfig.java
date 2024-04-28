package com.nowcoder.community.config;

import com.nowcoder.community.quartz.AlphaJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @Author: yyp
 * @Date: 2024/4/28 - 04 - 28 - 21:10
 * @Description: com.nowcoder.community.config
 * @version: 1.0
 */

// 配置 -> 数据库 -> 调用数据库来进行调度
@Configuration
public class QuartzConfig {

    // FactoryBean 可简化 Bean 的实例化过程:
    // 1. 通过 FactoryBean 封装了 Bean 的实例化过程.
    // 2. 将 FactoryBean 装配到 Spring 容器中.
    // 3. 将 FactoryBean 注入给其他的 Bean.
    // 4. 该 Bean 得到的是 FactoryBean 所管理的对象实例.

    // 配置 JobDetail
    // @Bean
    public JobDetailFactoryBean alphaJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    // 配置 Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)
    // @Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(alphaJobDetail);
        factoryBean.setName("alphaTrigger");
        factoryBean.setGroup("alphaTriggerGroup");
        factoryBean.setRepeatInterval(3000);
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }



}
