package org.subscribe.master.exceptionHandling;

import java.time.Instant;

public class ErrorResponseDTO {

    private String title;
    private int status;
    private String detail;
    private String path;
    private Instant timestamp;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String title, int status, String detail, String path, Instant timestamp) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.path = path;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
