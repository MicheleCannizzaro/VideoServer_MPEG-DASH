package com.dslab.homework1.videomanagementservice.entity;


import java.io.Serializable;

public enum VideoStatus implements Serializable {
    WAITING_UPLOAD,
    UPLOADED,
    AVAILABLE,
    NOT_AVAILABLE
}
