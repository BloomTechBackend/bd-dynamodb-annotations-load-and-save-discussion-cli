package com.amazon.ata.dynamodbannotationsloadsave.handler;

import com.amazon.ata.dynamodbannotationsloadsave.cli.DiscussionCliOperation;
import com.amazon.ata.dynamodbannotationsloadsave.cli.DiscussionCliState;
import com.amazon.ata.dynamodbannotationsloadsave.dynamodb.TopicMessage;
import com.amazon.ata.dynamodbannotationsloadsave.dynamodb.TopicMessageDao;
import com.amazon.ata.string.TextTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handler for the VIEW_TOPIC operation.
 */
public class ViewTopicMessagesHandler implements DiscussionCliOperationHandler {
    private TopicMessageDao topicMessageDao;

    /**
     * Constructs handler with its dependencies.
     * @param topicMessageDao TopicMessageDao
     */
    public ViewTopicMessagesHandler(TopicMessageDao topicMessageDao) {
        this.topicMessageDao = topicMessageDao;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        if (null == state.getCurrentTopic()) {
            state.setNextOperation(DiscussionCliOperation.VIEW_TOPICS);
            return "Sorry, you must select a topic first.";
        }

        String topicName = state.getCurrentTopic().getName();
        List<TopicMessage> topicMessages = topicMessageDao.getMessagesForTopicName(topicName);
        state.setListedTopicMessages(topicMessages);
        return renderTopicMessages(topicMessages);
    }

    private String renderTopicMessages(List<TopicMessage> topicMessages) {
        List<String> headers = Arrays.asList("#", "Author", "Timestamp", "Message");
        List<List<String>> rows = new ArrayList<>();
        int messageNum = 0;
        for (TopicMessage topicMessage : topicMessages) {
            List<String> row = new ArrayList<>();
            row.add(Integer.toString(messageNum++));
            row.add(topicMessage.getAuthor());
            row.add(topicMessage.getTimestamp());
            row.add(topicMessage.getMessageContent());
            rows.add(row);
        }

        return new TextTable(headers, rows).toString();
    }
}
