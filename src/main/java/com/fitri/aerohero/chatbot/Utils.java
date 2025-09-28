package com.fitri.aerohero.chatbot;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class Utils {

    //converts a relative folder path ("rag")into a full path object
    //used to find files inside the src/main/resources at runtime
    public static Path toPath(String relativePath) {
        try {
            //get the resource URL from the classpath
            URL fileUrl = Utils.class.getClassLoader().getResource(relativePath);
            
            //throw error if the folder doesn't exist
            if (fileUrl == null) throw new RuntimeException("Cannot find resource: " + relativePath);
            
            //convert the URL to a proper filesystem Path
            return Paths.get(fileUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve path: " + relativePath, e);
        }
    }

    // Creates a glob pattern matcher (like "*.txt", "data/*.json", etc.)
    // Used to filter files when loading documents
    public static PathMatcher glob(String pattern) {
        return FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }
}
