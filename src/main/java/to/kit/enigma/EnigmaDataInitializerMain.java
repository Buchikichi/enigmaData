package to.kit.enigma;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import to.kit.enigma.data.QuestionLoader;
import to.kit.enigma.data.QuestionService;
import to.kit.enigma.data.dto.QzSchema;

/**
 * EnigmaDataInitializerMain.
 */
@Configuration
@ComponentScan
public class EnigmaDataInitializerMain {
	private static final Logger LOG = LogManager.getLogger();

	@Resource
	private QuestionLoader loader;
	@Resource
	private QuestionService service;

	private void execute() throws IOException {
		LOG.info("BEGIN");
		QzSchema schema = new QzSchema();

		this.loader.load(schema);
		this.service.save(schema);
		LOG.info("END");
	}

	public static void main(String[] args) throws Exception {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				EnigmaDataInitializerMain.class)) {
			context.getBean(EnigmaDataInitializerMain.class).execute();
			System.exit(0);
		}
	}
}
