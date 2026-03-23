package ai.gebo.acl;

import java.util.List;

public interface IAclAliasesDao {
	public int addAcl(GAclEntry entry);

	public GAclEntry findAcl(int alias);
	
	public List<Integer> findAliasesByAclGrantedUniqueId(String aclGrantedUniqueId);

	public Integer findAlias(GAclEntry entry);

	public void removeAcl(int alias);

}
