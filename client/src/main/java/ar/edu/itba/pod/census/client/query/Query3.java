package ar.edu.itba.pod.census.client.query;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.TopNCollator;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query2CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query2Mapper;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query3Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query2ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;

public class Query3 extends HazelcastQuery<Region, Long> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query3(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<Region, Long> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
    	return null;
//        return job.mapper(new Query3Mapper<>())
//                .combiner(new Query2CombinerFactory())
//                .reducer(new Query2ReducerFactory())
//                .submit(new TopNCollator<>(params.getN()))
//                .get();
    }
}
