/*
 * Copyright (C) 2019 CypherOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.aoscp.hwcontrollers;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import android.view.KeyEvent;

/*
 * Alert Slider API
 *
 * A device can enable utilize it's tri-state key events
 * provided by it's assigned keycode code. User's can swap
 * between zen modes via Alert Slider when this is in use.
 */

public class AlertSliderController {

    // Slider key codes
    private static final int MODE_NORMAL = 601;
    private static final int MODE_VIBRATION = 602;
    private static final int MODE_SILENCE = 603;

    private static boolean sSystemReady = false;
    private static AudioManager sAudioManager;
    private static Vibrator sVibrator;

    /*
     * All HAF classes should export this boolean.
     * Real implementations must, of course, return true
     */
    public static boolean isSupported() {
        return true;
    }

    /*
     * Notifies the handler that the tri-state handler
     * can be used after systemReady() is called
     */
    public static boolean triStateReady(Context context) {
        sAudioManager = context.getSystemService(AudioManager.class);
        sVibrator = context.getSystemService(Vibrator.class);
        sSystemReady = true;
        return true;
    }

    /*
     * The primary handler that changes zen mode based
     * on the trigger tri-state keycode
     */
    public static KeyEvent handleTriStateEvent(KeyEvent event) {
        int scanCode = event.getScanCode();
        if (!sSystemReady) return null;

        switch (scanCode) {
            case MODE_NORMAL:
                sAudioManager.setRingerModeInternal(AudioManager.RINGER_MODE_NORMAL);
                break;
            case MODE_VIBRATION:
                sAudioManager.setRingerModeInternal(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case MODE_SILENCE:
                sAudioManager.setRingerModeInternal(AudioManager.RINGER_MODE_SILENT);
                break;
            default:
                return event;
        }
        doHapticFeedback();

        return null;
    }

    private static void doHapticFeedback() {
        if (sVibrator == null || !sVibrator.hasVibrator()) {
            return;
        }

        sVibrator.vibrate(50);
    }
}