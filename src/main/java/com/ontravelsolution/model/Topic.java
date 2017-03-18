package com.ontravelsolution.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;
import java.util.List;

@Entity
@Table (name = "topic")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Topic {

    @Id
    @GeneratedValue
    @Column (name = "id_topic")
    private Long id;
    private String topicName;
    private Date startDate;
    private Date endDate;
    
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answer;

    public Topic() {
		super();
		this.startDate = new Date();
    }
    
    public Topic(String topicName, Date startDate, Date endDate, List<Answer> answer) {
		super();
		this.topicName = topicName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.answer = answer;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Answer> getAnswerList() {
        return answer;
    }
	
    public void setAnswerList(List<Answer> answer) {
        this.answer = answer;
    }

	public Answer addAnswer(Answer answer) {
		getAnswerList().add(answer);
		answer.setTopic(this);

		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswerList().remove(answer);
		answer.setTopic(null);

		return answer;
	}
    
    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", topicName='" + topicName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", answer=" + answer +
                '}';
    }

}
