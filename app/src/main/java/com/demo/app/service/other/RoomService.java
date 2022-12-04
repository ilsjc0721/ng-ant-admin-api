package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.other.RoomMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomMapper roomMapper;

    public Result listRoom(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        RoomEntity searchRoomEntity = new RoomEntity();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchRoomEntity = getSearchRoomEntity(searchFilter.getFilters());
        }

        List<SearchRoomDto> roomList = roomMapper.selectRooms(searchRoomEntity);

        PageInfo<SearchRoomDto> selectRoomPageInfo = new PageInfo<>(roomList);
        return Result.success(selectRoomPageInfo);
    }

    public Result listRoomStatus(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        RoomEntity searchRoomEntity = new RoomEntity();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchRoomEntity = getSearchRoomEntity(searchFilter.getFilters());
        }

        List<RoomStatusResponse> roomList = roomMapper.selectRoomStatus(searchRoomEntity);

        PageInfo<RoomStatusResponse> selectRoomPageInfo = new PageInfo<>(roomList);
        return Result.success(selectRoomPageInfo);
    }

    public Result roomDetail(Integer id) {
        RoomEntity searchRoomEntity = new RoomEntity();
        searchRoomEntity.setId(id);
        List<SearchRoomDto> customerList = roomMapper.selectRooms(searchRoomEntity);
        Optional<SearchRoomDto> room = customerList.stream().findFirst();
        RoomResponse roomResponse = new RoomResponse();
        if (room.isPresent()){
            BeanUtils.copyProperties(room.get(), roomResponse);
            return Result.success(preparePriceDetail(roomResponse));
        } else {
            return Result.success(roomResponse);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insertRoom(InsertRoom insertRoom) {
        // 驗證身分證號是否重複
        if (isUniqueName(insertRoom.getName())) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_ADD_ROOM_DUPLICATE);
        }

        RoomEntity room = new RoomEntity();
        BeanUtils.copyProperties(insertRoom, room);
        int res = roomMapper.insert(room);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        }
        return Result.success();
    }

    private boolean isUniqueName(String name) {
        QueryWrapper<RoomEntity> nameQuery = new QueryWrapper<RoomEntity>().eq("name", name);
        List<RoomEntity> RoomEntitys = roomMapper.selectList(nameQuery);
        return CollectionUtil.isNotEmpty(RoomEntitys);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateRoom(UpdateRoom updateRoom) {
        RoomEntity room = new RoomEntity();
        BeanUtils.copyProperties(updateRoom, room);

        int res = roomMapper.updateById(room);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result delRoom(BatchDeleteDto batchDeleteDto) {
        roomMapper.deleteBatchIds(batchDeleteDto.getIds());
        return Result.success();
    }

    private RoomEntity getSearchRoomEntity(JSONObject jsonObject) {
        RoomEntity searchRoomEntity = new RoomEntity();
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");
        Integer id = jsonObject.getInteger("id");

        if (Objects.nonNull(name)) {
            searchRoomEntity.setName(name);
        }
        if (Objects.nonNull(status)) {
            searchRoomEntity.setStatus(status);
        }
        if (Objects.nonNull(id)) {
            searchRoomEntity.setId(id);
        }

        return searchRoomEntity;
    }

    private RoomResponse preparePriceDetail(RoomResponse roomResponse) {
        List<PriceEntity> roomPriceList = new ArrayList<>();
        PriceEntity roomPrice1 =new PriceEntity();
        roomPrice1.setName("原價");
        roomPrice1.setPrice(roomResponse.getRoomPrice1());
        roomPriceList.add(roomPrice1);
        PriceEntity roomPrice2 =new PriceEntity();
        roomPrice2.setName("優惠價");
        roomPrice2.setPrice(roomResponse.getRoomPrice2());
        roomPriceList.add(roomPrice2);
        PriceEntity roomPrice3 =new PriceEntity();
        roomPrice3.setName("平日");
        roomPrice3.setPrice(roomResponse.getRoomPrice3());
        roomPriceList.add(roomPrice3);
        PriceEntity roomPrice4 =new PriceEntity();
        roomPrice4.setName("假日");
        roomPrice4.setPrice(roomResponse.getRoomPrice4());
        roomPriceList.add(roomPrice4);
        roomResponse.setRoomPrice(roomPriceList);

        List<PriceEntity> restPriceList = new ArrayList<>();
        PriceEntity restPrice1 =new PriceEntity();
        restPrice1.setName("原價");
        restPrice1.setPrice(roomResponse.getRestPrice1());
        restPriceList.add(restPrice1);
        PriceEntity restPrice2 =new PriceEntity();
        restPrice2.setName("優惠價");
        restPrice2.setPrice(roomResponse.getRestPrice2());
        restPriceList.add(restPrice2);
        PriceEntity restPrice3 =new PriceEntity();
        restPrice3.setName("平日");
        restPrice3.setPrice(roomResponse.getRestPrice3());
        restPriceList.add(restPrice3);
        PriceEntity restPrice4 =new PriceEntity();
        restPrice4.setName("假日");
        restPrice4.setPrice(roomResponse.getRestPrice4());
        restPriceList.add(restPrice4);
        roomResponse.setRestPrice(restPriceList);

        List<PriceEntity> overtimePriceList = new ArrayList<>();
        PriceEntity overtimePrice1 =new PriceEntity();
        overtimePrice1.setName("原價");
        overtimePrice1.setPrice(roomResponse.getOvertimePrice1());
        overtimePriceList.add(overtimePrice1);
        PriceEntity overtimePrice2 =new PriceEntity();
        overtimePrice2.setName("優惠價");
        overtimePrice2.setPrice(roomResponse.getOvertimePrice2());
        overtimePriceList.add(overtimePrice2);
        PriceEntity overtimePrice3 =new PriceEntity();
        overtimePrice3.setName("平日");
        overtimePrice3.setPrice(roomResponse.getOvertimePrice3());
        overtimePriceList.add(overtimePrice3);
        PriceEntity overtimePrice4 =new PriceEntity();
        overtimePrice4.setName("假日");
        overtimePrice4.setPrice(roomResponse.getOvertimePrice4());
        overtimePriceList.add(overtimePrice4);
        roomResponse.setOvertimePrice(overtimePriceList);
        return roomResponse;
    }
}
