﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScoreManager : MonoBehaviour
{
    // Start is called before the first frame update

    Dictionary< string, Dictionary<string, int> >playerScores;
    void Start()
    {	
	
	SetScore("quill18","kills",9001);
	Debug.Log(GetScore("quill18","kills"));
    }

    void Init(){
	if(playerScores != null)
	return;
	
	playerScores = new Dictionary<string ,Dictionary<string ,int>>();

	}
	public int GetScore(string username, string scoreType)
	{
		Init();
		if(playerScores.ContainsKey(username)== false)
		{
			return 0;

		}
		if(playerScores[username].ContainsKey(scoreType)== false)
		{
			return 0;

		}
		return playerScores[username][scoreType];

	}
	public void SetScore(string username , string scoreType , int value)
	{
		Init();
		if(playerScores.ContainsKey(username)== false)
		{
			playerScores[username]= new Dictionary<string ,int>();
		}
		playerScores[username][scoreType]= value;
	}
	public void ChangeScore(string username, string scoreType, int amount)
	{
		Init();
		int currScore= GetScore(username, scoreType);
		SetScore(username, scoreType,currScore+ amount);
	}
}
