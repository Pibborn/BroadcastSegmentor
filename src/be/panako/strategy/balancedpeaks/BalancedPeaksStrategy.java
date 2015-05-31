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

import be.panako.strategy.QueryResultHandler;
import be.panako.strategy.Strategy;
import be.panako.strategy.balancedpeaks.storage.BalancedPeaksFingerprintHit;
import be.panako.strategy.balancedpeaks.storage.BalancedPeaksFingerprintQueryMatch;
import be.panako.strategy.balancedpeaks.storage.BalancedPeaksMapDBStorage;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mattia Cerrato <mattia.cerrato@edu.unito.it>
 */
public class BalancedPeaksStrategy extends Strategy {
    
    private final BalancedPeaksMapDBStorage storage;

    public BalancedPeaksStrategy() {
        storage = BalancedPeaksMapDBStorage.getInstance();
    }
    
    @Override
    public double store(String resource, String description) {
        double durationInSeconds = 0.0f;
        System.out.println("hello store!");
        return durationInSeconds;
    }
    
    @Override
    public void query(String query, final int maxNumberOfResults, QueryResultHandler handler) {
        // di nuovo get dell'istanza del dbcontroller?
        
        final List<BalancedPeaksFingerprintQueryMatch> queryMatchList = new ArrayList<>();
        
        System.out.println("hello query!");
        // da capire: queryhandler, riga 129 di fftstrategy
    }

    @Override
    public void printStorageStatistics() {
        System.out.println("Unsupported operation as of now.");
    }
    
    @Override
    public boolean isStorageAvailable() {
        // non mi curo ancora della concorrenza di più query, TODO
        return true;
    }
    
    @Override
    public boolean hasResource(String resource) {
        // ancora cose sulla concorrenza e le risorse (le entry di una hashmap, mi pare). TODO
        
        // ritorno false in modo che - per ora - non vengano mai percepiti i duplicati nel db
        // (che poi non esiste). TODO

        return false;
    }
    
    @Override
    public void monitor(String query, final int maxNumberOfReqults, final QueryResultHandler handler) {
        // di nuovo get dell'istanza del dbcontroller?
    }
}
