package com.dudu.workflow.guard;

import com.dudu.aiowner.commonlib.commonutils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2016/2/17.
 * 本地保存/获取防盗模式开关状态
 */
public class GuardFlow {
    private static final String GUARD_STATE = "guard_state";

    public static void saveGuardState(Boolean guardState) {
        SharedPreferencesUtils.setParam(GUARD_STATE, guardState);
    }

    public static Boolean getGuardState() {
        return Boolean.valueOf(SharedPreferencesUtils.getParam(GUARD_STATE, Boolean.FALSE).toString());
    }
}
