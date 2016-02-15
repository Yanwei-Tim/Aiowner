package com.dudu.aiowner;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.dudu.aiowner.ui.main.HomeActivity;

import org.junit.Before;
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
public class RegistTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "15820757371";
    }

    @Test
    public void test_regist_click() {
        onView(withId(R.id.home_regist_btn)).perform(click());
    }


    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.regist_input_phonenumber_et))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.regist_phonenumber_btn)).perform(click());

    }
}
