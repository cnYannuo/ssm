package com.yn.common.entity;

public enum ErrorCode {

    SQL_ERROR("SQL-000", "数据库操作错误"),
    SQL_INVALID("SQL-001", "数据库SQL无效"),
    SQL_OPTIMISTIC_LOCK_ERROR("SQL-002", "当前更新的数据已被其他人更新, 请刷新数据重新处理"),

    SQL_TYPE_NOT_ITERABLE_ERROR("SQL-003", "数据类型错误, 必须为Iterable的实现类型"),
    SQL_TYPE_NOT_BETWEEN_ERROR("SQL-004", "数据类型错误, 必须为BetweenPair/Map的类型"),

    SQL_OPERATOR_ERROR("SQL-005", "操作符处理错误"),

    SQL_OPTIMISTIC_LOCKED_FAIL("SQL-006", "乐观锁检查错误，您的数据可能已被其他操作员修改过，请刷新数据重新修改"),
    SQL_INVALID_COLUMN("SQL-007", "无效列"),
    //登陆错误
    /**
     * 登录被挤下线
     */
    SESSION_CONCURRENT_LOGIN("AUTH-001", "您的账号在另一设备登录，您被迫下线。"),
    /**
     * 被踢下线
     */
    SESSION_KICKOUT_BY_MANAGER("AUTH-002", "会话已过期，请重新登录。"),
    /**
     * session过期
     */
    SESSION_EXPIRED("AUTH-003", "您已经下线，请重新登录。"),

    USER_NOT_EXISTS("AUTH-004", "用户不存在"),
    USER_AUTH_FAIL("AUTH-005", "用户账号或密码错误"),
    USER_NOT_ACTIVE("AUTH-006", "用户账号没有启用"),
    USER_PARAM_NOT_FILL("AUTH-007", "用户账户或密码未输入"),
    USER_NOT_AUTHORITY("AUTH-008", "用户没有权限执行这次操作"),
    USER_PASSWORD_NOT_MATCH("AUTH-009", "密码不正确"),
    USER_NOT_FOUND("AUTH-010", "没有找到用户"),
    USER_NOT_LOGIN("AUTH-011", "用户未登录"),
    USER_NOT_AUTHORITY_DATA("AUTH-012", "用户没有权限操作这些数据"),

    //系统错误
    /**
     * 参数不合法
     */
    INVALID_PARAMS("SYS-0002", "参数错误"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("SYS-0001", "服务器内部错误，请稍后再试"),
    INVALID_PAGESIZE("SYS-0003", "分页大小无效"),
    DATA_NOT_EXISTS("SYS-0004", "数据不存在"),
    DATA_TYPE_ERROR("SYS-0005", "数据类型错误"),
    DATA_EXISTS("SYS-0006", "数据已存在"),
    DATA_DIRTY("SYS-0007", "数据不是最新的状态"),
    DATA_STATUS_WRONG("SYS-0008", "数据状态错误"),
    LOCKED_BY_TX("SYS-0009", "分布式事务锁定"),
    UPDATE_DATA_EXCEPTION("SYS-0010", "数据更新失败，请稍后再试"),
    CACHE_LIMITED_EXCEPTION("SYS-0011", "缓存受限，无法存入这个对象"),
    API_CALL_EXCEPTION("SYS-0012", "API调用异常"),
    API_BASE_PARAM_INVALID("SYS-0013", "API基础参数不合法"),
    SYSTEM_OOM_ERROR("SYS-0014", "应用服务内存不足，请联系系统管理员处理"),
    SYSTEM_SOF_ERROR("SYS-0015", "应用服务堆栈错误，请联系系统管理员处理"),
    NOT_SUPPORT_ERROR("SYS-0016", "不支持的操作"),

    MICRO_SERVICE_FAILED("SYS-0017", "微服务模块调用失败"),

    SERVICE_UNAVAILABLE("SYS-0018", "服务不可用"),
    SERVICE_BAD_REQUEST("SYS-0019", "非法服务请求"),
    SERVICE_REDIRECT_REQUEST("SYS-0020", "服务请求重定向"),
    SERVICE_NOT_IMPLEMENTED("SYS-0021", "服务未实现"),
    SERVICE_BAD_GATEWAY("SYS-0022", "无效网关"),
    SERVICE_GATEWAY_TIMEOUT("SYS-0023", "网关访问超时"),
    SERVICE_SERVER_ERROR("SYS-0024", "服务调用错误"),

    SERVICE_RERUTN_DECODE_ERROR("SYS-0025", "服务调用结果解码失败"),

    SERVICE_SHORTCIRCUIT_ERROR("SYS-0026", "服务不可用，已被阻断处理"),

    SERVICE_REJECTED_THREAD_ERROR("SYS-0027", "服务不可用，服务执行线程不足"),

    SERVICE_TIMEOUT_ERROR("SYS-0028", "服务不可用，服务执行超时"),

    SERVICE_COMMAND_ERROR("SYS-0029", "服务不可用，执行失败"),

    DATA_NOT_PERMISSION("DATA_NOT_PERMISSION", "没有数据权限"),


    SEND_MQ_MESSAGE_FAIL("MQ-0001", "发送消息到 MQ 失败"),
    ;
    /**
     * 业务码
     */
    private String code;
    /**
     * 描述
     */
    private String message;

    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
    /**
     * message允许编辑
     * @param message
     * @return
     * @date 2015年11月24日 下午5:27:37
     */
    public ErrorCode setMessage(String message) {
        this.message = message;
        return this;
    }

    public static ErrorCode findByCode(String code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if(errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return null;
    }
}

