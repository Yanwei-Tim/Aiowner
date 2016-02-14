package com.dudu.aiowner;

import com.dudu.aiowner.utils.RegisterVerifyUtils.RegisterVerify;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.user.UserRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2016/2/13.
 */
public class RegisterTest {
    String cellphone = "13113131313";
    String securityCode = "010101";
    String password = "123456";
    String method = "mothod";
    String messageId = "123";
    String type = "register";

    public UserRequest userRequest;

    @Before
    public void setUp() {
        userRequest = RequestFactory.getUserRequestTestImpl();
    }

    @Test
    public void test_isPhoneNumberValid() {
        assertTrue(RegisterVerify.isPhoneNumberValid(cellphone));
    }

    @Test
    public void test_requestVerifyCode() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userRequest.requestVerifyCode(cellphone, new UserRequest.RequestVerifyCodeCallback() {
            @Override
            public void requestVerifyCodeResult(boolean success) {
                signal.countDown();
                assertTrue(success);
            }
        });
        signal.await();
    }

    @Test
    public void test_isVerifyCodeValid() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userRequest.isVerifyCodeValid(cellphone, securityCode, new UserRequest.VerifyCodeValidCallback() {
            @Override
            public void verifyCodeIsValid(boolean success) {
                signal.countDown();
                assertTrue(success);
            }
        });
        signal.await();
    }

    public boolean checkPasswordLength(String password) {
        return !(password.length() < 6);
    }

    @Test
    public void test_isPasswordValid() {
        String password = "123456";
        String password2 = "123456";
        assertTrue(checkPasswordLength(password));
        assertEquals(password2, password);
    }

    @Test
    public void test_settingPassword() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userRequest.settingPassword(cellphone, securityCode, password, new UserRequest.RegisterCallback() {
            @Override
            public void registerSuccess(boolean success) {
                signal.countDown();
                assertTrue(success);
            }
        });
        signal.await();
    }
}
