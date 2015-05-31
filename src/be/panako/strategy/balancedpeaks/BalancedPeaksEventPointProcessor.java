/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.panako.strategy.balancedpeaks;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

/**
 *
 * @author Mattia Cerrato <mattia.cerrato@edu.unito.it>
 */
public class BalancedPeaksEventPointProcessor implements AudioProcessor {
    
    public boolean process(AudioEvent event) {
        return true;
    }
    
    public void processingFinished() {
        
    }
    
}
