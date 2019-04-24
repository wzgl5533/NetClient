/*
 * Copyright (C) 2015 Square, Inc.
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

package com.qlh.netclient.converter;

import com.google.gson.TypeAdapter;
import com.qlh.netclient.base.BaseBean;
import com.qlh.netclient.exception.NoDataExceptionException;
import com.qlh.netclient.exception.ServerResponseException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * 这个是response结果的转换，提取数据部分
 *
 * 根据情况替换BaseBean
 * **/

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            BaseBean response = (BaseBean) adapter.fromJson(value.charStream());
            if (!response.isOk()) {//访问不成功
                // 特定 API 的错误，在相应的 NetworkObserver 的 onError 的方法中进行处理
                throw new ServerResponseException(response.getCode(), response.getErrorResponse());
            } else if (response.isOk()) {//访问成功
                if(response.getValue()!=null)//这个需要根据实际的情况，有的有可能就是空，但尽量避免，以免引起歧义
                return response.getValue();
                else throw new NoDataExceptionException();
            }
        } finally {
            value.close();
        }
        return null;
    }
}
