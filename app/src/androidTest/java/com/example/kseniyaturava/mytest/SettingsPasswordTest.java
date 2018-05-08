package com.example.kseniyaturava.mytest;


import android.os.SystemClock;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingsPasswordTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityTestRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void settingsPasswordActivityTest() {
         /*
            @author KseniyaTurava
            Checking for errors in EditText on SettingsPassword Activity
            Test starts on ProfileActivity
         */
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menuProfile), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        //Click on menu: Settings icon on action bar
        actionMenuItemView.perform(click());
         /*
            Activity SettingsActivity starts
            click on "Cambiar Contraseña"
         */

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.layout_changePass),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        linearLayout.perform(click());
        //Activity SettingsPassword starts
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_pass1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());
        /*
            1nd Test
            password 1 must have 6-15 chars
            password 1 must match with password2
         */

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_pass1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("kseniya"), closeSoftKeyboard());
        // password2 is Empty and don't mutch with password1
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.et_pass2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("  "), closeSoftKeyboard());
        //click on Save info: if password isn't correct, Toast appears
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_save), withText("Guardar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());
        /*
            2nd Test
            password 1 must have 6-15 chars
            password 1 must match with password2
         */
        SystemClock.sleep(2000);//waiting to see the toast correctly
        ViewInteraction appCompatEditText0 = onView(
                allOf(withId(R.id.et_pass1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText0.perform(replaceText("ks"), closeSoftKeyboard());
        // password2 must have more than 6 chars
        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.et_pass2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText1.perform(replaceText("ks"), closeSoftKeyboard());
        //click on Save info: if password isn't correct, Toast appears

        SystemClock.sleep(2000);//waiting to see the toast correctly
        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.btn_save), withText("Guardar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton22.perform(click());

        SystemClock.sleep(2000);//waiting to see the toast correctly
        /*
            3nd Test
            password 1 must have 6-15 chars
            password 1 must match with password2
         */
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.et_pass1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("kseniyakseniyaaa"), closeSoftKeyboard());
        // password have to much chars
        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.et_pass2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("kseniyakseniyaaa"), closeSoftKeyboard());
        //click on Save info: if password isn't correct, Toast appears
        SystemClock.sleep(2000);//waiting to see the toast correctly
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_save), withText("Guardar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton10.perform(click());
        SystemClock.sleep(2000);//waiting to see the toast correctly
        /*
            4nd Test
            password 1 must have 6-15 chars
            password 1 must match with password2
         */
        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.et_pass1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("kseniya"), closeSoftKeyboard());
        // password1 and 2 are correct writed
        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.et_pass2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("kseniya"), closeSoftKeyboard());
        // click on Save info
        SystemClock.sleep(2000);//waiting to see the toast correctly
        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.btn_save), withText("Guardar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton13.perform(click());
        //  if passwords writed correctly, ProfileActivity starts


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
