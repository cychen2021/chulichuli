package xyz.cychen.chulichuli.encoder.encoder;

import com.rabbitmq.client.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ws.schild.jave.*;
import ws.schild.jave.Encoder;
import xyz.cychen.chulichuli.browser.model.RabbitMQ;
import xyz.cychen.chulichuli.browser.model.MinIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@ComponentScan(basePackageClasses = { RabbitMQ.class})
@Import({RabbitMQ.class, MinIO.class})
public class EncoderApplication implements CommandLineRunner {

	private RabbitMQ rabbitMQ;
	private MinIO minIO;
	public EncoderApplication(RabbitMQ rabbitMQ, MinIO minIO) {
		this.rabbitMQ = rabbitMQ;
		this.minIO = minIO;
	}

	private static EncodingAttributes encodingAttributes(int w, int h) {
		AudioAttributes a = new AudioAttributes();
		a.setCodec("aac");
		a.setBitRate(64000);
		a.setChannels(2);
		a.setSamplingRate(44100);
		VideoAttributes v = new VideoAttributes();
		v.setCodec("h264");
		v.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
		v.setBitRate(160000);
		v.setFrameRate(15);
		v.setSize(new VideoSize(w, h));
		EncodingAttributes e = new EncodingAttributes();
		e.setVideoAttributes(v);
		e.setAudioAttributes(a);
		e.setFormat("mp4");
		return e;
	}

	private void copyInputSteam2File(InputStream is, File f) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(f, false);
		int read;
		byte[] bytes = new byte[1024];
		while ((read = is.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
	}

	private void  processMessage(GetResponse message){
		String text = new String(message.getBody(), StandardCharsets.UTF_8);
		Pattern p = Pattern.compile("\\{(.+)}\\{(.+)}\\{(.+)}");
		Matcher m = p.matcher(text);
		if (!m.matches()) {
			System.err.println(String.format("Wrong-formatted message \"%s\"", text));
			return;
		}
		String fileName = m.group(1);
		String f360Name = m.group(2);
		String f720Name = m.group(3);
		try {
			InputStream stream = minIO.get(fileName, MinIO.Bucket.BOrig);
			File input = File.createTempFile(fileName,"src.tmp");
			input.deleteOnExit();
			copyInputSteam2File(stream, input);

			EncodingAttributes attr360 = encodingAttributes(600,360);
			EncodingAttributes attr720 = encodingAttributes(1080,720);
			File f360 = File.createTempFile(fileName, "dst360.tmp");
			f360.deleteOnExit();
			Encoder e = new Encoder();
			e.encode(new MultimediaObject(input), f360, attr360);
			minIO.saveFile(f360, f360Name, MinIO.Bucket.B360);

			System.out.println(fileName + " converted to 360p!");

			File f720 = File.createTempFile(fileName, "dst720.tmp");
			f720.deleteOnExit();
			e.encode(new MultimediaObject(input), f720, attr720);
			minIO.saveFile(f720, f720Name, MinIO.Bucket.B720);
			System.out.println(fileName + " converted to 720p!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final Integer maxTasks = 3;
	private final AtomicInteger currentTasks = new AtomicInteger(0);

	@Override
	public void run(String... args) throws Exception {
	    new Thread(() -> {
			while (true) {
				if (currentTasks.get() < maxTasks) {
					try {
						final GetResponse response = rabbitMQ.get();
						if (response != null) {
							currentTasks.incrementAndGet();
							new Thread(() -> {
								processMessage(response);
								currentTasks.decrementAndGet();
							}).start();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static void main(String[] args) {
		SpringApplication.run(EncoderApplication.class, args);
	}

}
