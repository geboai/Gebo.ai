package ai.gebo.security.model;

import ai.gebo.security.model.UsersGroup;
import jakarta.validation.constraints.NotNull;

/**
 * Inner class for holding group information, including code and description.
 */
public class GroupInfo {
	@NotNull
	public String code=null;
	@NotNull
	public String description=null;

	/**
	 * Creates a GroupInfo instance from a UsersGroup object.
	 * 
	 * @param ug UsersGroup object to be converted
	 * @return GroupInfo object
	 */
	public static GroupInfo of(UsersGroup ug) {
		GroupInfo i=new GroupInfo();
		i.code=ug.getCode();
		i.description=ug.getDescription();
		return i;
	}
}