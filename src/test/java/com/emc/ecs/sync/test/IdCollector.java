/*
 * Copyright 2013-2017 EMC Corporation. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.emc.ecs.sync.test;

import com.emc.ecs.sync.config.annotation.FilterConfig;
import com.emc.ecs.sync.filter.AbstractFilter;
import com.emc.ecs.sync.model.ObjectContext;
import com.emc.ecs.sync.model.SyncObject;

import java.util.HashMap;
import java.util.Map;

public class IdCollector extends AbstractFilter<IdCollector.IdCollectorConfig> {
    @Override
    public void filter(ObjectContext objectContext) {
        config.idMap.put(objectContext.getSourceSummary().getIdentifier(), null);
        getNext().filter(objectContext);
        config.idMap.put(objectContext.getSourceSummary().getIdentifier(), objectContext.getTargetId());
    }

    @Override
    public SyncObject reverseFilter(ObjectContext objectContext) {
        return getNext().reverseFilter(objectContext);
    }

    @FilterConfig(cliName = "id-collecting")
    public static class IdCollectorConfig {
        Map<String, String> idMap = new HashMap<>();

        public Map<String, String> getIdMap() {
            return new HashMap<>(idMap);
        }
    }
}
