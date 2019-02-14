package com.tensquare.base;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 操作mongoDB
 * @Author shawn
 * @create 2019/2/14 0014
 */
public class TestMongodb {
    public static void main(String[] args){
        MongoClient mongoClient = new MongoClient("47.107.183.79",27017);

        MongoDatabase spitdb = mongoClient.getDatabase("spitdb");

        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //封装查询条件  特殊条件不能用字符串
        BasicDBObject bson = new BasicDBObject("visits",new BasicDBObject("$gt",1000));
        FindIterable<Document> documents = spit.find(bson);
        for (Document document : documents){
            System.out.println("内容"+document.getString("content"));
            System.out.println("用户id"+document.getString("userid"));
            System.out.println("访问量"+document.getInteger("visits"));
        }

        //添加记录 { "_id" : "5", "content" : "我还是没有想明白到底为啥出错", "userid" : "1012", "nickname" : "小明", "visits" : 5 }
        Map map = new HashMap();
        map.put("_id","5");
        map.put("content","这个是我用java代码新添加的文档");
        map.put("userid","1015");
        map.put("nickname","shawn");
        map.put("visits","10");
        Document document = new Document(map);
        spit.insertOne(document);
        mongoClient.close();
    }
}
