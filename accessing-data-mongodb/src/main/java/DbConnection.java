import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
public class DbConnection {

    public String createConnection(){
        String connectionString = System.getProperty("mongodb+srv://Manjunathajs22:Manju8746!@cluster0.yr8vx.mongodb.net/?retryWrites=true&w=majority");
        try(MongoClient mongoClient = MongoClients.create(connectionString))
        {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
        return "";
    }

}
