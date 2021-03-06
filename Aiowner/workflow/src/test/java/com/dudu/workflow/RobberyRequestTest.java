package com.dudu.workflow;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.robbery.RobberyRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2016/2/15.
 */
public class RobberyRequestTest {

    @Before
    public void setUp() {
        CommonLib.getInstance().init();
//        FlowFactory.getInstance().init();
//        CommonParams.getInstance().init()
        RequestFactory.getInstance().init();
        Request.getInstance().init();
    }

    @Test
    public void test_isCarRobbed() throws InterruptedException {

        final CountDownLatch signal = new CountDownLatch(1);
        RequestFactory.getInstance().getRobberyRequest()
                .isCarRobbed(new RobberyRequest.CarRobberdCallback() {

            @Override
            public void hasRobbed(boolean success) {
                signal.countDown();
                assertTrue(success);
            }

            @Override
            public void requestError(String error) {
                signal.countDown();
                assertNull(error);
            }
        });
        signal.await();
    }
    @Test
    public void test_robberySwitch() throws InterruptedException {

        final CountDownLatch signal = new CountDownLatch(1);
        RequestFactory.getInstance().getRobberyRequest()
                .settingAntiRobberyMode(0, 1, new RobberyRequest.SwitchCallback() {
            @Override
            public void switchSuccess(boolean success) {
                signal.countDown();
                assertTrue(success);
            }

            @Override
            public void requestError(String error) {
                signal.countDown();
                assertNull(error);
            }
        });
        signal.await();
    }

    @Test
    public void test_getRobberyState() throws InterruptedException {

        final CountDownLatch signal = new CountDownLatch(1);
        RequestFactory.getInstance().getRobberyRequest()
                .getRobberyState(new RobberyRequest.RobberStateCallback() {

            @Override
            public void switchsState(boolean flashRateTimes, boolean emergencyCutoff, boolean stepOnTheGas) {
                signal.countDown();
                assertFalse(flashRateTimes);
                assertTrue(emergencyCutoff);
                assertFalse(stepOnTheGas);
            }

            @Override
            public void requestError(String error) {
                signal.countDown();
                assertNull(error);
            }
        });
        signal.await();
    }
}
