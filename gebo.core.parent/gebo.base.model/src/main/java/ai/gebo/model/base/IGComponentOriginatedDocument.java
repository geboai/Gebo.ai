package ai.gebo.model.base;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

public interface IGComponentOriginatedDocument {
	@NotNull
	public GeboComponentInfo getOriginComponent();

	@NotNull
	public String getCode();
	
	public Date getModificationDate();
}
