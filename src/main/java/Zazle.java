import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.imgscalr.Scalr;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.awt.event.ActionEvent;

public class Zazle extends JFrame {

	private JPanel contentPane;
	private static JTextField textField;
	private static JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zazle frame = new Zazle();
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Zazle() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 686, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(67, 143, 553, 188);
		contentPane.add(scrollPane);
		
				JTextArea textArea = new JTextArea();
				scrollPane.setViewportView(textArea);

		textField = new JTextField();
		textField.setBounds(67, 0, 553, 32);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnDownload = new JButton("download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i=0;
				String[] array=textArea.getText().split("\\n");
				for (int j = 0; j < array.length; j++) {
					download(array[j],j);
				}
				
			}
		});
		btnDownload.setBounds(257, 352, 141, 35);
		contentPane.add(btnDownload);
		
		JButton btnThuMucLuu = new JButton("thu muc luu");
		btnThuMucLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Chọn thư mục lưu");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					String stringUrlSave = chooser.getSelectedFile().toString();
					textField_1.setText(stringUrlSave + "\\");

				} else {
					System.out.println("No Selection ");
				}

			}
		});
		btnThuMucLuu.setBounds(67, 53, 176, 35);
		contentPane.add(btnThuMucLuu);
		
		textField_1 = new JTextField();
		textField_1.setBounds(257, 56, 361, 32);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}

	public static void download(String line,int i) {
		try {

			// System.out.println(String.valueOf(3d).replaceAll(".0", ""));

			Document doc = Jsoup.connect(line).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
					.header("cookie", textField.getText())
					// .header("x-csrf-token", "2418afe1a459f661")
					.header("authority", "www.zazzle.com")
					.header("accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
					.timeout(10000).get();

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
			
			 URL url = new URL(link);
             //System.out.println(FilenameUtils.getBaseName(url.getPath())); // -> file
             InputStream in = new BufferedInputStream(url.openStream());
             ByteArrayOutputStream out2 = new ByteArrayOutputStream();
             byte[] buf = new byte[2048];
             int n = 0;
             while (-1 != (n = in.read(buf))) {
                 out2.write(buf, 0, n);
             }
             out2.close();
             in.close();
             byte[] response2 = out2.toByteArray();
             FileOutputStream fos = new FileOutputStream(textField_1.getText() + title.replaceAll("[^a-zA-Z0-9\\s+]", "") + i+".png");
             fos.write(response2);
             fos.close();
//             
//             int newHeight = 5400;
//             int newWidth = 4500;
//             int newHeightresize = 5400 ;
//             int newWidthresize = 4500;
//             // PNG supports transparency
//             // int type = config.formatName.equals("png")?BufferedImage.TYPE_INT_ARGB:BufferedImage.TYPE_INT_RGB;
//             ByteArrayInputStream bais = new ByteArrayInputStream(response2);
//
//             BufferedImage outputImage = null;
//
//             outputImage = ImageIO.read(bais);
//             outputImage = trimImage(outputImage);
//
//             Scalr.Mode mode = Scalr.Mode.FIT_TO_HEIGHT;
//             BufferedImage outputImage2 = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY, mode, newWidthresize - 10, newHeightresize - 10, Scalr.OP_ANTIALIAS);
//             //System.out.println(outputImage2.getHeight());
//             if (outputImage2.getWidth() > newWidth) {
//                 Scalr.Mode mode2 = Scalr.Mode.FIT_TO_WIDTH;
//                 outputImage2 = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY, mode2, newWidthresize - 10, newHeightresize - 10, Scalr.OP_ANTIALIAS);
//                 int hightwirte = Math.round((newHeight - outputImage2.getHeight()) / 2);
//
//                 if ((newHeight - outputImage2.getHeight()) > 100) {
//                     hightwirte = 100;
//                 }
//
//                 int widthwirte = Math.round((newWidth - outputImage2.getWidth()) / 2);
//                 int type = BufferedImage.TYPE_INT_ARGB;
//
//                 BufferedImage outputImage4 = new BufferedImage(newWidth, newHeight, type);
//                 Graphics2D graphics2D = outputImage4.createGraphics();
//                 RenderingHints hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
//                         RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//                 hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//                 hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//                 graphics2D.setRenderingHints(hints);
//
//                 graphics2D.drawImage(outputImage2, widthwirte, hightwirte, null);
//                 graphics2D.dispose();
//
//                 boolean check = ImageIO.write(outputImage4, "png", new File(textField_1.getText() + title.replaceAll("[^a-zA-Z0-9\\s+]", "")  + ".png"));
//                 if (check);
//             } else {
//                 int hightwirte = Math.round((newHeight - outputImage2.getHeight()) / 2);
//                 int widthwirte = Math.round((newWidth - outputImage2.getWidth()) / 2);
//                 if ((newHeight - outputImage2.getHeight()) > 100) {
//                     hightwirte = 100;
//                 } 
//                 int type = BufferedImage.TYPE_INT_ARGB;
//
//                 BufferedImage outputImage4 = new BufferedImage(newWidth, newHeight, type);
//                 Graphics2D graphics2D = outputImage4.createGraphics();
//                 RenderingHints hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
//                         RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//                 hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//                 hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//                 graphics2D.setRenderingHints(hints);
//
//                 graphics2D.drawImage(outputImage2, widthwirte, hightwirte, null);
//                 graphics2D.dispose();
//
//                 boolean check = ImageIO.write(outputImage4, "png", new File(textField_1.getText() + title.replaceAll("[^a-zA-Z0-9\\s+]", "")  + ".png"));
//                 if (check);
//             }


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
	 private static BufferedImage trimImage(BufferedImage image) {
	        try {

	            WritableRaster raster = image.getAlphaRaster();
	            int width = raster.getWidth();
	            int height = raster.getHeight();
	            int left = 0;
	            int top = 0;
	            int right = width - 1;
	            int bottom = height - 1;
	            int minRight = width - 1;
	            int minBottom = height - 1;

	            top:
	            for (; top < bottom; top++) {
	                for (int x = 0; x < width; x++) {
	                    if (raster.getSample(x, top, 0) != 0) {
	                        minRight = x;
	                        minBottom = top;
	                        break top;
	                    }
	                }
	            }

	            left:
	            for (; left < minRight; left++) {
	                for (int y = height - 1; y > top; y--) {
	                    if (raster.getSample(left, y, 0) != 0) {
	                        minBottom = y;
	                        break left;
	                    }
	                }
	            }

	            bottom:
	            for (; bottom > minBottom; bottom--) {
	                for (int x = width - 1; x >= left; x--) {
	                    if (raster.getSample(x, bottom, 0) != 0) {
	                        minRight = x;
	                        break bottom;
	                    }
	                }
	            }

	            right:
	            for (; right > minRight; right--) {
	                for (int y = bottom; y >= top; y--) {
	                    if (raster.getSample(right, y, 0) != 0) {
	                        break right;
	                    }
	                }
	            }
	            return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
	        } catch (Exception e) {
	            return image;
	        }

	    }
}
