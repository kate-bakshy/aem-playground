package com.kb.playground.core.workflows;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import static com.adobe.granite.workflow.PayloadMap.TYPE_JCR_PATH;


@Component (
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label=Playground: Delete nodes process",
                Constants.SERVICE_DESCRIPTION + "=Delete nodes according to the provided CSV file"
        })
public class DeleteNodesAccToFileProcess implements WorkflowProcess {
    
    @Reference
    private JobManager jobManager;
    
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) {
        final WorkflowData workflowData = workItem.getWorkflowData();
        final String payloadType = workflowData.getPayloadType();
        final String payloadPath = workflowData.getPayload().toString();
        
        if (StringUtils.isNotBlank(payloadPath) && TYPE_JCR_PATH.equals(payloadType)) {
            Map<String, Object> jobProperties = new HashMap<>();
            jobProperties.put("filePath", payloadPath);
            jobManager.addJob("node_removal_by_path/job", jobProperties);
        }
    }
}
