package com.example.accessingdatamongodb.Controller;


import com.example.accessingdatamongodb.dto.CalculateCost;
import com.example.accessingdatamongodb.dto.expensiveStates;
import com.mongodb.client.*;
import com.mongodb.internal.operation.OrderBy;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Sorts.descending;

@RestController
@RequestMapping("/default")
public class defaultController {
    private static final Logger logger = Logger.getLogger(defaultController.class.getName());

    /* The port on which the server should run */
    private static final int PORT = 50051;
    private Server server;

    private void start() throws IOException {
        server = Grpc.newServerBuilderForPort(PORT, InsecureServerCredentials.create())
                .build()
                .start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    defaultController.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
    public String Hello1()
    {
        return "Hello World";
    }
    @PostMapping("/Task1.2.1")
    public String task1(@RequestBody CalculateCost calculateCost){
        String connectionString = System.getProperty("mongodb://localhost:27017/");
        Integer totalcost=0;
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/"))
        {
            MongoDatabase database = mongoClient.getDatabase("GRPC");
            try{
                MongoCollection<Document> eduCostStatCollection = database.getCollection("EduCostStat");
                FindIterable<Document> educostList=  eduCostStatCollection.find(new Document("Year", calculateCost.getYear()).append("State",calculateCost.getState())
                        .append("Type",calculateCost.getType()).append("Length",calculateCost.getLength())
                        .append("Expense",calculateCost.getExpense()));
                String completeQuery ="eduCostStatCollection.find(new Document('Year',"+ calculateCost.getYear()+").append('State',"+calculateCost.getState()+").append('Type',"+calculateCost.getType()+").append('Length',"+calculateCost.getLength()+").append('Expense',"+calculateCost.getExpense()+"))";
                MongoCollection<Document> eduCostStatoneCollection = database.getCollection("EduCostStatQueryOne");
                eduCostStatoneCollection.insertOne(new Document("query",completeQuery));
                MongoCursor<Document> it = educostList.iterator();

                while (it.hasNext()) {
                    int val=Integer.parseInt(it.next().get("Value").toString());
                    totalcost=totalcost+val;
                }
                System.out.println(totalcost);
            }catch (Exception ex){

            }
        }
        String s=String.valueOf(totalcost);
        return String.valueOf(totalcost);
    }

    @PostMapping("/Task1.2.3")
    public String task3(@RequestBody expensiveStates expensiveStates){
        String connectionString = System.getProperty("mongodb://localhost:27017/");
        Integer totalcost=0;
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/"))
        {
            MongoDatabase database = mongoClient.getDatabase("GRPC");
            try{
                MongoCollection<Document> eduCostStatCollection = database.getCollection("EduCostStat");
                String expense="Expense";
                FindIterable<Document> educostList=  eduCostStatCollection.find(new Document("Year", expensiveStates.getYear())
                        .append("Type",expensiveStates.getType()).append("Length",expensiveStates.getLength())).sort(descending("Value"));
                MongoCursor<Document> it = educostList.iterator();
                Document group = new Document("$group", new Document("_id","$State"));
                Document limit = new Document("$limit", 5);
                MongoCursor<Document> cursor = eduCostStatCollection.aggregate(Arrays.asList(group)).iterator();
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    System.out.println(doc.toJson());
                }
                System.out.println(totalcost);


            }catch (Exception ex){

            }
        }
        String s=String.valueOf(totalcost);
        return String.valueOf(totalcost);
    }

    @PostMapping("/Task1.2.2")
    public String task2(@RequestBody expensiveStates expensiveStates){
        String connectionString = System.getProperty("mongodb://localhost:27017/");
        Integer totalcost=0;
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/"))
        {
            MongoDatabase database = mongoClient.getDatabase("GRPC");
            try{
                MongoCollection<Document> eduCostStatCollection = database.getCollection("EduCostStat");
                String expense="Expense";
                FindIterable<Document> educostList=  eduCostStatCollection.find(new Document("Year", expensiveStates.getYear())
                        .append("Type",expensiveStates.getType()).append("Length",expensiveStates.getLength())).sort(descending("Value"));
                MongoCursor<Document> it = educostList.iterator();


            }catch (Exception ex){

            }
        }
        String s=String.valueOf(totalcost);
        return String.valueOf(totalcost);
    }

    @GetMapping(value = "/")
    public String Hello()
    {
        return "Hello World";
    }


}
