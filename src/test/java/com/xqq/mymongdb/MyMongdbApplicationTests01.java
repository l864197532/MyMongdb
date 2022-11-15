package com.xqq.mymongdb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xqq.mymongdb.entity.User;
import com.xqq.mymongdb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class MyMongdbApplicationTests01 {

    @Autowired
    private UserRepository repository;


    @Test
    void create() {
        User user = new User();
        user.setAge(22);
        user.setName("lzt");
        user.setEmail("13212@qq.com");
        repository.save(user);
    }

    @Test
    void findAll() {
        List<User> all = repository.findAll();
        System.out.println(all);
    }

    @Test
    void findbyId() {
        User user = repository.findById("6373724be6fe135beacd62f4").get();
        System.out.println(user);
    }

    @Test
    void findUserList() {
        User user = new User();
        user.setAge(22);
        Example<User> userExample = Example.of(user);
        List<User> all = repository.findAll(userExample);
        System.out.println(all);
    }
    @Test
    void findLikeUserList() {
        //模糊查询
        ExampleMatcher exampleMatcher =ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);


        User user = new User();
        user.setName("q");
        Example<User> userExample = Example.of(user,exampleMatcher);
        List<User> all = repository.findAll(userExample);
    }

    @Test
    void findPageUserList() {
        Pageable pageable = PageRequest.of(0, 3);


        User user = new User();
        user.setAge(22);
        Example<User> userExample = Example.of(user);
        Page<User> all = repository.findAll(userExample, pageable);
        List<User> content = all.getContent();
        System.out.println(content);

    }

    @Test
    public void updateUser(){
        User user = repository.findById("6373724be6fe135beacd62f4").get();
        user.setAge(100);
        User save = repository.save(user);
        System.out.println(save);
    }

    @Test
    public void deleteUser(){
        repository.deleteById("6373724be6fe135beacd62f4");
    }
}
