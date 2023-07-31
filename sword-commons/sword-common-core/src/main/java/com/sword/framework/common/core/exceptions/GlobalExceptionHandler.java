package com.sword.framework.common.core.exceptions;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.sword.framework.common.core.enums.ResultEnum;
import com.sword.framework.common.core.bean.R;
import com.sword.framework.common.core.utils.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public R<?> handleBizException(BizException e) {
        log.error(e.getMessage(), e);
        return R.custom(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    public R<?> handleSaTokenException(SaTokenException e) {
        log.error(e.getMessage(), e);
        return R.custom(ResultEnum.NO_PERMISSION.getCode(), MessageUtils.message(ResultEnum.NO_PERMISSION));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public R<?> handleHttpMessageConversionException(HttpMessageConversionException e) {
        log.error(e.getMessage(), e);
        return R.custom(ResultEnum.ILLEGAL_REQUEST);
    }

    /**
     * 权限码异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public R<?> handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限码校验失败'{}'", requestURI, e.getMessage());
        return R.custom(ResultEnum.NO_PERMISSION);
    }

    /**
     * 角色权限异常
     */
    @ExceptionHandler(NotRoleException.class)
    public R<Void> handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',角色权限校验失败'{}'", requestURI, e.getMessage());
        return R.custom(ResultEnum.NO_PERMISSION);
    }

    /**
     * 认证失败
     */
    @ExceptionHandler(NotLoginException.class)
    public R<Void> handleNotLoginException(NotLoginException e, HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',认证失败'{}',无法访问系统资源", requestURI, e.getMessage());
        if (NotLoginException.NOT_TOKEN.equals(e.getType())
                || NotLoginException.INVALID_TOKEN.equals(e.getType())
                || NotLoginException.TOKEN_TIMEOUT.equals(e.getType())) {
            return R.custom(ResultEnum.NO_PERMISSION.getCode(), e.getMessage());
        }

        if (NotLoginException.BE_REPLACED.equals(e.getType())) {
            return R.custom(ResultEnum.ACCOUNT_SQUEEZE_UP.getCode(), MessageUtils.message(ResultEnum.ACCOUNT_SQUEEZE_UP));
        }

        if (NotLoginException.KICK_OUT.equals(e.getType())) {
            return R.custom(ResultEnum.ACCOUNT_KICK_DOWN.getCode(), MessageUtils.message(ResultEnum.ACCOUNT_KICK_DOWN));
        }
        return R.custom(ResultEnum.NO_PERMISSION);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                       HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return R.fail(e.getMessage());
    }

    /**
     * 主键或UNIQUE索引，数据重复异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Void> handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',数据库中已存在记录'{}'", requestURI, e.getMessage());
        return R.fail("数据库中已存在该记录，请联系管理员确认");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return R.custom(ResultEnum.SYSTEM_ERROR);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return R.custom(ResultEnum.SYSTEM_ERROR);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public R<?> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        return R.custom(ResultEnum.SYSTEM_ERROR.getCode(), e.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<?> constraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return R.custom(ResultEnum.DEFAULT_ERROR_CODE, e.getConstraintViolations().stream().findFirst().get().getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return R.custom(ResultEnum.SYSTEM_ERROR.getCode(), Optional.ofNullable(e.getBindingResult().getFieldError()).map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(MessageUtils.message(ResultEnum.SYSTEM_ERROR)));
    }

}
