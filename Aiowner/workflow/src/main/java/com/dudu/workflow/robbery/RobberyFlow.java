package com.dudu.workflow.robbery;

import com.dudu.aiowner.commonlib.config.SharedPreferencesUtils;

/**
 * Created by Administrator on 2016/2/17.
 *  本地保存/获取防劫模式开关状态
 */
public class RobberyFlow {
    private static final String ROBBERY_STATE = "robbery_state";
    private static final String ROBBERY_SWITCH_STATE = "robbery_switch_state";

    public static void saveRobbeyState(boolean robberyState) {
        SharedPreferencesUtils.setParam(ROBBERY_STATE, robberyState);
    }

    public static Boolean getRobbeyState() {
        return Boolean.valueOf(SharedPreferencesUtils.getParam(ROBBERY_STATE, Boolean.FALSE).toString());
    }

    public static void saveRobbeySingleState(Boolean robberyState, int type) {
        SharedPreferencesUtils.setParam(ROBBERY_SWITCH_STATE+type, robberyState);
    }

    public static Boolean getRobbeySingleState() {
        return Boolean.valueOf(SharedPreferencesUtils.getParam(ROBBERY_SWITCH_STATE, Boolean.FALSE).toString());
    }
}
