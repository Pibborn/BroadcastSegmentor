/***************************************************************************
*                                                                          *                     
* Panako - acoustic fingerprinting                                         *   
* Copyright (C) 2014 - Mattia Cerrato                                      *   
*                                                                          *
* This program is free software: you can redistribute it and/or modify     *
* it under the terms of the GNU Affero General Public License as           *
* published by the Free Software Foundation, either version 3 of the       *
* License, or (at your option) any later version.                          *
*                                                                          *
* This program is distributed in the hope that it will be useful,          *
* but WITHOUT ANY WARRANTY; without even the implied warranty of           *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
* GNU Affero General Public License for more details.                      *
*                                                                          *
* You should have received a copy of the GNU Affero General Public License *
* along with this program.  If not, see <http://www.gnu.org/licenses/>     *
*                                                                          *
****************************************************************************
*    ______   ________   ___   __    ________   ___   ___   ______         *
*   /_____/\ /_______/\ /__/\ /__/\ /_______/\ /___/\/__/\ /_____/\        *      
*   \:::_ \ \\::: _  \ \\::\_\\  \ \\::: _  \ \\::.\ \\ \ \\:::_ \ \       *   
*    \:(_) \ \\::(_)  \ \\:. `-\  \ \\::(_)  \ \\:: \/_) \ \\:\ \ \ \      * 
*     \: ___\/ \:: __  \ \\:. _    \ \\:: __  \ \\:. __  ( ( \:\ \ \ \     * 
*      \ \ \    \:.\ \  \ \\. \`-\  \ \\:.\ \  \ \\: \ )  \ \ \:\_\ \ \    * 
*       \_\/     \__\/\__\/ \__\/ \__\/ \__\/\__\/ \__\/\__\/  \_____\/    *
*                                                                          *
****************************************************************************
*                                                                          *
*                              Panako                                      * 
*                       Acoustic Fingerprinting                            *
*                                                                          *
****************************************************************************/


package be.panako.strategy.balancedpeaks;


/** ==TODO== 
 * In a BalancedPeaks strategy, a fingerprint is a pair of spectrogram peaks. 
 * The fingerprint is identified by its hash value
 * @author Mattia Cerrato <mattia.cerrato@edu.unito.it>
 */
public class BalancedPeaksFingerprint {
    // the analysis frame at which the two peaks were detected
    public int t1;
    public int t2;
    
    // the fft bin at which the two peaks were detected
    public int b1;
    public int b2;
    
    
    public BalancedPeaksFingerprint(int t1, int t2, int b1, int b2) {
        this.t1 = t1;
        this.t2 = t2;
        this.b1 = b1;
        this.b2 = b2;
    }
    
    public BalancedPeaksFingerprint(BalancedPeaksEventPoint p1, BalancedPeaksEventPoint p2) {
        this.t1 = p1.getTime();
        this.t2 = p2.getTime();
        this.b1 = p1.getBin();
        this.b2 = p2.getBin();
    }
    
    public int getHash() {
        int timeDiff = t2 - t1;
        
        return timeDiff;
    }
}
