package com.ontravelsolution.service;

import java.util.List;

import com.ontravelsolution.model.Answer;

public interface AnswerService {
	public Answer findById(long id);
	public void saveAnswer(Answer answer);
	public void updateAnswer(Answer answer);
	public void deleteAnswerById(long id);
	public List<Answer> findAllAnswersByTopicId(long id);
	public void deleteAllAnswersByTopicId(long id);
}
