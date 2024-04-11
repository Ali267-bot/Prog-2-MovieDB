package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getImage(String url) {
        return cache.computeIfAbsent(url, k -> new Image(k, true));
    }
}

