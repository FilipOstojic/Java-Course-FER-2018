package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred <code>BlogEntry</code> predstavlja objavu na našem blogu.
 * 
 * @author Filip
 *
 */
@NamedQueries({
	@NamedQuery(name = "BlogEntry.upit1", 
				query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
public class BlogEntry {
	/**
	 * Id.
	 */
	private Long id;
	/**
	 * Lista komentara.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Datum nastanka.
	 */
	private Date createdAt;
	/**
	 * Datum zadnje izmjene.
	 */
	private Date lastModifiedAt;
	/**
	 * Naslov.
	 */
	private String title;
	/**
	 * Sadržaj objave.
	 */
	private String text;
	/**
	 * Vlasnik objave.
	 */
	private BlogUser creator;

	/**
	 * Getter za id.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter za id.
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter za listu komentara.
	 * 
	 * @return lista komentara
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter za listu komentara
	 * 
	 * @param comments
	 *            lista komentara
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter za datum.
	 * 
	 * @return datum
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter za datum.
	 * 
	 * @param createdAt
	 *            datum
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter za datum zadanje promjene.
	 * 
	 * @return datum zadanje promjene
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter za datum zadanje promjene.
	 * 
	 * @param lastModifiedAt
	 *            datum zadanje promjene
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter za naslov.
	 * 
	 * @return naslov
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter za naslov.
	 * 
	 * @param title
	 *            naslov
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter za sadržaj.
	 * 
	 * @return sadržaj
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Setter za sadržaj.
	 * 
	 * @param text
	 *            sadržaj
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter za vlasnika.
	 * 
	 * @return vlasnik
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter za vlasnika.
	 * 
	 * @param creator
	 *            vlasnik
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}