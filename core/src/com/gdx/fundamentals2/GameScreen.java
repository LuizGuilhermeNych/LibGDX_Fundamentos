/**
 * 
 */
package com.gdx.fundamentals2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import helper.TileMapHelper;
import objects.player.Player;

import static helper.Constant.PPM;

/**
 * @author luiz.vale
 *
 */
public class GameScreen extends ScreenAdapter{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer box2DebugRenderer;
	
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private TileMapHelper tileMapHelper;
	
	private Player player;

	private Boot parent;
	
	public GameScreen(OrthographicCamera camera, Boot boot) {
		parent = boot;
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.world = new World(new Vector2(0, -25f), false);
		this.box2DebugRenderer = new Box2DDebugRenderer();
		
		this.tileMapHelper = new TileMapHelper(this);
		this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
	}
	
	public void update() {
		world.step(1 / 60f, 6, 2);
		cameraUpdate();
		
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);
		player.update();
	}
	
	private void cameraUpdate() {
		Vector3 position = camera.position;
		position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
		position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
		camera.position.set(position);
		camera.update();
	}

	@Override
	public void render(float delta) {
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		orthogonalTiledMapRenderer.render();
		
		batch.begin();
		player.render(batch);
		batch.end();
		box2DebugRenderer.render(world, camera.combined.scl(32.0f));
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void show() {

	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}
}