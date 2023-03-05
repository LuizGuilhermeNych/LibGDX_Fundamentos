/**
 * 
 */
package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.Constant.PPM;

/**
 * @author luiz.vale
 *
 */
public class Player extends GameEntity{

	private String estado;
	private boolean canJump;
	private Animation<TextureRegion> idle, walk, crouch, jump;
	public float stateTimer;
	
	public Player(float width, float height, Body body) {
		super(width, height, body);
		this.speed = 10f;
		
//		TextureAtlas idleAtlas;
//		idleAtlas = new TextureAtlas(Gdx.files.internal("spr_idle.atlas"));
//		idle = new Animation(0.2f, idleAtlas.getRegions());
//
//		TextureAtlas walkAtlas;
//		walkAtlas = new TextureAtlas(Gdx.files.internal("spr_walk.atlas"));
//		walk = new Animation(0.2f, idleAtlas.getRegions());
	}

	@Override
	public void update() {
		x = body.getPosition().x * PPM;
		y = body.getPosition().y * PPM;
		
		inputHandler();
		stateHandler();
		spriteHandler();
		System.out.println("LinearVeloc. Y : " + body.getLinearVelocity().y + " | " + "Estado : " + estado);
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	private void stateHandler() {
		if(body.getLinearVelocity().x == 0) {
			estado = "parado";
			canJump = true;
		}
		if(body.getLinearVelocity().x != 0) {
			estado = "movendo";
			canJump = true;
		}
		if(body.getLinearVelocity().y > 0) {
			estado = "pulando";
			canJump = false;
		}
		if(body.getLinearVelocity().y < 0) {
			estado = "caindo";
			canJump = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			estado = "agachado";
		}
		
	}
	
	private void spriteHandler() {
		
		TextureRegion region;
		switch (estado) {
		case "movendo":
			//Sprite: movendo
//			region = walk.getKeyFrame(stateTimer, true);
			break;
		case "pulando":
			//Sprite: pulando
			break;
		case "caindo":
			//Sprite: caindo
			break;
		case "agachado":
			//Sprite: agachado
		default:
			//Sprite: parado
//			region = idle.getKeyFrame(stateTimer);
			break;
		}
	}
	
	private void inputHandler() {
		velX = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			velX = 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			velX = -1;
		}
		if(canJump) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				float force = body.getMass() * 18;
				body.setLinearVelocity(body.getLinearVelocity().x, 0);
				body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
			}
		}
		
		body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
		
	}
}