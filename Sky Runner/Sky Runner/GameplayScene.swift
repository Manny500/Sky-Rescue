//
//  GameplayScene.swift
//  Sky Runner
//
//  Created by Manuel Tenorio on 8/11/17.
//  Copyright © 2017 Manuel Tenorio. All rights reserved.
//

import SpriteKit

class GameplayScene: SKScene {
    
    private var bg1: BGClass?;
    private var bg2: BGClass?;
    private var bg3: BGClass?;
    
    private var guide1: GuideClass?;
    private var guide2: GuideClass?;
    private var guide3: GuideClass?;
    
    private var player: Player?;

    private var mainCamera: SKCameraNode?;
    
    override func didMove(to view: SKView) {
        
        initializeGame();
    }
    
    override func update(_ currentTime: TimeInterval) {
        manageCamera();
        manageBGsAndGuide()
        player?.move();
    }
    
    
    private func initializeGame() {
        
        mainCamera = childNode(withName: "MainCamera") as? SKCameraNode!
        
        bg1 = childNode(withName: "BG1") as? BGClass!;
        bg2 = childNode(withName: "BG2") as? BGClass!;
        bg3 = childNode(withName: "BG3") as? BGClass!;
        
        guide1 = childNode(withName: "Guide1") as? GuideClass!;
        guide2 = childNode(withName: "Guide2") as? GuideClass!;
        guide3 = childNode(withName: "Guide3") as? GuideClass!;
        
        player = childNode(withName: "Player") as? Player!;
        player?.initializePlayer();

    }
    
    private func manageCamera() {
        
        self.mainCamera?.position.y += 10;
    }
    
    private func manageBGsAndGuide(){
        
        bg1?.moveBG(camera: mainCamera!);
        bg2?.moveBG(camera: mainCamera!);
        bg3?.moveBG(camera: mainCamera!);
        
        guide1?.moveGuide(camera: mainCamera!);
        guide2?.moveGuide(camera: mainCamera!);
        guide3?.moveGuide(camera: mainCamera!);


    }
    
    private func moveLeft(){
        
    }
    private func moveRight(){
        
    }
}
