package net.abadguy.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义需要校验的License参数
 *
 */
@Getter
@Setter
public class LicenseCheckModel implements Serializable {

    private static final long serialVersionUID = 8600137500316662317L;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"ipAddress\":").append(ipAddress)
                .append(", \"macAddress\":").append(macAddress)
                .append(", \"cpuSerial\":").append(cpuSerial)
                .append(", \"mainBoardSerial\":").append(mainBoardSerial)
                .append('}');
        return sb.toString();
    }

    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

}
