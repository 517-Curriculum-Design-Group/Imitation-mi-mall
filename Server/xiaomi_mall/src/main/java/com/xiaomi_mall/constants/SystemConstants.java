package com.xiaomi_mall.constants;

/**
 * 系统常量
 *
 * @author HP
 * @date 2023/03/08
 */
//实际项目中都不允许直接在代码中使用字面值。都需要定义成常量来使用。这种方式有利于提高代码的可维护性。
public class SystemConstants {
    /**
     *  用户名不重复
     */
    public static final int USERNAME_NOT_EXIT = 0;
    /**
     *  用户昵称不重复
     */
    public static final int NICKNAME_NOT_EXIT = 0;

    /**
     * 分类的状态正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 文章评论的根id
     */
    public static final int ROOT_ID = -1;

    /**
     * 用户状态正常
     */
    public static final int USER_STATUS_NORMAL = 0;

    /**
     * 用户状态错误
     */
    public static final int USER_STATUS_ERROR = 1;
    /**
     * 分类可以删除
     */
    public static final int CATE_CAN_DELETE = 0;
    /**
     * 默认地址
     */
    public static final int DEFAULT_ADDRESS = 1;
    /**
     * 不是默认地址
     */
    public static final int NOT_DEFAULT_ADDRESS = 0;
}