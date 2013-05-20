package main.states.game.utils;

import org.gamejolt.GameJoltAPI;

public class SubmitThread implements Runnable {
	String name;
	String userToken;
	GameJoltAPI api;
	int score = 0;
	int trophy = 0;
	public SubmitThread(String name, String userToken, GameJoltAPI api, int score)
	{
		this.name = name;
		this.userToken = userToken;
		this.api = api;
		this.score = score;
	}
	public SubmitThread(String name, String userToken, int trophy, GameJoltAPI api)
	{
		this.name = name;
		this.userToken = userToken;
		this.api = api;
		this.trophy = trophy;
	}
	public void run()
	{
		if (api.verifyUser(name, userToken)) 
	    {
		 if(score!=0)
		 {
			 api.addHighscore(""+score, score);
		 }else{
			 api.achieveTrophy(trophy);
		 }
	    }
	}
}
