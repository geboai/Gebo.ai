package ai.gebo.acl;

import java.util.ArrayList;
import java.util.List;

public interface IAclGrantedAccessor {
	public List<IAclGrantedAccess> getAccesses();

	public default List<Integer> getAllOwnedAclAliases() {
		List<Integer> ids = new ArrayList<>();
		List<IAclGrantedAccess> ac = getAccesses();
		if (ac != null) {
			for (IAclGrantedAccess aclGra : ac) {
				List<Integer> _ids = aclGra.getOwnedAclAliases();
				if (_ids != null) {
					ids.addAll(_ids);
				}
			}
		}
		return ids;
	}

}
