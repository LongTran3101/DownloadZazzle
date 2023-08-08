import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Test {

	public static void main(String[] args) {
		try {

			// System.out.println(String.valueOf(3d).replaceAll(".0", ""));

			Document doc = Jsoup.connect(
					"https://www.zazzle.com/halloween_boys_dabbing_skeleton_scary_pumpkin_jack_t_shirt-235077001127395647")
					.userAgent(
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
					.header("cookie",
							"bx=zlng%3den%26zlng_x%3d133378560000000000; us=CCBC4D38-0705-4235-81C7-69FD62697345; general_maturity=1; NSC_xxx01=1116a3dbe042f48b86876dccf6a4421932d8a8ae7a75ba93019d78e8d52b10b5451498d9; _pxvid=b1ed4031-2445-11ee-9377-1899d8a957ee; pxcts=b52c71b2-2445-11ee-94c0-59707652424c; _gcl_au=1.1.1162398486.1689559225; _fbp=fb.1.1689559225236.853424489; _tt_enable_cookie=1; _ttp=PYozAdYcp_5O5_5k719WWvZqQPn; tn-pixel-ref=Direct; tn-pixel-userId=a223996d-6f8d-4c8a-87d1-6389457c961e; _pin_unauth=dWlkPVpEZG1aV0ptWTJNdFpqSXdOQzAwTWpJNUxUa3haVGd0T1dKa1pHTmtOV014TW1Kaw; __attentive_id=48ca0336e0d64e829a622e0937a1057b; _attn_=eyJ1Ijoie1wiY29cIjoxNjg5NTU5MjI5MDY1LFwidW9cIjoxNjg5NTU5MjI5MDY1LFwibWFcIjoyMTkwMCxcImluXCI6ZmFsc2UsXCJ2YWxcIjpcIjQ4Y2EwMzM2ZTBkNjRlODI5YTYyMmUwOTM3YTEwNTdiXCJ9In0=; __attentive_cco=1689559229069; __gsas=ID=d16598cbd8b35013:T=1689559236:RT=1689559236:S=ALNI_Ma6r5p3MjSPJYSmwnu3O5Ki2ExUyg; NSC_smw=68bfa3fefcac1851fba4a16395ae156d74322e7cd8ec7ea018f021fbf6a7c52d84308960; _ga_44HW65CJLG=GS1.1.1689905306.1.0.1689905420.0.0.0; _ga_LL4J9H50WM=GS1.1.1689905307.1.0.1689905436.0.0.0; _ga_RPMSHXCWC7=GS1.1.1689952674.2.0.1689953346.0.0.0; _gid=GA1.2.736291211.1690275317; __attentive_dv=1; _ga_JFR6RCXPZ5=GS1.1.1690276656.1.0.1690276896.0.0.0; _clck=1pm1a6d|2|fdm|0|1293; _pxhd=2C2qqTIEHuPuUOhxC3ilqREYkGSp3AWkkXy167ekE/9PLaBynAayU1hQXJjXzoif5jbwMlJG-kVbC61oRglcFg==:sP0PZchUn-hyN7Z6KdcELNlAlbGsKT6F1Qm3jHsZ8Ar396onFAbsOseNq0iYuzScXUWvROQqgZcfx7K5YC-mYGIerZa157LtxI1MGiHfoqk=; __attentive_ss_referrer=https://www.zazzle.com/s/dabbing+halloween+boys+skeleton+shirt; __gads=ID=fc18eb2eb4ac100d:T=1689559236:RT=1690337844:S=ALNI_MbGVuw81rgBlHneVcZhnHnKj_Ll_g; __gpi=UID=00000c215df45f70:T=1689559236:RT=1690337844:S=ALNI_MZwYRYZAIa3v12iHuQLLKcxdbOt0g; bs=pis%3d188%26zshopurl%3dz%2fs%2fdabbing%20halloween%20boys%20skeleton%20shirt; _ga=GA1.1.2042621713.1689559226; _ga_FMN87GXWKG=GS1.1.1690337744.7.1.1690338051.53.0.0; _uetsid=fa5702902ac811eeb108ddd473f3ab53; _uetvid=b26bc470244511ee993153b910562f61; _px2=eyJ1IjoiMGI0ZDdjZjAtMmI1Yi0xMWVlLTljNDctZWZmYjM1MDdkYjFmIiwidiI6ImIxZWQ0MDMxLTI0NDUtMTFlZS05Mzc3LTE4OTlkOGE5NTdlZSIsInQiOjE2OTAzMzgzNTQyMzgsImgiOiJiYTkzNDE4Mjc5NmU5YmExMzExMTAxYzM4Mjc0MTc1MjdmYTZiMmEwN2ExN2YzMTljMGIxMjRjY2FkMjAyZDRkIn0=; __attentive_pv=2; _clsk=1dgfo8d|1690338057865|3|0|v.clarity.ms/collect; zm=AQABAAAAlRkAABTaGBwX19C_2BvBTzcVsbXlEbafgeZy564q9AuFot0Son7RjkzLPqJlGbzGGOjOQrCV5kOF8R4le2pe9MQhwiCdgaiOIXBEvQUxU9-HC5fRqBFwLqoNi9rMIc73GBypFRqY_NLm; zs=633C74CB-55F0-41E3-BC4F-A4A195C80CD2%7c0%7c13334811781%7cAQABAAAAlRkAABSRBGO71vNvI1flQSX1Ei7-hTRZL2FmML1ljliT4XgPVme5iydO07rzfSjoyKcgpBGTUd1G%7c")
					// .header("x-csrf-token", "2418afe1a459f661")
					.header("authority", "www.zazzle.com")
					.header("accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
					.timeout(5000).get();

			String a = doc.toString();
			int bcd = a.indexOf("mainDesignViewId");
			String efg = a.substring(bcd, (bcd + 37));
			String id = efg.substring(19, 37);
			System.out.println(id);
			int cde = a.indexOf("\"designId\"");
			String cdds = a.substring(cde, (cde + 48));
			String design = cdds.replace("\"designId\":\"", "");
			System.out.println(design);
			String link = "https://rlv.zcache.com/svc/view?realview=" + id + "&design=" + design
					+ "&rlvnet=1&style=hanes_mens_crew_darktshirt_5250&color=black&size=a_s&max_dim=2000&hide=bleed%2Csafe%2Cvisible%2CvisibleMask&bg=0x00000000&image_type=png&rvtype=content&csa=true&areacolor=false";
			System.out.println(link);
			String title = doc.title().replace(" | Zazzle", "");
			System.out.println(title);

			// File file = new File("C:\\Users\\longtn\\Downloads\\results.html");
			//
			// FileWriter writer = new FileWriter(file);
			// writer.write(doc.html());
			// writer.flush();
			// writer.close();
			//
			// System.out.println(doc.getElementsByClass("film-info-title").text());
			//
			// System.out.println(doc.getElementsByClass("film-info-description").text());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
