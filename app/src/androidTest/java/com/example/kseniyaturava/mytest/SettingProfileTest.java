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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingProfileTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityTestRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void settingProfileTest() {
         /*
            @KseniyaTurava
            checking for errors in EditText on SettingsProfile Activity
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
       // Click on menu: Settings icon on action bar
        actionMenuItemView.perform(click());
         /*
            Activity SettingsActivity starts
            click on "Redactar Perfil"
         */

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.layout_redactProfile),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        /*   Activity SettingsProfile starts
             user must have minimum 2 chars, if the String.length is less o iquals 1,
             value isn't accepted
        */
        onView(withId(R.id.et_user))
                .perform(typeText("k"), closeSoftKeyboard());
        /*
            email must have "@" and ".", if the String don't contains them,
             value isn't accepted
         */
        onView(withId(R.id.et_email))
                .perform(typeText("correo."), closeSoftKeyboard());
        /*
             age must be a int value, if the String can't be converted to Integer,
             value isn't accepted
         */
        onView(withId(R.id.et_age))
                .perform(typeText("letras"), closeSoftKeyboard());
        onView(withId(R.id.btn_save)).perform(click());
        /*
            click on save changes
            if the values are incorrect, Toast displayed and info isn't saved
         */

        /*
            2nd Test
            if user is Empty, default value equals to "Usuario"
             value isn't accepted
         */
        onView(withId(R.id.et_user))
                .perform(replaceText(""), closeSoftKeyboard());
        /*
        email must have "@" and ".", if the String don't contains them,
        value is accepted
         */

        onView(withId(R.id.et_email))
                .perform(replaceText("kuse@gmail.com"), closeSoftKeyboard());
        /*
             age must be from 18 age to 100, if it's diferent
             value isn't accepted
         */
        onView(withId(R.id.et_age))
                .perform(replaceText("0"), closeSoftKeyboard());
        //click on save changes
        onView(withId(R.id.btn_save)).perform(click());
        //if the values are incorrect, Toast displayed and info isn't saved

        /*
              3rd Test
              All the values are correct
              changes are saved, and intent ProfileActivity starts
          */

       onView(withId(R.id.et_user))
                .perform(replaceText("kseniya"), closeSoftKeyboard());
        onView(withId(R.id.et_email))
                .perform(replaceText("kuse@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.et_age))
                .perform(replaceText("80"), closeSoftKeyboard());
        onView(withId(R.id.btn_save)).perform(click());



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
