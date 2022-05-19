package net.yorksolutions.apiexercise;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class IpInformation {
    @JsonProperty
    public String ip;

    IpInformation(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpInformation that = (IpInformation) o;
        return Objects.equals(ip, that.ip);
    }



//    public IpInformation() throws UnknownHostException {
//        var ipData = InetAddress.getLocalHost().getHostAddress();
//        this.ip = ipData;
//    }
}
