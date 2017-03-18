package com.ontravelsolution.controller;

import java.util.Date;
import java.util.List;

import com.ontravelsolution.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ontravelsolution.errors.CustomErrorType;
import com.ontravelsolution.model.Answer;
import com.ontravelsolution.model.Topic;
import com.ontravelsolution.service.TopicService;

@RestController
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	TopicService topicService;

	@Autowired
	AnswerService answerService;

	// -------------------Retrieve All Topics---------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/topic/all", method = RequestMethod.GET)
	public ResponseEntity<List<Topic>> listAllTopics() {
		List<Topic> topics = topicService.findAllTopics();
		if (topics.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Topics not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Topic>>(topics, HttpStatus.OK);
	}

	// -------------------Retrieve a Single Topic------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTopic(@PathVariable("id") long id) {
		logger.info("Fetching Topic with id {}", id);
		Topic topic = topicService.findById(id);
		if (topic == null) {
			logger.error("Topic with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Topic with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Topic>(topic, HttpStatus.OK);
	}

	// -------------------Create the Topic-------------------------------------------
	@RequestMapping(value = "topic/create", method = RequestMethod.POST)
	public ResponseEntity<?> createTopic(@RequestBody Topic topic, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Topic : {}", topic);
		topic.setStartDate(new Date());
		topicService.saveTopic(topic);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update the Topic ------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "topic/{id}/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTopic(@PathVariable("id") long id, @RequestBody Topic topic) {
		logger.info("Updating User with id {}", id);

		Topic currentTopic = topicService.findById(id);

		if (currentTopic == null) {
			logger.error("Unable to update. Topic with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Topic with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentTopic.setTopicName(topic.getTopicName());
		currentTopic.setAnswerList(topic.getAnswerList());
		currentTopic.setStartDate(topic.getStartDate());
		currentTopic.setEndDate(topic.getEndDate());
	
		topicService.updateTopic(currentTopic);
		return new ResponseEntity<Topic>(currentTopic, HttpStatus.OK);
	}

	// ------------------- Delete the Topic-----------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "topic/{id}/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTopic(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Topic with id {}", id);

		Topic topic = topicService.findById(id);
		if (topic == null) {
			logger.error("Unable to delete. Topic with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Topic with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		topicService.deleteTopicById(id);
		return new ResponseEntity<Topic>(HttpStatus.OK);
	}

	// ------------------- Delete All Topics-----------------------------
	@RequestMapping(value = "topic/deleteall", method = RequestMethod.DELETE)
	public ResponseEntity<Topic> deleteAllTopics() {
		logger.info("Deleting All Topics");
		topicService.deleteAllTopics();
		return new ResponseEntity<Topic>(HttpStatus.NO_CONTENT);
	}

    // ------------------- Close the Topic ------------------------------------------------
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "topic/{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<?> closeTopic(@PathVariable("id") long id) {
        logger.info("Updating User with id {}", id);
        Topic currentTopic = topicService.findById(id);

        if (currentTopic == null) {
            logger.error("Unable to close voting. Topic with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to close voting." +
                    " Topic with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentTopic.setEndDate(new Date());
        topicService.updateTopic(currentTopic);
        return new ResponseEntity<Topic>(currentTopic, HttpStatus.OK);
    }

	// -------------------Create a Answer-------------------------------------------
	@RequestMapping(value = "topic/{id}/createanswer", method = RequestMethod.POST)
	public ResponseEntity<?> createAnswer(@RequestBody Answer answer, UriComponentsBuilder ucBuilder,
                                          @PathVariable("id") long id) {
		logger.info("Creating Answer : {}", answer);
		Topic topic = topicService.findById(id);
		answer.setTopic(topic);
		answerService.saveAnswer(answer);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri());
		return new ResponseEntity<Topic>(topicService.findById(id), HttpStatus.CREATED);
	}

	// ------------------- Update a answer ------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "topic/{id}/updateanswer/{id_answer}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAnswer(@PathVariable("id_answer") long id_answer, @RequestBody Answer answer,
                                          @PathVariable("id") long id) {
		logger.info("Updating answer with id {}", id_answer);
		Answer currentAnswer = answerService.findById(id_answer);

		if (currentAnswer == null) {
			logger.error("Unable to update. Answer with id {} not found.", id_answer);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Answer with id " + id_answer + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentAnswer.setAnswerName(answer.getAnswerName());
		currentAnswer.setCountVoice(answer.getCountVoice());
		answerService.updateAnswer(currentAnswer);

		return new ResponseEntity<Topic>(topicService.findById(id), HttpStatus.OK);
	}

	// ------------------- vote the answer ------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "topic/{id}/vote/{id_answer}", method = RequestMethod.PUT)
	public ResponseEntity<?> voteForAnswer(@PathVariable("id") long id, @PathVariable("id_answer") long id_answer) {
		logger.info("vote for answer with id {}", id_answer);
		Answer currentAnswer = answerService.findById(id_answer);
		Topic topic = topicService.findById(id);

        if (topic == null) {
            logger.error("Unable to vote. Topic with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to vote. Topic with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        if (topic.getEndDate() != null) {
            logger.error("Unable to vote. The Topic voting already over");
            return new ResponseEntity(new CustomErrorType("Unable to vote. The Topic voting already over"),
                    HttpStatus.OK);
        }

		if (currentAnswer == null) {
			logger.error("Unable to vote. Answer with id {} not found.", id_answer);
			return new ResponseEntity(new CustomErrorType("Unable to vote. Answer with id " + id_answer + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentAnswer.setCountVoice(currentAnswer.getCountVoice() + 1);
		answerService.updateAnswer(currentAnswer);
        return new ResponseEntity<Topic>(topic, HttpStatus.OK);
	}


}