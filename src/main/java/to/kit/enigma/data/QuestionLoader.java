package to.kit.enigma.data;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import to.kit.enigma.data.dto.QzSchema;
import to.kit.enigma.data.dto.QzSchema.QuestionSet;

@Component
public final class QuestionLoader {
	private static final Logger LOG = LogManager.getLogger();
	private static final String RESOURCE = "/qz.7z";
	private static final String PASSWORD = "";

	private enum ReadPhase {QUESTION, ANSWER}

	/**
	 * Please remember when you want to use encryption technology
	 * outside the United States.
	 */
	private void showKeyLength() {
		try {
			LOG.info("KeyLength:" + Cipher.getMaxAllowedKeyLength("AES/ECB/PKCS5Padding"));
		} catch (NoSuchAlgorithmException e) {
			// nop
		}
	}

	private File getArchiveFile() {
		URL url = QuestionLoader.class.getResource(RESOURCE);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			// Not impossible this state.
		}
		return file;
	}

	private void load(QzSchema schema, String source, byte[] content) throws IOException {
		ReadPhase phase = null;
		String catId = null;
		QuestionSet currentQuestion = null;

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(content), "UTF-8"))) {
			for (;;) {
				String line = in.readLine();

				if (line == null) {
					break;
				}
				if (line.startsWith("@")) {
					String catName = line.substring(1);
					catId = schema.addCategory(catName);
					continue;
				} else if (line.startsWith("--")) {
					phase = ReadPhase.QUESTION;
					currentQuestion = schema.newQuestion(catId);
					currentQuestion.setSource(source);
					continue;
				} else if (line.startsWith("/")) {
					phase = ReadPhase.ANSWER;
					continue;
				}
				if (ReadPhase.QUESTION == phase) {
					if (currentQuestion != null) {
						currentQuestion.appendContent(line);
					}
				} else if (ReadPhase.ANSWER == phase) {
					if (currentQuestion != null) {
						line = line.replaceAll("^[0-9][.]", StringUtils.EMPTY);
						currentQuestion.appendAnswer(line);
					}
				}
			}
		}
	}

	public void load(QzSchema schema) throws IOException {
		File file = getArchiveFile();
		byte[] password = PASSWORD.getBytes("UTF-16LE");

		showKeyLength();
		try (SevenZFile archive = new SevenZFile(file, password)) {
			for (;;) {
				ArchiveEntry entry = archive.getNextEntry();
				if (entry == null) {
					break;
				}
				String source = entry.getName();
				if (!source.endsWith(".txt")) {
					continue;
				}
				long size = entry.getSize();
				byte[] content = new byte[(int) size];

				LOG.debug(source + ":" + size);
				archive.read(content);
				load(schema, source, content);
			}
		}
	}
}
