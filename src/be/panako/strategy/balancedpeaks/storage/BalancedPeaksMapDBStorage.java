/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.panako.strategy.balancedpeaks.storage;

import be.panako.strategy.fft.FFTFingerprint;
import be.panako.strategy.fft.storage.FFTFingerprintQueryMatch;
import be.panako.util.Config;
import be.panako.util.Key;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * A singleton class that interacts with a MapDB. It acts as a database controller.
 * @author Mattia Cerrato <mattia.cerrato@edu.unito.it>
 */
public class BalancedPeaksMapDBStorage {
    private static BalancedPeaksMapDBStorage instance;
    private final DB db;
    
    private BalancedPeaksMapDBStorage() {
        File dbFile = new File(Config.get(Key.FFT_MAPDB_DATABASE));
        db = DBMaker.newFileDB(dbFile)
					.closeOnJvmShutdown() // close the database automatically
					.make();
    }
    
    public static BalancedPeaksMapDBStorage getInstance() {
        if (instance == null) return new BalancedPeaksMapDBStorage();
        else return instance;
    }
    
    public List<FFTFingerprintQueryMatch> getMatches(List<FFTFingerprint> fingerprints, int size) {
        List<FFTFingerprintQueryMatch> matchesList = new ArrayList<>();
        return matchesList;
    }

    public boolean hasDescription(String description) {
        // non c'è il controllo dei duplicati nel database, per ora. TODO
        return false;
    }
}
