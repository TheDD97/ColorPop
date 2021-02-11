package model;

import javafx.scene.image.Image;

import java.util.HashMap;

public class Flyweight {
    private HashMap<String, Image> image = new HashMap<>();

    public Image getImage(String img) {
        if (!image.containsKey(img))
            image.put(img, new Image("/images/" + img + ".png"));
        return image.get(img);
    }
}
