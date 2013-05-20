package main.states.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

import main.states.game.actors.LoopMonster;
import main.states.game.actors.RepeatMonster;

public class Level {
	int[][] level;
	int height,width;
	int StartX,StartY;
	BufferedImage LevelImage;
	public Level (int lvl, Stage stage)
	{
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("data/levels/level"+lvl+".txt")));
			String line = "";
			while((line = in.readLine())!=null)
			{
				if(line.equals("<height>"))
				{
					height = Integer.parseInt(in.readLine());
				}else if (line.equals("<map>"))
				{
					in.mark(20);
					width = in.readLine().length();
					in.reset();
					level = new int[width][height];
					for(int y = 0;y<height;y++)
					{
						String row = in.readLine();
						for(int x = 0;x<width;x++)
						{
							level[x][y] = Character.getNumericValue(row.charAt(x));
							if(level[x][y]==2)
							{
								StartX = x;
								StartY = y;
							}
						}
					}
				}else if(line.equals("<monster>"))
				{
					LinkedList<Point> path = new LinkedList<Point>();
					boolean repeat = false;
					if(Integer.parseInt(in.readLine())==1)repeat = true;
					while(!((line = in.readLine()).equals("</monster>")))
					{
						String[] coords = line.split(",");
						path.add(new Point(Integer.parseInt(coords[0]),Integer.parseInt(coords[1])));
					}
					if(repeat)
						{
							RepeatMonster m = new RepeatMonster(stage,path.listIterator());
							stage.addActor(m);
						}else
						{
							LoopMonster m = new LoopMonster(stage,path);
							stage.addActor(m);
						}
					
				}
			}
			in.close();
		}catch(Exception e){e.printStackTrace();};
	}
	public void GenerateImage(Image LevelImage)
	{
		Graphics LevelGraphics = LevelImage.getGraphics();
		for(int x = 0;x<width;x++)
		{
			for (int y=0;y<height;y++)
			{
				LevelGraphics.drawImage(Stage.imgCache.load("data/images/levels/tile"+level[x][y]+".png"),x*32,y*32,null);
			}
		}
		this.LevelImage = (BufferedImage)LevelImage;
	}
}
