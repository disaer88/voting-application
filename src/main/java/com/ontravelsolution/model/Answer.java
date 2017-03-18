package com.ontravelsolution.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table (name = "answer")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Answer {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column (name="answer_name")
    private String answerName;
    
    @Column (name="count_voices")
    private int countVoice;
    
    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;

    public Answer() {
		super();
    }
    
    public Answer(String answerName, int countVoice, Topic topic) {
		super();
		this.answerName = answerName;
		this.countVoice = countVoice;
		this.topic = topic;
	}
    
    @Column (name = "id_answer")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public int getCountVoice() {
        return countVoice;
    }

    public void setCountVoice(int countVoice) {
        this.countVoice = countVoice;
    }
 
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerName='" + answerName + '\'' +
                ", countVoice=" + countVoice +
                ", topic=" + topic +
                '}';
    }
}
