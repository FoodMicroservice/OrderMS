
package org.codedecode.orderms.service;

import org.codedecode.orderms.entity.Sequence;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGenrator {

    private final MongoOperations mongoOperations;

    public SequenceGenrator(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int generateNextOrder() {
        Sequence counter = mongoOperations.findAndModify(
                query(where("_id").is("sequence")),
                new Update().inc("sequence", 1),
                FindAndModifyOptions.options()
                        .returnNew(true)
                        .upsert(true),
                Sequence.class
        );

        if (counter == null) {
            throw new IllegalStateException(
                    "Unable to generate the next order ID"
            );
        }

        return counter.getSequence();
    }
}
