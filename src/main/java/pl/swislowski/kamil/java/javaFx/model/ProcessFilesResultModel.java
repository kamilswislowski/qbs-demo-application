package pl.swislowski.kamil.java.javaFx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessFilesResultModel {
    private Path tempDirPath;
}
