package ai.gebo.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GAclEntry implements Serializable {
	private String aclGrantedUniqueId = null;
	private AclGrantType grant = null;

	public static final GAclEntry EVERYONE_READ_ACCESS = new GAclEntry(IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID,
			AclGrantType.READ);
	public static final GAclEntry EVERYONE_WRITE_ACCESS = new GAclEntry(IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID,
			AclGrantType.WRITE);
	public static final GAclEntry EVERYONE_EXECUTE_ACCESS = new GAclEntry(IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID,
			AclGrantType.EXECUTE);
}
