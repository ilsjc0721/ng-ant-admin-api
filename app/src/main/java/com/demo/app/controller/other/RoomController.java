package com.demo.app.controller.other;

import com.demo.app.service.other.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "RoomController",tags = "房間管理")
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @PostMapping("/list")
    @ApiOperation(value = "取得房間清單")
    public Result listRoom(@RequestBody @Validated SearchFilter searchFilter) {
        return roomService.listRoom(searchFilter);
    }

    @PostMapping("/list/status")
    @ApiOperation(value = "取得房間狀態清單")
    public Result listRoomStatus(@RequestBody @Validated SearchFilter searchFilter) {
        return roomService.listRoomStatus(searchFilter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "取得房間明細")
    public Result roomDetail(@PathVariable Integer id) {
        return roomService.roomDetail(id);
    }

    @PostMapping
    @ApiOperation(value = "新增房間")
//    @PreAuthorize("@ss.hasPer('default:system:room:add')")
    public Result insertRoom(@RequestBody @Validated InsertRoom room) {
        return roomService.insertRoom(room);
    }

    @PutMapping
    @ApiOperation(value = "修改房間明細")
//    @PreAuthorize("@ss.hasPer('default:system:room:edit')")
    public Result updateRoom(@RequestBody @Validated UpdateRoom room) {
        return roomService.updateRoom(room);
    }



    @PostMapping("/del")
    @ApiOperation(value = "刪除房間")
//    @PreAuthorize("@ss.hasPer('default:system:room:del')")
    public Result delRoom(@RequestBody @Validated BatchDeleteDto batchDeleteDto) {
        return roomService.delRoom(batchDeleteDto);
    }

    @PutMapping("/clean")
    @ApiOperation(value = "設定房間清潔開始")
//    @PreAuthorize("@ss.hasPer('default:system:room:edit')")
    public Result setRoomCleanStart(@RequestBody @Validated SetRoomClean setRoomClean) {
        return roomService.setRoomClean(setRoomClean);
    }

    @PutMapping("/clean/end")
    @ApiOperation(value = "設定房間清潔結束")
//    @PreAuthorize("@ss.hasPer('default:system:room:edit')")
    public Result setRoomCleanEnd(@RequestBody @Validated SetRoomCleanEnd setRoomCleanEnd) {
        return roomService.setRoomCleanEnd(setRoomCleanEnd);
    }
    @GetMapping("/clean/{id}")
    @ApiOperation(value = "取得房間清潔明細")
//    @PreAuthorize("@ss.hasPer('default:system:room:edit')")
    public Result roomCleanDetail(@PathVariable Integer id) {
        return roomService.roomCleanDetail(id);
    }

    @PostMapping("/clean/report")
    @ApiOperation(value = "帳款報表")
//    @PreAuthorize("@ss.hasPer('default:system:customer:report')")
    public Result report(@RequestBody @Validated SearchFilter searchFilter) {
        return roomService.report(searchFilter);
    }

}
