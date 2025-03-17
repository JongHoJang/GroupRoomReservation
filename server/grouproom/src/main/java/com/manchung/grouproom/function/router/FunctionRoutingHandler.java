package com.manchung.grouproom.function.router;

import lombok.AllArgsConstructor;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class FunctionRoutingHandler {
    private final FunctionCatalog functionCatalog;

    public Object routeFunction(Map<String, Object> request) {
        String functionName = (String) request.get("functionName");
        Object input = request.get("input");

        // ✅ FunctionCatalog에서 해당 Function 조회
        Function<Object, Object> function = functionCatalog.lookup(Function.class, functionName);

        if (function == null) {
            throw new IllegalArgumentException("Function not found: " + functionName);
        }

        // ✅ 요청된 Function 실행
        return function.apply(input);
    }
}
