//
//  BGClass.swift
//  Sky Runner
//
//  Created by Manuel Tenorio on 8/11/17.
//  Copyright © 2017 Manuel Tenorio. All rights reserved.
//

import SpriteKit

class BGClass: SKSpriteNode{
    
    func moveBG(camera: SKCameraNode){
        
        if self.position.y + self.size.height < camera.position.y {
            self.position.y += self.size.height * 3;
        }
    }
}
