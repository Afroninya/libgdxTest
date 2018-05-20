package execution;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Console {

    //TODO: finish
    //SpriteBatch sb;
    BitmapFont font;

    public Console(SpriteBatch sb) {
        //this.sb = sb;
        font = new BitmapFont();
    }

    public void render(SpriteBatch sb) {
        font.draw(sb, "TestXY", 30, 30);
    }

}
