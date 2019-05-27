using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class targetScore2 : MonoBehaviour
{
    private Text TargetText2;
    private int Target2;
    private Text Text;
    public GameObject completeLevelUI2;
    // Start is called before the first frame update
    void  Awake()
    {
        TargetText2=GameObject.Find("targetText2").GetComponent<Text>();
	Target2=Random.Range(145,155);
	TargetText2.text=Target2.ToString();
    }
    public void  CompleteLevel2()
    {		PlayerScore.score=0;
		completeLevelUI2.SetActive(true);
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
        if(PlayerScore.score > Target2)
	{
		CompleteLevel2();
 		//PlayerScore.score=0;
		
		//SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex - 2);
	}

    }
}
