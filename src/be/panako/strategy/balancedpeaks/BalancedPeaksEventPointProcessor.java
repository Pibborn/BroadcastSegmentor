/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.panako.strategy.balancedpeaks;

import be.panako.util.Config;
import be.panako.util.Key;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.util.fft.FFT;
import be.tarsos.dsp.util.fft.HammingWindow;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts spectrogram peaks so that they are balanced, that is, for each user-defined band
 * there is a fixed number of peaks that are extracted.
 * @author Mattia Cerrato <mattia.cerrato@edu.unito.it>
 */
public class BalancedPeaksEventPointProcessor implements AudioProcessor {
    int fftSize;
    int fftOverlap;
    int samplerate;
    int minFreq;
    int maxFreq;
    int numBands;
    int stepSize;
    
    private FFT fft;
    private List<BalancedPeaksEventPoint> peakList = new ArrayList<>();
    private List<BalancedPeaksFingerprint> fingerprintList = new ArrayList<>();
    private List<BalancedPeaksEventPoint> framePeakList = new ArrayList<>();

    private float[][] magnitudes;
    private float[][] phases;
    private int frame;

    BalancedPeaksEventPointProcessor(int size, int overlap, int samplerate) {
        this.fftSize = size;
        this.fftOverlap = overlap;
        this.samplerate = samplerate;
        
        this.fft = new FFT(size, new HammingWindow());
        this.minFreq = Config.getInt(Key.BALPEAKS_MIN_FREQ);
        this.maxFreq = Config.getInt(Key.BALPEAKS_MAX_FREQ);
        this.numBands = Config.getInt(Key.BALPEAKS_BANDS);
        this.stepSize = Config.getInt(Key.BALPEAKS_FFT_STEP_SIZE);
        
        this.magnitudes = new float[10000][fftSize/2];
        this.phases = new float[10000][fftSize/2];
        
        this.frame = 0;
    }
    
    @Override
    public boolean process(AudioEvent event) {
        float[] audio = event.getFloatBuffer();
        
        float[] audioCopy = audio.clone();
        //calculate the fft
//        fft.forwardTransform(audio);

        fft.powerPhaseFFT(audioCopy, magnitudes[frame], phases[frame]);
        //store the magnitudes (moduli) in magnitudes
//        fft.powerAndPhaseFromFFT(audio, magnitudes[frame], phases[frame]);
        
        //separate the magnitude array in equal parts and extract the highest peaks from each part
        framePeakList = extractBalancedPeaks(magnitudes[frame]);
        
        // store the frame peaks in the general list
        peakList.addAll(framePeakList);
        frame++;
        return true;
    }
    
    @Override
    public void processingFinished() {
//        packEventPointsIntoFingerprints();
        System.out.println("Hello fingerprint extraction!");
    }

    private void packEventPointsIntoFingerprints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<BalancedPeaksEventPoint> extractBalancedPeaks(float[] magnitudes) {
        // size of a frequency band in fft bins
        int bandSize = magnitudes.length / this.numBands;
        
        BalancedPeaksEventPoint bandPeak;
        framePeakList = new ArrayList<BalancedPeaksEventPoint>();
        int i = 0; 
        for (int j = 0; j < numBands; j++) {
            float maxMagnitude = Float.MIN_VALUE;
            int maxMagnitudeIndex = -1;
            for (i = i; i < bandSize * (j+1); i++) {
                if (magnitudes[i] > maxMagnitude) {
                    maxMagnitude = magnitudes[i];
                    maxMagnitudeIndex = i;
                } 
            }
            bandPeak = new BalancedPeaksEventPoint(frame, maxMagnitudeIndex, maxMagnitude);
            framePeakList.add(bandPeak);
        }
        return framePeakList;
    }
    
    public List<BalancedPeaksEventPoint> getPeaks() {
        return peakList;
    }
    
    public float[][] getMagnitudes() {
        return this.magnitudes;
    }
    
}
