import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.nio.file.Files;
import java.nio.charset.*;
import java.nio.file.*;

public class genHTMLFile{
  private String[][] data;
  private ArrayList<BookRec> recs = new ArrayList<BookRec>();
  private ArrayList<String> tags = new ArrayList<String>();
  public genHTMLFile() throws Exception{
    //import data from tsv file
    Scanner sc = new Scanner(new File("recommendations.tsv"));
    List<String[]> lines = new ArrayList<String[]>();
    sc.nextLine();
    while (sc.hasNextLine()) {
     lines.add(sc.nextLine().split("\\t"));
    }
    sc.close();
    
    //store data in 2d array for easy access
    data = new String[lines.size()][0];
    lines.toArray(data);

    //convert all text to html formatting
    for (String[] row : data){
      for (String item : row){
        item = txtToHtml(item);
      }
    }

    //create list of BookRec + list of tags
    for (String[] row : data){
      //System.out.println(new BookRec(row[0],row[1],row[2],row[3],row[4]));
      recs.add(new BookRec(row[0],row[1],row[2],row[3],row[4]));
      
      for (String tag : row[4].split(" ")){
        if (tags.contains(tag)==false){
          tags.add(tag);
        }
      }
    }

    //gen file
    PrintWriter out = new PrintWriter(new File("output.html"));
    out.print(getFileContents("-2.txt"));
    for (String tag : tags)
      out.print(getFileContents("-1.txt").replace("FILTER",tag));
    out.print(getFileContents("0.txt"));
    for (BookRec rec : recs)
      out.print(rec.toString());
    out.print(getFileContents("6.txt"));
    out.close();
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
  
  private static String txtToHtml(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    builder.append(c);

            }
        }
        String converted = builder.toString();
        String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
        Pattern patt = Pattern.compile(str);
        Matcher matcher = patt.matcher(converted);
        converted = matcher.replaceAll("<a href=\"$1\">$1</a>");
        return converted;
    }
}