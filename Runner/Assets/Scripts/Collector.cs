using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Collector : MonoBehaviour
{

    private float width = 0f;

    // Start is called before the first frame update
    void Awake() { width = GameObject.Find("BG").GetComponent<BoxCollider2D>().size.x;}
    //12.8f;

    private void OnTriggerEnter2D(Collider2D target)
    {
        if (target.tag == "BG" || target.tag == "Ground")
        {
            Vector3 temp = target.transform.position;
            temp.x += width * 3;
            target.transform.position = temp;
        }

        if (target.tag == "Coin" || target.tag == "Rocket" || target.tag == "box" || target.tag == "d6" || target.tag == "m2" || target.tag == "p2" || target.tag == "s8" || target.tag == "s9" || target.tag == "p3" || target.tag == "+1"|| target.tag == "+2"|| target.tag == "+3"|| target.tag == "+4"|| target.tag == "+5"|| target.tag == "+6"|| target.tag == "+7"|| target.tag == "+8"|| target.tag == "+9"|| target.tag == "m2" || target.tag == "m3" || target.tag == "m4" || target.tag == "m5" || target.tag == "m6" || target.tag == "m7" || target.tag == "m8" || target.tag == "m9" || target.tag == "s2"|| target.tag == "s3"|| target.tag == "s4"|| target.tag == "s5"|| target.tag == "s6"|| target.tag == "s7"|| target.tag == "s8"|| target.tag == "s1"|| target.tag == "d3"|| target.tag == "d4"|| target.tag == "d5"|| target.tag == "d7"|| target.tag == "d8" || target.tag == "d2")
        {
	    if(PauseMenu.GameIsPaused)
	    {
		target.gameObject.SetActive(true);
		Time.timeScale = 0f;
		
 	    }
            target.gameObject.SetActive(false);
        }

    }
}