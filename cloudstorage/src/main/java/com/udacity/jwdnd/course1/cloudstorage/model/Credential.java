package com.udacity.jwdnd.course1.cloudstorage.model;

/*

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);
 */
public class Credential {
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;
    private String decryptedPass;

    // this is a temp credential I think
    public static Credential buildPartialCredential(Integer credentialid, String url, String username, String decryptedPass) {
        Credential cr = new Credential(credentialid, url, username, null, null, null);
        cr.setDecryptedPass(decryptedPass);
        return cr;
    }
    public static Credential fromWeb(Integer credentialid, String url, String username, String encrpted) {
        Credential cr = new Credential(credentialid, url, username, null, encrpted, null);
        return cr;
    }


    public Credential(Integer credentialid, String url, String username, String key, String password, Integer userid) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "credentialid=" + credentialid +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", key='" + key + '\'' +
                ", password='" + password + '\'' +
                ", decreptedPass='" + decryptedPass + '\'' +
                ", userid=" + userid +
                '}';
    }

    public Credential setDecryptedPass(String s) {
        this.decryptedPass = s;
        return this;
    }

    public String getDecryptedPass() {
        return (this.decryptedPass);
    }
}
