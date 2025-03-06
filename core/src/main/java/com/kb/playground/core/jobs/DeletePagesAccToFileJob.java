package com.kb.playground.core.jobs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

/**
 * Sling job to delete nodes according to the paths provided in the CSV file
 */

@Component(
        service = JobConsumer.class,
        immediate = true,
        property = {
                JobConsumer.PROPERTY_TOPICS + "=node_removal_by_path/job"
        })
public class DeletePagesAccToFileJob implements JobConsumer {
    
    private static final String CSV_FILE_EXTENSION = ".csv";
    private static final String PLAYGROUND_WORKFLOW_USER = "playground-workflow-user";
    private static final String PAYLOAD_FILE_PATH = "filePath";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletePagesAccToFileJob.class);
    
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    
    @Reference
    Replicator replicator;
    
    @Override
    public JobResult process (Job job) {
        
        final String payloadPath = job.getProperty(PAYLOAD_FILE_PATH).toString();
        final Map<String, Object> authInfo = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, PLAYGROUND_WORKFLOW_USER);
        
        if (StringUtils.isNotBlank(payloadPath) && payloadPath.endsWith(CSV_FILE_EXTENSION)) {
         
            try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(authInfo)) {
                Resource fileResource = resourceResolver.getResource(payloadPath);
                if (fileResource != null) {
                    Asset file = fileResource.adaptTo(Asset.class);
                    InputStream fileStream = file.getOriginal().getStream();
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream));
                    
                    Session session = resourceResolver.adaptTo(Session.class);
                    
                    String nodePath = StringUtils.EMPTY;
                    while ((nodePath = fileReader.readLine()) != null) {
                        if (StringUtils.isNotBlank(nodePath)) {
                            Resource resourceToDelete = resourceResolver.getResource(nodePath);
                            if (resourceToDelete != null) {
                                try {
                                    if (replicator.getReplicationStatus(session, nodePath).isActivated()) {
                                        replicator.replicate(session, ReplicationActionType.DEACTIVATE, nodePath);
                                    }
                                    resourceResolver.delete(resourceToDelete);
                                    LOGGER.info("Node {} was successfully deleted.", nodePath);
                                } catch (PersistenceException|ReplicationException e) {
                                    LOGGER.error("Error during deletion of the node {}", resourceToDelete.getPath(), e);
                                }
                            }
                        }
                    }
                    
                    fileReader.close();
                    
                    if (resourceResolver.hasChanges()) {
                        resourceResolver.commit();
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error during node deletion process.", e);
                return JobResult.FAILED;
            }
        }
        return JobResult.OK;
    }
}
