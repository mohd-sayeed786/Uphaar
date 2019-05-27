using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoveCamera : MonoBehaviour
{
    private float speed = 2f;

    // Update is called once per frame
    void Update()
    {
        Vector3 temp = transform.position;
        temp.x += speed * Time.deltaTime;
        if(speed<=5f)
            speed = speed + Time.deltaTime/3;
        transform.position = temp;
        
    }
}
