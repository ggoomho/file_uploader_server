package com.chols.fu.monitor;

import java.util.HashMap;
import java.util.Map;

import com.chols.fu.error.CustomException;
import com.chols.fu.error.ErrorCode;

public class ProgressMonitor {
    private Map<Long, FileUploadProgress> progressMap;

    public ProgressMonitor() {
        this.progressMap = new HashMap<>();
    }

    public FileUploadProgress addProgress(long fileId, int total) {
        FileUploadProgress progress = new FileUploadProgress(total);
        this.progressMap.put(fileId, progress);
        return progress;
    }

    public void removeProgress(long fileId) {
        if(this.progressMap.get(fileId) == null) {
            throw new CustomException(ErrorCode.PROGRESS_NOT_FOUND);
        }

        this.progressMap.remove(fileId);
    }

    public FileUploadProgress getProgress(Long fileId) {
        FileUploadProgress progress = progressMap.get(fileId);

        if(progress == null) {
            throw new CustomException(ErrorCode.PROGRESS_NOT_FOUND);
        }

        return progressMap.get(fileId);
    }
}
