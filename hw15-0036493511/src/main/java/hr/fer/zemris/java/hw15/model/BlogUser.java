package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Razred <code>BlogUser</code> predstavlja vlasnika objave na našem blogu.
 * 
 * @author Filip
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.one",
				query="select b from BlogUser as b where b.nickname=:bu"),
	@NamedQuery(name="BlogUser.all",
				query="select b from BlogUser as b")
})
@Entity
@Table(name = "blog_users")
public class BlogUser {
	/**
	 * Id.
	 */
	private Long id;
	/**
	 * Ime.
	 */
	private String firstName;
	/**
	 * Prezime.
	 */
	private String lastName;
	/**
	 * Nadimak.
	 */
	private String nickname;
	/**
	 * E-mail.
	 */
	private String email;
	/**
	 * SHA-1 od lozinke
	 */
	private String passwordHash;
	/**
	 * Lista objava
	 */
	private List<BlogEntry> entries = new ArrayList<>();

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
	 * Getter za ime.
	 * 
	 * @return ime
	 */
	@Column
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter za ime.
	 * 
	 * @param firstname
	 *            ime
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter za prezime.
	 * 
	 * @return prezime
	 */
	@Column
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter za prezime.
	 * 
	 * @param lastName
	 *            prezime
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter za nadimak.
	 * 
	 * @return nadimak
	 */
	@Column
	public String getNickname() {
		return nickname;
	}

	/**
	 * Setter za nadimak.
	 * 
	 * @param nickname
	 *            nadimak
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Getter za E-mail.
	 * 
	 * @return E-mail
	 */
	@Column
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za E-mail
	 * 
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter za SHA-1 od lozinke.
	 * 
	 * @return lozinka
	 */
	@Column
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter za lozinku.
	 * 
	 * @param passwordHash
	 *            lozinka
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter za listu objava.
	 * 
	 * @return lista objava
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter za listu objava.
	 * 
	 * @param entries
	 *            lista objava
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		BlogUser other = (BlogUser) obj;
		if (this.id != other.id)
			return false;
		return true;
	}

}
