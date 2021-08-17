package DatabaseFiles;

import Model.Postagem;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.function.Consumer;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnection {

    public MongoConnection() {
    }

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

    MongoDatabase database = mongoClient.getDatabase("projeto")
            .withCodecRegistry(pojoCodecRegistry);

    MongoCollection<Postagem> collection= database.getCollection("Usuario", Postagem.class);

    public void adicionaPostagem(Postagem postagem){
        collection.insertOne(postagem);
    }

    public void buscaPostagem(String email){
        collection.find(Filters.eq("email", email))
                .forEach((Consumer<Postagem>) postagem ->System.out.println(postagem));

    }

    public void encerra(){
        mongoClient.close();
    }
}
