package com.dudu.aiowner;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.dudu.aiowner.ui.activity.preventTheft.PreventTheftActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Administrator on 2016/2/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PreventTheftTest {

//    private Integer mState;

    @Rule
    public ActivityTestRule<PreventTheftActivity> mHomeActivityRule = new ActivityTestRule<>(
            PreventTheftActivity.class);

//    @Before
//    public void initValidString() {
//        // Specify a valid string.
//        mState = 1;
//    }

    @Test
    public void test_theft_switch_click() {
        onView(withId(R.id.prevent_theft_switch)).perform(click());
        onView(withId(R.id.prevent_theft_switch)).check(matches(isChecked()));

    }


//    @Test
//    public void changeText_sameActivity() {
//        // Type text and then press the button.
//        onView(withId(R.id.regist_input_phonenumber_et))
//                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
//        onView(withId(R.id.regist_phonenumber_btn)).perform(click());
//        onView(withId(R.id.regist_phonenumber_btn))
//                .check(matches(withText("测试")));
//    }
}
