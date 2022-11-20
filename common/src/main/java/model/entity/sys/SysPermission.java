package model.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 【请填写功能名称】对象 sys_permission_up
 *
 * @author fbl
 * @date 2022-03-31
 */
@Data
@TableName("sys_permission")
public class SysPermission {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("menu_name")
    private String menuName;

    @TableField("code")
    private String code;

    @TableField("father_id")
    private Integer fatherId;

    @TableField("order_num")
    private Integer orderNum;

    @TableField("path")
    private String path;
    /**
     * （M 目錄 C menu F button）
     */
    @TableField("menu_type")
    private String menuType;

    @TableField("visible")
    private Boolean visible;

    @TableField("status")
    private Boolean status;

    @TableField("icon")
    private String icon;

    @TableField("is_new_link")
    private Boolean newLinkFlag;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp updateTime;

}
