import java.nio.file.Files;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
import java.lang.Exception.*;

class Main {
  public static void main(String[] args) {
    try{
      new genBookRecs();
    }catch(Exception e){
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      String sStackTrace = sw.toString(); // stack trace as a string
      System.out.println(sStackTrace);
    }
  }
}