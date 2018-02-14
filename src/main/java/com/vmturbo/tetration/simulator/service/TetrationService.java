package com.vmturbo.tetration.simulator.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TetrationService {
    /**
     * The spec file name.
     */
    private static final String SPEC_FILE = "/home/turbonomic/config/flow_spec.txt";

    /**
     * The multiplication constants: KiB.
     */
    private final static double KiB = 1024.;

    /**
     * The multiplication constants: MiB.
     */
    private final static double MiB = 1024. * 1024.;

    /**
     * The multiplication constants: GiB.
     */
    private final static double GiB = 1024. * 1024. * 1024.;

    /**
     * The supported protocols.
     */
    private enum PROTOCOL {
        UDP,
        TCP
    }

    /**
     * The GSON.
     */
    private static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization()
                                                      .setPrettyPrinting().create();

    /**
     * Parses the flow amount.
     * ammount_digits[ suffix].
     * The suffix is separated by a space.
     * The amount may have suffix:
     * K - KiB
     * M - MiB
     * G - GiB
     *
     * @param amount The flow amount.
     * @return The parsed amount.
     */
    private static long parseFlowAmount(final @NotNull String amount) {
        String[] parts = amount.trim().toUpperCase().split("\\ ");
        double result = Double.parseDouble(parts[0]);
        if (parts.length == 1) {
            return Math.round(result);
        }
        switch (parts[1]) {
            case "K":
                result *= KiB;
                break;
            case "M":
                result *= MiB;
                break;
            case "G":
                result *= GiB;
                break;
        }
        return Math.round(result);
    }

    /**
     * Retrieves the t1 timestamp.
     *
     * @param request The request.
     * @return The t1 from request.
     */
    @SuppressWarnings("unchecked")
    private long getLastTS(final @NotNull String request) {
        Map<String, ?> map = (Map<String, ?>)GSON.fromJson(request, Map.class);
        String value = map.get("t1").toString();
        return Math.round(Double.parseDouble(value));
    }

    /**
     * Returns simulated response.
     *
     * @return The simulated response.
     */
    public String flowSearch(final @NotNull String request) {
        File f = new File(SPEC_FILE);
        if (!f.isFile()) {
            return "No flow definitions found.";
        }
        // The last flow time will be <= t1.
        final long requestT1 = getLastTS(request) - 1;
        final List<Flow> flowList = new ArrayList<>();
        // We do have the file.
        // Spec: src_ip -> dst_ip:dst_port, protocol, fwd_bytes, rev_bytes, latency
        try (FileInputStream fis = new FileInputStream(f);
             BufferedReader in = new BufferedReader(new InputStreamReader(fis))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                // Comment, skip
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                String[] srcDst = line.split("\\-\\>");
                if (srcDst.length != 2) {
                    throw new IOException("Invalid line " + line);
                }
                String[] flowParts = srcDst[1].split("\\,");
                // We have to have 5 parts. See comments above for details.
                if (flowParts.length != 5) {
                    throw new IOException("Invalid line " + line);
                }
                // IP:port
                String[] dst = flowParts[0].split("\\:");
                if (dst.length != 2) {
                    throw new IOException("Invalid line " + line);
                }
                final String dstIP = dst[0].trim();
                final int dstPort = Integer.parseInt(dst[1].trim());
                final PROTOCOL protocol = PROTOCOL.valueOf(flowParts[1].trim());
                Flow flow = new Flow(srcDst[0].trim(), dstIP, protocol.name(),
                                     dstPort, parseFlowAmount(flowParts[2].trim()),
                                     parseFlowAmount(flowParts[3].trim()),
                                     Integer.parseInt(flowParts[4].trim()),
                                     new Date(requestT1));
                flowList.add(flow);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        // Network flow.
        NetworkFlow networkFlow = new NetworkFlow();
        networkFlow.setResults(flowList.toArray(new Flow[flowList.size()]));
        return GSON.toJson(networkFlow);
    }
}
