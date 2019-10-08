package com.ognice.module;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class DiscoveryService {
    private String name;
    private String host;
    private String port;
    private String lastHeartBeat;
    private String regTime;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscoveryService that = (DiscoveryService) o;
        return name.equals(that.name) &&
                host.equals(that.host) &&
                port.equals(that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, host, port);
    }
}
