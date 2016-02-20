package com.dudu.workflow;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.driving.DrivingRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2016/2/17.
 */
public class DrivingRequestTest {

    private DrivingRequest request;

    @Before
    public void setUp() {
        CommonLib.getInstance().init();
//        FlowFactory.getInstance().init();
//        CommonParams.getInstance().init();
        RequestFactory.getInstance().init();
        Request.getInstance().init();
        request = RequestFactory.getDrivingRequest();
    }

    @Test
    public void test_requestAccTestStart() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        request.startacceleratedTesting(1, new DrivingRequest.RequestCallback() {
            @Override
            public void requestSuccess(boolean success) {
                signal.countDown();
                assertTrue(success);
            }
        });
        signal.await();
    }
}
