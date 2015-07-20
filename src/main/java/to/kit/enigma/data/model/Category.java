package to.kit.enigma.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String catID;

	private String catName;

	public Category() {
	}

	public String getCatID() {
		return this.catID;
	}

	public void setCatID(String catID) {
		this.catID = catID;
	}

	public String getCatName() {
		return this.catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

}