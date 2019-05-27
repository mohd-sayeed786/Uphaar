using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerMovement : MonoBehaviour
{

    private Button jumBtn;

    private Rigidbody2D myBody;

    private void Awake()
    {
        jumBtn = GameObject.Find("Jump").GetComponent<Button>();
        jumBtn.onClick.AddListener(() => Jump());
        myBody = GetComponent<Rigidbody2D>();
    }
    private float speed = 2f;

    // Update is called once per frame
    void Update()
    {
        Vector3 temp = transform.position;
        temp.x += speed * Time.deltaTime;
        if (speed <= 5f)
            speed = speed + Time.deltaTime/3;
        transform.position = temp;

    }

    public void Jump()
    {
        myBody.gravityScale *= -1;
        Vector3 temp = transform.localScale;
        temp.y *= -1;
        transform.localScale = temp;
    }
}
