package ai.gebo.acl;

import java.util.List;

public interface IAclAliasesDao {
	public int addAcl(GAclEntry entry);

	public GAclEntry findAcl(int alias);

	public List<Integer> findAliasesByAclGrantedUniqueId(String aclGrantedUniqueId);

	public List<Integer> findAliasesByAclGrantedUniqueIdAndAclGrantType(String aclGrantedUniqueId,
			AclGrantType grantType);

	public Integer findAlias(GAclEntry entry);

	public void removeAcl(int alias);

}
