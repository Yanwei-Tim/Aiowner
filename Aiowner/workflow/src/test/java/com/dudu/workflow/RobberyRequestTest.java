package com.dudu.workflow;

import com.dudu.workflow.robbery.RobberyRequest;
import com.dudu.workflow.robbery.RobberyRequestRetrofitImpl;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2016/2/15.
 */
public class RobberyRequestTest {

    @Test
    public void test_robberySwitch() throws InterruptedException {

        final CountDownLatch signal = new CountDownLatch(1);
        RequestFactory.getInstance().getRobberyRequest()
                .robberySwitch("13800138000", 0, 1, new RobberyRequest.SwitchCallback() {
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
        RobberyRequestRetrofitImpl.getInstance().getRobberyState("13800138000", new RobberyRequest.RobberStateCallback() {

            @Override
            public void switchsState(boolean robbery, boolean flashRateTimes, boolean emergencyCutoff, boolean stepOnTheGas) {
                signal.countDown();
                assertTrue(robbery);
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
