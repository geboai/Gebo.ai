package ai.gebo.acl;

import java.util.List;

public interface IAclGrantedAccess {
	public static final String EVERYONE_ACL_UNIQUE_ID = "everyone:everyone@gebo.ai";

	public String getAclUniqueId();

	public String getObjectType();

	public List<Integer> getOwnedAclAliases();
}
