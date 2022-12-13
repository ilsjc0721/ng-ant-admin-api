package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper extends BaseMapper<RoomEntity> {
    List<SearchRoomDto> selectRooms(RoomEntity roomEntity);

    List<RoomStatusResponse> selectRoomStatus(RoomEntity roomEntity);

    List<RoomCleanResponse> roomCleanDetail(@Param("roomId") Integer roomId);

    List<ReportResponse> selectReport(SearchReportDto searchReportDto);

    Integer updateRoomCheckOut(@Param("roomId") Integer roomId);
}
