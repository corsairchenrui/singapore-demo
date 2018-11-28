package com.example.singaporedemo;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AService {
    @Autowired
    TrCodeMapper trCodeMapper;

    public String invoke(String trCode, String version, String body) throws Exception {

        BoeingTx tx = trCodeMapper.getTx(trCode);
        String roota = tx.getRoota();
        String rootb = tx.getRootb();
        Map<String, String> invokerMap = JsonFlattener.flattenAsMap(body)
                .entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("data.")&&!StringUtils.isEmpty(entry.getValue()))
                .collect(Collectors.toMap(entry -> entry.getKey().replaceFirst("data", roota),
                        entry -> entry.getValue().toString()));
        Map<String, String> result = new HashMap<>();
        result.put("COA","error");
        result.put("EBMFOPC7_O.field1","111");
        result.put("EBMFOPC7_O.field2","222  ");
        result.put("EBMFOPC7_O.field3","   \0\0\0");
        result.put("EBMFOPC7_O.field4.a","aa");
        result.put("EBMFOPC7_O.field4.b","bb");
        result.put("EBMFOPC7_O.field4.c","cc");


//        if(result.get("COA").equals("error")) {
//            throw new Exception("error");
//        }
        String jsonString = result.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(rootb)&&!StringUtils.isEmpty(entry.getValue()))
                .map(entry -> {
                    String key = entry.getKey().replaceFirst(rootb+".", "");
                    String value = entry.getValue().replaceAll("\0", "").trim();
                    if(StringUtils.isEmpty(value))return "";
                    return MessageFormat.format("\"{0}\":\"{1}\"", key, value);
                }).reduce((s0, s1) -> s0 + "," + s1).get();
        if(jsonString.endsWith(","))jsonString = jsonString.substring(0, jsonString.length()-1);
        jsonString = "{"+jsonString+"}";
//                .collect(Collectors.toMap(
//                        entry -> ((Map.Entry<String, String>)entry).getKey().replaceFirst(rootb+".", ""),
//                        entry -> ((Map.Entry<String, String>)entry).getValue().replaceAll("\0", "").trim()
//                ));
        return JsonUnflattener.unflatten(jsonString);
    }

}
