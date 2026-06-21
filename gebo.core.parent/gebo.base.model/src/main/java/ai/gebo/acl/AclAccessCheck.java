package ai.gebo.acl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AclAccessCheck {
	
	public static boolean hasAccess(IAclAliasesDao dao, IAclGrantedAccessor accessor, IAclGrantedResource resource,
			AclGrantType grantType, boolean emptyAclMeansCanAccess) {
		Map<String, Boolean> selectedOwnedIds = new HashMap<>();
		List<IAclGrantedAccess> accesses = accessor.getAccesses();
		List<GAclEntry> acls = new ArrayList<>();
		List<Integer> aclsAliases = resource.getAclAliases();

		if (emptyAclMeansCanAccess && (aclsAliases == null || aclsAliases.isEmpty())) {
			return true;
		}
		if (accesses != null)
			for (IAclGrantedAccess access : accesses) {
				selectedOwnedIds.put(access.getAclUniqueId(), true);
			}
		if (aclsAliases != null && !aclsAliases.isEmpty()) {
			for (Integer integer : aclsAliases) {
				GAclEntry aclEntry = dao.findAcl(integer.intValue());
				if (aclEntry != null && aclEntry.getGrant() == grantType
						&& selectedOwnedIds.containsKey(aclEntry.getAclGrantedUniqueId())) {
					if (acls == null)
						acls = new ArrayList<>();
					acls.add(aclEntry);
				}
			}
		}

		return !acls.isEmpty();
	}

}
