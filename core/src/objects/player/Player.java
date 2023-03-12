/**
 * 
 */
package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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

	private Texture tex;

	private enum State {IDLE, WALKING, JUMPING, FALLING, CROUCHING};
	public State currentState;
	public State previousState;
	public boolean walkingRight;
	private Animation<TextureRegion> idle;
	private Animation<TextureRegion> walk;
	private Animation<TextureRegion> currentAnimation;
	public float stateTimer;
	
	public Player(float width, float height, Body body) {
		super(width, height, body);
		this.speed = 10f;

		currentState = State.IDLE;
		previousState = State.IDLE;
		walkingRight = true;
		stateTimer = 0;

		tex = new Texture("fr_id_0.png");
		
		TextureAtlas idleAtlas;
		idleAtlas = new TextureAtlas(Gdx.files.internal("hunter/spr_idle.atlas"));
		idle = new Animation(0.2f, idleAtlas.getRegions());

		TextureAtlas walkAtlas;
		walkAtlas = new TextureAtlas(Gdx.files.internal("hunter/spr_walk.atlas"));
		walk = new Animation(0.2f, idleAtlas.getRegions());

		currentAnimation = idle;
	}
	@Override
	public void update() {
		x = body.getPosition().x * PPM;
		y = body.getPosition().y * PPM;

		inputHandler();
		stateHandler();
//		System.out.println("LinearVeloc. Y : " + body.getLinearVelocity().y + " | " + "Estado : " + estado);
	}

	@Override
	public void render(SpriteBatch batch) {
//		stateTimer += Gdx.graphics.getDeltaTime();
//
//		switch (currentState){
//			case IDLE:
//				setRegion(idle.getKeyFrame(stateTimer));
//				break;
//			case WALKING:
//				setRegion(walk.getKeyFrame(stateTimer, true));
//		}
		batch.draw(tex, body.getPosition().x * PPM - (tex.getWidth() / 2), body.getPosition().y * PPM - (tex.getHeight() / 2) - 10);
	}

	public State stateHandler() {
		if(body.getLinearVelocity().x == 0) {
			currentState = State.IDLE;
			estado = "parado";
			canJump = true;
		}
		if(body.getLinearVelocity().x != 0) {
			currentState = State.WALKING;
			estado = "movendo";
			canJump = true;
		}
		if(body.getLinearVelocity().y > 0) {
			currentState = State.JUMPING;
			estado = "pulando";
			canJump = false;
		}
		if(body.getLinearVelocity().y < 0) {
			currentState = State.FALLING;
			estado = "caindo";
			canJump = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			currentState = State.CROUCHING;
			estado = "agachado";
		}
		return currentState;
	}
	
	public TextureRegion spriteHandler(float dt) {

		currentState = stateHandler();
		TextureRegion region = null;

		switch (currentState) {
		case WALKING:
			//Sprite: movendo
			region = walk.getKeyFrame(stateTimer, true);
			break;
		case JUMPING:
			//Sprite: pulando
			break;
		case FALLING:
			//Sprite: caindo
			break;
		case CROUCHING:
			//Sprite: agachado
		default:
			//Sprite: parado
			region = idle.getKeyFrame(stateTimer, true);
			break;
		}
		return region;
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