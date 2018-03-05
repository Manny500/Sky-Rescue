///Users/manueltenorio/Sky-Rescue/Sky Runner/Sky Runner/Player.swift
//  Player.swift
//  Sky Runner
//
//  Created by Manuel Tenorio on 8/11/17.
//  Copyright © 2017 Manuel Tenorio. All rights reserved.
//

import SpriteKit

struct ColliderType{
    
    static let PLAYER: UInt32 = 0;
    static let ROCKET_AND_COLLECTABLES: UInt32 = 1;

}
class Player: SKSpriteNode {
    
    
    func initializePlayer(){
        
        name = "Player";
        
        physicsBody = SKPhysicsBody(rectangleOf: CGSize(width: self.size.width - 100 , height: self.size.height - 60));
        
        physicsBody?.affectedByGravity = false;
        physicsBody?.categoryBitMask = ColliderType.PLAYER;
        physicsBody?.collisionBitMask = ColliderType.ROCKET_AND_COLLECTABLES;
        
    }
    
    func move() {
        
        self.position.y += 10;
    }
    
}
