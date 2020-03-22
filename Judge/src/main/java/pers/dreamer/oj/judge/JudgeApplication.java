package pers.dreamer.oj.judge;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Objects;

@SpringBootApplication
// 扫描mybatis mapper包路径
@MapperScan(basePackages="pers.dreamer.oj.judge.dao")
// 扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@EnableRabbit
@ComponentScan(basePackages= {"pers.dreamer*"})
@EnableAsync
public class JudgeApplication {

    @Autowired
    private LettuceConnectionFactory factory;

    @Bean
    public Gson gson(){
        return new Gson();
    }

    //<!--redis-->
    @Bean
    public RedisTemplate stringRedisConnection(RedisTemplate redisTemplate){
        LettuceConnectionFactory redisConnectionFactory = (LettuceConnectionFactory)redisTemplate.getConnectionFactory();
        redisConnectionFactory.setDatabase(1);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(JudgeApplication.class, args);
    }

}
