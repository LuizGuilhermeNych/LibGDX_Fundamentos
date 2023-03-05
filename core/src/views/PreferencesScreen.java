package views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.fundamentals2.Boot;

public class PreferencesScreen implements Screen {


    private Boot parent;
    private Stage stage;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public PreferencesScreen(Boot boot){
        parent = boot;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
            volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
            volumeMusicSlider.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                        return false;
                }
            });

        final Slider soundSlider = new Slider(0f, 1f, 0.1f, false, skin);
            soundSlider.setValue(parent.getPreferences().getSoundVolume());
            soundSlider.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    parent.getPreferences().setSoundVolume(soundSlider.getValue());
                    return false;
                }
            });

        final CheckBox musicCheckBox = new CheckBox(null, skin);
            musicCheckBox.setChecked(parent.getPreferences().isMusicEnabled());
            musicCheckBox.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = musicCheckBox.isChecked();
                    parent.getPreferences().setMusicEnabled(enabled);
                    return false;
                }
            });

        final CheckBox soundCheckBox = new CheckBox(null, skin);
        soundCheckBox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckBox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        final TextButton backButton = new TextButton("back", skin, "small");
            backButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.changeScreen(Boot.MENU);
                }
            });
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
