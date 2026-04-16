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
}
