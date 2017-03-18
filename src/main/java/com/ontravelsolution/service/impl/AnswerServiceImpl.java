package com.ontravelsolution.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ontravelsolution.model.Answer;
import com.ontravelsolution.repository.AnswerRepository;
import com.ontravelsolution.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	private AnswerRepository answerRepository;
	
	@Override
	public Answer findById(long id) {
		return answerRepository.findOne(id);
	}

	@Override
	@Transactional
	public void saveAnswer(Answer answer) {
		answerRepository.save(answer);
	}

	@Override
	@Transactional
	public void updateAnswer(Answer answer) {
		answerRepository.saveAndFlush(answer);
	}

	@Override
	@Transactional
	public void deleteAnswerById(long id) {
		answerRepository.delete(id);
	}

	@Override
	public List<Answer> findAllAnswersByTopicId(long id) {
		return answerRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteAllAnswersByTopicId(long id) {
		answerRepository.deleteAll();
	}

}
