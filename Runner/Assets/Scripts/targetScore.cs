using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


public class targetScore : MonoBehaviour
{
    private Text TargetText;
    //private Text TargetText2;
    public static int Target;
    //private int Target2;
    private Text Text;
    public GameObject completeLevelUI1;

    
    void Awake()
    {   
	
        TargetText=GameObject.Find("targetText").GetComponent<Text>();
	Target=Random.Range(80,140);
	TargetText.text=Target.ToString();
	
	//print(Target);
    }
    public void  CompleteLevel1()
    {		PlayerScore.score=0;
		completeLevelUI1.SetActive(true);
		StartCoroutine(RestartGame());
    }
    IEnumerator RestartGame()
    {
        yield return new WaitForSecondsRealtime(1f);
	SceneManager.LoadScene("Gameplay/Menu");
    }
    // Update is called once per frame
    void Update()
    {
        if(PlayerScore.score == Target)
	{	
		CompleteLevel1();
		//print("you won");
		//StartCoroutine(RestartGame());
	}

    }
}
