package com.fernandowirtz.swingtesting.ui;

import com.fernandowirtz.swingtesting.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 *
 * @author gag
 *
 * Example Test Class with JUnit and AssertJ. Test Java Swing UI, searching
 * components by name, content or type.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class PersonListTest {

    private FrameFixture window;

    private PersonList app;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        PersonList frame = GuiActionRunner.execute(() -> {
            app = new PersonList();
            return app;
        });
        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }

    @Test
    public void requireGenerateReportButtonInitiallyDisabled() {
        window.button(JButtonMatcher.withText("Generate Report")).requireDisabled();
    }

    @Test
    public void shouldUpdateListWithNewItems() {
        GuiActionRunner.execute(() -> {
            List<Person> persons = new ArrayList<>();
            Person[] p = {new Person("Gon", "gon@gmail.com"), new Person("Ale", "ale@gmail.com"), new Person("Pau", "pau@gmail.com")};
            persons.addAll(Arrays.asList(p));
            app.setPersons(persons);
        });

        // Simulate user actions
        window.list().requireNoSelection().requireItemCount(3);
        Assertions.assertEquals("Gon (gon@gmail.com)", window.list().item(0).value());
    }

    @Test
    public void requireGenerateReportButtonOnlyEnabledWhenSelected() {
        GuiActionRunner.execute(() -> {
            List<Person> persons = new ArrayList<>();
            Person[] p = {new Person("Gon", "gon@gmail.com"), new Person("Ale", "ale@gmail.com"), new Person("Pau", "pau@gmail.com")};
            persons.addAll(Arrays.asList(p));
            app.setPersons(persons);
        });

        window.button(JButtonMatcher.withText("Generate Report")).requireDisabled();
        window.list().selectItem(0);
        window.button(JButtonMatcher.withText("Generate Report")).requireEnabled();
        window.list().clearSelection();
        window.button(JButtonMatcher.withText("Generate Report")).requireDisabled();
    }

    @Test
    public void requireLabelUpdatedWhenSelected() {
        GuiActionRunner.execute(() -> {
            List<Person> persons = new ArrayList<>();
            Person[] p = {new Person("Gon", "gon@gmail.com"), new Person("Ale", "ale@gmail.com"), new Person("Pau", "pau@gmail.com")};
            persons.addAll(Arrays.asList(p));
            app.setPersons(persons);
        });

        window.label("lblSelectedPerson").requireText("None selected");
        window.list().selectItem(0);
        window.label("lblSelectedPerson").requireText("Gon (gon@gmail.com)");
        window.list().clearSelection();
        window.label("lblSelectedPerson").requireText("None selected");
    }

    @AfterEach
    public void tearDown() {
        if (Objects.nonNull(window)) {
            window.cleanUp();
        }
    }
}
