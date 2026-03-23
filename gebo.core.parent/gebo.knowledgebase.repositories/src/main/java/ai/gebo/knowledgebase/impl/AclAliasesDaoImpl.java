package ai.gebo.knowledgebase.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.architecture.persistence.IGMongoSequenceService;
import ai.gebo.knowledgebase.impl.model.AclEntryRecord;
import ai.gebo.knowledgebase.repositories.AclEntryRecordRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AclAliasesDaoImpl implements IAclAliasesDao {
	private static final String ACL_ENTRY = "aclEntry";
	final AclEntryRecordRepository repository;
	final IGMongoSequenceService mongoSequenceService;
	@Override
	@Transactional
	public int addAcl(GAclEntry entry) {
		long aclId = mongoSequenceService.nextSequence(ACL_ENTRY);
		AclEntryRecord record = new AclEntryRecord();
		record.setId((int) aclId);
		record.setGrant(entry.getGrant());
		record.setAclGrantedUniqueId(entry.getAclGrantedUniqueId());
		repository.insert(record);
		return record.getId();
	}

	@Override
	public GAclEntry findAcl(int alias) {
		AclEntryRecord record = repository.findById(alias).orElse(null);
		return record != null ? new GAclEntry(record.getAclGrantedUniqueId(), record.getGrant()) : null;
	}

	@Override
	public Integer findAlias(GAclEntry entry) {
		List<AclEntryRecord> found = repository.findByAclGrantedUniqueIdAndGrant(entry.getAclGrantedUniqueId(),
				entry.getGrant());
		return found.isEmpty() ? null : found.get(0).getId();
	}

	@Override
	@Transactional
	public void removeAcl(int alias) {
		repository.deleteById(alias);
	}
}
