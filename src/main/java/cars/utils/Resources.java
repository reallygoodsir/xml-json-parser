package cars.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class Resources {
    public File read(String fileName) throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return new File(Objects.requireNonNull(resource).toURI());
    }
}
