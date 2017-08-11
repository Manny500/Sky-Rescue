//
//  GuideClass.swift
//  Sky Rescue
//
//  Created by Manuel Tenorio on 8/10/17.
//  Copyright © 2017 Manuel Tenorio. All rights reserved.
//

//Class enables use of iphone features
import SpriteKit

class GuideClass: SKSpriteNode {
    
    /////////////////////////INSTANCES/////////////////////////////////////
    
    /////////////////////////FUNCTIONS/////////////////////////////////////
    
    func moveGuide(camera: SKCameraNode) {
        
        //will duplicate the guide in iu once it runs out
        if self.position.y + self.size.height < camera.position.y {
            
            self.position.y += self.size.height * 3;
        }
        
    }//End fo moveGuide
    
}//End of GuideClass
