package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.StringPair;
import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link ReducerFactory} for the query 1
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
 */
public class Query7ReducerFactory implements ReducerFactory<StringPair, StringPair, Long> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query7ReducerFactory.class);
    
    @Override
    public Reducer<StringPair, Long> newReducer(final StringPair provincePair) {
        return new Reducer<StringPair, Long>() {
            
            private final Map<String, IntegerPair> departments = new HashMap<>();
            private long count;
            
            @Override
            public synchronized void reduce(final StringPair department) {
                
                final boolean isRight = department.getLeft().equals("");
                final String departmentName = isRight ? department.getRight() : department.getLeft();
                
                if (!departments.containsKey(departmentName)) {
                    departments.put(departmentName, new IntegerPair(isRight ? 0 : 1, isRight ? 1 : 0));
                    return;
                }
                
                final IntegerPair pair = departments.get(departmentName);
                
                if (pair.getLeft() == 1 && pair.getRight() == 1) {
                    return;
                }
                
                if (pair.getLeft() == 1 && isRight) {
                    departments.put(departmentName, new IntegerPair(1, 1));
                    count++;
                } else if (pair.getRight() == 1 && !isRight) {
                    count++;
                    departments.put(departmentName, new IntegerPair(1, 1));
                }
            }
    
            @Override
            public Long finalizeReduce() {
                return count;
            }
        };
    }
}
