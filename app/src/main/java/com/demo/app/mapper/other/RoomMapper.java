package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.RoomEntity;
import model.dto.other.RoomStatusResponse;
import model.dto.other.SearchRoomDto;

import java.util.List;

public interface RoomMapper extends BaseMapper<RoomEntity> {
    List<SearchRoomDto> selectRooms(RoomEntity roomEntity);

    List<RoomStatusResponse> selectRoomStatus(RoomEntity roomEntity);
}
