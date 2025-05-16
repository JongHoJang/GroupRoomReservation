import React from 'react'
import { Room } from '@/types/room'
import { Dispatch, SetStateAction } from 'react'
import RoomCard from '@/app/(feat)/_component/RoomCard'

interface RoomListContainerProps {
  rooms: Room[]
  setSelectedRoom?: Dispatch<SetStateAction<Room | null>>
  selectedRoom?: Room | null
  clickable?: boolean
}

const RoomListContainer: React.FC<RoomListContainerProps> = ({
  rooms,
  setSelectedRoom,
  selectedRoom,
  clickable = false,
}) => {
  const floorList = Array.from(new Set(rooms?.map(room => room.floor))).sort()

  return (
    <div className="space-y-8">
      {floorList.map(floor => {
        const roomsOnFloor = rooms.filter(room => room.floor === floor)
        return (
          <div key={floor}>
            <h2 className="text-lg font-bold mb-4 md:mb-2 justify-center flex md:justify-start">
              {floor}
            </h2>
            {/*<div className="flex flex-wrap gap-4">*/}
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-5 gap-4">
              {roomsOnFloor?.map(room => (
                <RoomCard
                  key={room.roomId}
                  room={room}
                  isSelected={room.roomId === selectedRoom?.roomId}
                  clickable
                  onClick={
                    clickable && setSelectedRoom
                      ? () =>
                          setSelectedRoom(prev =>
                            prev?.roomId === room.roomId ? null : room
                          )
                      : undefined
                  }
                />
              ))}
            </div>
          </div>
        )
      })}
    </div>
  )
}

export default RoomListContainer
