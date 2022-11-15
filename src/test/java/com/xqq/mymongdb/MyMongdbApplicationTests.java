package com.xqq.mymongdb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xqq.mymongdb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class MyMongdbApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void create() {
        User user = new User();
        user.setAge(22);
        user.setName("xqq");
        user.setEmail("123@qq.com");
        User insert = mongoTemplate.insert(user);
        System.out.println(insert);
    }

    @Test
    void findAll() {
        List<User> all = mongoTemplate.findAll(User.class);
        System.out.println(all);
    }

    @Test
    void findbyId() {
        User all= mongoTemplate.findById("63735a5efb20355a2658458b",User.class);
        System.out.println(all);
    }

    @Test
    void findUserList() {
        Query query = new Query(Criteria.where("name").is("xqq").and("age").is(22));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
    }
    @Test
    void findLikeUserList() {
        String name = "qq";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern =Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
    }

    @Test
    void findPageUserList() {

        int pageNum =1;
        int pageSize= 2;

        String name = "qq";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern =Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));

        long count = mongoTemplate.count(query, User.class);
        List<User> users = mongoTemplate.find(query.skip((pageNum - 1) * pageSize).limit(pageSize), User.class);
        System.out.println(users);
    }

    @Test
    public void updateUser(){
        User byId = mongoTemplate.findById("63735a5efb20355a2658458b", User.class);
        byId.setAge(55);
        Query query = new Query(Criteria.where("_id").is("63735a5efb20355a2658458b"));
        Update update = new Update();
        update.set("age",byId.getAge());
        UpdateResult upsert = mongoTemplate.upsert(query, update, User.class);
        long matchedCount = upsert.getMatchedCount();
        System.out.println(matchedCount);
    }

    @Test
    public void deleteUser(){
        Query query = new Query(Criteria.where("_id").is("63735a5efb20355a2658458b"));
        DeleteResult remove = mongoTemplate.remove(query, User.class);
        System.out.println(remove.getDeletedCount());
    }
}
