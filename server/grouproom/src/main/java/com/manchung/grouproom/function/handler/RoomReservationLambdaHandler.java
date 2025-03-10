package com.manchung.grouproom.function.handler;

import com.manchung.grouproom.function.request.RoomReservationRequest;
import com.manchung.grouproom.function.response.RoomReservationResponse;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

public class RoomReservationLambdaHandler extends SpringBootRequestHandler<RoomReservationRequest, RoomReservationResponse> {
}
