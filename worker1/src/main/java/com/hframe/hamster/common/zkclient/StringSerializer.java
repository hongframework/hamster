/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
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

package com.hframe.hamster.common.zkclient;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

/**
 * 基于string的序列化方式
 * 
 * @author ** 2012-7-11 下午02:57:09
 * @version 4.1.0
 */
public class StringSerializer implements ZkSerializer {

    public Object deserialize(final byte[] bytes) throws ZkMarshallingError {
        try {
            return new String(bytes, "utf-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ZkMarshallingError(e);
        }
    }

    public byte[] serialize(final Object data) throws ZkMarshallingError {
        try {
            return ((String) data).getBytes("utf-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ZkMarshallingError(e);
        }
    }

}
