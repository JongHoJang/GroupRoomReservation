package com.manchung.grouproom.entity;

import com.manchung.grouproom.entity.enums.AvailableStatus;
import com.manchung.grouproom.entity.enums.SittingType;
import com.manchung.grouproom.entity.enums.converter.AvailableStatusConverter;
import com.manchung.grouproom.entity.enums.converter.SittingTypeConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "name", length = 50, nullable = false)
    private String name; // 방이름 (ex. 101호)

    @Column(name = "floor", length = 50, nullable = false)
    private String floor;

    @Column(name = "person_affordable_count", nullable = false)
    private Integer personAffordableCount;

    @Column(name = "group_affordable_count", nullable = false)
    private Integer groupAffordableCount;

    @Convert(converter = AvailableStatusConverter.class)
    @Column(name = "available_status", nullable = false)
    private AvailableStatus availableStatus;

    @Convert(converter = SittingTypeConverter.class)
    @Column(name = "sitting_type", nullable = false)
    private SittingType sittingType;
}
