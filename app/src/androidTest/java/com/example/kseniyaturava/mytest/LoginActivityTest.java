package com.example.kseniyaturava.mytest;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    /*
        This Test is to prove the login registration
    */

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {

        //Message error with an invalid user or/and password

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.user),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("eli"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.user), withText("eli"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView2.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("eli"), closeSoftKeyboard());

        //Press Button Login -> We received a message saying that some field is not correct

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btLogin), withText("Log in"),
                        childAtPosition(
                                allOf(withId(R.id.loginForm),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        //Enter with a valid user

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.user),
                childAtPosition(
                childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0),
                0)));
        appCompatAutoCompleteTextView3.perform(scrollTo(), replaceText("eli.coca"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                allOf(withId(R.id.user), withText("eli.coca"),
                childAtPosition(
                childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0),
                0),
                isDisplayed()));
        appCompatAutoCompleteTextView4.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                childAtPosition(
                childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0),
                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("eli.coca"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btLogin), withText("Log in"),
                childAtPosition(
                allOf(withId(R.id.loginForm),
                childAtPosition(
                withId(R.id.login_form),
                0)),
                2)));
        appCompatButton2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay. -> go to MainActivity ok

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
