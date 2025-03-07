package com.manchung.grouproom.function.handler;

import com.manchung.grouproom.function.response.RoomWithReservationResponse;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import java.util.List;

public class RoomWithReservationHandler extends SpringBootRequestHandler<Void, List<RoomWithReservationResponse>> {
}
