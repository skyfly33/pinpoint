/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.collector.handler;

import com.navercorp.pinpoint.thrift.dto.TIruenInfo;
import com.navercorp.pinpoint.thrift.dto.TResult;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author lee.donghoon
 */
@Service("iruenInfoHandler")
public class IruenInfoHandler implements SimpleHandler, RequestResponseHandler {

    private final Logger logger = LoggerFactory.getLogger(IruenInfoHandler.class.getName());

    public void handleSimple(TBase<?, ?> tbase) {
        handleRequest(tbase);
    }

    @Override
    public TBase<?, ?> handleRequest(TBase<?, ?> tbase) {
        if (!(tbase instanceof TIruenInfo)) {
            logger.warn("invalid tbase:{}", tbase);
            // it happens to return null  not only at this BO(Business Object) but also at other BOs.

            return null;
        }

        try {
            TIruenInfo iruenInfo = (TIruenInfo) tbase;

            logger.debug("Received IruenInfo={}", iruenInfo);
            return new TResult(true);

        } catch (Exception e) {
            logger.warn("IruenInfo handle error. Caused:{}", e.getMessage(), e);
            TResult result = new TResult(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

}
