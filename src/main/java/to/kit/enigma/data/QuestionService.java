package to.kit.enigma.data;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import to.kit.enigma.data.dao.AnswerDao;
import to.kit.enigma.data.dao.CategoryDao;
import to.kit.enigma.data.dao.QuestionDao;
import to.kit.enigma.data.dto.QzSchema;
import to.kit.enigma.data.dto.QzSchema.QuestionSet;
import to.kit.enigma.data.model.Question;

@Component
public final class QuestionService {
	private static final Logger LOG = LogManager.getLogger();

	@Resource
	private MyEntityManager myEntityManager;
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private QuestionDao questionDao;
	@Resource
	private AnswerDao answerDao;

	private EntityTransaction getEntityTransaction() {
		EntityManager manager = this.myEntityManager.getEntityManager();
		return manager.getTransaction();
	}

	public void save(QzSchema schema) {
		EntityTransaction transaction = getEntityTransaction();
		String currentSource = null;

		transaction.begin();
		this.categoryDao.truncate();
		this.categoryDao.insert(schema.getCategories());
		this.questionDao.truncate();
		this.answerDao.truncate();
		for (QuestionSet questionSet : schema) {
			Question question = questionSet.getQuestion();

			if (StringUtils.isBlank(question.getContent())) {
				continue;
			}
			String source = question.getSource();
			if (!source.equals(currentSource)) {
				if (currentSource != null) {
					transaction.commit();
					transaction.begin();
				}
				LOG.info(source);
				currentSource = source;
			}
			this.questionDao.insert(question);
			this.answerDao.insert(questionSet.getAnswers());
		}
		transaction.commit();
	}
}
