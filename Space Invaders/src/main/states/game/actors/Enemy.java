package main.states.game.actors;

import java.awt.image.BufferedImage;
import java.util.Random;

import main.states.game.Actor;
import main.states.game.Stage;
import main.states.game.actors.powerups.ExtraLife;
import main.states.game.actors.powerups.Repair;
import main.states.game.actors.powerups.SpeedShot;
import engine.graphics.Animation;

public class Enemy extends Actor {
	public static float vx = 1;
	public static float vy = 0;
	int enemyNumber;
	public static int enemies;
	static double lastShot;
	static int random;
	static int delay = 500;
	static int lastShooterNumber;
	static int nextShooterNumber = -1;
	static int reevaluate;
	int type;
	int points;
	Random r;
	static boolean shootTimeAllowed;
	static BufferedImage[][] images = {{image("Enemy0#1.png"),image("Enemy0#2.png")},
										{image("Enemy1#1.png"),image("Enemy1#2.png")},
										{image("Enemy2#1.png"),image("Enemy2#2.png")}};
	public static boolean moveDown;
	public Enemy(Stage stage, int type)
	{
		super(stage);
		super.animated = true;
		this.type = type;
		points = (int) (Math.pow(2,type)*10);
		enemies++;
		delay = 500 * enemies;
		enemyNumber = enemies;
		/*try {
			image = ImageIO.read(getClass().getClassLoader().getResource("data/Enemy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		setWidth(images[type][0].getWidth());
		setHeight(images[type][0].getHeight());
		r = new Random();
		nextShooterNumber = r.nextInt(enemies)+1;
		animation = new Animation(images[type],500);
		vx+=stage.getLevel()/2;
	}
	public void update(int delta)
	{
		animation.advance(delta);
		reevaluate++;
		enemyNumber = reevaluate;
		if(reevaluate>=enemies)reevaluate = 0;
		if(x >=650&&vx>0){vx = -vx;moveDown = true;}
		if(x <=0&&vx<0){vx = -vx;moveDown = true;}
		if(y+getHeight() >=400){stage.lose();}
		if(System.currentTimeMillis()-lastShot>=delay/enemies)shootTimeAllowed = true;
		if(shootTimeAllowed&nextShooterNumber==enemyNumber)shoot();
		if(shootTimeAllowed&lastShooterNumber==enemyNumber)shoot();
		super.vx = Enemy.vx;
		super.vy = Enemy.vy;
		super.update(delta);
		//System.out.println(enemyNumber+","+nextShooterNumber);
	}
	public void collide(Actor a)
	{
		if (a instanceof Bullet&!MarkedForRemoval&!a.MarkedForRemoval)
		{
			if(((Bullet) a).playerBullet){
				remove();
				enemies--;
				stage.kill(points);
				if (enemies <=0){ stage.victory();}else{
				powerupdrop();
				}
			}
		}
	}
	public void shoot()
	{
		Bullet bullet = new Bullet(stage,false);
		bullet.move((int)x+getWidth()/2,(int)y+getHeight());
		stage.addActor(bullet);
		lastShot = System.currentTimeMillis();
		lastShooterNumber = nextShooterNumber;
		nextShooterNumber = r.nextInt(enemies)+1;
		//System.out.println("next"+nextShooterNumber);
		shootTimeAllowed = false;
	}
	public void powerupdrop()
	{
		if(r.nextInt(20)==1)
		{
			SpeedShot ss = new SpeedShot(stage);
			ss.move((int)x,(int)y+getHeight());
			stage.addActor(ss);
		}else if(r.nextInt(50)==1){	
			ExtraLife el = new ExtraLife(stage);
			el.move((int)x,(int)y+getHeight());
			stage.addActor(el);
		}else if(r.nextInt(50)==1)
		{
			Repair r = new Repair(stage);
			r.move((int)x,(int)y+getHeight());
			stage.addActor(r);
		}
	}
	public static BufferedImage image(String url)
    {
    	BufferedImage image = null;
    	image = Stage.imgCache.load("data/"+url);
    	return image;
    }
}
