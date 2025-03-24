package com.manchung.grouproom.repository;

import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.AvailableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
