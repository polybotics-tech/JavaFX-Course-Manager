package com.cos201.coursemanager.utils;

import javafx.scene.text.Font;

public final class FontLoader {
    private FontLoader() {}

    /**
    * Bundles and loads all custom fonts
    */
    public static void load() {
        Font.loadFont(FontLoader.class.getResourceAsStream("/com/cos201/coursemanager/fonts/Poppins-Regular.ttf"), 12);
        Font.loadFont(FontLoader.class.getResourceAsStream("/com/cos201/coursemanager/fonts/Poppins-Medium.ttf"), 12);
        Font.loadFont(FontLoader.class.getResourceAsStream("/com/cos201/coursemanager/fonts/Poppins-SemiBold.ttf"), 12);
        Font.loadFont(FontLoader.class.getResourceAsStream("/com/cos201/coursemanager/fonts/Poppins-Bold.ttf"), 12);
    }
}
