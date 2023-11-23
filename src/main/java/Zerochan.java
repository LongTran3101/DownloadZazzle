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

public class Zerochan extends JFrame {

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
					Zerochan frame = new Zerochan();
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
	public Zerochan() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 686, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(67, 143, 553, 188);
		contentPane.add(scrollPane);
		
			final JTextArea textArea = new JTextArea();
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

				
			 URL url = new URL(line);
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
             String filename = line.substring(line.lastIndexOf('/') + 1).replaceAll(".jpg", "").replaceAll(".png", "");
             byte[] response2 = out2.toByteArray();
             FileOutputStream fos = new FileOutputStream(textField_1.getText() + filename.replaceAll("[^a-zA-Z0-9\\s+]", "") + i+".png");
             fos.write(response2);
             fos.close();


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
