import java.nio.file.Files;
import java.nio.charset.*;
import java.nio.file.*;

public class BookRec{
  private String grade;
  private String googleDriveID;
  private String review;
  private String titleAndAuthor;
  private String tags;

  public BookRec(String titleAndAuthor, String review, String grade, String googleDriveID, String tags){
    this.titleAndAuthor=titleAndAuthor;
    this.review=review;
    this.grade=grade;
    this.googleDriveID=googleDriveID;
    this.tags=tags;
  }

  public String getTitleAndAuthor(){
    return titleAndAuthor;
  }

  public void setTitleAndAuthor(String titleAndAuthor){
    this.titleAndAuthor=titleAndAuthor;
  }

   public String getReview(){
    return review;
  }

  public void setReview(String review){
    this.review=review;
  }

   public String getGrade(){
    return grade;
  }

  public void setGrade(String grade){
    this.grade=grade;
  }

   public String getGoogleDriveID(){
    return googleDriveID;
  }

  public void setGoogleDriveID(String googleDriveID){
    this.googleDriveID=googleDriveID;
  }

   public String getTags(){
    return tags;
  }

  public void setTags(String tags){
    this.tags=tags;
  }
  
  public String toString(){
    return getFileContents("1.txt")+tags+getFileContents("2.txt")+googleDriveID+getFileContents("3.txt")+titleAndAuthor+getFileContents("4.txt")+"\""+review+"\" -LPS "+grade+" grader"+getFileContents("5.txt");
  }

  private String getFileContents(String path){
    Path path1 = Paths.get(path);
    String contents="";
    try{
      contents = Files.readString(path1, StandardCharsets.US_ASCII);
    }catch(Exception e){
      System.out.println("Could not read file");
    }
    return contents;
  }
}