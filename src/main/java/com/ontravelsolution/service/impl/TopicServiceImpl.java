package com.ontravelsolution.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ontravelsolution.model.Topic;
import com.ontravelsolution.repository.TopicRepository;
import com.ontravelsolution.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService{

	@Autowired
	private TopicRepository topicRepository;
	
	@Override
	public Topic findById(long id) {
		return topicRepository.findOne(id);
	}

	@Override
	@Transactional
	public void saveTopic(Topic topic) {
		topicRepository.save(topic);		
	}

	@Override
	@Transactional
	public void updateTopic(Topic topic) {
		topicRepository.saveAndFlush(topic);		
	}

	@Override
	@Transactional
	public void deleteTopicById(long id) {
		topicRepository.delete(id);		
	}

	@Override
	public List<Topic> findAllTopics() {
		return topicRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteAllTopics() {
		topicRepository.deleteAll();		
	}

}
