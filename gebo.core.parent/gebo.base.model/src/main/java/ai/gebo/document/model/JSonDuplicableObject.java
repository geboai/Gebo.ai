package ai.gebo.document.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JSonDuplicableObject<Type> {
	static ObjectMapper mapper = new ObjectMapper();

	/***************************************
	 * Create a copy of the actual object using Jackson Json
	 * serialization/deserialization
	 * 
	 * @return
	 */
	public default Type createCopy() {
		try {
			// Serialize the current object to JSON and then deserialize it back to create a
			// deep copy.
			String _out = mapper.writeValueAsString(this);
			return (Type) mapper.readValue(_out.getBytes(), this.getClass());
		} catch (Throwable th) {
			throw new RuntimeException("GeboDocument.createCopy() passing from ObjectMapper has failed", th);
		}
	}
}
