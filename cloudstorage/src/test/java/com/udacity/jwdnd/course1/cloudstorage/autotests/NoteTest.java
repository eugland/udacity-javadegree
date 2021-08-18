package com.udacity.jwdnd.course1.cloudstorage.autotests;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomeForm;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {
    private final String HOST = "http://localhost:";

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private HomeForm homeForm;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.get(HOST + port + "/login");
        LoginForm loginForm = new LoginForm(driver);
        loginForm.setUsernameField("user1");
        loginForm.setPassWord("pass");
        loginForm.submit();
        Optional<String> result = loginForm.getErrorMessage();

        // check if login success
        // otherwise just fail whatever test going on.
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals("Home", driver.getTitle());
        homeForm = new HomeForm(driver);
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
        //clear homeForm Reference?
        homeForm = null;
    }


    private void goHome() {
        driver.get(HOST + port + "/home");
    }

    @Test
    public void getInitialNotesList() {
        homeForm.selectNotesWindow();
        List<Note> notes = homeForm.getNotes();
        System.out.println(notes);
        // check if each is a Note object
        Assertions.assertNotNull(notes);
        notes.parallelStream().forEach(Assertions::assertNotNull);
    }

    @Test
    public void createNote()  {
        // go to notes window and add
        homeForm.selectNotesWindow();
        Note toAdd = new Note(null, "NoteTitle", "This is a note added to description and this note will be used to test createNote and viewNotes", null);
        homeForm.addNote(toAdd);


        // assert page jump to result page, and then has a successful result message
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("divSuccess")));
        Assertions.assertThrows(NoSuchElementException.class, ()->driver.findElement(By.id("divFailed")));

        // go home and check if the notes has been added
        goHome();
        homeForm.selectNotesWindow();
        List<Note> updatedNotes = homeForm.getNotes();
        Assertions.assertTrue(homeForm.noteIsIn(toAdd, updatedNotes));
    }

    @Test
    public void updateNotes() {
        homeForm.selectNotesWindow();
        // Note toAdd = new Note(null, "NoteTitle", "This is a note added to description and this note will be used to test createNote and viewNotes", null);
        List<Note> notes = homeForm.getNotes();
        Note toUpdate = notes.get(0);
        toUpdate.setNotetitle("modified by test");
        toUpdate.setNotedescription("This note will be updated by the test, sent to the database, " +
                "and then we will get this notes again from the list returned from the Databse");
        homeForm.updateNote(toUpdate);


        // assert page jump to result page, and then has a successful result message
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("divSuccess")));
        Assertions.assertThrows(NoSuchElementException.class, ()->driver.findElement(By.id("divFailed")));

        // go home and check if the notes has been updated
        goHome();
        homeForm.selectNotesWindow();
        List<Note> updatedNotes = homeForm.getNotes();
        Assertions.assertTrue(homeForm.noteIsIn(toUpdate, updatedNotes));
    }

    @Test
    public void deleteNotes() throws Exception {
        homeForm.selectNotesWindow();
        // Note toAdd = new Note(null, "NoteTitle", "This is a note added to description and this note will be used to test createNote and viewNotes", null);
        List<Note> notes = homeForm.getNotes();
        Note toDelete = notes.get(0);
        homeForm.deleteNote(toDelete);


        // assert page jump to result page, and then has a successful result message
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("divSuccess")));
        Assertions.assertThrows(NoSuchElementException.class, ()->driver.findElement(By.id("divFailed")));

        // go home and check if the notes has been deleted
        goHome();
        homeForm.selectNotesWindow();
        List<Note> updatedNotes = homeForm.getNotes();
        Assertions.assertFalse(homeForm.noteIsIn(toDelete, updatedNotes));
    }



}
