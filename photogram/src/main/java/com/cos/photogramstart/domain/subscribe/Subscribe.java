package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 

// 두개를 복합적으로 유니크를 줄때
@Table(
		uniqueConstraints = {
			@UniqueConstraint(
				name = "subscribe_uk",
				columnNames= {"fromUserId","toUserId"}
			)
		}
)

public class Subscribe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@JoinColumn(name="fromUserId") // from_user형식이 싫다면 
	@ManyToOne						// JoinColumn으로 내가원하는 이름으로 사용가능
	private User fromUser;
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser;
	
	private LocalDateTime createDate;
	
	@PrePersist 
	public void createDate() {
		this.createDate =  LocalDateTime.now();
	}
	
}
