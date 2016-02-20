package com.dudu.workflow;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2016/2/15.
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
        CommonLib.getInstance().init();
        RequestFactory.getInstance().init();
        userRequest = RequestFactory.getUserRequest();
    }

    @Test
    public void test_isPhoneNumberValid() {
        assertTrue(MultVerify.isPhoneNumberValid(cellphone));
    }

    @Test
    public void test_requestVerifyCode() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userRequest.setRequestVerifyCodeResult(true);
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
        userRequest.setVerifyCodeIsValid(true);
        userRequest.isVerifyCodeValid(cellphone, securityCode, new UserRequest.VerifyCodeValidCallback() {
            @Override
            public void verifyCodeIsValid(boolean success) {
                signal.countDown();
                assertTrue(success);
            }
        });
        signal.await();
    }

    @Test
    public void test_isPasswordValid() {
        String password = "Tt123456";
        String password2 = "Tt123456";
        assertTrue(MultVerify.isPasswordValid(password));
        assertEquals(MultVerify.isPasswordValid(password2, password),-1);
    }

    @Test
    public void test_settingPassword() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userRequest.setRegisterSuccess(true);
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
