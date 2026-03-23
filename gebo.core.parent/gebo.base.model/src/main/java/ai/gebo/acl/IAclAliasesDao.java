package ai.gebo.acl;

public interface IAclAliasesDao {
	public int addAcl(GAclEntry entry);

	public GAclEntry findAcl(int alias);

	public Integer findAlias(GAclEntry entry);

	public void removeAcl(int alias);

}
