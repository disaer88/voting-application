package com.ontravelsolution.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontravelsolution.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{

}
