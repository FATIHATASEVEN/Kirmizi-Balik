package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;
public class Balik extends ApplicationAdapter {
	SpriteBatch batch;
	Texture sea;
	Texture fish;
	Texture fishenemy,shark,turtle;
	float fx,fy,fw,fh,dusx,dusy;
	float sw,sh;
	float gravity = 0.8f;
	float v = 0.0f;
	int state = 0;
	int zararli=3;
	float dusmanx[] = new float[zararli];
	float dusmany[][] = new float[3][zararli];
	Circle c_fish;
	Circle c_d1[] =new Circle[zararli];
	Circle c_d2[] =new Circle[zararli];
	Circle c_d3[] =new Circle[zararli];
	BitmapFont font;
	BitmapFont font1;

	boolean flag=true;
	boolean flag1=true;
	int puan;
	ShapeRenderer sr ;
	float d;
	@Override
	public void create () {
		batch = new SpriteBatch();
		sea = new Texture("sea.png");
		fish = new Texture("fish.png");
		fishenemy = new Texture("fishenemy.png");
		shark = new Texture("shark.png");
		turtle = new Texture("turtle.png");
		sw=Gdx.graphics.getWidth();
		sh=Gdx.graphics.getHeight();
		fx=sw/4;
		fy=sh/3;
		fw=sw/13;
		fh=sh/10;
		dusx=1200;
		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(8);

		font1 = new BitmapFont();
		font1.setColor(Color.BLACK);
		font1.getData().setScale(9);

		sr = new ShapeRenderer();
		c_fish = new Circle();
		c_d1 = new Circle[zararli];

		c_d2 = new Circle[zararli];

		c_d3 = new Circle[zararli];

		d = Gdx.graphics.getWidth()/2;

		for(int i=0;i< zararli;i++){
			dusmanx[i]=Gdx.graphics.getWidth()+i*d;
			Random r1= new Random();
			Random r2= new Random();
			Random r3= new Random();

			dusmany[0][i] = r1.nextFloat() * Gdx.graphics.getHeight();
			dusmany[1][i] = r2.nextFloat() * Gdx.graphics.getHeight();
			dusmany[2][i] = r3.nextFloat() * Gdx.graphics.getHeight();
			c_d1[i]=new Circle();
			c_d2[i]=new Circle();
			c_d3[i]=new Circle();
		}

	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(sea,0,0,sw,sh);
		batch.draw(fish,fx,fy,fw,fh);
		if(state==1){
			flag1=true;

			if(Gdx.input.justTouched()){
				v=-15;
			}
			for(int i=0;i< zararli;i++){
				if( dusmanx[i]<fw){
					dusmanx[i] += zararli*d;
					Random r1= new Random();
					Random r2= new Random();
					Random r3= new Random();

					dusmany[0][i] = r1.nextFloat() * Gdx.graphics.getHeight()-fh;
					dusmany[1][i] = r2.nextFloat() * Gdx.graphics.getHeight()-fh;
					dusmany[2][i] = r3.nextFloat() * Gdx.graphics.getHeight()-fh;
				}
				if(fx> dusmanx[i] && flag){
					puan++;
					System.out.println(puan);
					flag=false;
				}
				if( dusmanx[i]<fw+4){
					flag=true;
				}
				dusmanx[i] -=4;
				batch.draw(fishenemy, dusmanx[i],dusmany[0][i],fw,fh);
				batch.draw(turtle, dusmanx[i],dusmany[1][i],fw,fh);
				batch.draw(shark, dusmanx[i],dusmany[2][i],fw,fh);
			}
			if(fy<0){
				state = 2;
				fy=sh/3;
				v=0;
			}else{
				v = v + gravity;
				fy=fy-v;
			}
		}
		else if(state==2){
			font1.draw(batch,"Yeniden Denemek Icin Dokun",0,Gdx.graphics.getHeight()/2);

			if(flag1){

				flag1=false;
			}
			puan=0;
			v=0;

			if(Gdx.input.justTouched()){
				state=1;
				for(int i=0;i< zararli;i++){
					dusmanx[i]=Gdx.graphics.getWidth()+i*d;
					Random r1= new Random();
					Random r2= new Random();
					Random r3= new Random();

					dusmany[0][i] = r1.nextFloat() * Gdx.graphics.getHeight();
					dusmany[1][i] = r2.nextFloat() * Gdx.graphics.getHeight();
					dusmany[2][i] = r3.nextFloat() * Gdx.graphics.getHeight();
					c_d1[i]=new Circle();
					c_d2[i]=new Circle();
					c_d3[i]=new Circle();
				}

			}
		}
		else if(state == 0){
			flag1=true;

			font1.draw(batch,"Baslamak Icin Dokun",Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/2);

			puan=0;
			v=0;
			if(Gdx.input.justTouched()){
				state=1;
			}

		}
		font.draw(batch,String.valueOf(puan),Gdx.graphics.getWidth()-fw,fh);

		c_fish.set(fx+fw/2,fy+fh/2,fw/2);

		for (int i = 0 ; i < zararli; i++){
			c_d1[i].set( dusmanx[i]+fw/2,dusmany[0][i]+fh/2,fw/2);
			c_d2[i].set( dusmanx[i]+fw/2,dusmany[1][i]+fh/2,fw/2);
			c_d3[i].set( dusmanx[i]+fw/2,dusmany[2][i]+fh/2,fw/2);
			if(Intersector.overlaps(c_fish,c_d1[i]) || Intersector.overlaps(c_fish,c_d2[i]) || Intersector.overlaps(c_fish,c_d3[i])){
				state=2;
			}
		}
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
		sea.dispose();
	}
}
