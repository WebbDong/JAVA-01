package com.webbdong.sharding.model.scaling4_1_0;

import lombok.Builder;
import lombok.Data;

/**
 * @author Webb Dong
 * @date 2021-03-15 12:47 AM
 */
@Data
@Builder
public class DataSourceParameter {

    private String name;

    private String username;

    private String password;

    private String url;

}
