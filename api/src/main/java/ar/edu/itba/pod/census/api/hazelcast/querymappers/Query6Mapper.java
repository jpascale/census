package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.Map;
import java.util.function.Function;

/**
 * {@link Mapper} for the query 5 (i.e transforms {@link Citizen} into a unit {@link Map.Entry}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query6Mapper<K> implements Mapper<K, Citizen, String, String> {
	
	/**
	 * Used for serialization of this {@link Mapper}.
	 */
	private static final long serialVersionUID = 1L;
	
	public void map(K key, Citizen citizen, Context<String, String> context) {
		context.emit(toOutKeyFunction().apply(citizen), citizen.getProvince().getName());
	}
	
	public Function<Citizen, String> toOutKeyFunction() {
		return Citizen::getDepartmentName;
	}
}
