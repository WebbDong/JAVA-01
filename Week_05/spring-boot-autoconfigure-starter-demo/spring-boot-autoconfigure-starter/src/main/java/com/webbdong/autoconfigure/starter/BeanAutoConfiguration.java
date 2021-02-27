package com.webbdong.autoconfigure.starter;

import com.webbdong.autoconfigure.starter.bean.Klass;
import com.webbdong.autoconfigure.starter.bean.LinuxOperatingSystem;
import com.webbdong.autoconfigure.starter.bean.OperatingSystem;
import com.webbdong.autoconfigure.starter.bean.School;
import com.webbdong.autoconfigure.starter.bean.Student;
import com.webbdong.autoconfigure.starter.bean.WindowsOperatingSystem;
import com.webbdong.autoconfigure.starter.condition.LinuxCondition;
import com.webbdong.autoconfigure.starter.condition.WindowsCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置例子，配置一些 bean
 * @author Webb Dong
 * @date 2021-02-26 2:41 PM
 */
@Configuration
// 只有满足 my.beans.enabled 为 true 时，此自动配置才生效
// matchIfMissing 为 false 时，如果 my.beans.enabled 不为 true，当存在注入相关 bean 时，就会报错。
// matchIfMissing 为 true 时，如果 my.beans.enabled 不为 true，当存在注入相关 bean 时，就不会报错，会自动创建对应的 bean 实例并且注入
@ConditionalOnProperty(prefix = "my.beans", name = "enabled", havingValue = "true", matchIfMissing = false)
public class BeanAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "my.beans.student")
    public Student getStudent() {
        return new Student();
    }

    @Bean
    @ConfigurationProperties(prefix = "my.beans.school")
    public School getSchool() {
        return new School();
    }

    @Bean
    @ConfigurationProperties(prefix = "my.beans.klass")
    public Klass getKlass() {
        return new Klass();
    }

    /**
     * Windows 环境会注入此实例
     */
    @Bean
    @Conditional(WindowsCondition.class)
    public OperatingSystem getWindowsOperatingSystem() {
        return new WindowsOperatingSystem();
    }

    /**
     * Linux 环境会注入此实例
     */
    @Bean
    @Conditional(LinuxCondition.class)
    public OperatingSystem getLinuxOperatingSystem() {
        return new LinuxOperatingSystem();
    }

}
