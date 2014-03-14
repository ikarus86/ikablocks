package my.ikarus.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureSetup {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("../ikablocks-android/assets/","../ikablocks-android/assets/textures/", "textures");
    }
}


