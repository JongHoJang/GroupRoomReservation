package com.manchung.grouproom.repository.mock;

import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.AvailableStatus;
import com.manchung.grouproom.entity.enums.SittingType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MockRoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    public MockRoomRepository() {
        // ✅ 초기 방 데이터 추가
        rooms.add(new Room(1, "101호", "1층", 10, 2, AvailableStatus.AVAILABLE, SittingType.CHAIR));
        rooms.add(new Room(2, "102호", "1층", 8, 1, AvailableStatus.AVAILABLE, SittingType.FLOOR));
        rooms.add(new Room(3, "103호", "1층", 12, 3, AvailableStatus.AVAILABLE, SittingType.CHAIR));
        rooms.add(new Room(4, "201호", "2층", 15, 4, AvailableStatus.UNAVAILABLE, SittingType.FLOOR));
        rooms.add(new Room(5, "202호", "2층", 6, 1, AvailableStatus.AVAILABLE, SittingType.CHAIR));
        rooms.add(new Room(6, "203호", "2층", 20, 5, AvailableStatus.AVAILABLE, SittingType.FLOOR));
        rooms.add(new Room(7, "301호", "3층", 10, 2, AvailableStatus.AVAILABLE, SittingType.CHAIR));
        rooms.add(new Room(8, "302호", "3층", 18, 3, AvailableStatus.UNAVAILABLE, SittingType.FLOOR));
        rooms.add(new Room(9, "303호", "3층", 8, 2, AvailableStatus.AVAILABLE, SittingType.CHAIR));
        rooms.add(new Room(10, "304호", "3층", 25, 6, AvailableStatus.AVAILABLE, SittingType.FLOOR));
    }

    public List<Room> findAll() {
        return rooms;
    }

    public Optional<Room> findById(Integer roomId) {
        return rooms.stream().filter(room -> room.getRoomId().equals(roomId)).findFirst();
    }

    public List<Room> findByAvailableStatus(AvailableStatus status) {
        return rooms.stream().filter(room -> room.getAvailableStatus().equals(status)).toList();
    }
}
