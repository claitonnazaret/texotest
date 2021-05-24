package br.com.texo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    private List<ResultObjectDTO> min;
    private List<ResultObjectDTO>  max;

    public ResultDTO(List<ResultObjectDTO>  min, List<ResultObjectDTO>  max) {
        this.min = min;
        this.max = max;
    }
}
