import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.opencsv.CSVWriter;

public class Test {

	public static void main(String[] args) {
		try {

			// System.out.println(String.valueOf(3d).replaceAll(".0", ""));
			List<Image> lstImage=new ArrayList<Image>();
			Document doc = Jsoup.connect(
					"https://www.wallpaperflare.com/search?wallpaper=nature")
					.userAgent(
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
					.header("accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
					.timeout(5000).get();

			String a = doc.toString();
			List<Element> lst= doc.select("#gallery li");
			
			for (Element element : lst) {
				try {
					Element taga=element.selectFirst("a");
					System.out.println(taga.attr("href"));
					Document doc2 = Jsoup.connect(taga.attr("href")+"/download")
							.userAgent(
									"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
							.header("accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
							.timeout(5000).get();
					Element IdImage=doc2.selectFirst("#show_img");
					List<Element> tags= doc2.select(".tag a");
					String tagtxt="";
					for (Element element2 : tags) {
						tagtxt=tagtxt+element2.text()+",";
					}
					Image img=new Image();
					img.title=IdImage.attr("alt");
					img.url=IdImage.attr("src");
					img.tag=tagtxt;
					String b=IdImage.attr("src");
					int index=b.lastIndexOf("/");
					String c= b.substring(index + 1);
					img.name=c;
					lstImage.add(img);
//					URL url = new URL(finalURL(IdImage.attr("src")));
//					
//	                    InputStream in = new BufferedInputStream(url.openStream());
//	                    ByteArrayOutputStream out2 = new ByteArrayOutputStream();
//	                    byte[] buf = new byte[2048];
//	                    int n = 0;
//	                    while (-1 != (n = in.read(buf))) {
//	                        out2.write(buf, 0, n);
//	                    }
//	                    out2.close();
//	                    in.close();
//	                    byte[] response2 = out2.toByteArray(); 
//	                    FileOutputStream fos = new FileOutputStream("C:\\Users\\longtn\\Downloads\\naruto\\"+uuidAsString+".jpg");
//                        fos.write(response2);
//                        fos.close();
					
				} catch (Exception e) {
					continue;
					// TODO: handle exception
				}
				
				
			}
			writeDataLineByLine("C:\\Users\\longtn\\Downloads\\naruto\\anc.csv", lstImage);
//
//			 File file = new File("C:\\Users\\longtn\\Downloads\\results.html");
//			
//			 FileWriter writer = new FileWriter(file);
//			 writer.write(doc.html());
//			 writer.flush();
//			 writer.close();
//			
//			 System.out.println(doc.getElementsByClass("film-info-title").text());
//			
//			 System.out.println(doc.getElementsByClass("film-info-description").text());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
	
	static String finalURL(String url) {
		try {
			 HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			    con.setInstanceFollowRedirects(false);
			    con.connect();
			    return con.getHeaderField("Location").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	   
	}
	

public static void writeDataLineByLine(String filePath,List<Image> lst)
{
    // first create file object for file placed at location
    // specified by filepath
    File file = new File(filePath);
    try {
        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);
  
        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);
        String[] header = { "path", "name", "tag","url" };
        writer.writeNext(header);
        for (Image image : lst) {
        	  String[] data1 = { image.name, image.title, image.tag,image.url };
              writer.writeNext(data1);
		}

  
        // closing writer connection
        writer.close();
    }
    catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}

}
