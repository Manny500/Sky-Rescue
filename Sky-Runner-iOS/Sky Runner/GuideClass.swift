//
//  GuideClass.swift
//  Sky Runner
//
//  Created by Manuel Tenorio on 8/11/17.
//  Copyright © 2017 Manuel Tenorio. All rights reserved.
//

import SpriteKit

class GuideClass: SKSpriteNode{
    
    func moveGuide(camera: SKCameraNode){
        
        if self.position.y + self.size.height < camera.position.y {
            
            self.position.y += self.size.height * 3;
        }
    }
}
