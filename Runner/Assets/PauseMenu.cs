using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PauseMenu : MonoBehaviour
{
    private GameObject items;
    public static bool GameIsPaused=false;
    public GameObject pauseMenuUI; 
   	

    // Update is called once per frame
    void Update()
    {
        if(Input.GetKeyDown(KeyCode.Escape))
	{
		if(GameIsPaused)
		{
			Resume();

		}
		else
		{
			Pause();

		}
	}

    }
   
   void   Resume()
    {	
	pauseMenuUI.SetActive(false);
	Time.timeScale  = 1f;
	GameIsPaused=false;
    }	
    void  Pause()
    {	
	pauseMenuUI.SetActive(true);
	Time.timeScale = 0f;
	//for (int i = 0; i < items.Length; i++)
		Destroy(items,0.5f);//Destroy(items[i],5f);
	GameIsPaused=true;
	
    }
    
    public void LoadMenu()
    {	
	Time.timeScale = 1f;
	SceneManager.LoadScene("Gameplay/Menu");
    }	
	
    public void quit()
    {
	Debug.Log("quiting game");
	Application.Quit();
    }		
}
