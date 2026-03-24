package ai.gebo.knowledgebase.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.acl.AclGrantType;
import ai.gebo.knowledgebase.impl.model.AclEntryRecord;

public interface AclEntryRecordRepository extends MongoRepository<AclEntryRecord, Integer> {
	List<AclEntryRecord> findByAclGrantedUniqueId(String uniqueId);

	List<AclEntryRecord> findByAclGrantedUniqueIdAndGrant(String uniqueId, AclGrantType grant);

	List<AclEntryRecord> findByAclGrantedUniqueIdInAndGrant(List<String> uniqueIds, AclGrantType grant);

	void deleteByAclGrantedUniqueId(String uniqueId);
}
