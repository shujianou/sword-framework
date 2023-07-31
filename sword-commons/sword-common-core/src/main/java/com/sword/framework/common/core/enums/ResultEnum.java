package com.sword.framework.common.core.enums;


import com.sword.framework.common.core.bean.IResultEnum;

/**
 * 业务异常信息
 *
 * @author shujian.ou
 * @since 2022/12/13 11:23
 */
public enum ResultEnum implements IResultEnum {

    /**
     * 无权限
     */
    NO_PERMISSION(403, "global.noPermission"),

    OPERATION_SUCCESS(ResultEnum.SUCCESS_CODE, "global.operationSuccess"),
    SYSTEM_BUSY(ResultEnum.FAILED_CODE, "global.systemBusy"),

    OPERATION_FAIL(ResultEnum.FAILED_CODE, "global.operationFail"),

    PARAM_CANNOT_DEL(20001, "global.paramCannotDel"),

    INVALID_FILENAME(20002, "global.invalid.fileName"),

    INVALID_RESOURCE(20003, "global.invalid.resource"),


    VISIT_OFTEN(20004, "global.visitOften"),

    SERVER_LIMIT_EXCEPTION(20005, "global.serverLimitException"),

    ILLEGAL_REQUEST(ResultEnum.DEFAULT_ERROR_CODE, "global.illegalRequest"),

    ENCRYPT_VERSION_ERROR(ResultEnum.DEFAULT_ERROR_CODE, "global.encrypt.version.error"),

    PARAM_FORMAT_ERROR(ResultEnum.DEFAULT_ERROR_CODE, "global.param.format.error"),

    REJECT_SERVICE(400, "global.reject.service"),

    ACCOUNT_SQUEEZE_UP(204, "global.account.squeezeUp"),

    ACCOUNT_KICK_DOWN(205, "global.account.tickDown"),

    ACCOUNT_BANED(206, "global.account.baned"),

    ACCOUNT_CANCEL(207, "global.account.cancel"),

    OPERATE_OVER_SPEED(ResultEnum.DEFAULT_ERROR_CODE, "global.operate.overSpeed"),

    SMS_SEND_SUCCESS(ResultEnum.SUCCESS_CODE, "global.sms.sendSuccess"),

    /**
     * 系统繁忙
     */
    SYSTEM_ERROR(ResultEnum.DEFAULT_ERROR_CODE, "global.systemError"),
    ;

    private final int code;

    private final String msgKey;

    public static final int DEFAULT_ERROR_CODE = 500;

    public static final int SUCCESS_CODE = 200;

    public static final int FAILED_CODE = 201;

    ResultEnum(int code, String msgKey) {
        this.code = code;
        this.msgKey = msgKey;
    }

    ResultEnum(String msgKey) {
        this.code = ResultEnum.DEFAULT_ERROR_CODE;
        this.msgKey = msgKey;
    }

    @Override
    public String getMsgKey() {
        return msgKey;
    }

    @Override
    public int getCode() {
        return code;
    }

}
