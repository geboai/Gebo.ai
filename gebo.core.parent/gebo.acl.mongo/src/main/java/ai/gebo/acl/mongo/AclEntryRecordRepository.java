package ai.gebo.acl.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.acl.AclGrantType;

public interface AclEntryRecordRepository extends MongoRepository<AclEntryRecord, Integer> {
	List<AclEntryRecord> findByAclGrantedUniqueId(String uniqueId);

	List<AclEntryRecord> findByAclGrantedUniqueIdAndGrant(String uniqueId, AclGrantType grant);

	List<AclEntryRecord> findByAclGrantedUniqueIdInAndGrant(List<String> uniqueIds, AclGrantType grant);

	void deleteByAclGrantedUniqueId(String uniqueId);

	List<AclEntryRecord> findByAclGrantedUniqueIdIn(List<String> aclGrantedUniqueId);
}
