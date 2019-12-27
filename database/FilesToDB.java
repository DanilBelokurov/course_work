package database;

import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.mysql.jdbc.Blob;


public class FilesToDB {

	/* ------------------- UPLOAD TO SERVER ------------------- */
	public boolean export(HttpServletRequest request) {

		Connection conn = null;

		try {
			System.out.println("Start export\n");

			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/example", "root", "root");
			conn.setAutoCommit(false);

			String id = request.getParameter("work_id");
			System.out.println(id);

			for (Part part : request.getParts()) {

				String fileName = extractFileName(part);

				if (fileName != null && fileName.length() > 0) {
					InputStream is = part.getInputStream();
					this.writeToDB(conn, fileName, is, id);
				}
			}
			conn.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			this.closeQuietly(conn);
		}
	}

	/* ------------------- DOWNLOAD FROM SERVER ------------------- */
	public Attachment inport (HttpServletRequest request, HttpServletResponse resp, String ID) {

		Connection conn = null;
		HttpServletResponse response = resp;

		try {
			System.out.println("Start import");
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/example", "root", "root");
			conn.setAutoCommit(false);

			String id = ID;
			System.out.println(id.toString());

			Attachment attachment = getAttachmentFromDB(conn, id);
			return attachment;
/*			
			if (attachment == null) {
				response.getWriter().write("No data found");
			}

			String fileName = attachment.getFileName();
			System.out.println("File Name: " + fileName);

			String contentType = URLConnection.guessContentTypeFromName(fileName);
			System.out.println("Content Type: " + contentType);

			response.setHeader("Content-Type", contentType);

			response.setHeader("Content-Length", String.valueOf(attachment.getFileData().length()));

			response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getFileName() + "\"");

			Blob fileData = attachment.getFileData();
			InputStream is = fileData.getBinaryStream();
			byte[] bytes = new byte[1024];
			int bytesRead;

			while ((bytesRead = is.read(bytes)) != -1) {
				response.getOutputStream().write(bytes, 0, bytesRead);
				System.out.println("asdASDD");
			}

			is.close();
			return response;*/
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			this.closeQuietly(conn);
		}
	}
	
	/* ------------------- GET CURRENT STATUS OF REPORT (S) ------------------- */
	public int getReportStatus(String ID) {
		
		Connection conn = null;
		try {
			
			System.out.println(ID);
			
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/example", "root", "root");
			
			Attachment attachment = getAttachmentFromDB(conn, ID);
		
			return Integer.parseInt(attachment.getDescription());
			
		}catch(Exception e) {
			//e.printStackTrace();
			return 0;
		}finally{
			this.closeQuietly(conn);
		}
	}
	
	/* ------------------- SET CURRENT STATUS TO REPORT (T) ------------------- */
	public boolean setReportStatus(String ID, String status) {
		
		Connection conn = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/example", "root", "root");
			Statement statement = conn.createStatement();
			
			String updateString = "UPDATE attachment SET description='" + status + //
					"' where id='" + ID + "';";
			
			statement.executeUpdate(updateString);
			statement.close();
			
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			this.closeQuietly(conn);
		}
	}
	

	/* ------------------- STUFF FUNCS ------------------- */
	
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
				clientFileName = clientFileName.replace("\\", "/");
				int i = clientFileName.lastIndexOf('/');
				return clientFileName.substring(i + 1);
			}
		}
		return null;
	}

	private void writeToDB(Connection conn, String fileName, InputStream is, String id) throws SQLException {

		String sql = "Insert into attachment (Id,File_Name,File_Data,Description) " //
				+ " values (?,?,?,?) ";
		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, id);
		pstm.setString(2, fileName);
		pstm.setBlob(3, is);
		pstm.setString(4, "1");//waiting for check
		pstm.executeUpdate();
		System.out.println("Export complete");
	}

	private Attachment getAttachmentFromDB(Connection conn, String id) throws SQLException {

		String sql = "Select * from attachment WHERE id ='" + id + "'";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String fileName = rs.getString(2);
			Blob fileData = (Blob) rs.getBlob(3);
			String description = rs.getString(4);
			
			return (new Attachment(id, fileName, fileData, description));
		}

		return null;
	}
	
	private void closeQuietly(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}