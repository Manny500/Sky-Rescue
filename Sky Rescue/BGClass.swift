//
//  BGClass.swift
//  Sky Rescue
//
//  Created by Manuel Tenorio on 8/10/17.
//  Copyright © 2017 Manuel Tenorio. All rights reserved.
//

//Class enables use of iphone features
import SpriteKit

//Initialize the class
class BGClass: SKSpriteNode{
    
    /////////////////////////INSTANCES/////////////////////////////////////
    
    /////////////////////////FUNCTIONS/////////////////////////////////////
    
    func moveBG(camera: SKCameraNode) {
        
        //will duplicate the backgrounds in iu once it runs out
        if self.position.y + self.size.height < camera.position.y {
            
            self.position.y += self.size.height * 3;
        }
        
    }//end of moveBG
    
} //End of BGClass
