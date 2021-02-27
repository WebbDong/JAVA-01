package com.webbdong.autoconfigure.starter.bean;

/**
 * Windows
 * @author Webb Dong
 * @date 2021-02-26 6:45 PM
 */
public class WindowsOperatingSystem implements OperatingSystem {

    @Override
    public String getCurrentSystemName() {
        return "Windows";
    }

}
