package to.kit.enigma.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the answer database table.
 *
 */
@Embeddable
public class AnswerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String qID;

	private byte seq;

	public AnswerPK() {
	}
	public String getQID() {
		return this.qID;
	}
	public void setQID(String qID) {
		this.qID = qID;
	}
	public byte getSeq() {
		return this.seq;
	}
	public void setSeq(byte seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnswerPK)) {
			return false;
		}
		AnswerPK castOther = (AnswerPK)other;
		return
			this.qID.equals(castOther.qID)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.qID.hashCode();
		hash = hash * prime + this.seq;

		return hash;
	}
}
