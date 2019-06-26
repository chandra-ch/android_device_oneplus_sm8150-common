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

#define LOG_TAG "aoscp.displayengine@1.0-service.oneplus_msmnile"

#include <android-base/logging.h>
#include <binder/ProcessState.h>
#include <hidl/HidlTransportSupport.h>
#include "DisplayModes.h"

using ::vendor::aoscp::displayengine::V1_0::IDisplayModes;
using ::vendor::aoscp::displayengine::V1_0::implementation::DisplayModes;

int main() {
    android::sp<IDisplayModes> deService = new DisplayModes();

    android::hardware::configureRpcThreadpool(1, true /*callerWillJoin*/);

    if (deService->registerAsService() != android::OK) {
        LOG(ERROR) << "Cannot register display modes HAL service.";
        return 1;
    }

    LOG(INFO) << "DisplayEngine HAL service ready.";

    android::hardware::joinRpcThreadpool();

    LOG(ERROR) << "DisplayEngine HAL service failed to join thread pool.";
    return 1;
}
