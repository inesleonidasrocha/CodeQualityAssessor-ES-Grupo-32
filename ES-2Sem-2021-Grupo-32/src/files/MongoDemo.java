package files;

import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDemo {

	public static void main(String[] args) {

		char[] pass = { 'a', 'l', 'u', 'n', 'o' };

		MongoCredential credential = MongoCredential.createCredential("aluno", "admin", pass);
		MongoClient mongoClientCloud = new MongoClient(new ServerAddress("194.210.86.10", 27017),
				Arrays.asList(credential));
		MongoDatabase cloudSid = mongoClientCloud.getDatabase("sid2021");

		MongoClient mongoClientLocal = new MongoClient("localhost", 27017);
		MongoDatabase localSid = mongoClientLocal.getDatabase("g20");
		while (true) {
			writeNewLocalCollection("sensort1", cloudSid, localSid);
		}
	}

	private static void writeNewLocalCollection(String collection, MongoDatabase cloudDB, MongoDatabase localDB) {

		MongoCollection<Document> cloudDocuments = cloudDB.getCollection(collection);

		MongoCollection<Document> localDocuments = localDB.getCollection(collection);

		for (Document doc : cloudDocuments.find()) {
			Document myDoc = localDocuments.find(doc).first();
			if (myDoc == null) {
				System.out.println("Ainda não existe vou inserir " + doc.toString());
				localDocuments.insertOne(doc);

			}

		}
	}
	// private class blsaaaa{
	// }
}
