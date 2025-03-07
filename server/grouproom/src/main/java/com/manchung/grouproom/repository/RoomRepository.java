package com.manchung.grouproom.repository;

import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.AvailableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    // ✅ 사용 가능한 방 조회
    List<Room> findByAvailableStatus(AvailableStatus availableStatus);

    // ✅ 특정 층의 방 조회
    List<Room> findByFloor(String floor);

}
