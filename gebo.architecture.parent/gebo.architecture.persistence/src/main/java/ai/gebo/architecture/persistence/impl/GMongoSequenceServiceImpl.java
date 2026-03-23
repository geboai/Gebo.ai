package ai.gebo.architecture.persistence.impl;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.IGMongoSequenceService;
import ai.gebo.architecture.persistence.impl.model.Counter;

@Service
public class GMongoSequenceServiceImpl implements IGMongoSequenceService {

	private final MongoOperations mongoOperations;

	public GMongoSequenceServiceImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public long nextSequence(String sequenceName) {
		Query query = new Query(Criteria.where("_id").is(sequenceName));

		Update update = new Update().inc("seq", 1);

		FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true) // restituisce il documento DOPO
																						// l'incremento
				.upsert(true); // crea il documento se non esiste

		Counter counter = mongoOperations.findAndModify(query, update, options, Counter.class);

		return counter != null ? counter.getSeq() : 1L;
	}
}