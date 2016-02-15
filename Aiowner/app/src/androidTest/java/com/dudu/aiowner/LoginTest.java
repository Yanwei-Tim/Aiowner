package com.dudu.aiowner;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.dudu.aiowner.ui.main.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    @Test
    public void test_login_click() {
        onView(withId(R.id.home_login_btn)).perform(click());

    }

    @Test
    public void test_input() {
        onView(withId(R.id.login_user_edittext)).perform(typeText("aaaa"),closeSoftKeyboard());
    }
}
