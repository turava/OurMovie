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
    import static android.support.test.espresso.Espresso.pressBack;
    import static android.support.test.espresso.action.ViewActions.click;
    import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
    import static android.support.test.espresso.action.ViewActions.replaceText;
    import static android.support.test.espresso.action.ViewActions.scrollTo;
    import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
    import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
    import static android.support.test.espresso.matcher.ViewMatchers.withId;
    import static android.support.test.espresso.matcher.ViewMatchers.withParent;
    import static android.support.test.espresso.matcher.ViewMatchers.withText;
    import static org.hamcrest.Matchers.allOf;
    import static org.hamcrest.Matchers.is;

    @LargeTest
    @RunWith(AndroidJUnit4.class)
    public class AlertsActivityTest {
        /*
        Test don't works without login registration
         */

        @Rule
        public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

        @Test
        public void alertsActivityTest() {
            ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.user),
                childAtPosition(
                childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0),
                0)));
                        appCompatAutoCompleteTextView.perform(scrollTo(), click());

            ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.user),
                childAtPosition(
                childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0),
                 0)));
            appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("kseniya"), closeSoftKeyboard());

            ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                childAtPosition(
                childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0),
                0)));
             appCompatEditText.perform(scrollTo(), replaceText("kseniya"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btLogin), withText("Log in"),
                childAtPosition(
                allOf(withId(R.id.loginForm),
                childAtPosition(
                withId(R.id.login_form),
                0)),
                2)));
            appCompatButton.perform(scrollTo(), click());


            ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.notificationItem),
                childAtPosition(
                childAtPosition(
                withId(R.id.bottomNavigationView),
                0),
                3),
                isDisplayed()));
                //  .perform(click());
                //bottomNavigationItemView.check(matches(isCompletelyDisplayed()));
            bottomNavigationItemView.perform(click());
             // onView(withId(R.id.notificationItem))
             // .perform(scrollTo(), click());

            ViewInteraction linearLayout = onView(
                allOf(withId(R.id.layout),
                childAtPosition(
                allOf(withId(R.id.layout0),
                withParent(withId(R.id.listView))),
                0),
                isDisplayed()));
            linearLayout.perform(scrollTo(),click());

             // Added a sleep statement to match the app's execution delay.
     // The recommended way to handle such scenarios is to use Espresso idling resources:
      // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
         try {
             Thread.sleep(400);
             } catch (InterruptedException e) {
             e.printStackTrace();
             }

            pressBack();

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
                            && view.equals(((ViewGroup)parent).getChildAt(position));
                }
            };
        }
        }
