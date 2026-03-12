// This file has been updated to unlock TX on full RX frequency range.

public class RadioAudioService {
    // Frequency limits for 2m
    private static final float min2mTxFreq = 134.0f; // Updated from 144.0f
    private static final float max2mTxFreq = 174.0f; // Updated from 148.0f

    // Frequency limits for 70cm
    private static final float min70cmTxFreq = 400.0f; // Updated from 420.0f
    private static final float max70cmTxFreq = 480.0f; // Updated from 450.0f

    // Other code ...
}