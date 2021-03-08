package com.webbdong.shardingsphere.dynamicdatasource.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据源切换算法
 * @author Webb Dong
 * @date 2021-03-08 12:10 PM
 */
public class DatasourceRoutingAlgorithm implements HintShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         HintShardingValue<String> shardingValue) {
        Set<String> result = new HashSet<>();
        for (String value : shardingValue.getValues()) {
            if (availableTargetNames.contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

}
