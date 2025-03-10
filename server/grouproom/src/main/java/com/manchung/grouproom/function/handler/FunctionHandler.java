package com.manchung.grouproom.function.handler;

import com.manchung.grouproom.function.router.FunctionRouter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

@Component
@AllArgsConstructor
public class FunctionHandler implements RequestHandler<Map<String, Object>, Object> {
    private final FunctionRouter functionRouter;

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        return functionRouter.routeFunction(input);
    }
}