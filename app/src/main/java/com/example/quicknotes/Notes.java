package com.example.quicknotes;

public class Notes {
    private String id;
    private String title;
    private String message;


    // Table Colum
    public static final String KEY_ID = "Id";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_MESSAGE = "Message";

    // Notes Table
    public static final String TABLE_NAME = "NotesTable";

    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_TITLE, KEY_MESSAGE);
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM "+TABLE_NAME;

    // Deleted Table
    public static final String DELETE_TABLE = "DeleteTable";

    public static final String CREATE_DELETE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY , %s TEXT, %s TEXT)", DELETE_TABLE, KEY_ID, KEY_TITLE, KEY_MESSAGE);
    public static final String DROP_DELETE_TABLE = "DROP TABLE IF EXISTS " + DELETE_TABLE;
    public static final String SELECT_DELETE_TABLE = "SELECT * FROM " + DELETE_TABLE;

    // Archived Table
    public static final String ARCHIVE_TABLE = "ArchiveTable";

    public static final String CREATE_ARCHIVE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY , %s TEXT, %s TEXT)", ARCHIVE_TABLE, KEY_ID, KEY_TITLE, KEY_MESSAGE);
    public static final String DROP_ARCHIVE_TABLE = "DROP TABLE IF EXISTS "+ARCHIVE_TABLE;
    public static final String SELECT_ARCHIVE_TABLE = "SELECT * FROM "+ARCHIVE_TABLE;

    public Notes() {
    }

    public Notes(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Notes(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
