package dudu.workflow;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.dudu.workflow.robbery.RobberyRequest;
import com.dudu.workflow.robbery.RobberyRequestRetrofitImpl;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @LargeTest
    public void test_robberySwitch() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        RobberyRequestRetrofitImpl.getInstance().robberySwitch("18520339890", 0, 1, new RobberyRequest.SwitchCallback() {
            @Override
            public void switchSuccess(boolean success) {
                signal.countDown();
                assertTrue(false);
            }

            @Override
            public void requestError(String error) {
                signal.countDown();
                assertNotNull(error);
            }
        });
        signal.await();
    }
}