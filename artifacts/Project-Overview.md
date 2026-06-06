# Project Overview: kv4p-ht

The `kv4p-ht` is an open-source handheld amateur (ham) radio transceiver project. It enables makers and radio enthusiasts to turn their Android smartphone into a modern handheld transceiver by connecting to a custom ESP32-based hardware interface via a USB-C cable. The device operates at 1 watt on VHF or UHF frequencies using a custom hardware board featuring a DRA818 or SA818 RF module.

---

## 1. File Map & Directory Structure

*   **[3d-print-case/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/3d-print-case)**: Contains the 3D-printable enclosure models (STL, STEP format) for housing the PCB.
*   **[artwork/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/artwork)**: Graphics, logo resources, and app launcher icons.
*   **[android-src/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/android-src)**: Android Studio project comprising the Android companion app.
    *   **[KV4PHT/app/src/main/java/com/vagell/kv4pht/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/android-src/KV4PHT/app/src/main/java/com/vagell/kv4pht)**: Main packages:
        *   `aprs/`: APRS packet encoding, decoding, weather reporting, and position parsing.
        *   `data/`: Room Database local cache layers.
        *   `firmware/`: Flashing library code to update ESP32 firmware directly from the phone.
        *   `javAX25/`: Pure Java software AFSK1200 modulator/demodulator and AX.25 frame processing.
        *   `radio/`: Manages serial communication protocol, audio stream compression (Opus), tone generation, and background execution services.
        *   `ui/`: Android UI components (ViewModels, Activities, and Fragments).
*   **[microcontroller-src/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/microcontroller-src)**: ESP32 firmware source code.
    *   `platformio.ini`: PlatformIO build specification targeting the Espressif32 dev board with Arduino framework.
    *   **[kv4p_ht_esp32_wroom_32/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/microcontroller-src/kv4p_ht_esp32_wroom_32)**: Firmware source directory:
        *   `kv4p_ht_esp32_wroom_32.ino`: Core entry point, setup, and control loop.
        *   `board.h`: Dynamic hardware pins configuration, legacy auto-detection, and NVS preferences.
        *   `globals.h`: Global structures, defaults, volume settings, and runtime state enumerations.
        *   `protocol.h`: Serial transmission packet format, framing, and command dispatch.
        *   `rxAudio.h` / `txAudio.h`: Multi-threaded I2S audio interfaces, analog-to-digital (ADC) conversion, and digital-to-analog (DAC) conversion.
*   **[pcb/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/pcb)**: KiCad schematic and PCB design files.
*   **[website-src/](file:///c:/Users/EyalRashelbach/antigravity/kv4p-ht-GMDSS/website-src)**: Source code for the landing page (`index.html`) and an interactive in-browser firmware flasher (`firmware.html`) using Web Serial APIs.

---

## 2. Key Modules & Subsystems

```mermaid
graph TD
    subgraph Android App (Java)
        UI[UI / Activities] <--> VM[ViewModels]
        VM <--> RAS[RadioAudioService]
        RAS <--> Opus[Opus Codec (Concentus)]
        RAS <--> AX25[javAX25 (Modulator/Demodulator)]
        RAS <--> AP[APRS Parser]
        RAS <--> USB[usbSerialForAndroid]
    end

    subgraph Hardware Board
        ESP32[ESP32 Microcontroller] <--> RadioModule[SA818 / DRA818 RF Module]
        ESP32 <--> I2S_Codec[I2S Audio ADC/DAC]
    end

    USB <--- USB_Cable ---> ESP32
```

### A. Android Companion App
*   **Modem & AX.25 Subsystem (`javAX25`)**: Handles pure software-based digital signal processing (DSP) to modulate binary frames into AFSK1200 audio tones and demodulate incoming audio tones back into binary frames.
*   **APRS Engine (`aprs`)**: Implements parsers and validators for the Automatic Packet Reporting System (APRS). Features include decoding/beaconing positions, text messaging, and weather telemetry.
*   **Radio Integration (`radio`)**: Handles low-level serial communication framing, flow control, and background service lifecycle (`RadioAudioService.java`).

### B. Microcontroller Firmware
*   **PlatformIO / Arduino Coexistence**: Built dynamically under both PlatformIO (using `platformio.ini`) and Arduino IDE (using `.ino` file references).
*   **Dynamic Hardware Config (`board.h`)**: Supports reading and persisting board settings (e.g. customized GPIO assignments, attenuation levels) to the ESP32 NVS (Non-Volatile Storage) preferences library.
*   **Audio Pipelines (`rxAudio.h` / `txAudio.h`)**: Manages real-time audio sample transfers at 48kHz. In Rx mode, it reads samples from the built-in ADC via DMA (Direct Memory Access), packetizes, and streams it over USB. In Tx mode, it accepts audio samples from USB, decompresses them, and plays them via the built-in DAC.

---

## 3. Architecture Decisions

1.  **No On-Board Battery**:
    The hardware design does not include a battery or power management IC. It derives its power directly from the connected Android device over USB-C, simplifying enclosure size, safety, and shelf-life.
2.  **Dual-Core Execution on ESP32**:
    The firmware is built targeting dual cores (`-DARDUINO_RUNNING_CORE=1` / `-DARDUINO_EVENT_RUNNING_CORE=0`). Critical audio sampling and processing tasks execute on Core 0/1 separately to ensure time-critical interrupts are serviced promptly, eliminating audio stuttering.
3.  **Opus Compressed Audio**:
    To reduce serial bandwidth and overhead over USB connection, audio is compressed using the Opus audio codec (via `concentus` on Android and `arduino-libopus` on ESP32).
4.  **Flow-Control Command Window**:
    Due to the limited buffers of the USB serial interface on both host and client, a sliding-window-based flow control protocol is implemented. The host tracks available buffer space (`flowControlWindow`) and blocks when sending packets that exceed it. The ESP32 sends `COMMAND_WINDOW_UPDATE` notifications back to the host when it successfully consumes data, enlarging the host's window.

---

## 4. Communication Protocol & Framing Specification

The serial link between Android and the ESP32 runs at **115200 baud**. Both devices utilize the same packet framing structure:

```
+-----------------------------------+--------------------+------------------------+--------------------------+
| DELIMITER (4 bytes)               | COMMAND (1 byte)   | PARAM_LEN (2 bytes)    | PARAM DATA (N bytes)     |
| 0xDE, 0xAD, 0xBE, 0xEF            | e.g. 0x01          | Little-Endian length   | Byte array               |
+-----------------------------------+--------------------+------------------------+--------------------------+
```

### Command Sets

#### Host to ESP32 (SndCommand / RcvCommand)
*   `0x01` (`COMMAND_HOST_PTT_DOWN`): Key up the radio transmitter.
*   `0x02` (`COMMAND_HOST_PTT_UP`): Release the transmitter, return to Rx mode.
*   `0x03` (`COMMAND_HOST_GROUP`): Sets RF parameters (band, frequency, CTCSS tones, squelch level).
*   `0x04` (`COMMAND_HOST_FILTERS`): Configures pre-emphasis, high-pass, and low-pass filters.
*   `0x05` (`COMMAND_HOST_STOP`): Disables radio functions and enters low-power stand-by mode.
*   `0x06` (`COMMAND_HOST_CONFIG`): Requests initialization and queries module status.
*   `0x07` (`COMMAND_HOST_TX_AUDIO`): Sends a chunk of Opus-encoded transmit audio data.
*   `0x08` (`COMMAND_HOST_HL`): Configures high/low transmitter output power.
*   `0x09` (`COMMAND_HOST_RSSI`): Toggles real-time RSSI signal strength reports.

#### ESP32 to Host (RcvCommand / SndCommand)
*   `0x53` (`COMMAND_SMETER_REPORT`): Periodic signal strength report (RSSI).
*   `0x44` (`COMMAND_PHYS_PTT_DOWN`): Alerts the host that the physical PTT button on the case has been pressed.
*   `0x55` (`COMMAND_PHYS_PTT_UP`): Alerts the host that the physical PTT button has been released.
*   `0x01`–`0x05` (`COMMAND_DEBUG_INFO/ERROR/WARN/DEBUG/TRACE`): Debug logs generated by the microcontroller, routed to logcat.
*   `0x06` (`COMMAND_HELLO`): Emitted by the ESP32 upon reboot.
*   `0x07` (`COMMAND_RX_AUDIO`): Sends raw demodulated audio samples from ESP32 to Android.
*   `0x08` (`COMMAND_VERSION`): Returns firmware version, radio status code, serial window size, module type, and hardware flags.
*   `0x09` (`COMMAND_WINDOW_UPDATE`): Signals window increment to unblock the host's serial sender queue.

---

## 5. Coding Standards & Important Patterns

*   **Cross-IDE Compatibility**:
    Keep firmware source files in the `.ino` format so they remain fully compatible with both PlatformIO CLI / VSCode plugin and the standard Arduino IDE. Revert extensions back to `.ino` before committing.
*   **Struct Packing & Trivially Copyable Types**:
    All packet parameter structures defined in `protocol.h` (e.g. `Version`, `Group`, `Filters`) must be packed using `[[gnu::packed]]`. Include a static assertion checks `REQUIRE_TRIVIALLY_COPYABLE` to prevent compiler differences in padding:
    ```cpp
    struct [[gnu::packed]] Group {
      uint8_t bw;
      float freq_tx;
      float freq_rx;
      uint8_t ctcss_tx;
      uint8_t squelch;
      uint8_t ctcss_rx;
    };
    REQUIRE_TRIVIALLY_COPYABLE(Group);
    ```
*   **Asynchronous Flow-controlled Writes**:
    On Android, when queuing command packets or audio buffers, use the `waitUntilCanSend(size)` pattern before invoking `writeAsync()`. This prevents saturating the Android USB serial buffer.
*   **Lombok Annotations**:
    Lombok is heavily utilized in the Java app for auto-generating builders, getters, and data structures. For example:
    ```java
    @Data
    @Builder
    public static class Filters {
        private final boolean pre;
        private final boolean high;
        private final boolean low;
        ...
    }
    ```
*   **Watchdog Resets**:
    ESP32 task watchdog (`esp_task_wdt`) is set to 10 seconds. Long-running or heavy operations must call `esp_task_wdt_reset()` periodically to avoid hardware reboots.
