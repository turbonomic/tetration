package com.vmturbo.tetration.simulator.service;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Flow {
    @SerializedName("src_address")
    private final String srcAddress;

    @SerializedName("dst_address")
    private final String dstAddress;

    @SerializedName("proto")
    private final String proto;

    @SerializedName("dst_port")
    private final int dstPort;

    @SerializedName("fwd_bytes")
    private final double fwdBytes;

    @SerializedName("rev_bytes")
    private final double revBytes;

    @SerializedName("total_network_latency_usec")
    private final long totalNetworkLatencyUsec;

    @SerializedName("timestamp")
    private final Date timestamp;

    /**
     * Construct {@link Flow} instance.
     *
     * @param srcAddress              source IP address
     * @param dstAddress              destination IP address
     * @param proto                   protocol
     * @param dstPort                 destination port
     * @param fwdBytes                forwarded bytes
     * @param revBytes                received bytes
     * @param totalNetworkLatencyUsec total network latency
     */
    Flow(String srcAddress, String dstAddress, String proto,
         int dstPort, double fwdBytes, double revBytes,
         long totalNetworkLatencyUsec, Date timestamp) {
        this.srcAddress = srcAddress;
        this.dstAddress = dstAddress;
        this.proto = proto;
        this.dstPort = dstPort;
        this.fwdBytes = fwdBytes;
        this.revBytes = revBytes;
        this.totalNetworkLatencyUsec = totalNetworkLatencyUsec;
        this.timestamp = timestamp;
    }

    /**
     * Get source IP address.
     *
     * @return source IP address
     */
    @SuppressWarnings("unused")
    public String getSrcAddress() {
        return srcAddress;
    }

    /**
     * Get destination IP address.
     *
     * @return destination IP address
     */
    @SuppressWarnings("unused")
    public String getDstAddress() {
        return dstAddress;
    }

    /**
     * Get protocol.
     *
     * @return protocol
     */
    @SuppressWarnings("unused")
    public String getProto() {
        return proto;
    }

    /**
     * Get destination port.
     *
     * @return destination port
     */
    @SuppressWarnings("unused")
    public int getDstPort() {
        return dstPort;
    }

    /**
     * Get forwarded bytes.
     *
     * @return forwarded bytes
     */
    @SuppressWarnings("unused")
    public double getFwdBytes() {
        return fwdBytes;
    }

    /**
     * Get receive bytes.
     *
     * @return receive bytes
     */
    @SuppressWarnings("unused")
    public double getRevBytes() {
        return revBytes;
    }

    /**
     * Get total network latency.
     *
     * @return total network latency
     */
    @SuppressWarnings("unused")
    public long getTotalNetworkLatencyUsec() {
        return totalNetworkLatencyUsec;
    }

    /**
     * Get timestamp in seconds.
     *
     * @return timestamp in seconds
     */
    @SuppressWarnings("unused")
    public long getTimestampInSeconds() {
        return timestamp.getTime() / 1000;
    }
}
