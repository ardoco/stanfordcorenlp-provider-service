/* Licensed under MIT 2023. */
package stanfordnlp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class IncomingDependencyDTO {
    private DependencyType dependencyType;
    private long sourceWordId;

    @JsonProperty("dependencyType")
    public DependencyType getDependencyType() {
        return dependencyType;
    }

    @JsonProperty("dependencyType")
    public void setDependencyType(DependencyType value) {
        this.dependencyType = value;
    }

    @JsonProperty("sourceWordId")
    public long getSourceWordId() {
        return sourceWordId;
    }

    @JsonProperty("sourceWordId")
    public void setSourceWordId(long value) {
        this.sourceWordId = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        IncomingDependencyDTO that = (IncomingDependencyDTO) o;
        return sourceWordId == that.sourceWordId && dependencyType == that.dependencyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependencyType, sourceWordId);
    }
}
