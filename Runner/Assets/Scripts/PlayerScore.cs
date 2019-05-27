using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


public class PlayerScore : MonoBehaviour
{
    private Text scoreText;

    public static int score = 0;
   // private  Text TargetText; 
   // private int target;
    private Text Text;
    public GameObject completeLevelUI;
    
    //public int x;

    // Start is called before the first frame update
    void Awake()
    {
        scoreText = GameObject.Find("ScoreText").GetComponent<Text>();
        scoreText.text= "0";
	//TargetText=GameObject.Find("targetText").GetComponent<Text>();
	//TargetText = GameObject.Find("targetText").GetComponent<Text>();
	//target=Random.Range(1,3);
	
	//Text = items[3].GetComponent<Text>();
	//Text.text = "50";	
    }

    private void OnTriggerEnter2D(Collider2D target)
    {	
	
        if(target.tag == "Coin")
        {
            score++;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "Rocket")
        {
            transform.position = new Vector3(0, 1000, 0);

	    FindObjectOfType<AudioManager>().Play("PlayerDeath");
            target.gameObject.SetActive(false);
	    
	    

            StartCoroutine(RestartGame());
        }
	/*if(target.tag == "box")
        {
            score+=5;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }*/
	if(target.tag == "d6")
        {
            score/=6;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        } 
        if(target.tag == "d2")
        {
            score/=2;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "d3")
        {
            score/=3;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "d4")
        {
            score/=4;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "d5")
        {
            score/=5;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "d7")
        {
            score/=7;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "d8")
        {
            score/=8;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
	if(target.tag == "+1")
        {
            score+=1;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+2")
        {
            score+=2;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+3")
        {
            score+=3;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+4")
        {
            score+=4;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+5")
        {
            score+=5;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+6")
        {
            score+=6;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+7")
        {
            score+=7;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        
        if(target.tag == "+8")
        {
            score+=8;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "+9")
        {
            score+=9;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        
        if(target.tag == "m1")
        {
            score*=1;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m2")
        {
            score*=2;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m3")
        {
            score*=3;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m4")
        {
            score*=4;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m5")
        {
            score*=5;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m6")
        {
            score*=6;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m7")
        {
            score*=7;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m8")
        {
            score*=8;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "m9")
        {
            score*=9;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
	if(target.tag == "m2")
        {
            score*=2;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
	if(target.tag == "p2")
        {
            score+=2;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
	if(target.tag == "s8")
        {
            score-=8;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
	if(target.tag == "s9")
        {
            score-=9;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s7")
        {
            score-=7;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s6")
        {
            score-=6;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s5")
        {
            score-=5;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s4")
        {
            score-=4;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s3")
        {
            score-=3;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s2")
        {
            score-=2;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
        if(target.tag == "s1")
        {
            score-=1;
            scoreText.text = score.ToString();
            target.gameObject.SetActive(false);
        }
    }

    public void  CompleteLevel()
    {		score=0;
		completeLevelUI.SetActive(true);
		StartCoroutine(RestartGame());
    }
    
     
   
    public void targetfunct()
    {
	score=0;
	scoreText.text= "0";
	scoreText.text = score.ToString();
    }
   

    IEnumerator RestartGame()
    {
        yield return new WaitForSecondsRealtime(1f);
	SceneManager.LoadScene("Gameplay/Menu");
        //SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }
    // Update is called once per frame
    void Update()
    {
        if(score < 0)
	{
		score=0;
		scoreText.text= "0";
		scoreText.text = score.ToString();
               
	}
	/*if(score == targetScore.Target)
	{
		  CompleteLevel();
		 //SceneManager.LoadScene("Gameplay/Menu");
	}*/
	/*x=Spawner.target1();
	if(score > x)
	{ 
		 CompleteLevel();
	}*/
	
	/*if(score > targetscore.Target)
	{
		FindObjectOfType<targetText>().targetfunct;
	}*/
    }
}
