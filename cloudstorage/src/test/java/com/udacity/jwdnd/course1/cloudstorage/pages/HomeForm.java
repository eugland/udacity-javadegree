package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HomeForm {

    @FindBy(id= "logout")
    private WebElement logoutButton;

    @FindBy(id= "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTabButton;

    private final WebDriver driver;

    public HomeForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() {
        logoutButton.submit();
    }

    public void selectNotesWindow () {
        notesTabButton.click();
    }

    public void selectCredentialsWindow() {
        credentialTabButton.click();
    }

    public void addNote(String title, String description) {
        addNote(new Note(null, title, description, null));
    }

    public void addNote(Note note) {
        addNoteButton.click();
        noteTitle.sendKeys(note.getNotetitle());
        noteDescription.sendKeys(note.getNotedescription());
        noteTitle.submit();
    }

    public List<Note> getNotes() {
        // TODO finish the rest of getNote method, we want to get note from the elements
        List<WebElement> notes = driver.findElements(By.className("notes"));
        List<Note> list = notes.parallelStream()
                .map(noteElement ->
                    new Note(Integer.parseInt(noteElement.findElement(By.id("elNoteID")).getText()),
                            noteElement.findElement(By.id("elNoteTitle")).getText(),
                            noteElement.findElement(By.id("elNoteDesc")).getText(), -1))
                .collect(Collectors.toList());
        //System.out.println(list);
        return list;
    }

    public boolean noteIsIn(Note note, List<Note> notes) {
        return notes.parallelStream().anyMatch(n ->
                n.getNotetitle().equals(note.getNotetitle()) &&
                n.getNotedescription().equals(note.getNotedescription()));
    }

    public void updateNote(Note note) {
        WebElement editNoteButton = driver.findElement(By.id("update"+note.getNoteid()));
        editNoteButton.click();
        noteTitle.clear();
        noteTitle.sendKeys(note.getNotetitle());
        noteDescription.clear();
        noteDescription.sendKeys(note.getNotedescription());
        noteTitle.submit();
    }

    public void deleteNote(Note note) {
        WebElement deleteNoteButton = driver.findElement(By.id("deletenote"+note.getNoteid()));
        deleteNoteButton.click();
    }

    public void addCredential(Credential cred) {
        addCredentialButton.click();
        credentialUrl.sendKeys(cred.getUrl());
        credentialUsername.sendKeys(cred.getUsername());
        credentialPassword.sendKeys(cred.getDecryptedPass());
        credentialUrl.submit();
    }

    public List<Credential> getCredentials() {
        List<WebElement> allCredentials = driver.findElements(By.className("credentialsList"));
        System.out.println(allCredentials);
        List<Credential> credentials = allCredentials.parallelStream().map(e->
                Credential.buildPartialCredential(
                        Integer.valueOf(e.findElement(By.id("credId")).getText()),
                        e.findElement(By.id("credUrl")).getText(),
                        e.findElement(By.id("credUsername")).getText(),
                        e.findElement(By.id("credPass")).getText()
                )).collect(Collectors.toList());
        System.out.println(credentials);
        return credentials;
    }

    public boolean credisIn(Credential cr, List<Credential> creds) {
        return creds.parallelStream().anyMatch(c ->
                c.getUrl().equals(cr.getUrl()) &&
                        c.getUsername().equals(cr.getUsername()) &&
                        c.getDecryptedPass().equals(cr.getDecryptedPass())
        );
    }

    public void updateCred(Credential cr) {
        WebElement credEditButton = driver.findElement(By.id("updateCred" + cr.getCredentialid()));
        credEditButton.click();
        credentialPassword.clear();
        credentialPassword.sendKeys(cr.getDecryptedPass());
        credentialUrl.clear();
        credentialUrl.sendKeys(cr.getUrl());
        credentialUsername.clear();
        credentialUsername.sendKeys(cr.getUsername());
        credentialUrl.submit();
    }

    public void deleteCred(Credential cr) {
        driver.findElement(By.id("deleteCred" + cr.getCredentialid())).click();
    }










}


