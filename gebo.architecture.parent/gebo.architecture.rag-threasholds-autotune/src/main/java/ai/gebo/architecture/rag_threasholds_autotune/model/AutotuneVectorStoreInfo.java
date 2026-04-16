package ai.gebo.architecture.rag_threasholds_autotune.model;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class AutotuneVectorStoreInfo extends GBaseObject {
	private ThreasholdAutotuneProcessResult autotuneResult = null;

	public AutotuneVectorStoreInfo() {

	}

	public AutotuneVectorStoreInfo(GBaseObject o) {
		super(o);

	}

}
