/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.panako.strategy.balancedpeaks.storage;

import be.panako.cli.Panako;
import be.panako.strategy.fft.FFTFingerprint;
import be.panako.strategy.fft.storage.FFTFingerprintQueryMatch;
import be.panako.strategy.nfft.storage.NFFTMapDBStorage;
import be.panako.util.Config;
import be.panako.util.Key;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.logging.Logger;
import org.mapdb.Atomic;
import org.mapdb.BTreeKeySerializer;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

/**
 * A singleton class that interacts with a MapDB. It acts as a database controller.
 * @author Mattia Cerrato <mattia.cerrato@edu.unito.it>
 */
public class BalancedPeaksMapDBStorage {
    private static BalancedPeaksMapDBStorage instance;
    private final DB db;
    private final static Logger LOG = Logger.getLogger(BalancedPeaksMapDBStorage.class.getName());
    
    /**
    * A mutex for synchronization purposes
    */
    private static Object mutex = new Object();
    
    private ConcurrentNavigableMap<Integer, String> audioNameStore;

    private NavigableSet<Fun.Tuple3<Integer, Integer, Integer>> balPeaksFingerprintStore;

    private Atomic.Long secondsCounter;

    
    private BalancedPeaksMapDBStorage() {
        final String audioStore = "audio_store";
        final String balPeaksStore = "balpeaks_store";
        
        File dbFile = new File(Config.get(Key.BALPEAKS_MAPDB_DATABASE));
        
        // if this application writes to storage (i.e. store) and a dbfile has not yet been created
        if (Panako.getCurrentApplication().writesToStorage() && !dbFile.exists()) {
            // create it
            db = DBMaker.newFileDB(dbFile)
                .closeOnJvmShutdown() // close the database automatically
                .make();
            
            // create a meta-data storage. unclear what this is. maybe the .t and .d db files?
            audioNameStore = db.createTreeMap(audioStore)
                    .counterEnable()
                    .makeOrGet();
            
            balPeaksFingerprintStore = db.createTreeSet(balPeaksStore)
                .counterEnable() // enable size counter
                .serializer(BTreeKeySerializer.TUPLE3)
                .makeOrGet();
            
            // create a seconds counter
            secondsCounter = db.getAtomicLong("seconds_counter");
        }
        // the db is needed, but in read-only mode
        else if (Panako.getCurrentApplication().needsStorage()) {
            db = DBMaker.newFileDB(dbFile)
                .closeOnJvmShutdown() // close the database automatically
                .readOnly() // make the database read only
                .make();
            
            audioNameStore = db.getTreeMap(audioStore);
            balPeaksFingerprintStore = db.getTreeSet(balPeaksStore);
            secondsCounter = db.getAtomicLong("seconds_counter");
            
        }
        // no db is really needed
        else {
            secondsCounter = null;
            audioNameStore = null;
            balPeaksFingerprintStore = null;
            db = null;
        }
    }
    
    public static BalancedPeaksMapDBStorage getInstance() {
        if (instance == null) {
            synchronized(mutex) {
                if (instance == null) {
                    return new BalancedPeaksMapDBStorage();
                }
            }
        }
        return instance;
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
