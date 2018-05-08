package com.example.kseniyaturava.mytest;


import android.support.test.espresso.DataInteraction;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityTestRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void profileActivityTest() {
         /*
            @KseniyaTurava
            checking click "Foros" and "Favoritos" on ProfileActivity
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
            needed for simulate login, because bottomNavigation don't works in Test
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
        /*
            Activity SettingsProfile starts
            typing correct user information, to simulate login
            because bottomNavigation don't works in Test
         */
        onView(withId(R.id.et_user))
                .perform(replaceText("kseniya"), closeSoftKeyboard());
        onView(withId(R.id.et_email))
                .perform(replaceText("kuse@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.et_age))
                .perform(replaceText("80"), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).perform(click());

         /*
            Profile Activity starts
            click on item in listview Tab:"Foros"
            ForoActivity starts correctly
         */
        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withId(R.id.tab2),
                                0)))
                .atPosition(0);
        linearLayout2.perform(click());
        /*
            back pressed, ProfileActivity starts correctly
         */
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

         /*
            click on tab "Favoritos"
            Error
            Error performing 'single click' on view '(Child at position 1 in parent (with id: android:id/tabs and Child at position 0 in parent with class name: is "android.widget.LinearLayout") and is displayed on the screen to the user)'.
            Action will not be performed because the target view does not match one or more of the following constraints:
            at least 90 percent of the view's area is displayed to the user.
         */

        ViewInteraction linearLayout3 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.tabs),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                        isDisplayed()));
        linearLayout3.perform(click());//Error

        /*
        click on item on gridview, tab: "Favoritos"
        MovieActivity starts
         */
        DataInteraction linearLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView),
                        childAtPosition(
                                withId(R.id.tab1),
                                0)))
                .atPosition(0);
        linearLayout4.perform(click());
        /*
        back pressed, ProfileActivity starts
         */
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

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
