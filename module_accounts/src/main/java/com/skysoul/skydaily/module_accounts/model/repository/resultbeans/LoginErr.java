package com.skysoul.skydaily.module_accounts.model.repository.resultbeans;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author skysoul
 * @date 2020/8/4
 * @description 这个转成kotlin 不能约束参数，先只用java
 */
@IntDef({LoginErr.noErr, LoginErr.noTryTimes,LoginErr.noUser,LoginErr.passWordWrong})
@Retention(RetentionPolicy.SOURCE)
public @interface LoginErr {
    public static final int noErr = 0;
    public static final int noUser = -1;
    public static final int noTryTimes = -2;
    public static final int passWordWrong = -3;
}


