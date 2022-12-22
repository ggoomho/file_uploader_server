package com.chols.fu.monitor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadProgress {
    private final int total;
    private int finished;
    private int processing;
    private int error;
    private boolean isCompleted = false;

    private int progress;
    private int buffer;

    public FileUploadProgress(int total) {
        this.total = total;
    }

    public void completeProccessing() {
        finished += processing;
        processing = 0;
        progress = (finished * 100) / total;
        buffer = (processing * 100) / total;
    }

    public void process(int processing) {
        this.processing += processing;
        buffer = (processing * 100) / total;
    }
}
