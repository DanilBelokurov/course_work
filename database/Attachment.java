package database;

import com.mysql.jdbc.Blob;

public class Attachment {

	private String id;
	private String fileName;
	private Blob fileData;
	private String description;

	public Attachment(String id, String fileName, Blob fileData, String description) {
		this.id = id;
		this.fileName = fileName;
		this.fileData = fileData;
		this.description = description;
	}

	public Blob getFileData() {
		return this.fileData;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}