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
        if (room.isPresent()){
            return Result.success(room.get());
        } else {
            return Result.success(new RoomEntity());
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
}
