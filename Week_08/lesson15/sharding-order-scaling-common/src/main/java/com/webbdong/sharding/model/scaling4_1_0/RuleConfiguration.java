package com.webbdong.sharding.model.scaling4_1_0;

import lombok.Builder;
import lombok.Data;

/**
 * @author Webb Dong
 * @date 2021-03-15 12:43 AM
 */
@Data
@Builder
public class RuleConfiguration {

    private String sourceDatasource;

    private String sourceRule;

    private DataSourceParameter destinationDataSources;

}
