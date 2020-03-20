package com.revature.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//Done

@Entity
@Table(name="Comments")
public class Comment {
	@Id
	@SequenceGenerator(name="comments", sequenceName="Comments_seq", allocationSize=1)
	@GeneratedValue(generator="comments", strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patronId")
	private Patron patron;
	@Column
	private Integer cardId;
	@Column
	private String commentText;
	@Column
	private Timestamp timeOfComment;
	
	public Comment() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
		result = prime * result + ((commentText == null) ? 0 : commentText.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patron == null) ? 0 : patron.hashCode());
		result = prime * result + ((timeOfComment == null) ? 0 : timeOfComment.hashCode());
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
		Comment other = (Comment) obj;
		if (cardId == null) {
			if (other.cardId != null)
				return false;
		} else if (!cardId.equals(other.cardId))
			return false;
		if (commentText == null) {
			if (other.commentText != null)
				return false;
		} else if (!commentText.equals(other.commentText))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patron == null) {
			if (other.patron != null)
				return false;
		} else if (!patron.equals(other.patron))
			return false;
		if (timeOfComment == null) {
			if (other.timeOfComment != null)
				return false;
		} else if (!timeOfComment.equals(other.timeOfComment))
			return false;
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Patron getPatron() {
		return patron;
	}
	public void setPatron(Patron patron) {
		this.patron = patron;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Timestamp getTimeOfComment() {
		return timeOfComment;
	}
	public void setTimeOfComment(Timestamp timeOfComment) {
		this.timeOfComment = timeOfComment;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", patron=" + patron + ", cardId=" + cardId + ", commentText=" + commentText
				+ ", timeOfComment=" + timeOfComment + "]";
	}
	
	
}
