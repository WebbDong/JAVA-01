package com.webbdong.autoconfigure.starter.bean;

/**
 * Linux
 * @author Webb Dong
 * @date 2021-02-26 6:52 PM
 */
public class LinuxOperatingSystem implements OperatingSystem {

    @Override
    public String getCurrentSystemName() {
        return "Linux";
    }

}
