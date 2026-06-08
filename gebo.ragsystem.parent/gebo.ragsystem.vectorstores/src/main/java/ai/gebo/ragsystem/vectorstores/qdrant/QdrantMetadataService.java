package ai.gebo.ragsystem.vectorstores.qdrant;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.JsonWithInt.Value;
import io.qdrant.client.grpc.Points.PointId;
import io.qdrant.client.grpc.Points.RetrievedPoint;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorizedFragmentMetadata;

import static io.qdrant.client.PointIdFactory.id;
import static io.qdrant.client.ValueFactory.value;

@AllArgsConstructor
public class QdrantMetadataService {

	private final QdrantClient qdrantClient;
	private final String collectionName;

	public List<VectorizedFragmentMetadata> readMetadataByIds(List<String> ids) throws ExecutionException, InterruptedException {

		if (ids == null || ids.isEmpty()) {
			return List.of();
		}

		List<PointId> pointIds = ids.stream().filter(Objects::nonNull).map(QdrantMetadataService::toPointId).toList();

		if (pointIds.isEmpty()) {
			return List.of();
		}

		List<RetrievedPoint> points = qdrantClient.retrieveAsync(collectionName, pointIds, true, // withPayload
				false, // withVectors
				null).get();

		List<VectorizedFragmentMetadata> result = new ArrayList<>();

		for (RetrievedPoint point : points) {
			result.add(new VectorizedFragmentMetadata(fromPointId(point.getId()), fromQdrantPayload(point.getPayloadMap())));
		}

		return result;
	}

	/**
	 * Aggiorna solo le chiavi presenti in metadata. Non tocca né il vettore né le
	 * altre chiavi payload già presenti.
	 */
	public void patchMetadataByIds(List<VectorizedFragmentMetadata> entries) throws ExecutionException, InterruptedException {

		if (entries == null || entries.isEmpty()) {
			return;
		}

		for (VectorizedFragmentMetadata entry : entries) {
			if (entry == null || entry.getId() == null) {
				continue;
			}

			Map<String, Object> metadata = entry.getMetadata();

			if (metadata == null || metadata.isEmpty()) {
				continue;
			}

			Map<String, Value> payloadPatch = metadata.entrySet().stream().filter(e -> e.getKey() != null)
					.filter(e -> e.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey,
							e -> toQdrantValue(e.getValue()), (a, b) -> b, LinkedHashMap::new));

			if (payloadPatch.isEmpty()) {
				continue;
			}

			qdrantClient
					.setPayloadAsync(collectionName, payloadPatch, List.of(toPointId(entry.getId())), true, null, null)
					.get();
		}
	}

	/**
	 * Variante: sostituisce completamente il payload.
	 */
	public void overwriteMetadataByIds(List<VectorizedFragmentMetadata> entries) throws ExecutionException, InterruptedException {

		if (entries == null || entries.isEmpty()) {
			return;
		}

		for (VectorizedFragmentMetadata entry : entries) {
			if (entry == null || entry.getId() == null) {
				continue;
			}

			Map<String, Object> metadata = entry.getMetadata() != null ? entry.getMetadata() : Map.of();

			Map<String, Value> payload = metadata.entrySet().stream().filter(e -> e.getKey() != null)
					.filter(e -> e.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey,
							e -> toQdrantValue(e.getValue()), (a, b) -> b, LinkedHashMap::new));

			qdrantClient
					.overwritePayloadAsync(collectionName, payload, List.of(toPointId(entry.getId())), true, null, null)
					.get();
		}
	}

	private static PointId toPointId(String idValue) {
		return id(UUID.fromString(idValue));
	}

	private static String fromPointId(PointId pointId) {
		if (pointId.hasUuid()) {
			return pointId.getUuid();
		}
		if (pointId.hasNum()) {
			return Long.toString(pointId.getNum());
		}
		return pointId.toString();
	}

	private static Map<String, Object> fromQdrantPayload(Map<String, Value> payload) {
		Map<String, Object> result = new LinkedHashMap<>();

		if (payload == null || payload.isEmpty()) {
			return result;
		}

		for (Map.Entry<String, Value> entry : payload.entrySet()) {
			result.put(entry.getKey(), fromQdrantValue(entry.getValue()));
		}

		return result;
	}

	private static Object fromQdrantValue(Value value) {
		if (value == null) {
			return null;
		}

		if (value.hasStringValue()) {
			return value.getStringValue();
		}

		if (value.hasIntegerValue()) {
			return value.getIntegerValue();
		}

		if (value.hasDoubleValue()) {
			return value.getDoubleValue();
		}

		if (value.hasBoolValue()) {
			return value.getBoolValue();
		}

		if (value.hasListValue()) {
			return value.getListValue().getValuesList().stream().map(QdrantMetadataService::fromQdrantValue).toList();
		}

		if (value.hasStructValue()) {
			Map<String, Object> map = new LinkedHashMap<>();

			value.getStructValue().getFieldsMap().forEach((k, v) -> map.put(k, fromQdrantValue(v)));

			return map;
		}

		return null;
	}

	private static Value toQdrantValue(Object value) {
		if (value instanceof String s) {
			return value(s);
		}

		if (value instanceof Integer i) {
			return value(i.longValue());
		}

		if (value instanceof Long l) {
			return value(l);
		}

		if (value instanceof Float f) {
			return value(f.doubleValue());
		}

		if (value instanceof Double d) {
			return value(d);
		}

		if (value instanceof Boolean b) {
			return value(b);
		}

		if (value instanceof Instant instant) {
			return value(instant.toString());
		}

		if (value instanceof Date date) {
			return value(date.toInstant().toString());
		}

		if (value instanceof Collection<?> collection) {
			return value(collection.stream().map(QdrantMetadataService::toQdrantValue).toList());
		}

		if (value instanceof Map<?, ?> map) {
			Map<String, Value> fields = new LinkedHashMap<>();

			map.forEach((k, v) -> {
				if (k != null && v != null) {
					fields.put(k.toString(), toQdrantValue(v));
				}
			});

			return value(fields);
		}

		return value(value.toString());
	}
}