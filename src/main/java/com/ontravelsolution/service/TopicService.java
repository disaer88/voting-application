package com.ontravelsolution.service;

import java.util.List;
import com.ontravelsolution.model.Topic;

public interface TopicService {
	
	public Topic findById(long id);
	public void saveTopic(Topic topic);
	public void updateTopic(Topic topic);
	public void deleteTopicById(long id);
	public List<Topic> findAllTopics();
	public void deleteAllTopics();
	
}
