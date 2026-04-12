package ai.gebo.systems.abstraction.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.virtualfs.VFilesystemReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
public class RemoteVirtualFileSystemContentConsumingSessionParam extends AbstractContentConsumingSessionParam {
	public static enum OperationType {
		ADD, REMOVE
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VirtualFileSystemOperation implements Serializable{
		private OperationType operation = null;
		private VFilesystemReference reference = null;
	}

	private List<VirtualFileSystemOperation> operations = new ArrayList<>();
}
