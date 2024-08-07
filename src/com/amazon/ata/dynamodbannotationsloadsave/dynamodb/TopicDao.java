package com.amazon.ata.dynamodbannotationsloadsave.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to Topic items.
 */
public class TopicDao {
    private DynamoDBMapper mapper;

    /**
     * Constructs a TopicDao with the given DynamoDBMapper.
     * @param mapper the DynamoDBMapper
     */
    public TopicDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Creates a new topic from the Topic object provided.
     * @param topic the new Topic
     * @return The topic that was created
     */
    public Topic createTopic(Topic topic) {

        return null;

    }

    /**
     * Returns a list of up to {@code numTopics}.
     * @param numTopics The maximum number of topics to be returned. There is no
     *                  guaranteed that this many will be returned, even if there are
     *                  more than this number in the table.
     * @return The Topics
     */
    public List<Topic> getTopics(int numTopics) {
        List<Topic> topics = mapper.scan(Topic.class, new DynamoDBScanExpression());
        int endIndex = Integer.min(numTopics, topics.size());
        return new ArrayList<>(topics.subList(0, endIndex));
    }
}
