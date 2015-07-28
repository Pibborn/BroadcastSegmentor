import numpy.fft
from scipy.io import wavfile
import sys
import math
import csv

def ccwRotation(matrix):
    reverse_matrix = matrix[::1]
    rotated_matrix = zip(*reverse_matrix)
    return rotated_matrix
    
def extractSpectrogramMatrix(path):
    fftStepSize = 1024
    sf, data = wavfile.read(path)
    left_channel = data.T[0]
    if data.dtype == "int16":
        # da complemento a 2 a [-1, 1]
        left_channel_norm = left_channel / (2.**15)
    else:
        print "unsupported wav type: "+str(data.dtype)
        return
    print "fourier transform..."
#   left_channel_fft = numpy.fft.fft(left_channel_norm)
#   left_channel_fft = list(math.log(abs(elem)) for elem in left_channel_fft)
    samplesCont = 0
    total_fft = []
    while samplesCont < len(data.T[0]) - fftStepSize: # drops the last incomplete frame
        frame_fft = numpy.fft.fft(left_channel_norm[samplesCont:samplesCont+fftStepSize-1])
        frame_fft = frame_fft[0:math.ceil((fftStepSize+1)/2)]
        frame_fft = abs(frame_fft)
        frame_fft = frame_fft / float(fftStepSize)
        frame_fft = frame_fft**2
        if fftStepSize % 2 > 0:
            frame_fft[1:len(frame_fft)] = p[1:len(frame_fft)] * 2
        else:
            frame_fft[1:len(frame_fft) -1] = frame_fft[1:len(frame_fft) - 1] * 2
        samplesCont += fftStepSize
        total_fft.append(frame_fft)
    print "...done"
    return total_fft

def toCsv(specMatrix):
    i = 0
    csvfile = open("spectrum.csv", "w")
    spectrumwriter = csv.writer(csvfile, delimiter=',',
                            quotechar='|', quoting=csv.QUOTE_MINIMAL)
    while (i < len(specMatrix)):
        spectrumwriter.writerow(specMatrix[i])
        i += 1



def main():
    specMatrix = extractSpectrogramMatrix(sys.argv[1])

main()
