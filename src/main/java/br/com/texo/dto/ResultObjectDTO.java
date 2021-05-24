package br.com.texo.dto;

import lombok.Data;

public interface ResultObjectDTO {
    String getProducer();
    Long getInterval();
    String getPreviousWin();
    String getFollowingWin();
}
