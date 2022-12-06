package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.RoomCleanResponse;
import model.dto.other.RoomEntity;
import model.dto.other.RoomStatusResponse;
import model.dto.other.SearchRoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper extends BaseMapper<RoomEntity> {
    List<SearchRoomDto> selectRooms(RoomEntity roomEntity);

    List<RoomStatusResponse> selectRoomStatus(RoomEntity roomEntity);

    List<RoomCleanResponse> roomCleanDetail(@Param("roomId") Integer roomId);

}
