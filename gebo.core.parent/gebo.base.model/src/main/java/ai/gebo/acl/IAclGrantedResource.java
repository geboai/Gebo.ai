package ai.gebo.acl;

import java.util.List;

public interface IAclGrantedResource {
	public List<Integer> getAclAliases();

	public void setAclAliases(List<Integer> acl);
}
