package ai.gebo.architecture.angular.persistence.model;

import ai.gebo.model.GUserMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeletableStatus {
	private final boolean deletable;
	private final GUserMessage userMessage;
	public static final DeletableStatus DELETABLE_STATUS = new DeletableStatus(true,
			GUserMessage.infoMessage("You can delete this", "The current viewed data can be deleted safely"));
	public static  final DeletableStatus NOT_DELETABLE_STATUS = new DeletableStatus(false, GUserMessage
			.infoMessage("You cannot delete this", "This object is referred from other part(s) of the application"));

}
