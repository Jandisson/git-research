package usp.br.jandisson.gitresearch.step.sonar;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pageIndex",
        "pageSize",
        "total"
})
public class Paging {

    @JsonProperty("pageIndex")
    private Integer pageIndex;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("total")
    private Integer total;


    @JsonProperty("pageIndex")
    public Integer getPageIndex() {
        return pageIndex;
    }

    @JsonProperty("pageIndex")
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }



}