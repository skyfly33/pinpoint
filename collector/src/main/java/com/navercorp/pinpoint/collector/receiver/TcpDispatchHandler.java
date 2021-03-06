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

package com.navercorp.pinpoint.collector.receiver;

import com.navercorp.pinpoint.collector.handler.AgentInfoHandler;
import com.navercorp.pinpoint.collector.handler.IruenInfoHandler;
import com.navercorp.pinpoint.collector.handler.RequestResponseHandler;
import com.navercorp.pinpoint.collector.handler.SimpleHandler;
import com.navercorp.pinpoint.thrift.dto.*;

import org.apache.thrift.TBase;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author emeroad
 * @author koo.taejin
 * @author lee.donghoon
 */
public class TcpDispatchHandler extends AbstractDispatchHandler {

    @Autowired()
    @Qualifier("agentInfoHandler")
    private AgentInfoHandler agentInfoHandler;

    @Autowired()
    @Qualifier("iruenInfoHandler")
    private IruenInfoHandler iruenInfoHandler;

    @Autowired()
    @Qualifier("sqlMetaDataHandler")
    private RequestResponseHandler sqlMetaDataHandler;

    @Autowired()
    @Qualifier("apiMetaDataHandler")
    private RequestResponseHandler apiMetaDataHandler;

    @Autowired()
    @Qualifier("stringMetaDataHandler")
    private RequestResponseHandler stringMetaDataHandler;


    public TcpDispatchHandler() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }


    @Override
    RequestResponseHandler getRequestResponseHandler(TBase<?, ?> tBase) {
        if (tBase instanceof TSqlMetaData) {
            return sqlMetaDataHandler;
        }
        if (tBase instanceof TApiMetaData) {
            return apiMetaDataHandler;
        }
        if (tBase instanceof TStringMetaData) {
            return stringMetaDataHandler;
        }
        if (tBase instanceof TAgentInfo) {
            return agentInfoHandler;
        }
        if (tBase instanceof TIruenInfo) {
            return iruenInfoHandler;
        }
        return null;
    }

    @Override
    SimpleHandler getSimpleHandler(TBase<?, ?> tBase) {

        if (tBase instanceof TAgentInfo) {
            return agentInfoHandler;
        } else if (tBase instanceof TIruenInfo) {
            return iruenInfoHandler;
        }

        return null;
    }
}
