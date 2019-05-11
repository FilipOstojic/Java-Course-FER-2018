package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred <code>BlogComment</code> predstavlja komentar na našem blogu.
 * 
 * @author Filip
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {
	/**
	 * Id.
	 */
	private Long id;
	/**
	 * {@link BlogEntry}
	 */
	private BlogEntry blogEntry;
	/**
	 * E-mail adresa.
	 */
	private String usersEMail;
	/**
	 * Poruka, tj komentar.
	 */
	private String message;
	/**
	 * Datum kada je komentar objavaljen.
	 */
	private Date postedOn;

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
	 * Getter za {@link BlogEntry} kojem pripada komentar.
	 * 
	 * @return {@link BlogEntry}
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter za {@link BlogEntry}.
	 * 
	 * @param blogEntry
	 *            {@link BlogEntry}
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter za E-mail adresu.
	 * 
	 * @return E-mail adresa
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter za E-mail adresu.
	 * 
	 * @param usersEMail
	 *            E-mail adresa
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter za sadržaj komentara.
	 * 
	 * @return sadržaj komentara
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter za sadržaj komentara.
	 * 
	 * @param message
	 *            sadržaj komentara
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter za datum objavljavanja.
	 * 
	 * @return datum objavljivanja
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter za datum objavljivanja.
	 * 
	 * @param postedOn
	 *            datum objavljivanja
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}