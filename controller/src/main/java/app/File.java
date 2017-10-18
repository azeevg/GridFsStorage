package app;

import org.hibernate.validator.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

public class File {
    private String filename;

    public File(@NotNull final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(@NotNull final String filename) {
        this.filename = filename;
    }
}
