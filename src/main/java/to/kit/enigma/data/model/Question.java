package to.kit.enigma.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the question database table.
 * 
 */
@Entity
@Table(name="question")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String qID;

	private String catID;

	@Lob
	private String content;

	@Lob
	private String source;

	public Question() {
	}

	public String getQID() {
		return this.qID;
	}

	public void setQID(String qID) {
		this.qID = qID;
	}

	public String getCatID() {
		return this.catID;
	}

	public void setCatID(String catID) {
		this.catID = catID;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}