package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    String Port;
    String Memory_Limit;
    String CF_Instance_Index;
    String CF_Instance_Addr;

    public EnvController(
            @Value("${PORT:UNSET}") String Port,
            @Value("${MEMORY_LIMIT:UNSET}") String MemLim,
            @Value("${CF_INSTANCE_INDEX:UNSET}") String CF_InsIndex,
            @Value("${CF_INSTANCE_ADDR:UNSET}") String CF_InsAddr
    ) {
        this.Port = Port;
        this.Memory_Limit = MemLim;
        this.CF_Instance_Index = CF_InsIndex;
        this.CF_Instance_Addr = CF_InsAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> map = new HashMap<>();

        map.put("PORT", this.Port);
        map.put("MEMORY_LIMIT", this.Memory_Limit);
        map.put("CF_INSTANCE_INDEX", this.CF_Instance_Index);
        map.put("CF_INSTANCE_ADDR", this.CF_Instance_Addr);

        return map;
    }
}
