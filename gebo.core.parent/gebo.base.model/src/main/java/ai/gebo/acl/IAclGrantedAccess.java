package ai.gebo.acl;

import java.util.List;

public interface IAclGrantedAccess {
	public String getAclUniqueId();
	public String getObjectType();
	public List<Integer> getOwnedAclAliases();
}
