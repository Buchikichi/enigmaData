package to.kit.enigma.data.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import to.kit.enigma.data.dto.QzSchema.QuestionSet;
import to.kit.enigma.data.model.Answer;
import to.kit.enigma.data.model.AnswerPK;
import to.kit.enigma.data.model.Category;
import to.kit.enigma.data.model.Question;

public final class QzSchema implements Iterable<QuestionSet> {
	private Map<String, Category> categoryMap = new HashMap<>();
	private List<QuestionSet> questions = new ArrayList<>();

	@Override
	public Iterator<QuestionSet> iterator() {
		return this.questions.iterator();
	}
	public String addCategory(String catName) {
		Category category;

		if (this.categoryMap.containsKey(catName)) {
			category = this.categoryMap.get(catName);
		} else {
			category = new Category();
			UUID uuid = UUID.randomUUID();

			category.setCatID(uuid.toString());
			category.setCatName(catName);
			this.categoryMap.put(catName, category);
		}
		return category.getCatID();
	}
	public Collection<Category> getCategories() {
		return this.categoryMap.values();
	}
	public List<QuestionSet> getQuestions() {
		return this.questions;
	}
	public QuestionSet newQuestion(String catID) {
		QuestionSet questionSet = new QuestionSet();
		UUID uuid = UUID.randomUUID();

		questionSet.question.setCatID(catID);
		questionSet.question.setQID(uuid.toString());
		this.questions.add(questionSet);
		return questionSet;
	}

	public static class QuestionSet {
		final Question question = new Question();
		final List<Answer> answers = new ArrayList<>();

		public void appendContent(String str) {
			String content = StringUtils.defaultString(this.question.getContent());

			content += str + '\n';
			this.question.setContent(content);
		}
		public void setSource(String source) {
			this.question.setSource(source);
		}
		public void appendAnswer(String content) {
			Answer answer = new Answer();
			AnswerPK id = new AnswerPK();
			byte seq = (byte) this.answers.size();

			id.setQID(this.question.getQID());
			id.setSeq(seq);
			answer.setId(id);
			answer.setContent(content);
			this.answers.add(answer);
		}
		public Question getQuestion() {
			return this.question;
		}
		public List<Answer> getAnswers() {
			return this.answers;
		}
	}
}
